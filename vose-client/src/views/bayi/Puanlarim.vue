<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.puanlarim')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('bayi.puanDistribution')" >
          <b-table :items="puanlar" :fields="fields" hover foot-clone>
            <template v-slot:foot(altBayi)>{{$t('bayi.totalPuan')}}</template>
            <template v-slot:foot(pv)>{{bayi.totalPv}}</template>
            <template v-slot:foot(cv)>{{bayi.totalCv}}</template>
          </b-table>
        </b-card>
    </b-colxx>
  </b-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  data() { return {
    fields: [
        {key:"altBayi", label: this.$t("bayi.altBayi")},
        {key:"pv", label: this.$t("common.pv")},
        {key:"cv", label: this.$t("common.cv")}
    ]
  }},
  methods: {
    identityOf(altBayiId) {
        return this.bayi.altBayiler.find(b => b.id == altBayiId).identity;
    },
  },
  computed: {
    ...mapGetters(['bayi']),
    puanlar() {
        if(!this.bayi || !this.bayi.puanlar) return [];
        const result = this.bayi.puanlar.map(p => {
            return {
                altBayi: this.identityOf(p.altBayiId),
                pv: p.pv.normalize(),
                cv: p.cv.normalize()
            }
        })
        return result;
    }
  }
}
</script>