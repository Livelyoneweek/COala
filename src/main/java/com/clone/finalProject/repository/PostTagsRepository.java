package com.clone.finalProject.repository;

import com.clone.finalProject.domain.PostTags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostTagsRepository extends JpaRepository<PostTags, Long> {

    List<PostTags> findAllByTags_TagName(String tagName);
    List<PostTags> findAllByPost_Pid(Long pid);

    void deleteAllByPost_Pid(Long pid);


}