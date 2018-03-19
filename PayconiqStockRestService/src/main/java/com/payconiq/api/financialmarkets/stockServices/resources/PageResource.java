/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.resources;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;


/**
 * This class provides HATEOS links for a given page. 
 * @author Shweta
 *
 */
public class PageResource extends ResourceSupport {
	
	private List<StockResource> content;
	
	private int currentPage;
	
	private int itemsPerPage;
	
	private long totalItems;
	
	public PageResource (final List<StockResource> stockList,int currentPageToSet, int itemsPerPageToSet,long totalItemsToSet) {
		this.content = stockList;
		this.currentPage = currentPageToSet;
		this.itemsPerPage = itemsPerPageToSet;
		this.totalItems = totalItemsToSet;
		
			
	}

	/**
	 * @return the content
	 */
	public List<StockResource> getContent() {
		return content;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @return the itemsPerPage
	 */
	public int getItemsPerPage() {
		return itemsPerPage;
	}

	/**
	 * @return the totalItems
	 */
	public long getTotalItems() {
		return totalItems;
	}

	
}
