package com.bist.stocktrack.controller;

import com.bist.stocktrack.service.StockApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final StockApiService apiService;

    @GetMapping("/stock-info")
    public ResponseEntity<String> getStockInfo() {
        String stockInfo = apiService.getStockInfo();

        if (stockInfo != null) {
            return ResponseEntity.ok(stockInfo);
        } else {
            return ResponseEntity.status(500).body("Failed to fetch stock info.");
        }
    }
}
