package com.vose.voseengine.service.helper;

import com.vose.voseengine.Util;
import com.vose.voseengine.model.entity.*;
import com.vose.voseengine.model.service.MetaSiparis;
import com.vose.voseengine.model.service.SepetItem;
import com.vose.voseengine.service.AdresService;
import com.vose.voseengine.service.AppSettingService;
import com.vose.voseengine.service.BayiService;
import com.vose.voseengine.service.UrunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SepetCalculator {

    @Autowired
    private BayiService bayiService;
    @Autowired
    private UrunService urunService;
    @Autowired
    private AppSettingService settingService;
    @Autowired
    private AdresService adresService;

    @Value("${vose.settingNames.kargoUcret}")
    private String KARGO_UCRET_KEY;
    @Value("${vose.settingNames.kargoUstLimit}")
    private String KARGO_UST_LIMIT_KEY;

    public Siparis calculateSiparis(MetaSiparis metaSiparis) {
        Bayi bayi = null;
        if(metaSiparis.getBayiId() != null) {
            bayi = bayiService.findOneById(metaSiparis.getBayiId());
        }

        if(metaSiparis.getIndirim() > 0 && bayi == null) {
            throw new RuntimeException("Bayi olmadan indirim olamaz!");
        }
        if(metaSiparis.isPaketAlisveris() && bayi == null) {
            throw new RuntimeException("Bayi olmadan paket fark alışverişi olamaz!");
        }
        if(bayi != null && metaSiparis.isPaketAlisveris() && metaSiparis.getIndirim() > 0) {
            throw new RuntimeException("Paket fark alışverişinde indirim olamaz!");
        }
        if(bayi != null && !metaSiparis.isPaketAlisveris() && bayi.getKariyer().getAlisverisIndirimi() != metaSiparis.getIndirim()) {
            throw new RuntimeException(String.format("%s, belirtilen %d kadar indirim oranına sahip  değil. Gerçek oran: %d",
                    bayi.getIdentity(), metaSiparis.getIndirim(), bayi.getKariyer().getAlisverisIndirimi()));
        }

        List<Long> urunIds = metaSiparis.getUrunler().stream().map(SepetItem::getUrunId).collect(Collectors.toList());
        Map<Long, Urun> urunMap = Util.convertToMap(urunService.findManyByIds(urunIds));

        List<SiparisDetay> siparisDetayItems = new ArrayList<>();
        for (SepetItem sepetItem : metaSiparis.getUrunler()) {
            Urun urun = urunMap.get(sepetItem.getUrunId());
            SiparisDetay siparisDetay = new SiparisDetay();
            //siparisDetay.setUrun(urun);
            siparisDetay.setUrunId(sepetItem.getUrunId());
            siparisDetay.setMiktar(sepetItem.getMiktar());
            siparisDetay.setKatalogBirimFiyat(urun.getSatisFiyat());
            siparisDetay.setIndirimliBirimFiyat(Util.normalizeDouble(urun.getSatisFiyat()*(100-metaSiparis.getIndirim())/100.0));
            siparisDetay.setTutar(Util.normalizeDouble(siparisDetay.getIndirimliBirimFiyat()*sepetItem.getMiktar()));
            siparisDetay.setBirimPv(urun.getPv());
            siparisDetay.setBirimCv(urun.getCv());
            siparisDetay.setToplamPv(urun.getPv()*sepetItem.getMiktar());
            siparisDetay.setToplamCv(urun.getCv()*sepetItem.getMiktar());
            siparisDetayItems.add(siparisDetay);
        }

        double toplamFiyat = siparisDetayItems.stream().map(SiparisDetay::getTutar).reduce(0.0, Double::sum);
        if(Util.compareDoubles(toplamFiyat, metaSiparis.getToplamFiyat()) != 0) {
            throw new RuntimeException(String.format("Gelen toplam tutar %.2f ile sunucu tarafında hesaplanan tutar %.2f ile uyuşmuyor!", metaSiparis.getToplamFiyat(), toplamFiyat));
        }

        double kargoDefaultUcret = settingService.readSetting(KARGO_UCRET_KEY).getDouble();
        double kargoUstLimit = settingService.readSetting(KARGO_UST_LIMIT_KEY).getDouble();
        double kargoUcreti = BigDecimal.valueOf(toplamFiyat).compareTo(BigDecimal.valueOf(kargoUstLimit)) >= 0 ? 0 : kargoDefaultUcret;
        double odenecekTutar = toplamFiyat+kargoUcreti;

        if(Util.compareDoubles(odenecekTutar, metaSiparis.getOdenecekTutar()) != 0) {
            throw new RuntimeException(String.format("Gelen ödenecek tutar %.2f ile sunucu tarafında hesaplanan tutar %.2f ile uyuşmuyor!", metaSiparis.getOdenecekTutar(), odenecekTutar));
        }

        int urunSayisi = metaSiparis.getUrunler().stream().map(SepetItem::getMiktar).reduce(0, Integer::sum);
        double toplamPv = siparisDetayItems.stream().map(SiparisDetay::getToplamPv).reduce(0.0, Double::sum);
        double toplamCv = siparisDetayItems.stream().map(SiparisDetay::getToplamCv).reduce(0.0, Double::sum);

        Siparis siparis = new Siparis();
        siparis.setSiparisItems(siparisDetayItems);
        siparis.setBayi(SubBayi.of(bayi));
        siparis.setUrunSayisi(urunSayisi);
        siparis.setOdemeYontemi(metaSiparis.getOdemeYontemi());
        siparis.setIndirim(metaSiparis.getIndirim());
        siparis.setUrunTutar(Util.normalizeDouble(toplamFiyat));
        siparis.setOdenenTutar(Util.normalizeDouble(odenecekTutar));
        siparis.setToplamPv(Util.normalizeDouble(toplamPv));
        siparis.setToplamCv(Util.normalizeDouble(toplamCv));
        siparis.setSiparisVeren(bayi != null ? bayi.getIsimSoyisim() : metaSiparis.getIsimSoyisim());
        siparis.setAdres(metaSiparis.getAdres());
        siparis.setPaketAlisveris(metaSiparis.isPaketAlisveris());
        siparis.setSiparisDurum(Siparis.Durum.ODEME_BEKLENIYOR);
        siparis.setSonGuncelleyen("system");

        return siparis;
    }
}
