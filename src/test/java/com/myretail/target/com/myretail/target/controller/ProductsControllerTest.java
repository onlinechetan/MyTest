package com.myretail.target.com.myretail.target.controller;

import com.myretail.target.com.myretail.target.dto.Price;
import com.myretail.target.com.myretail.target.dto.ProductDetail;
import com.myretail.target.com.myretail.target.exception.DefaultExceptionHandler;
import com.myretail.target.com.myretail.target.exception.ProductInternalServerError;
import com.myretail.target.com.myretail.target.exception.ProductNotFound;
import com.myretail.target.com.myretail.target.service.NoSqlService;
import com.myretail.target.com.myretail.target.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

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
    @Mock
    private NoSqlService noSqlService;

    private ProductsController productsController;
    private DefaultExceptionHandler defaultExceptionHandler;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        productsController = new ProductsController(productService, noSqlService);
        defaultExceptionHandler = new DefaultExceptionHandler();
        mockMvc = standaloneSetup(productsController)
                .setControllerAdvice(defaultExceptionHandler)
                .build();
    }

    /**
     * Verify expected response after mocking service class.
     * @throws Exception throws exception
     */
    @Test
    public void testProductDetail() throws Exception {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId("15117729");
        productDetail.setTitle("The Big Lebowski (Blu-ray) (Widescreen)");
        when(productService.getProductDetail("15117729")).thenReturn(productDetail);
        Price price = new Price();
        price.setCurrency_code("USD");
        price.setValue(13.49);
        when(noSqlService.getProductPricing("15117729")).thenReturn(price);
        mockMvc.perform(get("/products/15117729"))
                .andExpect(jsonPath("$.id", equalTo("15117729")))
                .andExpect(jsonPath("$.name", equalTo("The Big Lebowski (Blu-ray) (Widescreen)")))
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