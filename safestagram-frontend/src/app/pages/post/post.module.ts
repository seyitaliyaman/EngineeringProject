import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostComponent } from './post.component';
import { MatButtonModule, MatCardModule } from '@angular/material';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [PostComponent],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    RouterModule
  ]
})
export class PostModule { }
