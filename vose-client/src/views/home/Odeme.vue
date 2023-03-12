<template>
    <b-container class="section rounded">
        <b-row class="mt-4 mb-4">
            <b-colxx xxs="12" lg="6">
                <b-card v-if="!isLogin" class="mb-4">
                    <b-card-title>{{$t('odeme.faturaBilgileri')}}</b-card-title>
                    <b-form-group :label="$t('odeme.isimSoyisim')">
                        <b-form-input v-model="manuelIsimSoyisim" />
                    </b-form-group>
                    <b-form-group>
                        <b-form-checkbox :checked="true" :disabled="true">{{$t('odeme.faturaKargoAyniAdres')}}</b-form-checkbox>
                    </b-form-group>
                </b-card>
                <b-card>
                    <b-card-title>{{$t('adres.adres')}}</b-card-title>
                    <template v-if="isLogin">
                        <b-form-select :options="adresOptions" v-model="adres" :placeholder="$t('adres.selectAdres')" class="mb-4" />
                        <template v-if="adresSelected">
                            <p class="mb-1">{{adres.adres}}</p>
                            <p>{{adres.postaKodu}} {{adres.ilce}}/{{adres.il}}, {{adres.ulke}}</p>
                        </template>
                        <div class="d-flex justify-content-end mt-4">
                            <b-button v-if="adresSelected" variant="outline-secondary" @click="editAdres">{{$t('general.edit')}}</b-button>
                            <b-button class="ml-3" variant="outline-primary" @click="addAdres">{{$t('general.add-new')}}</b-button>
                        </div>
                        <adres-form-modal ref="adresModal" @done="adresDone" />
                    </template>
                    <template v-else>
                        <adres-form ref="adresForm" />
                    </template>
                </b-card>
            </b-colxx>
            <b-colxx xxs="12" lg="6">
                <payment-form ref="paymentForm" @yontem-select="odemeYontemiSelected" />
            </b-colxx>
        </b-row>
        <div class="d-flex flex-column">
            <div class="d-flex justify-content-end">
                <b-form-group id="checkbox-group" class="d-flex">
                    <b-form-checkbox v-model="kabulMesafeliSatis" value="accepted"><span v-html="$t('yonetim.sozlesme.okudumMesafeliSatis', {url: '/sozlesme/mesafeli-satis'})" /></b-form-checkbox>
                    <b-form-checkbox v-model="kabulGizlilik" value="accepted"><span v-html="$t('yonetim.sozlesme.okudumGizlilik', {url: '/sozlesme/mesafeli-satis'})" /></b-form-checkbox>
                    <span v-html="$t('yonetim.sozlesme.okudumIade', {url: '/sozlesme/iade'})" />
                </b-form-group>
            </div>

            <div class="d-flex justify-content-end">
                <state-button variant="primary" id="siparisTamamlaBtn" class="mb-3" :click="siparisiTamamla" :disabled="!canComplete">
                    <div class="d-flex align-items-center justify-content-between">
                        <i class="glyph-icon iconsminds-coins mr-2" style="font-size:1.7rem;"/> 
                        {{$t('sepet.siparisiTamamla')}}
                        <h5 class="mb-0 ml-2"><b-badge variant="light">{{sepetOdenecekTutar}} ₺</b-badge></h5>
                    </div>
                </state-button>
            </div>
        </div>
        <b-row class="mt-4">
            <b-colxx xxs="12">
                <b-card>
                    <b-card-title>{{$t('sepet.siparisOzeti')}}</b-card-title>
                    <b-table :items="siparisItems" :fields="siparisFieldsCmp" striped responsive foot-clone>
                        <template v-slot:foot(urunKodu)>{{$t('general.total')}}</template>
                        <template v-slot:foot(urunAdi)>&nbsp;</template>
                        <template v-slot:foot(adet)>&nbsp;</template>
                        <template v-slot:foot(birimNetFiyat)>&nbsp;</template>
                        <template v-slot:foot(birimKdv)>&nbsp;</template>
                        <template v-slot:foot(birimSatisFiyat)>&nbsp;</template>
                        <template v-slot:foot(birimIndirimliFiyat)>&nbsp;</template>
                        <template v-slot:foot(toplamFiyat)>{{sepetOdenecekTutar}}</template>
                        <template v-slot:foot(birimPvCv)>&nbsp;</template>
                        <template v-slot:foot(toplamPvCv)>{{sepetToplamPv}} / {{sepetToplamCv}}</template>
                    </b-table>
                </b-card>
            </b-colxx>
        </b-row>
    </b-container>
</template>

<script>
import { mapGetters } from 'vuex'
import StateButton from "@/components/Common/StateButton";

import OdemeBilgileri from '@/containers/OdemeBilgileri'
import AdresFormModal from '@/containers/AdresFormModal'
import AdresForm from '@/containers/AdresForm'

