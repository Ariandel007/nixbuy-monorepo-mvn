package com.mvnnixbuyapi.keyProduct.model.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyCreateCommand {
    @NotBlank(message = "EMPTY_KEY_CODE_ERROR")
    String keyCode;
    @NotBlank(message = "EMPTY_KEY_STATUS_ERROR")
    String status;

    @NotBlank(message = "EMPTY_ID_PRODUCT_ERROR")
    @NotNull(message = "NULL_ID_PRODUCT_ERROR")
    Long idProduct;

    @NotBlank(message = "EMPTY_ID_PLATFORM_ERROR")
    @NotNull(message = "NULL_ID_PLATFORM_ERROR")
    Long idPlatform;

}
