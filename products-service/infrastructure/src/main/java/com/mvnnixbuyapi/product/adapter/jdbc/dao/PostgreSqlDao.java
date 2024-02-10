package com.mvnnixbuyapi.product.adapter.jdbc.dao;

import com.mvnnixbuyapi.product.adapter.jdbc.queries.PostgreSqlQueries;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.query.ItemCartDto;
import com.mvnnixbuyapi.product.port.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PostgreSqlDao implements ProductDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PostgreSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<ProductDto> getProductByIds(Long orderId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("orderId", orderId);
        return jdbcTemplate.query(
                PostgreSqlQueries.getAvaibleProductsOfOrder,
                parameters,
                BeanPropertyRowMapper.newInstance(ProductDto.class)
        );
    }

    @Override
    public List<ProductDto> getProductsAvailable(List<ItemCartDto> itemCartDtoList) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        var idProductList = itemCartDtoList.stream().map(ItemCartDto::getProductId).toList();
        parameters.addValue("idProductList", idProductList);
        return jdbcTemplate.query(
                PostgreSqlQueries.getProductsAvailable,
                parameters,
                BeanPropertyRowMapper.newInstance(ProductDto.class)
        );
    }
}
