<template>
    <b-modal ref="detaymodal" size="lg" :title="title" hide-footer>
        <b-table ref="table" :items="siparis.siparisItems" :fields="fields" hover>
            <template v-slot:cell(urunKodu)="row">
                {{getUrunProp(row.item.urunId, 'urunKodu')}}
            </template>
            <template v-slot:cell(urunAdi)="row">
                {{getUrunProp(row.item.urunId, 'urunAdi')}}
            </template>
            <template v-slot:cell(miktar)="row">
                {{row.item.miktar}}
            </template>
        </b-table>
        <div>
            <p class="mb-1">{{adres.aliciAdi}} - {{adres.aliciTel}}</p>
            <p class="mb-1">{{adres.adres}}</p>
            <p>{{adres.postaKodu}} {{adres.ilce}}/{{adres.il}}, {{adres.ulke}}</p>
        </div>
        <div class="loading" v-if="loading" />
        <!--<div style="font-size:14pt; margin-bottom:10px;">
            <span><strong>{{$t('bayi.totalPv')}}:</strong> {{bayi.totalPv}}</span>
            <span style="margin-left: 10px;"><strong>{{$t('bayi.totalCv')}}:</strong> {{bayi.totalCv}}</span>
        </div> -->
    </b-modal>
</template>

<script>
import service from '@/service'

export default {
    data() { return {
        siparis: {},
        fields: [
            {key: "urunKodu", label: this.$t('urun.urunKodu')},
            {key: "urunAdi", label: this.$t('urun.urunAdi')},
            {key: "miktar", label: this.$t('siparis.miktar')}
        ],
        loading: false,
        urunMap: {}
    }},
    methods: {
        open(siparis) {
            this.siparis = siparis;
            this.loadUrunler();
            this.showModal();
        },
        loadUrunler() {
            this.loading = true;
            const ids = this.siparis.siparisItems.map(item => item.urunId).join(',');
            service.get('/urun/many?ids='+ids)
            .then(res => {
                if(!res.data || !res.data.length ) return;
                for(let urun of res.data) {
                    this.urunMap[urun.id] = urun;
                }
                this.$refs.table.refresh();
            })
            .finally(() => {
                this.loading = false;
            })
        },
        showModal() {
            this.$refs['detaymodal'].show();
        },
        hideModal() {
            this.$refs['detaymodal'].hide();
        },
        getUrunProp(urunId, propName) {
            return this.$gutil.getProperty(this.urunMap, urunId+'.'+propName);
        }
    },
    computed: {
        title() {
            if(!this.siparis || !this.siparis.id) return "";
            return this.siparis.id+" - "+this.$t("siparis.detay");
        },
        adres() {
            if(!this.siparis || !this.siparis.adres) return {};
            return this.siparis.adres;
        }
    }
}
</script>