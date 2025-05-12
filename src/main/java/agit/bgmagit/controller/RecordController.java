package agit.bgmagit.controller;


import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.RecordResponse;
import agit.bgmagit.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bgm-agit")
public class RecordController {
    
    private final RecordService recordService;
    
    @GetMapping(value = "/player")
    public List<RecordResponse> getPlayer() {
        return recordService.findAllPlayers();
    }
    
    @PostMapping(value = "/player")
    public ApiResponse addPlayer(@RequestBody RecordRequestList playRequestList) {
        return recordService.savePlayer(playRequestList);
    }
}
