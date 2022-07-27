package com.deu.marketplace.config;

import com.deu.marketplace.utils.fileUploader.FileUploader;
import com.deu.marketplace.utils.fileUploader.FileUploaderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {

    @Bean
    public FileUploader fileUploader() {
        return new FileUploaderImpl();
    }
}
