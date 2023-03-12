<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.sozlesmeler')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('menu.sozlesmeler')" >
          <b-form-group>
            <b-form-select v-model="selectedSozlesme" :options="sozlesmeOptions" @input="sozlesmeSelected" :placeholder="$t('common.select')" />
          </b-form-group>
          <p class="text-semi-muted text-small">{{$t('yonetim.sozlesmeBaslikWarn')}}</p>
          <quill-editor ref="textEditor"
                        v-model="content"
                        :options="editorOption"
                        style="min-height:300px;">
          </quill-editor>
          <div class="mt-3 d-flex justify-content-end width:100%;">
            <b-button variant="primary" @click="saveItem" :disabled="loading">{{$t('general.save')}}</b-button>
          </div>
          <div class="loading" v-if="loading" />
        </b-card>
    </b-colxx>
  </b-row>
  </div>
</template>

<style>
.ql-container{
  min-height: inherit;
}
.ql-editor{
  min-height: inherit;
}
</style>

<script>
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import { quillEditor } from 'vue-quill-editor'

const editorOptions =  {
  placeholder: '',
  modules: {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike', 'blockquote'],
      [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
      [
        { list: 'ordered' },
        { list: 'bullet' },
        { indent: '-1' },
        { indent: '+1' }
      ],
      ['link', 'image'],
      ['clean']
    ]
  }
};

export default {
  components: {
    'quill-editor' : quillEditor
  },
  data() { return {
    content: '',
    editorOption: editorOptions,
    sozlesmeOptions: [
      { value: 'sozlesmeMesafeliSatis', text: this.$t('yonetim.sozlesme.mesafeliSatis')},
      { value: 'sozlesmeIade', text: this.$t('yonetim.sozlesme.iade')},
      { value: 'sozlesmeKayit', text: this.$t('yonetim.sozlesme.kayit')},
      { value: 'sozlesmeGizlilik', text: this.$t('yonetim.sozlesme.gizlilik')}
    ],
    selectedSozlesme: null,
    loading: false,
  }},
  methods: {
    sozlesmeSelected(value) {
      this.content = this.$store.getters['settings/getByName'](value);
    },
    loadItems() {
      this.loading = true;
      return this.$store.dispatch('settings/fetch', 'sozlesme')
      .finally(() => { this.loading = false; });
    },
    saveItem() {
      const payload = {};
      payload[this.selectedSozlesme] = this.content;
      this.loading = true;
      this.$store.dispatch('settings/save', payload)
      .finally(() => { this.loading = false; });
    }
  },
  mounted() {
    this.loadItems()
    .then(() => {
      this.selectedSozlesme = this.sozlesmeOptions[0].value;
    });
  }
}
</script>