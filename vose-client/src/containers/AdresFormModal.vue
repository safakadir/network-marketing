<template>
    <b-modal id="adresmodal" ref="adresmodal" size="lg" :busy="loading" :title="title">
        <b-form>
            <b-row>
                <b-col>
                    <b-form-group :label="$t('adres.baslik')">
                        <b-form-input v-model="currentItem.baslik" :placeholder="$t('adres.baslikPlaceholder')" />
                    </b-form-group>
                    <b-form-group :label="$t('adres.aliciAdi')">
                        <b-form-input v-model="currentItem.aliciAdi" />
                    </b-form-group>
                    <b-form-group :label="$t('adres.aliciTel')">
                        <b-form-input v-model="currentItem.aliciTel" />
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group :label="$t('adres.adres')">
                        <b-form-textarea v-model="currentItem.adres" rows="3" max-rows="5" />
                    </b-form-group>
                    <b-row>
                        <b-col>
                            <b-form-group :label="$t('adres.postaKodu')">
                                <b-form-input v-model="currentItem.postaKodu" type="number" no-wheel />
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group :label="$t('adres.ilce')">
                                <b-form-input v-model="currentItem.ilce" />
                            </b-form-group>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <b-form-group :label="$t('adres.il')">
                                <b-form-input v-model="currentItem.il" />
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group :label="$t('adres.ulke')">
                                <b-form-input v-model="currentItem.ulke" />
                            </b-form-group>
                        </b-col>
                    </b-row>
                </b-col>
            </b-row>
        </b-form>
        <template slot="modal-footer">
            <b-button variant="primary" @click="saveItem" class="mr-1">{{$t('general.save')}}</b-button>
            <b-button variant="secondary" @click="hideModal">{{$t('general.cancel')}}</b-button>
        </template>
    </b-modal>
</template>

<script>
const NEW_ITEM = {};

export default {
  data() { return {
    currentItem: this.$gutil.clone(NEW_ITEM),
    loading: false
  }},
  methods: {
    open(item, newItem) {
      if(!item) this.currentItem = this.$gutil.clone(newItem);
      else this.currentItem = this.$gutil.clone(item);
      this.showModal();
    },
    showModal() {
      this.$refs['adresmodal'].show();
    },
    hideModal() {
      this.$refs['adresmodal'].hide();
    },
    async saveItem() {
        this.currentItem.bayiId = this.$store.getters.bayiId;
        
        this.loading = true;
        const action = 'adres/' + (this.isNew ? 'add' : 'update');
        let adres = await this.$store.dispatch(action, this.currentItem)
        if(adres) {
            this.$emit('done', res.data);
            this.currentItem = this.$gutil.clone(NEW_ITEM);
            this.hideModal();
        }
        this.loading = false;
    }
  },
  computed: {
    isNew() {
      return !this.currentItem.id;
    },
    title() {
        return this.isNew ? this.$t('adres.add-new-title') : this.$t('adres.edit-title')
    }
  }
}
</script>
