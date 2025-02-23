package com.testcase.csvReader.service;

import com.testcase.csvReader.dto.TradeDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeServiceTest {

    TradeService service = Mockito.mock(TradeService.class);
    MultipartFile file = Mockito.mock(MultipartFile.class);

    List<TradeDto> list = List.of(
            new TradeDto(
                    UUID.randomUUID(),
                    "20120120",
                    "prodName1",
                    "UAH",
                    4.25d
            ),
            new TradeDto(
                    UUID.randomUUID(),
                    "20120121",
                    "prodName2",
                    "UAH",
                    5.25d
            ),
            new TradeDto(
                    UUID.randomUUID(),
                    "20120122",
                    "prodName3",
                    "UAH",
                    6.25d
            )
    );

    @Test
    void getProductNameById() {
        Mockito.when(service.getProductNameById("1")).thenReturn("testName");

        String testName = service.getProductNameById("1");

        assertEquals(testName, "testName");
    }

    @Test
    void getTradesList() throws IOException {
        Mockito.when(service.getTradesList(file)).thenReturn(list);

        List<TradeDto> tradeDtoList = service.getTradesList(file);

        assertEquals(tradeDtoList.size(), 3);
        assertTrue(tradeDtoList.containsAll(list));
    }

    @Test
    void enrich() throws IOException {
        Mockito.when(service.enrich(file)).thenReturn(String.valueOf(buildTheString(list)));

        String expecting = service.enrich(file);

        Mockito.verify(service).enrich(file);
        assertEquals(expecting, String.valueOf(buildTheString(list)));
    }

    private static StringBuilder buildTheString(List<TradeDto> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("date,productName,currency,price\n");

        list.forEach(trade -> stringBuilder
                .append(trade.date()).append(",")
                .append(trade.prodId()).append(",")
                .append(trade.currency()).append(",")
                .append(trade.price()).append("\n"));
        return stringBuilder;
    }
}