<template>
<b-card class="d-flex flex-row" no-body>
    <a href="#" @click.prevent="editResim" class="d-flex">
        <img :src="imgSrc" class="list-thumbnail responsive border-0" :alt="item.urunAdi" />
    </a>
    <div class="pl-2 d-flex flex-grow-1 min-width-zero">
        <div class="card-body align-self-center d-flex flex-column flex-lg-row justify-content-between min-width-zero align-items-lg-center" style="padding: 1.25rem;">
            <p class="list-item-heading mb-0 truncate w-40 w-sm-100">
                <a href="#" @click.prevent="showAciklama">{{urunIdentity}}</a>
            </p>
            <p class="mb-0 text-muted text-small w-10 w-sm-100">{{item.kategori.kategoriAdi}}</p>
            <div class="mb-0 w-10 w-sm-100 d-flex justify-content-start">
                <div class="d-flex flex-column align-items-center">
                <p class="mb-0 text-muted text-small">{{$t('urun.stokMiktar')}}:</p>
                <p class="mb-0 text-muted text-one">{{item.urunStok.miktar}}</p>
                </div>
            </div>
            <p class="mb-0 text-large w-15 w-sm-100">{{item.satisFiyat}}₺</p>
            <div class="w-10 w-sm-100 d-flex flex-column align-items-start">
                <p class="mb-0 text-muted text-small ">{{item.pv+' '+$t('common.pv')}}</p>
                <p class="mb-0 text-muted text-small ">{{item.cv+' '+$t('common.cv')}}</p>
            </div>
            <div class="w-15 w-sm-100 d-flex justify-content-end">
                <b-button :variant="item.starred ? 'warning' : 'outline-warning'" @click="toggleStarred">
                    <i class="glyph-icon simple-icon-star" /> 
                </b-button>
                <b-button-group class="ml-3">
                    <b-button variant="warning" size="xs" @click="edit">
                    <i class="glyph-icon simple-icon-pencil" /> 
                    </b-button>
                    <b-button variant="danger" size="xs" @click="remove">
                    <i class="glyph-icon simple-icon-trash" /> 
                    </b-button>
                </b-button-group>
            </div>
        </div>
        <b-modal :ref="'aciklamamodal-'+item.id"  :title="modalTitle">
            {{item.aciklama}}
            <template slot="modal-footer">
                <b-button variant="primary" @click="$refs['aciklamamodal-'+item.id].hide()">{{$t('general.ok')}}</b-button>
            </template>
        </b-modal>
    </div>
</b-card>
</template>

<script>
import { serviceConfig } from '../../constants/config';
export default {
    props: ['item'],
    data() { return {
    }},
    methods: {
        showAciklama() {
            this.$refs['aciklamamodal-'+this.item.id].show();
        },
        edit() {
            this.$emit('edit', this.item);
        },
        remove() {
            this.$emit('remove', this.item);
        },
        editResim() {
            this.$emit('edit-resim', this.item);
        },
        toggleStarred() {
            this.$emit('toggle-starred', this.item);
        }
    },
    computed: {
        urunIdentity() {
            return this.item.urunAdi+' - '+this.item.urunKodu;
        },
        modalTitle() {
            return this.urunIdentity+' '+this.$t('urun.aciklama')
        },
        imgSrc() {
            if(!this.item || !this.item.img) return '/assets/img/noimage.png'
            return serviceConfig.imgPrefix+this.item.img;
        }
    }
}
</script>
