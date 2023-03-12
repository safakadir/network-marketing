<template>
<b-card>
    <b-card-title>{{$t('odeme.odemeYontemi')}}</b-card-title>
    <b-form-group>
        <b-form-radio v-model="odemeYontemi" name="odemeYontemi" value="banka">{{$t('odeme.banka')}}</b-form-radio>
        <b-form-radio v-model="odemeYontemi" name="odemeYontemi" value="kart">{{$t('odeme.kart')}}</b-form-radio>
    </b-form-group>
    <div v-show="odemeYontemi == 'banka'">
        <dl>
            <dt>{{$t('yonetim.bankaAdi')}}:</dt>
            <dd>{{bankaAdi}}</dd>
            <dt>{{$t('yonetim.bankaAlici')}}:</dt>
            <dd>{{bankaAlici}}</dd>
            <dt>{{$t('yonetim.bankaIban')}}:</dt>
            <dd>{{bankaIban}}</dd>
            <dt>{{$t('yonetim.bankaNotlar')}}:</dt>
            <dd>{{bankaNotlar}}</dd>
        </dl>
    </div>
    <div v-show="odemeYontemi == 'kart'">
        <b-alert show variant="danger" class="rounded">Kredi kartı ile ödeme şu an aktif değildir.</b-alert>
        <b-form>
            <b-form-group :label="$t('odeme.ccname')">
                <b-form-input name="ccname" v-model="ccname" />
            </b-form-group>
            <b-form-group :label="$t('odeme.cardnumber')">
                <b-form-input name="cardnumber" v-model="cardnumber" />
            </b-form-group>
            <b-row>
                <b-colxx xxs="8">
                    <b-form-group :label="$t('odeme.ccdate')">
                        <b-form-input name="ccmonth" v-model="ccmonth" type="number" :placeholder="$t('odeme.monthPlaceholder')" style="width:100px; display:inline-block;" />
                        <b-form-input name="ccyear" v-model="ccyear" type="number" :placeholder="$t('odeme.yearPlaceholder')" style="width:100px; display:inline-block;" />
                    </b-form-group>
                </b-colxx>
                <b-colxx xxs="4">
                    <b-form-group :label="$t('odeme.cvc')">
                        <b-form-input name="cvc" v-model="cvc" type="number" no-wheel />
                    </b-form-group>
                </b-colxx>
            </b-row>
        </b-form>
    </div>
</b-card>
</template>

<script>
export default {
    data() { return {
        odemeYontemi: 'banka',
        ccname: null,
        cardnumber: null,
        ccmonth: null,
        ccyear: null,
        cvc: null,
    }},
    methods: {
        getPaymentInfo() {
            return {
                type: this.odemeYontemi,
                ccname: this.ccname,
                cardnumber: this.cardnumber,
                ccmonth: this.ccmonth,
                ccyear: this.ccyear,
                cvc: this.cvc,
            }
        }
    },
    computed: {
        bankaAdi() { return this.$store.getters['settings/settingMap'].bankaAdi; },
        bankaAlici() { return this.$store.getters['settings/settingMap'].bankaAlici; },
        bankaIban() { return this.$store.getters['settings/settingMap'].bankaIban; },
        bankaNotlar() { return this.$store.getters['settings/settingMap'].bankaNotlar; },
    },
    watch: {
        odemeYontemi(value) {
            this.$emit('yontem-select', value);
        }
    },
    mounted() {
        this.$emit('yontem-select', this.odemeYontemi);
    }
}
</script>