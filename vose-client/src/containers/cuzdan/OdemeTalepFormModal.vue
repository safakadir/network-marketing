<template>
  <b-modal
    id="modalright"
    ref="modalright"
    :title="$t('cuzdan.yeniOdemeTalebi')"
    modal-class="modal-right"
  >
    <b-form v-model="valid" class="av-tooltip tooltip-label-bottom">
      <b-form-group :label="$t('cuzdan.miktar')" class="has-float-label mb-4" 
        :description="$t('cuzdan.talepMiktarDesc')">
        <b-form-input type="text" v-model="$v.form.miktar.$model" :state="!$v.form.miktar.$error"/>
        <b-form-invalid-feedback v-if="!$v.form.miktar.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-if="!$v.form.miktar.numeric">{{$t('common.validation.numeric')}}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-if="!$v.form.miktar.multipleOf">{{$t('cuzdan.multipleOfValidation')}}</b-form-invalid-feedback>
      </b-form-group>

      <b-form-group :label="$t('cuzdan.banka')" class="has-float-label mb-4" 
        :description="$t('cuzdan.talepBankaDesc')">
        <b-form-input type="text" v-model="$v.form.banka.$model" :state="!$v.form.banka.$error"/>
        <b-form-invalid-feedback v-if="!$v.form.banka.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-else-if="!$v.form.banka.alphaRule">{{$t('common.validation.alpha')}}</b-form-invalid-feedback>
      </b-form-group>
      
      <b-form-group :label="$t('cuzdan.iban')" class="has-float-label mb-4" 
        :description="$t('cuzdan.talepIbanDesc')">
        <b-form-input type="text" v-model="$v.form.iban.$model" :state="!$v.form.iban.$error" :formatter="ibanFormatter"/>
        <b-form-invalid-feedback v-if="!$v.form.iban.required">{{$t('common.validation.required')}}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-else-if="!$v.form.iban.minLength">{{$t('cuzdan.ibanLengthValidation')}}</b-form-invalid-feedback>
      </b-form-group>
    </b-form>

    <template slot="modal-footer">
      <b-button variant="outline-secondary" @click="hideModal('modalright')">{{ $t('general.cancel') }}</b-button>
      <b-button variant="primary" @click="saveItem()" class="mr-1">{{ $t('general.save') }}</b-button>
    </template>
  </b-modal>
</template>


<script>
import service from '@/service'

const validators = require('vuelidate/lib/validators');
const NEW_ITEM = {
};

const alphaRule = value => /^[a-zA-ZığüşöçİĞÜŞÖÇ\. ]+$/.test(value);

export default {
  props: ['cuzdanMax'],
  data() { return {
    currentItem: this.$gutil.clone(NEW_ITEM),
    form: {
      miktar: "",
      banka: "",
      iban: "TR",
    },
    valid: false,
    loading: false
  }},
  validations: {
    form: {
      miktar: {
        required: validators.required,
        numeric: validators.numeric,
        multipleOf: v => v%10 == 0
      },
      banka: {
        required: validators.required,
        alphaRule
      },
      iban: {
        required: validators.required,
        minLength: validators.minLength(32),
      }
    }
  },
  methods: {
    open(item) {
      if(!item) this.currentItem = this.$gutil.clone(NEW_ITEM);
      else this.currentItem = this.$gutil.clone(item);
      this.showModal('modalright');
    },
    showModal(refname) {
      this.$refs[refname].show();
    },
    hideModal(refname) {
      this.$refs[refname].hide();
    },
    ibanFormatter(str) {
      const iban = 'TR'+str.replace(/[a-zA-Z ]+/g, '');
      let formatted = '';
      for(let i = 0; i <= iban.length; i += 4) {
        formatted += iban.slice(i, Math.min(iban.length, i+4))+' ';
      }
      return formatted.trim().substring(0, 32);
    },
    saveItem() {
      if(!this.valid) {
        console.log("not valid");
        return;
      }
      this.$v.$touch();
      this.$v.form.$touch();
      if (!this.$v.form.$anyError) {
        console.log("register");
        console.log(this.$v.form);
        const odemeTalep = this.$gutil.clone(this.$v.form.$model);
        odemeTalep.bayiId = this.$store.getters.bayiId;
        this.loading = true;
        service.post('/odemetalep', odemeTalep)
        .then(res =>  {
          this.hideModal("modalright");
          this.$emit('done');
        })
        .catch(err => {})
        .finally(() => {
          this.loading = false;
        })
      }
    }
  },
  computed: {
    isNew() {
      return !this.currentItem.id;
    },
  }
}
</script>
