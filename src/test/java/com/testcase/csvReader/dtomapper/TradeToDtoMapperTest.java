package com.testcase.csvReader.dtomapper;

import com.testcase.csvReader.dto.TradeDto;
import com.testcase.csvReader.entity.Trade;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TradeToDtoMapperTest {

    Trade trade = new Trade(
            "20250223",
            "154",
            "UAH",
            40d
    );
    TradeDto DTO = new TradeDto(
            UUID.randomUUID(),
            "20250223",
            "154",
            "UAH",
            40d
    );

    @Test
    void toDto() {
        TradeDto tradeDto = TradeToDtoMapper.toDto(trade);

        assertEquals(tradeDto.date(), DTO.date());
        assertEquals(tradeDto.prodId(), DTO.prodId());
        assertEquals(tradeDto.currency(), DTO.currency());
        assertEquals(tradeDto.price(), DTO.price());
    }
}