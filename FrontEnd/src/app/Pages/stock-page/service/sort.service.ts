import { Injectable } from '@angular/core';
import { StockClass } from 'src/app/Entity/StockClass';

@Injectable({
  providedIn: 'root',
})
export class SortService {
  constructor() {}
  sortByTicker(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.symbol > b.symbol ? 1 : -1));
  }
  sortByName(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.companyName > b.companyName ? 1 : -1));
  }

  sortByPrice(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.price > b.price ? 1 : -1));
  }

  sortByChange(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.changes > b.changes ? 1 : -1));
  }

  sortByChangePercent(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.changePercent > b.changePercent ? 1 : -1));
  }

  sortByTickerD(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.symbol > b.symbol ? -1 : 1));
  }
  sortByNameD(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.companyName > b.companyName ? -1 : 1));
  }

  sortByPriceD(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.price > b.price ? -1 : 1));
  }

  sortByChangeD(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.changes > b.changes ? -1 : 1));
  }

  sortByChangePercentD(Stocks: StockClass[]): StockClass[] {
    return Stocks.sort((a, b) => (a.changePercent > b.changePercent ? -1 : 1));
  }
}
