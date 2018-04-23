/*
  Copy right header.
 */
package com.myretail.target.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.myretail.target.dto.Price;
import com.myretail.target.exception.ProductInternalServerError;
import com.myretail.target.service.NoSqlService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Service to interact with no sql repository.
 */
@Service
public class NoSqlServiceImpl implements NoSqlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoSqlServiceImpl.class);
    private ObjectMapper mapper = new ObjectMapper();
    private MongoDatabase myRetailDB;

    @Autowired
    public NoSqlServiceImpl(MongoDatabase mongoDatabase) {
        this.myRetailDB = mongoDatabase;
    }

    @Override
    public List<Price> getProductPricing(String productId) {
        LOGGER.info("product id {}", productId);
        List<Price> prices = new ArrayList<>();

        FindIterable<Document> document = myRetailDB.getCollection("pricing").find(eq("productId",
                productId));
        Price price;
        for (Document product : document) {
            try {
                price = mapper.readValue(product.toJson(), Price.class);
            } catch (IOException e) {
                throw new ProductInternalServerError("no sql server error while getting pricing " +
                        "information " + productId);
            }
            prices.add(price);
        }
        LOGGER.debug("Total prices found: {}", prices.size());
        return prices;
    }
}
