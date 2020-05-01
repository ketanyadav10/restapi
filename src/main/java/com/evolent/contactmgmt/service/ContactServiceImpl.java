package com.evolent.contactmgmt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolent.contactmgmt.dao.ContactDAO;
import com.evolent.contactmgmt.model.Contact;
import com.evolent.contactmgmt.validations.OtherValidations;

@Service(value = "contactService")
@Transactional
public class ContactServiceImpl implements ContactService {
	@Autowired
	ContactDAO contactDAO;
	@Autowired
	OtherValidations validations;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void addContact(Contact contact) throws Exception {
		contactDAO.addContact(contact);
	}

	@Override
	public Contact getContact(String phoneNo) throws Exception {
		Contact contact = null;
		if(validations.validatePathVariable(phoneNo))
			contact = contactDAO.getContact(phoneNo);
		return contact;
	}

	@Override
	public void deleteContact(String phoneNo) throws Exception {
		if(validations.validatePathVariable(phoneNo))
			contactDAO.deleteContact(phoneNo);
	}

	@Override
	public List<Contact> getAllContactDetails() throws Exception {
		List<Contact> contactList = null;
		contactList = contactDAO.getAllContactDetails();
		return contactList;
	}

	@Override
	public void updateContact(String phoneNo, Contact contact) throws Exception {
		if(validations.validatePathVariable(phoneNo))
			contactDAO.updateContact(phoneNo, contact);
	}

	@Override
	public void createAndUpdateContact(String phoneNo, Contact contact) throws Exception {
		if(validations.validatePathVariable(phoneNo))
			contactDAO.replaceContact(phoneNo, contact);
	}
}
