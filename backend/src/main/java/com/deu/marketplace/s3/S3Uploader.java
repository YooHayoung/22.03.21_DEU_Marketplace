package com.deu.marketplace.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.deu.marketplace.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> upload(List<MultipartFile> multipartFiles, String dirName, Long itemId, Long memberId) throws FileUploadFailedException, EmptyFileException {
        List<String> fileUrls = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            if (fileUrls.size() > 10) {
                throw new FileUploadFailedException();
            }

            String fileName = FileUtils.buildFileName(dirName, file.getOriginalFilename(), itemId, memberId);
            fileNames.add(fileName);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            try (InputStream inputStream = file.getInputStream()) {
                System.out.println("bucket = " + bucket);
                System.out.println("fileName = " + fileName);
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicReadWrite));
                fileUrls.add(amazonS3Client.getUrl(bucket, fileName).toString());
            } catch (IOException e) {
                throw new FileUploadFailedException();
            }
        }
        return fileNames;
    }

    public void fileDelete(List<String> fileNames) {
        try {
            for (String fileName : fileNames) {
                System.out.println("bucket = " + bucket);
                System.out.println("fileName = " + fileName);
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
            }
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }
    }

    public List<String> toUrl(List<String> imgFileNames) {
        List<String> fileUrls = new ArrayList<>();
        for (String fileName : imgFileNames) {
            fileUrls.add(amazonS3Client.getUrl(bucket, fileName).toString());
        }
        return fileUrls;
    }

    public String toUrl(String imgFileName) {
        return amazonS3Client.getUrl(bucket, imgFileName).toString();
    }

    private void validateFileExists(MultipartFile multipartFile) throws EmptyFileException {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException();
        }
    }
}