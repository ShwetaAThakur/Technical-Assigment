/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.common;

import org.springframework.http.HttpStatus;

/**
 * 
 * StockControllerException  : Exception class for StockController
 * @author Shweta
 *
 */
public class StockControllerException extends Exception{
	
	private String errorMessageKey;
	
	private String errorDesc;
	
	private HttpStatus httpStatusCode;
	
	public StockControllerException(String errorMessageKey,String errorDesc,HttpStatus httpStatusCode) {
		this.errorMessageKey = errorMessageKey;
		this.errorDesc = errorDesc;
		this.httpStatusCode = httpStatusCode;
	}

	/**
	 * @return the errorMessageKey
	 */
	public String getErrorMessageKey() {
		return errorMessageKey;
	}

	/**
	 * @param errorMessageKey the errorMessageKey to set
	 */
	public void setErrorMessageKey(String errorMessageKey) {
		this.errorMessageKey = errorMessageKey;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the httpStatusCode
	 */
	public HttpStatus getHttpStatusCode() {
		return httpStatusCode;
	}

	/**
	 * @param httpStatusCode the httpStatusCode to set
	 */
	public void setHttpStatusCode(HttpStatus httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	
	
}
