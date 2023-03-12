package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.job.helper.PvCollection;
import com.vose.voseengine.model.entity.Bayi;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.model.entity.Kariyer;
import com.vose.voseengine.model.entity.SubBayi;
import com.vose.voseengine.model.service.BayiAgac;
import com.vose.voseengine.model.service.BayiContainer;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.model.service.PuanEkleRequest;
import com.vose.voseengine.service.BayiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bayi")
public class BayiController {
    @Autowired
    private BayiService bayiService;

    @GetMapping
    @Authorize(type = "ADMIN")
    public PageResult<Bayi> getBayiler(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) int perPage) {
        return bayiService.findPaginated(page, perPage);
    }

    @PutMapping("/by/ids")
    @Authorize(type = "ADMIN")
    public ResponseEntity<List<SubBayi>> getSubBayilerByIds(@RequestBody List<Long> ids) {
        return ResponseEntity.ok()
                .header("Request-On-Fly", "true") // this custom header prevents client showing extra success message for response of this request
                .body(bayiService.findByBayiIds(ids));
    }

    @PutMapping("/search")
    @Authorize(type = "ADMIN")
    public ResponseEntity<List<SubBayi>> getSubBayilerBySearch(@RequestBody String searchParam) {
        return ResponseEntity.ok()
                .header("Request-On-Fly", "true")
                .body(bayiService.findBySearchParam(searchParam));
    }

    @GetMapping("/{bayiId}")
    @Authorize(type = "BAYICHAIN")
    public Bayi getBayi(@PathVariable("bayiId") Long bayiId) {
        return bayiService.findOneById(bayiId);
    }

    @GetMapping("/{bayiId}/next")
    @Authorize(type = "BAYI")
    public Kariyer getNextKariyer(@PathVariable("bayiId") Long bayiId) {
        Kariyer next = bayiService.getNextKariyer(bayiId);
        if(next == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No more next kariyer for bayi "+bayiId);
        }
        return next;
    }

    @GetMapping("/{bayiId}/pv")
    @Authorize(type = "BAYI")
    public PvCollection getPvCollection(@PathVariable("bayiId") Long bayiId) {
        return bayiService.getPvCollection(bayiId);
    }

    @GetMapping("/{bayiId}/agac")
    @Authorize(type = "BAYI")
    public BayiAgac getBayiAgac(@PathVariable("bayiId") Long bayiId) {
        return bayiService.getBayiAgac(bayiId);
    }

    @PostMapping
    public Bayi addBayi(@RequestBody BayiContainer bayiContainer) {
        Bayi bayi = bayiContainer.getBayi();
        bayi.setSifre(bayiContainer.getSifre());
        return bayiService.addEntity(bayi);
    }

    @PutMapping
    @Authorize(type = "BAYI")
    public Bayi updateBayi(@RequestBody Bayi bayi) {
        return bayiService.updateEntity(bayi);
    }

    @DeleteMapping("/{bayiId}")
    @Authorize(type = "ADMIN")
    public boolean deleteBayi(@PathVariable("bayiId") Long bayiId) {
        return bayiService.deleteEntity(bayiId);
    }

    @PutMapping("/puan")
    @Authorize(type = "ADMIN")
    public Job puanEkle(@RequestBody PuanEkleRequest request) {
        return bayiService.requestPuanEkle(request);
    }
}
