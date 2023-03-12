<template>
<main style="margin-left:60px;">
<div class="container-fluid">
    <div>
    <b-row>
        <b-colxx xxs="12" class="mt-4 mb-4 rounded">
            <div class="loading" v-if="loading" />
            <b-card no-body class="d-flex mb-3" key="iletisim">
                <div class="d-flex flex-grow-1 min-width-zero" role="tab">
                    <b-button href="#" class="card-body  btn-empty btn-link list-item-heading text-left text-one" v-b-toggle="`iletisim`" variant="link">{{$t('common.contact')}}</b-button>
                </div>
                <b-collapse id="iletisim" :visible="indexFromPath == 0" accordion="faq-accordion" role="tabpanel">
                    <div class="card-body accordion-content pt-0">
                        <strong>{{$t('yonetim.telefon')}}</strong>: {{iletisimTelefon}}<br/>
                        <strong>{{$t('yonetim.email')}}</strong>: {{iletisimEmail}}<br/>
                        <strong>{{$t('yonetim.lokasyon')}}</strong>: {{iletisimLokasyon}}<br/>
                        <strong>{{$t('yonetim.calismaSaatleri')}}</strong>: {{iletisimCalismaSaatleri}}
                    </div>
                </b-collapse>
            </b-card>
            <b-card no-body class="d-flex mb-3" v-for="(item,index) in infoData" :key="`faq_${index}`">
                <div class="d-flex flex-grow-1 min-width-zero" role="tab">
                    <b-button href="#" class="card-body  btn-empty btn-link list-item-heading text-left text-one" v-b-toggle="`faq_${index}`" variant="link">{{item.title}}</b-button>
                </div>
                <b-collapse :id="`faq_${index}`" :visible="index+1 == indexFromPath" accordion="faq-accordion" role="tabpanel">
                    <div class="card-body accordion-content pt-0" v-html="item.description" />
                </b-collapse>
            </b-card>
        </b-colxx>
    </b-row>
    </div>
</div>
</main>
</template>

<script>
import infoData from "@/data/vose"

export default {
    data() { return {
        infoData,
        loading: false
    }},
    computed: {
        iletisimTelefon() {
            return this.$store.getters['settings/getByName']('iletisimTelefon');
        },
        iletisimEmail() {
            return this.$store.getters['settings/getByName']('iletisimEmail');
        },
        iletisimLokasyon() {
            return this.$store.getters['settings/getByName']('iletisimLokasyon');
        },
        iletisimCalismaSaatleri() {
            return this.$store.getters['settings/getByName']('iletisimCalismaSaatleri');
        },
        indexFromPath() {
            if(!this.$route.params.index) return 0;
            return parseInt(this.$route.params.index);
        }
    },
    mounted() {
        this.loading = true;
        this.$store.dispatch('settings/fetch', 'iletisim')
        .finally(() => { this.loading = false; });
    }
}
</script>
