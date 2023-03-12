import service from '@/service'
import Vue from 'vue'

export default {
  namespaced: true,
  state: {
    settingMap: {},
    processing: false,
    fetchError: null
  },
  getters: {
    settingMap: state => state.settingMap,
    getByName: state => name => state.settingMap[name],
    processing: state => state.processing,
    fetchError: state => state.fetchError,
  },
  mutations: {
    setItems(state, payload) {
      const settingMap = Vue.gutil.clone(state.settingMap);
      for(let i = 0; i < payload.length; i++) {
        let afterParse = payload[i].value;
        try {
          afterParse = JSON.parse(payload[i].value);
        } catch(e) {}
        settingMap[payload[i].name] = afterParse;
      }
      Vue.set(state, 'settingMap', settingMap);
    },
    setItem(state, payload) {
      Vue.set(state.settingMap, payload.name, payload.value);
    },
    setProcessing(state, payload) {
      state.processing = payload
      state.fetchError = null
    },
    setError(state, payload) {
      state.fetchError = payload
      state.processing = false
    },
    clearError(state) {
      state.fetchError = null
    }
  },
  actions: {
    fetch({commit}, prefix) {
      commit('setProcessing', true)
      let url = '/setting';
      if(!!prefix) url += '/prefix/'+prefix;
      return service.get(url)
        .then(
          response => {
            commit('setItems', response.data)
            commit('setProcessing', false)
          },
          err => {
            commit('setError', err.message)
            setTimeout(() => {
              commit('clearError')
            }, 3000)
          }
        )
    },
    save({commit}, payload) {
      commit('setProcessing', true);
      const list = [];
      for(let name in payload) {
        let value = payload[name];
        if(typeof value != 'string') {
          value = JSON.stringify(value);
        }
        list.push({name, value});
      }
      return service.put('/setting/bulk', list) 
      .then(
        response => {
          commit('setItems', response.data)
          commit('setProcessing', false)
        },
        err => {
          commit('setError', err.message)
          setTimeout(() => {
            commit('clearError')
          }, 3000)
        }
      )
    }
   
  }
}
