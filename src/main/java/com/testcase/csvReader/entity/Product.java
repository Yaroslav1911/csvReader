package com.testcase.csvReader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    private Long productId;
    private String productName;

    public Product(Long productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public Product() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
