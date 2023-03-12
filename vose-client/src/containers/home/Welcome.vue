<template>
    <div class="section home" id="sectionHome">
        <div class="container">
            <div class="row home-row" id="homeRow">

                <div class="col-12 col-xl-4 col-lg-5 col-md-6">
                    <div class="home-text">
                    <div class="display-1">
                        VOSE
                        <br />NETWORK &amp; MARKETING
                    </div>
                    <template v-if="isLogin">
                        <p class="white mb-5">
                            Vose dünyasına hoşgeldiniz.
                            <br />İndirimli ürünler ve eşsiz kazanç fırstalarının tadını çıkarın.
                            <br />
                            <br />Haydi, fırsatı kaçırma!
                        </p>
                    </template>
                    <template v-else>
                        <p class="white mb-5">
                            Bayi olarak ürünleri indirimli alabilir,
                            <br />eşsiz kazanç fırstalarına ortak olabilirsiniz.
                            <br />
                            <br />Haydi, fırsatı kaçırma!
                        </p>
                        <a class="btn btn-light btn-xl mr-2 mb-2" style="position:absolute;right:10%;" href="/kayit" target="_blank">
                            KAYIT OL
                            <i class="simple-icon-arrow-right"></i>
                        </a>
                    </template>
                    </div>
                </div>
                <div class="col-12 col-xl-7 offset-xl-1 col-lg-7 col-md-6 d-none d-md-block">
                    <div class="hero" />
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss">
$breakpoints: (
  xxs: 420px,
  xs: 576px,
  sm: 768px,
  md: 992px,
  lg: 1200px,
  xl: 1440px
);
@mixin respond-below($breakpoint) {
  // If the breakpoint exists in the map.
  @if map-has-key($breakpoints, $breakpoint) {
    // Get the breakpoint value.
    $breakpoint-value: map-get(
      $breakpoints,
      $breakpoint
    ); // Write the media query.
    @media (max-width: ($breakpoint-value - 1)) {
      @content;
    }
    // If the breakpoint doesn't exist in the map.
  } @else {
    // Log a warning.
    @warn "Invalid breakpoint: #{$breakpoint}.";
  }
}
.hero {
    position: relative;
    width: 800px;
    height: 450px;
    background-image: url("/assets/img/landing-page/home-kariyer.jpg");
    background-size: cover;
    border-radius: 15%;

    @include respond-below(md) {
        width: 600px;
        height: 340px;
    }
    @include respond-below(md) {
        width: 400px;
        height: 225px;
    }
}
</style>

<script>
import { adminRoot } from "../../constants/config";

import Carousel from '../../containers/home/Carousel'

export default {
    components: {
        'home-carousel': Carousel,
    },
    data() { return {
        adminRoot: adminRoot
    }},
    methods: {
        scrollTo(target) {
            this.$emit('scroll-to', target);
        }
    },
    computed: {
        isLogin() {
            return !!this.$store.getters.login;
        }
    }
}
</script>
