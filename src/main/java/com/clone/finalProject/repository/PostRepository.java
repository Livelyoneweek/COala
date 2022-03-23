package com.clone.finalProject.repository;

import com.clone.finalProject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPid(Long pid);
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByStatusContainingOrderByCreatedAtDesc(String check);
    List<Post> findAllByStatusContainsOrderByCreatedAtDesc(String noCheck);
    List<Post> findByPostTitleContaining(String keyword);
    List<Post> findByCategoryContaining(String keyword);


    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByStatusContaining(Pageable pageable,String check);
    Page<Post> findAllByStatusContains(Pageable pageable,String noCheck);
    Page<Post> findAllByPostTitleContaining(Pageable pageable,String keyword);
    Page<Post> findAllByCategoryContaining(Pageable pageable,String keyword);

}
