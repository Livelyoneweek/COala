package com.clone.finalProject.repository;

import com.clone.finalProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    boolean existsByNickname(String nickname);

    Optional<User> findByUid(Long uid);

    List<User> findTop10ByOrderByPointDesc();

    List<User> findTop10ByOrderByWeekPointDesc();

    List<User> findTop10ByOrderByMonthPointDesc();

    List<User> findAllByOrderByPointDesc();
    List<User> findAllByOrderByWeekPointDesc();
    List<User> findAllByOrderByMonthPointDesc();


}