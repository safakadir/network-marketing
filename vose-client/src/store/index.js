import Vue from 'vue'
import Vuex from 'vuex'

import app from '../main'
import menu from './modules/menu'
import user from './modules/user'
import kariyer from './modules/kariyer'
import kategori from './modules/kategori'
import sepet from './modules/sepet'
import settings from './modules/settings'
import adres from './modules/adres'
import siparis from './modules/siparis'
import { setCurrentLanguage } from '../utils'
import { version } from '../../package.json'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    initializing: true,
    version: version
  },
  mutations: {
    changeLang(state, payload) {
      app.$i18n.locale = payload
      setCurrentLanguage(payload);
    },
    setInitCompleted(state) {
      state.initializing = false;
    }
  },
  getters: {
    initCompleted: state => !state.initializing,
    version: state => state.version
  },
  actions: {
    setLang({ commit }, payload) {
      commit('changeLang', payload)
    }
  },
  modules: {
    menu,
    user,
    kariyer,
    kategori,
    sepet,
    settings,
    adres,
    siparis
  }
})
