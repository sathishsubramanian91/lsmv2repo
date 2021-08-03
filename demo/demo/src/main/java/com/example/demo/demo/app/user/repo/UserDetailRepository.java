package com.example.demo.demo.app.user.repo;

import com.example.demo.demo.app.user.entity.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {
  Optional<UserDetailEntity> findByUsername(String name);

}
