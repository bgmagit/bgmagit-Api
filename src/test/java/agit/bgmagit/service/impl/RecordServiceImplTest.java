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
        RecordRequestList requestList;
        
        List<RecordRequest> recordRequests1 = Arrays.asList(
                new RecordRequest("서정", 67300, "남"),
                new RecordRequest("라스", 34800, "동"),
                new RecordRequest("김민건", 27200, "서"),
                new RecordRequest("광진", -9300, "북")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests1);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests2 = Arrays.asList(
                new RecordRequest("배성환", 47600, "서"),
                new RecordRequest("광진", 31300, "동"),
                new RecordRequest("라스", 26100, "남"),
                new RecordRequest("밤", 15000, "북")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests2);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests3 = Arrays.asList(
                new RecordRequest("라스", 49900, "동"),
                new RecordRequest("배성환", 37300, "서"),
                new RecordRequest("밤", 35800, "북"),
                new RecordRequest("광진", -3000, "남")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests3);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests4 = Arrays.asList(
                new RecordRequest("남군", 61100, "북"),
                new RecordRequest("리일쓰", 31700, "동"),
                new RecordRequest("민준", 22600, "서"),
                new RecordRequest("이찬용", 4600, "남")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests4);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests5 = Arrays.asList(
                new RecordRequest("민준", 65500, "동"),
                new RecordRequest("이찬용", 47900, "서"),
                new RecordRequest("리일쓰", 20200, "북"),
                new RecordRequest("남군", -13600, "남")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests5);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests6 = Arrays.asList(
                new RecordRequest("민준", 56700, "남"),
                new RecordRequest("남군", 50200, "동"),
                new RecordRequest("이찬용", 16500, "북"),
                new RecordRequest("리일쓰", -3400, "서")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests6);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests7 = Arrays.asList(
                new RecordRequest("민준", 58200, "북"),
                new RecordRequest("남군", 39800, "서"),
                new RecordRequest("리일쓰", 31200, "남"),
                new RecordRequest("이찬용", -9200, "동")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests7);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests8 = Arrays.asList(
                new RecordRequest("민준", 54500, "동"),
                new RecordRequest("리일쓰", 36700, "북"),
                new RecordRequest("남군", 23600, "남"),
                new RecordRequest("이찬용", 5200, "서")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests8);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests9 = Arrays.asList(
                new RecordRequest("이찬용", 62600, "남"),
                new RecordRequest("리일쓰", 31000, "북"),
                new RecordRequest("민준", 16000, "서"),
                new RecordRequest("남군", 10400, "동")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests9);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests10 = Arrays.asList(
                new RecordRequest("민준", 44300, "남"),
                new RecordRequest("이찬용", 33000, "동"),
                new RecordRequest("남군", 30000, "북"),
                new RecordRequest("리일쓰", 12700, "서")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests10);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests11 = Arrays.asList(
                new RecordRequest("김필완", 45600, "북"),
                new RecordRequest("이찬용", 35000, "동"),
                new RecordRequest("남군", 31600, "남"),
                new RecordRequest("리일쓰", 7800, "서")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests11);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests12 = Arrays.asList(
                new RecordRequest("남군", 40100, "서"),
                new RecordRequest("리일쓰", 35800, "북"),
                new RecordRequest("김필완", 24300, "남"),
                new RecordRequest("이찬용", 19800, "동")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests12);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests13 = Arrays.asList(
                new RecordRequest("리일쓰", 46900, "남"),
                new RecordRequest("라스", 31700, "서"),
                new RecordRequest("이찬용", 21300, "동"),
                new RecordRequest("김필완", 20100, "북")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests13);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests14 = Arrays.asList(
                new RecordRequest("라스", 70400, "남"),
                new RecordRequest("광진", 29600, "동"),
                new RecordRequest("수환", 20900, "서"),
                new RecordRequest("리일쓰", -900, "북")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests14);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests15 = Arrays.asList(
                new RecordRequest("수환", 81700, "서"),
                new RecordRequest("이찬용", 29600, "동"),
                new RecordRequest("리일쓰", 5400, "남"),
                new RecordRequest("김필완", 3300, "북")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests15);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests16 = Arrays.asList(
                new RecordRequest("광진", 36300, "남"),
                new RecordRequest("밤", 32000, "동"),
                new RecordRequest("라스", 30700, "서"),
                new RecordRequest("은우", 21000, "북")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests16);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests17 = Arrays.asList(
                new RecordRequest("리일쓰", 42500, "서"),
                new RecordRequest("수환", 41700, "동"),
                new RecordRequest("라스", 36400, "북"),
                new RecordRequest("이찬용", -600, "남")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests17);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests18 = Arrays.asList(
                new RecordRequest("김필완", 49200, "남"),
                new RecordRequest("리일쓰", 35200, "서"),
                new RecordRequest("수환", 25500, "동"),
                new RecordRequest("이찬용", 10100, "북")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests18);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests19 = Arrays.asList(
                new RecordRequest("민후", 42700, "남"),
                new RecordRequest("레이노즈", 29300, "북"),
                new RecordRequest("민트", 24400, "서"),
                new RecordRequest("진하", 23600, "동")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests19);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests20 = Arrays.asList(
                new RecordRequest("레이노즈", 47100, "북"),
                new RecordRequest("이지금", 38400, "남"),
                new RecordRequest("진하", 30300, "서"),
                new RecordRequest("민트", 4200, "동")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests20);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests21 = Arrays.asList(
                new RecordRequest("레이노즈", 59000, "북"),
                new RecordRequest("민트", 24300, "동"),
                new RecordRequest("진하", 24100, "남"),
                new RecordRequest("이지금", 12600, "서")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests21);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests22 = Arrays.asList(
                new RecordRequest("진하", 53600, "남"),
                new RecordRequest("민트", 23100, "동"),
                new RecordRequest("이지금", 21700, "북"),
                new RecordRequest("레이노즈", 21600, "서")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests22);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests23 = Arrays.asList(
                new RecordRequest("진하", 60600, "서"),
                new RecordRequest("민트", 29000, "북"),
                new RecordRequest("레이노즈", 25700, "남"),
                new RecordRequest("이지금", 4700, "동")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests23);
        recordService.saveRecord(requestList);
        
        List<RecordRequest> recordRequests24 = Arrays.asList(
                new RecordRequest("진하", 44400, "북"),
                new RecordRequest("레이노즈", 43800, "서"),
                new RecordRequest("이지금", 22100, "남"),
                new RecordRequest("민트", 9700, "동")
        );
        requestList = new RecordRequestList(Wind.SOUTH);
        requestList.setRecordRequests(recordRequests24);
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
    
    private void saveRecords(List<RecordRequest>... recordGroups) {
        for (List<RecordRequest> records : recordGroups) {
            RecordRequestList requestList = new RecordRequestList(Wind.SOUTH);
            requestList.setRecordRequests(records);
            recordService.saveRecord(requestList);
        }
    }
    
}