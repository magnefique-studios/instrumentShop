package com.shabushabu.javashop.instruments.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;


import javax.sql.DataSource;

@Component
public class DataGenerator {

    @Autowired
    private DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
    	/*ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/instruments-latest.sql"));
        resourceDatabasePopulator.execute(dataSource);
        
        resourceDatabasePopulator = 
        		new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/instruments_chicago.sql"));
	    resourceDatabasePopulator.execute(dataSource);
*/
	 /*   resourceDatabasePopulator = 
	    		new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/instruments-utah.sql"));
	    resourceDatabasePopulator.execute(dataSource);
	    resourceDatabasePopulator = 
	    		new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/instruments-colorado.sql"));
	    resourceDatabasePopulator.execute(dataSource);
	    resourceDatabasePopulator = 
	    		new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/instruments-california.sql"));
	    resourceDatabasePopulator.execute(dataSource);
	    resourceDatabasePopulator = 
	    		new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/instruments-austin.sql"));
	    resourceDatabasePopulator.execute(dataSource);
*/
//        resourceDatabasePopulator = 
//        		new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/stocks-new.sql"));
 //       resourceDatabasePopulator.execute(dataSource);
       
    }
}



