/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.payconiq.api.financialmarkets.stockServices.resources.Stock;

/**
 * StockService : This is service which provides various operation related to Stock. Currently ONLY CRUD operations are implemented. 
 * 							
 * @author Shweta
 *
 */
public interface StockService {
	
	
	/**
	 * This operation is responsible for retrieving all stocks which are currently present in the system. 
	 * @return List of Stocks 
	 * @throws StockServiceException
	 */
	public List<Stock> retrieveAllStocks() throws StockServiceException;
	
	/**
	 * This method is responsible for retrieving a particular stock details based on input Stock id. 
	 * @param stockId id of Stock whose details needs to be retrieved. 
	 * @return Stock details 
	 * @throws StockServiceException
	 */
	public Stock findStockbyID(long stockId) throws StockServiceException;
	
	/**
	 * This method is responsible for storing input stock details in the System.
	 * @param inputStockDetails stock details which needs to be stored in system.
	 * @return Stock details which are created in System. 
	 * @throws StockServiceException
	 */
	public Stock createStock(Stock inputStockDetails) throws StockServiceException;
	
	/**
	 * This method is responsible for updating price of a particular stock identified by input stockId
	 * @param stockId id of stock whose price needs to be updated. 
	 * @param stockPrice new price
	 * @return updated stock details
	 * @throws StockServiceException
	 */
	public Stock updateStockPrice(long stockId,BigDecimal stockPrice) throws StockServiceException;
	
	/**
	 * This service retrieves list of stocks from Database based on input pagination information. 
	 * @param pageable input like size and page number
	 * @return list of stocks in terms of pages which provides additional information apart from actual stock details 
	 * @throws StockServiceException
	 */
	public Page<Stock> retrieveStocksInPages(Pageable pageable) throws StockServiceException;
	
	

}
