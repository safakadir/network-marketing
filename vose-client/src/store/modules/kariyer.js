import service from '@/service'

export default {
  namespaced: true,
  state: {
    items: [],
    processing: false,
    fetchError: null
  },
  getters: {
    items: state => state.items,
    processing: state => state.processing,
    fetchError: state => state.fetchError,
  },
  mutations: {
    setItems(state, payload) {
      state.items = payload
    },
    updateItems(state, payload) {
      let list = payload;
      if(!Array.isArray(payload)) {
        list = [payload];
      }
      for(let updatedItem of list) {
        state.items[updatedItem.siraNo-1] = updatedItem;
      }
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
    fetch({commit}) {
      commit('setProcessing', true)
      return service.get('/kariyer')
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

    add({commit, dispatch}, payload) {
      commit('setProcessing', true)
      return service.post('/kariyer?addAfter='+payload.addAfter, payload)
      .then(
        response => {
          return dispatch('fetch');
        },
        err => {
          commit('setError', err.message)
          setTimeout(() => {
            commit('clearError')
          }, 3000)
        }
      )
    },

    update({commit}, payload) {
      commit('setProcessing', true)
      return service.put('/kariyer', payload)
      .then(
        response => {
          commit('updateItems', response.data)
          commit('setProcessing', false)
          return true;
        },
        err => {
          commit('setError', err.message)
          setTimeout(() => {
            commit('clearError')
          }, 3000)
        }
      )
    },

    remove({commit, dispatch}, id) {
      commit('setProcessing', true)
      return service.delete('/kariyer/'+id)
      .then(
        response => {
          return dispatch('fetch');
        },
        err => {
          commit('setError', err.message)
          setTimeout(() => {
            commit('clearError')
          }, 3000)
        }
      )
    },

    changeOrder({commit}, {id, direction}) {
      commit('setProcessing', true)
      return service.post('/kariyer/changeorder/'+id+'?direction='+direction)
      .then(
        response => {
          commit('updateItems', response.data)
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
