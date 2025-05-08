package agit.bgmagit.service;

import agit.bgmagit.controller.request.PlayRequestList;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.PlayResponse;

import java.util.List;

public interface PlayerService {
    
    List<PlayResponse> findAllPlayers();
    
    ApiResponse savePlayer(PlayRequestList playRequestList);
}
