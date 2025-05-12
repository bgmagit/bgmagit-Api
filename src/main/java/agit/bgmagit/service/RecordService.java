package agit.bgmagit.service;

import agit.bgmagit.controller.request.RecordModifyRequestList;
import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.RecordModifyResponseList;
import agit.bgmagit.controller.response.RecordResponse;

import java.util.List;

public interface RecordService {
    
    List<RecordResponse> findAllPlayers();
    
    RecordModifyResponseList findOneRecord(Long matchId);
    
    ApiResponse saveRecord(RecordRequestList recordRequestList);
    
    ApiResponse modifyRecord(RecordModifyRequestList recordModifyRequest);
    
    ApiResponse removeRecord(Long matchId);
}
