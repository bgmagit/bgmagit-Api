package agit.bgmagit.controller;

import agit.bgmagit.ControllerTestSupport;
import agit.bgmagit.base.entity.Wind;
import agit.bgmagit.controller.request.PlayRequestList;
import agit.bgmagit.controller.request.PlayerRequest;
import agit.bgmagit.service.PlayerService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerTest extends ControllerTestSupport {
    
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    
    @Autowired
    private PlayerService playerService;
    
    @DisplayName("전체조회")
    @Test
    void test1() throws Exception {
        
        mockMvc.perform(get("/bgm-agit/player")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
    }
    @DisplayName("기록 저장 컨트롤러 테스트")
    @Test
    void test2() throws Exception {
        List<PlayerRequest> playerRequests = Arrays.asList(
                new PlayerRequest("쵸리", 55300, "동")
                , new PlayerRequest("큐브", 26700, "남")
                ,new PlayerRequest("민준", 4200, "서")
                , new PlayerRequest("남군", 33800, "북")
        );
        PlayRequestList playRequestList = new PlayRequestList(playerRequests, Wind.SOUTH);
        String jsonRequest = objectMapper.writeValueAsString(playRequestList);
        mockMvc.perform(post("/bgm-agit/player")
                        .content(jsonRequest)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        
    }
}