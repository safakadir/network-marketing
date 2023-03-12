import Vue from 'vue'
import service from '@/service'
import { loadSepet, persistSepet, convertToMetaSepet, extendUrun } from './../../utils'

export default {
  state: {
    sepetUrunler: [],
    savedSiparis: {},
    processing: false,
    sepetFetchError: null,
    isPaketAlisveris: JSON.parse(localStorage.getItem('paketAlisveris', false)),
  },
  getters: {
    sepetUrunler: state => state.sepetUrunler,
    sepetCount: state => state.sepetUrunler.length,
    sepetUrunCount: state => state.sepetUrunler.map(u => u.miktar).reduce((s,v) => s+v, 0),
    sepetToplamNetIndirimli: state => state.sepetUrunler.map(u => u.miktar*u.urun.indirimliNetFiyat).reduce((s,v) => s+v, 0).normalize(),
    sepetToplamKdvIndirimli: state => state.sepetUrunler.map(u => u.miktar*u.urun.indirimliKdvMiktar).reduce((s,v) => s+v, 0).normalize(),
    sepetToplamFiyatIndirimsiz: state => state.sepetUrunler.map(u => u.miktar*u.urun.satisFiyat).reduce((s,v) => s+v, 0).normalize(),
    sepetToplamFiyat: state => state.sepetUrunler.map(u => u.miktar*u.urun.indirimliSatisFiyat).reduce((s,v) => s+v, 0).normalize(),
    sepetKargoUcret: (state, getters, rootState, rootGetters) => {
      const ustLimit = rootGetters['settings/settingMap'].kargoUstLimit;
      const ucret = rootGetters['settings/settingMap'].kargoUcret;
      return ustLimit && getters.sepetToplamFiyat >= ustLimit ? 0 : (ucret ? ucret : 0);
    },
    sepetOdenecekTutar: (state, getters) => (getters.sepetToplamFiyat+getters.sepetKargoUcret).normalize(),
    sepetToplamPv: state => state.sepetUrunler.map(u => u.miktar*u.urun.pv).reduce((s,v) => s+v, 0).normalize(),
    sepetToplamCv: state => state.sepetUrunler.map(u => u.miktar*u.urun.cv).reduce((s,v) => s+v, 0).normalize(),
    indirim: (state, getters, rootState, rootGetters) => (getters.isPaketAlisveris || !rootGetters.kayitDurum) ? 0 : rootGetters.kariyerIndirim,
    isPaketAlisveris: state => state.isPaketAlisveris,
    savedSiparis: state => state.savedSiparis,

    sepetFetchError: state => state.sepetFetchError,

    attributeOf: state => (id, attrKey) => {
      const item = state.sepetUrunler.find(b => b.id == id);
      if(!item) return null;
      return item[attrKey];
    },
  },
  mutations: {
    setSepetUrunler(state, payload) {
      state.sepetUrunler = payload;
    },
    clearSepetUrunler(state) {
      state.sepetUrunler = [];
      persistSepet(null);
    },
    addSepetUrun(state, payload) {
      if(typeof payload.miktar == 'string') payload.miktar = parseInt(payload.miktar);
      payload.urun = extendUrun(payload.urun, payload.indirim);
      let existingIndex = -1;
      for(let i = 0; i < state.sepetUrunler.length; i++) {
        const sepetUrun = state.sepetUrunler[i];
        if(sepetUrun.urun.id == payload.urun.id) {
          existingIndex = i;
          break;
        }
      }
      if(existingIndex > -1) {
        Vue.set(state.sepetUrunler[existingIndex], 'miktar', state.sepetUrunler[existingIndex].miktar+payload.miktar);
      }
      else {
        payload.siraNo = state.sepetUrunler.length;
        delete payload.indirim;
        state.sepetUrunler.push(payload);
      }
      persistSepet(state.sepetUrunler);
    },
    removeSepetUrun(state, siraNo) {
      let indexToRemove = -1;
      for(let i = 0; i < state.sepetUrunler.length; i++) {
        const sepetUrun = state.sepetUrunler[i];
        if(sepetUrun.siraNo == siraNo) {
          indexToRemove = i;
          break;
        }
      }
      if(indexToRemove > -1) {
        state.sepetUrunler.splice(indexToRemove, 1);
        for(let i = 0; i < state.sepetUrunler.length; i++) {
          state.sepetUrunler[i].siraNo = i;
        }
        persistSepet(state.sepetUrunler);
      }
    },
    changeSepetUrunMiktar(state, {siraNo, miktar}) {
      if(miktar == "") return;
      let existingIndex = -1;
      for(let i = 0; i < state.sepetUrunler.length; i++) {
        const sepetUrun = state.sepetUrunler[i];
        if(sepetUrun.siraNo == siraNo) {
          existingIndex = i;
          break;
        }
      }
      if(existingIndex > -1) {
        Vue.set(state.sepetUrunler[existingIndex], 'miktar', parseInt(miktar));
        persistSepet(state.sepetUrunler);
      }
    },
    setSavedSiparis(state, payload) {
      state.savedSiparis = payload;
    },
    clearSavedSiparis(state) {
      state.savedSiparis = {};
    },
    setPaketAlisveris(state, payload) {
      state.isPaketAlisveris = payload;
      localStorage.setItem('paketAlisveris', payload);
    },
    setProcessing(state, payload) {
      state.processing = payload
      state.sepetFetchError = null
    },
    setError(state, payload) {
      state.sepetFetchError = payload
      state.processing = false
    },
    clearError(state) {
      state.sepetFetchError = null
    }
  },
  actions: {
    reloadSepet({commit, getters}) {
      commit('setProcessing', true);
      const metaSepet = loadSepet();
      if(metaSepet.length == 0) return;
      const ids = metaSepet.map(u => u.urunId).join(',');
      service.get('urun/many?ids='+ids)
      .then(
        response => {
          const result = [];
          response.data.forEach(urun => {
            urun = extendUrun(urun, getters.indirim);
            const metaSepetItem = metaSepet.find(u => u.urunId == urun.id);
            result[metaSepetItem.siraNo] = {siraNo: parseInt(metaSepetItem.siraNo), miktar: parseInt(metaSepetItem.miktar), urun: urun};
          })
          commit('setSepetUrunler', result);
          commit('setProcessing', false);
        },
        err => {
          commit('setError', err.message)
          setTimeout(() => {
            commit('clearError')
          }, 3000)
        })
    },
    recalculateSepet({commit, getters}) {
      const result = [];
      getters.sepetUrunler.forEach(sepetUrun => {
        sepetUrun.urun = extendUrun(sepetUrun.urun, getters.indirim);
        result[sepetUrun.siraNo] = sepetUrun;
      })
      commit('setSepetUrunler', result);
    },
    saveSiparis({getters, commit}, params) {
      commit('setProcessing', true);
      const sepetItems = convertToMetaSepet(getters.sepetUrunler);
      const metaSiparis = { 
        bayiId: getters.bayiId,
        indirim: getters.indirim, 
        toplamFiyatIndirimsiz: getters.sepetToplamFiyatIndirimsiz, 
        toplamFiyat: getters.sepetToplamFiyat, 
        odenecekTutar: getters.sepetOdenecekTutar, 
        urunler: sepetItems,
        odemeYontemi: params.odemeYontemi,
        adres: params.adres,
        isimSoyisim: getters.bayi ? getters.bayi.isimSoyisim : params.isimSoyisim,
        paketAlisveris: getters.isPaketAlisveris
      };
      return service.post('/siparis', metaSiparis).then(
        response => {
          Vue.$notify("success", 'Alışveriş Başarılı', metaSiparis.odenecekTutar+" ₺ tutarındaki siparişinizi başarıyla aldık.", { 
            duration: 5000,
            permanent: false
          });
          commit('setSavedSiparis', response.data);
          commit('clearSepetUrunler');
          commit('setProcessing', false);
        },
        err => {
          commit('setError', err.message)
          setTimeout(() => {
            commit('clearError')
          }, 3000)
          return Promise.reject(err);
        })
    },
  }
}
