package com.example.demo.repository.search;

import com.example.demo.entity.Membership;
import com.example.demo.entity.QBoard;

import com.example.demo.entity.QMembership;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class MemberSearchImpl extends QuerydslRepositorySupport implements MemberSearch {


    public MemberSearchImpl() {
        super(Membership.class);
    }

    @Override
    public Page<Membership> searchAllMember(String[] types, String keyword, Pageable pageable) {
        QMembership mem = QMembership.membership;
        JPQLQuery<Membership> query = from(mem); //select * from table
        log.info(query);
        //검색 조건 과 키워드가 있다면
        if( ( types != null) && types.length > 0 && keyword != null ){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(mem.name.contains(keyword));//where title= keyword
                        break;
                    case "c":
                        booleanBuilder.or(mem.userId.contains(keyword));//where contetn= keyword
                        break;

                }
            }//for end
            query.where(booleanBuilder);
        }//if end
        query.where(mem.mno.gt(0L));
        log.info( " 이건 엔티티매니져"+this.getEntityManager());

        log.info(query);
        log.info(this.getQuerydsl().applyPagination(pageable, query));
        List<Membership> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }


}
