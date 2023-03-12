import { adminRoot } from "./config";
import { bayiRoot } from "./config";

export const adminMenu = [
  {
    id: "genel-bakis",
    icon: "iconsminds-digital-drawing",
    label: "menu.genel-bakis",
    to: `${adminRoot}/genel-bakis`,
  },
  {
    id: "site-yonetimi",
    icon: "iconsminds-chemical",
    label: "menu.site-yonetimi",
    to: `${adminRoot}/site-yonetimi`,
    subs: [
      /*{
        icon: "simple-icon-paper-plane",
        label: "menu.genel-ayarlar",
        to: `${adminRoot}/site-yonetimi/genel-ayarlar`,
      },*/
      {
        icon: "simple-icon-paper-plane",
        label: "menu.iletisim-ayarlari",
        to: `${adminRoot}/site-yonetimi/iletisim-ayarlari`,
      },
      {
        icon: "simple-icon-paper-plane",
        label: "menu.sozlesmeler",
        to: `${adminRoot}/site-yonetimi/sozlesmeler`,
      },
      {
        icon: "simple-icon-paper-plane",
        label: "menu.banka-hesaplari",
        to: `${adminRoot}/site-yonetimi/banka-hesaplari`,
      },
      {
        icon: "simple-icon-paper-plane",
        label: "menu.sss",
        to: `${adminRoot}/site-yonetimi/sss`,
      },
      {
        icon: "simple-icon-paper-plane",
        label: "menu.kargo-yonetimi",
        to: `${adminRoot}/site-yonetimi/kargo-yonetimi`,
      },
      /*{
        icon: "simple-icon-paper-plane",
        label: "menu.haberler",
        to: `${adminRoot}/site-yonetimi/haberler`,
      }*/
    ]
  },
  {
    id: "urun-yonetimi",
    icon: "iconsminds-shopping-bag",
    label: "menu.urun-yonetimi",
    to: `${adminRoot}/urun-yonetimi`,
    subs: [
      {
        icon: "simple-icon-paper-plane",
        label: "menu.urunler",
        to: `${adminRoot}/urun-yonetimi/urunler`,
      },
      {
        icon: "simple-icon-paper-plane",
        label: "menu.kategoriler",
        to: `${adminRoot}/urun-yonetimi/kategoriler`,
      },
    ]
  },
  {
    id: "kariyer-yonetimi",
    icon: "iconsminds-medal",
    label: "menu.kariyer-yonetimi",
    to: `${adminRoot}/kariyer-yonetimi`,
  },
  {
    id: "bayi-yonetimi",
    icon: "iconsminds-business-man-woman",
    label: "menu.bayi-yonetimi",
    to: `${adminRoot}/bayi-yonetimi`,
  },
  {
    id: "kazanc-yonetimi",
    icon: "iconsminds-euro",
    label: "menu.kazanc-yonetimi",
    to: `${adminRoot}/kazanc-yonetimi`,
  },
  {
    id: "siparis-yonetimi",
    icon: "iconsminds-checkout",
    label: "menu.siparis-yonetimi",
    to: `${adminRoot}/siparis-yonetimi`,
    subs: [
      {
        icon: "iconsminds-add-cart",
        label: "menu.onay-bekleyen-siparis",
        to: `${adminRoot}/siparis-yonetimi/onay-bekleyen`,
      },
      {
        icon: "iconsminds-receipt-4",
        label: "menu.siparisler",
        to: `${adminRoot}/siparis-yonetimi/tum-siparisler`,
      },
    ]
  },
  {
    id: "odeme-yonetimi",
    icon: "iconsminds-money-bag",
    label: "menu.odeme-yonetimi",
    to: `${adminRoot}/odeme-yonetimi`,
  },
];

export const bayiMenu =[
  {
    id: "kariyerim",
    icon: "iconsminds-digital-drawing",
    label: "menu.kariyerim",
    to: `${bayiRoot}/kariyerim`,
  },
  {
    id: "puanlarim",
    icon: "iconsminds-bar-chart-4",
    label: "menu.puanlarim",
    to: `${bayiRoot}/puanlarim`,
  },
  {
    id: "siparislerim",
    icon: "iconsminds-checkout",
    label: "menu.siparislerim",
    to: `${bayiRoot}/siparislerim`,
  },
  {
    id: "ekibim",
    icon: "iconsminds-business-man-woman",
    label: "menu.ekibim",
    to: `${bayiRoot}/ekibim`,
  },
  {
    id: "kazanclarim",
    icon: "iconsminds-euro",
    label: "menu.kazanclarim",
    to: `${bayiRoot}/kazanclarim`,
  },
  {
    id: "cuzdanim",
    icon: "iconsminds-wallet",
    label: "menu.cuzdanim",
    to: `${bayiRoot}/cuzdanim`,
  },
];
