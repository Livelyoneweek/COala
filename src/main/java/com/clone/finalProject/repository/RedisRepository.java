package com.clone.finalProject.repository;

import com.clone.finalProject.dto.RedisDto;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisDto, String> {
}
