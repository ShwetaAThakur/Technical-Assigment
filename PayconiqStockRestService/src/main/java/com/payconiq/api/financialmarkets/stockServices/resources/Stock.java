/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.resources;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Stock :   This class represents a resource Stock . 
 * @author Shweta 
 *
 */
@Entity
@Table(name="Stock")
public class Stock {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	 @Column(name = "LAST_MODIFIED")
	private Timestamp lastUpdateTime;
	
	 @Column(name = "PRICE")
	private BigDecimal currentPrice; 
	
	 @Column(name = "NAME")
	private String name;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the currentPrice
	 */
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @param currentPrice the currentPrice to set
	 */
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	

	
	

}
