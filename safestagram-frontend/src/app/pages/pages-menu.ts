import { NbMenuItem } from '@nebular/theme';

export const SuperAdminMenuItem: NbMenuItem = {
  title: 'Ayarlar',
  icon: 'settings-outline',
  children: [
    {
      title: 'Kullanıcı Yönetimi',
      link: '/pages/settings/user-management',
    },
    {
      title: 'Hesap Ayarları',
      link: '/pages/settings/account-settings',
    },
    {
      title: 'Paket Ayarları',
      link: '/pages/settings/package-settings'
    }
  ]
}

export const AdminMenuItem: NbMenuItem = {
  title: 'Ayarlar',
  icon: 'settings-outline',
  children: [
    {
      title: 'Hesap Ayarları',
      link: '/pages/settings/account-settings',
    },
  ],
}

export const HomeMenuItem: NbMenuItem = {
    title: 'Anasayfa',
    icon: 'home-outline',
    link: '/pages/home',
    home: true,
}

export const CompanyMenuItem: NbMenuItem = {
    title: 'Firma İşlemleri',
    icon: 'edit-2-outline',
    link: '/pages/company',
}

export const UserMenuItem: NbMenuItem = {
    title: 'Kullanıcı İşlemleri',
    icon: 'paper-plane-outline',
    link : '/pages/users',
}

