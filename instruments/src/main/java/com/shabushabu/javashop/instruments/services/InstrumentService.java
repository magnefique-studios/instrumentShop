package com.shabushabu.javashop.instruments.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shabushabu.javashop.instruments.model.Instrument;
import com.shabushabu.javashop.instruments.repositories.InstrumentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Service
public class InstrumentService {

	 private static Logger s_logger = LogManager.getLogger(InstrumentService.class);
	 
    private InstrumentRepository instrumentRepo;

    @Autowired
    public InstrumentService(InstrumentRepository instrumentRepo) {
        this.instrumentRepo = instrumentRepo;
    }

  /*  public List<Instrument> getInstruments() {
        return StreamSupport.stream(instrumentRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
*/
    @SuppressWarnings("unchecked")
	public List<Instrument> getInstruments(String location) {
        
    	s_logger.info("Entering InstrumentService::getInstruments: Location= " + location);
    	
    	if (location.equalsIgnoreCase("Chicago")) {
    	
    		Object obj = instrumentRepo.findInstruments() ;
    		
    		if ( null == obj || !( obj instanceof List<?>) ) {
    			return null;
    		} else {
    			return  StreamSupport.stream(instrumentRepo.findAll().spliterator(), false)
    					.collect(Collectors.toList());
    		}	
    	}
    	else {
    		return  StreamSupport.stream(instrumentRepo.findAll().spliterator(), false)
                    .collect(Collectors.toList());
    		
    	}
    }

}
