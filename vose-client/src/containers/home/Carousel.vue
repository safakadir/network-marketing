<template>
    <div class="row">
        <div class="col-12 p-0">
            <div class="home-carousel">
            <glide-component :settings="slideSettings">
                <div class="card" v-for="(f,index) in slideItems" :key="`slide_${index}`">
                <div class="card-body text-center">
                    <div>
                    <i :class="f.icon+ ' large-icon'"></i>
                    <h5 class="mb-3 font-weight-semibold">{{f.title}}</h5>
                    </div>
                    <div>
                    <p class="detail-text">{{f.detail}}</p>
                    </div>
                </div>
                </div>
            </glide-component>
            </div>
        </div>
    </div>
</template>

<script>
import GlideComponent from "../../components/Carousel/GlideComponent";

const slideSettings = {
  type: "carousel",
  gap: 30,
  perView: 4,
  hideNav: true,
  peek: { before: 10, after: 10 },
  breakpoints: {
    "600": { perView: 1 },
    "992": { perView: 2 },
    "1200": { perView: 3 }
  }
};

const slideItems = [
  {
    icon: "iconsminds-mouse-3",
    title: "Right Click Menu",
    detail:
      "Increases overall usability of the project by providing additional actions menu."
  },
  {
    icon: "iconsminds-electric-guitar",
    title: "Video Player",
    detail:
      "Carefully themed multimedia players powered by Video.js library with Youtube support."
  },
  {
    icon: "iconsminds-keyboard",
    title: "Keyboard Shortcuts",
    detail:
      "Easily configurable keyboard shortcuts plugin that highly improves user experience."
  },
  {
    icon: "iconsminds-three-arrow-fork ",
    title: "Two Panels Menu",
    detail:
      "Three states two panels icon menu that looks good, auto resizes and does the job well."
  },
  {
    icon: "iconsminds-deer",
    title: "Icons Mind",
    detail:
      "1040 icons in 53 different categories, designed pixel perfect and ready for your project."
  },
  {
    icon: "iconsminds-palette",
    title: "20 Color Schemes",
    detail:
      "Colors, icons and design harmony that creates excellent themes to cover entire project."
  },
  {
    icon: "iconsminds-air-balloon-1",
    title: "3 Applications",
    detail:
      "Applications that mostly made of components are the way to get started to create something similar."
  },
  {
    icon: "iconsminds-resize",
    title: "Extra Responsive",
    detail:
      "Custom Bootstrap 4 xxs & xxl classes delivers better experiences for smaller and larger screens."
  }
];

export default {
    components: {
        "glide-component": GlideComponent,
    },
    data() {return {
        slideSettings: slideSettings,
        slideItems: slideItems
    }}
}
</script>