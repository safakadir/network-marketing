import { defaultDirection, defaultColor, themeSelectedColorStorageKey, themeRadiusStorageKey, localeOptions, defaultLocale } from '../constants/config'



export const mapOrder = (array, order, key) => {
  array.sort(function (a, b) {
    var A = a[key]
    var B = b[key]
    if (order.indexOf(A + '') > order.indexOf(B + '')) {
      return 1
    } else {
      return -1
    }
  })
  return array
}

export const getDateWithFormat = () => {
  const today = new Date()
  let dd = today.getDate()
  let mm = today.getMonth() + 1 // January is 0!

  var yyyy = today.getFullYear()
  if (dd < 10) {
    dd = '0' + dd
  }
  if (mm < 10) {
    mm = '0' + mm
  }
  return dd + '.' + mm + '.' + yyyy
}

export const getCurrentTime = () => {
  const now = new Date()
  return now.getHours() + ':' + now.getMinutes()
}

export const ThemeColors = () => {
  let rootStyle = getComputedStyle(document.body)
  return {
    themeColor1: rootStyle.getPropertyValue('--theme-color-1').trim(),
    themeColor2: rootStyle.getPropertyValue('--theme-color-2').trim(),
    themeColor3: rootStyle.getPropertyValue('--theme-color-3').trim(),
    themeColor4: rootStyle.getPropertyValue('--theme-color-4').trim(),
    themeColor5: rootStyle.getPropertyValue('--theme-color-5').trim(),
    themeColor6: rootStyle.getPropertyValue('--theme-color-6').trim(),
    themeColor1_10: rootStyle.getPropertyValue('--theme-color-1-10').trim(),
    themeColor2_10: rootStyle.getPropertyValue('--theme-color-2-10').trim(),
    themeColor3_10: rootStyle.getPropertyValue('--theme-color-3-10').trim(),
    themeColor4_10: rootStyle.getPropertyValue('--theme-color-3-10').trim(),
    themeColor5_10: rootStyle.getPropertyValue('--theme-color-3-10').trim(),
    themeColor6_10: rootStyle.getPropertyValue('--theme-color-3-10').trim(),
    primaryColor: rootStyle.getPropertyValue('--primary-color').trim(),
    foregroundColor: rootStyle.getPropertyValue('--foreground-color').trim(),
    separatorColor: rootStyle.getPropertyValue('--separator-color').trim()
  }
}

export const getDirection = () => {
  let direction = defaultDirection
  if (localStorage.getItem('direction')) {
    const localValue = localStorage.getItem('direction')
    if (localValue === 'rtl' || localValue === 'ltr') {
      direction = localValue
    }
  }
  return {
    direction,
    isRtl: direction === 'rtl'
  }
}

export const setDirection = localValue => {
  let direction = 'ltr'
  if (localValue === 'rtl' || localValue === 'ltr') {
    direction = localValue
  }
  localStorage.setItem('direction', direction)
}


export const getThemeColor = () => {
  let color = defaultColor;
  try {
    if (localStorage.getItem(themeSelectedColorStorageKey)) {
      color = localStorage.getItem(themeSelectedColorStorageKey) || defaultColor;
    }
  } catch (error) {
    console.log(">>>> src/utils/index.js : getThemeColor -> error", error)
    color = defaultColor;
  }
  return color;
}

export const setThemeColor = (color) => {
  try {
    localStorage.setItem(themeSelectedColorStorageKey, color);
  } catch (error) {
    console.log(">>>> src/utils/index.js : setThemeColor -> error", error)
  }
}

export const getThemeRadius = () => {
  let radius = "rounded";
  try {
    if (localStorage.getItem(themeRadiusStorageKey)) {
      radius = localStorage.getItem(themeRadiusStorageKey) || "rounded";
    }
  } catch (error) {
    console.log(">>>> src/utils/index.js : getThemeRadius -> error", error)
    radius = "rounded";
  }
  return radius;
}

export const setThemeRadius = (radius) => {
  try {
    localStorage.setItem(themeRadiusStorageKey, radius);
  } catch (error) {
    console.log(">>>> src/utils/index.js : setThemeRadius -> error", error)
  }
}

export const getCurrentLanguage = () => {
  let locale = defaultLocale;
  try {
    if (localStorage.getItem('currentLanguage') && localeOptions.filter(x => x.id === localStorage.getItem('currentLanguage')).length > 0) { locale = localStorage.getItem('currentLanguage'); }
  } catch (error) {
    console.log(">>>> src/utils/index.js : getCurrentLanguage -> error", error)
    locale = defaultLocale;
  }
  return locale;
}

export const setCurrentLanguage = (lang) => {
  try {
    localStorage.setItem('currentLanguage', lang)
  } catch (error) {
    console.log(">>>> src/utils/index.js : setCurrentLanguage -> error", error)
  }
}

export const getPersistedLogin = () => {
  let login = null;
  try {
    login = localStorage.getItem('login') != null ? JSON.parse(localStorage.getItem('login')) : null;
  } catch (error) {
    console.log(">>>> src/utils/index.js : getPersistedLogin -> error", error)
    login = null;
  }
  return login;
}

export const setPersistedLogin = (login) => {
  try {
    if (login) {
      localStorage.setItem('login', JSON.stringify(login))
    } else {
      localStorage.removeItem('login');
    }
  } catch (error) {
    console.log(">>>> src/utils/index.js : setPersistedLogin -> error", error)
  }
}

export const loadSepet = () => {
  let sepet = [];
  try {
    const data = localStorage.getItem('sepet');
    sepet = data != null ? JSON.parse(data) : [];
  } catch (error) {
    console.log(">>>> src/utils/index.js : loadSepet -> error", error)
    sepet = [];
  }
  return sepet;
}

export const convertToMetaSepet = (sepet) => {
  return sepet.map(sepetUrun => {
    return {miktar: sepetUrun.miktar, siraNo: sepetUrun.siraNo, urunId: sepetUrun.urun.id}
  });
}

export const persistSepet = (sepet) => {
  try {
    if(sepet && Array.isArray(sepet) && sepet.length > 0) {
      const sepetToBeSaved = convertToMetaSepet(sepet);
      localStorage.setItem('sepet', JSON.stringify(sepetToBeSaved));
      const sepetCount = sepetToBeSaved.map(s => s.miktar).reduce((s,v) => s+v, 0);
      localStorage.setItem('sepetCount', sepetCount);
    } else {
      localStorage.removeItem('sepet');
      localStorage.removeItem('sepetCount');
    }
  } catch (error) {
    console.log(">>>> src/utils/index.js : persistSepet -> error", error)
  }
}

export const extendUrun = (urun, indirim) => {
  const indirimliNetFiyat = indirim > 0 ? (urun.netFiyat*(100-indirim)/100).normalize() : urun.netFiyat;;
  urun.indirimOrani = indirim;
  urun.indirimliNetFiyat = indirimliNetFiyat;
  urun.indirimliSatisFiyat = indirim > 0 ? (urun.satisFiyat*(100-indirim)/100).normalize() : urun.satisFiyat;
  urun.kdvMiktar = (urun.netFiyat*urun.kdv/100).normalize();
  urun.indirimliKdvMiktar = (indirimliNetFiyat*urun.kdv/100).normalize();
  return urun;
}
