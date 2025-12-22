package com.example.LunchGo.member.repository;

import com.example.LunchGo.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 이미 존재하는 이메일인지 확인
     */
    boolean existsByEmail(String email);

    /**
     * name과 phone으로 사용자 email 찾기
     * */
    Optional<User> findByNameAndPhone(String name, String phone);
}
