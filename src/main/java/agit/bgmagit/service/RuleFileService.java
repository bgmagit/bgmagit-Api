package agit.bgmagit.service;

import agit.bgmagit.controller.response.RuleFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface RuleFileService {
    
    RuleFileResponse getFile();
    
    RuleFileResponse saveFile(MultipartFile multipartFiles);
}
