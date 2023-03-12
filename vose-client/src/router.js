import Vue from "vue";
import VueRouter from "vue-router";
import store from '@/store'
import { gutil } from './utils/generic'

import { adminRoot, bayiRoot } from "./constants/config";

Vue.use(VueRouter);

const odemeMw = (to, from, next) => {
  const sepetCount = localStorage.getItem('sepetCount', 0);
  if(!sepetCount) {
    next(from.path);
  }
  else {
    next();
  }
}

const odemeBasariliMw = (to, from, next) => {
  const isSavedSiparisExist = !!gutil.getProperty(store.getters.savedSiparis, 'id');
  if(isSavedSiparisExist) {
    next();
  }
  else {
    next(from.path);
  }
}

const adminCheck = (to, from, next) => {
  if(!store.getters.initCompleted || !!store.getters.admin) {
    next();
  }
  else {
    next('/login/admin');
  }
}

const bayiCheck = (to, from, next) => {
  if(!store.getters.initCompleted || !!store.getters.bayiId) {
    next();
  }
  else {
    next('/login');
  }
}

const kayitCheck = (to, from, next) => {
  if(!!to.query.sponsorId && !store.getters.bayiId) {
    next(from.path);
  }
  else if(!to.query.sponsorId && !!store.getters.bayiId) {
    next(from.path);
  }
  else {
    next();
  }
}

const routes = [
  {
    path: "/",
    component: () => import(/* webpackChunkName: "home" */ "./views/home"),
    redirect: 'home',
    children: [
      { path: 'home', component: () => import('./views/home/Home') },
      { path: 'login', component: () => import('./views/home/Login') },
      { path: 'login/admin', component: () => import('./views/home/AdminLogin') },
      { path: 'kayit', beforeEnter: kayitCheck, component: () => import('./views/home/Kayit') },
      { path: 'kayitbasarili', component: () => import('./views/home/KayitBasarili') },
      { path: 'sepet', component: () => import('./views/home/Sepet') },
      { path: 'odeme', beforeEnter: odemeMw, component: () => import('./views/home/Odeme') },
      { path: 'odemebasarili', beforeEnter: odemeBasariliMw, component: () => import('./views/home/OdemeBasarili') },
      { path: 'urunler', component: () => import('./views/home/Urunler') },
      { path: 'about', component: () => import('./views/home/VoseBilgi') },
      { path: 'about/:index', component: () => import('./views/home/VoseBilgi') },
      { path: 'sozlesme/:key', component: () => import('./views/home/Sozlesme') },
    ]
  },
  {
    path: adminRoot,
    component: () => import("./views/admin"),
    redirect: `${adminRoot}/genel-bakis`,
    beforeEnter: adminCheck,
    children: [
      {
        path: "genel-bakis",
        component: () =>
          import("./views/admin/GenelBakis")
      },
      {
        path: "site-yonetimi",
        component: () =>
          import("./views/admin/site-yonetimi"),
        redirect: `${adminRoot}/site-yonetimi/genel-ayarlar`,
        children: [
          { path: 'genel-ayarlar', component: () => import('./views/admin/site-yonetimi/genel-ayarlar') },
          { path: 'iletisim-ayarlari', component: () => import('./views/admin/site-yonetimi/iletisim-ayarlari') },
          { path: 'sozlesmeler', component: () => import('./views/admin/site-yonetimi/sozlesmeler') },
          { path: 'banka-hesaplari', component: () => import('./views/admin/site-yonetimi/banka-hesaplari') },
          { path: 'sss', component: () => import('./views/admin/site-yonetimi/sss') },
          { path: 'kargo-yonetimi', component: () => import('./views/admin/site-yonetimi/kargo-yonetimi') },
          { path: 'haberler', component: () => import('./views/admin/site-yonetimi/haberler') },
        ]
      },
      {
        path: "urun-yonetimi",
        component: () =>
          import("./views/admin/urun"),
        redirect: `${adminRoot}/urun-yonetimi/urunler`,
        children: [
          { path: 'urunler', component: () => import('./views/admin/urun/Urunler') },
          { path: 'kategoriler', component: () => import('./views/admin/urun/Kategoriler') },
        ]
      },
      {
        path: "kariyer-yonetimi",
        component: () =>
          import("./views/admin/KariyerYonetimi")
      },
      {
        path: "bayi-yonetimi",
        component: () =>
          import("./views/admin/BayiYonetimi")
      },
      {
        path: "kazanc-yonetimi",
        component: () =>
          import("./views/admin/KazancYonetimi")
      },
      {
        path: "siparis-yonetimi",
        component: () =>
          import("./views/admin/siparis"),
        redirect: `${adminRoot}/siparis-yonetimi/onay-bekleyen`,
        children: [
          { path: 'onay-bekleyen', component: () => import('./views/admin/siparis/Siparisler') },
          { path: 'tum-siparisler', component: () => import('./views/admin/siparis/Siparisler') },
        ]
      },
      {
        path: "odeme-yonetimi",
        component: () =>
          import("./views/admin/OdemeYonetimi")
      }
    ]
  },
  {
    path: bayiRoot,
    component: () => import("./views/bayi"),
    redirect: `${bayiRoot}/kariyerim`,
    beforeEnter: bayiCheck,
    children: [
      {
        path: "kariyerim",
        component: () =>
          import("./views/bayi/Kariyerim")
      },
      {
        path: "puanlarim",
        component: () =>
          import("./views/bayi/Puanlarim")
      },
      {
        path: "siparislerim",
        component: () =>
          import("./views/bayi/Siparislerim")
      },
      {
        path: "ekibim",
        component: () =>
          import("./views/bayi/Ekibim")
      },
      {
        path: "kazanclarim",
        component: () =>
          import("./views/bayi/Kazanclarim")
      },
      {
        path: "cuzdanim",
        component: () =>
          import("./views/bayi/Cuzdanim")
      },
    ]
  },
  {
    path: "/error",
    component: () => import(/* webpackChunkName: "error" */ "./views/Error")
  },
  {
    path: "*",
    component: () => import(/* webpackChunkName: "error" */ "./views/Error")
  }
];

const router = new VueRouter({
  linkActiveClass: "active",
  routes,
  mode: "history"
});

export default router;
