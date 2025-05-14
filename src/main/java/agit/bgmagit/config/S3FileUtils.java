package agit.bgmagit.config;

import agit.bgmagit.base.entity.RuleFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3FileUtils {
    private final S3Client s3Client;
    
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    
    public List<RuleFile> storeFiles(List<MultipartFile> multipartFiles) {
        List<RuleFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadFiles.add(storeFile(multipartFile));
            }
        }
        
        return uploadFiles;
    }
    
    public RuleFile storeFile(MultipartFile multipartFile)  {
        
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        // S3에 파일 업로드
        try {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(storeFileName)
                    .build(), software.amazon.awssdk.core.sync.RequestBody.fromBytes(multipartFile.getBytes()));
            String fileUrl = s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(storeFileName)).toExternalForm();
            return new RuleFile(originalFilename, fileUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void deleteFile(String fileUrl) {
        String key = getFileNameFromUrl(fileUrl);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
    
    private String getFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }
    
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
    
}
