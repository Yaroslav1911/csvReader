package com.testcase.csvReader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Trade {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String date;
    private String prodId;
    private String currency;
    private Double price;

    public Trade(String date, String prodId, String currency, Double price) {
        this.date = date;
        this.prodId = prodId;
        this.currency = currency;
        this.price = price;
    }

    public Trade() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "price=" + price +
                ", currency='" + currency + '\'' +
                ", prodId='" + prodId + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
