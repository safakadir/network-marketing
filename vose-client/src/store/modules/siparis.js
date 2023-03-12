import service from '@/service'

export default {
  namespaced: true,
  state: {
    processing: false,
    fetchError: null
  },
  getters: {
    processing: state => state.processing,
    fetchError: state => state.fetchError,
  },
  mutations: {
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
    updateSiparisDurum({commit}, payload) {
      commit('setProcessing', true)
      return service.put('/siparis/'+payload.siparisId+'?durum='+payload.durum)
      .then(
        response => {
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
  }
}
