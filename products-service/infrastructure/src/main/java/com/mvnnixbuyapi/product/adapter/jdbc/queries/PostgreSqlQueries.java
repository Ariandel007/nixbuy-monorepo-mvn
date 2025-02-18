package com.mvnnixbuyapi.product.adapter.jdbc.queries;

public class PostgreSqlQueries {
    public static String getAvaibleProductsOfOrder =
              "SELECT p.id, p.name, p.description, p.price, count(*) as quantity    "
            + "FROM products p                                                      "
            + "INNER JOIN key_products kp ON kp.product_id = p.id                       "
            + "WHERE kp.order_id = :orderId                                         "
            + "GROUP BY p.id, p.name, p.description, p.price                        "
            ;

    public static String getProductsAvailable =
            "SELECT p.id, p.name, p.description, p.price, count(*) as quantity     "
          + "FROM products p                                                       "
          + "INNER JOIN key_products kp ON kp.key_id = p.id                        "
          + "WHERE p.id in (:idProductList)                                               "
          + "AND kp.status = 'ACTIVE'                                              "
          + "GROUP BY p.id, p.name, p.description, p.price                         "
    ;

}
