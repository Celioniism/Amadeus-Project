import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PagesRoutingModule } from './pages-routing.module';
import { StockPageComponent } from './stock-page/stock-page.component';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  declarations: [StockPageComponent],
  imports: [CommonModule, PagesRoutingModule, BrowserModule],
})
export class PagesModule {}
