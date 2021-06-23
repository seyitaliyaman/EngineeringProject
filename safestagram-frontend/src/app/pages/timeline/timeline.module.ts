import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimelineComponent } from './timeline.component';
import {MatCardModule} from '@angular/material/card'
import { MatButtonModule } from '@angular/material';


@NgModule({
  declarations: [TimelineComponent],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
  ]
})
export class TimelineModule { }
