<template>
<b-card no-body>
    <div class="position-relative">
        <div class="w-100">
            <img :src="imgSrc" class="card-img-top" :alt="item.urunAdi" />
        </div>
        <b-badge pill :variant="statusColor" class="position-absolute badge-top-left">{{ status }}</b-badge>
        <b-badge v-if="indirimPresent" pill variant="secondary" class="position-absolute badge-top-left-2">%{{ indirimOrani }} {{$t('common.indirim')}}</b-badge>
    </div>
    <b-card-body>
        <b-row>
            <b-colxx xxs="12" class="mb-3">
                <h3 class="card-title mb-0">{{item.urunAdi}}</h3>
                <h6 class="card-subtitle mb-0">{{item.kategori.kategoriAdi}}</h6>
            </b-colxx>
            <b-colxx xxs="12" class="mb-3">
                <b-button size="xs" variant="outline-dark" @click="showAciklama = !showAciklama">{{ $t('urun.aciklama') }}</b-button>
                <b-collapse id="collapse1" v-model="showAciklama">
                    <div class="p-4 border mt-4">
                        <p class="mb-0">
                            {{item.aciklama}}
                        </p>
                    </div>
                </b-collapse>
            </b-colxx>
        </b-row>
        <b-row class="justify-content-around align-items-center">
            <span class="w-30 w-sm-50 d-flex justify-content-center">
                <div v-if="indirimPresent">
                    <p class="mb-0 line-through">{{item.satisFiyat}}₺</p>
                    <h3 class="mb-0">{{indirimliFiyat}}₺</h3>
                </div>
                <h3 v-else class="mb-0">{{item.satisFiyat}}₺</h3>
            </span>
            <span class="w-40 w-sm-0">
            </span>
            <span class="w-30 w-sm-50 d-flex justify-content-end align-items-center">
                <template v-if="isStokta">
                    <div class="d-flex flex-column align-items-center"> 
                        <div style="height:11px;" />
                        <b-form-input v-model="miktar" size="sm" class="input-mini" type="number" /> 
                        <span class="text-small">{{$t('sepet.adet')}}</span>
                    </div>
                    <span class="text-primary ml-2 clickable" @click="sepeteEkle" >
                        <i class="glyph-icon iconsminds-add-cart" style="font-size:1.7rem;" /> 
                    </span>
                </template>
                <p v-else class="text-semi-muted mb-0 mr-4" style="white-space: nowrap;">{{$t('urun.stoktaYok')}}</p>
            </span>
        </b-row>
    </b-card-body>
</b-card>
</template>

<script>
import { serviceConfig } from '../../constants/config';
export default {
    props: ['item'],
    data() { return {
        miktar: 1,
        showAciklama: false,
    }},
    methods: {
        sepeteEkle() {
            const sepetUrun = {miktar: this.miktar, urun: this.item, indirim: this.$store.getters.indirim};
            this.$store.commit('addSepetUrun', sepetUrun);
            this.$notify('success', this.$t('sepet.eklendi'), this.sepetMesaj(), { duration: 3000} );
        },
        sepetMesaj() {
            return this.miktar+' '+this.$t('sepet.adet')+', '+this.item.urunAdi + 
                '<br/><a href="/sepet">'+this.$t('sepet.sepeteGit')+'</a>';
        }
    }, 
    computed: {
        imgSrc() {
            if(!this.item || !this.item.img) return '/assets/img/noimage.png';
            return serviceConfig.imgPrefix+this.item.img;
        },
        isStokta() {
            return this.item.urunStok.miktar > 0;
        },
        status() {
            if(this.isStokta)
                return this.$t('urun.stokta');
            else
                return this.$t('urun.stoktaYok');
        },
        statusColor() {
            if(this.isStokta)
                return 'primary';
            else
                return 'danger';
        },
        indirimOrani() {
            return this.$store.getters.indirim;
        },
        indirimliFiyat() {
            return (this.item.satisFiyat * (100-this.indirimOrani) / 100).normalize();
        },
        isLogin() {
            return !!this.$store.getters.login;
        },
        indirimPresent() {
            return this.isLogin && this.indirimOrani > 0;
        },
    }
}
</script>
