package agit.bgmagit.controller.response;

import agit.bgmagit.controller.request.RecordRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecordModifyResponseList {

    private Long matchsId;
    private String  matchsWind;
    private List<RecordModifyResponse> recordModifyResponses;
    
    public RecordModifyResponseList(Long matchsId, String matchsWind, List<RecordModifyResponse> recordModifyResponses) {
        this.matchsId = matchsId;
        this.matchsWind = matchsWind;
        this.recordModifyResponses = recordModifyResponses;
    }
}
