package agit.bgmagit.controller.request;

import agit.bgmagit.base.entity.Wind;
import lombok.Data;

import java.util.List;

@Data
public class RecordModifyRequest {
    private List<RecordRequest> recordRequests;
    private Wind matchsWind;
    private Long matchId;
}
