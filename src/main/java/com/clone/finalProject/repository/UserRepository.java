package com.clone.finalProject.repository;

import com.clone.finalProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일(로그인 id) 존재 여부
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    boolean existsByNickname(String nickname);
    User findByNickname(String nickname);
}
