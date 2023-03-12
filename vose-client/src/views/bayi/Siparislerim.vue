<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.siparislerim')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
      <b-card class="mb-4" :title="$t('menu.siparislerim')" >
        <api-table :fields="fields" :apiUrl="apiUrl" dateFilter pagination @row-click="handleRowClick" />
      </b-card>
    </b-colxx>
    <b-colxx xxs="12" id="siparisDetaySection">
      <b-card v-if="siparis" class="mb-4" :title="$t('siparis.siparisNoDetay', {id: siparis.id})" >
        <b-table :items="siparis.siparisItems" :fields="detayFields" />
      </b-card>
    </b-colxx>
  </b-row>
  </div>
</template>

<script>
import VueScrollTo from "vue-scrollto"

export default {
  data() { return {
    fields: [
      {key: 'id', label: this.$t('siparis.siparisNumarasi')},
      {key: 'siparisTarihi', label: this.$t('siparis.tarih'), formatter: this.$gutil.formatDateTime},
      {key: 'siparisDurum', label: this.$t('siparis.durumTitle'), formatter: v => this.$t('siparis.durum.'+v)},
      {key: 'sonGuncelleme', label: this.$t('siparis.sonGuncelleme'), formatter: this.$gutil.formatDateTime},
      {key: 'odenenTutar', label: this.$t('siparis.odenenTutar'), formatter: v => v+' ₺'},
      {key: 'toplamPv', label: this.$t('siparis.toplamPv'), formatter: v => v.normalize()},
      {key: 'toplamCv', label: this.$t('siparis.toplamCv'), formatter: v => v.normalize()},
    ],
    detayFields: [
      {key: 'urun.urunAdi', label: this.$t('urun.urunAdi')},
      {key: 'urun.urunKodu', label: this.$t('urun.urunKodu')},
      {key: 'miktar', label: this.$t('siparis.miktar')},
      {key: 'katalogBirimFiyat', label: this.$t('sepet.birimIndirimsizFiyat'), formatter: v => v+' ₺'},
      {key: 'indirimliBirimFiyat', label: this.$t('sepet.indirimliFiyat'), formatter: v => v+' ₺'},
      {key: 'tutar', label: this.$t('siparis.toplamTutar'), formatter: v => v+' ₺'},
      {key: 'toplamPv', label: this.$t('common.pv')},
      {key: 'toplamCv', label: this.$t('common.cv')},
    ],
    siparis: null
  }},
  methods: {
    handleRowClick(args) {
      this.siparis = args.item;
      VueScrollTo.scrollTo('#siparisDetaySection');
    }
  },
  computed: {
    apiUrl() {
      return "/siparis/bayi/"+this.$store.getters.bayiId;
    }
  }
}
</script>
