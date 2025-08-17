package animal.diary.service;

import animal.diary.exception.ImageUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 여러 이미지 업로드
    public List<String> uploadMultiple(List<MultipartFile> multipartFiles, String dirName) {
        return multipartFiles.stream()
                .map(multipartFile -> {
                    try {
                        return upload(multipartFile, dirName);
                    } catch (IOException e) {
                        log.error("Error uploading file: {}", e.getMessage());
                        throw new ImageUploadException("이미지 업로드에 실패했습니다. 파일 크기를 확인해주세요.");
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        // UUID 기반 파일명
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        String fileName = dirName + "/" + uuid;

        log.info("fileName: {}", fileName);

        // S3 업로드
        putS3(multipartFile, fileName);

        return fileName;
    }

    private void putS3(MultipartFile multipartFile, String fileName) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize())
        );

        log.info("S3 upload success: s3://{}/{}", bucket, fileName);
    }

    public void deleteFile(String fileName) {
        try {
            String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            log.info("Deleting file from S3: {}", decodedFileName);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(decodedFileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

        } catch (Exception e) {
            log.error("Error while deleting file: {}", e.getMessage());
        }
    }
}
