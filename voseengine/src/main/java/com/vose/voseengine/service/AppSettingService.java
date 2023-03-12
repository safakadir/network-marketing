package com.vose.voseengine.service;

import com.google.gson.Gson;
import com.vose.voseengine.model.entity.AppSetting;
import com.vose.voseengine.repository.AppSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AppSettingService {
    @Autowired
    private AppSettingRepository repository;

    private Gson gson = new Gson();

    @Transactional
    public AppSetting putSetting(String name, Object value) {
        AppSetting setting = new AppSetting(name, gson.toJson(value));
        return repository.save(setting);
    }

    @Transactional
    public List<AppSetting> putSettings(List<AppSetting> settings) {
        return (List<AppSetting>) repository.saveAll(settings);
    }

    public List<AppSetting> readAll() {
        return (List<AppSetting>) repository.findAll();
    }

    public List<AppSetting> readSettingsByPrefix(String prefix) {
        return repository.findByNameStartsWith(prefix);
    }

    public AppSetting readSetting(String name) {
        Optional<AppSetting> settingOptional = repository.findById(name);
        if(!settingOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting "+name+" not found");
        return settingOptional.get();
    }
}
