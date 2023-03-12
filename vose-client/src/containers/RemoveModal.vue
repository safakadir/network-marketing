<template>
    <b-modal id="confirmmodal" ref="confirmmodal" :busy="loading" :title="$t('general.ruSure')">
        {{$t('general.removeConfirm', {name: name})}}
        <template slot="modal-footer">
            <b-button variant="primary" @click="remove()" class="mr-1" :disabled="loading">{{$t('general.delete')}}<b-spinner v-if="loading"/></b-button>
            <b-button variant="secondary" @click="hideModal('confirmmodal')" :disabled="loading">{{$t('general.cancel')}}</b-button>
        </template>
    </b-modal>
</template>

<script>
import service from '@/service'
export default {
    props: {module: String, store: Boolean},
    data() { return {
        id: 0,
        name: "",
        loading: false
    }},
    methods: {
        open(id, name) {
            this.id = id;
            this.name = name;
            this.showModal('confirmmodal');
        },
        showModal(refname) {
            this.$refs[refname].show();
        },
        hideModal(refname) {
            this.$refs[refname].hide();
        },
        async remove() {
            this.loading = true;
            try {
                if(this.store) {
                    await this.$store.dispatch(this.module+'/remove', this.id);
                }
                else {
                    await service.delete(this.module+'/'+this.id);
                }
                this.hideModal('confirmmodal');
                this.$emit('done');
            } catch(err) {}
            finally { this.loading = false; }
        }
    }
}
</script>
