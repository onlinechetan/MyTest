/*
  Copy right header.
 */
package com.myretail.target.com.myretail.target.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.myretail.target.com.myretail.target.dto.Price;
import com.myretail.target.com.myretail.target.exception.ProductInternalServerError;
import com.myretail.target.com.myretail.target.service.NoSqlService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;

/**
 * Service to interact with no sql repository.
 */
@Service
public class NoSqlServiceImpl implements NoSqlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoSqlServiceImpl.class);

    public Price getProductPricing(String productId) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("myRetail");
        FindIterable document =  database.getCollection("pricing").find(eq("productId",productId));
        Document pricing = (Document) document.first();
        ObjectMapper mapper = new ObjectMapper();
        Price price = new Price();
        if(pricing!= null) {
            try {
                price = mapper.readValue(pricing.toJson(), Price.class);
            } catch (IOException e) {
                throw new ProductInternalServerError("no sql server error while getting pricing information "
                        + productId);
            }
        }
        LOGGER.debug("product id {}",pricing);
        return price;
    }
}
