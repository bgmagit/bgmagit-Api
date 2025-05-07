package agit.bgmagit.service.impl;

import agit.bgmagit.base.entity.AgitSetting;
import agit.bgmagit.controller.response.AgitSettingResponse;
import agit.bgmagit.repository.AgitSettingRepository;
import agit.bgmagit.service.AgitSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AgitSettingServiceImpl implements AgitSettingService {
    
    private final AgitSettingRepository agitSettingRepository;
    
    @Override
    public AgitSettingResponse findAgitSetting(Long id) {
        Optional<AgitSetting> agitSetting = agitSettingRepository.findById(id);
        return agitSetting.map(AgitSetting::toResponse).orElse(new AgitSettingResponse());
    }
}
