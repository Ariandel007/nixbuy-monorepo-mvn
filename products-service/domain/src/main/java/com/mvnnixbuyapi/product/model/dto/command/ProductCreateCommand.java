package com.mvnnixbuyapi.product.model.dto.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateCommand {

    @NotBlank(message = "EMPTY_NAME_PRODUCT_ERROR")
    String name;

    @NotBlank(message = "EMPTY_DESCRIPTION_PRODUCT_ERROR")
    String description;
    @NotNull(message = "NULL_PRICE_PRODUCT_ERROR")
    @Min(value = 0, message = "PRICE_SHOULD_BE_MORE_THAN_ZERO")
    BigDecimal price;
}
