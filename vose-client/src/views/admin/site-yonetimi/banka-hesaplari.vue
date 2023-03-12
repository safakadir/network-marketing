<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.banka-hesaplari')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('menu.banka-hesaplari')" >
          <b-form>
            <b-form-group :label="$t('yonetim.bankaAdi')">
              <b-form-input v-model="bankaAdi" />
            </b-form-group>
            
            <b-form-group :label="$t('yonetim.bankaAlici')" >
              <b-form-input v-model="bankaAlici" />
            </b-form-group>

            <b-form-group :label="$t('yonetim.bankaIban')" >
              <b-form-input :label="$t('yonetim.bankaIban')" v-model="bankaIban" placeholder="TR00 0000 0000 0000 0000 0000 00" />
            </b-form-group>

            <b-form-group :label="$t('yonetim.bankaNotlar')" >
              <b-form-textarea v-model="bankaNotlar" rows="3" max-rows="5" />
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
    bankaAdi: "",
    bankaAlici: 0,
    bankaIban: "",
    bankaNotlar: ""
  }},
  methods: {
    async save() {
      await this.$store.dispatch('settings/save', {
        bankaAdi: this.bankaAdi,
        bankaAlici: this.bankaAlici,
        bankaIban: this.bankaIban,
        bankaNotlar: this.bankaNotlar,
      });
      this.loadData();
    },
    loadData() {
      this.bankaAdi = this.$store.getters['settings/getByName']('bankaAdi');
      this.bankaAlici = this.$store.getters['settings/getByName']('bankaAlici');
      this.bankaIban = this.$store.getters['settings/getByName']('bankaIban');
      this.bankaNotlar = this.$store.getters['settings/getByName']('bankaNotlar');
    }
  },
  mounted() {
    this.$store.dispatch('settings/fetch', 'banka')
    .then(res => {
      this.loadData();
    });
  }
}
</script>
