/*
  Copyright header.
 */
package com.myretail.target.com.myretail.target.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data transfer object for product response, used for sending back client response via controller.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ProductResponse {
    private String id;
    private String name;
    //change it to List<Price> to hold multiple currency price.
    private Price currency_price;
}
