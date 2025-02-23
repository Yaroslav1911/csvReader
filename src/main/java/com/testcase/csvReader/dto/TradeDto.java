package com.testcase.csvReader.dto;

import java.util.UUID;

public record TradeDto(
        UUID uuid,
        String date,
        String prodId,
        String currency,
        Double price
) {
}
