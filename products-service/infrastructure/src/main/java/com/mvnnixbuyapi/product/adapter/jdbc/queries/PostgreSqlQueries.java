package com.mvnnixbuyapi.product.adapter.jdbc.queries;

public class PostgreSqlQueries {
    public static String getProductsByIds = "SELECT id, name, description, price FROM products WHERE id IN (:productIds)";
}
