package agit.bgmagit.base.entity;


import agit.bgmagit.base.BaseDate;
import agit.bgmagit.controller.request.RecordModifyRequestList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "MATCHS")
@Entity
@Getter
@NoArgsConstructor
public class Matchs extends BaseDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATCHS_ID")
    private Long matchsId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "MATCHS_WIND")
    private Wind matchsWind;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchs",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Record> records = new ArrayList<>();
    
    public Matchs(Wind matchsWind) {
        this.matchsWind = matchsWind;
    }
    
    public void modifyMatchWind(RecordModifyRequestList recordModifyRequest) {
        this.matchsWind = recordModifyRequest.getMatchsWind();
    }
    
}
