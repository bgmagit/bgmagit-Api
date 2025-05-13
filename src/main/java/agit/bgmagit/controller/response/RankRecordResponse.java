package agit.bgmagit.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RankRecordResponse {
    private Long matchsId;
    private Long recordId;
    private String wind;
    private String first;
    private String second;
    private String third;
    private String fourth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registDate;
    
    public RankRecordResponse(Long matchsId, Long recordId, String wind, String first, String second, String third, String fourth, LocalDateTime registDate) {
        this.matchsId = matchsId;
        this.recordId = recordId;
        this.wind = wind;
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.registDate = registDate;
    }
}
