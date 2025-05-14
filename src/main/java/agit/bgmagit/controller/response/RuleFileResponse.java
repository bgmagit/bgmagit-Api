package agit.bgmagit.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RuleFileResponse {
    
    private Long ruleFileId;
    private String ruleFileOriginalFileName;
    private String ruleFileUrl;
    
    public RuleFileResponse(Long ruleFileId, String ruleFileOriginalFileName, String ruleFileUrl) {
        this.ruleFileId = ruleFileId;
        this.ruleFileOriginalFileName = ruleFileOriginalFileName;
        this.ruleFileUrl = ruleFileUrl;
    }
}
