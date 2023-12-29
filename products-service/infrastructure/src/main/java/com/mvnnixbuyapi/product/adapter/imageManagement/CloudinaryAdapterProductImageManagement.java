package com.mvnnixbuyapi.product.adapter.imageManagement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mvnnixbuyapi.product.exceptions.ProductImageMultipartException;
import com.mvnnixbuyapi.product.exceptions.ProductUploadToCloudinaryException;
import com.mvnnixbuyapi.product.model.entity.Product;
import com.mvnnixbuyapi.product.port.imageManagement.ProductImageManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
@Slf4j
public class CloudinaryAdapterProductImageManagement implements ProductImageManagement {
    private final Cloudinary cloudinaryConfig;

    @Autowired
    public CloudinaryAdapterProductImageManagement(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Override
    public String generateNewUrl(Product product, MultipartFile file) {
        File uploadedFile = null;
        try {
            uploadedFile = this.convertMultiPartToFile(file);
            Map uploadResult = this.uploadFileToCloudinary(uploadedFile);
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
            log.error("An error with the file has happened");
            e.printStackTrace();
            // TODO: throw AN EXCEPTION
            throw new ProductImageMultipartException("ERROR_PRODUCT_IMAGE_MULTIPART_EXCEPTION");
        }
    }

    private Map uploadFileToCloudinary(File fileToUpload) {
        try {
            return this.cloudinaryConfig.uploader().upload(fileToUpload, ObjectUtils.emptyMap());
        } catch (Exception e) {
            if(fileToUpload != null) {
                fileToUpload.delete();
            }
            log.error("An error uploading to cloudinary has happened");
            e.printStackTrace();
            // TODO: throw AN EXCEPTION
            throw new ProductUploadToCloudinaryException("ERROR_PRODUCT_UPLOAD_TO_CLOUDINARY");
        }
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(convFile)) {
            FileCopyUtils.copy(inputStream, outputStream);
        }
        return convFile;

    }
}
