import { Component, OnInit, OnDestroy } from '@angular/core';


import { HttpHeaders } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { NbMenuItem } from '@nebular/theme';
import { HomeMenuItem, CompanyMenuItem, UserMenuItem, SuperAdminMenuItem, AdminMenuItem } from './pages-menu';

@Component({
  selector: 'ngx-pages',
  styleUrls: ['pages.component.scss'],
  template: `
    <ngx-one-column-layout>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})

export class PagesComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];

  headers = new HttpHeaders({
    'Authorization': localStorage.getItem('aToken'),
  });

  constructor() { }
  menu: NbMenuItem[] = [];
  con = false;


  ngOnInit() {

    
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }




}
