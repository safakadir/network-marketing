package com.vose.voseengine;

import com.vose.voseengine.job.helper.GreedyPvCollector;
import com.vose.voseengine.job.helper.PvCollection;
import com.vose.voseengine.job.helper.PvCollector;
import com.vose.voseengine.model.entity.BayiPuan;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PvCollectorTest {

    private List<BayiPuan> createAltPuanlar(Double... puanlar) {
        List<BayiPuan> result = new ArrayList<>();
        int count = 0;
        for (Double puan : puanlar) {
            BayiPuan p = new BayiPuan();
            p.setAltBayiId(10L + ++count);
            p.setBayiId(1L);
            p.setPv(puan);
            result.add(p);
        }
        return result;
    }

    @Test
    public void testPvCollector() {
        PvCollector pvCollector = new GreedyPvCollector();

        PvCollection pvCollection;

        pvCollection = pvCollector.collect(1, createAltPuanlar(1000.0, 1000.0, 500.0), 2500, 0.4);
        System.out.println(pvCollection+"\n");

        pvCollection = pvCollector.collect(1, createAltPuanlar(1500.0, 1500.0, 200.0), 2500, 0.4);
        System.out.println(pvCollection+"\n");

        pvCollection = pvCollector.collect(1, createAltPuanlar(2500.0, 1500.0), 2500, 0.4);
        System.out.println(pvCollection+"\n");

        pvCollection = pvCollector.collect(1, createAltPuanlar(1500.0, 1500.0, 1000.0), 2500, 0.4);
        System.out.println(pvCollection+"\n");

        pvCollection = pvCollector.collect(1, createAltPuanlar(1000.0, 1000.0, 900.0), 2500, 0.4);
        System.out.println(pvCollection+"\n");

        pvCollection = pvCollector.collect(1, createAltPuanlar(900.0, 900.0, 700.0), 2500, 0.4);
        System.out.println(pvCollection+"\n");

        pvCollection = pvCollector.collect(1, createAltPuanlar(1500.0, 1500.0, 1500.0, 1500.0), 2500, 0.4);
        System.out.println(pvCollection+"\n");

        pvCollection = pvCollector.collect(1, createAltPuanlar(450.0, 400.0, 300.0, 500.0, 600.0, 500.0), 2500, 0.4);
        System.out.println(pvCollection+"\n");
    }
}
