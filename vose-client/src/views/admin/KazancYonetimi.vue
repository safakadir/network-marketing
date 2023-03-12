<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <div class="top-right-button-container">
      <b-button
        variant="primary"
        size="lg"
        class="top-right-button"
        @click="addNew"
      >{{ $t('general.add-new') }}
      </b-button>
      </div>
      <piaf-breadcrumb :heading="$t('menu.kazanc-yonetimi')"/>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs=12> 
      <b-form-group label="Bayi Seçin">
        <model-list-select v-model="selectedBayiId" :list="bayiSearchResults" option-value="id" option-text="identity" @searchchange="searchChanged" :placeholder="$t('kazanc.searchBayiHint')" />
      </b-form-group>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
      <b-card class="mb-4" :title="title" >
        <api-table
          ref="kazancTable"
          :fields="fields"
          :apiUrl="apiUrl"
          pagination
          @load="kazancLoaded"
        />
        <b-modal v-model="addNewModalShown" :title="$t('kazanc.ekle')" ref="formModal" >
          <b-form-group :label="$t('bayi.kod')">
            <b-form-input v-model="newItem.bayiId" />
          </b-form-group>
          <b-form-group :label="$t('kazanc.miktar')">
            <b-form-input v-model="newItem.miktar" />
          </b-form-group>
          <b-form-group :label="$t('kazanc.kazancTuru')">
            <b-form-select v-model="newItem.kazancTuru" :options="kazancTuruOptions" />
          </b-form-group>
          <b-form-group :label="$t('common.aciklama')">
            <b-form-input v-model="newItem.aciklama" />
          </b-form-group>
          <template slot="modal-footer">
            <b-spinner variant="primary" v-if="loading"></b-spinner>
            <b-button variant="secondary" @click="addNewModalShown = false" :disabled="loading">{{$t('general.cancel')}}</b-button>
            <b-button variant="primary" @click="modalDone" :disabled="loading">{{$t('general.ok')}}</b-button>
          </template>
        </b-modal>
      </b-card>
      <div class="loading" v-if="loading" />
    </b-colxx>
  </b-row>

  </div>
</template>

<script>
import moment from 'moment'
import service from '@/service'

export default {
  components: {
  },
  data() { return {
    items: [],
    fields: [
      {key: "bayi.identity", label: this.$t("kazanc.bayi")},
      {key: "kazancTuru", label: this.$t("kazanc.kazancTuru")},
      {key: "miktar", label: this.$t("kazanc.miktar"), formatter: value => value+'₺'},
      {key: "altBayi", label: this.$t("kazanc.altBayi"), formatter: value => value ? value.identity : '-'},
      {key: "tarih", label: this.$t("kazanc.tarih"), formatter: value => moment(value.tarih).format('DD-MM-YYYY hh:mm')},
      {key: "cuzdanaIslendi", label: this.$t("kazanc.cuzdanaIslendi"), formatter: value => value ? this.$t('general.yes') : this.$t('general.no') },
    ],
    kazancTuruOptions: [
      {value:'EKIP_ALISVERIS_PRIMI', text: this.$t('kazanc.tur.EKIP_ALISVERIS_PRIMI')},
      {value:'BINARY_KAZANCI', text: this.$t('kazanc.tur.BINARY_KAZANCI')},
      {value:'EKIP_KAZANC_PRIMI', text: this.$t('kazanc.tur.EKIP_KAZANC_PRIMI')},
      {value:'BAYI_FARK_KAZANCI', text: this.$t('kazanc.tur.BAYI_FARK_KAZANCI')},
      {value:'SPONSOR_FARK_KAZANCI', text: this.$t('kazanc.tur.SPONSOR_FARK_KAZANCI')},
      {value:'SPONSOR_PRIMI', text: this.$t('kazanc.tur.SPONSOR_PRIMI')},
      {value:'BAYI_AKTIFLIK_PRIMI', text: this.$t('kazanc.tur.BAYI_AKTIFLIK_PRIMI')},
      {value:'LIDER_DESTEK_PRIMI', text: this.$t('kazanc.tur.LIDER_DESTEK_PRIMI')},
      {value:'LIDER_CIKARMA_PRIMI', text: this.$t('kazanc.tur.LIDER_CIKARMA_PRIMI')},
      {value:'REKLAM_PRIMI', text: this.$t('kazanc.tur.REKLAM_PRIMI')},
      {value:'ARAC_PRIMI', text: this.$t('kazanc.tur.ARAC_PRIMI')},
      {value:'DIGER', text: this.$t('kazanc.tur.DIGER')},
    ],
    selectedBayiId: 0,
    loading: false,
    bayiSearchResults: [],
    lastResults: [],
    searchDirty: false,
    searchLastChange: 0,
    search: "",
    newItem: {},
    addNewModalShown: false
  }},
  methods: {
    addNew() {
      this.newItem = {};
      this.addNewModalShown = true;
    },
    loadBayiler() {
      this.loading = true;
      service.put('/bayi/search', this.search, {headers: {"Content-Type": "text/plain"}})
      .then(response => {
        this.bayiSearchResults = response.data;
        this.lastResults = response.data;
      })
      .finally(() => {
        this.loading = false;
      })
    },
    kazancLoaded() {
      this.bayiSearchResults = [];
    },
    searchChanged(search) {
      if(!search || search == '') {
        this.bayiSearchResults = [];
        return;
      }
      this.search = search;
      this.searchLastChange = this.$gutil.now();
      this.searchDirty = true;
    },
    modalDone() {
      this.loading = true;
      const item = this.$gutil.clone(this.newItem);
      item.bayi = {id: item.bayiId};
      delete item.bayiId;
      service.post('/kazanc', item)
      .then(() => {
        this.$refs.kazancTable.reload();
        this.addNewModalShown = false;
      })
      .finally(() => {
        this.loading = false;
      })
    }
  },
  computed: {
    apiUrl() {
      if(!this.selectedBayiId) return '/kazanc';
      return '/kazanc/bayi/'+this.selectedBayiId;
    },
    title() {
      return !this.selectedBayiId ? 
        this.$t('kazanc.tumKazanclar') : 
        this.$t('kazanc.kazanclar') + ': '+this.$gutil.getProperty(this.selectedBayi, 'identity');
    },
    selectedBayi() {
      if(!this.selectedBayiId) return;
      return this.lastResults.find(b => b.id == this.selectedBayiId);
    }
  },
  mounted() {
    setInterval(() => {
      if(this.searchDirty && this.$gutil.now()-this.searchLastChange > 300) {
        this.searchDirty = false;
        this.loadBayiler();
      }
    }, 50);
  }
}
</script>
