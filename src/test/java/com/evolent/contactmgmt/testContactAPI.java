package com.evolent.contactmgmt;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.evolent.contactmgmt.model.Contact;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class testContactAPI {

	@Autowired
	private TestRestTemplate template;
	URL base;
	Contact contact;
	List<Contact> contacts;

	@Before
	public void setUp() throws MalformedURLException {
		template = new TestRestTemplate("user", "password");
		base = new URL("http://localhost:3010/v1/contacts");
	}

	@Test
	public void givenAuthRequestShouldSucceedWith200() throws Exception {
		ResponseEntity<String> result = template.withBasicAuth("admin", "contact1")
				.getForEntity("http://localhost:3010/v1/contacts", String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(result.getBody().contains("phoneNo"));
		assertTrue(result.getBody().contains("firstName"));
	}

	@Test
	public void whenUserWithWrongCredentials_thenUnauthorized() throws Exception {
		template = new TestRestTemplate("user", "wrongpassword");
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void testResponseforGET() throws Exception {
		ResponseEntity<String> result = template.withBasicAuth("admin", "contact1")
				.getForEntity("http://localhost:3010/v1/contacts", String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(result.getBody().contains("phoneNo"));
		assertTrue(result.getBody().contains("firstName"));
	}

	@Test
	public void testResponseforPOST() throws Exception {
		contact = new Contact();
		contact.setEmailId("Kyadav073@gmail.com");
		contact.setFirstName("Ketan");
		contact.setLastName("Yadav");
		contact.setPhoneNo("7387431338");
		contact.setStatus("Active");
		template = new TestRestTemplate("admin", "contact1");
		template.delete(base.toString() + "/7387431338");
		ResponseEntity<String> response = template.postForEntity(base.toString(), contact, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().contains("Contact added successfully"));
	}

	@Test
	public void testResponseforPUT() throws Exception {
		contact = new Contact();
		contact.setEmailId("Kyadav073@gmail.com");
		contact.setFirstName("Ketan");
		contact.setLastName("Yadav");
		contact.setPhoneNo("7387431338");
		contact.setStatus("Inactive");
		template = new TestRestTemplate("admin", "contact1");
		template.put(base.toString() + "/7387431338", contact);
	}

	@Test
	public void testResponseforPATCH() throws Exception {
		contact = new Contact();
		contact.setPhoneNo("7387431338");
		contact.setStatus("active");
		template = new TestRestTemplate("admin", "contact1");
		// String responseEntity = template.patchForObject(base.toString(), contact,
		// String.class);
	}

	@Test
	public void testResponseforDELETE() throws Exception {
		template = new TestRestTemplate("admin", "contact1");
		template.postForEntity(base.toString(), contact, String.class);
		template.delete(base.toString() + "/7387431338");
		ResponseEntity<String> responsenew = template.postForEntity(base.toString(), contact, String.class);
		assertEquals(HttpStatus.OK, responsenew.getStatusCode());
	}
}
