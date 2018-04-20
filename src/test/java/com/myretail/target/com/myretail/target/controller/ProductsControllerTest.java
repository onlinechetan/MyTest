package com.myretail.target.com.myretail.target.controller;

import com.myretail.target.com.myretail.target.dto.Price;
import com.myretail.target.com.myretail.target.dto.ProductResponse;
import com.myretail.target.com.myretail.target.exception.DefaultExceptionHandler;
import com.myretail.target.com.myretail.target.exception.ProductInternalServerError;
import com.myretail.target.com.myretail.target.exception.ProductNotFound;
import com.myretail.target.com.myretail.target.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class ProductsControllerTest {
    @Mock
    private ProductService productService;
    private ProductsController productsController;
    private DefaultExceptionHandler defaultExceptionHandler;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        productsController = new ProductsController(productService);
        defaultExceptionHandler = new DefaultExceptionHandler();
        mockMvc = standaloneSetup(productsController)
                .setControllerAdvice(defaultExceptionHandler)
                .build();
    }

    /**
     * Verify expected response after mocking service class.
     */
    @Test
    public void testProductDetail() throws Exception {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId("15117729");
        productResponse.setName("The Big Lebowski (Blu-ray) (Widescreen)");

        Price priceUSD = new Price();
        priceUSD.setCurrency_code("USD");
        priceUSD.setValue(13.49);

        Price priceINR = new Price();
        priceINR.setCurrency_code("INR");
        priceINR.setValue(750.49);
        productResponse.setCurrency_price(Arrays.asList(priceUSD, priceINR));
        when(productService.getProductDetail("15117729")).thenReturn(productResponse);

        mockMvc.perform(get("/products/15117729"))
                .andExpect(jsonPath("$.id", equalTo("15117729")))
                .andExpect(jsonPath("$.name", equalTo("The Big Lebowski (Blu-ray) (Widescreen)")))
                .andExpect((jsonPath("$.currency_price[0].currency_code", equalTo("USD"))))
                .andExpect((jsonPath("$.currency_price[0].value", equalTo(13.49))))
                .andExpect((jsonPath("$.currency_price[1].currency_code", equalTo("INR"))))
                .andExpect((jsonPath("$.currency_price[1].value", equalTo(750.49))))
                .andReturn();
    }

    /**
     * Verify response after mocking service class throws not found exception.
     */
    @Test
    public void testProductDetailNotFound() throws Exception {
        when(productService.getProductDetail(anyString())).thenThrow(new ProductNotFound("not found"));
        mockMvc.perform(get("/products/15117729")).andExpect(status().isNotFound());
    }

    /**
     * Verify response after mocking service class throws exception.
     */
    @Test
    public void testProductDetailServerException() throws Exception {
        when(productService.getProductDetail(anyString())).thenThrow(new ProductInternalServerError("server error"));
        mockMvc.perform(get("/products/15117729")).andExpect(status().isInternalServerError());
    }
}