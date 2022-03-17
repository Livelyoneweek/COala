package com.clone.finalProject.repository;


import com.clone.finalProject.domain.AnswerLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerLikeRepository extends JpaRepository<AnswerLike,Long> {

    Optional<AnswerLike> findByUidAndPid(Long uid, Long pid);
    Optional<AnswerLike> findByAnswerId(Long answerId);
    void deleteByAnswerId(Long answerId);

}