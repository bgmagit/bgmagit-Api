package agit.bgmagit.base.entity;

import agit.bgmagit.base.BaseDate;
import agit.bgmagit.controller.request.RecordModifyRequest;
import agit.bgmagit.controller.request.RecordRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RECORD")
@Getter
@NoArgsConstructor
public class Record extends BaseDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID")
    private Long recordId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCHS_ID")
    private Matchs matchs;
    @Column(name = "RECORD_NAME")
    private String recordName;
    @Column(name = "RECORD_RANK")
    private Integer recordRank;
    @Column(name = "RECORD_SCORE")
    private Integer recordScore;
    @Column(name = "RECORD_SEAT")
    private String recordSeat;
    @Column(name = "RECORD_POINT")
    private Double recordPoint;
    
    public Record(RecordRequest playerRequests, AgitSetting agitSettings, String name) {
        this.recordName =  playerRequests.getRecordName();
        this.recordRank =  playerRequests.getRecordRank();
        this.recordScore =  playerRequests.getRecordScore();
        this.recordSeat = playerRequests.getRecordSeat();
        this.registDate = LocalDateTime.now();
        int seatMultiplier = getSeatMultiplier(name);
        if (this.recordRank != null) {
            this.recordPoint = calculatePlayerPoint(agitSettings, seatMultiplier);
        }
        
    }
    
    
    public void modifyRecord(RecordModifyRequest recordModifyRequest,AgitSetting agitSettings, String name) {
        this.recordName =  recordModifyRequest.getRecordName();
        this.recordScore =  recordModifyRequest.getRecordScore();
        this.recordSeat = recordModifyRequest.getRecordSeat();
        this.recordRank = recordModifyRequest.getRecordRank();
        int seatMultiplier = getSeatMultiplier(name);
        if (this.recordRank != null) {
            this.recordPoint = calculatePlayerPoint(agitSettings, seatMultiplier);
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
        int base = this.recordScore - settings.getAgitSettingTurning();
        int uma = switch (this.recordRank) {
            case 1 -> settings.getAgitSettingFirstUma();
            case 2 -> settings.getAgitSettingSecondUma();
            case 3 -> settings.getAgitSettingThirdUma();
            case 4 -> settings.getAgitSettingFourthUma();
            default -> 0;
        };
        double rawPoint = (double) base / 1000 + uma * seatMultiplier;
        return Math.round(rawPoint * 10) / 10.0;
    }
    
    public String toFormattedString() {
        return String.format("[%s]%s: %d", recordSeat, recordName, recordScore);
    }
    
    /**
     * 연관 관계 편의메서드
     * @param matchs
     */
    public void setMatchs(Matchs matchs) {
        this.matchs = matchs;
        if (matchs != null && !matchs.getRecords().contains(this)) {
            matchs.getRecords().add(this);
        }
    }
    
}
