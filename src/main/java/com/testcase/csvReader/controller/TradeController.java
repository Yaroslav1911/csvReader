package com.testcase.csvReader.controller;

import com.testcase.csvReader.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping(value = "/enrich", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadFile(
            @RequestPart("file")MultipartFile file
            ) throws IOException {
        return ResponseEntity.ok(tradeService.enrich(file));
    }
}
