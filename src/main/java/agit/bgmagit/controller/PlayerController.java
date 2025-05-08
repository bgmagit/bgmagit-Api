package agit.bgmagit.controller;


import agit.bgmagit.controller.request.PlayRequestList;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.PlayResponse;
import agit.bgmagit.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bgm-agit")
public class PlayerController {
    
    private final PlayerService playerService;
    
    @GetMapping(value = "/player")
    public List<PlayResponse> getPlayer() {
        return playerService.findAllPlayers();
    }
    
    @PostMapping(value = "/player")
    public ApiResponse addPlayer(@RequestBody PlayRequestList playRequestList) {
        return playerService.savePlayer(playRequestList);
    }
}
