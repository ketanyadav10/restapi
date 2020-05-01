package com.evolent.contactmgmt;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.evolent.contactmgmt.exceptions.InvalidRequestException;
import com.evolent.contactmgmt.validations.OtherValidations;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testValidations {
	@Autowired
	OtherValidations val;

	@Test
	public void testValidatePathVariable_happyPath() throws Exception {
		assertTrue(val.validatePathVariable("7387431338"));
	}

	@Test(expected = InvalidRequestException.class)
	public void testValidatePathVariable_exception() throws Exception {
		val.validatePathVariable("73878");
	}
}
