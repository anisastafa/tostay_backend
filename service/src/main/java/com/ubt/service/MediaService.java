package com.ubt.service;

import com.ubt.model.Apartment;
import com.ubt.model.Media;
import com.ubt.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public Media getById(int id){
        return mediaRepository.findById(id);
    }

    public Media save(Media media){
       return mediaRepository.save(media);
    }

    public List<Media> getAll(){
        return mediaRepository.findAll();
    }

}
