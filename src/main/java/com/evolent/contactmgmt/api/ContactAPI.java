package com.evolent.contactmgmt.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evolent.contactmgmt.model.Contact;
import com.evolent.contactmgmt.service.ContactService;

@RestController
@RequestMapping(value="/contacts")
public class ContactAPI {
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Contact>> getAllContactDetails() throws Exception {
		List<Contact> contactList = contactService.getAllContactDetails();
		ResponseEntity<List<Contact>> response = new ResponseEntity<List<Contact>>(contactList, HttpStatus.OK);
		return response;
	}
	
	
	@GetMapping(value = "/{phoneNo}",produces = "application/json")
	public ResponseEntity<Contact> getContactDetails(@PathVariable String phoneNo)  throws Exception  {
		
		Contact contact = contactService.getContact(phoneNo);
		ResponseEntity<Contact> response = new ResponseEntity<Contact>(contact, HttpStatus.OK);
		return response;
	}
	
	
    @PostMapping(consumes = "application/json")
	public ResponseEntity<String> addContact(@RequestBody Contact contact) throws Exception  {
		contactService.addContact(contact);
		String successMessage = "Contact added successfully";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
		return response;
	}
    @PatchMapping(value = "/{phoneNo}",consumes = "application/json")
    public ResponseEntity<String> updateContact(@PathVariable String phoneNo, @RequestBody Contact contact)  throws Exception {
		contactService.updateContact(phoneNo,contact);
		String successMessage = "Contact updated successfully.";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
		return response;
	}
    @PutMapping(value = "/{phoneNo}",consumes = "application/json")
	public ResponseEntity<String> createAndUpdateContact(@PathVariable String phoneNo, @RequestBody Contact contact)  throws Exception {
		contactService.createAndUpdateContact(phoneNo, contact);
		String successMessage = "Contact updated successfully.";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
		return response;
	}
    
    
	@DeleteMapping(value = "/{phoneNo}", produces = "text/html")
	public ResponseEntity<String> deleteContact(@PathVariable String phoneNo) throws Exception  {
		contactService.deleteContact(phoneNo);
		String successMessage = "Contact deleted succssfully";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
		return response;
	}

}
