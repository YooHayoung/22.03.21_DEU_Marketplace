package com.deu.marketplace.utils.fileUploader;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FileUploaderImpl implements FileUploader {

    @Override
    public Map<String, String> uploadFiles(List<MultipartFile> files) {
        Map<String, String> result = new HashMap<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                result.put(file.getOriginalFilename(), uploadFile(file));
            }
        }

        return result;
    }

    private String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String storeFileName = createStoreFileName(file.getOriginalFilename());

        // TODO 저장 로직 구현

        return storeFileName;
    }

    private String createStoreFileName(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        String storeFileName = UUID.randomUUID().toString();
        return storeFileName + "." + ext;
    }

    @Override
    public void deleteFiles(List<String> storeFileNames) {
        // TODO 삭제 로직 구현
    }
}
