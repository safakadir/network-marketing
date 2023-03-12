<template>
  <b-row>
    <b-colxx class="disable-text-selection">
      <siparis-heading
        :title="$t('siparis.title')"
        :changePageSize="changePageSize"
        :from="from"
        :to="to"
        :total="total"
        :perPage="perPage"
        admin
      ></siparis-heading>
      <template v-if="isLoad">
        <siparis-listing
          :items="items"
          :lastPage="lastPage"
          :perPage="perPage"
          :page="page"
          :changePage="changePage"
          displayMode="thumb"
          @action="onSiparisAction"
        ></siparis-listing>
      </template>
      <template v-else>
        <div class="loading"></div>
      </template>
    </b-colxx>
  </b-row>
</template>

<script>
import service from '@/service'
import SiparisHeading from "@/containers/siparis/SiparisHeading"
import SiparisListing from "@/containers/siparis/SiparisListing"

export default {
  components: {
    "siparis-heading": SiparisHeading,
    "siparis-listing": SiparisListing
  },
  data() {
    return {
      isLoad: false,
      page: 1,
      perPage: 5,
      search: "",
      from: 0,
      to: 0,
      total: 0,
      lastPage: 0,
      items: [],
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
          const page = response.data;
          this.total = page.total;
          this.from = page.from;
          this.to = page.to;
          this.items = page.data;
          this.perPage = page.per_page;
          this.lastPage = page.last_page;
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
    changePage(pageNum) {
      this.page = pageNum;
    },
    onSiparisAction() {
      this.loadItems();
    }
  },
  computed: {
    apiUrl() {
      const onayBekleyen = this.$route.path.endsWith('onay-bekleyen') ? '&durum=ODEME_BEKLENIYOR' : '';
      return `/siparis?page=${this.page}&per_page=${this.perPage}${onayBekleyen}`;
    },
    isOnayBekleyen() {
      return this.$route.path.includes("onay-bekleyen");
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
