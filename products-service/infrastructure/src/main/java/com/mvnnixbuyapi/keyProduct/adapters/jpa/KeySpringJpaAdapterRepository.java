package com.mvnnixbuyapi.keyProduct.adapters.jpa;

import com.mvnnixbuyapi.keyProduct.adapters.entity.KeyProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeySpringJpaAdapterRepository extends JpaRepository<KeyProductEntity, Long> {

    @Query(value =
            " SELECT k.* FROM key_products k                          " +
            " WHERE k.product_id = :productId AND k.status = 'ACTIVE' " +
            " ORDER BY k.create_date LIMIT :quantity                  ", nativeQuery = true)
    List<KeyProductEntity> findLimitedByProductIdAndQuantity(
            @Param("productId") Long productId,
            @Param("quantity") Integer quantity
    );

    @Modifying
    @Query("UPDATE KeyProductEntity k                                         " +
            "SET k.orderId = :orderId " +
            "WHERE k.id in (:keyProducts)                                      ")
    int updateOrderIdFieldOnKeyProducts(
            @Param("keyProducts") List<Long> keyProducts,
            @Param("orderId") Long orderId
    );

}