export default {
    components: {
        'payment-form': OdemeBilgileri,
        'adres-form-modal': AdresFormModal,
        'adres-form': AdresForm,
        'state-button': StateButton
    },
    data() { return {
       siparisFields: [
           {key: 'urunKodu', label: this.$t('urun.urunKodu')},
           {key: 'urunAdi', label: this.$t('urun.urunAdi')},
           {key: 'adet', label: this.$t('sepet.adetUpper')},
           {key: 'birimNetFiyat', label: this.$t('sepet.birimNetFiyat')},
           {key: 'birimKdv', label: this.$t('urun.kdv')},
           {key: 'birimIndirimliFiyat', label: this.$t('sepet.birimFiyat')},
           {key: 'birimSatisFiyat', label: this.$t('sepet.birimIndirimsizFiyat')},
           {key: 'toplamFiyat', label: this.$t('sepet.toplamFiyat')},
           {key: 'birimPvCv', label: this.$t('sepet.birimPvCv')},
           {key: 'toplamPvCv', label: this.$t('sepet.toplamPvCv')},
       ],
       adres: null,
       odemeYontemi: null,
       manuelIsimSoyisim: null,
       kabulMesafeliSatis: null,
       kabulGizlilik: null
    }},
    methods: {
        siparisiTamamla() {
            const adres = this.getAdresCmp()
            if(!adres) return Promise.resolve();
            const paymentInfo = this.$refs.paymentForm.getPaymentInfo();
            return this.$store.dispatch('saveSiparis', { odemeYontemi: this.odemeYontemi, adres: adres, isimSoyisim: this.isimSoyisimCmp })
            .then(() => {
                this.$router.push('/odemebasarili');
            })
            .catch(err => {});
        },
        addAdres() {
            this.$refs.adresModal.open(null, this.adresTemplate);
        },
        editAdres() {
            this.$refs.adresModal.open(this.adres);
        },
        adresDone(adres) {
            this.adres = this.adresOptions.find(a => a.value.id == adres.id).value;
        },
        odemeYontemiSelected(yontem) {
            this.odemeYontemi = yontem;
        },
        getAdresCmp() {
            let adres = this.adres;
            if(this.$gutil.isEmpty(adres)) adres = this.$refs.adresForm.getAdres();
            return adres;
        },
    },
    computed: {
        ...mapGetters([
            'sepetUrunler',
            'sepetCount',
            'sepetUrunCount',
            'sepetToplamNetIndirimli',
            'sepetToplamKdvIndirimli',
            'sepetToplamFiyatIndirimsiz',
            'sepetToplamFiyat',
            'sepetKargoUcret',
            'sepetOdenecekTutar',
            'sepetToplamPv',
            'sepetToplamCv'
        ]),
        adresOptions() {
            return this.$store.getters['adres/items'].map(a => {
                return {
                    value: a,
                    text: a.baslik
                }
            });
        },
        siparisFieldsCmp() {
            if(this.isLogin) return this.siparisFields;
            else return this.siparisFields.slice(0, this.siparisFields.length-2);
        },
        siparisItems() {
            return this.sepetUrunler.map(u => {
                return {
                    urunKodu: u.urun.urunKodu,
                    urunAdi: u.urun.urunAdi,
                    adet: u.miktar,
                    birimNetFiyat: u.urun.indirimliNetFiyat,
                    birimKdv: u.urun.indirimliKdvMiktar,
                    birimSatisFiyat: u.urun.satisFiyat,
                    birimIndirimliFiyat: u.urun.indirimliSatisFiyat,
                    toplamFiyat: u.urun.indirimliSatisFiyat*u.miktar,
                    birimPvCv: u.urun.pv+' / '+u.urun.cv,
                    toplamPvCv: u.urun.pv*u.miktar + ' / ' + u.urun.cv*u.miktar,
                }
            })
        },
        adresSelected() {
            return this.adres && !!this.adres.id;
        },
        isLogin() {
            return !!this.$store.getters.login;
        },
        bayi() {
            return this.$store.getters.bayi;
        },
        adresTemplate() {
            return {
                aliciAdi: this.bayi.isimSoyisim,
                aliciTel: this.bayi.telefon1,
                il: this.bayi.sehir,
                ulke: this.bayi.ulke
            }
        },
        isimSoyisimCmp() {
            return this.isLogin ? this.$store.getters.bayi.isimSoyisim : this.manuelIsimSoyisim; 
        },
        odemeUygun() {
            return !!this.odemeYontemi && this.odemeYontemi!='kart';
        },
        canComplete() {
            return this.odemeUygun && this.kabulMesafeliSatis == 'accepted' && this.kabulGizlilik == 'accepted';
        },
    },
    mounted() {
        this.$store.dispatch('settings/fetch', 'banka');
        if(this.isLogin) {
            this.$store.dispatch('adres/fetch', this.$store.getters.bayiId)
            .then((adresler) => {
                this.adres = adresler[0];
            })
        }
    }
}
</script>
