package agit.bgmagit.controller;


import agit.bgmagit.controller.response.RuleFileResponse;
import agit.bgmagit.service.RuleFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bgm-agit")
public class RuleFileController {
    
    private final RuleFileService ruleFileService;
    
    @GetMapping("rule-file")
    public RuleFileResponse getRuleFile() {
        return ruleFileService.getFile();
    }
    
    
    @PostMapping("/rule-file")
    public RuleFileResponse uploadFile(@RequestParam("ruleFile") MultipartFile ruleFile) {
        return ruleFileService.saveFile(ruleFile);
    }
}
