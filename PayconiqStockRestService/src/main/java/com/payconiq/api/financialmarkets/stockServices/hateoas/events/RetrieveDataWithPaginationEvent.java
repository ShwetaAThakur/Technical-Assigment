/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.hateoas.events;


import org.springframework.context.ApplicationEvent;

import com.payconiq.api.financialmarkets.stockServices.resources.PageResource;

/**
 * Event that is fired when a paginated read is performed.
 * This event object contains all the information needed to create the URL for the paginated results
 * @author Shweta 
 *
 */
@SuppressWarnings("serial")
public class RetrieveDataWithPaginationEvent<Stock> extends ApplicationEvent {

	private final PageResource pageResource;
    private final int page;
    private final int totalPages;
    private final int pageSize;

    public RetrieveDataWithPaginationEvent(final Class<Stock> resourceClass,PageResource pageResourceToSet, final int pageToSet, final int totalPagesToSet, final int pageSizeToSet) {
        super(resourceClass);
        pageResource = pageResourceToSet;
        page = pageToSet;
        totalPages = totalPagesToSet;
        pageSize = pageSizeToSet;
    }

    /**
     * @return the page
     */
    public final int getPage() {
        return page;
    }

    /**
     * @return the totalPages
     */
    public final int getTotalPages() {
        return totalPages;
    }

    /**
     * @return the pageSize
     */
    public final int getPageSize() {
        return pageSize;
    }


	/**
	 * @return the pageResource
	 */
	public PageResource getPageResource() {
		return pageResource;
	}

    
  }
