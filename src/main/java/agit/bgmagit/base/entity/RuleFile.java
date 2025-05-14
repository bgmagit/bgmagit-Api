package agit.bgmagit.base.entity;

import agit.bgmagit.base.BaseDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "RULE_FILE")
@Entity
@Getter
@NoArgsConstructor
public class RuleFile extends BaseDate {
    
    @Id
    @Column(name = "RULE_FILE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleFileId;
    @Column(name = "RULE_FILE_ORIGINAL_FILE_NAME")
    private String ruleFileOriginalFileName;
    @Column(name = "RULE_FILE_URL")
    private String ruleFileUrl;
    
    
    public RuleFile(String ruleFileOriginalFileName, String ruleFileUrl) {
        this.ruleFileOriginalFileName = ruleFileOriginalFileName;
        this.ruleFileUrl = ruleFileUrl;
    }
}
