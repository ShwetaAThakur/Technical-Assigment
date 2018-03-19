package com.payconiq.api.financialmarkets.stockServices.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.api.financialmarkets.stockServices.common.GlobalExceptionHandler;
import com.payconiq.api.financialmarkets.stockServices.resources.Stock;
import com.payconiq.api.financialmarkets.stockServices.services.StockService;
import com.payconiq.api.financialmarkets.stockServices.services.StockServiceException;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StockController.class, secure = false)
public class StockControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	StockService mockStockService;
	
	@Configuration
    @ComponentScan(basePackageClasses = { StockController.class,GlobalExceptionHandler.class })
    public static class TestConf {}
	
	@Test
	public void testReadStock() {
		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(8888));
			mockedStockResource.setId(2);
		Mockito.when(
				mockStockService.findStockbyID(Mockito.anyLong())).thenReturn(mockedStockResource);
		 mockMvc.perform(get("/api/stocks/2")).andDo(print()).andExpect(status().isOk())
         .andExpect(content().string(containsString("8888"))).andExpect(content().string(containsString("2")));
		}catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testReadStock_noStockId() {
		try {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
					"/api/stocks/0").accept(
					MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
			
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testReadStock_noDataFound() {
		try {
			
			Mockito.when(
					mockStockService.findStockbyID(Mockito.anyLong())).thenReturn(null);
		
			mockMvc.perform(get("/api/stocks/2")).andDo(print()).andExpect(status().isNotFound());
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testReadStock_serviceException() {
		try {
			Mockito.when(
					mockStockService.findStockbyID(Mockito.anyLong())).thenThrow(new StockServiceException(null));
		 
		mockMvc.perform(get("/api/stocks/2")).andDo(print()).andExpect(status().isInternalServerError());
			
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testCreateStock() {
		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(434.9));
			mockedStockResource.setName("Stock created during Junit");
			mockedStockResource.setId(1);
			
			Stock inputStockResource = new Stock();
			inputStockResource.setCurrentPrice(new BigDecimal(434.9));
			inputStockResource.setName("Stock created during Junit");
			Mockito.when(
					mockStockService.createStock(Mockito.any(Stock.class))).thenReturn(mockedStockResource);
		 
			mockMvc.perform(
		            post("/api/stocks")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(inputStockResource)))
		            .andExpect(status().isCreated())
		            .andExpect(header().string("location", containsString("/api/stocks/1")));
			
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testCreateStock_serviceException() {
		try {
			Stock inputStockResource = new Stock();
			inputStockResource.setCurrentPrice(new BigDecimal(434.9));
			inputStockResource.setName("Stock created during Junit");
			
			Mockito.when(
					mockStockService.createStock(Mockito.any(Stock.class))).thenThrow(new StockServiceException(null));
			
			mockMvc.perform(
		            post("/api/stocks")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(inputStockResource)))
		            .andExpect(status().isInternalServerError());
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testCreateStock_NoValidDataInRequest() {
		try {
			Stock inputStockResourceCase2 = new Stock();
			inputStockResourceCase2.setCurrentPrice(new BigDecimal(434.9));
			
			Stock inputStockResourceCase3 = new Stock();
			inputStockResourceCase3.setName("Stock created during Junit");
			//Case 1 : when Stock Details are none
			mockMvc.perform(
		            post("/api/stocks")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(null)))
		            .andExpect(status().isInternalServerError());
			
			//Case 2 :  when Name of the stock is not present in the Request 
			mockMvc.perform(
		            post("/api/stocks")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(inputStockResourceCase2)))
		            .andExpect(status().isBadRequest());
			
			//Case 3: When current price of stock is not defined in the request 
			mockMvc.perform(
		            post("/api/stocks")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(inputStockResourceCase3)))
		            .andExpect(status().isBadRequest());
			
		}catch (Exception e) {
			fail();
		}
			
	}
	@Test
	public void testUpdateStockPrice() {
		try {
			Stock mockedStockResource = new Stock();
			mockedStockResource.setCurrentPrice(new BigDecimal(434.9));
			mockedStockResource.setName("Stock Updated during Junit");
			mockedStockResource.setId(1);
			
			Stock inputStockResource = new Stock();
			inputStockResource.setCurrentPrice(new BigDecimal(434.9));
			Mockito.when(
					mockStockService.updateStockPrice(1, inputStockResource.getCurrentPrice())).thenReturn(mockedStockResource);
		 
			mockMvc.perform(
					 put("/api/stocks/1/price")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(inputStockResource)))
		            .andExpect(status().isOk())
		            .andExpect(content().string(containsString("/api/stocks/1")))
		            .andExpect(content().string(containsString(inputStockResource.getCurrentPrice().toString())));
			
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testUpdateStockPrice_noStockId() {
		try {
			Stock inputStockResource = new Stock();
			inputStockResource.setCurrentPrice(new BigDecimal(434.9));
			
			
			mockMvc.perform(
		            put("/api/stocks/0/price")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(inputStockResource)))
		            .andExpect(status().isBadRequest());
			
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testUpdateStockPrice_serviceException() {
		try {
			Stock inputStockResource = new Stock();
			inputStockResource.setCurrentPrice(new BigDecimal(434.9));
						
			Mockito.when(
					mockStockService.updateStockPrice(Mockito.anyLong(), Mockito.any(BigDecimal.class))).thenThrow(new StockServiceException(null));
			
			mockMvc.perform(
		            post("/api/stocks/1/price")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(inputStockResource)))
		            .andExpect(status().isInternalServerError());
			
					
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testUpdateStockPrice_noDataFound() {
		try {
			Stock inputStockResource = new Stock();
			inputStockResource.setCurrentPrice(new BigDecimal(434.9));
			
			
			Mockito.when(
					mockStockService.updateStockPrice(Mockito.anyLong(), Mockito.any(BigDecimal.class))).thenReturn(null);
		
			mockMvc.perform(
		            put("/api/stocks/2/price")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(inputStockResource)))
		            .andExpect(status().isNotFound());
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testRetrieveAllStocks () {
		try {
			Stock mockedStockResource1 = new Stock();
			mockedStockResource1.setCurrentPrice(new BigDecimal(8888));
			mockedStockResource1.setId(1);
			
			Stock mockedStockResource2 = new Stock();
			mockedStockResource2.setCurrentPrice(new BigDecimal(8888));
			mockedStockResource2.setId(2);
			List<Stock> mockedStockResult = Arrays.asList(mockedStockResource1, mockedStockResource2);
			
		Mockito.when(
				mockStockService.retrieveAllStocks()).thenReturn(mockedStockResult);
		 mockMvc.perform(get("/api/stocks")).andDo(print()).andExpect(status().isOk())
         .andExpect(content().string(containsString("8888"))).andExpect(content().string(containsString("2")));
		}catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testRetrieveAllStocks_noDataFound() {
		try {
			
			Mockito.when(
					mockStockService.retrieveAllStocks()).thenReturn(null);
		
			mockMvc.perform(get("/api/stocks")).andDo(print()).andExpect(status().isNotFound());
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testRetrieveAllStocks_serviceException() {
		try {
			Mockito.when(
					mockStockService.retrieveAllStocks()).thenThrow(new StockServiceException(null));
		 
		mockMvc.perform(get("/api/stocks")).andDo(print()).andExpect(status().isInternalServerError());
			
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testRetrieveAllStocksWithPagination () {
		try {
			Stock mockedStockResource1 = new Stock();
			mockedStockResource1.setCurrentPrice(new BigDecimal(8888));
			mockedStockResource1.setId(1);
			
			Stock mockedStockResource2 = new Stock();
			mockedStockResource2.setCurrentPrice(new BigDecimal(8888));
			mockedStockResource2.setId(2);
			Page<Stock> mockedStockPage =  new PageImpl(Arrays.asList(mockedStockResource1, mockedStockResource2), new PageRequest(1, 2), 4);
		Mockito.when(
				mockStockService.retrieveStocksInPages(Mockito.any(Pageable.class))).thenReturn(mockedStockPage);
		mockMvc.perform(get("/api/stocks")
				.param("page", "0")
				.param("size", "2"))
		.andDo(print()).andExpect(status().isOk())
         .andExpect(content().string(containsString("8888")));
		}catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testRetrieveAllStocksWithPagination_noDataFound() {
		try {
			
			Mockito.when(
					mockStockService.retrieveStocksInPages(Mockito.any(Pageable.class))).thenReturn(null);
		
			mockMvc.perform(get("/api/stocks")
					.param("page", "0")
					.param("size", "2"))
					.andDo(print()).andExpect(status().isNotFound());
		}catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testRetrieveAllStocksWithPagination_serviceException() {
		try {
			Mockito.when(
					mockStockService.retrieveStocksInPages(Mockito.any(Pageable.class))).thenThrow(new StockServiceException(null));
		 
			mockMvc.perform(get("/api/stocks")
					.param("page", "0")
					.param("size", "2"))
			.andDo(print()).andExpect(status().isInternalServerError());
			
		}catch (Exception e) {
			fail();
		}
			
	}
	
	private String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	

}
