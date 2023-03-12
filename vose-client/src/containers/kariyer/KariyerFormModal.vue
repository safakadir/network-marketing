<template>
  <b-modal
    id="modalright"
    ref="modalright"
    :busy="loading"
    :title="isNew ? $t('kariyer.add-new-title') : $t('kariyer.edit-title', {name: currentItem.kariyerAdi})"
    modal-class="modal-right"
  >
    <b-form>
      <b-form-group :label="$t('kariyer.kariyerAdi')">
        <b-form-input v-model="currentItem.kariyerAdi" />
      </b-form-group>
      <b-form-group v-if="isNew" :label="$t('kariyer.form.addAfter')">
        <b-form-select v-model="currentItem.addAfter" :options="kariyerOptions" />
      </b-form-group>
      <b-form-group>
        <b-form-checkbox v-model="currentItem.baslangicPaket" >{{$t('kariyer.baslangicPaket')}}</b-form-checkbox>
      </b-form-group>
      <b-form-group :label="$t('kariyer.baslangicPaketTutar')">
        <b-form-input v-model="currentItem.baslangicPaketTutar" type="number" :disabled="!currentItem.baslangicPaket" />
      </b-form-group>
      <b-form-group :label="$t('kariyer.kariyerPuan')">
        <b-form-input v-model="currentItem.kariyerPuan" type="number" :disabled="currentItem.baslangicPaket" />
      </b-form-group>
      <b-form-group :label="$t('kariyer.aktiflikSarti')" 
                    :description="$t('kariyer.form.aktiflikSartiDesc')">
        <b-form-input v-model="currentItem.aktiflikSarti" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('kariyer.alisverisIndirimi')" 
                    :description="$t('kariyer.form.percentageDesc')">
        <b-form-input v-model="currentItem.alisverisIndirimi" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('kariyer.binaryEslesme')" 
                    :description="$t('kariyer.form.percentageDesc')">
        <b-form-input v-model="currentItem.binaryEslesme" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('kariyer.sponsorPrimi')" 
                    :description="$t('kariyer.form.csvDerinlikDesc')">
        <b-form-input v-model="currentItem.sponsorPrimi" />
      </b-form-group>
      <b-form-group :label="$t('kariyer.ekipAlisveris')" 
                    :description="$t('kariyer.form.csvDerinlikDesc')">
        <b-form-input v-model="currentItem.ekipAlisveris" />
      </b-form-group>
      <b-form-group :label="$t('kariyer.ekipKazanc')" 
                    :description="$t('kariyer.form.csvDerinlikDesc')">
        <b-form-input v-model="currentItem.ekipKazanc" />
      </b-form-group>
      <b-form-group :label="$t('kariyer.bayiAktiflikPuan')" >
        <b-form-input v-model="currentItem.bayiAktiflikPuan" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('kariyer.bayiAktiflikKazanc')" >
        <b-form-input v-model="currentItem.bayiAktiflikKazanc" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('kariyer.liderDestek')" >
        <b-form-input v-model="currentItem.liderDestek" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('kariyer.liderCikarma')" 
                    :description="$t('kariyer.form.liderCikarmaDesc')">
        <b-form-input v-model="currentItem.liderCikarma" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('kariyer.tazminat')" >
        <b-form-input v-model="currentItem.tazminat" type="number" no-wheel />
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

const NEW_ITEM = {
  addAfter: 0,
};

export default {
  props: ["kariyerler"],
  data() { return {
    currentItem: this.$gutil.clone(NEW_ITEM),
    loading: false
  }},
  methods: {
    open(item) {
      if(!item) this.currentItem = this.$gutil.clone(NEW_ITEM);
      else this.currentItem = this.$gutil.clone(item);
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
      const action = 'kariyer/' + (this.isNew ? 'add' : 'update');
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
    kariyerOptions() {
      const options = this.kariyerler.map(k => { return {value: k.siraNo, text: k.kariyerAdi} })
      options.unshift({value: 0, text: this.$t('kariyer.form.toStart')});
      return options;
    },
    isNew() {
      return !this.currentItem.id;
    },
  }
}
</script>
