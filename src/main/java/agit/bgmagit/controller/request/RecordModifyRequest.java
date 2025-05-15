package agit.bgmagit.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordModifyRequest {
    private Long recordId;
    @NotBlank(message = "이름을 입력해 주세요")
    private String recordName;
    @NotNull(message = "점수를 입력해 주세요")
    private Integer recordScore;
    @NotBlank(message = "자리를 입력해주세요")
    private String recordSeat;
    private Integer recordRank;
    
    public RecordModifyRequest(Long recordId, String recordName, Integer recordScore, String recordSeat) {
        this.recordId = recordId;
        this.recordName = recordName;
        this.recordScore = recordScore;
        this.recordSeat = recordSeat;
    }
}
