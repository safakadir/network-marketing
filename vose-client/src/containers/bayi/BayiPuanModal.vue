<template>
    <b-modal ref="puanmodal" size="lg" :title="title" hide-footer>
        <b-table :items="puanlar" :fields="fields" hover foot-clone>
            <template v-slot:foot(altBayi)>{{$t('bayi.totalPuan')}}</template>
            <template v-slot:foot(pv)>{{bayi.totalPv}}</template>
            <template v-slot:foot(cv)>{{bayi.totalCv}}</template>
        </b-table>
        <!--<div style="font-size:14pt; margin-bottom:10px;">
            <span><strong>{{$t('bayi.totalPv')}}:</strong> {{bayi.totalPv}}</span>
            <span style="margin-left: 10px;"><strong>{{$t('bayi.totalCv')}}:</strong> {{bayi.totalCv}}</span>
        </div> -->
    </b-modal>
</template>

<script>
export default {
    data() { return {
        bayi: {},
        fields: [
            {key:"altBayi", label: this.$t("bayi.altBayi")},
            {key:"pv", label: this.$t("common.pv")},
            {key:"cv", label: this.$t("common.cv")}
        ]
    }},
    methods: {
        open(bayi) {
            this.bayi = bayi;
            this.showModal();
        },
        showModal() {
            this.$refs['puanmodal'].show();
        },
        hideModal() {
            this.$refs['puanmodal'].hide();
        },
        identityOf(bayiId) {
            const altBayi = this.bayi.altBayiler.find(b => b.id == bayiId);
            return this.$gutil.getProperty(altBayi, 'identity');
        },
    },
    computed: {
        title() {
            if(!this.bayi || !this.bayi.id) return "";
            return this.bayi.identity+" - "+this.$t("bayi.puanDistribution");
        },
        puanlar() {
            if(!this.bayi || !this.bayi.puanlar) return [];
            const result = this.bayi.puanlar.map(p => {
                return {
                    altBayi: this.identityOf(p.altBayiId),
                    pv: p.pv.normalize(),
                    cv: p.cv.normalize()
                }
            })
            /*result.push({
                altBayi: this.$t('general.total'),
                pv: this.bayi.totalPv, 
                cv: this.bayi.totalCv
            });*/
            return result;
        }
    }
}
</script>