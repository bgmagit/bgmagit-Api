package agit.bgmagit.controller.request;

import agit.bgmagit.base.entity.Wind;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecordRequestList {
    @Valid
    private List<RecordRequest> recordRequests;
    private Wind matchsWind;
    
    public RecordRequestList(List<RecordRequest> playerRequests, Wind matchsWind) {
        this.recordRequests = playerRequests;
        this.matchsWind = matchsWind;
    }
    
    public RecordRequestList(Wind matchsWind) {
        this.matchsWind = matchsWind;
    }
}
