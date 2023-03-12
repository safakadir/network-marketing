package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.entity.Cuzdan;
import com.vose.voseengine.service.CuzdanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuzdan")
public class CuzdanController {

    @Autowired
    private CuzdanService cuzdanService;

    @GetMapping("/bayi/{bayiId}")
    @Authorize(type = "BAYI")
    public List<Cuzdan> getCuzdanHareketler(@PathVariable("bayiId") Long bayiId,
                                            @RequestParam(value = "start", defaultValue = "0", required = false) long start,
                                            @RequestParam(value = "end", defaultValue = "0", required = false) long end) {
        return cuzdanService.query(bayiId, start, end);
    }

    @GetMapping("/bayi/{bayiId}/latest")
    @Authorize(type = "BAYI")
    public Cuzdan getCuzdanLatest(@PathVariable("bayiId") Long bayiId) {
        return cuzdanService.findLatestOne(bayiId);
    }
}
