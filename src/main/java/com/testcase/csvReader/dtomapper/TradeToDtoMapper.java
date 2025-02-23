package com.testcase.csvReader.dtomapper;

import com.testcase.csvReader.dto.TradeDto;
import com.testcase.csvReader.entity.Trade;
import org.springframework.stereotype.Component;

@Component
public class TradeToDtoMapper {
    public static TradeDto toDto(Trade trade) {
        return new TradeDto(
                trade.getUuid(),
                trade.getDate(),
                trade.getProdId(),
                trade.getCurrency(),
                trade.getPrice()
        );
    }
}
