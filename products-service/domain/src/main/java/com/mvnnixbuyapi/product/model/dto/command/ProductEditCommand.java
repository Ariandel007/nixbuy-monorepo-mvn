package com.mvnnixbuyapi.product.model.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEditCommand {
    private Long id;
    private String name;
    private String description;
    private String urlImage;
    private Boolean isPhotoUploaded;
    MultipartFile mainPhotoOfProductFile;
}
