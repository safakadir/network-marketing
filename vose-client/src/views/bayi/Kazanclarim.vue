<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.kazanclarim')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('menu.kazanclarim')">
          <api-table :fields="fields" :apiUrl="apiUrl" dateFilter pagination />
        </b-card>
    </b-colxx>
  </b-row>
  </div>
</template>

<script>
import moment from 'moment';
import { mapGetters } from 'vuex';
import service from '@/service';

export default {
  data() { return {
    fields: [
      {key: "kazancTuru", label: this.$t("kazanc.kazancTuru")},
      {key: "miktar", label: this.$t("kazanc.miktar"), formatter: value => value+'₺'},
      {key: "altBayi", label: this.$t("kazanc.altBayi"), formatter: value => value ? value.identity : '-'},
      {key: "tarih", label: this.$t("kazanc.tarih"), formatter: value => moment(value).format('DD-MM-YYYY hh:mm')},
      {key: "cuzdanaIslendi", label: this.$t("kazanc.cuzdanaIslendi"), formatter: value => value ? this.$t('general.yes') : this.$t('general.no') },
    ],
  }},
  methods: {
  },
  computed: {
    ...mapGetters(['bayiId', 'initCompleted']),
    apiUrl() {
      if(!this.bayiId) return;
      return '/kazanc/bayi/'+this.bayiId;
    }
  },
}
</script>