package com.example.LunchGo.member.repository;

import com.example.LunchGo.member.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    /**
     * 이미 존재하는 아이디인지 확인
     * */
    boolean existsByLoginId(String loginId);

    /**
     * name, businessNum, phone으로 아이디 찾기
     * */
    Optional<Owner> findByNameAndBusinessNumAndPhone(String name, String businessNum, String phone);
}
