
<template>
    <b-modal ref="agacmodal" size="xl" :title="title" hide-footer class="pa-3">
          <span>{{bayi.identity}}</span>
        <b-tree-view 
            :data="treeData"
            :renameNodeOnDblClick="false"
            :contextMenu="false"
            :contextMenuItems="[]"
          ></b-tree-view>
    </b-modal>
</template>

<script>
import 'echarts/lib/chart/tree'

import service from '@/service'

export default {
    data() { return {
        bayi: {},
        treeData: []
    }},
    methods: {
        async open(bayi) {
            this.bayi = bayi;
            this.showModal();
            const response = await service.get('bayi/'+bayi.id+'/agac');
            this.treeData = response.data.children ? response.data.children : [];
        },
        showModal() {
            this.$refs['agacmodal'].show();
        },
        hideModal() {
            this.$refs['agacmodal'].hide();
        }
    },
    computed: {
        title() {
            if(!this.bayi || !this.bayi.id) return "";
            return this.bayi.identity+" - "+this.$t("bayi.agac");
        }
    }
}
</script>