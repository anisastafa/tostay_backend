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
import java.util.Arrays;
import java.util.List;

@RestController
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/upload")
    public String handleFileUpload(@RequestParam(value = "files") MultipartFile[] files) throws IOException {
        Arrays.stream(files)
                .forEach(file -> {
                    try {
                        mediaService.save(new Media(file.getOriginalFilename(),
                                file.getContentType(), file.getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return "Files uploaded successfully";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "getAllImages")
    public List<Media> getAllImages() {
        return mediaService.getAll();
    }


    @GetMapping("/media/{media_id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int media_id) {
        Media media = mediaService.getById(media_id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(media.getMimeType()));
//        header.setContentLength(media.getPic().length);
        header.set("Content-Disposition", "attachment; filename=" + media.getName());
        return new ResponseEntity<>(header, HttpStatus.OK);
//        return new ResponseEntity<>(media.getPic(), header, HttpStatus.OK);
    }
}
