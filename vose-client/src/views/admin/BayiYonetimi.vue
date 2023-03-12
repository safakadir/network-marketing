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
      <piaf-breadcrumb :heading="$t('menu.bayi-yonetimi')"/>
      <bayi-form-modal ref="formModal" :kariyerler="kariyerler" @done="$refs.table.reload()"></bayi-form-modal>
      <remove-modal module="bayi" ref="removeModal" @done="$refs.table.reload()"></remove-modal>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
      <b-card class="mb-4" :title="$t('bayi.title')" >
        <api-table 
          :fields="fields"
          apiUrl="/bayi"
          pagination
          @load="handleItemsLoad"
          ref="table"
        >
          <template v-slot:cell(puan)="row">

            <a href="#" @click="showPuan(row.item)">{{row.item.totalPv+' / '+row.item.totalCv}}</a>
            <a href="#" class="p-0 ml-1" @click="addPuan(row.item)">
              <i class="glyph-icon simple-icon-plus" /> 
            </a>
          </template>

          <template v-slot:cell(agac)="row">
            <a href="#" @click.prevent="showTree(row.item)">
              <i class="glyph-icon iconsminds-network text-one" />
            </a>
          </template>

          <template v-slot:cell(actions)="row">
            <b-button-group>
            <b-button variant="outline-warning" size="xs" @click="edit(row.item)">
              <i class="glyph-icon simple-icon-pencil" /> 
            </b-button>
            <b-button variant="outline-danger" size="xs" @click="remove(row.item)">
              <i class="glyph-icon simple-icon-trash" /> 
            </b-button>
            </b-button-group>
            <b-button variant="outline-info" size="xs" @click="changePassword(row.item)">
              <i class="glyph-icon simple-icon-lock" /> 
            </b-button>
          </template>
        </api-table>
        <div class="loading" v-if="loading" />
        <bayi-puan-modal ref="puanmodal" />
        <bayi-agac-modal ref="agacmodal" />

        <b-modal v-model="addPuanModalShown" :title="$t('bayi.puanEkle', {name: puanItem.identity})" ref="puaneklemodal" >
          <b-form-group :label="$t('bayi.altBayiKodu')" :description="$t('bayi.puanEkleAltBayiHint')">
            <b-form-input v-model="puanItem.altBayiId" />
          </b-form-group>
          <b-form-group :label="$t('bayi.pvMiktar')" :description="$t('bayi.puanHint')">
            <b-form-input v-model="puanItem.pv" />
          </b-form-group>
          <b-form-group :label="$t('bayi.cvMiktar')" :description="$t('bayi.puanHint')">
            <b-form-input v-model="puanItem.cv" />
          </b-form-group>
          <p class="text-semi-muted text-small mb-0"><i class="simple-icon-exclamation text-one" /> {{$t('bayi.puanEkleWarning')}}</p>
          <template slot="modal-footer">
            <b-spinner variant="primary" v-if="loading"></b-spinner>
            <b-button variant="secondary" @click="addPuanModalShown = false" :disabled="loading">{{$t('general.cancel')}}</b-button>
            <b-button variant="primary" @click="puanEkleModalDone" :disabled="loading">{{$t('general.ok')}}</b-button>
          </template>
        </b-modal>

        <b-modal v-model="changePassModalShown" :title="$t('bayi.sifreDegistir', {name: changePassItem.identity})" ref="changepassmodal" >
          <b-form-group :label="$t('bayi.newPassword')" >
            <b-form-input v-model="changePassItem.newPassword" type="password" />
          </b-form-group>
          <b-form-group :label="$t('bayi.newPasswordRepeat')" >
            <b-form-input v-model="changePassItem.repeat" type="password" />
          </b-form-group>
          <template slot="modal-footer">
            <b-spinner variant="primary" v-if="loading"></b-spinner>
            <b-button variant="secondary" @click="changePassModalShown = false" :disabled="loading">{{$t('general.cancel')}}</b-button>
            <b-button variant="primary" @click="changePassModalDone" :disabled="loading || changePassItem.newPassword != changePassItem.repeat">{{$t('general.ok')}}</b-button>
          </template>
        </b-modal>
      </b-card>
    </b-colxx>
  </b-row>

  </div>
