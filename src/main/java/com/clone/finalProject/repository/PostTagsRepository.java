package com.clone.finalProject.repository;

import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.PostTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostTagsRepository extends JpaRepository<PostTags, Long> {

    List<PostTags> findAllByTags_TagName(String tagName);
    List<PostTags> findAllByPost_Pid(Long pid);

    Optional<PostTags> findByPost_PidAndTags_TagId(Long pid, Long tagId);


    Page<PostTags> findAllByTags_TagName(Pageable pageable,String tagName);

    void deleteAllByPost_Pid(Long pid);


}