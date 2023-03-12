
import service from '@/service'

import { setPersistedLogin, getPersistedLogin } from '../../utils'
import { gutil } from '../../utils/generic'

export default {
  state: {
    login: getPersistedLogin(),
    loginError: null,
    loginloginProcessing: false,
    registeredBayi: null,
    registerError: null,
    registerProcessing: false,
    forgotMailSuccess:null,
    resetPasswordSuccess:null
  },
  getters: {
    login: state => state.login,
    loginType: state => gutil.getProperty(state.login, 'type'),
    bayi: state => gutil.getProperty(state.login, 'bayi'),
    admin: state => gutil.getProperty(state.login, 'admin'),
    bayiId: state => gutil.getProperty(state.login, 'bayi.id'),
    accessToken: state => gutil.getProperty(state.login, 'accessToken'),
    refreshToken: state => gutil.getProperty(state.login, 'refreshToken'),
    
    loginProcessing: state => state.loginProcessing,
    loginError: state => state.loginError,
    registerProcessing: state => state.registerProcessing,
    registerError: state => state.registerError,
    registeredBayi: state => state.registeredBayi,

    kariyerIndirim: state => {
      const indirim = gutil.getProperty(state.login, 'bayi.kariyer.alisverisIndirimi');
      if(!indirim) return 0;
      return indirim;
    },
    kayitDurum: state => gutil.getProperty(state.login, 'bayi.kayitDurum')
  },
  mutations: {
    setLogin(state, payload) {
      if(payload.admin) {
        payload.bayi = { isimSoyisim: payload.admin.isimSoyisim, email: payload.admin.email, telefon1: payload.admin.telefon}; //temp
      }
      state.login = payload
      state.loginProcessing = false
      state.loginError = null
      service.defaults.headers.common['Authorization'] = 'Bearer '+payload.accessToken;
    },
    setLogout(state) {
      state.login = null
      state.loginProcessing = false
      state.loginError = null
    },
    setLoginProcessing(state, payload) {
      state.loginProcessing = payload
      state.loginError = null
    },
    setLoginError(state, payload) {
      state.loginError = payload
      state.login = null
      state.loginProcessing = false
    },
    setRegister(state, payload) {
      state.registeredBayi = payload;
      state.registerProcessing = false
      state.registerError = null
    },
    setRegisterProcessing(state, payload) {
      state.registerProcessing = payload
      state.registerError = null
    },
    setRegisterError(state, payload) {
      state.registerError = payload
      state.registerProcessing = false
    },
    clearError(state) {
      state.loginError = null
      state.registerError = null
    },
  },
  actions: {
    loginAction({ commit, dispatch }, payload) {
      commit('clearError')
      commit('setLoginProcessing', true)
      return service.post('/auth'+(payload.admin ? '/admin' : ''), {username: payload.username, password: payload.password})
        .then(
          response => {
            setPersistedLogin(response.data)
            commit('setLogin', response.data)
            dispatch('recalculateSepet');
            return response.data;
          },
          err => {
            setPersistedLogin(null);
            commit('setLoginError', err.message)
            setTimeout(() => {
              commit('clearError')
            }, 3000)
          }
        )
    },
    checkLogin({commit, getters}, payload) {
      commit('clearError')
      const refreshToken = getters.refreshToken || payload ? payload.refreshToken : null;
      if(!refreshToken) return Promise.resolve();
      commit('setLoginProcessing', true)
      return service.get('/auth', {headers: {'Authorization': 'Bearer '+refreshToken}})
      .then(response => {
        setPersistedLogin(response.data)
        commit('setLogin', response.data)
        return response.data;
      })
      .catch(err => {
        setPersistedLogin(null);
        commit('setLoginError', err.message)
        setTimeout(() => {
          commit('clearError')
        }, 3000)
        return Promise.reject(err);
      })
    },
    signOut({ commit, dispatch }) {
      setPersistedLogin(null);
      commit('setLogout')
      commit('setPaketAlisveris', false, {root: true})
      dispatch('recalculateSepet');
    },
    registerAction({ commit }, payload) {
      commit('clearError')
      commit('setRegisterProcessing', true)

      return service.post('/bayi', {bayi: payload, sifre: payload.sifre})
        .then(
          response => {
            commit('setRegister', response.data);
            return response.data;
          },
          err => {
            commit('setRegisterError', err.message)
            setTimeout(() => {
              commit('clearError')
            }, 3000)
          }
        )
    },
  }
}
