package com.evolent.contactmgmt.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import org.springframework.web.server.ResponseStatusException;

import com.evolent.contactmgmt.exceptions.DuplicateContactException;
import com.evolent.contactmgmt.exceptions.InvalidRequestException;
import com.evolent.contactmgmt.exceptions.NoSuchContactException;
import com.evolent.contactmgmt.model.Contact;
import com.evolent.contactmgmt.service.ContactService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/contacts")
@Api(value = "ContactController, REST APIs that deal with Contact DTO")
public class ContactAPI {

	@Autowired
	private ContactService contactService;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Environment environemnt;

	@ApiOperation(value = "Fetch all the contacts in the database", response = Contact.class, tags = "fetchContacts")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Returns the response"),
			@ApiResponse(code = 404, message = "No contact detail not found") })
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Contact>> getAllContactDetails() throws Exception {
		try {
			List<Contact> contactList = contactService.getAllContactDetails();
			ResponseEntity<List<Contact>> response = new ResponseEntity<List<Contact>>(contactList, HttpStatus.OK);
			return response;
		} catch (NoSuchContactException e) {
			logger.debug("ContactAPI.getAllContactDetails" + e.getMessage());
			throw new NoSuchContactException("DAO.NO_CONTACT_AVAILABLE");
		}
		catch (Exception e) {
			logger.debug("ContactAPI.getAllContactDetails" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@ApiOperation(value = "Fetch a single contact with phone number", response = Contact.class, tags = "fetchContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Returns the response"),
			@ApiResponse(code = 404, message = "Contact with phone no :\"+phoneNo+\" doesnot exist.") })

	@GetMapping(value = "/{phoneNo}", produces = "application/json")
	public ResponseEntity<Contact> getContactDetails(@PathVariable String phoneNo) throws Exception {
		try {
			Contact contact = contactService.getContact(phoneNo);
			ResponseEntity<Contact> response = new ResponseEntity<Contact>(contact, HttpStatus.OK);
			return response;
		}catch (InvalidRequestException e) {
			logger.debug("ContactAPI.getContactDetails" + e.getMessage());
			throw new InvalidRequestException("Validations.PATH_VARIABLE_INVALID");
		}catch (NoSuchContactException e) {
			logger.debug("ContactAPI.getContactDetails" + e.getMessage());
			throw new NoSuchContactException("DAO.CONTACT_UNAVAILABLE");
		}catch (Exception e) {
			logger.debug("ContactAPI.getContactDetails" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@ApiOperation(value = "Submits a contact to the database", response = Contact.class, tags = "createContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Contact added successfully"),
			@ApiResponse(code = 404, message = "Contact with same phone number is already existing.") })
	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> addContact(@Valid @RequestBody Contact contact) throws Exception {
		try {
			contactService.addContact(contact);
			String successMessage = "Contact added successfully";
			ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
			return response;
		}catch (DuplicateContactException e) {
			logger.debug("ContactAPI.addContact" + e.getMessage());
			throw new DuplicateContactException("DAO.CONTACT_ALREADY_EXISTS");
		}catch (Exception e) {
			logger.debug("ContactAPI.addContact" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@ApiOperation(value = "Update a contact partially.", response = Contact.class, tags = "patchContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Contact updated successfully."),
			@ApiResponse(code = 404, message = "Contact with phone no :\"+phoneNo+\" doesnot exist.") })

	@PatchMapping(value = "/{phoneNo}", consumes = "application/json")
	public ResponseEntity<String> updateContact(@PathVariable String phoneNo, @RequestBody Contact contact)
			throws Exception {
		try {
			contactService.updateContact(phoneNo, contact);
			String successMessage = "Contact updated successfully.";
			ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
			return response;
		}catch (InvalidRequestException e) {
			logger.debug("ContactAPI.updateContact" + e.getMessage());
			throw new InvalidRequestException("Validations.PATH_VARIABLE_INVALID");
		}catch (NoSuchContactException e) {
			logger.debug("ContactAPI.updateContact" + e.getMessage());
			throw new NoSuchContactException("DAO.CONTACT_UNAVAILABLE");
		}catch (Exception e) {
			logger.debug("ContactAPI.updateContact" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@ApiOperation(value = "Updates the contact with specified phone no  by replacing all values", response = Contact.class, tags = "updateContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Contact updated successfully."),
			@ApiResponse(code = 404, message = "Contact with phone no :\"+phoneNo+\" doesnot exist.") })

	@PutMapping(value = "/{phoneNo}", consumes = "application/json")
	public ResponseEntity<String> createAndUpdateContact(@PathVariable String phoneNo,
			@RequestBody @Valid Contact contact) throws Exception {
		try {
			contactService.createAndUpdateContact(phoneNo, contact);
			String successMessage = "Contact updated successfully.";
			ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
			return response;
		}catch (InvalidRequestException e) {
			logger.debug("ContactAPI.createAndUpdateContact" + e.getMessage());
			throw new InvalidRequestException("Validations.PATH_VARIABLE_INVALID");
		}catch (NoSuchContactException e) {
			logger.debug("ContactAPI.createAndUpdateContact" + e.getMessage());
			throw new NoSuchContactException("DAO.CONTACT_UNAVAILABLE");
		}catch (Exception e) {
			logger.debug("ContactAPI.createAndUpdateContact" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@ApiOperation(value = "Deletes the contact with specified phone no.", response = Contact.class, tags = "deleteContact")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Contact deleted successfully."),
			@ApiResponse(code = 404, message = "Contact with phone no :\"+phoneNo+\" doesnot exist.") })

	@DeleteMapping(value = "/{phoneNo}", produces = "text/html")
	public ResponseEntity<String> deleteContact(@PathVariable String phoneNo) throws Exception {
		try {
			contactService.deleteContact(phoneNo);
			String successMessage = "Contact deleted succssfully";
			ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
			return response;
		}catch (InvalidRequestException e) {
			logger.debug("ContactAPI.deleteContact" + e.getMessage());
			throw new InvalidRequestException("Validations.PATH_VARIABLE_INVALID");
		}catch (NoSuchContactException e) {
			logger.debug("ContactAPI.deleteContact" + e.getMessage());
			throw new NoSuchContactException("DAO.CONTACT_UNAVAILABLE");
		}catch (Exception e) {
			logger.debug("ContactAPI.deleteContact" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
