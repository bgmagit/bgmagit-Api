package agit.bgmagit.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class RankResponse {
    private String recordName;
    private Double recordSumPoint;
    private Integer round;
    private List<Integer> recordIds;
    
    public RankResponse(String recordName, Double recordSumPoint, Integer round, List<Integer> recordIds) {
        this.recordName = recordName;
        this.recordSumPoint = recordSumPoint;
        this.round = round;
        this.recordIds = recordIds;
    }
}
