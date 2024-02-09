package com.shabushabu.javashop.instruments.model;




import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shabushabu.javashop.instruments.exceptions.InvalidLocaleException;

import io.opentelemetry.instrumentation.annotations.WithSpan;


public class FilteredInstrument {
	
   private static final boolean  s_Localedisabled = true; 
	
   private final Logger s_logger = LoggerFactory.getLogger(FilteredInstrument.class);
	
@WithSpan
public boolean filterInstruments( String locale) throws InvalidLocaleException {
	
	boolean result = true;
	
	if (locale.equalsIgnoreCase("Oregon")) {
	
		if (s_Localedisabled) {
				
				// See src/main/resources/application.properties ..
				s_logger.error("Trying to filter to disabled Region: " + locale);
				
				throw new InvalidLocaleException("Trying to filter to disabled Region: Oregon");
		} else {	
				
				// See src/main/resources/application.properties ..
				s_logger.info("Calling Oregon Specific data.....");
				
				result = true;	 
		}
	}
	
	return result;
}

}