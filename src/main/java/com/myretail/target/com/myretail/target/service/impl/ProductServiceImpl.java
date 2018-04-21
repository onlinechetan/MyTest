/*
  copyright headers.
 */
package com.myretail.target.com.myretail.target.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.myretail.target.com.myretail.target.dto.ProductResponse;
import com.myretail.target.com.myretail.target.exception.ProductInternalServerError;
import com.myretail.target.com.myretail.target.exception.ProductNotFound;
import com.myretail.target.com.myretail.target.service.NoSqlService;
import com.myretail.target.com.myretail.target.service.ProductService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Product service that holds the logic for data handling with controller and repository.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private NoSqlService noSqlService;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Bean
    private HttpClient httpClient(){
        return HttpClientBuilder.create().build();
    }
    /**
     * {@inheritDoc}
     *
     * Populate Product response by getting details from external rest service.
     *
     * @return
     */
    @Override
    public ProductResponse getProductDetail(String productId) {
        LOGGER.info("Executing service to get product details {}", productId);
        ProductResponse productResponse = new ProductResponse();

        String url = "https://redsky.target.com/v1/pdp/tcin/" + productId + "?excludes=taxonomy,price,promotion," +
                "bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        try {
            Future<HttpResponse> responseFuture = populateProductDetail(url);
            //get details from no sql.
            productResponse.setCurrency_price(noSqlService.getProductPricing(productId));
            //now get the response from rest end point.
            HttpResponse response = responseFuture.get();
            String responseEntity  = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() == HttpStatus.NOT_FOUND.value()){
                throw new ProductNotFound("Product not found, please check product id " +productId);
            }
            String title = JsonPath.read(responseEntity, "$.product.item.product_description.title");
            productResponse.setId(productId);
            productResponse.setName(title);
        } catch(IOException |InterruptedException| ExecutionException e) {
            throw new ProductInternalServerError("Server error while getting product details "+productId);
        }
        LOGGER.debug("Returning Product response for: {}", productResponse.getId());
        return productResponse;
    }

    @Async
    private Future<HttpResponse> populateProductDetail(String url) {
        LOGGER.debug("get url: {}", url);
        return executorService.submit(() -> {
            HttpGet get = new HttpGet(url);
            return httpClient().execute(get);
        });
    }
}