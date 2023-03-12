<template>
<div>
  <b-row>
    <b-colxx xxs="12">
      <piaf-breadcrumb :heading="$t('menu.ekibim')"/>
      <div class="separator mb-5"></div>
    </b-colxx>
  </b-row>
  <b-row>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="$t('bayi.tumEkibim')" >
          <span style="cursor: pointer;" @click="selectRoot">{{selfBayi.identity}}</span>
          <b-tree-view 
            :data="treeData"
            :renameNodeOnDblClick="false"
            :contextMenuItems="[ {code: 'ADD_NODE', label:$t('bayi.bayiKaydet')} ]"
            @contextMenuItemSelect="handleContextMenu"
            @nodeSelect="handleNodeSelect"
          ></b-tree-view>
        </b-card>
    </b-colxx>
    <b-colxx xxs="12">
        <b-card class="mb-4" :title="bayi.identity" >
          <dl>
            <dt>{{$t('bayi.kariyerAdi')}}:</dt>
            <dd>{{$gutil.getProperty(bayi, 'kariyer.kariyerAdi')}}</dd>
            <dt>{{$t('bayi.totalPuan')}}:</dt>
            <dd>{{bayi.totalPv}} Pv / {{bayi.totalCv}} Cv</dd>
            <dt>{{$t('bayi.buAyAktiflik')}}:</dt>
            <dd>{{formatAktiflik(bayi)}}</dd>

            <dt>{{$t('bayi.kayitDurum')}}:</dt>
            <dd>{{bayi.kayitDurum ? $t('bayi.completed') : $t('bayi.notCompleted')}}</dd>

            <dt>{{$t('bayi.telefon1')}}:</dt>
            <dd>{{bayi.telefon1 || "-"}}</dd>
            <dt>{{$t('bayi.telefon2')}}:</dt>
            <dd>{{bayi.telefon2 || "-"}}</dd>

            <dt>{{$t('bayi.email')}}:</dt>
            <dd>{{bayi.email || "-"}}</dd> 

            <dt>{{$t('bayi.dogumTarihi')}}:</dt>
            <dd>{{$gutil.formatDate(bayi.dogumTarihi)}}</dd>
        </dl>
        </b-card>
    </b-colxx>
  </b-row>
  <div class="loading" v-if="loading" />
</div>
</template>

<script>
import service from '@/service'

export default {
  data() { return {
    loading: false,
    treeData: [],
    bayi: {},
    selectedNode: null
  }},
  methods: {
    handleContextMenu(menuItem, treeNode) {
      if(menuItem.code == 'ADD_NODE') {
        this.$router.push('/kayit?sponsorId='+treeNode.data.id);
      }
    },
    handleNodeSelect(treeNode, isSelected) {
      this.selectedNode = treeNode;
      if(!isSelected) return;
      this.loading = true;
      service.get('/bayi/'+treeNode.data.id)
      .then(response => {
        this.bayi = response.data;
      })
      .finally(() => {
        this.loading = false;
      })
    },
    formatAktiflik(bayi) {
      if(!bayi.aktiflik) return this.$t("bayi.notAktifZero");
      if(!bayi.aktiflik.durum) return this.$t("bayi.notAktifWith", {count: bayi.aktiflik.urunSayisi});
      return this.$t("bayi.aktif", {count: bayi.aktiflik.urunSayisi});
    },
    selectRoot() {
      this.bayi = this.selfBayi;
      if(this.selectedNode != null) {
        this.selectedNode.deselect();
        this.selectedNode = null;
      }
    }
  },
  computed: {
    bayiId() {
      return this.$store.getters.bayiId;
    },
    selfBayi() {
      return this.$store.getters.bayi;
    }
  },
  mounted() {
    this.loading = true;
    service.get('/bayi/'+this.bayiId+'/agac')
    .then(response => {
      this.treeData = response.data.children;
    })
    .finally(() => {
      this.loading = false;
      this.bayi = this.selfBayi;
    })
  }
}
</script>