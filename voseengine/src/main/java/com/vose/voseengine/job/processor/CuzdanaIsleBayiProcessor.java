package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.model.entity.Cuzdan;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.repository.CuzdanRepository;
import com.vose.voseengine.Util;
import com.vose.voseengine.model.entity.Kazanc;
import com.vose.voseengine.repository.KazancRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuzdanaIsleBayiProcessor implements JobProcessor {
    @Autowired
    private KazancRepository kazancRepository;
    @Autowired
    private CuzdanRepository cuzdanRepository;
    @Autowired
    private JobManager jobManager;

    @Transactional
    @Override
    public void process(Job job) {
        List<Kazanc> aylikKazanc = kazancRepository.getAylikKazanc(job.getBayiIdArg(), Util.startOfMonthByKey(job.getMonthKeyArg()));
        if(aylikKazanc.size() > 0) {
            double aylikToplamKazanc = aylikKazanc.stream().map(Kazanc::getMiktar).reduce(0.0, Double::sum);
            if(BigDecimal.valueOf(aylikToplamKazanc).compareTo(BigDecimal.ZERO) <= 0) return;
            Cuzdan latest = cuzdanRepository.findTopByBayiIdOrderByIdDesc(job.getBayiIdArg());
            double prevToplam = latest != null ? latest.getCuzdanToplam() : 0;
            Cuzdan cuzdan = new Cuzdan();
            cuzdan.setBayiId(job.getBayiIdArg());
            cuzdan.setMiktar(aylikToplamKazanc);
            cuzdan.setYon(+1);
            cuzdan.setCuzdanToplam(Util.round(prevToplam+aylikToplamKazanc, 2));
            cuzdan.setAciklama(Util.localeMonthText(job.getMonthKeyArg())+" kazancınız.");
            cuzdanRepository.save(cuzdan);
            aylikKazanc.forEach(k -> {
                k.setCuzdanaIslendi(true);
                kazancRepository.save(k);
            });
        }
    }
}
