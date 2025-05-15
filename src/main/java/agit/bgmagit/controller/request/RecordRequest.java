package agit.bgmagit.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RecordRequest {
    
    
    private String recordName;
    
    private Integer recordScore;
    
    private String recordSeat;
    private Integer recordRank;
    private LocalDateTime recordDateTime;
    public RecordRequest(String recordName, Integer recordScore, String recordSeat) {
        this.recordName = recordName;
        this.recordScore = recordScore;
        this.recordSeat = recordSeat;
    }
    
    public RecordRequest(String recordName, Integer recordScore, String recordSeat, LocalDateTime recordDateTime) {
        this.recordName = recordName;
        this.recordScore = recordScore;
        this.recordSeat = recordSeat;
        this.recordDateTime = recordDateTime;
    }
}
