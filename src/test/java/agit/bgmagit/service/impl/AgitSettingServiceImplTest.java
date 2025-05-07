package agit.bgmagit.service.impl;

import agit.bgmagit.MapperAndServiceTestSupport;
import agit.bgmagit.base.entity.AgitSetting;
import agit.bgmagit.controller.response.AgitSettingResponse;
import agit.bgmagit.service.AgitSettingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@Transactional
class AgitSettingServiceImplTest extends MapperAndServiceTestSupport {
    
    @Autowired
    private AgitSettingService agitSettingService;
    
    @DisplayName("")
    @Test
    void findAgitSetting(){
        Long id = 1L;
        AgitSettingResponse agitSetting = agitSettingService.findAgitSetting(id);
        assertThat(agitSetting).isNotNull();
    }
}