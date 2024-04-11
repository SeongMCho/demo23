package com.example.demo.repository.search;

import com.example.demo.entity.Membership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberSearch {


    Page<Membership> searchAllMember(String[] types, String keyword, Pageable pageable);



}
