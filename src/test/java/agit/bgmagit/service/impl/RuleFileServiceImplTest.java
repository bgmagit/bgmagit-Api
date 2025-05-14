package agit.bgmagit.service.impl;

import agit.bgmagit.MapperAndServiceTestSupport;
import agit.bgmagit.config.S3FileUtils;
import agit.bgmagit.service.RuleFileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RuleFileServiceImplTest extends MapperAndServiceTestSupport {
    
    @Autowired
    private RuleFileService ruleFileService;
    
    @Autowired
    private S3FileUtils s3FileUtils;
    
    @DisplayName("")
    @Test
    void test(){
        //given
        
        //when
        
        //then
    
    }
}