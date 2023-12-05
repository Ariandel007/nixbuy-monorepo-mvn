package com.mvnnixbuyapi.userservice.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mvnnixbuyapi.userservice.exceptions.ErrorUploadToCloudinary;
import com.mvnnixbuyapi.userservice.services.CloudinaryService;
import com.mvnnixbuyapi.userservice.utils.UserServiceMessageErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinaryConfig;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Override
    public Map uploadFileToCloudinary(File fileToUpload) {
        try {
            return this.cloudinaryConfig.uploader().upload(fileToUpload, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new ErrorUploadToCloudinary(UserServiceMessageErrors.ERROR_FILE_UPLOAD_CODE, UserServiceMessageErrors.ERROR_FILE_UPLOAD_MSG);
        }
    }

}
