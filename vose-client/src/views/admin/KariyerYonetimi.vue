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
      <piaf-breadcrumb :heading="$t('menu.kariyer-yonetimi')"/>
      <kariyer-form-modal ref="formModal" :kariyerler="items" @done="$refs.table.refresh()"></kariyer-form-modal>
      <remove-modal ref="removeModal" module="kariyer" @done="$refs.table.refresh()" store></remove-modal>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
      <b-card class="mb-4" :title="$t('kariyer.title')" >
        <b-table 
          ref="table"
          hover 
          responsive 
          :items="items" 
          :fields="fields"
          :busy="isBusy"
          primary-key="id"
          >
          
          <template v-slot:cell(siraNo)="row">
            <b-button-group>
              <b-button variant="outline-info" size="xs" @click="changeOrder(row.item, -1)" :disabled="row.item.siraNo <= 1">
                <i class="glyph-icon simple-icon-arrow-up" /> 
              </b-button>
              <b-button variant="outline-info" size="xs" disabled style="opacity:100;pointer-events:none;">
                {{row.item.siraNo}}
              </b-button>
              <b-button variant="outline-info" size="xs" @click="changeOrder(row.item, +1)" :disabled="row.item.siraNo >= items.length">
                <i class="glyph-icon simple-icon-arrow-down" /> 
              </b-button>
            </b-button-group>
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
          </template>

        </b-table>
      </b-card>
    </b-colxx>
  </b-row>
  </div>
</template>

<script>
import KariyerFormModal from '@/containers/kariyer/KariyerFormModal'
import RemoveModal from '@/containers/RemoveModal'

export default {
  components: {
    "kariyer-form-modal": KariyerFormModal,
    "remove-modal": RemoveModal
  },
  data() { return {
    fields: [
      {key: "siraNo", label: this.$t("kariyer.siraNo")},
      {key: "kariyerAdi", label: this.$t("kariyer.kariyerAdi")},
      {key: "baslangicPaket", label: this.$t("kariyer.baslangicPaket"),
       formatter: value => value && value != "0" ? this.$t("general.yes") : null },
      {key: "baslangicPaketTutar", label: this.$t("kariyer.baslangicPaketTutar")},
      {key: "kariyerPuan", label: this.$t("kariyer.kariyerPuan")},
      {key: "aktiflikSarti", label: this.$t("kariyer.aktiflikSarti")},
      {key: "alisverisIndirimi", label: this.$t("kariyer.alisverisIndirimi")},
      {key: "binaryEslesme", label: this.$t("kariyer.binaryEslesme")},
      {key: "farkKazanci", label: this.$t("kariyer.farkKazanci")},
      {key: "sponsorPrimi", label: this.$t("kariyer.sponsorPrimi")},
      {key: "ekipAlisveris", label: this.$t("kariyer.ekipAlisveris")},
      {key: "ekipKazanc", label: this.$t("kariyer.ekipKazanc")},
      {key: "bayiAktiflikPuan", label: this.$t("kariyer.bayiAktiflikPuan")},
      {key: "bayiAktiflikKazanc", label: this.$t("kariyer.bayiAktiflikKazanc")},
      {key: "liderDestek", label: this.$t("kariyer.liderDestek")},
      {key: "liderCikarma", label: this.$t("kariyer.liderCikarma")},
      {key: "tazminat", label: this.$t("kariyer.tazminat")},
      {key: "actions", label: this.$t("general.actions")},
    ],
  }},
  methods: {
    async changeOrder(item, direction) {
      await this.$store.dispatch('kariyer/changeOrder', {id: item.id, direction: direction});
      this.$refs.table.refresh();
    },
    addNew() {
      this.$refs['formModal'].open(null);
    },
    edit(item) {
      this.$refs['formModal'].open(item);
    },
    remove(item) {
      this.$refs['removeModal'].open(item.id, item.kariyerAdi);
    }
  },
  computed: {
    items() {
      return this.$store.getters['kariyer/items'];
    },
    isBusy() {
      return this.$store.getters['kariyer/processing'];
    }
  },
  mounted() {
    this.$store.dispatch('kariyer/fetch');
  }
}
</script>
