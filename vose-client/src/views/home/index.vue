<template>
  <home-layout>
    <div :class="{'landing-page': true,'show-mobile-menu': showMobileMenu}">
      <home-mobile-menu @toggle-mobile-menu="showMobileMenu = !showMobileMenu" />

      <div class="main-container">
        <home-top-nav :isHome="isHome" @toggle-mobile-menu="showMobileMenu = !showMobileMenu" />

        <router-view />

        <home-footer />

      </div>
    </div>
  </home-layout>
</template>

<script>
import VueScrollTo from "vue-scrollto"
import HomeLayout from "../../layouts/HomeLayout"

import TopNavWrapper from '../../containers/home/TopNavWrapper'
import MobileMenu from '../../containers/home/MobileMenu'
import Footer from '../../containers/home/Footer'

const scrollOptions = {
  container: "body",
  easing: "ease-in",
  offset: -120,
  force: true,
  x: false,
  y: true
};

export default {
  components: {
    'home-top-nav': TopNavWrapper,
    'home-mobile-menu': MobileMenu,

    "home-layout": HomeLayout,
    'home-footer': Footer,
  },
  data() {
    return {
      showMobileMenu: false,
    };
  },
  methods: {
    scrollTo(target) {
        VueScrollTo.scrollTo(target, 200, scrollOptions);
    },
    onWindowScroll() {
      this.showMobileMenu = false;
    },
    onWindowClick() {
      this.showMobileMenu = false;
    },
  },
  computed: {
    isHome() {
      return this.$route.path == '/home';
    }
  },
  mounted() {
    document.body.classList.add("no-footer");
    window.addEventListener("scroll", this.onWindowScroll);
    window.addEventListener("click", this.onWindowClick);
    this.$store.dispatch('reloadSepet');
    this.$store.dispatch('settings/fetch', 'iletisim');
  },
  beforeDestroy() {
    document.body.classList.remove("no-footer");
    window.removeEventListener("scroll", this.onWindowScroll);
    window.removeEventListener("click", this.onWindowClick);
  }
};
</script>
