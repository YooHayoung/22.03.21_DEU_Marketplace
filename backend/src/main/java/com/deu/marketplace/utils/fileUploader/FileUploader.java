package com.deu.marketplace.utils.fileUploader;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileUploader {

    Map<String, String> uploadFiles(List<MultipartFile> files);

    void deleteFiles(List<String> storeFileNames);
}
