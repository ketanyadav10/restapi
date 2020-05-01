package com.evolent.contactmgmt;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.evolent.contactmgmt.api.ContactAPI;
import com.evolent.contactmgmt.model.Contact;
import com.evolent.contactmgmt.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class testContactAPI {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
    private WebApplicationContext context;
 
	@MockBean
	private ContactService contactService;
	Contact contact;
	List<Contact> contacts;
	@Before
	 public void initializeContact()
	 {
		contact= new Contact();
		contacts= new ArrayList<Contact>();
		 contact.setEmailId("Kyadav073@gmail.com");
		 contact.setFirstName("Ketan");
		 contact.setLastName("Yadav");
		 contact.setPhoneNo("7387431338");
		 contact.setStatus("Active");
		 contacts.add(contact);
	 }
	@Test
	public void testGetAllContactDetails() throws Exception{
		
		Mockito.when(contactService.getAllContactDetails()).thenReturn(contacts);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("http://localhost:3010/evocon/contacts/").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(response);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapper.writeValue(out, contacts);
		byte[] data = out.toByteArray();
		Assert.assertEquals(new String(data),response.getContentAsString());
		
	}
	
}
