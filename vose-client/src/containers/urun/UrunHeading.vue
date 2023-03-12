<template>
  <b-row>
    <b-colxx xxs="12">
      <h1 class="mb-0">{{ title }}</h1>
      <template v-if="admin">
        <div class="top-right-button-container">
          <b-button
            variant="primary"
            size="lg"
            class="top-right-button"
            @click="addNew"
          >{{ $t('general.add-new') }}</b-button>
        </div>
        <piaf-breadcrumb />
      </template>

      <div class="mb-2 mt-2">
        <b-button
          variant="empty"
          class="pt-0 pl-0 d-inline-block d-md-none"
          v-b-toggle.displayOptions
        >
          {{ $t('misc.display-options') }}
          <i class="simple-icon-arrow-down align-middle" />
        </b-button>
        <b-collapse id="displayOptions" class="d-md-block">
          <div class="d-block d-md-inline-block pt-1">
            <b-dropdown
              id="ddown1"
              :text="`${$t('general.orderby')} ${sort.label}`"
              variant="outline-dark"
              class="mr-1 float-md-left btn-group"
              size="xs"
            >
              <b-dropdown-item
                v-for="(order,index) in sortOptions"
                :key="index"
                @click="changeOrderBy(order)"
              >{{ order.label }}</b-dropdown-item>
            </b-dropdown>

            <div v-if="!!searchChange" class="search-sm d-inline-block float-md-left mr-1 align-top">
              <b-input :placeholder="$t('menu.search')"  @input="(val) => searchChange(val)" />
            </div>
          </div>
          <div class="float-md-right pt-1">
            <span class="text-muted text-small mr-1 mb-2">{{from}}-{{to}} of {{ total }}</span>
            <b-dropdown
              id="ddown2"
              right
              :text="`${perPage}`"
              variant="outline-dark"
              class="d-inline-block"
              size="xs"
            >
              <b-dropdown-item
                v-for="(size,index) in pageSizes"
                :key="index"
                @click="changePageSize(size)"
              >{{ size }}</b-dropdown-item>
            </b-dropdown>
          </div>
        </b-collapse>
      </div>
      <div class="separator mb-5" />
    </b-colxx>
  </b-row>
</template>
<script>

export default {
  props: [
    "title",
    "changeOrderBy",
    "changePageSize",
    "sort",
    "searchChange",
    "from",
    "to",
    "total",
    "perPage",
    "admin"
  ],
  data() {
    return {
      sortOptions: [
        {
          column: "id",
          label: this.$t('urun.eklenmeSirasi')
        },
        {
          column: "urunAdi",
          label: this.$t('urun.urunAdi')
        },
        {
          column: "kategori",
          label: this.$t('urun.kategori')
        },
        {
          column: "satisFiyat",
          label: this.$t('urun.satisFiyat')
        }
      ],
      pageSizes: [5, 10, 20]
    };
  },
  methods: {
    addNew() {
      this.$emit("add-new");
    },
  },
};
</script>
