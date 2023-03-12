<template>
    <b-container class="section rounded">
        <b-row class="mt-4 mb-4">
            <b-colxx xxs="12" >
                <b-card>
                    <b-card-title>Kaydınız başarı ile gerçekleşti...</b-card-title>
                    <p>Bayi kodunuzu not almayı unutmayınız. <br/>Giriş yaparken bayi kodunuzu ve kayıtta belirttiğiniz şifrenizi kullanacaksınız.</p>
                    <b-alert show variant="success">
                        <dl>
                            <dt>Bayi Kodunuz:</dt>
                            <dd>{{bayiKodu}}</dd>
                        </dl>
                    </b-alert>
                    <div class="d-flex justify-content-end">
                        <b-button variant="secondary" @click="goHome" class="ml-3">{{$t('general.returnHome')}}</b-button>
                        <b-button variant="primary" @click="goLogin" class="ml-3">{{$t('login.title')}}</b-button>
                    </div>
                </b-card>
            </b-colxx>
        </b-row>
    </b-container>
</template>

<script>
export default {
    methods: {
        goLogin() {
            this.$router.push('/login');
        },
        goHome() {
            this.$router.push('/');
        }
    },
    computed: {
        registeredBayi() {
            return this.$store.getters.registeredBayi;
        },
        bayiKodu() {
            if(!this.registeredBayi) return;
            return this.registeredBayi.id;
        }
    }
}
</script>
