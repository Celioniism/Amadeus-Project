package com.amadeus.stocks.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeus.stocks.entity.StocksClass;
import com.amadeus.stocks.service.StockService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class StockController {

	@Autowired
	StockService SS;
	
	@GetMapping("/RetrieveStocks")
	public List<StocksClass> retrieve() {
		return SS.getAll();
	}
}
