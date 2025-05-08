package agit.bgmagit.base.entity;

import agit.bgmagit.base.BaseDate;
import agit.bgmagit.controller.request.PlayerRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PLAYER")
@Getter
@NoArgsConstructor
public class Player extends BaseDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYER_ID")
    private Long playerId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCHS_ID")
    private Matchs matchs;
    @Column(name = "PLAYER_NAME")
    private String playerName;
    @Column(name = "PLAYER_RANK")
    private Integer playerRank;
    @Column(name = "PLAYER_SCORE")
    private Integer playerScore;
    @Column(name = "PLAYER_SEAT")
    private String playerSeat;
    @Column(name = "PLAYER_POINT")
    private Double playerPoint;
    
    public Player(PlayerRequest playerRequests, AgitSetting agitSettings, String name) {
        this.playerName =  playerRequests.getPlayerName();
        this.playerRank =  playerRequests.getPlayerRank();
        this.playerScore =  playerRequests.getPlayerScore();
        this.playerSeat = playerRequests.getPlayerSeat();
        int seatMultiplier = getSeatMultiplier(name);
        if (this.playerRank != null) {
            this.playerPoint = calculatePlayerPoint(agitSettings, seatMultiplier);
        }
      
    }
    private int getSeatMultiplier(String name) {
        return switch (name) {
            case "EAST" -> 1;
            case "SOUTH" -> 2;
            case "WEST" -> 3;
            case "NORTH" -> 4;
            default -> 0;
        };
    }
    
    private double calculatePlayerPoint(AgitSetting settings, int seatMultiplier) {
        int base = this.playerScore - settings.getAgitSettingTurning();
        int uma = switch (this.playerRank) {
            case 1 -> settings.getAgitSettingFirstUma();
            case 2 -> settings.getAgitSettingSecondUma();
            case 3 -> settings.getAgitSettingThirdUma();
            case 4 -> settings.getAgitSettingFourthUma();
            default -> 0;
        };
        return (double) base / 1000 + uma * seatMultiplier;
    }
    
    public String toFormattedString() {
        return String.format("[%s]%s: %d", playerSeat, playerName, playerScore);
    }
    
    /**
     * 연관 관계 편의메서드
     * @param matchs
     */
    public void setMatchs(Matchs matchs) {
        this.matchs = matchs;
        if (matchs != null && !matchs.getPlayer().contains(this)) {
            matchs.getPlayer().add(this);
        }
    }
    
}
