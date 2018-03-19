/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.services;

/**
 * 
 * StockServiceException  : Exception class for StockService
 * @author Shweta
 *
 */
@SuppressWarnings("serial")
public class StockServiceException extends Exception{
	
	private String errorMessageKey;
	
	public StockServiceException(String errorMessageKey) {
		this.errorMessageKey = errorMessageKey;
	
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

	
}
