/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.implementation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.payconiq.api.financialmarkets.stockServices.resources.Stock;
import com.payconiq.api.financialmarkets.stockServices.respository.StockRepository;
import com.payconiq.api.financialmarkets.stockServices.services.StockService;
import com.payconiq.api.financialmarkets.stockServices.services.StockServiceException;

/**
 * @author Shweta 
 *
 */

@Service("stockService")
public class StockServiceImpl implements StockService {

	 @Autowired
	 StockRepository repository;
	
		
	@Override
	public List<Stock> retrieveAllStocks() throws StockServiceException {
		return repository.findAll();
	}

	@Override
	public Page<Stock> retrieveStocksInPages(Pageable pageable) throws StockServiceException {
		if(pageable == null) {
			throw new StockServiceException(StockServiceErrorConstants.NULL_PARAMETER_ERROR);
		}
		return repository.findAll(pageable);
	}
	@Override
	public Stock findStockbyID(long stockId) throws StockServiceException {
		return repository.findById(stockId);
	}

	@Override
	public Stock createStock(Stock inputStockDetails) throws StockServiceException {
		if(inputStockDetails ==  null) {
			throw new StockServiceException(StockServiceErrorConstants.NULL_PARAMETER_ERROR);
		}
		//Name is considered to be a mandatory data
		if(StringUtils.isBlank(inputStockDetails.getName())) {
			throw new StockServiceException(StockServiceErrorConstants.INVALID_INPUT_ERROR);
		}
		inputStockDetails.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		return repository.save(inputStockDetails);
	}

	@Override
	public Stock updateStockPrice(long stockId, BigDecimal stockPrice) throws StockServiceException {
		if(stockPrice == null) {
			throw new StockServiceException(StockServiceErrorConstants.NULL_PARAMETER_ERROR);
		}
		int noOfRowsUpdated = repository.updatePrice(stockId, stockPrice, new Timestamp(System.currentTimeMillis()));
		if(noOfRowsUpdated == 1) {
			return repository.findById(stockId);
		}else {
			return null;
		}
		
	}
	
}
