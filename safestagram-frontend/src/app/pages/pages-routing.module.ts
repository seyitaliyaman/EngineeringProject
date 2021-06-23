import { RouterModule, Routes } from '@angular/router';
import { NgModule, Component } from '@angular/core';
import { PagesComponent } from './pages.component';
import { TimelineComponent } from './timeline/timeline.component';
import { SearchComponent } from './search/search.component';
import { SettingsComponent } from './settings/settings.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ProfileComponent } from './profile/profile.component';
import { PostComponent } from './post/post.component';
import { AddPhotoComponent } from './add-photo/add-photo.component';


const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'timeline',
      component: TimelineComponent
    },
    {
      path: 'search',
      component: SearchComponent
    },
    {
      path: 'settings',
      component: SettingsComponent
    },
    {
      path: 'userprofile',
      component: UserProfileComponent
    },
    {
      path: 'profile/:id',
      component: ProfileComponent
    },
    {
      path: 'post/:userId/:id',
      component: PostComponent
    },
    {
      path: 'addPhoto',
      component: AddPhotoComponent
    }
    
    // {
    //   path: 'home',
    //   component: HomeComponent,
    // },
    // {
    //   path : 'company',
    //   component: CompanyComponent,
    // },
    // {
    //   path : 'users',
    //   component : UsersComponent,
    // },
    // {
    //   path : 'settings',
    //   loadChildren : () => import('./settings/settings.module').then(m => m.SettingsModule),
    // },
    // {
    //   path: '**',
    //   component: NotFoundComponent,
    // },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
