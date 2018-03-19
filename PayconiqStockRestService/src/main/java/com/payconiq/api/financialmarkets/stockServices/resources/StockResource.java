/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.payconiq.api.financialmarkets.stockServices.common.StockControllerException;
import com.payconiq.api.financialmarkets.stockServices.controller.StockController;

/**
 * StockResource : This class provides Stock resource along with HATEOS links 
 * @author Shweta
 *
 */
public class StockResource extends ResourceSupport {
	
	private Stock stock;
	
	
	/**
	 * Constructor
	 * @param stock
	 * @param isStockLinkAdded
	 */
	public StockResource (final Stock stock,final boolean isStockLinkAdded) {
		this.stock = stock;
		long currentStockId = stock.getId();
		if(isStockLinkAdded) {
			add(linkTo(StockController.class).withRel("stocks"));
		}
		try {
			add(linkTo(methodOn(StockController.class).readStock(currentStockId)).withSelfRel());
		}catch(StockControllerException exp) {
			//DO nothing. Ignore the exception. In this case Link for 'self' will not be created. 
		}
		
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}
	
	

}
