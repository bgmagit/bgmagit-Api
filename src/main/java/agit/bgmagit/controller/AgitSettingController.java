package agit.bgmagit.controller;


import agit.bgmagit.controller.response.AgitSettingResponse;
import agit.bgmagit.service.AgitSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bgm-agit")
public class AgitSettingController {

    private final AgitSettingService agitSettingService;
    
    @GetMapping("/setting/{settingId}")
    public AgitSettingResponse getAgitSettingResponse(@PathVariable Long settingId) {
        return agitSettingService.findAgitSetting(settingId);
    }
}
