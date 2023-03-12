<template>
<b-row class="section h-100 mt-5 admin">
    <b-colxx xxs="12" md="10" class="mx-auto my-auto">
        <b-card class="auth-card" no-body>
            <div class="position-relative image-side">
                <h3 class="text-white">{{ $t('login.admin') }}</h3>
                <p class="white mb-0">
                    {{$t('login.descAdmin')}}
                    <br />
                </p>
            </div>
            <div class="form-side">
                <router-link tag="a" to="/">
                    <span class="logo-single" />
                </router-link>
                <h6 class="mb-4">{{ $t('login.title')}}</h6>

                <b-form @submit.prevent="formSubmit" v-model="valid" class="av-tooltip tooltip-label-bottom">
                    <b-form-group :label="$t('login.email')" class="has-float-label mb-4">
                        <b-form-input type="text" v-model="$v.form.email.$model" :state="!$v.form.email.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.email.required">{{$t('login.validation.emailRequired')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.email.email">{{$t('login.validation.emailInvalid')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.email.minLength">{{$t('login.validation.emailLength')}}</b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group :label="$t('login.password')" class="has-float-label mb-4">
                        <b-form-input type="password" v-model="$v.form.password.$model" :state="!$v.form.password.$error" />
                        <b-form-invalid-feedback v-if="!$v.form.password.required">{{$t('login.validation.passwordRequired')}}</b-form-invalid-feedback>
                        <b-form-invalid-feedback v-else-if="!$v.form.password.minLength || !$v.form.password.maxLength">{{$t('login.validation.passwordLength')}}</b-form-invalid-feedback>
                    </b-form-group>
                    <div class="d-flex justify-content-between align-items-center">
                        <b-button type="submit" variant="primary" size="lg" :disabled="loginProcessing" :class="{'btn-multiple-state btn-shadow': true,
                    'show-spinner': loginProcessing,
                    'show-success': !loginProcessing && loginError===false,
                    'show-fail': !loginProcessing && loginError }">
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
                            <span class="label">{{ $t('login.title') }}</span>
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
const {
    required,
    maxLength,
    minLength,
    email
} = require("vuelidate/lib/validators");
import { adminRoot } from '../../constants/config';

export default {
    data() {
        return {
            form: {
                email: "",
                password: ""
            },
            valid: false
        };
    },
    validations: {
        form: {
            password: {
                required,
                maxLength: maxLength(16),
                minLength: minLength(4)
            },
            email: {
                required,
                email,
                minLength: minLength(4)
            }
        }
    },
    computed: {
        ...mapGetters(["login", "loginProcessing", "loginError"]),
    },
    methods: {
        ...mapActions(["loginAction"]),
        formSubmit() {
            if(!this.valid) {
                console.log("not valid");
                return;
            }
            this.$v.$touch();
            this.$v.form.$touch();
            if (!this.$v.form.$anyError) {
                this.loginAction({
                    username: this.form.email,
                    password: this.form.password,
                    admin: true
                })
                .then(result => {
                    if(!result) {
                        this.$notify("error", "Giriş Hatalı", "Email ve şifrenizi doğru girdiğinizden emin olun", {
                            duration: 3000,
                            permanent: false
                        });
                    }
                    else {
                        this.$router.push('/admin');
                    }
                })
            }
        },
    }
};
</script>
