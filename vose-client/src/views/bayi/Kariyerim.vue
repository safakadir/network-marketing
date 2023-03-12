<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.kariyerim')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12" md="6">
      <div class="icon-cards-row rounded d-flex">
        <b-card class="mb-4 mr-4 text-center" style="width:500px;">
          <i class="iconsminds-medal"/>
          <p class="card-text font-weight-semibold mb-0">{{ $t('kariyer.currentKariyer') }}</p>
          <h2>{{ currentKariyer.kariyerAdi }}</h2>
        </b-card>
        <b-card class="mb-4 text-center" style="width:250px;">
          <i class="iconsminds-trophy"/>
          <p class="card-text font-weight-semibold mb-0">{{ $t('kariyer.nextKariyer') }}</p>
          <p class="lead font-weight-semibold text-center">{{ nextKariyer.kariyerAdi }}</p>
        </b-card>
      </div>
    </b-colxx>
    <b-colxx xxs="12" md="6" class="rounded">
      <gradient-with-radial-progress-card
        v-if="!nextKariyer.baslangicPaket"
        icon="iconsminds-upgrade"
        :title="$t('bayi.toNextKariyer')"
        :detail="puanDetail"
        :percent="percent"
        :progressText="'%'+percent"
      />
      <gradient-with-radial-progress-card
        v-else-if="bayi.kayitDurum"
        icon="iconsminds-upgrade"
        :title="$t('bayi.paketYukselt')"
        :detail="$t('bayi.farkAlisverisi', {amount: paketFark})"
        :percent="100"
        :progressText="paketFark+' ₺'"
      />
      <gradient-with-radial-progress-card
        v-else
        icon="iconsminds-upgrade"
        :title="$t('bayi.kayitTamamla')"
        :detail="$t('bayi.paketAlisverisi', {amount: bayi.kariyer.baslangicPaketTutar})"
        :percent="100"
        :progressText="bayi.kariyer.baslangicPaketTutar+' ₺'"
      />
    </b-colxx>
    <b-colxx xxs="12" class="mt-5">
      <div class="icon-cards-row rounded d-flex">
        <b-card class="mb-4 mr-4 text-center" style="width:500px;">
          <i class="iconsminds-pulse"/>
          <p class="card-text font-weight-semibold mb-0">{{ $t('bayi.buAyAktiflik') }}</p>
          <p class="lead font-weight-semibold text-center">{{ formatAktiflik(bayi) }}</p>
        </b-card>
        <b-card v-if="!bayi.kayitDurum" class="mb-4 text-center" style="width:250px;">
          <i class="iconsminds-over-time-2"/>
          <p class="card-text font-weight-semibold mb-0">{{ $t('bayi.kayitDurum') }}</p>
          <p class="lead font-weight-semibold text-center">{{ $t('bayi.notCompleted') }}</p>
        </b-card>
      </div>
    </b-colxx>
  </b-row>
  <div class="loading" v-if="loading" />
  </div>
</template>

<style>
.kariyer-card {
  width: 25%;
}
</style>

<script>
import { mapGetters } from 'vuex';
import service from '@/service';
import IconCard from "@/components/Cards/IconCard";
import GradientWithRadialProgressCard from "@/components/Cards/GradientWithRadialProgressCard";

export default {
  components: {
    "icon-card": IconCard,
    "gradient-with-radial-progress-card": GradientWithRadialProgressCard
  },
  data() { return {
    currentKariyer: {},
    nextKariyer: {},
    loading: false,
    pvCollection: null
  }},
  methods: {
    formatAktiflik(bayi) {
      if(!bayi.aktiflik) return this.$t("bayi.notAktifZero");
      if(!bayi.aktiflik.durum) return this.$t("bayi.notAktifWith", {count: bayi.aktiflik.urunSayisi});
      return this.$t("bayi.aktif", {count: bayi.aktiflik.urunSayisi});
    }
  },
  computed: {
    ...mapGetters(['bayi']),
    percent() {
      if(!this.pvCollection) return 0;
      return Math.round(this.pvCollection.totalObtained*100/this.pvCollection.hedefPuan);
    },
    puanDetail() {
      if(!this.pvCollection) return 0;
      return this.$t('bayi.toplananPuan') +': '+ this.pvCollection.totalObtained.normalize()+' / '+this.pvCollection.hedefPuan
    },
    paketFark() {
      if(!this.nextKariyer.baslangicPaket) return;
      return this.nextKariyer.baslangicPaketTutar - this.bayi.kariyer.baslangicPaketTutar;
    }
  },
  mounted() {
    this.loading = true;
    this.currentKariyer = this.bayi.kariyer;
    service.get('/bayi/'+this.bayi.id+'/next')
    .then(response => {
      this.nextKariyer = response.data;
      if(!this.nextKariyer.baslangicPaket) return service.get('/bayi/'+this.bayi.id+'/pv');
      else return Promise.resolve({data: {}});
    })
    .then(response => {
      this.pvCollection = response.data;
    })
    .catch(err => {
      console.error(err);
    })
    .finally(() => {
      this.loading = false;
    })
  }
}
</script>