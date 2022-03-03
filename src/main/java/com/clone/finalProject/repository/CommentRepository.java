package com.clone.finalProject.repository;

import com.clone.finalProject.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentId(Long commentId);
    List<Comment> findAllByOrderByCreatedAtDesc();
    List<Comment> findAllByAnswer_answerIdOrderByCreatedAtAsc(Long answerId);
    void deleteAllByAnswer_answerId(Long answerId);
    void deleteAllByPid(Long pid);


}
