package com.ubt.app.controllers;

import com.ubt.model.Media;
import com.ubt.service.MediaService;
import jdk.internal.org.objectweb.asm.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/upload")
    public String handleFileUpload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        Media media = new Media(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        mediaService.save(media);
        return "File uploaded successfully - image name: " + file.getOriginalFilename();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "getAllImages")
    public List<Media> getAllImages() {
        return mediaService.getAll();
    }

    @GetMapping("/media/{media_id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int media_id){
        Media media = mediaService.getById(media_id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(media.getMimeType()));
        header.setContentLength(media.getPic().length);
        header.set("Content-Disposition", "attachment; filename=" + media.getName());
        return new ResponseEntity<>(media.getPic(), header, HttpStatus.OK);
    }
}
