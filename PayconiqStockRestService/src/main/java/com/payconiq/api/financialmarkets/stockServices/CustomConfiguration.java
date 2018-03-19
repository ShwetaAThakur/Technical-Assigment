/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.payconiq.api.financialmarkets.stockServices.resources.Stock;

/**
 * 
 * Custom configuration added to expose ID fields in the response body
 * @author Shweta
 *
 */
@Configuration
public class CustomConfiguration extends RepositoryRestConfigurerAdapter {
	
	 @Override
	    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
	        config.exposeIdsFor(Stock.class);
	 }
}
