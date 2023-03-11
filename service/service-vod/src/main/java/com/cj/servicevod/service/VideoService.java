package com.cj.servicevod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile file);
    void removeVideo(String id);
    void removeVideoList(List<String> videoIdList);
    String getAuth(String id);
    String getVideoAddress(String id) throws ClientException;
}
