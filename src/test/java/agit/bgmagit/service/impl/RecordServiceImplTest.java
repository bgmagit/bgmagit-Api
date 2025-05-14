package agit.bgmagit.service.impl;

import agit.bgmagit.MapperAndServiceTestSupport;
import agit.bgmagit.base.entity.Wind;
import agit.bgmagit.controller.request.RecordModifyRequest;
import agit.bgmagit.controller.request.RecordModifyRequestList;
import agit.bgmagit.controller.request.RecordRequest;
import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.RecordModifyResponse;
import agit.bgmagit.controller.response.RecordModifyResponseList;
import agit.bgmagit.controller.response.RecordResponse;
import agit.bgmagit.repository.MatchsRepository;
import agit.bgmagit.service.RecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


class RecordServiceImplTest extends MapperAndServiceTestSupport {
    
    @Autowired
    private RecordService recordService;
    
    @Autowired
    private MatchsRepository matchsRepository;
    
    @DisplayName("기록 저장 테스트")
    @Test
    void test(){
        RecordRequestList requestList;
        
        List<RecordRequest> recordRequests1 = Arrays.asList(
                new RecordRequest("서정", 32000, "남"),
                new RecordRequest("라스", 30000, "동"),
                new RecordRequest("김민건", 30000, "서"),
                new RecordRequest("광진", 29000, "북")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests1);
        recordService.saveRecord(requestList);
        
     
    }
    
    @DisplayName("기록 전체 조회 테스트")
    @Test
    void tes2t(){
        
        List<RecordResponse> allPlayers = recordService.findAllRecord();
        
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
    void test4() throws JsonProcessingException {
        RecordModifyResponseList oneRecord = recordService.findOneRecord(3L);
        
        List<RecordModifyResponse> recordModifyResponses = oneRecord.getRecordModifyResponses();
        for (RecordModifyResponse recordModifyRespons : recordModifyResponses) {
            System.out.println(recordModifyRespons);
            if ("쵸리".equals(recordModifyRespons.getRecordName())) {
                recordModifyRespons.setRecordScore(56100);
            } else if ("이지금".equals(recordModifyRespons.getRecordName())) {
                recordModifyRespons.setRecordScore(38500);
            } else if ("여우".equals(recordModifyRespons.getRecordName())) {
                recordModifyRespons.setRecordScore(32500);
            } else if ("리체".equals(recordModifyRespons.getRecordName())) {
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
    
    private void saveRecords(List<RecordRequest>... recordGroups) {
        for (List<RecordRequest> records : recordGroups) {
            RecordRequestList requestList = new RecordRequestList(Wind.SOUTH);
            requestList.setRecordRequests(records);
            recordService.saveRecord(requestList);
        }
    }
    
}