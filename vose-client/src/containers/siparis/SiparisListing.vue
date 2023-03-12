<template>
  <div>

    <!--<b-row v-if="displayMode==='image'" key="image">
      <b-colxx
        sm="6"
        lg="4"
        xl="3"
        class="mb-3"
        v-for="(item,index) in items"
        :key="index"
        :id="item.id"
      >
        <urun-user-item
          :key="item.id"
          :item="item"
        />
      </b-colxx>
    </b-row>-->

    <!--<b-row v-else-if="displayMode==='thumb'" key="thumb">-->
    <b-row key="thumb">
      <b-colxx xxs="12" class="mb-3 rounded" v-for="(item,index) in items" :key="index" :id="item.id">
        <siparis-admin-item
          :key="item.id"
          :item="item"
          v-on="$listeners"
        />
      </b-colxx>
    </b-row>

    <!--<b-row v-else-if="displayMode==='sepet'" key="sepet">
      <b-colxx xxs="12" class="mb-3 rounded" v-for="(item,index) in items" :key="index" :id="item.id">
        <urun-sepet-item
          :key="item.id"
          :sepetUrun="item"
          v-on="$listeners"
        />
      </b-colxx>
    </b-row>-->

    <b-row v-if="lastPage>1">
      <b-colxx xxs="12">
        <b-pagination-nav
          :number-of-pages="lastPage"
          :link-gen="linkGen"
          :value="page"
          @change="(a)=>changePage(a)"
          :per-page="perPage"
          align="center"
        >
          <template v-slot:next-text>
            <i class="simple-icon-arrow-right" />
          </template>
          <template v-slot:prev-text>
            <i class="simple-icon-arrow-left" />
          </template>
          <template v-slot:first-text>
            <i class="simple-icon-control-start" />
          </template>
          <template v-slot:last-text>
            <i class="simple-icon-control-end" />
          </template>
        </b-pagination-nav>
      </b-colxx>
    </b-row>
    
  </div>
</template>
<script>
import SiparisAdminItem from "../../components/Listing/SiparisAdminItem";

export default {
  components: {
    "siparis-admin-item": SiparisAdminItem,
  },
  props: [
    "items",
    "lastPage",
    "perPage",
    "page",
    "changePage",
    "displayMode"
  ],
  methods: {
    linkGen(pageNum) {
      return "#page-" + pageNum;
    },
  }
};
</script>
