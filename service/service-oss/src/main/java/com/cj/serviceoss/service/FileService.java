package com.cj.serviceoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile file,String file_type);
    Boolean delete(String objectName);
}
