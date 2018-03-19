/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;


/**
 * GlobalExceptionHandler : This is global exception handler for a Stock Service
 * @author Shweta
 *
 */


@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Unable to process request.")
	 @ExceptionHandler(Throwable.class)
	    public void handleGlobalException(Throwable t) {
			LOGGER.error("Unhandled exception Occurred : "+t.getMessage());
		 
	    }

	    @ExceptionHandler(HttpServerErrorException.class)
	    public ResponseEntity handleGlobalException(HttpServerErrorException e) {
	    	LOGGER.error("HTTP Server exception Occurred : "+e.getMessage());
	        return new ResponseEntity(e.getStatusCode());
	    }
	    
	    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(IllegalArgumentException.class)
	    public void handleBadRequest() {
	    	LOGGER.error("BAD request");
	    }
	    
	    @ExceptionHandler(StockControllerException.class)
	    public HttpEntity handleStockControllerException(StockControllerException e) {
	    	LOGGER.error(e.getErrorMessageKey() + " : " + e.getErrorDesc());
	        return new ResponseEntity(e.getHttpStatusCode());
	    }
	
}
