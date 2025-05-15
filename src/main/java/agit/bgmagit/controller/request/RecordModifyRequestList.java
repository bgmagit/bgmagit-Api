package agit.bgmagit.controller.request;

import agit.bgmagit.ano.ValidTotalModifyScore;
import agit.bgmagit.base.entity.Wind;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ValidTotalModifyScore
public class RecordModifyRequestList {
    @Valid
    private List<RecordModifyRequest> recordRequests;
    private Wind matchsWind;
    private Long matchId;
    
    public RecordModifyRequestList(List<RecordModifyRequest> recordRequests, Wind matchsWind, Long matchId) {
        this.recordRequests = recordRequests;
        this.matchsWind = matchsWind;
        this.matchId = matchId;
    }
}
