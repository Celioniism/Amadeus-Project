package com.amadeus.stocks.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StocksClass {
	
@Id
private String Symbol;
private String CompanyName;
private float price;
private float changes;
private float changePercent;
}
