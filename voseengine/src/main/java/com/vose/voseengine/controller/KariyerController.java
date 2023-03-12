package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.entity.Kariyer;
import com.vose.voseengine.service.KariyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kariyer")
public class KariyerController {
    @Autowired
    private KariyerService kariyerService;

    @GetMapping
    public List<Kariyer> getKariyerler() {
        return kariyerService.findAll();
    }

    @GetMapping("/{kariyerId}")
    public Kariyer getKariyer(@PathVariable("kariyerId") Long kariyerId) {
        return kariyerService.findOneById(kariyerId);
    }

    @PostMapping
    @Authorize(type = "ADMIN")
    public Kariyer addKariyer(@RequestBody Kariyer kariyer, @RequestParam("addAfter") int addAfter) {
        return kariyerService.addKariyer(kariyer, addAfter);
    }

    @PutMapping
    @Authorize(type = "ADMIN")
    public Kariyer updateKariyer(@RequestBody Kariyer kariyer) {
        return kariyerService.updateKariyer(kariyer);
    }

    @DeleteMapping("/{kariyerId}")
    @Authorize(type = "ADMIN")
    public boolean deleteKariyer(@PathVariable("kariyerId") Long kariyerId) {
        return kariyerService.deleteKariyer(kariyerId);
    }

    @PostMapping("/changeorder/{kariyerId}")
    @Authorize(type = "ADMIN")
    public List<Kariyer> changeOrder(@PathVariable("kariyerId") Long kariyerId, @RequestParam("direction") int direction) {
        return kariyerService.changeOrder(kariyerId, direction);
    }
}
