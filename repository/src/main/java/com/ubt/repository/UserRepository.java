package com.ubt.repository;

import com.ubt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String u);
    User findById(int id);
    User findByEmail(String email);
}
