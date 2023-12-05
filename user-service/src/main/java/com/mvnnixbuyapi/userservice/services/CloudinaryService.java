package com.mvnnixbuyapi.userservice.services;

import java.io.File;
import java.util.Map;

public interface CloudinaryService {
    Map uploadFileToCloudinary(File fileToUpload);
}
