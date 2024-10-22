package com.mvnnixbuyapi.order.adapters.jpa.repository;

import com.mvnnixbuyapi.order.adapters.jpa.OrderSpringJpaAdapterRepository;
import com.mvnnixbuyapi.order.adapters.mapper.OrderDboMapper;
import com.mvnnixbuyapi.order.model.entity.Order;
import com.mvnnixbuyapi.order.port.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false)
public class OrderPostgresRepository implements OrderRepository {
    private final OrderSpringJpaAdapterRepository orderSpringJpaAdapterRepository;
    private final OrderDboMapper orderDboMapper;

    @Autowired
    public OrderPostgresRepository(
            OrderSpringJpaAdapterRepository orderSpringJpaAdapterRepository,
            OrderDboMapper orderDboMapper
    ) {
        this.orderSpringJpaAdapterRepository = orderSpringJpaAdapterRepository;
        this.orderDboMapper = orderDboMapper;
    }

    @Override
    public Order create(Order product) {
        var orderEntityToCreate = this.orderDboMapper.domainToEntity(product);
        var orderEntityCreated = this.orderSpringJpaAdapterRepository.save(orderEntityToCreate);
        return this.orderDboMapper.entityToDomain(orderEntityCreated);
    }

    @Override
    public Order updateOrder(Order product) {
        var orderEntityToCreate = this.orderDboMapper.domainToEntity(product);
        var orderEntityCreated = this.orderSpringJpaAdapterRepository.save(orderEntityToCreate);
        return this.orderDboMapper.entityToDomain(orderEntityCreated);
    }
}
