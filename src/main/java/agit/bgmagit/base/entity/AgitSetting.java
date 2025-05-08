package agit.bgmagit.base.entity;

import agit.bgmagit.base.BaseDate;
import agit.bgmagit.controller.response.AgitSettingResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Table(name = "AGIT_SETTING")
@Entity
@Getter
@NoArgsConstructor
public class AgitSetting extends BaseDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AGIT_SETTING_ID")
    private Long agitSettingId;
    
    @Column(name = "AGIT_SETTING_TURNING")
    private Integer agitSettingTurning;
    
    @Column(name = "AGIT_SETTING_FIRST_UMA")
    private Integer agitSettingFirstUma;
    
    @Column(name = "AGIT_SETTING_SECOND_UMA")
    private Integer agitSettingSecondUma;
    
    @Column(name = "AGIT_SETTING_THIRD_UMA")
    private Integer agitSettingThirdUma;
    
    @Column(name = "AGIT_SETTING_FOURTH_UMA")
    private Integer agitSettingFourthUma;
    
    public AgitSettingResponse toResponse() {
        return new ModelMapper().map(this, AgitSettingResponse.class);
    }
}
