package agit.bgmagit.service;

import agit.bgmagit.controller.response.AgitSettingResponse;

public interface AgitSettingService {
    
    AgitSettingResponse findAgitSetting(Long id);
}
