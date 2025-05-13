package agit.bgmagit.service.impl;

import agit.bgmagit.MapperAndServiceTestSupport;
import agit.bgmagit.controller.response.RankRecordResponse;
import agit.bgmagit.controller.response.RankResponse;
import agit.bgmagit.service.RankService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class RankServiceImplTest extends MapperAndServiceTestSupport {
    

    @Autowired
    private RankService rankService;
   
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    
    @DisplayName("")
    @Test
    void test(){
        
        List<RankResponse> ranks = rankService.findRanks();
        
        System.out.println(ranks);
        
    }
    @DisplayName("")
    @Test
    void test2(){
        
        List<RankRecordResponse> name = rankService.findRankRecords("큐브");
        
        System.out.println(name);
    
    }
}