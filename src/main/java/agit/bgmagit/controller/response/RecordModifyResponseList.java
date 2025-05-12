package agit.bgmagit.controller.response;

import agit.bgmagit.base.entity.Matchs;
import agit.bgmagit.base.entity.Wind;
import agit.bgmagit.controller.request.RecordRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecordModifyResponseList {

    private Long matchsId;
    private Wind matchsWind;
    private List<RecordModifyResponse> recordModifyResponses;
    
    public RecordModifyResponseList(Long matchsId, Wind matchsWind, List<RecordModifyResponse> recordModifyResponses) {
        this.matchsId = matchsId;
        this.matchsWind = matchsWind;
        this.recordModifyResponses = recordModifyResponses;
    }
}
