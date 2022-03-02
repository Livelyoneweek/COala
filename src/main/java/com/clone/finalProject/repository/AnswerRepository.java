package com.clone.finalProject.repository;

import com.clone.finalProject.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByAnswerId(Long answerId);
    List<Answer> findAllByOrderByCreatedAtDesc();
    void deleteallByPost_pid(Long pid);

}
