package com.ubt.repository;

import com.ubt.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Integer> {

    Media findById(int id);
}
