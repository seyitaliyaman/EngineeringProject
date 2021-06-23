import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';
import { ThemeModule } from '../@theme/theme.module';
import { PagesComponent } from './pages.component';
import { PagesRoutingModule } from './pages-routing.module';
import { TimelineModule } from './timeline/timeline.module';
import { SearchComponent } from './search/search.component';
import { SearchModule } from './search/search.module';
import { ProfileComponent } from './profile/profile.component';
import { SettingsComponent } from './settings/settings.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ProfileModule } from './profile/profile.module';
import { SettingsModule } from './settings/settings.module';
import { UserProfileModule } from './user-profile/user-profile.module';
import { PostModule } from './post/post.module';
import { AddPhotoComponent } from './add-photo/add-photo.component';
import { AddPhotoModule } from './add-photo/add-photo.module';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    TimelineModule,
    SearchModule,
    ProfileModule,
    SettingsModule,
    UserProfileModule,
    PostModule,
    AddPhotoModule
  ],
  declarations: [
    PagesComponent,
  ],
  exports : [
  ],
})
export class PagesModule {
}
