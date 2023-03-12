<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.kargo-yonetimi')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('menu.kargo-yonetimi')" >
          <b-form>
            <b-form-group :label="$t('yonetim.kargoFirma')">
              <b-form-input v-model="kargoFirma" />
            </b-form-group>

            <b-form-group :label="$t('yonetim.kargoUcret')" >
              <b-form-input v-model="kargoUcret" type="number" no-wheel />
            </b-form-group>

            <b-form-group :label="$t('yonetim.kargoUstLimit')" :description="$t('yonetim.kargoUstLimitDesc')">
              <b-form-input :label="$t('yonetim.kargoUstLimit')" v-model="kargoUstLimit" type="number" no-wheel />
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
    kargoFirma: "",
    kargoUcret: 0,
    kargoUstLimit: 0,
  }},
  methods: {
    async save() {
      await this.$store.dispatch('settings/save', {kargoFirma: this.kargoFirma, kargoUcret: this.kargoUcret, kargoUstLimit: this.kargoUstLimit});
      this.loadData();
    },
    loadData() {
      this.kargoFirma = this.$store.getters['settings/getByName']('kargoFirma');
      this.kargoUcret = this.$store.getters['settings/getByName']('kargoUcret');
      this.kargoUstLimit = this.$store.getters['settings/getByName']('kargoUstLimit');
    }
  },
  mounted() {
    this.$store.dispatch('settings/fetch', 'kargo')
    .then(res => {
      this.loadData();
    });

  }
}
</script>
