package com.amadeus.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amadeus.stocks.entity.StocksClass;

public interface StockRepo extends JpaRepository<StocksClass,String>{

}
