package com.mvnnixbuyapi.product.model.dto.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateCommand {

    @NotBlank(message = "EMPTY_NAME_PRODUCT_ERROR")
    String name;

    @NotBlank(message = "EMPTY_DESCRIPTION_PRODUCT_ERROR")
    String description;
}
