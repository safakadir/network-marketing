<template>
  <b-modal
    id="modalright"
    ref="modalright"
    :busy="loading"
    :title="isNew ? $t('kariyer.add-new-title') : $t('kariyer.edit-title', {name: currentItem.kariyerAdi})"
    modal-class="modal-right"
  >
    <b-form>
      <b-form-group :label="$t('bayi.tckn')">
        <b-form-input v-model="currentItem.tckn" />
      </b-form-group>
      <b-form-group :label="$t('bayi.isim')">
        <b-form-input v-model="currentItem.isim" />
      </b-form-group>
      <b-form-group :label="$t('bayi.soyisim')">
        <b-form-input v-model="currentItem.soyisim" />
      </b-form-group>
      <b-form-group :label="$t('bayi.email')">
        <b-form-input v-model="currentItem.email" type="email" />
      </b-form-group>
      <b-form-group v-if="isNew" :label="$t('bayi.sifre')">
        <b-form-input v-model="currentItem.sifre" type="password" />
      </b-form-group>
      <b-form-group v-if="isNew" :label="$t('bayi.paket')">
        <b-form-select v-model="currentItem.kariyer" :options="paketOptions" />
      </b-form-group>
      <b-form-group v-if="!isNew" :label="$t('bayi.kariyer')">
        <b-form-select v-model="currentItem.kariyer" :options="kariyerOptions" />
      </b-form-group>
      <b-form-group :label="$t('bayi.sponsorKodu')" :description="$t('bayi.sponsorHint')">
        <b-form-input v-model="currentItem.sponsorId" />
      </b-form-group>
      <b-form-group :label="$t('bayi.dogumTarihi')">
        <b-form-input v-model="currentItem.dogumTarihi" type="date" />
      </b-form-group>
      <b-form-group :label="$t('bayi.telefon1')">
        <b-form-input v-model="currentItem.telefon1" type="tel" />
      </b-form-group>
      <b-form-group :label="$t('bayi.telefon2')">
        <b-form-input v-model="currentItem.telefon2" type="tel" />
      </b-form-group>
      <b-form-group :label="$t('bayi.ulke')">
        <b-form-input v-model="currentItem.ulke" />
      </b-form-group>
      <b-form-group :label="$t('bayi.sehir')">
        <b-form-input v-model="currentItem.sehir" />
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
import moment from 'moment'
import service from '@/service'

const NEW_ITEM = {
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
      else {
        this.currentItem = this.$gutil.clone(item);
        // this.currentItem.dogumTarihi = moment(this.currentItem.dogumTarihi).utc().format('YYYY-MM-DD');
      }
      this.showModal('modalright');
    },
    showModal(refname) {
      this.$refs[refname].show();
    },
    hideModal(refname) {
      this.$refs[refname].hide();
    },
    saveItem() {
      const bayiContainer = { bayi: this.currentItem, sifre: this.currentItem.sifre };
      this.loading = true;
      const method = this.isNew ? 'post' : 'put';
      service[method]('/bayi', this.currentItem)
      .then(res => {
        this.currentItem = this.$gutil.clone(NEW_ITEM);
        this.hideModal("modalright");
        this.$emit('done');
      })
      .catch(err => {})
      .finally(() => {
        this.loading = false;
      })
    }
  },
  computed: {
    paketOptions() {
      return this.kariyerler.filter(k => k.baslangicPaket)
                            .map(k => { return {value: k, text: k.kariyerAdi+"-"+k.baslangicPaketTutar}});
    },
    kariyerOptions() {
      return this.kariyerler.map(k => { return {value: k, text: k.kariyerAdi}});
    },
    sponsorOptions() {
      return this.bayiler.map(b => { return {value: b.id, text: b.identity}});
    },
    isNew() {
      return !this.currentItem.id;
    },
  }
}
</script>
