<template>
    <home-layout>
        <div :class="{'landing-page': true,'show-mobile-menu': showMobileMenu}">
        <home-mobile-menu @toggle-mobile-menu="showMobileMenu = !showMobileMenu" />

            <div class="main-container" :class="getMenuType">
                <home-top-nav @toggle-mobile-menu="showMobileMenu = !showMobileMenu" />
                <sidebar :menu-items="bayiMenu" />

                <main>
                    <div class="container-fluid">
                    <router-view />
                    </div>
                </main>
            </div>
        </div>
    </home-layout>
</template>

<script>
import HomeLayout from '@/layouts/HomeLayout' 
import TopNavWrapper from '@/containers/home/TopNavWrapper'
import MobileMenu from '../../containers/home/MobileMenu'
import Sidebar from "@/containers/navs/Sidebar";
import { mapGetters } from "vuex";
import { bayiMenu } from '@/constants/menu'

export default {
    components: {
        'home-layout': HomeLayout,
        'home-top-nav': TopNavWrapper,
        'home-mobile-menu': MobileMenu,
        'sidebar': Sidebar
    },
    data() { return {
        bayiMenu,
        showMobileMenu: false
    }},
    computed: {
        ...mapGetters(["getMenuType"])
    },
    mounted() {
        this.$store.dispatch('reloadSepet');
    }
}
</script>