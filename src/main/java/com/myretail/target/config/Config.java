package com.myretail.target.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Configuration used across by services.
 */
@Configuration
public class Config {

    /**
     * Instantiate retail database.
     *
     * @return instance of MongoDatabase
     */
    @Bean
    MongoDatabase retailDB() {
        return new MongoClient("localhost", 27017).getDatabase("myRetail");
    }

    /**
     * Instantiate httpclient
     *
     * @return instance of HttpClient
     */
    @Bean
    HttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }

    /**
     * Instantiate executor service
     *
     * @return instance of ExecutorService
     */
    @Bean
    ExecutorService executorService() {
        return Executors.newSingleThreadExecutor();
    }
}
