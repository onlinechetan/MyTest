/*
  Copy right header.
 */
package com.myretail.target.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.myretail.target.dao.ProductDAO;
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

/**
 * Service to interact with no sql repository.
 */
@Service
public class NoSqlServiceImpl implements NoSqlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoSqlServiceImpl.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final ProductDAO productDAO;

    @Autowired
    public NoSqlServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<Price> getProductPricing(String productId) {
        LOGGER.info("product id {}", productId);
        List<Price> prices = new ArrayList<>();

        FindIterable<Document> pricings = productDAO.getProductPricings(productId);
        for (Document pricing : pricings) {
            try {
                prices.add(mapper.readValue(pricing.toJson(), Price.class));
            } catch (IOException e) {
                throw new ProductInternalServerError("no sql server error while getting pricing " +
                        "information " + productId);
            }
        }
        LOGGER.debug("Total prices found: {}", prices.size());
        return prices;
    }
}
