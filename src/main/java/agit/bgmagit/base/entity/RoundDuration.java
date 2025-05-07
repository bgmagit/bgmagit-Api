package agit.bgmagit.base.entity;


import agit.bgmagit.base.BaseDate;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RoundDuration extends BaseDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUND_DURATION_ID")
    private Long roundDurationId;
    
    @Column(name = "ROUND_DURATION_WIND")
    private String roundDurationWind;
    
}
