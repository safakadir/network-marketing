<template>
<b-container class="section rounded">
  <h2 class="mb-0 mt-4">{{$t('home.menu.sepet')}}</h2>
  <b-row class="mt-4">
    <b-colxx xxs="12" md="12" lg="8" class="col-left">
      <p v-if="isEmpty">
        <b-alert show variant="primary" class="rounded">{{ $t('sepet.sepetBos') }}</b-alert>
      </p>
      <urun-listing
          :items="sepetUrunler"
          displayMode="sepet"
      ></urun-listing>
    </b-colxx>
    <b-colxx xxs="12" md="12" lg="4" class="col-left">
      <b-card class="mb-4" no-body>
          <b-card-body>
            <b-badge v-if="isLogin && indirim>0" pill variant="secondary" class="position-absolute badge-top-right-2">%{{ indirim }} {{$t('common.indirim')}}</b-badge>
            <b-card-title>{{$t('sepet.sepetOzeti')}}</b-card-title>
            <b-card-text>
              <div class="ml-3">
                <p class="mb-1">{{$t('sepet.toplamUrun')}}: <strong>{{sepetUrunCount}}</strong></p>
                <template v-if="!isEmpty" >
                  <p class="mb-1">{{$t('sepet.araToplam')}}: <strong>{{sepetToplamNetIndirimli}} ₺</strong></p>
                  <p class="mb-1">{{$t('sepet.toplamKdv')}}: <strong>{{sepetToplamKdvIndirimli}} ₺</strong></p>
                  <p v-if="indirimPresent" class="mb-1">{{$t('sepet.toplamFiyatIndirimsiz')}}: <span style="text-decoration:line-through;">{{sepetToplamFiyatIndirimsiz}} ₺</span></p>
                  <p v-if="indirimPresent" class="mb-1">{{$t('sepet.toplamFiyat')}}: <strong>{{sepetToplamFiyat}} ₺</strong></p>
                  <p v-if="!indirimPresent" class="mb-1">{{$t('sepet.toplamFiyat')}}: <strong>{{sepetToplamFiyat}} ₺</strong></p>
                  <p v-if="indirimPresent" class="mb-1">{{$t('sepet.kazanciniz')}}: <strong>{{sepetKazanc}} ₺</strong></p>
                  <p class="mb-1">{{$t('sepet.kargoUcret')}}: <strong>{{sepetKargoUcret}} ₺</strong></p>
                  <p v-if="isLogin" class="mb-1">{{$t('common.pv')}}: <strong>{{sepetToplamPv}}</strong></p>
                  <p v-if="isLogin" class="mb-1">{{$t('common.cv')}}: <strong>{{sepetToplamCv}}</strong></p>
                </template>
              </div>
              <b-form-checkbox v-if="paketOptionEnabled" v-model="paketOption" :disabled="!kayitDurum">{{paketOptionText}}</b-form-checkbox>
              <b-alert show :variant="hedefAchieved ? 'info' : 'danger'" class="mt-4">
                {{$t('sepet.odenecekTutar')}}: <strong>{{isEmpty ? 0 : sepetOdenecekTutar}} ₺</strong>
                <div v-if="!hedefAchieved" class="text-small">{{$t('sepet.hedefNotAchieved')}}</div>
              </b-alert>
            </b-card-text>
            <b-button block variant="primary" @click="odemeyeGec" :disabled="isEmpty || !hedefAchieved">{{$t('sepet.odemeyeGec')}}</b-button>
          </b-card-body>
      </b-card>
    </b-colxx>
  </b-row>
</b-container>
</template>

<script>
import { mapGetters } from 'vuex'
import service from '../../service'
import UrunListing from '../../containers/urun/UrunListing'

export default {
  components: {
    "urun-listing": UrunListing,
  },
  data() { return {
    nextKariyer: null
  }},
  methods: {
    odemeyeGec() {
      this.$router.replace('/odeme')
    }
  },
  computed: {
    ...mapGetters([
      'sepetUrunler',
      'sepetCount',
      'sepetUrunCount',
      'sepetToplamNetIndirimli',
      'sepetToplamKdvIndirimli',
      'sepetToplamFiyatIndirimsiz',
      'sepetToplamFiyat',
      'sepetKargoUcret',
      'sepetOdenecekTutar',
      'sepetToplamPv',
      'sepetToplamCv',
      'bayi',
      'indirim'
    ]),
    isLogin() {
      return !!this.$store.getters.login;
    },
    indirimPresent() {
      return this.isLogin && this.indirim > 0;
    },
    sepetKazanc() {
      return (this.sepetToplamFiyatIndirimsiz-this.sepetToplamFiyat).normalize();
    },
    isEmpty() {
      return this.sepetCount == 0;
    },
    kayitDurum() {
      return this.bayi.kayitDurum;
    },
    paketOption: {
      get() {
        return this.$store.getters.isPaketAlisveris;
      },
      set(value) {
        this.$store.commit('setPaketAlisveris', value);
        this.$store.dispatch('recalculateSepet');
      }
    },
    paketOptionText() {
      return this.kayitDurum ? this.$t('sepet.paketFarkAlisverisi', {amount: this.hedefAlisveris}) : this.$t('sepet.paketAlisverisi', {amount: this.hedefAlisveris});
    },
    hedefAlisveris() {
      if(!this.nextKariyer || !this.nextKariyer.baslangicPaket) return 0;
      return this.bayi.kayitDurum ? this.nextKariyer.baslangicPaketTutar-this.bayi.kariyer.baslangicPaketTutar : this.bayi.kariyer.baslangicPaketTutar;
    },
    paketOptionEnabled() {
      return this.isLogin && (!this.bayi.kayitDurum || this.nextKariyer && this.nextKariyer.baslangicPaket);
    },
    hedefAchieved() {
      if(!this.paketOption) return true;
      return this.sepetToplamFiyat >= this.hedefAlisveris;
    }
  },
  mounted() {
    this.$store.dispatch('settings/fetch', 'kargo');
    if(this.isLogin) {
      service.get('/bayi/'+this.bayi.id+'/next')
      .then(response => {
        this.nextKariyer = response.data;
      });
      if(!this.bayi.kayitDurum) {
        this.paketOption = true;
      }
    }
  }
}
</script>
