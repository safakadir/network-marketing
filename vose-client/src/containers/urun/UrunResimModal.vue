<template>
    <b-modal id="resimmodal" ref="resimmodal" :busy="loading" :title="title">
        <img :src="imgSrc" class="responsive border-0 border-radius img-fluid mb-3" />
        <vue-dropzone ref="myVueDropzone" id="dropzone" :options="dropzoneOptions" @vdropzone-success="imageUploaded"></vue-dropzone>
        <template slot="modal-footer">
            <b-button variant="primary" @click="saveItem()" class="mr-1">{{$t('general.save')}}</b-button>
            <b-button variant="secondary" @click="hideModal()">{{$t('general.cancel')}}</b-button>
        </template>
    </b-modal>
</template>

<script>
import service from '@/service'
import VueDropzone from "vue2-dropzone"
import { serviceConfig } from '../../constants/config';

export default {
    components: {
        "vue-dropzone": VueDropzone
    },
    data() { return {
        item: {},
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
            this.item = item;
            this.filename = item.img;
            this.showModal();
        },
        showModal() {
            this.$refs['resimmodal'].show();
        },
        hideModal() {
            this.$refs['resimmodal'].hide();
        },
        saveItem() {
            this.item.img = this.filename;
            this.loading = true;
            service.put('/urun', this.item)
            .then(res => {
                this.hideModal();
                this.$emit('done');
            })
            .catch(err => {})
            .finally(() => {
                this.loading = false;
            });
        },
        imageUploaded(file, response) {
            this.filename = response.filename;
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
        title() {
            if(!this.item || !this.item.id) return "";
            return this.item.urunAdi+" - "+this.$t("urun.resim");
        },
        imgSrc() {
            if(!this.item || !this.item.img) return '/assets/img/noimage.png'
            return serviceConfig.imgPrefix+this.item.img;
        }
    }
}
</script>
