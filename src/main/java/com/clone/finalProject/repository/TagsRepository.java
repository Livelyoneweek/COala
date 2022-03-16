package com.clone.finalProject.repository;

import com.clone.finalProject.domain.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, Long> {

    Optional<Tags> findByTagName(String tagName);

}

