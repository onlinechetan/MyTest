package com.myretail.target.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.eq;

/**
 * Data access object for Product to interact with Produc Product repository.
 */
@Component
public class ProductDAO {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProductDAO.class);
    private final MongoDatabase myRetailDB;

    @Autowired
    public ProductDAO(MongoDatabase mongoDatabase) {
        this.myRetailDB = mongoDatabase;
    }

    public FindIterable<Document> getProductPricings(String productId) {
        LOGGER.info("finding pricings for product {}", productId);
        return myRetailDB.getCollection("pricing").find(eq("productId",
                productId));
    }
}
