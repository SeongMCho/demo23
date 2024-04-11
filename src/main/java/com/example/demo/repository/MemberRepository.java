package com.example.demo.repository;

import com.example.demo.entity.Membership;
import com.example.demo.repository.search.MemberSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends
        JpaRepository<Membership, Long>, MemberSearch {



}
