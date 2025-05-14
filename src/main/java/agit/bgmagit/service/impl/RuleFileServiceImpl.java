package agit.bgmagit.service.impl;

import agit.bgmagit.base.entity.QRuleFile;
import agit.bgmagit.base.entity.RuleFile;
import agit.bgmagit.config.S3FileUtils;
import agit.bgmagit.controller.response.RuleFileResponse;
import agit.bgmagit.repository.RuleFileRepository;
import agit.bgmagit.service.RuleFileService;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static agit.bgmagit.base.entity.QRuleFile.*;

@RequiredArgsConstructor
@Service
@Transactional
public class RuleFileServiceImpl implements RuleFileService {
    
    private final RuleFileRepository ruleFileRepository;
    private final S3FileUtils s3FileUtils;
    private final JPAQueryFactory queryFactory;
    
    @Override
    public RuleFileResponse getFile() {
        RuleFile findRuleFile = queryFactory
                .selectFrom(ruleFile)
                .where(
                        ruleFile.ruleFileId.eq(
                                JPAExpressions
                                        .select(ruleFile.ruleFileId.max())
                                        .from(ruleFile)
                        )
                ).fetchOne();
        
        if(findRuleFile == null) {
            return new RuleFileResponse();
        }
        return new RuleFileResponse(findRuleFile.getRuleFileId(), findRuleFile.getRuleFileOriginalFileName(), findRuleFile.getRuleFileUrl());
    }
    
    @Override
    public RuleFileResponse saveFile(MultipartFile multipartFiles) {
        RuleFile ruleFile = s3FileUtils.storeFile(multipartFiles);
        
        List<RuleFile> all = ruleFileRepository.findAll();
        
        for (RuleFile file : all) {
            s3FileUtils.deleteFile(file.getRuleFileUrl());
        }
        
        ruleFileRepository.save(ruleFile);
        return new RuleFileResponse(ruleFile.getRuleFileId(),ruleFile.getRuleFileOriginalFileName(),ruleFile.getRuleFileUrl());
    }
}
