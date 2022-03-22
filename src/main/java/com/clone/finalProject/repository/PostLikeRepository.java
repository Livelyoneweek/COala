package com.clone.finalProject.repository;


import com.clone.finalProject.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostLikeRepository extends JpaRepository<PostLike,Long> {

    Optional<PostLike> findByUser_UidAndPost_Pid(Long uid, Long pid);
    List<PostLike> findAllByUser_Uid(Long uid);
    List<PostLike> findAllByPost_Pid(Long pid);

    void deleteAllByPost_pid(Long pid);


}
