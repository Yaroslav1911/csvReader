package com.testcase.csvReader.csvmapper;

import com.opencsv.bean.CsvBindByName;

public class TradeCsvMapper {

    @CsvBindByName(column = "date")
    private String mDate;
    @CsvBindByName(column = "productId")
    private String mProdId;
    @CsvBindByName(column = "currency")
    private String mCurrency;
    @CsvBindByName(column = "price")
    private Double mPrice;

    public TradeCsvMapper(String mDate, String mProdId, String mCurrency, Double mPrice) {
        this.mDate = mDate;
        this.mProdId = mProdId;
        this.mCurrency = mCurrency;
        this.mPrice = mPrice;
    }

    public TradeCsvMapper() {
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmProdId() {
        return mProdId;
    }

    public void setmProdId(String mProdId) {
        this.mProdId = mProdId;
    }

    public String getmCurrency() {
        return mCurrency;
    }

    public void setmCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public Double getmPrice() {
        return mPrice;
    }

    public void setmPrice(Double mPrice) {
        this.mPrice = mPrice;
    }
}
