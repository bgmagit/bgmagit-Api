package agit.bgmagit.controller;

import agit.bgmagit.ControllerTestSupport;
import agit.bgmagit.base.entity.Wind;
import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.request.RecordRequest;
import agit.bgmagit.service.RecordService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecordControllerTest extends ControllerTestSupport {
    
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    
    @Autowired
    private RecordService playerService;
    
    @DisplayName("전체조회")
    @Test
    void test1() throws Exception {
        
        mockMvc.perform(get("/bgm-agit/record")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
    }
    
    
    @DisplayName("기록 저장 컨트롤러 테스트")
    @Test
    void test2() throws Exception {
        List<RecordRequest> recordRequests = Arrays.asList(
                new RecordRequest("쵸리", 553000, "동")
                , new RecordRequest("큐브", 26700, "남")
                ,new RecordRequest("민준", 4200, "서")
                , new RecordRequest("남군", 33800, "북")
        );
        RecordRequestList recordRequestList = new RecordRequestList(recordRequests, Wind.SOUTH);
        String jsonRequest = objectMapper.writeValueAsString(recordRequestList);
        mockMvc.perform(post("/bgm-agit/record")
                        .content(jsonRequest)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        
    }
}