<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.odeme-yonetimi')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('menu.odeme-yonetimi')" >
          <api-table ref="talepTable" :fields="fields" apiUrl="/odemetalep" pagination 
            @load="handleItemsLoad">
            <template v-slot:cell(actions)="row">
              <template v-if="row.item.durum == 'BEKLIYOR'">
                <a href="#" :id="'btn-approve-'+row.item.id" class="p-0 ml-1 text-one text-success" @click="approve(row.item)">
                  <i class="glyph-icon simple-icon-check" /> 
                </a>
                <b-tooltip :target="'btn-approve-'+row.item.id" placement="bottom" :title="$t('common.approve')" ></b-tooltip>
                <a href="#" :id="'btn-cancel-'+row.item.id" class="p-0 ml-1 text-one text-danger" @click="cancel(row.item)">
                  <i class="glyph-icon simple-icon-close" /> 
                </a>
                <b-tooltip :target="'btn-cancel-'+row.item.id" placement="bottom" :title="$t('common.cancel')" ></b-tooltip>
              </template>
              <span v-else> - </span>
            </template>
          </api-table>
        </b-card>
        <div class="loading" v-if="loading" />
    </b-colxx>
  </b-row>
  <!-- <b-modal v-model="durumModalShown" :title="modalTitle">
    <b-form-select v-model="selectedDurum" :options="durumOptions"></b-form-select>
    <template slot="modal-footer">
      <b-spinner variant="primary" v-if="durumLoading"></b-spinner>
      <b-button variant="secondary" @click="durumModalShown = false" :disabled="durumLoading">{{$t('general.cancel')}}</b-button>
      <b-button variant="primary" @click="modalDone" :disabled="durumLoading || !selectedDurum">{{$t('general.ok')}}</b-button>
    </template>
  </b-modal> -->
  </div>
</template>

<script>
import moment from 'moment'
import service from '@/service'

export default {
  data() { return {
    fields: [
      {key: "id", label: this.$t("cuzdan.talepId")},
      {key: "bayiIdentity", label: this.$t("kazanc.bayi")},
      {key: "durum", label: this.$t("cuzdan.talepDurum"), formatter: value => this.$t('cuzdan.durum.'+value)},
      {key: "durumTarih", label: this.$t("cuzdan.sonGuncelleme"), formatter: value => moment(value).format('DD-MM-YYYY hh:mm')},
      {key: "miktar", label: this.$t("cuzdan.miktar")},
      {key: "talepTarih", label: this.$t("cuzdan.talepTarih"), formatter: value => moment(value).format('DD-MM-YYYY hh:mm')},
      {key: "banka", label: this.$t("cuzdan.banka")},
      {key: "iban", label: this.$t("cuzdan.iban")},
      {key: "actions", label: this.$t("general.actions")},
    ],
    // durumModalShown: false,
    // durumOptions: [
    //   {value:'BEKLIYOR', text: this.$t('cuzdan.durum.BEKLIYOR')},
    //   {value:'ODENDI', text: this.$t('cuzdan.durum.ODENDI')},
    //   {value:'IPTAL_EDILDI', text: this.$t('cuzdan.durum.IPTAL_EDILDI')}
    // ],
    // selectedDurum: null,
    // selectedItem: null,
    // durumLoading: false,
    loading: false
  }},
  methods: {
    // openDurumModal(item) {
    //   this.durumModalShown = true;
    //   this.selectedDurum = item.durum;
    //   this.selectedItem = item;
    // },
    // modalDone() {
    //   if(!this.selectedDurum) return;
    //   const item = this.$gutil.clone(this.selectedItem);
    //   item.durum = this.selectedDurum;
    //   this.durumLoading = true;
    //   service.put('/odemetalep', item)
    //   .then(response => {
    //     this.durumModalShown = false;
    //     this.$refs.talepTable.reload();
    //   })
    //   .finally(() => {
    //     this.durumLoading = false;
    //   })
    // },
    approve(item) {
      this.loading = true;
      service.put('/odemetalep/approve/'+item.id)
      .then(() => { 
        this.$refs.talepTable.reload();
      })
      .finally(() => this.loading = false );
    },
    cancel(item) {
      this.loading = true;
      service.put('/odemetalep/cancel/'+item.id)
      .then(() => { 
        this.$refs.talepTable.reload();
      })
      .finally(() => this.loading = false );
    },
    handleItemsLoad(items) {
      this.loading = true;
      const ids = items.map(otlp => otlp.bayiId);
      service.put('/bayi/by/ids/', ids)
      .then(response => {
        items.forEach(otlp => {
          otlp.bayiIdentity = this.$gutil.getProperty(response.data.find(b => b.id == otlp.bayiId), 'identity');
        })
        this.$refs.talepTable.refresh();
      })
      .finally(() => {
        this.loading = false;
      })
    },
  },
  computed: {
    // modalTitle() {
    //   if(!this.selectedItem) return;
    //   return this.$t('cuzdan.durumGuncelle', {id:this.selectedItem.id});
    // }
  }
}
</script>