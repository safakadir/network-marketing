package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.entity.Kategori;
import com.vose.voseengine.service.KategoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/urun/kategori")
public class KategoriController {

    @Autowired
    private KategoriService kategoriService;

    @GetMapping
    public List<Kategori> getKategoriler() {
        return kategoriService.findAll();
    }

    @GetMapping("/{kategoriId}")
    public Kategori getKategori(@PathVariable("kategoriId") Long kategoriId) {
        return kategoriService.findOneById(kategoriId);
    }

    @PostMapping
    @Authorize(type = "ADMIN")
    public Kategori addKategori(@RequestBody Kategori kategori) {
        return kategoriService.addEntity(kategori);
    }

    @PutMapping
    @Authorize(type = "ADMIN")
    public Kategori updateKategori(@RequestBody Kategori kategori) {
        return kategoriService.updateEntity(kategori);
    }

    @DeleteMapping("/{kategoriId}")
    @Authorize(type = "ADMIN")
    public boolean deleteKategori(@PathVariable("kategoriId") Long kategoriId) {
        return kategoriService.deleteEntity(kategoriId);
    }
}
