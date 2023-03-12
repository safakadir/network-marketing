<template>
    <b-container class="section rounded">
        <b-row class="mt-4 mb-4">
            <b-colxx xxs="12" >
                <b-card>
                    <b-card-title>Siparişinizi Başarı ile Aldık...</b-card-title>
                    <b-alert show variant="success">
                    <dl>
                        <dt>Sipariş No:</dt>
                        <dd>{{siparis.id}}</dd>
                        <dt>Sipariş Tutarı:</dt>
                        <dd>{{siparis.odenenTutar}}</dd>
                        <dt>Sipariş Durumu:</dt>
                        <dd>{{$t('siparis.durum.'+siparis.siparisDurum)}}</dd>
                        <dt>Ödeme Yöntemi:</dt>
                        <dd>{{$t('odeme.'+siparis.odemeYontemi)}}</dd>
                        <dt>Sipariş Tarihi:</dt>
                        <dd>{{siparis.siparisTarihi}}</dd>
                    </dl>
                    </b-alert>
                    <b-alert show variant="warning" class="rounded">
                        Ödeme Yapmayı Unutmayınız
                    </b-alert>
                    <div class="d-flex justify-content-end">
                        <b-button variant="outline-primary" @click="goBack">{{$t('general.returnHome')}}</b-button>
                    </div>
                </b-card>
            </b-colxx>
        </b-row>
    </b-container>
</template>

<script>

export default {
    methods: {
        goBack() {
            this.$store.commit('clearSavedSiparis');
            this.$router.replace('/');
        }
    },
    computed: {
        siparis() {
            return this.$store.getters.savedSiparis;
        }
    }
}
</script>