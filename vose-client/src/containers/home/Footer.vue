<template>
<div class="section footer mb-0" id="sectionFooter">
  <div class="container">
    <b-row class="footer-row" style="color: #fff;">
      <b-colxx xxs="12" sm="6" lg="3" class="mb-5 footer-menu">
        <ul>
          <li v-if="bayi"><a :href="bayiRoot">{{$t('home.menu.bayiPaneli')}}</a></li>
          <li v-if="!bayi && !admin"><a :href="bayiRoot">{{$t('login.title')}}</a></li>
          <li><a href="/urunler">{{$t('home.tumUrunler')}}</a></li>
          <li><a href="/sepet">{{$t('home.sepet')}}</a></li>
          <li><a href="/sss">{{$t('home.sssLong')}}</a></li>
          <li><a href="/about">{{$t('home.hakkimizda')}}</a></li>
        </ul>
      </b-colxx>
      <b-colxx xxs="12" sm="6" lg="3" class="mb-5 footer-menu">
        <ul>
          <li><a href="/sozlesme/kayit">{{$t('yonetim.sozlesme.kayit')}}</a></li>
          <li><a href="/sozlesme/gizlilik">{{$t('yonetim.sozlesme.gizlilik')}}</a></li>
          <li><a href="/sozlesme/mesafeliSatis">{{$t('yonetim.sozlesme.mesafeliSatis')}}</a></li>
          <li><a href="/sozlesme/iade">{{$t('yonetim.sozlesme.iade')}}</a></li>
        </ul>

      </b-colxx>
      <b-colxx xxs="12" sm="6" lg="3" class="mb-5 footer-menu">
        <ul>
          <li><a href="/about/1">Vose Hakkında</a></li>
          <li><a href="/about/2">Misyonumuz ve Vizyonumuz</a></li>
          <li><a href="/about/3">Neden VoseNetwork?</a></li>
          <li><a href="/about/4">Vose İş Modeli</a></li>
          <li><a href="/about/5">Vose Yenilikçi Ürünler</a></li>
          <li><a href="/about/6">Ödeme Planı</a></li>
        </ul>

      </b-colxx>
      <b-colxx xxs="12" sm="6" lg="3" class="mb-5 footer-menu">
        <div style="padding-left: 40px;">
          <strong>{{$t('yonetim.telefon')}}</strong>: {{iletisimTelefon}}<br/>
          <strong>{{$t('yonetim.email')}}</strong>: {{iletisimEmail}}<br/>
          <strong>{{$t('yonetim.lokasyon')}}</strong>: {{iletisimLokasyon}}<br/>
          <strong>{{$t('yonetim.calismaSaatleri')}}</strong>: {{iletisimCalismaSaatleri}}
        </div>
      </b-colxx>
      <div class="col-12 text-center">
        <img class="footer-logo" alt="footer logo" src="/assets/logos/black.svg" />
      </div>
    </b-row>
  </div>
  <div class="container copyright pt-5 pb-5">
    <div class="row">
      <div class="col-12"></div>
      <div class="col-12 text-center">
        <p class="mb-0">2020 © Vose</p>
        <p class="mb-0 text-small">v{{version}}</p>
      </div>
    </div>
  </div>
  <div v-if="cerezAlert"
    class="d-flex justify-content-between position-fixed fixed-bottom border"
    style="background-color:white; z-index:2000; padding:5px;"
  >
    <div>
    Sitemizden en iyi şekilde faydalanabilmeniz için çerezler kullanılmaktadır. Sitemize giriş yaparak çerez kullanımını kabul etmiş sayılıyorsunuz. &nbsp;
    <a href="/sozlesme/gizlilik">Daha fazla bilgi</a>
    </div>
    <a href="#" @click.prevent="cerezAlert = false" ><i class="iconsminds-close" /></a>
  </div>
</div>
</template>

<script>
import { mapGetters } from "vuex";
import { bayiRoot, adminRoot } from '@/constants/config'

export default {
  data() { return {
    cerezAlert: true,
    bayiRoot,
    adminRoot
  }},
  methods: {
  },
  computed: {
    ...mapGetters(['bayi', 'admin']),
    version() {
      return this.$store.getters.version;
    },
    iletisimTelefon() {
        return this.$store.getters['settings/getByName']('iletisimTelefon');
    },
    iletisimEmail() {
        return this.$store.getters['settings/getByName']('iletisimEmail');
    },
    iletisimLokasyon() {
        return this.$store.getters['settings/getByName']('iletisimLokasyon');
    },
    iletisimCalismaSaatleri() {
        return this.$store.getters['settings/getByName']('iletisimCalismaSaatleri');
    },
  },
}
</script>
