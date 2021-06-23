import { Component, OnDestroy, OnInit } from '@angular/core';
import { NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService } from '@nebular/theme';
import { LayoutService } from '../../../@core/utils';
import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../../@core/mock/auth.service';

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;

  image = require('./account_circle.png');

  user: any = {
    name: '',
    picture: this.image,
  } ;


  themes = [
    {
      value: 'default',
      name: 'Light',
    },
    {
      value: 'dark',
      name: 'Dark',
    },
    {
      value: 'cosmic',
      name: 'Cosmic',
    },
    {
      value: 'corporate',
      name: 'Corporate',
    },
  ];

  currentTheme = 'default';

  userMenu = [ { title: 'Settings' }, { title: 'Logout' } ];
  dialogService: any;
  http: any;
  headers = new HttpHeaders({
    'Authorization': localStorage.getItem('token')});

   constructor(
               private menuService: NbMenuService,
               private themeService: NbThemeService,
               private breakpointService: NbMediaBreakpointsService,
               private router: Router,
               private authService: AuthService
               ) {
   }



  ngOnInit() {



    this.currentTheme = this.themeService.currentTheme;

    this.menuService.onItemClick().subscribe(( event ) => {
      this.onItemSelection(event.item.title);
    });

    const { xl } = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);

    this.themeService.onThemeChange()
      .pipe(
        map(({ name }) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName);
  }

  onItemSelection( title ) {
    if ( title === 'Settings' ) {

       
    } else if ( title === 'Logout' ) {
      this.authService.logout(this.headers)
      localStorage.removeItem('token');
      localStorage.clear();
      this.router.navigate(['/login']);
    }
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
    location.reload();
  }

  search(){
    this.router.navigate(['/pages/search']);
  }

  addPost(){
    this.router.navigate(['/pages/addPhoto']);
  }
}
