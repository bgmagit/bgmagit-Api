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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
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
    void test() {
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
    void tes2t() {
        
        List<RecordResponse> allPlayers = recordService.findAllRecord();
        
        for (RecordResponse allPlayer : allPlayers) {
            System.out.println(allPlayer);
        }
    }
    
    @DisplayName("")
    @Test
    void test3() {
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
    void test5() {
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
    
    @DisplayName("")
    @Test
    void test6() throws IOException {
        File excelFile = new File("src/test/java/agit/bgmagit/5월데이터이관.xlsx");
        List<RecordRequestList> recordRequestLists = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(excelFile);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;
                
                // 날짜
                String dateStr = row.getCell(0).getStringCellValue().trim();
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd HH:mm")
                        .optionalStart().appendPattern(":ss").optionalEnd()
                        .toFormatter();
                LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
                
                // 국 길이
                String windStr = row.getCell(1).getStringCellValue();
                Wind wind = Wind.valueOf(convertWind(windStr)); // convertWind("남장") -> "SOUTH"
                
                List<RecordRequest> records = new ArrayList<>();
                
                // 1~4위
                for (int i = 2; i <= 5; i++) {
                    String raw = row.getCell(i).getStringCellValue(); // ex: [동]홍길동: 32000
                    String[] parts = raw.split(":");
                
                    String name = parts[0].replaceAll("\\[.*?\\]", "").trim();     // "[동]레이노즈" → "레이노즈"
                    
                    String seat = getSeat(i); // 2=동, 3=남, 4=서, 5=북
                    Integer score = Integer.parseInt(parts[1].trim());
                    
                    records.add(new RecordRequest(name, score, seat,dateTime));
                }
                
                RecordRequestList requestList = new RecordRequestList(wind);
                requestList.setRecordRequests(records);
                recordRequestLists.add(requestList);
                recordService.saveRecord(requestList);
            }
        }
    }
    
    private String getSeat(int index) {
        return switch (index) {
            case 2 -> "동";
            case 3 -> "남";
            case 4 -> "서";
            case 5 -> "북";
            default -> throw new IllegalArgumentException("잘못된 순위 index: " + index);
        };
    }
    
    private String convertWind(String windKor) {
        return switch (windKor.trim()) {
            case "동장" -> "EAST";
            case "남장" -> "SOUTH";
            case "서장" -> "WEST";
            case "북장" -> "NORTH";
            default -> throw new IllegalArgumentException("국 길이 매칭 실패: " + windKor);
        };
    }
}