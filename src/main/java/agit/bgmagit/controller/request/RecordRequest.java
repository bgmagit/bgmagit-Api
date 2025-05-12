package agit.bgmagit.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecordRequest {
    
    private String recordName;
    private Integer recordScore;
    private String recordSeat;
    private Integer recordRank;
    
    public RecordRequest(String recordName, Integer recordScore, String recordSeat) {
        this.recordName = recordName;
        this.recordScore = recordScore;
        this.recordSeat = recordSeat;
    }
}
