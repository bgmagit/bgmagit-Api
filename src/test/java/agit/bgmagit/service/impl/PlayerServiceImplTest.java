package agit.bgmagit.service.impl;

import agit.bgmagit.MapperAndServiceTestSupport;
import agit.bgmagit.base.entity.Wind;
import agit.bgmagit.controller.request.PlayRequestList;
import agit.bgmagit.controller.request.PlayerRequest;
import agit.bgmagit.repository.MatchsRepository;
import agit.bgmagit.service.PlayerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


class PlayerServiceImplTest extends MapperAndServiceTestSupport {
    
    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private MatchsRepository matchsRepository;
    
    @DisplayName("")
    @Test
    void test(){
        
        
//        List<PlayerRequest> playerRequests = Arrays.asList(
//                 new PlayerRequest("꽐룰", 48400, "북")
//                , new PlayerRequest("큐브", 51800, "서")
//                ,new PlayerRequest("진하", 11100, "동")
//                , new PlayerRequest("김민건", 8700, "남")
//        );
        
        List<PlayerRequest> playerRequests = Arrays.asList(
                new PlayerRequest("김민건", 56100, "북")
                , new PlayerRequest("꽐룰", 38500, "서")
                ,new PlayerRequest("진하", 32500, "동")
                , new PlayerRequest("큐브", -7100, "남")
        );
        
        PlayRequestList requestList = new PlayRequestList(Wind.SOUTH);
        requestList.setPlayerRequests(playerRequests);
        
        playerService.savePlayer(requestList);
        
    }
}