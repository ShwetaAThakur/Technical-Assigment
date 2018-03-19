package com.payconiq.api.financialmarkets.stockServices.hateoas.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.payconiq.api.financialmarkets.stockServices.common.StockControllerException;
import com.payconiq.api.financialmarkets.stockServices.controller.StockController;
import com.payconiq.api.financialmarkets.stockServices.hateoas.events.RetrieveDataWithPaginationEvent;
import com.payconiq.api.financialmarkets.stockServices.resources.PageResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@SuppressWarnings({ "rawtypes" })
@Component
public class RetrieveDataWithPaginationEventListener implements ApplicationListener<RetrieveDataWithPaginationEvent> {

    private static final String PAGE = "page";
    
    private static final String SIZE = "size";
    
    private static final String REL_NEXT = "next";
    private static final String REL_PREV = "prev";
    private static final String REL_FIRST = "first";
    private static final String REL_LAST = "last";

    public RetrieveDataWithPaginationEventListener() {
        super();
    }

    // API

    @Override
    public final void onApplicationEvent(final RetrieveDataWithPaginationEvent event) {
       addLinkHeaderOnPagedResourceRetrieval(event.getPageResource(),event.getPage(), event.getTotalPages(), event.getPageSize());
    }

    final void addLinkHeaderOnPagedResourceRetrieval(final PageResource pageResource, final int page, final int totalPages, final int pageSize) {
      
        UriComponentsBuilder uriBuilder; 
        try {
        	 uriBuilder= linkTo(methodOn(StockController.class).retrieveAllStocks()).toUriComponentsBuilder();
        }catch(StockControllerException exp) {
        	//created hard coded one 
        	uriBuilder= UriComponentsBuilder.fromUriString("/api/stocks");
        }
        
        uriBuilder.queryParam(PAGE);
        uriBuilder.queryParam(SIZE);
        
       if (hasNextPage(page, totalPages)) {
            final String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
            pageResource.add(new Link(uriForNextPage).withRel(REL_NEXT));
            
        }
        if (hasPreviousPage(page)) {
            final String uriForPrevPage = constructPrevPageUri(uriBuilder, page, pageSize);
            pageResource.add(new Link(uriForPrevPage).withRel(REL_PREV));
            
        }
        if (hasFirstPage(page)) {
            final String uriForFirstPage = constructFirstPageUri(uriBuilder, pageSize);
            pageResource.add(new Link(uriForFirstPage).withRel(REL_FIRST));
           
        }
        if (hasLastPage(page, totalPages)) {
            final String uriForLastPage = constructLastPageUri(uriBuilder, totalPages, pageSize);
            pageResource.add(new Link(uriForLastPage).withRel(REL_LAST));
            
        }

    }

    final String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page + 1).replaceQueryParam(SIZE, size).build().encode().toUriString();
    }

    final String constructPrevPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page - 1).replaceQueryParam(SIZE, size).build().encode().toUriString();
    }

    final String constructFirstPageUri(final UriComponentsBuilder uriBuilder, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, 0).replaceQueryParam(SIZE, size).build().encode().toUriString();
    }

    final String constructLastPageUri(final UriComponentsBuilder uriBuilder, final int totalPages, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, totalPages).replaceQueryParam(SIZE, size).build().encode().toUriString();
    }

    final boolean hasNextPage(final int page, final int totalPages) {
        return page < (totalPages - 1);
    }

    final boolean hasPreviousPage(final int page) {
        return page > 0;
    }

    final boolean hasFirstPage(final int page) {
        return hasPreviousPage(page);
    }

    final boolean hasLastPage(final int page, final int totalPages) {
        return (totalPages > 1) && hasNextPage(page, totalPages);
    }

    final void appendCommaIfNecessary(final StringBuilder linkHeader) {
        if (linkHeader.length() > 0) {
            linkHeader.append(", ");
        }
    }
    
    
}
