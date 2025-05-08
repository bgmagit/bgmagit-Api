package agit.bgmagit.controller.request;

import agit.bgmagit.base.entity.Wind;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerRequest {
    
    private String playerName;
    private Integer playerScore;
    private String playerSeat;
    private Integer playerRank;
    
    public PlayerRequest(String playerName, Integer playerScore, String playerSeat) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.playerSeat = playerSeat;
    }
}
