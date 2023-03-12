package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.model.entity.AppSetting;
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
@RequestMapping("/")
public class AppController {

    @Autowired
    private AppSettingService appSettingService;

    @Autowired
    private JobManager jobManager;

    @Autowired
    private Environment environment;

    @Value("${app.version}")
    private String version;
    @Value("${app.envName}")
    private String envName;

    @GetMapping("/engineinfo")
    public String getHealthInfo(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if(ip == null) ip = request.getRemoteAddr();
        return String.format("Engine Works!<br/>IP:%s<br/>Profile:%s(%s)<br/>Version:%s<br/>",
                ip,envName,
                Arrays.stream(environment.getActiveProfiles()).collect(Collectors.joining(",")),
                version);
    }

    @PutMapping("/job/process")
    @Authorize(type = "ADMIN")
    public ResponseEntity<Job> processEvent() {
        Job processedJob =  jobManager.run();
        return new ResponseEntity<>(
                processedJob,
                processedJob != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @PostMapping("/job")
    @Authorize(type = "ADMIN")
    public Job produceJob(@RequestBody Job job) {
        return jobManager.produce(job);
    }

}
