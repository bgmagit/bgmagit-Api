package agit.bgmagit.service.impl;

import agit.bgmagit.MapperAndServiceTestSupport;
import agit.bgmagit.base.entity.Wind;
import agit.bgmagit.controller.request.RecordModifyRequest;
import agit.bgmagit.controller.request.RecordModifyRequestList;
import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.request.RecordRequest;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.RecordModifyResponse;
import agit.bgmagit.controller.response.RecordModifyResponseList;
import agit.bgmagit.controller.response.RecordResponse;
import agit.bgmagit.repository.MatchsRepository;
import agit.bgmagit.service.RecordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;


class RecordServiceImplTest extends MapperAndServiceTestSupport {
    
    @Autowired
    private RecordService recordService;
    
    @Autowired
    private MatchsRepository matchsRepository;
    
    @DisplayName("기록 저장 테스트")
    @Test
    void test(){
        
        
//        List<PlayerRequest> playerRequests = Arrays.asList(
//                 new PlayerRequest("꽐룰", 48400, "북")
//                , new PlayerRequest("큐브", 51800, "서")
//                ,new PlayerRequest("진하", 11100, "동")
//                , new PlayerRequest("김민건", 8700, "남")
//        );
        
        List<RecordRequest> recordRequests = Arrays.asList(
                new RecordRequest("김민건", 56100, "북")
                , new RecordRequest("꽐룰", 38500, "서")
                ,new RecordRequest("진하", 32500, "동")
                , new RecordRequest("큐브", -7100, "남")
        );
        RecordRequestList requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests);
        recordService.saveRecord(requestList);
    }
    
    @DisplayName("기록 전체 조회 테스트")
    @Test
    void tes2t(){
        
        List<RecordResponse> allPlayers = recordService.findAllPlayers();
        
        for (RecordResponse allPlayer : allPlayers) {
            System.out.println(allPlayer);
        }
    }
    
    @DisplayName("")
    @Test
    void test3(){
        RecordModifyResponseList oneRecord = recordService.findOneRecord(3L);
        System.out.println(oneRecord);
    }
    
    @DisplayName("")
    @Test
    void test4(){
        RecordModifyResponseList oneRecord = recordService.findOneRecord(3L);
        
        List<RecordModifyResponse> recordModifyResponses = oneRecord.getRecordModifyResponses();
        for (RecordModifyResponse recordModifyRespons : recordModifyResponses) {
            System.out.println(recordModifyRespons);
            if ("김민건".equals(recordModifyRespons.getRecordName())) {
                recordModifyRespons.setRecordScore(56100);
            } else if ("진하".equals(recordModifyRespons.getRecordName())) {
                recordModifyRespons.setRecordScore(38500);
            } else if ("꽐룰".equals(recordModifyRespons.getRecordName())) {
                recordModifyRespons.setRecordScore(32500);
            } else if ("큐브".equals(recordModifyRespons.getRecordName())) {
                recordModifyRespons.setRecordScore(-7100);
            }
        }
        
        List<RecordModifyRequest> requestList = recordModifyResponses.stream()
                .map(r -> new RecordModifyRequest(
                        r.getRecordId(),
                        r.getRecordName(),
                        r.getRecordScore(),
                        r.getRecordSeat()
                ))
                .collect(Collectors.toList());
        
        RecordModifyRequestList recordModifyRequestList = new RecordModifyRequestList(
                requestList,
                oneRecord.getMatchsWind(),
                oneRecord.getMatchsId()
        );
        ApiResponse apiResponse = recordService.modifyRecord(recordModifyRequestList);
        System.out.println(apiResponse);
    }
    @DisplayName("")
    @Test
    void test5(){
        ApiResponse apiResponse = recordService.removeRecord(3L);
        System.out.println(apiResponse);
    }
    
}