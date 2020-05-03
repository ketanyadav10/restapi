package com.evolent.contactmgmt;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.evolent.contactmgmt.model.Contact;
import com.evolent.contactmgmt.model.ErrorMessage;
import com.evolent.contactmgmt.model.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Profile("test")
public class testContactAPI {

	@Autowired
	private TestRestTemplate template;
	@LocalServerPort
    int randomServerPort;
	Contact contact;
	Contact newContact;
	List<Contact> contacts;
	@Autowired
	Environment env;
	String baseUrl;
	String phoneNo;
	@Before
	public void setUp() throws URISyntaxException  {
		template = new TestRestTemplate(env.getProperty("spring.security.user.name"), env.getProperty("spring.security.user.password"));
		template.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		baseUrl = "http://localhost:"+randomServerPort+"/v1/contacts";
		phoneNo="/7387431338";
		template.postForEntity(new URI(baseUrl),contact, String.class);
	}
	@Before
	public void innitializeContact() {
		contact = new Contact();
		contact.setEmailId("kyadav073@gmail.com");
		contact.setFirstName("Ketan");
		contact.setLastName("Yadav");
		contact.setPhoneNo("7387431338");
		contact.setStatus("Active".toUpperCase());
		contacts= new ArrayList<Contact>();
		contacts.add(contact);
		
		newContact = new Contact();
		newContact.setEmailId("ts@gmail.com");
		newContact.setFirstName("Toran");
		newContact.setLastName("Sahu");
		newContact.setPhoneNo("1111111111");
		newContact.setStatus("Active".toUpperCase());
	}
	
	@Test
	public void whenUserWithWrongCredentials_thenUnauthorized() throws Exception {
		template = new TestRestTemplate("user", "wrongpassword");
		ResponseEntity<String> response = template.getForEntity(new URI(baseUrl), String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	@Test
	public void testGetAllContactDetails_pass() throws Exception {
		ResponseEntity<String> result = template.getForEntity(new URI(baseUrl), String.class); //actual database call
		assertEquals(HttpStatus.OK, result.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapper.writeValue(out, contacts);
		byte[] data = out.toByteArray();
		assertTrue(result.getBody().contains(new String(data)));
		//assertEquals(new String(data), result.getBody());
	}
	@Test
	public void testGetAllContactDetails_fail() throws Exception {
		template.delete(new URI(baseUrl+phoneNo));
		ResponseEntity<String> result = template.getForEntity(new URI(baseUrl), String.class); //actual database call
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());  // No data exists at this point
		//Adding previously existing detail
		template.postForEntity(new URI(baseUrl),contact, ResponseMessage.class);	
	}
	@Test
	public void testResponseforGET_pass() throws Exception {
		ResponseEntity<String> result = template.getForEntity(new URI(baseUrl+phoneNo), String.class); //actual database call
		assertEquals(HttpStatus.OK, result.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapper.writeValue(out, contact);
		byte[] data = out.toByteArray(); //serialize object to JSON
		Assert.assertEquals(new String(data),result.getBody());
	}
	@Test
	public void testResponseforGET_fail() throws Exception {
		template.delete(new URI(baseUrl+phoneNo));
		ResponseEntity<ErrorMessage> result = template.getForEntity(new URI(baseUrl+phoneNo), ErrorMessage.class); //actual database call
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals(env.getProperty("DAO.CONTACT_UNAVAILABLE"), result.getBody().getMessage());
		template.postForEntity(new URI(baseUrl),contact, ResponseMessage.class);
	}
	@Test
	public void testResponseforPOST_pass() throws Exception {
		ResponseEntity<ResponseMessage> result = template.postForEntity(new URI(baseUrl),newContact, ResponseMessage.class); //actual database call
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		ResponseMessage msg= new ResponseMessage(HttpStatus.CREATED,env.getProperty("ContactAPI.ADD_SUCCESS"));
		assertEquals(msg.getMessage(), result.getBody().getMessage());
		assertEquals(msg.getResponseCode(), result.getBody().getResponseCode());
		template.delete(new URI(baseUrl+"/"+newContact.getPhoneNo())); // Removing newly added entry
		}
	@Test
	public void testResponseforPOST_fail() throws Exception {
		ResponseEntity<String> result = template.postForEntity(new URI(baseUrl),contact, String.class);
		assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
		assertTrue(result.getBody().contains(env.getProperty("DAO.CONTACT_ALREADY_EXISTS")));
		}
	@Test
	public void testResponseforPUT_pass() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Contact> entity= new HttpEntity<Contact>(contact,headers);
		ResponseEntity<ResponseMessage> result = template.exchange(new URI(baseUrl+phoneNo),HttpMethod.PUT,entity, ResponseMessage.class); 
		assertEquals(env.getProperty("ContactAPI.UPDATE_SUCCESS"), result.getBody().getMessage());
		assertEquals(HttpStatus.OK, result.getBody().getResponseCode());
	}
	@Test
	public void testResponseforPUT_fail() throws Exception {
		contact.setEmailId("ketan");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Contact> entity= new HttpEntity<Contact>(contact,headers);
		ResponseEntity<ResponseMessage> result = template.exchange(new URI(baseUrl+phoneNo),HttpMethod.PUT,entity, ResponseMessage.class); 
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	@Test
	public void testResponseforPATCH_pass() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Contact> entity= new HttpEntity<Contact>(contact,headers);
		ResponseEntity<ResponseMessage> result = template.exchange(new URI(baseUrl+phoneNo),HttpMethod.PATCH,entity, ResponseMessage.class); 
		assertEquals(env.getProperty("ContactAPI.UPDATE_SUCCESS"), result.getBody().getMessage());
		assertEquals(HttpStatus.OK, result.getBody().getResponseCode());
	}
	@Test
	public void testResponseforDELETE_pass() throws Exception {
		//adding before delete 
		template.postForEntity(new URI(baseUrl),newContact, ResponseMessage.class); 
		template.delete(new URI(baseUrl+"/"+newContact.getPhoneNo()));
		ResponseEntity<String> result = template.getForEntity(new URI(baseUrl+"/"+newContact.getPhoneNo()), String.class);
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}
	@Test
	public void testResponseforDELETE_fail() throws Exception {
		template.delete(new URI(baseUrl+"/"+newContact.getPhoneNo()));
		ResponseEntity<String> result = template.getForEntity(new URI(baseUrl+"/000"), String.class);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
}
