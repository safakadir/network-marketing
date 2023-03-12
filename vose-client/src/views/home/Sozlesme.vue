<template>
<main style="margin-left:60px;">
<div class="container-fluid">
    <div>
    <b-row>
        <b-colxx xxs="12" class="mt-4 mb-4 rounded">
            <b-card class="mb-4 p-5" no-body v-html="content">
            </b-card>
        </b-colxx>
    </b-row>
    <div class="loading" v-if="loading" />
    </div>
</div>
</main>
</template>

<script>
import service from '@/service'

const sozlesmeLiteral = 'sozlesme';

export default {
    data() { return {
        loading: false
    }},
    computed: {
        content() {
            const key = sozlesmeLiteral+this.$gutil.snakeToPascal(this.$route.params.key);
            return this.$store.getters['settings/getByName'](key);
        }
    },
    mounted() {
        this.$store.dispatch('settings/fetch', sozlesmeLiteral)
        .then(() => {})
        .finally(() => {
            this.loading = false;
        })
    }
}
</script>
