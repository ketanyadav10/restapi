package com.evolent.contactmgmt.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
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
import com.evolent.contactmgmt.model.ErrorMessage;
import com.evolent.contactmgmt.service.ContactService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/contacts")
@Api(value = "ContactController, REST APIs that deal with Contact DTO")
public class ContactAPI {
	
	@Autowired
	private ContactService contactService;
	@ApiOperation(value = "Fetch all the contacts in the database", response = Contact.class, tags="fetchContacts")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Returns the response"),
							 @ApiResponse(code = 404, message = "No Contacts available.") })
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Contact>> getAllContactDetails() throws Exception {
		List<Contact> contactList = contactService.getAllContactDetails();
		ResponseEntity<List<Contact>> response = new ResponseEntity<List<Contact>>(contactList, HttpStatus.OK);
		return response;
	}
	
	@ApiOperation(value = "Fetch a single contact with phone number", response = Contact.class, tags="fetchContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Returns the response"),
							 @ApiResponse(code = 404, message = "Contact with phone no :\"+phoneNo+\" doesnot exist.") })
	
	@GetMapping(value = "/{phoneNo}",produces = "application/json")
	public ResponseEntity<Contact> getContactDetails(@PathVariable String phoneNo)  throws Exception  {
		
		Contact contact = contactService.getContact(phoneNo);
		ResponseEntity<Contact> response = new ResponseEntity<Contact>(contact, HttpStatus.OK);
		return response;
	}
	@ApiOperation(value = "Submits a contact to the database", response = Contact.class, tags="createContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Contact added successfully"),
							 @ApiResponse(code = 404, message = "Contact with same phone number is already existing.") })
    @PostMapping(consumes = "application/json")
	public ResponseEntity<String> addContact(@Valid @RequestBody Contact contact, Errors errors) throws Exception {
		String allError = "";
		if (errors.hasErrors()) {
			// collecting the validation errors of all fields together in a String delimited
			// by commas
			allError = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(","));
			ErrorMessage error = new ErrorMessage();
			error.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
			error.setMessage(allError);
			ResponseEntity<String> response = new ResponseEntity<String>(allError, HttpStatus.CREATED);
			return response;
		} else {
			contactService.addContact(contact);
			String successMessage = "Contact added successfully";
			ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
			return response;
		}
	}
	@ApiOperation(value = "Update a contact partially.", response = Contact.class, tags="patchContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Contact updated successfully."),
							 @ApiResponse(code = 404, message = "Contact with phone no :\"+phoneNo+\" doesnot exist.") })
    
    @PatchMapping(value = "/{phoneNo}",consumes = "application/json")
    public ResponseEntity<String> updateContact(@PathVariable String phoneNo, @RequestBody Contact contact)  throws Exception {
		contactService.updateContact(phoneNo,contact);
		String successMessage = "Contact updated successfully.";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
		return response;
	}
	@ApiOperation(value = "Updates the contact with specified phone no  by replacing all values", response = Contact.class, tags="updateContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Contact updated successfully."),
							 @ApiResponse(code = 404, message = "Contact with phone no :\"+phoneNo+\" doesnot exist.") })
   
    @PutMapping(value = "/{phoneNo}",consumes = "application/json")
	public ResponseEntity<String> createAndUpdateContact(@PathVariable String phoneNo, @RequestBody Contact contact)  throws Exception {
		contactService.createAndUpdateContact(phoneNo, contact);
		String successMessage = "Contact updated successfully.";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
		return response;
	}
	@ApiOperation(value = "Deletes the contact with specified phone no.", response = Contact.class, tags="deleteContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Contact deleted successfully."),
							 @ApiResponse(code = 404, message = "Contact with phone no :\"+phoneNo+\" doesnot exist.") })
   
    
	@DeleteMapping(value = "/{phoneNo}", produces = "text/html")
	public ResponseEntity<String> deleteContact(@PathVariable String phoneNo) throws Exception  {
		contactService.deleteContact(phoneNo);
		String successMessage = "Contact deleted succssfully";
		ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
		return response;
	}

}
