package com.vose.voseengine.controller;

import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.entity.Urun;
import com.vose.voseengine.model.service.FileInfo;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.service.storage.StorageService;
import com.vose.voseengine.Util;
import com.vose.voseengine.service.UrunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/urun")
public class UrunController {

    @Autowired
    private UrunService urunService;

    @Autowired
    private StorageService storageService;

    @GetMapping
    public PageResult<Urun> findUrunler(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) int perPage,
            @RequestParam(value = "sort", defaultValue = "urunAdi", required = false) String sort,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "kategori", defaultValue = "0", required = false) Long kategoriId,
            @RequestParam(value = "onecikan", defaultValue = "false", required = false) boolean onecikan)
    {
        return urunService.findPaginated(page, perPage, sort, search, kategoriId, onecikan);
    }

    @GetMapping("/{urunId}")
    public Urun getUrun(@PathVariable("urunId") Long urunId) {
        return urunService.findOneById(urunId);
    }

    @GetMapping("/many")
    public List<Urun> getSepetUrunler(@RequestParam("ids") String idsStr) {
        List<Long> ids = Arrays.stream(idsStr.split(",")).map(Long::parseLong).collect(Collectors.toList());
        return urunService.findManyByIds(ids);
    }

    @PostMapping
    @Authorize(type = "ADMIN")
    public Urun addUrun(@RequestBody Urun urun) {
        return urunService.addEntity(urun);
    }

    @PutMapping
    @Authorize(type = "ADMIN")
    public Urun updateUrun(@RequestBody Urun urun) {
        return urunService.updateEntity(urun);
    }

    @DeleteMapping("/{urunId}")
    @Authorize(type = "ADMIN")
    public boolean deleteUrun(@PathVariable("urunId") Long urunId) {
        return urunService.deleteEntity(urunId);
    }

    @PostMapping("/upload")
    @Authorize(type = "ADMIN")
    public ResponseEntity<FileInfo> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        String filename = "";
        try {
            filename = Util.generateFilename(file.getOriginalFilename());
            storageService.save(file, filename);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new FileInfo(
                    file.getOriginalFilename(),
                    filename,
                    null,
                    file.getSize(),
                    message
            ));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileInfo(
                    file.getOriginalFilename(),
                    filename,
                    null,
                    file.getSize(),
                    message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(UrunController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(null, filename, url, 0, null);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
