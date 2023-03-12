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
      <piaf-breadcrumb :heading="$t('menu.kategori-yonetimi')"/>
      <kategori-form-modal ref="formModal" @done="$refs.table.refresh()"></kategori-form-modal>
      <remove-modal module="kategori" ref="removeModal" @done="$refs.table.refresh()" store></remove-modal>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('kategori.title')" >
          <b-table 
            ref="table"
            hover 
            responsive 
            :items="items" 
            :fields="fields"
            :busy="isBusy"
            primary-key="id"
          >

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
import RemoveModal from '../../../containers/RemoveModal'
import KategoriFormModal from '../../../containers/urun/KategoriFormModal'

export default {
  components: {
    "remove-modal": RemoveModal,
    "kategori-form-modal": KategoriFormModal,
  },
  data() { return {
    fields: [
      {key: "kategoriAdi", label: this.$t("kategori.kategoriAdi")},
      {key: "actions", label: this.$t("general.actions")},
    ]
  }},
  methods: {
     addNew() {
      this.$refs['formModal'].open(null);
    },
    edit(item) {
      this.$refs['formModal'].open(item);
    },
    remove(item) {
      this.$refs['removeModal'].open(item.id, item.kategoriAdi);
    },
  },
  computed: {
    items() {
      return this.$store.getters['kategori/items'];
    },
    isBusy() {
      return this.$store.getters['kategori/processing'];
    }
  },
  mounted() {
    this.$store.dispatch('kategori/fetch');
  }
}
</script>