</template>

<script>
import service from '@/service'

import BayiFormModal from '@/containers/bayi/BayiFormModal'
import RemoveModal from '@/containers/RemoveModal'
import BayiPuanModal from '@/containers/bayi/BayiPuanModal'
import BayiAgacModal from '@/containers/bayi/BayiAgacModal'

export default {
  components: {
    "bayi-form-modal": BayiFormModal,
    "remove-modal": RemoveModal,
    "bayi-puan-modal": BayiPuanModal,
    "bayi-agac-modal": BayiAgacModal
  },
  data() { return {
    fields: [
      {key: "identity", label: this.$t("bayi.identity")},
      {key: "kariyer", label: this.$t("bayi.kariyerAdi"), formatter: v => v.kariyerAdi},
      {key: "sponsorIdentity", label: this.$t("bayi.sponsor")},
      {key: "sponsorId", label: this.$t("bayi.sponsor")},
      {key: "agac", label: this.$t("bayi.agac")},
      {key: "derinlik", label: this.$t("bayi.derinlik")},
      {key: "puan", label: this.$t("bayi.puan")},
      {key: "email", label: this.$t("bayi.email")},
      {key: "telefon1", label: this.$t("bayi.telefon1")},
      {key: "telefon2", label: this.$t("bayi.telefon2")},
      {key: "kayitDurum", label: this.$t("bayi.kayitDurum"),
       formatter: value => value && value != "0" ? this.$t("bayi.completed") : this.$t("bayi.notCompleted") },
      {key: "ulke", label: this.$t("bayi.ulke")},
      {key: "sehir", label: this.$t("bayi.sehir")},
      {key: "actions", label: this.$t("general.actions")},
    ],
    loading: false,
    addPuanModalShown: false,
    puanItem: {},
    changePassModalShown: false,
    changePassItem: {},
    newPasswordRepeat: ""
  }},
  methods: {
    addNew() {
      this.$refs['formModal'].open(null);
    },
    edit(item) {
      this.$refs['formModal'].open(item);
    },
    remove(item) {
      this.$refs['removeModal'].open(item.id, item.identity);
    },
    showPuan(item) {
      this.$refs['puanmodal'].open(item);
    },
    showTree(item) {
      this.$refs['agacmodal'].open(item);
    },
    handleItemsLoad(items) {
      this.loading = true;
      const ids = items.map(b => b.sponsorId)
      service.put('/bayi/by/ids/', ids)
      .then(response => {
        items.forEach(b => {
          b.sponsorIdentity = this.$gutil.getProperty(response.data.find(s => s.id == b.sponsorId), 'identity');
        })
        this.$refs.table.refresh();
      })
      .finally(() => {
        this.loading = false;
      })
    },
    addPuan(item) {
      this.puanItem = {bayiId: item.id, identity: item.identity};
      this.addPuanModalShown = true;
    },
    puanEkleModalDone() {
      this.loading = true;
      service.put('bayi/puan', this.puanItem)
      .then(() => {
        this.$refs.table.reload();
        this.addPuanModalShown = false;
      })
      .finally(() => {
        this.loading = false;
      })
    },
    changePassword(item) {
      this.changePassItem = {bayiId: item.id, identity: item.identity, repeat: ""};
      this.changePassModalShown = true;
    },
    changePassModalDone() {
      this.loading = true;
      service.put('auth/password', this.changePassItem)
      .then(() => {
        this.changePassModalShown = false;
      })
      .finally(() => {
        this.loading = false;
      })
    }
  },
  computed: {
    kariyerler() {
      return this.$store.getters['kariyer/items'];
    },
  },
  mounted() {
    this.$store.dispatch('kariyer/fetch');
  }
}
</script>
