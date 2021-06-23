/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import { Component, OnInit, ViewChild } from '@angular/core';
import { AnalyticsService } from './@core/utils/analytics.service';
import { isNullOrUndefined } from 'util';
import { Router } from '@angular/router';
import { MatPaginator } from '@angular/material';

@Component({
  selector: 'ngx-app',
  template: '<router-outlet></router-outlet>',
})
export class AppComponent implements OnInit {

  constructor(private router: Router,private analytics: AnalyticsService) {
  }
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  ngOnInit(): void {

  }
}
