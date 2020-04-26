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
	public String addContact(Contact contact) throws Exception {
		
		ContactEntity entity= new ContactEntity();
		entity.setEmailId(contact.getEmailId());
		entity.setFirstName(contact.getFirstName());
		entity.setLastName(contact.getLastName());
		entity.setPhoneNo(contact.getPhoneNo());
		entity.setStatus(contact.getStatus());
		entityManager.persist(entity);
		return entity.getPhoneNo();
	}

	@Override
	public Contact getContact(String phoneNo ) throws Exception {
		Contact contact=null;
		ContactEntity entity=entityManager.find(ContactEntity.class, phoneNo);
		if(entity!=null) {
			contact= new Contact();
			contact.setEmailId(entity.getEmailId());
			contact.setFirstName(entity.getFirstName());
			contact.setLastName(entity.getLastName());
			contact.setStatus(entity.getStatus());
			contact.setPhoneNo(phoneNo);
		}
		return contact;
	}
	//PATCH
	@Override
	public void updateContact(String phoneNo, Contact contact) throws Exception {
		ContactEntity entity=entityManager.find(ContactEntity.class, phoneNo);
		if(null!=contact.getEmailId() && !contact.getEmailId().isEmpty())
			entity.setEmailId(contact.getEmailId());
		if(null!=contact.getFirstName() && !contact.getFirstName().isEmpty())
			entity.setFirstName(contact.getFirstName());
		if(null!=contact.getLastName() && !contact.getLastName().isEmpty())
			entity.setLastName(contact.getLastName());
		if(null!=contact.getStatus() && !contact.getStatus().isEmpty())
			entity.setStatus(contact.getStatus());
		entityManager.merge(entity);
	}
	
	//PUT
	@Override
	public void replaceContact(String phoneNo, Contact contact) throws Exception {
		ContactEntity entity=entityManager.find(ContactEntity.class, phoneNo);
		entity.setEmailId(contact.getEmailId());
		entity.setFirstName(contact.getFirstName());
		entity.setLastName(contact.getLastName());
		entity.setStatus(contact.getStatus());
		entityManager.merge(entity);
	}

	@Override
	public void deleteContact(String phoneNo) throws Exception {
		ContactEntity entity=entityManager.find(ContactEntity.class, phoneNo);
		entityManager.remove(entity);
	}

	@Override
	public List<Contact> getAllContactDetails() throws Exception {
		Query query=entityManager.createQuery("Select c from ContactEntity c");
		List<ContactEntity> list=(List<ContactEntity>)query.getResultList();
		List<Contact> contacts = new ArrayList<Contact>() ;
		
		for(ContactEntity entity: list) {
			Contact contact= new Contact();
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
