package com.evolent.contactmgmt.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evolent.contactmgmt.model.Contact;
import com.evolent.contactmgmt.service.ContactService;

@RestController
@RequestMapping(value="/contactmgmt")
public class ContactAPI {
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping(value = "/contacts")
	public ResponseEntity<List<Contact>> getAllContactDetails() throws Exception {
		List<Contact> contactList = contactService.getAllContactDetails();
		ResponseEntity<List<Contact>> response = new ResponseEntity<List<Contact>>(contactList, HttpStatus.OK);
		return response;
	}
	
	
	@GetMapping(value = "/contacts/{contactId}")
	public ResponseEntity<Contact> getContactDetails(@PathVariable Integer contactId)  throws Exception  {
		
		Contact contact = contactService.getContact(contactId);
		ResponseEntity<Contact> response = new ResponseEntity<Contact>(contact, HttpStatus.OK);
		return response;
	}
	
	
    @PostMapping(value = "/contacts")
	public ResponseEntity<String> addContact(@RequestBody Contact contact) throws Exception  {
		contactService.addContact(contact);
		String successMessage = "Contact added successfully";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
		return response;
	}
    
    
    @PutMapping(value = "/contacts/{contactId}")
	public ResponseEntity<String> updateContact(@PathVariable Integer contactId, @RequestBody Contact contact)  throws Exception {
		contactService.updateContact(contactId,contact.getEmailId());
		String successMessage = "Contact updated successfully.";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
		return response;
	}
    
    
	@DeleteMapping(value = "/contacts/{contactId}")
	public ResponseEntity<String> deleteContact(@PathVariable Integer contactId) throws Exception  {
		contactService.deleteContact(contactId);
		String successMessage = "Contact deleted succssfully";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
		return response;
	}

}
