<template>
  <nav>
    <div class="container d-flex align-items-center justify-content-between">

      <div v-if="isBayiPanel" style="position: absolute; left: 35px; cursor: pointer;">
        <span
          class="menu-button d-none d-md-block"
          @click.prevent.stop="changeSideMenuStatus({step :menuClickCount+1,classNames:menuType,selectedMenuHasSubItems})"
        >
          <i class="simple-icon-menu"></i>
        </span>

        <span
          class="menu-button-mobile d-xs-block d-sm-block d-md-none"
          @click.prevent.stop="changeSideMenuForMobile(menuType)"
        >
          <i class="simple-icon-menu"></i>
        </span>
      </div>

      <a class="navbar-logo pull-left" @click="isHome ? scrollTo('#home') : routeTo('/home')" href="javascript:;">
        <span class="white"></span>
        <span class="dark"></span>
      </a>

      <ul class="navbar-nav d-none d-lg-flex flex-row">
        <li class="nav-item">
          <a @click="isHome ? scrollTo('#home') : routeTo('/home')" href="javascript:;">{{$t('home.title')}}</a>
        </li>
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
            <div class="mr-1" style="margin-bottom:2px;"><i class="glyph-icon iconsminds-shopping-bag"></i></div>
            {{$t('home.menu.sepet')}}
            <b-badge :variant="badgeVariant" class="ml-1">{{sepetUrunCount}}</b-badge>
          </router-link>
        </li>
        <li class="nav-item">
          <b-dropdown v-if="isLogin"
            class="dropdown-menu-right"
            right
            variant="empty"
            toggle-class="p-0"
            no-caret
          >
            <template slot="button-content">
              {{$t('login.welcome')}}, {{bayi.isimSoyisim}}
            </template>
            <b-dropdown-item v-if="!admin" @click="goBayiPanel">{{$t('home.menu.bayiPaneli')}}</b-dropdown-item>
            <b-dropdown-item v-if="!!admin" @click="goAdminPanel">{{$t('home.menu.adminPaneli')}}</b-dropdown-item>
            <!--<b-dropdown-item @click="sifreDegistir">{{$t('common.sifreDegistir')}}</b-dropdown-item>-->
            <b-dropdown-divider />
            <b-dropdown-item @click="logout">{{$t('common.logout')}}</b-dropdown-item>
          </b-dropdown>
          <router-link v-else to="/login">{{$t('login.title')}}</router-link>
        </li>

      </ul>
      <span
        class="mobile-menu-button"
        @click="toggleMobileMenu(); $event.stopPropagation()"
      >
        <i class="simple-icon-menu"></i>
      </span>
    </div>
  </nav>
</template>

<script>
import { mapActions, mapMutations, mapGetters } from "vuex";
import { bayiRoot, adminRoot } from '@/constants/config'
import { MenuIcon, MobileMenuIcon } from "@/components/Svg";

export default {
    props: ["isHome", "isTop"],
    components: {
      "menu-icon": MenuIcon,
      "mobile-menu-icon": MobileMenuIcon,
    },
    data() { return {

    }},
    methods: {
      ...mapMutations(["changeSideMenuStatus", "changeSideMenuForMobile"]),
      routeTo(path) {
        this.$router.push(path);
      },
      scrollTo(target) {
          this.$emit('scroll-to', target);
      },
      toggleMobileMenu() {
        this.$emit('toggle-mobile-menu');
      },
      logout() {
        this.signOut().then(() => {
          this.$notify("info", "Oturum kapatıldı");
          this.$router.push('/');
        });
      },
      sifreDegistir() {

      },
      goBayiPanel() {
        this.$router.push(bayiRoot);
      },
      goAdminPanel() {
        this.$router.push(adminRoot);
      },
      ...mapActions(["signOut"]),
    },
    computed: {
      ...mapGetters(
        {
          menuType: "getMenuType",
          menuClickCount: "getMenuClickCount",
          selectedMenuHasSubItems: "getSelectedMenuHasSubItems",
          sepetUrunCount: "sepetUrunCount",
          login: "login",
          bayi: "bayi",
          admin: "admin"
        }),
      isLogin() {
        return !!this.login;
      },
      badgeVariant() {
        return this.isHome && this.isTop ? 'outline-light' : 'outline-primary';
      },
      isBayiPanel() {
        return this.$route.path.includes(bayiRoot);
      }
    }
}
</script>
