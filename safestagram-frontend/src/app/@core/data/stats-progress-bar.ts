import { Observable } from 'rxjs';

export interface ProgressInfo {
  title: string;
  value: string;
  activeProgress: number;
  // description: string;
}

export abstract class StatsProgressBarData {
  abstract getProgressInfoData(items: any): Observable<ProgressInfo[]>;
}
