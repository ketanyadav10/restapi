package com.evolent.contactmgmt.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.evolent.contactmgmt.entity.ContactEntity;
import com.evolent.contactmgmt.model.Contact;

@Repository(value="contactDAO")
public class ContactDAOImpl implements ContactDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Integer addContact(Contact contact) throws Exception {
		Integer contactId = null;
		ContactEntity entity= new ContactEntity();
		entity.setContactId(contact.getContactId());
		entity.setEmailId(contact.getEmailId());
		entity.setFirstName(contact.getFirstName());
		entity.setLastName(contact.getLastName());
		entity.setPhoneNo(contact.getPhoneNo());
		entity.setStatus(contact.getStatus());

		entityManager.persist(entity);
		contactId= entity.getContactId();
		return contactId;
	}

	@Override
	public Contact getContact(Integer contactId) throws Exception {
		Contact contact=null;
		ContactEntity entity=entityManager.find(ContactEntity.class, contactId);
		if(entity!=null) {
			contact= new Contact();
			contact.setContactId(entity.getContactId());
			contact.setEmailId(entity.getEmailId());
			contact.setFirstName(entity.getFirstName());
			contact.setLastName(entity.getLastName());
			contact.setPhoneNo(entity.getPhoneNo());
			contact.setStatus(entity.getStatus());
		}
		return contact;
	}

	@Override
	public void updateContact(Integer contactId, String emailId) throws Exception {
		ContactEntity entity=entityManager.find(ContactEntity.class, contactId);
		if(entity!=null) {
			entity.setEmailId(emailId);
			entityManager.merge(entity);
		}
	}

	@Override
	public void deleteContact(Integer contactId) throws Exception {
		ContactEntity entity=entityManager.find(ContactEntity.class, contactId);
		entityManager.remove(entity);
	}

	@Override
	public List<Contact> getAllContactDetails() throws Exception {
		 Query query=entityManager.createQuery("Select c from ContactEntity c");
		List<ContactEntity> list=(List<ContactEntity>)query.getResultList();
		List<Contact> contacts = new ArrayList<Contact>() ;
		
		for(ContactEntity entity: list) {
			Contact contact= new Contact();
			contact.setContactId(entity.getContactId());
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
