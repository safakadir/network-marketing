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

    attributeOf: state => (id, attrKey) => {
      const item = state.items.find(b => b.id == id);
      if(!item) return null;
      return item[attrKey];
    },
  },
  mutations: {
    setItems(state, payload) {
      state.items = payload
    },
    addItem(state, payload) {
      state.items.unshift(payload);
    },
    updateItem(state, payload) {
      for(let i = 0; i < state.items.length; i++) {
        const item = state.items[i];
        if(item.id == payload.id) {
          state.items[i] = payload;
          break;
        }
      }
    },
    removeItem(state, id) {
      let indexToRemove = -1;
      for(let i = 0; i < state.items.length; i++) {
        const item = state.items[i];
        if(item.id == id) {
          indexToRemove = i;
          break;
        }
      }
      if(indexToRemove > -1) {
        state.items.splice(indexToRemove, 1);
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
      return service.get('/urun/kategori')
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

    add({commit}, payload) {
      commit('setProcessing', true)
      return service.post('/urun/kategori', payload)
      .then(
        response => {
          commit('addItem', response.data)
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

    update({commit}, payload) {
      commit('setProcessing', true)
      return service.put('/urun/kategori', payload)
      .then(
        response => {
          commit('updateItem', response.data)
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

    remove({commit}, id) {
      commit('setProcessing', true)
      return service.delete('/urun/kategori/'+id)
      .then(
        response => {
          commit('removeItem', id)
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
    }
  }
}
