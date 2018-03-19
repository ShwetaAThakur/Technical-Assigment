/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.common;

/**
 * This class provides constants needed for various error codes 
 * @author Shweta
 *
 */
public class StockControllerErrorCodes {
	
	/**
	 * Private Constructor added to avoid implicit public constructor
	 */
	private StockControllerErrorCodes() {
		
	}
	
	public static final String NULL_PARAMETER_ERROR = "ERROR_CNTRL_0001";
	public static final String INVALID_INPUT_ERROR = "ERROR_CNTRL_0002";
	public static final String NO_DATA_FOUND = "ERROR_CNTRL_0003";
	
	public static final String NO_DATA_CREATED_ERR_DESC = "No data stored";

}
