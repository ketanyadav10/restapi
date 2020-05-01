package com.evolent.contactmgmt.validations;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.evolent.contactmgmt.exceptions.InvalidRequestException;

@Component
public class OtherValidations {
	public boolean validatePathVariable(String pathVariable) throws Exception{
		Pattern pattern = Pattern.compile("[0-9]{10}");
	      Matcher matcher = pattern.matcher(pathVariable);
	      if (!matcher.matches())
	    	  throw new InvalidRequestException("PATH_VARIABLE_INVALID");
	      return true;
	}
}
