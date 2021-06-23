import { Component } from '@angular/core';
import * as moment from 'moment';
@Component({
  selector: 'ngx-footer',
  styleUrls: ['./footer.component.scss'],
  template: `
    
    
  `,
})
export class FooterComponent {
  date = moment(new Date()).format('YYYY');
  

}
