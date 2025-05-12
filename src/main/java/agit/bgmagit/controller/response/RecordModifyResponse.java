package agit.bgmagit.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordModifyResponse {
    
    private Long recordId;
    private Integer recordScore;
    private String recordName;
    private String recordSeat;
    
    public RecordModifyResponse(Long recordId, Integer recordScore, String recordName,String recordSeat) {
        this.recordId = recordId;
        this.recordScore = recordScore;
        this.recordName = recordName;
        this.recordSeat = recordSeat;
    }
}
