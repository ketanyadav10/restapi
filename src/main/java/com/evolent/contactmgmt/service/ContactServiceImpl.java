package com.evolent.contactmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolent.contactmgmt.dao.ContactDAO;
import com.evolent.contactmgmt.exceptions.DuplicateContactException;
import com.evolent.contactmgmt.exceptions.NoSuchContactException;
import com.evolent.contactmgmt.model.Contact;

@Service(value="contactService")
@Transactional
public class ContactServiceImpl implements ContactService {
	@Autowired
	ContactDAO contactDAO;
	@Override
	public void addContact(Contact contact) throws Exception {
		if(contactDAO.getContact(contact.getPhoneNo())!=null) {
			throw new DuplicateContactException("Contact with same phone number is already existing.");
		}
		contactDAO.addContact(contact);
	}

	@Override
	public Contact getContact(String phoneNo) throws Exception {
		Contact contact = contactDAO.getContact(phoneNo);
		if (contact == null) {
			throw new NoSuchContactException("Contact with phone no :"+phoneNo+" doesnot exist.");
		}
		return contact;
	}

	

	@Override
	public void deleteContact(String phoneNo) throws Exception {
		Contact contact = contactDAO.getContact(phoneNo);
		if (contact == null) {
			throw new NoSuchContactException("Contact with phone no :"+phoneNo+" doesnot exist.");
		}
		contactDAO.deleteContact(phoneNo);
	}

	@Override
	public List<Contact> getAllContactDetails() throws Exception {
		List<Contact> contactList = contactDAO.getAllContactDetails();
		if (contactList == null || contactList.size()==0) {
			throw new NoSuchContactException("No Contacts available.");
		}
		return contactList;
	}

	@Override
	public void updateContact(String phoneNo, Contact contact) throws Exception {

		Contact existing = contactDAO.getContact(phoneNo);
		if (existing == null) {
			throw new NoSuchContactException("Contact with phone no :"+phoneNo+" doesnot exist.");
		}		
		contactDAO.updateContact(phoneNo, contact);
	
	}

	@Override
	public void createAndUpdateContact(String phoneNo, Contact contact) throws Exception {
		Contact existing = contactDAO.getContact(phoneNo);
		if (existing == null) 
			addContact(contact);
		else
			contactDAO.replaceContact(phoneNo, contact);
	}
}
