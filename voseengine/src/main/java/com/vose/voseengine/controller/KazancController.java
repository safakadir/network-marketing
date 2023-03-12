package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.entity.Kazanc;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.service.KazancService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kazanc")
public class KazancController {

    @Autowired
    private KazancService kazancService;

    @GetMapping
    @Authorize(type = "ADMIN")
    public PageResult<Kazanc> getKazanclar(
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "per_page", defaultValue = "10", required = false) int perPage,
                                         @RequestParam(value = "start", defaultValue = "0", required = false) long start,
                                         @RequestParam(value = "end", defaultValue = "0", required = false) long end) {
        return kazancService.findPaginated(page, perPage, start, end);
    }

    @GetMapping("/bayi/{bayiId}")
    @Authorize(type = "BAYI")
    public PageResult<Kazanc> getKazanclarOfBayi(@PathVariable("bayiId") Long bayiId,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "per_page", defaultValue = "10", required = false) int perPage,
                                                 @RequestParam(value = "start", defaultValue = "0", required = false) long start,
                                                 @RequestParam(value = "end", defaultValue = "0", required = false) long end) {
        return kazancService.findPaginatedByBayiId(page, perPage, bayiId, start, end);
    }

    @PostMapping
    @Authorize(type = "ADMIN")
    public Kazanc addKazanc(@RequestBody Kazanc kazanc) {
        return kazancService.addEntity(kazanc);
    }
}
