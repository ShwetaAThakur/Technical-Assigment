/**
 * 
 */
package com.payconiq.api.financialmarkets.stockServices.implementation;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payconiq.api.financialmarkets.stockServices.resources.Stock;
import com.payconiq.api.financialmarkets.stockServices.respository.StockRepository;
import com.payconiq.api.financialmarkets.stockServices.services.StockService;
import com.payconiq.api.financialmarkets.stockServices.services.StockServiceException;

/**
 * @author Shweta
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class StockServiceImplTest {

	@MockBean
	StockRepository mockRepository;

	
	@Autowired
	public StockService stockService;

	/*@Before
	public void setUp() throws Exception {
		//MockitoAnnotations.initMocks(this);
	}*/

	@Configuration
	static class AccountServiceTestContextConfiguration {
		@Bean
		public StockService stockService() {
			return new StockServiceImpl();
		}
	}

	@Test
	public void testCreate() {

		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(8888));
			mockedStockResource.setId(2);
			mockedStockResource.setName("Test Data created during Junit");
			
			Stock inputStockDetails = new Stock();
			inputStockDetails.setCurrentPrice(new BigDecimal(8888));
			inputStockDetails.setName("Test Data created during Junit");
			
			
			Mockito.when(
					mockRepository.save(Mockito.any(Stock.class))).thenReturn(mockedStockResource);
			
			
			Stock stockDetailsCreated = stockService.createStock(inputStockDetails);
			Assert.assertEquals(mockedStockResource.getCurrentPrice(), stockDetailsCreated.getCurrentPrice());
			Assert.assertEquals(mockedStockResource.getId(), stockDetailsCreated.getId());
			
		} catch (StockServiceException e) {
			fail();
		}
	}
	
	@Test
	public void testCreateNoInput() {

		try {
			stockService.createStock(null);
			fail();
		} catch (StockServiceException e) {
			Assert.assertEquals(StockServiceErrorConstants.NULL_PARAMETER_ERROR, e.getErrorMessageKey());
		}
	}
	
	@Test
	public void testCreateInvalidInput() {

		try {
			stockService.createStock(new Stock());
			fail();
			
		} catch (StockServiceException e) {
			Assert.assertEquals(StockServiceErrorConstants.INVALID_INPUT_ERROR, e.getErrorMessageKey());
		}
	}
	@Test
	public void testUpdateStockPrice() {

		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(56565.8989));
			mockedStockResource.setId(2);
			mockedStockResource.setName("Test Data created during Junit");
			
				
			Mockito.when(
					mockRepository.updatePrice(Mockito.anyLong(), Mockito.any(BigDecimal.class), Mockito.any(Timestamp.class))).thenReturn(1);
			
			Mockito.when(
					mockRepository.findById(Mockito.anyLong())).thenReturn(mockedStockResource);
			
			
			Stock updatedStockDetails = stockService.updateStockPrice(2,new BigDecimal(56565.8989));
			Assert.assertEquals(mockedStockResource.getCurrentPrice(), updatedStockDetails.getCurrentPrice());
			Assert.assertEquals(mockedStockResource.getId(), updatedStockDetails.getId());
			
		} catch (StockServiceException e) {
			fail();
		}
	}
	
	@Test
	public void testUpdateStockPriceNoInput() {

		try {
			stockService.updateStockPrice(2,null);
			fail();
		} catch (StockServiceException e) {
			Assert.assertEquals(StockServiceErrorConstants.NULL_PARAMETER_ERROR, e.getErrorMessageKey());
		}
	}
	
	@Test
	public void testUpdateStockPrice_NoDataFound() {

		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(56565.8989));
			mockedStockResource.setId(2);
			mockedStockResource.setName("Test Data created during Junit");
			
				
			Mockito.when(
					mockRepository.updatePrice(Mockito.anyLong(), Mockito.any(BigDecimal.class), Mockito.any(Timestamp.class))).thenReturn(0);
			
			Stock updatedStockDetails = stockService.updateStockPrice(2,new BigDecimal(56565.8989));
			Assert.assertEquals(null,updatedStockDetails);
			
			
		} catch (StockServiceException e) {
			fail();
		}
	}
	
	@Test
	public void testFindStockbyID() {

		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(56565.8989));
			mockedStockResource.setId(2);
			mockedStockResource.setName("Test Data created during Junit");
			
			Mockito.when(
					mockRepository.findById(Mockito.anyLong())).thenReturn(mockedStockResource);
			
			
			Stock resultStockDetails = stockService.findStockbyID(2);
			Assert.assertEquals(mockedStockResource.getCurrentPrice(), resultStockDetails.getCurrentPrice());
			Assert.assertEquals(mockedStockResource.getName(), resultStockDetails.getName());
			Assert.assertEquals(2, resultStockDetails.getId());
			
		} catch (StockServiceException e) {
			fail();
		}
	}
	
	@Test
	public void testRetrieveAllStocks() {

		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(56565.8989));
			mockedStockResource.setId(2);
			mockedStockResource.setName("Test Data created during Junit");
			
			Mockito.when(
					mockRepository.findAll()).thenReturn(Arrays.asList(mockedStockResource));
			
			
			List<Stock> resultStockDetails = stockService.retrieveAllStocks();
			Assert.assertEquals(1, resultStockDetails.size());
			Assert.assertEquals(mockedStockResource.getName(), resultStockDetails.get(0).getName());
			Assert.assertEquals(2, resultStockDetails.get(0).getId());
			
		} catch (StockServiceException e) {
			fail();
		}
	}
	
	@Test
	public void testRetrieveStocksInPages() {

		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(56565.8989));
			mockedStockResource.setId(2);
			mockedStockResource.setName("Test Data created during Junit");
			Page<Stock> mockedStockPage =  new PageImpl(Arrays.asList(mockedStockResource), new PageRequest(1, 2), 4);
			
			Mockito.when(
					mockRepository.findAll(Mockito.any(Pageable.class))).thenReturn(mockedStockPage);
			
			
			Page<Stock> resultStockDetails = stockService.retrieveStocksInPages(new PageRequest(0,2));
			Assert.assertEquals(4, resultStockDetails.getTotalElements());
			Assert.assertEquals(1, resultStockDetails.getContent().size());
		} catch (StockServiceException e) {
			fail();
		}
	}
	
	@Test(expected = StockServiceException.class)
	public void testRetrieveStocksInPages_NoInput() throws StockServiceException{
		stockService.retrieveStocksInPages(null);
	}
	
	

	
}
