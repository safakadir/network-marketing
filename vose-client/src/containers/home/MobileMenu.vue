<template>
    <div class="mobile-menu" @click="$event.stopPropagation()">
        <a @click="routeTo('/home')" href="javascript:;" class="logo-mobile">
          <span></span>
        </a>

        <div v-if="isLogin" style="margin-bottom:1rem;"> {{$t('login.welcome')}}, {{bayi.isimSoyisim}} </div>

        <ul class="navbar-nav">
          <li class="nav-item">
            <a @click="routeTo('/urunler')" href="javascript:;">{{$t('home.menu.urunler')}}</a>
          </li>
          <li class="nav-item">
            <a @click="routeTo('/about')" href="javascript:;">{{$t('home.menu.vose')}}</a>
          </li>
          <!--<li class="nav-item">
            <a @click="routeTo('/iletisim')" href="javascript:;">{{$t('home.menu.iletisim')}}</a>
          </li>-->
          <li class="nav-item" v-if="!admin">
            <router-link to="/sepet" class="d-flex align-items-center">
              {{$t('home.menu.sepet')}}
              <b-badge variant="outline-primary" class="ml-1">{{sepetUrunCount}}</b-badge>
            </router-link>
          </li>
          <li class="nav-item">
            <div class="separator"></div>
          </li>
          <template v-if="isLogin">
            <li class="nav-item" v-if="!admin">
              <a @click="routeTo(bayiRoot)" href="javascript:;">{{$t('home.menu.bayiPaneli')}}</a>
            </li>
            <li class="nav-item" v-if="!!admin">
              <a @click="routeTo(adminRoot)" href="javascript:;">{{$t('home.menu.adminPaneli')}}</a>
            </li>
            <!--<li class="nav-item">
              <a @click="routeTo('#')" href="javascript:;">{{$t('common.sifreDegistir')}}</a>
            </li>-->
            <li class="nav-item">
              <div class="separator"></div>
            </li>
          </template>
          <li v-if="isLogin" class="nav-item">
            <a @click="signOut" href="javascript:;">{{$t('common.logout')}}</a>
          </li>
          <li v-else class="nav-item">
            <a @click="routeTo('/login')" href="javascript:;">{{$t('login.title')}}</a>
          </li>
        </ul>
    </div>
</template>

<script>
import VueScrollTo from "vue-scrollto";
import { mapGetters, mapActions } from "vuex";
import { bayiRoot, adminRoot } from "@/constants/config";

export default {
    data() { return {
      bayiRoot,
      adminRoot
    }},
    methods: {
      scrollTo(target) {
          this.$emit('scroll-to', target);
      },
      routeTo(path) {
        this.$router.push(path);
        this.toggleMobileMenu();
      },
      toggleMobileMenu() {
        this.$emit('toggle-mobile-menu');
      },
      ...mapActions(["signOut"]),
    },
    computed: {
      ...mapGetters([
        'sepetUrunCount',
        'bayi',
        'admin'
      ]),
      isLogin() {
        return !!this.$store.getters.login;
      },
    }
}
</script>
