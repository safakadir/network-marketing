<template>
<b-card class="d-flex flex-row" no-body>
    <div class="pl-2 d-flex flex-grow-1 min-width-zero">
        <div class="card-body align-self-center d-flex flex-column flex-lg-row justify-content-between min-width-zero align-items-lg-center" 
            style="padding: 1.25rem;">

            <div class="d-flex justify-content-start align-items-center">
                <p class="list-item-heading mb-0 truncate">
                    {{item.id}}
                </p>
                <h4 class="mb-0 ml-4"><b-badge variant="primary" @click="openDurumModal" style="cursor:pointer;">{{$t('siparis.durum.'+item.siparisDurum)}}</b-badge></h4>
            </div>

            <div class=" d-flex flex-column align-items-center">
                <p class="mb-2 text-muted text-one">{{$t('odeme.'+item.odemeYontemi)}}</p>
                <p class="mb-0 text-large">{{item.odenenTutar}}₺</p>
            </div>
            <p class="mb-0 text-one">{{item.siparisVeren}}</p>
            <p class="mb-0 text-muted text-one">{{tarih}}</p>

            <b-button @click.prevent="showDetay" variant="outline-primary" class="ml-7">
                <i class="simple-icon-basket-loaded text-one" />
            </b-button>
        </div>
        <siparis-detay-modal ref="detaymodal" />
        <b-modal v-model="durumModalShown" :title="$t('siparis.durumGuncelle', {id:item.id})">
            <b-form-select v-model="selectedDurum" :options="durumOptions"></b-form-select>
            <template slot="modal-footer">
            <b-spinner variant="primary" v-if="durumLoading"></b-spinner>
            <b-button variant="secondary" @click="durumModalShown = false" :disabled="durumLoading">{{$t('general.cancel')}}</b-button>
            <b-button variant="primary" @click="modalDone" :disabled="durumLoading || !selectedDurum">{{$t('general.ok')}}</b-button>
            </template>
        </b-modal>
    </div>
</b-card>
</template>

<script>
import moment from 'moment'

import SiparisDetayModal from '@/containers/siparis/SiparisDetayModal'
import { serviceConfig } from '@/constants/config'

export default {
    components: {
        "siparis-detay-modal": SiparisDetayModal
    },
    props: ['item'],
    data() { return {
        durumModalShown: false,
        durumOptions: [
            {value:'ODEME_BEKLENIYOR', text: this.$t('siparis.durum.ODEME_BEKLENIYOR')},
            {value:'ODEME_ONAYLANDI', text: this.$t('siparis.durum.ODEME_ONAYLANDI')},
            {value:'HAZIRLANDI', text: this.$t('siparis.durum.HAZIRLANDI')},
            {value:'KARGOYA_VERILDI', text: this.$t('siparis.durum.KARGOYA_VERILDI')},
            {value:'TAMAMLANDI', text: this.$t('siparis.durum.TAMAMLANDI')},
        ],
        selectedDurum: null,
        durumLoading: false
    }},
    methods: {
        showDetay() {
            this.$refs.detaymodal.open(this.item);
        },
        openDurumModal() {
            this.durumModalShown = true;
            this.selectedDurum = this.item.siparisDurum;
        },
        modalDone() {
            if(!this.selectedDurum) return;
            this.durumLoading = true;
            this.$store.dispatch('siparis/updateSiparisDurum', {siparisId: this.item.id, durum: this.selectedDurum})
            .then(response => {
                this.durumModalShown = false;
                this.$emit('action');
            })
            .finally(() => {
                this.durumLoading = false;
            })
        }
    },
    computed: {
        tarih() {
            return moment(this.item.siparisTarihi).format('DD-MM-YYYY hh:mm');
        }
    }
}
</script>
