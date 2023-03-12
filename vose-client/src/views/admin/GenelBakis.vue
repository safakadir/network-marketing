<template>
<div>
    <b-row>
        <b-colxx xxs="12">
            <piaf-breadcrumb :heading="$t('menu.genel-bakis')" />
            <div class="separator mb-5"></div>
        </b-colxx>
    </b-row>
    <b-row>
        <b-colxx xxs="12">
            <b-card class="mb-4" :title="$t('misc.welcome-admin', {name: isimSoyisim})">
            </b-card>
        </b-colxx>
    </b-row>
</div>
</template>

<script>
export default {
    computed: {
        isimSoyisim() {
            if(this.$store.getters.admin) return this.$store.getters.admin.isimSoyisim;
            return '';
        }
    }
}
</script>