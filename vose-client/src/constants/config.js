export const defaultMenuType = 'menu-default' // 'menu-default', 'menu-sub-hidden', 'menu-hidden';
export const adminRoot = '/admin';
export const bayiRoot = '/bayi';

export const subHiddenBreakpoint = 1440
export const menuHiddenBreakpoint = 768

export const defaultLocale = 'tr'
export const defaultDirection = 'ltr'
export const localeOptions = [
  { id: 'tr', name: 'Türkçe', direction: 'ltr' }
]

const SERVICE_URL = process.env.VUE_APP_SERVICE_URL;
export const serviceConfig = {
  url: SERVICE_URL,
  imgPrefix: SERVICE_URL+'/urun/files/',
  timeout: 5000
}

export const themeRadiusStorageKey = 'theme_radius'
export const themeSelectedColorStorageKey = 'theme_selected_color'
export const defaultColor = 'light.blueolympic'
export const colors = ['bluenavy', 'blueyale', 'blueolympic', 'greenmoss', 'greenlime', 'purplemonster', 'orangecarrot', 'redruby', 'yellowgranola', 'greysteel']
