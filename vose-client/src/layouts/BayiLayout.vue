<template>
  <div class="homepage" :class="getMenuType">
    <sidebar />
    <main>
      <div class="container-fluid">
        <slot></slot>
      </div>
    </main>
    <footer-component />
  </div>
</template>

<script>
import Sidebar from "../containers/navs/Sidebar";
import Footer from "../containers/navs/Footer";
import { mapGetters } from "vuex";

export default {
  components: {
    sidebar: Sidebar,
    "footer-component": Footer
  },
  data() {
    return {
      show: false
    };
  },
  computed: {
    ...mapGetters(["getMenuType"])
  },
  mounted() {
    setTimeout(() => {
      document.body.classList.add("default-transition");
    }, 100);
  }
};
</script>
