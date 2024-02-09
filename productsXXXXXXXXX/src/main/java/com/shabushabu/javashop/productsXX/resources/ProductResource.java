package com.shabushabu.javashop.products.resources;

import java.util.Optional;
import java.util.Random;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.shabushabu.javashop.products.model.Product;
import com.shabushabu.javashop.products.services.ProductService;



@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
	

    private static Logger s_logger = LogManager.getLogger(ProductResource.class);
    

    
    private ProductService productService;

    @Inject
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GET
    public Response getAllProducts(@DefaultValue("California") @QueryParam("location") String location) {
    	s_logger.info("Enteriing ProductResource::getAllProducts() location = " + location);
    	// STEP X: All we know right now is somewhere in this function, latency was introduced.
  
    	myCoolFunction1(location);
    	myCoolFunction2(location);
    	myCoolFunction10(location);
    	myCoolFunction13(location);
    	myCoolFunction5(location);
    	myCoolFunction6(location);
    	
    	myCoolFunction7(location);
    	myCoolFunction8(location);
    	myCoolFunction9(location);
    	
    	myCoolFunction10(location);
    	myCoolFunction11(location);
    	myCoolFunction12(location);
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction23("The Location", location, 0);
    	myCoolFunction14(location);
    	///myCoolFunction15(location);
    	myCoolFunction12(location);
    	myCoolFunction24("The Location", location, 2);
    	myCoolFunction12(location);
    	myCoolFunction13(location);
    	
    	myCoolFunction14(location);
    	myCoolFunction11(location);
    	myCoolFunction13(location);
    	
    	myCoolFunction23("The Location", location, 0);
    	myCoolFunction24("The Location", location, 0);
    	myCoolFunction25("The Location", location, 0);
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction11(location);
    	myCoolFunction();
    	myCoolFunction();
    	
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction(location);
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK

    	myCoolFunction();
    	//something in HAYSTACK
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction(location);
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK
    	//something in HAYSTACK

    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
    	
    	myCoolFunction();
    	myCoolFunction();
    	myCoolFunction();
	   
	    myCoolFunction();
    	myCoolFunction();
    	
    	myCoolFunction();	  
			
        return Response.status(200)
                .entity(productService.getAllProducts())
                .build();
    }
    
    private void myCoolFunction(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        
        lookupLocation1(location);
        
        } catch (Exception e){
      	  
        }
      }
  
    
    private void myCoolFunction1(String location) {
      // Generate a FAST sleep of 0 time !
      int sleepy = lookupLocation1(location);
      try{
      Thread.sleep(sleepy);
      
      } catch (Exception e){
    	  
      }
    }
    private int lookupLocation1(String location) {
    	 int sleepy =4;
    	 
    	 return sleepy;
		
	}
    
    private int lookupLocation19(String location) {
    	
    	int sleepy = lookupLocation1(location);
    	// Generate a FAST sleep of 0 time !
         
    	try{
        	 Thread.sleep(sleepy);
        	 
        	 
         } catch (Exception e){
       	  
         }
		return sleepy;
	}

	private void myCoolFunction2(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 3;
        try{
        	
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
	
    private int lookupLocation3(String location) {
    	 // Generate a FAST sleep of 0 time !
        int sleepy = 2;
      
        try{
        	Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return sleepy;
		
	}
    private int lookupLocation4(String location) {
    	 // Generate a FAST sleep of 0 time !
        int sleepy = 2;
        if (location.equalsIgnoreCase("California")) {
        	sleepy=4;
        }
        try{	
        	Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return sleepy;
		
	}
    private int myCoolFunction5(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 2;
        lookupLocation3(location);
        try{
        	Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return sleepy;
      }
    private void myCoolFunction6(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 3;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction7(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 4;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction8(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 8;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction9(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        	
        	Thread.sleep(sleepy);
        
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction10(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction11(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = locationLookup11(location);
        try{
        	
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction12(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction13(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction14(String location) {
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    
    private void myCoolFunction23(String loc, String location, int index){
    	s_logger.info("Location Index is ... " + index);
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        	s_logger.info("Location Index is ... " + index);
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction24(String loc, String location, int index){
    	s_logger.info("Location Index is ... " + index);
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        	s_logger.info("Location Index is ... " + index);
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction25(String loc, String location, int index){
		s_logger.info("Location Index is ... ");

		s_logger.info("Location Index is ... " + index);
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        	s_logger.info("Location Index is ... " + index);
        	Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    
    private void myCoolFunction() {
    	s_logger.info("Location Index is ... ");
		int sleepy1 =3;
    	  Random random = new Random();

        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction33() {
        // Generate a FAST sleep of 0 time !
    	s_logger.info("Location Index is ... ");
		int sleepy1 =3;
    	  Random random = new Random();

        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction34(String location) {
    	s_logger.info("Location Index is ... ");
		int sleepy1 =3;
    	  Random random = new Random();
    	 
        // Generate a FAST sleep of 0 time !
        int sleepy = locationLookup2(location);
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
    
    private int locationLookup2(String location) {
    	System.out.println("Location Index is ... ");
    	int sleepy1 =3;
    	 // Generate a FAST sleep of 0 time !
        int sleepy = 1;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return 0;
	}
    
    private int locationLookup21(String location) {
    	s_logger.info("Location Index is ... ");
    	int sleepy1 =3;
    	 // Generate a FAST sleep of 0 time !
        int sleepy = 1;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return 0;
	}
    private int locationLookup22(String location) {
    	s_logger.info("Location Index is ... ");
    	int sleepy1 =3;
    	 // Generate a FAST sleep of 0 time !
        int sleepy = 1;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return 0;
	}
    private int locationLookup23(String location) {
    	s_logger.info("Location Index is ... ");
		int sleepy1 =3;
    	 // Generate a FAST sleep of 0 time !
        int sleepy = 1;
        try{
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return 0;
	}

	private int locationLookup11(String location) { 
		s_logger.info("Location Index is ... ");
		int sleepy = 1;
    	if (location.equalsIgnoreCase("Colorado")) {
      	  // Generate a FAST sleep of 0 time !
      	  Random random = new Random();
      	  try{
      		 myCoolFunction234234234(
      		 getMyInt(location));
      	  
      	  } catch (Exception e){
      	  
      	  }
        }
    	return sleepy;
      }
	
	 private int locationLookup15(String location) {
		 s_logger.info("Location Index is ... ");
		
    	 // Generate a FAST sleep of 0 time !
        int sleepy = 1;
        try{
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return 0;
	}
	 
	 
	 private void myCoolFunction234234234(int myInt) {
	    	// Generate a FAST sleep of 0 time !
	    	Random sleepy = new Random();
	    	try{
	       if (999==myInt) 
	          Thread.sleep(
	                 sleepy.nextInt(5000 - 3000)
	                 + 3000);
	        } catch (Exception e){
	       
	        }
	    		
	        }
	 
    private int locationLookup16(String location) {
    	s_logger.info("Location Index is ... ");
		
    	 // Generate a FAST sleep of 0 time !
        int sleepy = 1;
        try{
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
		return 0;
	}
    private void myCoolFunction3333() {
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        
        } catch (Exception e){
      	  
        }
      }
    private void myCoolFunction433434() {
        // Generate a FAST sleep of 0 time !
        int sleepy = 0;
        try{
        Thread.sleep(sleepy);
        Thread.sleep(sleepy);
        } catch (Exception e){
      	  
        }
      }
   
    
    private int getMyInt(String location ) {
    	return 999;
    }
    
    @GET
    @Path("/healthcheck")
    public String healthCheck() {	
      return "HTTP Status OK (CODE 200)\n"; 
    }  

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") String id) {
        Optional<Product> result = productService.getProduct(id);

        if (result.isPresent()) {
            return Response.status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}

