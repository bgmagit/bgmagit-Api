package agit.bgmagit.base.entity;


import agit.bgmagit.base.BaseDate;
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
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchs")
    private List<Player> player = new ArrayList<>();
    
    public Matchs(Wind matchsWind) {
        this.matchsWind = matchsWind;
    }
}
