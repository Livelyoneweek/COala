package com.clone.finalProject.repository;

import com.clone.finalProject.domain.PostTags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostTagsRepository extends JpaRepository<PostTags, Long> {

    List<PostTags> findAllByTags_TagName(String tagName);
    List<PostTags> findAllByPost_Pid(Long pid);

    Optional<PostTags> findByPost_PidAndTags_TagId(Long pid, Long tagId);

    void deleteAllByPost_Pid(Long pid);


}