<template>
<b-row class="section h-100 mt-5 register">
    <b-colxx xxs="12" md="10" class="mx-auto my-auto">
        <b-card class="auth-card" no-body>
            <div class="position-relative image-side">
                <h3 class="text-white">{{$t('login.vose')}}</h3>
                <p class="white mb-0">
                    {{$t('login.registerDesc')}}
                    <br />
                    {{$t('login.loginRedirect')}}
                    <router-link tag="a" to="/login" class="white-underline-link">{{$t('login.girisYapin')}}</router-link>
                </p>
            </div>
            <div class="form-side">
                <router-link tag="a" to="/">
                    <span class="logo-single" />
                </router-link>
                <h6 class="mb-4">{{ $t('login.register')}}</h6>

                <b-form @submit.prevent="formSubmit" class="av-tooltip tooltip-label-bottom">
                    <b-form-group :label="$t('bayi.tckn')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.tckn.$model" :state="!$v.form.tckn.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.tckn.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.tckn.numeric">{{$t('common.validation.numeric')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.tckn.minLength">{{$t('bayi.validation.tcknLength')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.tckn.maxLength">{{$t('bayi.validation.tcknLength')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.tckn.tcknRule">{{$t('bayi.validation.tcknInvalid')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.isim')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.isim.$model" :state="!$v.form.isim.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.isim.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.isim.alphaRule">{{$t('common.validation.alpha')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.isim.minLength">{{$t('common.validation.tooShort')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.soyisim')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.soyisim.$model" :state="!$v.form.soyisim.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.soyisim.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.soyisim.alphaRule">{{$t('common.validation.alpha')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.soyisim.minLength">{{$t('common.validation.tooShort')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.sifre')" class="has-float-label mb-4">
                        <b-form-input type="password" v-model="$v.form.sifre.$model" :state="!$v.form.sifre.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.sifre.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.sifre.minLength">{{$t('bayi.validation.sifreLength')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.sifre.maxLength">{{$t('bayi.validation.sifreLength')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.sifreTekrar')" class="has-float-label mb-4">
                        <b-form-input type="password" v-model="$v.form.sifreTekrar.$model" :state="!$v.form.sifreTekrar.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.sifreTekrar.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.sifreTekrar.sameAs">{{$t('bayi.validation.sifreTekrarSameAs')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.paket')" class="has-float-label mb-4">
                        <b-form-select v-model="$v.form.kariyer.$model" :options="paketOptions" />
                        <b-form-invalid-feedback v-if="!$v.form.kariyer.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.sponsorKodu')" class="has-float-label mb-4" :disabled="!selfRegister" :description="selfRegister ? $t('bayi.sponsorHint') : ''">
                        <b-form-input type="text" v-model="$v.form.sponsorId.$model" :state="!$v.form.sponsorId.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.sponsorId.numeric">{{$t('common.validation.numeric')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.email')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.email.$model" :state="!$v.form.email.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.email.email">{{$t('bayi.validation.emailInvalid')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.telefon1')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.telefon1.$model" :state="!$v.form.telefon1.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.telefon1.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-if="!$v.form.telefon1.numeric">{{$t('common.validation.numeric')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.telefon2')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.telefon2.$model" :state="!$v.form.telefon2.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.telefon2.numeric">{{$t('common.validation.numeric')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.dogumTarihi')" class="has-float-label mb-4">
                        <b-form-input type="date" v-model="$v.form.dogumTarihi.$model" :state="!$v.form.dogumTarihi.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.dogumTarihi.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.ulke')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.ulke.$model" :state="!$v.form.ulke.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.ulke.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-if="!$v.form.ulke.alphaRule">{{$t('common.validation.alpha')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('bayi.sehir')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.sehir.$model" :state="!$v.form.sehir.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.sehir.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-if="!$v.form.sehir.alphaRule">{{$t('common.validation.alpha')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group class="mb-4">
                        <b-form-checkbox v-model="$v.form.sozlesmeKayit.$model" value="accepted">
                            <span v-html="$t('yonetim.sozlesme.okudumKayit', {url: '/sozlesme/kayit'})" />
                        </b-form-checkbox>
                        <b-form-invalid-feedback v-if="!$v.form.sozlesmeKayit.sameAs">{{$t('common.validation.acceptTerms')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group class="mb-4">
                        <b-form-checkbox v-model="$v.form.sozlesmeGizlilik.$model" value="accepted">
                            <span v-html="$t('yonetim.sozlesme.okudumGizlilik', {url: '/sozlesme/gizlilik'})" />
                        </b-form-checkbox>
                        <b-form-invalid-feedback v-if="!$v.form.sozlesmeGizlilik.sameAs">{{$t('common.validation.acceptTerms')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <div class="d-flex justify-content-between align-items-center">
                        <span />
                        <b-button type="submit" variant="primary" size="lg" :disabled="!valid || registerProcessing" :class="{'btn-multiple-state btn-shadow': true,
                            'show-spinner': registerProcessing,
                            'show-success': !registerProcessing && registerError===false,
                            'show-fail': !registerProcessing && registerError }">
                            <span class="spinner d-inline-block">
                                <span class="bounce1"></span>
                                <span class="bounce2"></span>
                                <span class="bounce3"></span>
                            </span>
                            <span class="icon success">
                                <i class="simple-icon-check"></i>
                            </span>
                            <span class="icon fail">
                                <i class="simple-icon-exclamation"></i>
                            </span>
                            <span class="label">{{ $t('login.register') }}</span>
                        </b-button>
                    </div>
                </b-form>
            </div>
        </b-card>
    </b-colxx>
</b-row>
</template>

<script>
import {
    mapGetters,
    mapActions
} from "vuex";
const validators = require('vuelidate/lib/validators');

const tcknRule = tckn => {
    const digits = tckn.split("").map(v => parseInt(v));
    digits.unshift(0);

    let sum = 0;
    for(let i = 1; i <= 10; i++) {
        sum += digits[i];
    }
    if(sum%10 != digits[11]) return false;

    let sum1 = 0, sum2 = 0;
    for(let i = 1; i <= 9; i += 2) {
        sum1 += digits[i];
    }
    for(let i = 2; i <= 8; i += 2) {
        sum2 += digits[i];
    }
    if((sum1*7-sum2)%10 != digits[10]) return false;

    return true;
}
const alphaRule = value => /^[a-zA-ZığüşöçİĞÜŞÖÇ\. ]+$/.test(value);

export default {
    data() {
        return {
            form: {
                tckn: "",
                isim: "",
                soyisim: "",
                sifre: "",
                sifreTekrar: "",
                kariyer: null,
                sponsorKodu: "",
                email: "",
                telefon1: "",
                telefon2: "",
                dogumTarihi: "",
                ulke: "",
                sehir: "",
                sozlesmeKayit: false,
                sozlesmeGizlilik: false
            }
        };
    },
    validations: {
        form: {
            tckn: {
                required: validators.required,
                numeric: validators.numeric,
                maxLength: validators.maxLength(11),
                minLength: validators.minLength(11),
                tcknRule
            },
            isim: {
                required: validators.required,
                minLength: validators.minLength(2),
                alphaRule
            },
            soyisim: {
                required: validators.required,
                minLength: validators.minLength(2),
                alphaRule
            },
            sifre: {
                required: validators.required,
                minLength: validators.minLength(6),
                maxLength: validators.maxLength(16)
            },
            sifreTekrar: {
                required: validators.required,
                sameAs: validators.sameAs('sifre')
            },
            kariyer: {
                required: validators.required,
            },
            sponsorId: {
                numeric: validators.numeric
            },
            email: {
                email: validators.email
            },
            telefon1: {
                required: validators.required,
                numeric: validators.numeric
            },
            telefon2: {
                numeric: validators.numeric
            },
            dogumTarihi: {
                required: validators.required,
            },
            ulke: {
                required: validators.required,
                alphaRule
            },
            sehir: {
                required: validators.required,
                alphaRule
            },
            sozlesmeKayit: {
                sameAs: val => val == "accepted"
            },
            sozlesmeGizlilik: {
                sameAs: val => val == "accepted"
            }
        }
    },
    computed: {
        ...mapGetters(["registerProcessing", "registerError"]),
        paketOptions() {
        return this.kariyerler.filter(k => k.baslangicPaket)
                                .map(k => { return {value: k, text: k.kariyerAdi+"-"+k.baslangicPaketTutar}});
        },
        kariyerler() {
            return this.$store.getters['kariyer/items'];
        },
        selfRegister() {
            return !this.$route.query.sponsorId;
        },
        valid() {
            return !this.$v.form.$anyError;
        }
    },
    methods: {
        ...mapActions(["registerAction"]),
        formSubmit() {
            if(!this.valid) {
                console.log("not valid");
                return;
            }
            this.$v.$touch();
            this.$v.form.$touch();
            if (!this.$v.form.$anyError) {
                console.log("register");
                console.log(this.$v.form);
                this.registerAction(this.$v.form.$model)
                .then(result => {
                    if(!result) {
                        this.$notify("error", "Kayıt Yapılamadı", "Bilgilerinizi hatasız girdiğinizden emin olun.", {
                            duration: 3000,
                            permanent: false
                        });
                    }
                    else {
                        this.$router.push('/kayitbasarili');
                    }
                })
            }
        },
    },
    mounted() {
        this.$store.dispatch('kariyer/fetch')
        .then(() => {
            this.form.kariyer = this.$store.getters['kariyer/items'][0];
        });

        if(!this.selfRegister) {
            this.form.sponsorId = this.$route.query.sponsorId;
            this.$v.form.sponsorId.$touch();
        }
    }
};
</script>
