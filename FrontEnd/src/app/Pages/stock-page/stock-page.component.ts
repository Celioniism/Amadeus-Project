import { NONE_TYPE } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { StockClass } from 'src/app/Entity/StockClass';
import { SortService } from './service/sort.service';
import { StockService } from './service/stock.service';

@Component({
  selector: 'app-stock-page',
  templateUrl: './stock-page.component.html',
  styleUrls: ['./stock-page.component.css'],
})
export class StockPageComponent implements OnInit {
  Stocks: StockClass[];
  Colors = new Array<String>;
  sortType: string = 'none';
  SortType: number = 0;
  change: boolean = false;
  constructor(private stockService: StockService, private sortService: SortService) {}

  ngOnInit(): void {
    this.stockService.loadStocks().subscribe((response) => {
      this.Stocks = response;
      this.colors();
      setInterval(() => {
        this.stockService.loadStocks().subscribe((response) => {
          this.Stocks = response;
          this.colors();
          this.doSort();
        });
      }, 3000);
    });
  }
  colors() {
    for (let i = 0; i < this.Stocks.length; i++) {
      if (this.Stocks[i].changes < 0) {
        this.Colors[i] = 'red';
      } else if (this.Stocks[i].changes > 0) {
        this.Colors[i] = 'green';
      } else {
        this.Colors[i] = 'black';
      }
    }
  }
  ticker(){
    if(this.SortType < 1){
    this.change = true;
    }
    this.sortType = 'ticker';
    this.sortTypeChange();
    this.doSort();
  }

  name(){
    if(this.SortType < 1){
    this.change = true;
    }
    this.sortType = 'name';
    this.sortTypeChange();
    this.doSort();
  }

  price(){
    if(this.SortType < 1){
    this.change = true;
    }
    this.sortType = 'price';
    this.sortTypeChange();
    this.doSort();
  }

  changes(){
    if(this.SortType < 1){
    this.change = true;
    }
    this.sortType = 'change';
    this.sortTypeChange();
    this.doSort();
  }

  changePercents(){
    if(this.SortType < 1){
    this.change = true;
    }
    this.sortType = 'changepercent';
    this.sortTypeChange();
    this.doSort();
  }
  sortTypeChange(){
    if(this.change == true){
      this.SortType = 1;
      this.change = false;
    }else if(this.SortType == 1){
      this.SortType = 2;
    }else if(this.SortType == 2){
      this.SortType = 0;
      this.change = false;
    }
  }



  doSort(){
    if(this.SortType == 1){
      this.upSorter();
    }else if(this.SortType == 2){
      this.downSorter();
    }else{
    }
  }
  upSorter(){
    if(this.sortType == 'none'){
      
    } else if(this.sortType == 'ticker'){
      this.Stocks = this.sortService.sortByTicker(this.Stocks);
    }else if(this.sortType == 'name'){
      this.Stocks = this.sortService.sortByName(this.Stocks);
    }else if(this.sortType == 'price'){
      this.Stocks = this.sortService.sortByPrice(this.Stocks);
    }else if(this.sortType == 'change'){
      this.Stocks = this.sortService.sortByChange(this.Stocks);
    }else if(this.sortType == 'changepercent'){
      this.Stocks = this.sortService.sortByChangePercent(this.Stocks);
    } 
}

downSorter(){
    if(this.sortType == 'none'){
      
    } else if(this.sortType == 'ticker'){
      this.Stocks = this.sortService.sortByTickerD(this.Stocks);
    }else if(this.sortType == 'name'){
      this.Stocks = this.sortService.sortByNameD(this.Stocks);
    }else if(this.sortType == 'price'){
      this.Stocks = this.sortService.sortByPriceD(this.Stocks);
    }else if(this.sortType == 'change'){
      this.Stocks = this.sortService.sortByChangeD(this.Stocks);
    }else if(this.sortType == 'changepercent'){
      this.Stocks = this.sortService.sortByChangePercentD(this.Stocks);
    }
}
}
