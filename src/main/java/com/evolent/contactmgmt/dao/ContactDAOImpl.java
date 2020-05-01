package com.evolent.contactmgmt.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.evolent.contactmgmt.entity.ContactEntity;
import com.evolent.contactmgmt.exceptions.DuplicateContactException;
import com.evolent.contactmgmt.exceptions.NoSuchContactException;
import com.evolent.contactmgmt.model.Contact;

@Repository(value = "contactDAO")
public class ContactDAOImpl implements ContactDAO {

	@PersistenceContext
	private EntityManager entityManager;

	ContactEntity entity;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String addContact(Contact contact) throws Exception {
			entity = entityManager.find(ContactEntity.class, contact.getPhoneNo());
			if (entity == null) {
				entity = new ContactEntity();
				entity.setEmailId(contact.getEmailId());
				entity.setFirstName(contact.getFirstName());
				entity.setLastName(contact.getLastName());
				entity.setPhoneNo(contact.getPhoneNo());
				entity.setStatus(contact.getStatus().toUpperCase());
				entityManager.persist(entity);
				return entity.getPhoneNo();
			} else
				throw new DuplicateContactException("DAO.CONTACT_ALREADY_EXISTS");
	}

	@Override
	public Contact getContact(String phoneNo) throws Exception {
		Contact contact = null;
			entity = entityManager.find(ContactEntity.class, phoneNo);
			if (entity == null) {
				throw new NoSuchContactException("DAO.CONTACT_UNAVAILABLE");
			} else {
				contact = new Contact();
				contact.setEmailId(entity.getEmailId());
				contact.setFirstName(entity.getFirstName());
				contact.setLastName(entity.getLastName());
				contact.setStatus(entity.getStatus());
				contact.setPhoneNo(phoneNo);
			}
		return contact;
	}

	// PATCH
	@Override
	public void updateContact(String phoneNo, Contact contact) throws Exception {
			ContactEntity entity = entityManager.find(ContactEntity.class, phoneNo);
			if (entity == null) {
				throw new NoSuchContactException("DAO.CONTACT_UNAVAILABLE");
			} else {
				if (null != contact.getEmailId() && !contact.getEmailId().isEmpty())
					entity.setEmailId(contact.getEmailId());
				if (null != contact.getFirstName() && !contact.getFirstName().isEmpty())
					entity.setFirstName(contact.getFirstName());
				if (null != contact.getLastName() && !contact.getLastName().isEmpty())
					entity.setLastName(contact.getLastName());
				if (null != contact.getStatus() && !contact.getStatus().isEmpty())
					entity.setStatus(contact.getStatus().toUpperCase());
				entityManager.merge(entity);
			}
	}

	// PUT
	@Override
	public void replaceContact(String phoneNo, Contact contact) throws Exception {
			ContactEntity entity = entityManager.find(ContactEntity.class, phoneNo);
			if (entity == null)
				addContact(contact);
			else {
				entity.setEmailId(contact.getEmailId());
				entity.setFirstName(contact.getFirstName());
				entity.setLastName(contact.getLastName());
				entity.setStatus(contact.getStatus().toUpperCase());
				entityManager.merge(entity);
			}
	}

	@Override
	public void deleteContact(String phoneNo) throws Exception {
			ContactEntity entity = entityManager.find(ContactEntity.class, phoneNo);
			if (entity == null) {
				throw new NoSuchContactException("DAO.CONTACT_UNAVAILABLE");
			}
		entityManager.remove(entity);
	}

	@Override
	public List<Contact> getAllContactDetails() throws Exception {
		List<Contact> contacts = new ArrayList<Contact>();
			Query query = entityManager.createQuery("Select c from ContactEntity c");
			List<ContactEntity> list = (List<ContactEntity>) query.getResultList();
			if (list == null || list.size() == 0) {
				throw new NoSuchContactException("No Contacts Found.");
			}
			for (ContactEntity entity : list) {
				Contact contact = new Contact();
				contact.setEmailId(entity.getEmailId());
				contact.setFirstName(entity.getFirstName());
				contact.setLastName(entity.getLastName());
				contact.setPhoneNo(entity.getPhoneNo());
				contact.setStatus(entity.getStatus());
				contacts.add(contact);
			}
		return contacts;
	}
}
