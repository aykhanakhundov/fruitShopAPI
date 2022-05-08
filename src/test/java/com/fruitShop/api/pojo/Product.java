package com.fruitShop.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private String name;
    private double price;
    @JsonProperty("category_url")
    private String categoryUrl;
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
