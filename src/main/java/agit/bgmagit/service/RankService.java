package agit.bgmagit.service;

import agit.bgmagit.controller.response.RankResponse;

import java.util.List;

public interface RankService {
    
    List<RankResponse> findRanks();
}
