package com.mvnnixbuyapi.product.adapter.jdbc.queries;

public class PostgreSqlQueries {
    public static String getAvaibleProductsToBuyByIds =
              "SELECT DISTINCT p.id, p.name, p.description, p.price          "
            + "FROM products p                                               "
            + "INNER JOIN key_products kp ON kp.key_id = p.id                "
            + "INNER JOIN platforms pl ON pl.key_id = kp.plattform_id        "
            + "WHERE p.id IN (:productIds)                                   "
            + "AND pl.key_id = :idPlatform                                   "
            + "AND kp.status = 'ACTIVE'                                      "
            + "ORDER BY p.id                                                 "
            ;
}
