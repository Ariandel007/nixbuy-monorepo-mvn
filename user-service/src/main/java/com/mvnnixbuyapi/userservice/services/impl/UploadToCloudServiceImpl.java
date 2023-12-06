package com.mvnnixbuyapi.userservice.services.impl;

import com.mvnnixbuyapi.userservice.exceptions.ErrorConvertMultiPart;
import com.mvnnixbuyapi.userservice.services.CloudinaryService;
import com.mvnnixbuyapi.userservice.services.UploadToCloudService;
import com.mvnnixbuyapi.userservice.utils.UserServiceMessageErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@Service
@Slf4j
public class UploadToCloudServiceImpl implements UploadToCloudService {

    private final CloudinaryService cloudinaryService;

    @Autowired
    public UploadToCloudServiceImpl(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }
    @Override
    public String uploadFileToCloudinary(MultipartFile multipartFile) {
        File uploadedFile = null;
        try {
            uploadedFile = convertMultiPartToFile(multipartFile);
            Map uploadResult = cloudinaryService.uploadFileToCloudinary(uploadedFile);
            boolean isDeleted = uploadedFile.delete();

            if (isDeleted){
                log.info("Se elimino el archivo tras haberse subido");
            } else {
                log.info("El archivo no existe");
            }
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            if(uploadedFile != null) {
                uploadedFile.delete();
            }
            throw new ErrorConvertMultiPart(UserServiceMessageErrors.ERROR_FILE_CONVERTER_CODE, UserServiceMessageErrors.ERROR_FILE_CONVERTER_MSG);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        // Esto lo cargará en memoria:
//        File convFile = new File(file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;

        //Esto no pondra todo en el buffer de memoria:
        File convFile = new File(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(convFile)) {
            FileCopyUtils.copy(inputStream, outputStream);
        }
        return convFile;

    }
}
