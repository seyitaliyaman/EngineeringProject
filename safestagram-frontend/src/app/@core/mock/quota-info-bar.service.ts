import { Injectable } from '@angular/core';
import { of as observableOf, Observable } from 'rxjs';
import { QuotaInfo, QuotaBarData } from '../data/quota-info-bar';

@Injectable()
export class QuotaBarService extends QuotaBarData {
  private quotaInfoData: QuotaInfo[] = [
    {
      title: 'Kep Kalan Kota :',
      value: 12345,
      activeProgress: 65,
      // description: 'Better than last week (70%)',
    },
    {
      title: 'Kep Kota 2 :',
      value: 123,
      activeProgress: 40,
      // description: 'Better than last week (30%)',
    },
  ];

  getQuotaInfoData(): Observable<QuotaInfo[]> {
    return observableOf(this.quotaInfoData);
  }
}
