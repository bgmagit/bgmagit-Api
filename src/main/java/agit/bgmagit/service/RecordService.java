package agit.bgmagit.service;

import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.RecordResponse;

import java.util.List;

public interface RecordService {
    
    List<RecordResponse> findAllPlayers();
    
    ApiResponse savePlayer(RecordRequestList playRequestList);
}
