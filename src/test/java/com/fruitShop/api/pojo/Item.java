package com.fruitShop.api.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private int quantity;
    private double price;
    @JsonProperty("item_url")
    private String itemUrl;
    @JsonProperty("product_url")
    private String productUrl;



}
