import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import router from '@/router'
import { serviceConfig } from '@/constants/config'

const instance = axios.create({
    baseURL: serviceConfig.url,
    timeout: serviceConfig.timeout,
    //headers: {'X-Custom-Header': 'foobar'}
  });

instance.interceptors.response.use(res => {
  if(res.config.method != 'get' && !res.headers['request-on-fly']) {
    Vue.$notify('success', "Başarılı", null, { 
      duration: 2000,
      permanent: false
    });
  }
  return res;
}, err => {
  // TODO 0. handle status codes. 
  // future1. default mesajları replace et, sunucudan gelen specific mesajları göster.
  // future2. sunucudan dil değişkeni adı al, veya hata kodlarına karşılık gelen dil değişkenleri üret.

  if(err.config.method == 'post' && err.config.url.startsWith('/auth') && err.response && err.response.status == 401) {
    //kullanıcı adı şifre ile login denemiş, başarısız olmuş. geri kalanı login sayfası handle ediyor. buradaki işi bitir.
    return Promise.reject(err);
  }

  let title = "Hata";
  let message = err.message;

  const originalRequest = err.config;
  if(err.response && err.response.status == 401) { // token hatası, unauthorized
    const isRefreshRequest = originalRequest.url == '/auth' && originalRequest.method == 'get';
    // refresh request ya ilk sayfa açıldığında hafızadaki token içindir, ya da vart olan access token arıza yapmıştır onun içindir.
    // her iki durumda da refresh request hata alırsa tekrar deneme olmamalı
    if(!isRefreshRequest && !!store.getters.refreshToken) { //ilk defa 401 hatasını alıyor. refresh denenecek, refresh token var ise tabi
      originalRequest._retry = true;
      const authHeader = "Bearer "+store.getters.refreshToken;
      return instance.get('/auth', {'headers': {'Authorization': authHeader}})
      .then(response => {
        store.commit('setLogin', response.data);
      })
      .catch((err2) => {
        return Promise.reject(err2);
      });
    }
    else { // şansını kaybetti artık
      title = "Oturum Hatası";
      message = "Oturumunuz sonlandırılıyor."
      const isAdmin = !!store.getters.admin;
      store.dispatch('signOut');
      if(!isAdmin)
        router.push('/login');
      else
        router.push('/login/admin');
    }
  }
  Vue.$notify("error", title, message, { 
    duration: 5000,
    permanent: false
  });
  console.error(err);
  return Promise.reject(err);
})

export default instance;