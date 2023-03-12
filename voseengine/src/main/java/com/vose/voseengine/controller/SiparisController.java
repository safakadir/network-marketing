package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.entity.Siparis;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.model.service.MetaSiparis;
import com.vose.voseengine.service.SiparisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/siparis")
public class SiparisController {
    private static final Logger log = LoggerFactory.getLogger(SiparisController.class);

    @Autowired
    private SiparisService siparisService;

    @GetMapping("/bayi/{bayiId}")
    @Authorize(type = "BAYI")
    public PageResult<Siparis> findSiparisler(
            @PathVariable("bayiId") Long bayiId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) int perPage,
            @RequestParam(value = "start", defaultValue = "0", required = false) long start,
            @RequestParam(value = "end", defaultValue = "0", required = false) long end)
    {
        return siparisService.findByBayiIdPaginated(bayiId, page, perPage, end, start);
    }

    @GetMapping()
    @Authorize(type = "ADMIN")
    public PageResult<Siparis> findSiparisler(
            @RequestParam(value = "durum", defaultValue = "NONE", required = false) Siparis.Durum durum,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) int perPage,
            @RequestParam(value = "start", defaultValue = "0", required = false) long start,
            @RequestParam(value = "end", defaultValue = "0", required = false) long end)
    {
        if(durum == Siparis.Durum.NONE) {
            PageResult<Siparis> retval = siparisService.findAllPaginated(page, perPage, end, start);
            return retval;
        }
        else {
            return siparisService.findByDurumPaginated(durum, page, perPage, end, start);
        }
    }


    @GetMapping("/{siparisId}")
    @Authorize(type = "BAYI")
    public Siparis getSiparis(@PathVariable("siparisId") Long siparisId) {
        if(HelperUtil.isAdminFromContext()) {
            return siparisService.findOneById(siparisId);
        }
        else {
            Long bayiId = HelperUtil.userIdFromContext();
            return siparisService.findOneById(bayiId, siparisId);
        }
    }

    @PostMapping
    public Siparis saveSiparis(@RequestBody MetaSiparis metaSiparis) {
        return siparisService.saveSiparis(metaSiparis);
    }

    @PutMapping("/{siparisId}")
    @Authorize(type = "ADMIN")
    public Siparis updateSiparisDurum(@PathVariable("siparisId") Long siparisId, @RequestParam("durum") Siparis.Durum durum) {
        return siparisService.updateSiparisDurum(siparisId, durum);
    }

}
