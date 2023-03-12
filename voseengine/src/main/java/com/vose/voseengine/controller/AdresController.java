package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.entity.Adres;
import com.vose.voseengine.service.AdresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adres")
public class AdresController {

    @Autowired
    private AdresService adresService;

    @GetMapping
    @Authorize(type = "ADMIN")
    public List<Adres> getAdresler() {
        return adresService.findAll();
    }

    @GetMapping("/bayi/{bayiId}")
    @Authorize(type = "BAYI")
    public List<Adres> getAdreslerByBayiId(@PathVariable("bayiId") Long bayiId) {
        return adresService.findByBayiId(bayiId);
    }

    @GetMapping("/{adresId}")
    @Authorize(type = "BAYI")
    public Adres getAdres(@PathVariable("adresId") Long adresId) {
        if(HelperUtil.isAdminFromContext()) {
            return adresService.findOneById(adresId);
        }
        else {
            Long bayiId = HelperUtil.userIdFromContext();
            return adresService.findOneById(bayiId, adresId);
        }
    }

    @PostMapping
    @Authorize(type = "BAYI")
    public Adres addAdres(@RequestBody Adres adres) {
        return adresService.addEntity(adres);
    }

    @PutMapping
    @Authorize(type = "BAYI")
    public Adres updateAdres(@RequestBody Adres adres) {
        return adresService.updateEntity(adres);
    }

    @DeleteMapping("/{adresId}")
    @Authorize(type = "BAYI")
    public boolean deleteAdres(@PathVariable("adresId") Long adresId) {
        if(HelperUtil.isAdminFromContext()) {
            return adresService.deleteEntity(adresId);
        }
        else {
            Long bayiId = HelperUtil.userIdFromContext();
            return adresService.deleteEntity(bayiId, adresId);
        }
    }
}
