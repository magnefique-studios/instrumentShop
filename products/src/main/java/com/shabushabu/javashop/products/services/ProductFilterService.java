package com.shabushabu.javashop.products.services;


import java.util.List;
import java.util.Random;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shabushabu.javashop.products.model.Product;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;


public class ProductFilterService {

	
	    private static Logger s_logger = LogManager.getLogger(ProductFilterService.class);
	    
	     
	    @WithSpan()
	    public List<Product> filterAllProducts(@SpanAttribute("location") String location,  @SpanAttribute("productService") ProductService productService) {
	    	s_logger.info("Enteriing ProductResource::getAllProducts() location = " + location);
	    	//  All we know right now is somewhere in this function, latency was introduced.
	  
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
				
	        return productService.getAllProducts();
	          
	    }
	    
	     
	    @WithSpan()
	    private void myCoolFunction(@SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        Thread.sleep(sleepy);
	        
	        lookupLocation1(location);
	        
	        } catch (Exception e){
	      	  
	        }
	      }
	  
	    
	     
	    @WithSpan()
	    private void myCoolFunction1( @SpanAttribute("location") String location) {
	      // Generate a FAST sleep of 0 time !
	      int sleepy = lookupLocation1(location);
	      try{
	      Thread.sleep(sleepy);
	      
	      } catch (Exception e){
	    	  
	      }
	    }
	     
	    @WithSpan()
	    private int lookupLocation1( @SpanAttribute("location") String location) {
	    	 int sleepy =4;
	    	 
	    	 return sleepy;
			
		}
	    
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
		   private int lookupLocation19( @SpanAttribute("location") String location) {
	    	
	    	int sleepy = lookupLocation1(location);
	    	// Generate a FAST sleep of 0 time !
	         
	    	try{
	        	 Thread.sleep(sleepy);
	        	 
	        	 
	         } catch (Exception e){
	       	  
	         }
			return sleepy;
		}

		 
		@WithSpan()
		private void myCoolFunction2(@SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 3;
	        try{
	        	
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
		
	     
	    @WithSpan()
	    private int lookupLocation3( @SpanAttribute("location") String location) {
	    	 // Generate a FAST sleep of 0 time !
	        int sleepy = 2;
	      
	        try{
	        	Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
			return sleepy;
			
		}
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
		   private int lookupLocation4(@SpanAttribute("location") String location) {
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
	     
	    @WithSpan()
	    private int myCoolFunction5(@SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 2;
	        lookupLocation3(location);
	        try{
	        	Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
			return sleepy;
	      }
	     
	    @WithSpan()
	    private void myCoolFunction6(@SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 3;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction7(@SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 4;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction8(  @SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 8;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction9(  @SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        	
	        	Thread.sleep(sleepy);
	        
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction10(  @SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction11(  @SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = locationLookup11(location);
	        try{
	        	
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction12(  @SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction13(  @SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction14(  @SpanAttribute("location") String location) {
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	    
	     
	    @WithSpan()
	    private void myCoolFunction23(  @SpanAttribute("loc") String loc,   @SpanAttribute("location") String location,    @SpanAttribute("index") int index){
	    	s_logger.info("Location Index is ... " + index);
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        	s_logger.info("Location Index is ... " + index);
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction24(  @SpanAttribute("loc") String loc,   @SpanAttribute("location") String location,    @SpanAttribute("index") int index){
	    	s_logger.info("Location Index is ... " + index);
	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        	s_logger.info("Location Index is ... " + index);
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	     
	    @WithSpan()
	    private void myCoolFunction25(  @SpanAttribute("loc") String loc,   @SpanAttribute("location") String location,    @SpanAttribute("index") int index){
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
	    
	     
	    @WithSpan()
	    private void myCoolFunction() {
	    	s_logger.info("Location Index is ... ");
			@SuppressWarnings("unused")
			int sleepy1 =3;
	    	  @SuppressWarnings("unused")
			Random random = new Random();

	        // Generate a FAST sleep of 0 time !
	        int sleepy = 0;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
	      }
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
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
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
		   private void myCoolFunction34(  @SpanAttribute("location") String location) {
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
	    
	     
	    @WithSpan()
	    private int locationLookup2(  @SpanAttribute("location") String location) {
	    	System.out.println("Location Index is ... ");
	    	@SuppressWarnings("unused")
			int sleepy1 =3;
	    	 // Generate a FAST sleep of 0 time !
	        int sleepy = 1;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
			return 0;
		}
	    
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
		   private int locationLookup21(  @SpanAttribute("location") String location) {
	    	s_logger.info("Location Index is ... ");
	    	@SuppressWarnings("unused")
			int sleepy1 =3;
	    	 // Generate a FAST sleep of 0 time !
	        int sleepy = 1;
	        try{
	        Thread.sleep(sleepy);
	        } catch (Exception e){
	      	  
	        }
			return 0;
		}
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
		   private int locationLookup22(  @SpanAttribute("location") String location) {
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
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
		   private int locationLookup23(  @SpanAttribute("location") String location) {
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

		@SuppressWarnings("unused")
		@WithSpan()
 	
		private int locationLookup11(  @SpanAttribute("location") String location) { 
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
		
		 @SuppressWarnings("unused")
		 @WithSpan()
 	 
		 private int locationLookup15(  @SpanAttribute("location") String location) {
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
		 
		  
		 @WithSpan()
		 private void myCoolFunction234234234( @SpanAttribute("myInt") int myInt) {
		    	// Generate a FAST sleep of 0 time !
		    Random sleepy = new Random();
		    try{
		       if (999==myInt) 
		          Thread.sleep(
		                 sleepy.nextInt(500 - 300)
		                 + 966);
		    } catch (Exception e){
		       
		    }
		    		
		}
		 
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
		   private int locationLookup16(  @SpanAttribute("location") String location) {
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
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
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
	    @SuppressWarnings("unused")
		   @WithSpan()
 	   
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
	    
	  

	   
	   /* public Response getProduct(@PathParam("id") String id) {
	        Optional<Product> result = productService.getProduct(id);

	        if (result.isPresent()) {
	            return Response.status(Response.Status.OK)
	                    .entity(result.get())
	                    .build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND)
	                    .build();
	        }
	    }*/
	    
	    
	}


