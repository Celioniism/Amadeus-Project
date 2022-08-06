package com.amadeus.stocks.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int HistoryId;
	private String type;
	private String dateTime;
	private String SymbolHistory;
	private String CompanyNameHistory;
	private float priceHistory;
}
