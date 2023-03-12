<template>
  <b-modal
    id="modalright"
    ref="modalright"
    :busy="loading"
    :title="isNew ? $t('kategori.add-new-title') : $t('kategori.edit-title', {name: existingItem.kategoriAdi})"
    modal-class="modal-right"
  >
    <b-form>
      <b-form-group :label="$t('kategori.kategoriAdi')">
        <b-form-input v-model="currentItem.kategoriAdi" />
      </b-form-group>
    </b-form>
    <template slot="modal-footer">
      <b-button variant="outline-secondary" @click="hideModal('modalright')">{{ $t('general.cancel') }}</b-button>
      <b-button v-if="isNew" variant="primary" @click="saveItem()" class="mr-1">{{ $t('general.save') }}</b-button>
      <b-button v-else variant="warning" @click="saveItem()" class="mr-1">{{ $t('general.update') }}</b-button>
    </template>
  </b-modal>
</template>

<script>

const NEW_ITEM = {};

export default {
  data() { return {
    existingItem: {},
    currentItem: this.$gutil.clone(NEW_ITEM),
    loading: false
  }},
  methods: {
    open(item) {
      if(!item) this.currentItem = this.$gutil.clone(NEW_ITEM);
      else {
        this.currentItem = this.$gutil.clone(item);
        this.existingItem = item;
      }
      this.showModal('modalright');
    },
    showModal(refname) {
      this.$refs[refname].show();
    },
    hideModal(refname) {
      this.$refs[refname].hide();
    },
    async saveItem() {
      this.loading = true;
      const action = 'kategori/' + (this.isNew ? 'add' : 'update');
      let result = await this.$store.dispatch(action, this.currentItem);
      if(result) {
        this.currentItem = this.$gutil.clone(NEW_ITEM);
        this.hideModal("modalright");
        this.$emit('done');
      }
      this.loading = false;
    }
  },
  computed: {
    isNew() {
      return !this.currentItem.id;
    }
  }
}
</script>
