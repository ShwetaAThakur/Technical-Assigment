package com.payconiq.api.financialmarkets.stockServices.respository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.payconiq.api.financialmarkets.stockServices.resources.Stock;

/**
 * StockRepository :  This class acts as a repository class for connecting to a H2 Database. 
 * @author Shweta
 *
 */
public interface StockRepository extends PagingAndSortingRepository<Stock, Long>{

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findAll()
	 */
	List<Stock> findAll();
	
	/**
	 * This method searches a single record matching input stockID
	 * @param stockID
	 * @return
	 */
	Stock findById(long stockID);
	
	/**
	 * This method updates current price of a single stock which is identified by input stock id
	 * @param stockId
	 * @param newPrice
	 * @param lastModifiedTime
	 * @return
	 */
	@Transactional
	@Modifying
    @Query("UPDATE Stock SET PRICE = :newPrice, LAST_MODIFIED = :lastModifiedTime WHERE id = :stockId")
	int updatePrice(@Param("stockId") long stockId, @Param("newPrice") BigDecimal newPrice,@Param("lastModifiedTime") Timestamp lastModifiedTime);
}
