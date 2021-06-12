package com.randikalakmal.supplierservice.controller;


import com.randikalakmal.supplierservice.dto.StockRequest;
import com.randikalakmal.supplierservice.dto.StockUpdateRequest;
import com.randikalakmal.supplierservice.model.Stock;
import com.randikalakmal.supplierservice.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier/stock")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/all")
    public ResponseEntity<List<Stock>> getAllStock(){
        List<Stock> stockList = stockService.getAllStock();
        return new ResponseEntity<>(stockList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable("id") Integer id){
        Stock stock = stockService.getStockById(id);
        return new ResponseEntity<>(stock,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Stock> addStock(@RequestBody StockRequest stockRequest){
        Stock stock = stockService.addStock(stockRequest);
        return new ResponseEntity<>(stock,HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<Stock> updateStock(@RequestBody StockUpdateRequest stockUpdateRequest){
        Stock stock =stockService.updateStock(stockUpdateRequest);
        return new ResponseEntity<>(stock,HttpStatus.OK);

    }

}
