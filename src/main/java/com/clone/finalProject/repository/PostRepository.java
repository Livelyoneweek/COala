package com.clone.finalProject.repository;

import com.clone.finalProject.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPid(Long pid);
    List<Post> findAllByOrderByCreatedAtDesc();
}
