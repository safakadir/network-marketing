package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.model.entity.AppSetting;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.service.AppSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setting")
public class SettingsController {

    @Autowired
    private AppSettingService appSettingService;

    @GetMapping
    public List<AppSetting> getAppSettings() {
        return appSettingService.readAll();
    }

    @GetMapping("/{name}")
    public AppSetting getAppSetting(@PathVariable("name") String name) {
        return appSettingService.readSetting(name);
    }

    @GetMapping("/prefix/{prefix}")
    public List<AppSetting> getAppSettingsByPrefix(@PathVariable("prefix") String prefix) {
        return appSettingService.readSettingsByPrefix(prefix);
    }

    @PutMapping
    @Authorize(type = "ADMIN")
    public AppSetting putAppSetting(@RequestBody AppSetting setting) {
        return appSettingService.putSetting(setting.getName(), setting.getValue());
    }

    @PutMapping("/bulk")
    @Authorize(type = "ADMIN")
    public List<AppSetting> putAppSettings(@RequestBody List<AppSetting> settings) {
        return appSettingService.putSettings(settings);
    }
}
