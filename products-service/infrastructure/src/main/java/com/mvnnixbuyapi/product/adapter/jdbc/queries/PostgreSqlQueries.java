package com.mvnnixbuyapi.product.adapter.jdbc.queries;

public class PostgreSqlQueries {
    public static String getAvaibleProductsToBuyByIds =
              "SELECT p.id, p.name, p.description, p.price, count(*) as quantity    "
            + "FROM products p                                                      "
            + "INNER JOIN key_products kp ON kp.key_id = p.id                       "
            + "WHERE kp.order_id = :orderId                                         "
            + "AND kp.status = 'ACTIVE'                                             "
            + "GROUP BY p.id, p.name, p.description, p.price                        "
            ;
}
