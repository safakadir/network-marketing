
<template>
  <b-row>
    <b-colxx class="disable-text-selection">
      <urun-heading
        :title="$t('urun.title')"
        :changeOrderBy="changeOrderBy"
        :changePageSize="changePageSize"
        :sort="sort"
        :searchChange="searchChange"
        :from="from"
        :to="to"
        :total="total"
        :perPage="perPage"
        :admin="true"
        @add-new="addNew"
      ></urun-heading>
      <template v-if="isLoad">
        <urun-listing
          :items="items"
          :lastPage="lastPage"
          :perPage="perPage"
          :page="page"
          :changePage="changePage"
          displayMode="thumb"
          @edit="edit"
          @remove="remove"
          @edit-resim="editResim"
          @toggle-starred="toggleStarred"
        ></urun-listing>
        <urun-form-modal ref="formModal" :kategoriler="kategoriler" @done="loadItems"></urun-form-modal>
        <urun-resim-modal ref="resimModal" @done="loadItems"></urun-resim-modal>
        <remove-modal module="urun" ref="removeModal" @done="loadItems"></remove-modal>
      </template>
      <template v-else>
        <div class="loading"></div>
      </template>
    </b-colxx>
  </b-row>
</template>

<script>
import service from '@/service'
import UrunHeading from "../../../containers/urun/UrunHeading"
import UrunListing from "../../../containers/urun/UrunListing"
import UrunFormModal from "../../../containers/urun/UrunFormModal"
import UrunResimModal from "../../../containers/urun/UrunResimModal"
import RemoveModal from '../../../containers/RemoveModal'

export default {
  components: {
    "urun-heading": UrunHeading,
    "urun-listing": UrunListing,
    "urun-form-modal": UrunFormModal,
    "urun-resim-modal": UrunResimModal,
    "remove-modal": RemoveModal,
  },
  data() {
    return {
      isLoad: false,
      sort: {
        column: "id",
        label: this.$t("urun.eklenmeSirasi")
      },
      page: 1,
      perPage: 5,
      search: "",
      from: 0,
      to: 0,
      total: 0,
      lastPage: 0,
      items: [],
      selectedItems: [],
      apiUrlLastChange: 0,
      apiUrlDirty: false
    };
  },
  methods: {
    loadItems() {
      this.isLoad = false;

      service
        .get(this.apiUrl)
        .then(response => {
          const res = response.data;
          this.total = res.total;
          this.from = res.from;
          this.to = res.to;
          this.items = res.data;
          this.perPage = res.per_page;
          this.selectedItems = [];
          this.lastPage = res.last_page;
          this.isLoad = true;
        });
    },

    changePageSize(perPage) {
      this.page = 1;
      this.perPage = perPage;
    },
    changeOrderBy(sort) {
      this.sort = sort;
    },
    searchChange(val) {
      this.search = val;
      this.page = 1;
    },
    changePage(pageNum) {
      this.page = pageNum;
    },

    addNew() {
      this.$refs['formModal'].open(null);
    },
    edit(item) {
      this.$refs['formModal'].open(item);
    },
    remove(item) {
      this.$refs['removeModal'].open(item.id, item.urunAdi);
    },
    editResim(item) {
      this.$refs['resimModal'].open(item);
    },

    toggleStarred(item) {
      item.starred = !item.starred;
      service.put('/urun', item)
      .then(res => {
        this.loadItems();
      })
      .catch(err => {});
    }
  },
  computed: {
    apiUrl() {
      return `/urun?page=${this.page}&per_page=${this.perPage}&sort=${this.sort.column}&search=${this.search}`;
    },
    kategoriler() {
      return this.$store.getters['kategori/items'];
    }
  },
  watch: {
    search() {
      this.page = 1;
    },
    apiUrl() {
      this.apiUrlLastChange = this.$gutil.now();
      this.apiUrlDirty = true;
    }
  },
  mounted() {
    this.$store.dispatch('kategori/fetch');
    this.loadItems();
    setInterval(() => {
      if(this.apiUrlDirty && this.$gutil.now()-this.apiUrlLastChange > 300) {
        this.apiUrlDirty = false;
        this.loadItems();
      }
    }, 50);
  }
};
</script>
