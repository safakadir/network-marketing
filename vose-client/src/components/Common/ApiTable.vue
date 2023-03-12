<template>
<div>
    <b-row v-if="dateFilter">
        <b-colxx xxs="12">
          <b-button-group class="mb-2">
            <b-button
              variant="primary"
              @click="changeOption(1)"
              :pressed="option==1"
            >{{ $t('common.lastMonth') }}</b-button>
            <b-button
              variant="primary"
              @click="changeOption(2)"
              :pressed="option==2"
            >{{ $t('common.prevMonth') }}</b-button>
            <b-button
              variant="primary"
              @click="changeOption(3)"
              :pressed="option==3"
            >{{ $t('common.last3Month') }}</b-button>
          </b-button-group>
          <span class="text-one font-weight-semibold ml-4">{{showingMonth}}</span>
        </b-colxx>
    </b-row>
    <b-row>
        <b-colxx xxs="12">
            <b-table
                ref="_table"
                hover 
                responsive 
                :items="items" 
                :fields="fieldsCmp"
                :busy="loading"
                primary-key="id"
                show-empty
                @row-clicked="handleRowClick"
            >
                <template v-for="slotName in scopedSlotNames" v-slot:[slotName]="row">
                    <slot :item="row.item" :name="slotName"></slot>
                </template>

                <template v-slot:cell(_actions)="row">
                    <b-button variant="outline-danger" size="xs" @click="remove(row.item)">
                    <i class="glyph-icon simple-icon-trash" /> 
                    </b-button>
                </template>
                <template v-slot:empty>
                    <div style="width:100%; text-align:center;"><p>{{ $t('general.noData') }}</p></div>
                </template>
            </b-table>
            <div class="loading" v-if="loading" />
        </b-colxx>
    </b-row>
    <b-row v-if="pagination && lastPage>1">
        <b-colxx xxs="12">
            <b-pagination-nav
                :number-of-pages="lastPage"
                :link-gen="linkGen"
                :value="page"
                @change="changePage"
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
    <b-row v-if="pagination">
        <b-colxx xxs="12">
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
        </b-colxx>
    </b-row>
</div>
</template>

<script>
import moment from 'moment'
import service from '@/service'

export default {
    props: {
        fields: Array,
        apiUrl: String,
        pagination: Boolean,
        dateFilter: Boolean,
        actionDelete: Boolean
    },
    data() { return {
        page: 1,
        perPage: 10,
        from: 0,
        to: 0,
        total: 0,
        lastPage: 0,
        items: [],
        pageSizes: [5, 10, 20, 50],
        loading: false,
        option: 1,
        showingMonth: '',
        dateQuery: '',
    }},
    methods: {
        loadItems() {
            if(!this.apiUrlCmp) return;
            this.loading = true;
            return service.get(this.apiUrlCmp)
            .then(response => {
                if(this.pagination) {
                    const res = response.data;
                    this.total = res.total;
                    this.from = res.from;
                    this.to = res.to;
                    this.items = res.data;
                    this.perPage = res.per_page;
                    this.lastPage = res.last_page;
                }
                else {
                    this.items = response.data;
                }
                this.$emit('load', this.items);
            })
            .finally(() => {
                this.loading = false;
            });
        },
        reload() {
            this.loadItems();
        },
        remove(item) {
            const resource = this.apiUrl.split('/')[1]; // e.g. "/odemetalep/bayi/1" => "odemetalep"
            this.loading = true;
            service.delete(resource+'/'+item.id)
            .then(() => {
                return this.loadItems();
            })
            .finally(() => {
                this.loading = false;
            });
        },
        changeOption(option) {
            this.option = option;

            if(this.option == 1) {
                const start = moment().startOf('month');
                this.showingMonth = start.format('MMMM');
                this.dateQuery = 'start='+start.valueOf();
            }
            else if(this.option == 2) {
                const prevMonth = moment().subtract(1, 'months');
                this.showingMonth = prevMonth.format('MMMM');
                const start = prevMonth.startOf('month').valueOf();
                const end = prevMonth.endOf('month').valueOf();
                this.dateQuery = 'start='+start+'&end='+end;
            }
            else if(this.option == 3) {
                const start = moment().subtract(2, 'months').startOf('month');
                this.showingMonth = start.format('MMMM')+'-'+moment().format('MMMM');
                this.dateQuery = 'start='+start.valueOf();
            }
        },
        linkGen(pageNum) {
            return "#page-" + pageNum;
        },
        changePageSize(perPage) {
            this.page = 1;
            this.perPage = perPage;
        },
        changePage(pageNum) {
            this.page = pageNum;
        },
        handleRowClick(item, index, event) {
            this.$emit('row-click', {item, index, event});
        },
        refresh() {
            this.$refs._table.refresh();
        }
    },
    computed: {
        pageQuery() {
            return `page=${this.page}&per_page=${this.perPage}`;
        },
        apiUrlCmp() {
            if(!this.apiUrl) return;

            let result = this.apiUrl;
            if(!this.apiUrl.includes('?')) result += '?';
            else if(this.pagination || this.dateFilter) result += '&';

            if(this.pagination && this.dateFilter) result += this.pageQuery + '&' + this.dateQuery;
            else if(this.pagination) result += this.pageQuery;
            else if(this.dateFilter) result += this.dateQuery;

            return result;
        },
        fieldsCmp() {
            if(this.actionDelete) {
                return [...this.fields, {key: "_actions", label: this.$t("general.actions")}];
            }
            return this.fields;
        },
        scopedSlotNames() {
            return Object.keys(this.$scopedSlots);
        }
    },
    watch: {
        apiUrlCmp() {
            this.loadItems();
        }
    },
    mounted() {
        moment.locale('tr');
        if(this.dateFilter) this.changeOption(1);
        this.loadItems();
    }
}
</script>