package com.mvnnixbuyapi.order.port.repository;

import com.mvnnixbuyapi.order.model.entity.Order;
import com.mvnnixbuyapi.product.model.entity.Product;

public interface OrderRepository {
    Order create(Order product);
    Order updateOrder(Order product);

}
