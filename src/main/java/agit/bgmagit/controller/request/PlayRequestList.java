package agit.bgmagit.controller.request;

import agit.bgmagit.base.entity.Wind;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlayRequestList {
    
    private List<PlayerRequest> playerRequests;
    private Wind matchsWind;
    
    public PlayRequestList(List<PlayerRequest> playerRequests, Wind matchsWind) {
        this.playerRequests = playerRequests;
        this.matchsWind = matchsWind;
    }
    
    public PlayRequestList(Wind matchsWind) {
        this.matchsWind = matchsWind;
    }
}
