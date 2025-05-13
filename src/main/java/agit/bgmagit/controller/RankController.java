package agit.bgmagit.controller;

import agit.bgmagit.controller.response.RankRecordResponse;
import agit.bgmagit.controller.response.RankResponse;
import agit.bgmagit.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bgm-agit")
public class RankController {
    
    
    private final RankService rankService;
    
    @GetMapping("/rank")
    public List<RankResponse> getRanks() {
        return rankService.findRanks();
    }
    
    @GetMapping("/rank/{name}")
    public List<RankRecordResponse> getRankRecords(@PathVariable String name) {
        return rankService.findRankRecords(name);
    }
}

