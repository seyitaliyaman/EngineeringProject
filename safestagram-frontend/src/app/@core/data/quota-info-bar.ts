import { Observable } from 'rxjs';

export interface QuotaInfo {
  title: string;
  value: number;
  activeProgress: number;
  // description: string;
}

export abstract class QuotaBarData {
  abstract getQuotaInfoData(): Observable<QuotaInfo[]>;
}