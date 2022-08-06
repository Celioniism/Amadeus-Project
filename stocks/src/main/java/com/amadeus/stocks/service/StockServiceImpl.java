package com.amadeus.stocks.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amadeus.stocks.entity.StockHistory;
import com.amadeus.stocks.entity.StocksClass;
import com.amadeus.stocks.repository.HistoryRepo;
import com.amadeus.stocks.repository.StockRepo;

@Service
public class StockServiceImpl implements StockService {
	// variables
	private int crash = 0;
	private boolean open;

	// tools
	private static String getYesterday() { // gets yesterday's date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
		String yest = formatter.format(yesterday);
		return yest;
	}

	private static String retrieveDate() { // gets today's date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}

	private static String retrieveGenDate() { // gets general day information
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}

	private List<StockHistory> closingPrice(List<StockHistory> stocks) { // finds the most relevant price information
																			// from yesterday (closing price)
		// for the program to calculate changes
		List<StockHistory> closing = new ArrayList<>();
		Map<String, String> mappedTime = new HashMap<String, String>();
		for (StockHistory SH : stocks) {
			mappedTime.put(SH.getSymbolHistory(), SH.getDateTime());
		}
		for (StockHistory SH : stocks) {
			for (Entry<String, String> entry : mappedTime.entrySet()) {
				if (entry.getKey().equals(SH.getSymbolHistory())) {

					DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

					LocalDateTime s = LocalDateTime.parse(entry.getValue(), f);
					LocalDateTime e = LocalDateTime.parse(SH.getDateTime(), f);
					String today = retrieveGenDate() + " 00:00:00";
					LocalDateTime gd = LocalDateTime.parse(today, f);
					if (e.isAfter(gd)) {
						boolean isAfter = s.isAfter(e);
						if (isAfter) {
							mappedTime.put(entry.getKey(), SH.getDateTime());

						}
					} else {
						boolean isBefore = s.isBefore(e);
						if (isBefore) {
							mappedTime.put(entry.getKey(), SH.getDateTime());
						}
					}
				}
			}
		}
		for (Entry<String, String> entry : mappedTime.entrySet()) { // turning map values into a list again
			StockHistory hist = HR.findByDateTicker(entry.getValue(), entry.getKey());
			closing.add(hist);
		}

		return closing;
	}

	private void Randomize() { // changes stock information recursively
		// I believe this could be refined with a base case and tuning to more
		// accurately represent the stock market
		// and prevent eventual stack overflow errors due to recursive stack allocation
		// issues that I may not have noticed.

		try {
			Thread.sleep(5000); // sleeping the current thread so application can start SQL connection before
								// searching

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		double start = System.currentTimeMillis();
		double end = 0;
		boolean isEmpty = false;

		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String open = retrieveGenDate() + " 09:00:00", close = retrieveGenDate() + " 18:00:00";
		LocalDateTime daystart = LocalDateTime.parse(open, f);
		LocalDateTime dayend = LocalDateTime.parse(close, f);
		LocalDateTime now = LocalDateTime.parse(retrieveDate(), f);
		// checks if the time is between open and close
		if (now.isAfter(daystart) && now.isBefore(dayend)) {
			List<StocksClass> fluctuators = SR.findAll();
			Random r = new Random();
			List<StockHistory> StockHist = (HR.findByDate(retrieveGenDate()));
			if (StockHist.isEmpty()) {
				this.open = true;
				isEmpty = true;
			}
			for (StocksClass Stock : fluctuators) {

				StockHistory SH = new StockHistory();

				float random = 0 + r.nextFloat() * (2 - 1);
				if (random < .3) {
					random = -random;
				} else if (random >= .3 && random < .5) {
					random = 0;
				}

				if (random > 0 && Stock.getPrice() < 10) {
					random = random * (Stock.getPrice() / 10);
				} else if (random > 0 && Stock.getPrice() < 50) { // list of random price changes to weigh out large
																	// spikes
					random = random * (Stock.getPrice() / 50);
				} else if (random > 0 && Stock.getPrice() < 100) {
					random = random * (Stock.getPrice() / 100);
				} else if (random > 0 && Stock.getPrice() > 100 && Stock.getPrice() < 200) {
					random = random * (Stock.getPrice() / 200);
				}
				if (this.crash > 0 && Stock.getPrice() > random && Stock.getChanges() > 0) {
					System.out.println(Stock.getSymbol() + " is crashing");
					random = (float) (-random * 2);

				} else if (this.crash > 0 && Stock.getChanges() > 0) {
					random = (float) (-Stock.getPrice() * .10);
					System.out.println("small number crash");

				} else if (this.crash > 0 && Stock.getChanges() < 0) {
					random = (float) (-random / 2);
				}

				if (isEmpty) {
					SH.setCompanyNameHistory(Stock.getCompanyName());
					SH.setDateTime(retrieveDate());
					SH.setPriceHistory(Stock.getPrice());
					SH.setSymbolHistory(Stock.getSymbol());
					SH.setType("Open");
					HR.save(SH);
				}
				random = random / 2;
				Stock.setPrice(Stock.getPrice() + random);
				SR.save(Stock);
			}
			isEmpty = false;
			float random2 = 0 + r.nextFloat() * (1 - 0);
			if (random2 > 0.95) {
				this.crash += 7; // 5% chance for all stocks to crash for 7 recursive loops
			}
			if (crash > 0) {
				crash--; // countdown for crash
			}
			end = System.currentTimeMillis();
			System.out.println("time to complete: " + (end - start)); // just some interval checking to see if somehow
																		// my system is too clunky
			// I fixed stock history to be easier for the program to sort through (only 2
			// intervals are recorded per day open and close, with a more)
			// normalized database I could definitely store larger amounts of history per
			// day.

			try {
				Thread.sleep(5000); // sleeping the current thread

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Randomize();
		} else if (this.open) { // closing stock data recorded.
			List<StocksClass> fluctuators = SR.findAll();
			StockHistory SH = new StockHistory();
			this.open = false;
			for (StocksClass Stock : fluctuators) {
				SH.setCompanyNameHistory(Stock.getCompanyName());
				SH.setDateTime(retrieveDate());
				SH.setPriceHistory(Stock.getPrice());
				SH.setSymbolHistory(Stock.getSymbol());
				SH.setType("Close");
				HR.save(SH);
			}
		}
	}

	private List<StocksClass> calculate(List<StockHistory> hist) {
		// calculates change and percent change between stocks and retrieves them as a
		// complete list.
		//
		List<StocksClass> Stocks = new ArrayList<>();
		for (StockHistory H : hist) {
			StocksClass sc = SR.findById(H.getSymbolHistory()).get();
			if (sc.getPrice() != H.getPriceHistory()) {
				sc.setChanges(sc.getPrice() - H.getPriceHistory());
				float change = H.getPriceHistory() - sc.getPrice();
				float percent = 0;
				if (change > 0) {
					percent = -(((H.getPriceHistory() - sc.getPrice()) / H.getPriceHistory()) * 100);
				} else {
					percent = ((sc.getPrice() - H.getPriceHistory()) / H.getPriceHistory()) * 100;
				}
				sc.setChangePercent(percent);
				SR.save(sc);
			}
			Stocks.add(sc);
		}
		return Stocks;
	}

	// autos
	@Autowired
	StockRepo SR;

	@Autowired
	HistoryRepo HR;

	// public service methods

	@Override
	public List<StocksClass> getAll() { // used to get information for users

		List<StockHistory> hist = HR.findByDate(getYesterday());

		if (!hist.isEmpty()) { // checksif yesterday had prices
			hist = closingPrice(HR.findByDate(getYesterday()));

		} else { // checks for today's first price
			hist = closingPrice(HR.findByDate(retrieveGenDate()));
			System.out.println(HR.findByDate(retrieveGenDate()));
		}
		if (hist != null)
			return calculate(hist); // calculates if history is not null
		else
			return SR.findAll();
	}

	{ // auto starting static block using a new thread.
		new Thread(new Runnable() {
			@Override
			public void run() {
				Randomize();
			}
		}).start();
	}

}
