/**
 * copyright headers.
 */
package com.myretail.target.com.myretail.target.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.myretail.target.com.myretail.target.dto.ProductDetail;
import com.myretail.target.com.myretail.target.exception.ProductInternalServerError;
import com.myretail.target.com.myretail.target.exception.ProductNotFound;
import com.myretail.target.com.myretail.target.service.ProductService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The Product service that holds the logic for data handling with controller and repository.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * {@inheritDoc}
     *
     * Populate Product response by getting details from external rest service.
     *
     * @return
     */
    @Override
    public ProductDetail getProductDetail(String productId) {
        LOGGER.info("Executing service to get product details {}", productId);
        ProductDetail productDetail = new ProductDetail();
        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "https://redsky.target.com/v1/pdp/tcin/"+ productId +"?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        LOGGER.debug("get url: {}", url);
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(get);
            String responseEntity  = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() == HttpStatus.NOT_FOUND.value()){
                throw new ProductNotFound("Product not found, please check product id " +productId);
            }
            String title = JsonPath.read(responseEntity, "$.product.item.product_description.title");
            productDetail.setId(productId);
            productDetail.setTitle(title);
        } catch(IOException e) {
            throw new ProductInternalServerError("Server error while getting product details "+productId);
        }
        return productDetail;
    }
}