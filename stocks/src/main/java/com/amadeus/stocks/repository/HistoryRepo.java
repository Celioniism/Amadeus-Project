package com.amadeus.stocks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amadeus.stocks.entity.StockHistory;

public interface HistoryRepo extends JpaRepository<StockHistory,String>{
	@Query(value = "SELECT * FROM Stock_History WHERE date_time = ?1 AND symbol_history = ?2", nativeQuery = true)
	StockHistory findByDateTicker(String date, String Ticker);

	@Query(value = "SELECT * FROM Stock_History WHERE date_time>= ?1", nativeQuery = true)
	List<StockHistory> findByDate(String date);
}
