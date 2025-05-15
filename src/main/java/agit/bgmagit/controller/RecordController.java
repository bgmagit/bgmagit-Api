package agit.bgmagit.controller;


import agit.bgmagit.controller.request.RecordModifyRequestList;
import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.RecordModifyResponseList;
import agit.bgmagit.controller.response.RecordResponse;
import agit.bgmagit.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bgm-agit")
public class RecordController {
    
    private final RecordService recordService;
    
    @GetMapping(value = "/record")
    public List<RecordResponse> getRecord() {
        return recordService.findAllRecord();
    }
    
    @GetMapping(value = "/record/{matchId}")
    public RecordModifyResponseList getOneRecord(@PathVariable Long matchId) {
        return recordService.findOneRecord(matchId);
    }
    
    @PostMapping(value = "/record")
    public ApiResponse addRecord(@Validated @RequestBody RecordRequestList recordRequestList) {
        return recordService.saveRecord(recordRequestList);
    }
    
    @PutMapping(value = "/record")
    public ApiResponse updatePlayer(@RequestBody RecordModifyRequestList recordModifyRequestList) {
        return recordService.modifyRecord(recordModifyRequestList);
    }
    @DeleteMapping(value = "/record/{matchId}")
    public ApiResponse deleteRecord(@PathVariable Long matchId) {
        return recordService.removeRecord(matchId);
    }
}
