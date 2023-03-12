
<template>
  <b-row>
    <b-colxx v-if="!onecikan" xxs="12" md="4" lg="3" xl="2" class="mb-4 center-sm">
      <h3>{{$t('menu.kategoriler')}}</h3>
      <ul class="list-unstyled simple-menu">
        <li v-for="item in kategoriler" :key="item.id">
          <a href="#" @click.prevent="selectedKategori = item" :class="{active: selectedKategori && selectedKategori.id == item.id}">{{item.kategoriAdi}}</a>
        </li>
        <li>
          <a href="#" @click.prevent="selectedKategori = null" :class="{active: !selectedKategori}">{{$t('urun.tumUrunler')}}</a>
        </li>
      </ul>
    </b-colxx>
    <b-colxx class="disable-text-selection">
      <urun-heading
        :title="title"
        :changeOrderBy="changeOrderBy"
        :changePageSize="changePageSize"
        :sort="sort"
        :searchChange="!onecikan ? searchChange : null"
        :from="from"
        :to="to"
        :total="total"
        :perPage="perPage"
      ></urun-heading>
      <template v-if="isLoad">
        <urun-listing
          :items="items"
          :lastPage="lastPage"
          :perPage="perPage"
          :page="page"
          :changePage="changePage"
          displayMode="image"
        ></urun-listing>
      </template>
      <div class="loading" v-if="loading"></div>
    </b-colxx>
  </b-row>
</template>

<script>
import service from '@/service'
import UrunHeading from '../urun/UrunHeading'
import UrunListing from '../urun/UrunListing'

export default {
  components: {
    "urun-heading": UrunHeading,
    "urun-listing": UrunListing,
  },
  props: {
    onecikan: Boolean
  },
  data() {
    return {
      isLoad: false,
      loading: false,
      sort: {
        column: "urunAdi",
        label: this.$t("urun.urunAdi")
      },
      page: 1,
      perPage: 12,
      search: "",
      from: 0,
      to: 0,
      total: 0,
      lastPage: 0,
      items: [],
      selectedItems: [],
      apiUrlLastChange: 0,
      apiUrlDirty: false,
      selectedKategori: null
    };
  },
  methods: {
    loadItems() {
      this.isLoad = false;
      this.loading = true;

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
        })
        .finally(() => { this.loading = false; });
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
  },
  computed: {
    apiUrl() {
      let url = `/urun?page=${this.page}&per_page=${this.perPage}&sort=${this.sort.column}`;
      if(this.search && this.search.length > 0) url += `&search=${this.search}`;
      if(this.selectedKategori) url += `&kategori=${this.selectedKategori.id}`;
      if(this.onecikan) url += '&onecikan=true';
      return url;
    },
    kategoriler() {
      return this.$store.getters['kategori/items'];
    },
    title() {
      return this.onecikan ? this.$t('urun.oneCikanlar') :
            (this.selectedKategori ? this.selectedKategori.kategoriAdi : this.$t('urun.tumUrunler'));
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
