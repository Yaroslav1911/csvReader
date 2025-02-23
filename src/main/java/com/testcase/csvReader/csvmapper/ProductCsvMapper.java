package com.testcase.csvReader.csvmapper;

import com.opencsv.bean.CsvBindByName;

public class ProductCsvMapper {

    @CsvBindByName(column = "productId")
    private Long prodId;
    @CsvBindByName(column = "productName")
    private String prodName;

    public ProductCsvMapper(Long prodId, String prodName) {
        this.prodId = prodId;
        this.prodName = prodName;
    }

    public ProductCsvMapper() {
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}
