<template>
<b-card class="d-flex flex-row" no-body>
    <a href="#" @click.prevent="editResim" class="d-flex">
        <img :src="imgSrc" class="list-thumbnail responsive border-0" :alt="item.urunAdi" />
    </a>
    <div class="pl-2 d-flex flex-grow-1 min-width-zero">
        <div class="card-body align-self-center d-flex flex-column flex-lg-row justify-content-between min-width-zero align-items-lg-center" style="padding: 1rem;">
            <div class="d-flex flex-column align-items-center jsutify-content-around w-20 w-sm-100">
                <p class="list-item-heading truncate mb-0 text-one">{{item.urunAdi}}</p>
                <p class="mb-0 text-muted text-small">{{item.kategori.kategoriAdi}}</p>
            </div>
            <div class="w-15 w-sm-100 text-center">
                <p v-if="indirimPresent" class="mb-0 text-small text-muted line-through">{{item.satisFiyat}}₺</p>
                <p class="mb-0 text-one">{{item.indirimliSatisFiyat}}₺</p>
            </div>
            <div class="mb-0 w-15 w-sm-100 d-flex align-items-center">
                <b-form-input v-model="urunMiktar" size="sm" class="input-mini mr-1"/> {{$t('sepet.adet')}} 
            </div>
            <div class="w-15 w-sm-100">
                <p v-if="indirimPresent" class="mb-0 text-small text-muted line-through">{{indirimsizToplamFiyat}}₺</p>
                <p class="mb-0 text-one ">{{toplamFiyat}}₺</p>
            </div>
            <div class="w-10 w-sm-100 d-flex justify-content-end">
                    <b-button variant="outline-dark" size="xs" @click="remove">
                    <i class="glyph-icon simple-icon-trash" /> 
                    </b-button>
            </div>
        </div>
    </div>
</b-card>
</template>

<script>
import { serviceConfig } from '../../constants/config';
export default {
    props: ['sepetUrun'],
    methods: {
        remove() {
            this.$store.commit('removeSepetUrun', this.sepetUrun.siraNo);
        },
    },
    computed: {
        item() {
            return this.sepetUrun.urun;
        },
        imgSrc() {
            if(!this.item || !this.item.img) return '/assets/img/noimage.png'
            return serviceConfig.imgPrefix+this.item.img;
        },
        indirimsizToplamFiyat() {
            return (this.item.satisFiyat*this.sepetUrun.miktar).normalize();
        },
        toplamFiyat() {
            return (this.item.indirimliSatisFiyat*this.sepetUrun.miktar).normalize();
        },
        urunMiktar: {
            get() {
                return this.sepetUrun.miktar;
            },
            set(value) {
                this.$store.commit('changeSepetUrunMiktar', {siraNo: this.sepetUrun.siraNo, miktar: value})
            }
        },
        isLogin() {
            return !!this.$store.getters.login;
        },
        indirimPresent() {
            return this.isLogin && this.$store.getters.indirim > 0;
        },
    }
}
</script>
