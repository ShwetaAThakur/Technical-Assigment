/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.payconiq.api.financialmarkets.stockServices.common.StockControllerErrorCodes;
import com.payconiq.api.financialmarkets.stockServices.common.StockControllerException;
import com.payconiq.api.financialmarkets.stockServices.hateoas.events.RetrieveDataWithPaginationEvent;
import com.payconiq.api.financialmarkets.stockServices.resources.PageResource;
import com.payconiq.api.financialmarkets.stockServices.resources.Stock;
import com.payconiq.api.financialmarkets.stockServices.resources.StockResource;
import com.payconiq.api.financialmarkets.stockServices.services.StockService;
import com.payconiq.api.financialmarkets.stockServices.services.StockServiceException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * 
 * StockController handles HTTP requests for uri api/stocks
 * 
 * @author Shweta
 *
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {

	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	StockService stockService;
	
	/**
	 * This method provides list of all stocks currently present in the system 
	 * @return list of stocks along with links. 
	 * @throws StockControllerException
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StockResource>> retrieveAllStocks() throws StockControllerException{
		try {
			List<Stock> allStocks = stockService.retrieveAllStocks();
			if(allStocks == null || allStocks.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			List<StockResource> stockResourceList = allStocks.stream()
                    .map(o -> new StockResource(o,false))
                    .collect(Collectors.toList());
			return ResponseEntity.ok(stockResourceList); 
		}catch(StockServiceException serviceException){
			throw new StockControllerException(serviceException.getErrorMessageKey(),"Exception Received from Service",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	} 
	
	/**
	 * This methods retrieves list of all stocks with option of pagination.
	 * @param page
	 * @param size
	 * @param response
	 * @return
	 * @throws StockControllerException
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = { "page", "size" })
	public ResponseEntity<PageResource> retrieveAllStocksWithPagination(@RequestParam("page") final int page, @RequestParam("size") final int size) throws StockControllerException{
		try {
			Pageable pageable = new PageRequest(page,size);
			Page<Stock> allPages = stockService.retrieveStocksInPages(pageable);
			if(allPages == null || allPages.getContent() == null || allPages.getContent().isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			List<StockResource> stockResourceList = allPages.getContent().stream()
                    .map(o -> new StockResource(o,false))
                    .collect(Collectors.toList());
			PageResource pageResource = new PageResource (stockResourceList,allPages.getNumber(),allPages.getNumberOfElements(),allPages.getTotalElements());		
			eventPublisher.publishEvent(new RetrieveDataWithPaginationEvent<Stock>(Stock.class,pageResource, page, allPages.getTotalPages(), size));
			return ResponseEntity.ok(pageResource); 
		}catch(StockServiceException serviceException){
			throw new StockControllerException(serviceException.getErrorMessageKey(),"Exception Received from Service",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	/**
	 * This service reads details of a single Stock identified by input stock id
	 * @param stockId
	 * @return
	 * @throws StockControllerException
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<StockResource> readStock(@PathVariable("id") long stockId) throws StockControllerException{
		try {
			if(stockId == 0) {
				throw new StockControllerException(StockControllerErrorCodes.NULL_PARAMETER_ERROR,"No data received for Stock ID",HttpStatus.BAD_REQUEST);
			}
			Stock tempStock =  stockService.findStockbyID(stockId);
			if(tempStock == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(new StockResource(tempStock,true));
		}catch(StockServiceException serviceException) {
			throw new StockControllerException(serviceException.getErrorMessageKey(),"Exception Received from Service",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This service updates current price of stock which is identified by input id. 
	 * @param stockId
	 * @param stockDetails
	 * @return
	 * @throws StockControllerException
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/price",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StockResource> updateStockPrice(@PathVariable("id") long stockId, @RequestBody Stock stockDetails) throws StockControllerException{
		try {
			if(stockId == 0) {
				throw new StockControllerException(StockControllerErrorCodes.NULL_PARAMETER_ERROR,"No data received for Stock ID",HttpStatus.BAD_REQUEST);
			}
			if(stockDetails == null) {
				throw new StockControllerException(StockControllerErrorCodes.NULL_PARAMETER_ERROR,"No new price present in the request",HttpStatus.BAD_REQUEST);
			}
			
			Stock tempStock =  stockService.updateStockPrice(stockId, stockDetails.getCurrentPrice());
			if(tempStock == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(new StockResource(tempStock,true));
		
		}catch(StockServiceException serviceException) {
			throw new StockControllerException(serviceException.getErrorMessageKey(),"Exception Received from Service",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<StockResource> createStock(@RequestBody Stock stockDetails) throws StockControllerException{
		try {
			validateStock(stockDetails);
				Stock tempStock =  stockService.createStock(stockDetails);
				if(tempStock == null) {
					return ResponseEntity.unprocessableEntity().build();
				}
				final URI uri = MvcUriComponentsBuilder.fromController(getClass())
				            .path("/{id}")
				            .buildAndExpand(tempStock.getId())
				            .toUri();
				return ResponseEntity.created(uri).body(new StockResource(tempStock,true));
		}catch(StockServiceException serviceException) {
			throw new StockControllerException(serviceException.getErrorMessageKey(),"Exception Received from Service",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * This method validates input stock details before storing.
	 * Currently only null checks are added. 
	 * @param stockDetails
	 * @return
	 * @throws StockControllerException
	 */
	private void validateStock(Stock stockDetails) throws StockControllerException {
		
		if(StringUtils.isBlank(stockDetails.getName()) || stockDetails.getCurrentPrice() == null) {
			throw new StockControllerException(StockControllerErrorCodes.NULL_PARAMETER_ERROR,"Mandatory data missing in the request body",HttpStatus.BAD_REQUEST);
		}
	}

}
