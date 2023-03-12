<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.cuzdanim')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
      <div class="icon-cards-row d-flex">
        <b-card class="mb-4 mr-4 text-center" style="width:500px;">
          <i class="iconsminds-coins"/>
          <p class="card-text font-weight-semibold mb-0">{{ $t('cuzdan.bakiye') }}</p>
          <b-spinner v-if="totalLoading" variant="primary"></b-spinner>
          <h2 v-else>{{ cuzdan.cuzdanToplam }} ₺</h2>
        </b-card>
      </div>
      <div class="top-right-button-container">
        <b-button
          variant="primary"
          size="lg"
          class="top-right-button mb-3"
          @click="addNewTalep"
        >{{ $t('cuzdan.yeniOdemeTalebi') }}</b-button>
      </div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('cuzdan.talepler')" >
          <api-table ref="talepTable" :fields="talepFields" :apiUrl="talepApiUrl" dateFilter>
            <template v-slot:cell(detay)="row">
              <b-button variant="outline-info" @click="showDetayModal(row.item)">
                <i class="glyph-icon simple-icon-info" /> 
              </b-button>
            </template>
            <template v-slot:cell(actions)="row">
              <b-button variant="empty" size="xs" @click="cancel(row.item)" :disabled="row.item.durum != 'BEKLIYOR'">
                <i class="glyph-icon simple-icon-close" /> 
              </b-button>
            </template>
          </api-table>
        </b-card>
    </b-colxx>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('cuzdan.hareketler')" >
          <api-table :fields="hareketFields" :apiUrl="cuzdanApiUrl" dateFilter>
          </api-table>
        </b-card>
    </b-colxx>
  </b-row>
  <odemetalep-form-modal ref="formModal" :cuzdan-max="cuzdan.cuzdanToplam" @done="$refs['talepTable'].reload()" />
  </div>
</template>

<script>
import moment from 'moment'
import service from '@/service'
import OdemeTalepFormModal from '@/containers/cuzdan/OdemeTalepFormModal'

export default {
  components: {
    'odemetalep-form-modal': OdemeTalepFormModal
  },
  data() { return {
    hareketFields: [
      {key: "miktar", label: this.$t("cuzdan.miktar")},
      {key: "yon", label: this.$t("cuzdan.action"), formatter: value => value > 0 ? this.$t("cuzdan.eklenen") : this.$t("cuzdan.cekilen")},
      {key: "tarih", label: this.$t("cuzdan.tarih"), formatter: value => moment(value.tarih).format('DD-MM-YYYY hh:mm')},
      {key: "aciklama", label: this.$t("cuzdan.aciklama")},
      {key: "cuzdanToplam", label: this.$t("cuzdan.araToplam"), formatter: value => value+'₺'},
    ],
    talepFields: [
      {key: "id", label: this.$t("cuzdan.talepId")},
      {key: "durum", label: this.$t("cuzdan.talepDurum")},
      {key: "durumTarih", label: this.$t("cuzdan.sonGuncelleme"), formatter: value => moment(value.tarih).format('DD-MM-YYYY hh:mm')},
      {key: "miktar", label: this.$t("cuzdan.miktar")},
      {key: "talepTarih", label: this.$t("cuzdan.talepTarih"), formatter: value => moment(value.tarih).format('DD-MM-YYYY hh:mm')},
      {key: "banka", label: this.$t("cuzdan.banka")},
      {key: "iban", label: this.$t("cuzdan.iban")},
      {key: "actions", label: this.$t("cuzdan.iptalEt")},
    ],
    detayBanka: '',
    detayIban: '',
    modalTitle: '',
    cuzdan: { cuzdanToplam:0 },
    totalLoading: false,
    cancelLoading: false
  }},
  computed: {
    cuzdanApiUrl() {
      if(!this.$store.getters.bayiId) return;
      return "/cuzdan/bayi/"+this.$store.getters.bayiId;
    },
    talepApiUrl() {
      if(!this.$store.getters.bayiId) return;
      return "/odemetalep/bayi/"+this.$store.getters.bayiId;
    }
  },
  methods: {
    showDetayModal(item) {
      this.detayBanka = item.banka;
      this.detayIban = item.iban;
      this.modalTitle = '#'+item.id + ' - ' + this.$t("cuzdan.talepDetay");
      this.$refs['detaymodal'].show();
    },
    addNewTalep() {
      this.$refs['formModal'].open(null);
    },
    cancel(item) {
        this.cancelLoading = true;
        service.put('odemetalep/cancel/'+item.id)
        .then(() => {
            return this.$refs.talepTable.reload();
        })
        .finally(() => {
            this.cancelLoading = false;
        });
    },
  },
  mounted() {
    this.totalLoading = true;
    service.get(this.cuzdanApiUrl+'/latest')
    .then(response => {
      this.cuzdan = response.data;
      if(!this.cuzdan) this.cuzdan = {cuzdanToplam: 0};
    })
    .finally(() => {
      this.totalLoading = false;
    })
  }
}
</script>