import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPhotoComponent } from './add-photo.component';
import { MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import {TextFieldModule} from '@angular/cdk/text-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [AddPhotoComponent],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    TextFieldModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    RouterModule
  ]
})
export class AddPhotoModule { }
