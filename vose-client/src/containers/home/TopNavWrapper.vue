<template>
  <div class="landing-page-nav">
    <headroom v-if="isHome" style="height:initial;" @top="headroomTop" @not-top="headroomNotTop">
      <top-nav :isHome="isHome" :isTop="isTop" v-on="$listeners" />
    </headroom>
    <div v-else style="height:initial;">
      <div class="headroom--not-top headroom--not-bottom headroom--pinned headroom" >
        <top-nav :isHome="isHome" v-on="$listeners" />
      </div>
    </div>
  </div>
</template>

<script>
import { headroom } from "vue-headroom";
import TopNav from './TopNav'

export default {
    components: {
        'headroom': headroom,
        'top-nav': TopNav
    },
    props: ["isHome"],
    data() {return {
      isTop: true,
    }},
    methods: {
      headroomTop() {
        this.isTop = true;
      },
      headroomNotTop() {
        this.isTop = false;
      }
    }
}
</script>