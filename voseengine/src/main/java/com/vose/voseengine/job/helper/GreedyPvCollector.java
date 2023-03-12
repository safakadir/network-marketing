package com.vose.voseengine.job.helper;

import com.vose.voseengine.model.entity.BayiPuan;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class GreedyPvCollector implements PvCollector {
    @Override
    public PvCollection collect(long bayiId, List<BayiPuan> altPuanlar, int hedefPuan, double maxSingleCoef) {
        altPuanlar.sort(new Comparator<BayiPuan>() {
            @Override
            public int compare(BayiPuan o1, BayiPuan o2) {
                BigDecimal bd1 = new BigDecimal(o1.getPv());
                BigDecimal bd2 = new BigDecimal(o2.getPv());
                return -bd1.compareTo(bd2); //sort desending
            }
        });

        List<PvProjection> projectionList = new ArrayList<>();
        double maxForSingle = hedefPuan*maxSingleCoef;
        double remainingPercentage = 100;
        for(int i = 0; i < altPuanlar.size(); i++) {
            BayiPuan puan = altPuanlar.get(i);
            double kalanYuzdeMiktar = hedefPuan/100*remainingPercentage;
            double alinan = Math.min(Math.min(puan.getPv(), maxForSingle), kalanYuzdeMiktar);
            double yuzde = alinan*100/hedefPuan;
            projectionList.add(new PvProjection(puan.getAltBayiId(), alinan, puan.getPv(), yuzde));
            remainingPercentage -= yuzde;
            if(BigDecimal.valueOf(remainingPercentage).compareTo(BigDecimal.ZERO) <= 0) break;
        }
        return new PvCollection(bayiId, projectionList, hedefPuan);
    }
}
