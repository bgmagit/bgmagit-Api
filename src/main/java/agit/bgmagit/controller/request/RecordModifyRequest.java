package agit.bgmagit.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordModifyRequest {
    private Long recordId;
    private String recordName;
    private Integer recordScore;
    private String recordSeat;
    private Integer recordRank;
    
    public RecordModifyRequest(Long recordId, String recordName, Integer recordScore, String recordSeat) {
        this.recordId = recordId;
        this.recordName = recordName;
        this.recordScore = recordScore;
        this.recordSeat = recordSeat;
    }
}
