<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.sss')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('yonetim.sss')" >
          <b-form>
            <b-form-group :label="$t('yonetim.telefon')">
              <b-form-input v-model="iletisimTelefon" />
            </b-form-group>

            <b-form-group :label="$t('yonetim.email')" >
              <b-form-input v-model="iletisimEmail" />
            </b-form-group>

            <b-form-group :label="$t('yonetim.lokasyon')" >
              <b-form-input v-model="iletisimLokasyon" />
            </b-form-group>

            <b-form-group :label="$t('yonetim.calismaSaatleri')" >
              <b-form-input v-model="iletisimCalismaSaatleri" />
            </b-form-group>

            <b-button variant="primary" @click="save"><i class="iconsminds-save" />{{$t('general.save')}}</b-button>
          </b-form>
        </b-card>
    </b-colxx>
  </b-row>
  </div>
</template>

<script>
export default {
  data() { return {
    iletisimTelefon: "",
    iletisimEmail: "",
    iletisimLokasyon: "",
    iletisimCalismaSaatleri: "",
  }},
  methods: {
    async save() {
      await this.$store.dispatch('settings/save', 
        {
          iletisimTelefon: this.iletisimTelefon,
          iletisimEmail: this.iletisimEmail,
          iletisimLokasyon: this.iletisimLokasyon,
          iletisimCalismaSaatleri: this.iletisimCalismaSaatleri,
        });
      this.loadData();
    },
    loadData() {
      this.iletisimTelefon = this.$store.getters['settings/getByName']('iletisimTelefon');
      this.iletisimEmail = this.$store.getters['settings/getByName']('iletisimEmail');
      this.iletisimLokasyon = this.$store.getters['settings/getByName']('iletisimLokasyon');
      this.iletisimCalismaSaatleri = this.$store.getters['settings/getByName']('iletisimCalismaSaatleri');
    }
  },
  mounted() {
    this.$store.dispatch('settings/fetch', 'iletisim')
    .then(res => {
      this.loadData();
    });
  }
}
</script>
