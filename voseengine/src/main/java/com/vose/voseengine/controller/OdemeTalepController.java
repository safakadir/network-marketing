package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.entity.OdemeTalep;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.service.OdemeTalepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odemetalep")
public class OdemeTalepController {

    @Autowired
    private OdemeTalepService odemeTalepService;

    @GetMapping
    @Authorize(type = "ADMIN")
    public PageResult<OdemeTalep> getAllOdemeTalepler(
            @RequestParam(value = "durum", defaultValue = "NONE") OdemeTalep.Durum durum,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) int perPage) {
        return odemeTalepService.queryPaginated(durum, page, perPage);
    }

    @GetMapping("/bayi/{bayiId}")
    @Authorize(type = "BAYI")
    public List<OdemeTalep> getOdemeTaleplerByBayiId(@PathVariable("bayiId") Long bayiId,
                                                     @RequestParam(value = "durum", defaultValue = "NONE") OdemeTalep.Durum durum,
                                                     @RequestParam(value = "start", defaultValue = "0", required = false) long start,
                                                     @RequestParam(value = "end", defaultValue = "0", required = false) long end) {
        return odemeTalepService.query(bayiId, durum, start, end);
    }

    @PostMapping
    @Authorize(type = "BAYI")
    public OdemeTalep addOdemeTalep(@RequestBody OdemeTalep odemeTalep) {
        return odemeTalepService.addEntity(odemeTalep);
    }

    @PutMapping("/cancel/{odemeTalepId}")
    @Authorize(type = "BAYI")
    public OdemeTalep cancelTalep(@PathVariable("odemeTalepId") Long odemeTalepId) {
        OdemeTalep odemeTalep;
        if(HelperUtil.isAdminFromContext()) {
            odemeTalep = odemeTalepService.findOneById(odemeTalepId);
        }
        else {
            Long bayiId = HelperUtil.userIdFromContext();
            odemeTalep = odemeTalepService.findOneById(bayiId, odemeTalepId);
        }
        if(odemeTalep == null) return null;
        odemeTalep.setDurum(OdemeTalep.Durum.IPTAL_EDILDI);
        return odemeTalepService.updateEntity(odemeTalep);
    }

    @PutMapping
    @Authorize(type = "ADMIN")
    public OdemeTalep updateOdemeTalep(@RequestBody OdemeTalep odemeTalep) {
        return odemeTalepService.updateEntity(odemeTalep);
    }

    @PutMapping("/approve/{odemeTalepId}")
    @Authorize(type = "ADMIN")
    public OdemeTalep approveOdemeTalep(@PathVariable("odemeTalepId") Long odemeTalepId) {
        return odemeTalepService.changeStatus(odemeTalepId, OdemeTalep.Durum.ODENDI);
    }

    @DeleteMapping("/{odemeTalepId}")
    @Authorize(type = "BAYI")
    public boolean deleteOdemeTalep(@PathVariable("odemeTalepId") Long odemeTalepId) {
        if(HelperUtil.isAdminFromContext()) {
            return odemeTalepService.deleteEntity(odemeTalepId);
        }
        else {
            Long bayiId = HelperUtil.userIdFromContext();
            return odemeTalepService.deleteEntity(bayiId, odemeTalepId);
        }
    }
}
