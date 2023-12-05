package com.mvnnixbuyapi.userservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface UploadToCloudService {
    String uploadFileToCloudinary(MultipartFile multipartFile);
}
