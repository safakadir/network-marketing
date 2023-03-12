<template>
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
</template>

<script>
export default {
    data() { return {
        currentItem: {}
    }},
    methods: {
        getAdres() {
            return this.currentItem;
        }
    }
}
</script>