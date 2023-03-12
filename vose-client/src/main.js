import Vue from 'vue'
import App from './App'
import { getPersistedLogin } from './utils'

// BootstrapVue add
import BootstrapVue from 'bootstrap-vue'
// Router & Store add
import router from './router'
import store from './store'
// Multi Language Add
import tr from './locales/tr.json'
import VueI18n from 'vue-i18n'
// Notification Component Add
import Notifications from './components/Common/Notification'
// Breadcrumb Component Add
import Breadcrumb from './components/Common/Breadcrumb'
// RefreshButton Component Add
import RefreshButton from './components/Common/RefreshButton'
// Colxx Component Add
import Colxx from './components/Common/Colxx'
// Perfect Scrollbar Add
import vuePerfectScrollbar from 'vue-perfect-scrollbar'
import contentmenu from 'v-contextmenu'
import VueLineClamp from 'vue-line-clamp'
import VueScrollTo from 'vue-scrollto'

import Vuelidate from 'vuelidate'

import { getCurrentLanguage } from './utils'

import VueECharts from 'vue-echarts'
import GenericUtil from './utils/generic'

import { adminRoot, bayiRoot} from './constants/config'

import ApiTable from './components/Common/ApiTable'

import BootstrapVueTreeview from 'bootstrap-vue-treeview'

import 'vue-search-select/dist/VueSearchSelect.css'
import { ModelSelect, ModelListSelect } from 'vue-search-select'

Vue.use(BootstrapVue);
Vue.use(VueI18n);
const messages = { tr: tr };
const locale = getCurrentLanguage();
const i18n = new VueI18n({
  locale: locale,
  fallbackLocale: 'tr',
  messages
});
Vue.use(Notifications);
Vue.use(require('vue-shortkey'));
Vue.use(contentmenu);
Vue.use(VueScrollTo);
Vue.use(VueLineClamp, {
  importCss: true
});
Vue.use(Vuelidate);
Vue.use(GenericUtil);
Vue.use(BootstrapVueTreeview)

Vue.component('piaf-breadcrumb', Breadcrumb);
Vue.component('b-refresh-button', RefreshButton);
Vue.component('b-colxx', Colxx);
Vue.component('vue-perfect-scrollbar', vuePerfectScrollbar);
Vue.component('v-chart', VueECharts);
Vue.component('api-table', ApiTable);
Vue.component('model-select', ModelSelect);
Vue.component('model-list-select', ModelListSelect);

Vue.config.productionTip = false

const app = new Vue({
  i18n,
  router,
  store,
  render: h => h(App),
  created() {
    const isAdminPage = window.location.href.includes(adminRoot);
    const isBayiPage = window.location.href.includes(bayiRoot);
    const isLoginPage = window.location.href.includes('/login');

    const persistedLogin = getPersistedLogin();
    store.dispatch('checkLogin', persistedLogin)
    .then(login => {
      store.commit('setInitCompleted');
      this.$root.$emit('init-completed');
      if(isAdminPage) {
        if(login && login.bayi.id) {
          this.$router.push('/');
        }
        else if(!login || !login.admin) {
          this.$router.push('/login/admin');
        }
        else if(isLoginPage) {
          this.$router.push('/admin');
        }
      }
      else if(isBayiPage) {
        if(!Vue.gutil.getProperty(login, 'bayi.id')) {
          this.$router.push('/login');
        }
        else if(isLoginPage) {
          this.$router.push('/');
        }
      }
    })
    .catch(err => {
      console.log("Session failed.");
    })
    .finally(() => {
      store.commit('setInitCompleted');
      this.$root.$emit('init-completed');
    })
  }
}).$mount('#app');

export default app;
