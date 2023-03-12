<template>
  <b-modal
    id="modalright"
    ref="modalright"
    :busy="loading"
    :title="isNew ? $t('urun.add-new-title') : $t('urun.edit-title', {name: currentItem.urunAdi})"
    modal-class="modal-right"
  >
    <b-form>
      <b-form-group v-if="isNew" :label="$t('urun.urunResim')" :description="$t('urun.urunResimDesc')">
        <vue-dropzone ref="myVueDropzone" id="dropzone" :options="dropzoneOptions" @vdropzone-success="imageUploaded"></vue-dropzone>
      </b-form-group>
      <b-form-group :label="$t('urun.urunAdi')">
        <b-form-input v-model="currentItem.urunAdi" />
      </b-form-group>
      <b-form-group :label="$t('urun.urunKodu')">
        <b-form-input v-model="currentItem.urunKodu" />
      </b-form-group>
      <b-form-group :label="$t('urun.kategori')">
        <b-form-select v-model="currentItem.kategori" :options="kategoriOptions" />
      </b-form-group>
      <b-form-group :label="$t('urun.netFiyat')" :description="$t('urun.kdvHaric')" >
        <b-form-input v-model="currentItem.netFiyat" type="number" @input="kdvHesapla" />
      </b-form-group>
      <b-form-group :label="$t('urun.kdvOrani')" :description="$t('urun.yuzdeOlarak')">
        <b-form-input v-model="currentItem.kdv" type="number" @input="kdvHesapla" />
      </b-form-group>
      <b-form-group :label="$t('urun.kdvMiktar')">
        <b-form-input v-model="currentItem.kdvMiktar" type="number" readonly />
      </b-form-group>
      <b-form-group :label="$t('urun.satisFiyat')" :description="$t('urun.kdvDahil')" >
        <b-form-input v-model="currentItem.satisFiyat" type="number" readonly />
      </b-form-group>
      <b-form-group :label="$t('common.pv')">
        <b-form-input v-model="currentItem.pv" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('common.cv')">
        <b-form-input v-model="currentItem.cv" type="number" no-wheel />
      </b-form-group>
      <b-form-group :label="$t('urun.aciklama')">
        <b-form-textarea v-model="currentItem.aciklama" rows="3" max-rows="7" />
      </b-form-group>
      <b-form-group v-if="isNew" :label="$t('urun.initialStok')">
        <b-form-input v-model="currentItem.urunStok.miktar" type="number" no-wheel />
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
import VueDropzone from "vue2-dropzone"
import service from '@/service'
import { serviceConfig } from '../../constants/config'

const NEW_ITEM = { kdv: 18, urunStok: {miktar:0} };

export default {
  components: {
    "vue-dropzone": VueDropzone
  },
  props: ['kategoriler'],
  data() { return {
    currentItem: this.$gutil.clone(NEW_ITEM),
    dropzoneOptions:  {
      url: serviceConfig.url+'/urun/upload',
      thumbnailHeight: 200,
      maxFilesize: 8,
      previewTemplate: this.dropzoneTemplate(),
      headers: {'Authorization': 'Bearer '+this.$store.getters.accessToken}
    },
    loading: false
  }},
  methods: {
    open(item) {
      if(!item) this.currentItem = this.$gutil.clone(NEW_ITEM);
      else this.currentItem = this.$gutil.clone(item);
      this.kdvHesapla();
      this.showModal('modalright');
    },
    showModal(refname) {
      this.$refs[refname].show();
    },
    hideModal(refname) {
      this.$refs[refname].hide();
    },
    saveItem() {
      this.loading = true;
      const method = this.isNew ? 'post' : 'put';
      service[method]('/urun', this.currentItem)
      .then(res => {
        this.currentItem = this.$gutil.clone(NEW_ITEM);
        this.hideModal("modalright");
        this.$emit('done');
      })
      .catch(err => {})
      .finally(() => {
        this.loading = false;
      })
    },
    kdvHesapla() {
      if(this.currentItem.netFiyat && this.currentItem.kdv) {
        this.currentItem.kdvMiktar = (this.currentItem.netFiyat*this.currentItem.kdv/100).normalize();
        this.currentItem.satisFiyat = (this.currentItem.netFiyat*1+this.currentItem.kdvMiktar*1).normalize();
      }
    },
    imageUploaded(file, response) {
      this.currentItem.img = response.filename;
    },
    dropzoneTemplate() {
      return `<div class="dz-preview dz-file-preview mb-3">
                  <div class="d-flex flex-row "> <div class="p-0 w-30 position-relative">
                      <div class="dz-error-mark"><span><i></i>  </span></div>
                      <div class="dz-success-mark"><span><i></i></span></div>
                      <div class="preview-container">
                        <img data-dz-thumbnail class="img-thumbnail border-0" />
                        <i class="simple-icon-doc preview-icon"></i>
                      </div>
                  </div>
                  <div class="pl-3 pt-2 pr-2 pb-1 w-70 dz-details position-relative">
                    <div> <span data-dz-name /> </div>
                    <div class="text-primary text-extra-small" data-dz-size /> </div>
                    <div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress></span></div>
                    <div class="dz-error-message"><span data-dz-errormessage></span></div>
                  </div>
                  <a href="#" class="remove" data-dz-remove> <i class="glyph-icon simple-icon-trash"></i> </a>
                </div>
        `;
    }
  },
  computed: {
    isNew() {
      return !this.currentItem.id;
    },
    kategoriOptions() {
      return this.kategoriler.map(k => { return {
        value: k,
        text: k.kategoriAdi
      }})
    },
  }
}
</script>
