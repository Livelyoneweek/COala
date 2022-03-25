package com.clone.finalProject.repository;


import com.clone.finalProject.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AlarmRepository extends JpaRepository<Alarm,Long> {

    List<Alarm> findAllByUser_UidOrderByCreatedAt(Long uid);
    void deleteAllByPid(Long pid);

}
