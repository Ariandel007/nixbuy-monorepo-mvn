package com.mvnnixbuyapi.order.model.entity;

import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.model.valueobjects.*;
import com.mvnnixbuyapi.product.model.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {
    private OrderId orderId;
    private OrderCreationDate orderCreationDate;
    private OrderCurrencyCode orderCurrencyCode;
    private OrderExpirationDate orderExpirationDate;
    private OrderStatus orderStatus;
    private OrderTaxesPercentage orderTaxesPercentage;
    private OrderTotalPriceWithoutTaxes orderTotalPriceWithoutTaxes;
    private OrderKeyProducts keyProducts;
    private OrderUser orderUser;

    public Order(
            Long id,
            BigDecimal totalPriceWithoutTaxes,
            BigDecimal taxesPercentage,
            String currencyCode,
            Instant expirationDate,
            Instant creationDate,
            String status,
            Long userId
    ) {
        this.orderId = new OrderId(id);
        this.orderTotalPriceWithoutTaxes = new OrderTotalPriceWithoutTaxes(totalPriceWithoutTaxes);
        this.orderTaxesPercentage = new OrderTaxesPercentage(taxesPercentage);
        this.orderCurrencyCode = new OrderCurrencyCode(currencyCode);
        this.orderExpirationDate = new OrderExpirationDate(expirationDate);
        this.orderCreationDate = new OrderCreationDate(creationDate);
        this.orderStatus = new OrderStatus(status);
        this.keyProducts = new OrderKeyProducts(new ArrayList<>());
        this.orderUser = new OrderUser(userId);
    }

    public Order requestToCreate(OrderReceivedDto orderReceivedDto){
        this.orderId = new OrderId(orderReceivedDto.getId());
        this.orderTotalPriceWithoutTaxes = new OrderTotalPriceWithoutTaxes(orderReceivedDto.getTotalPriceWithoutTaxes());
        this.orderTaxesPercentage = new OrderTaxesPercentage(orderReceivedDto.getTaxesPercentage());
        this.orderCurrencyCode = new OrderCurrencyCode(orderReceivedDto.getCurrencyCode());
        this.orderExpirationDate = new OrderExpirationDate(orderReceivedDto.getExpirationDate());
        this.orderCreationDate = new OrderCreationDate(orderReceivedDto.getCreationDate());
        this.orderStatus = new OrderStatus(orderReceivedDto.getStatus());
        this.keyProducts = new OrderKeyProducts(new ArrayList<>());
        this.orderUser = new OrderUser(orderReceivedDto.getUserId());
        return this;
    }
// TODO: MUST IMPLEMENT USER
//    public void setOrderUser(OrderUser orderUser) {
//        this.orderUser = orderUser;
//    }

    public void addProduct(KeyProduct keyProduct) {
        keyProduct.setOrder(this);
        this.keyProducts.keyProductList().add(keyProduct);
    }

    public void addProducts(List<KeyProduct> keyProducts) {
        for (KeyProduct keyProduct: keyProducts) {
            keyProduct.setOrder(this);
        }
        this.keyProducts.keyProductList().addAll(keyProducts);
    }

    public Long getId() {
        return orderId.id();
    }
    public BigDecimal getTotalPriceWithoutTaxes() {
        return orderTotalPriceWithoutTaxes.totalPriceWithoutTaxes();
    }
    public BigDecimal getTaxesPercentage() {
        return orderTaxesPercentage.taxesPercentage();
    }
    public String getCurrencyCode() {
        return orderCurrencyCode.currencyCode();
    }
    public Instant getExpirationDate() {
        return orderExpirationDate.expirationDate();
    }
    public Instant getCreationDate() {
        return orderCreationDate.creationDate();
    }
    public String getStatus() {
        return orderStatus.status();
    }
    public Long getOrderUserId() {
        return orderUser.id();
    }

}
