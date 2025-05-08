package agit.bgmagit.service;

import agit.bgmagit.controller.request.PlayRequestList;
import agit.bgmagit.controller.request.PlayerRequest;
import agit.bgmagit.controller.response.ApiResponse;

import java.util.List;

public interface PlayerService {
    
    ApiResponse savePlayer(PlayRequestList playRequestList);
}
