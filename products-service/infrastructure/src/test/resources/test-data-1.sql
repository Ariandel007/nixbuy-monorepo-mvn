CREATE TABLE IF NOT EXISTS public.products
(
    id bigserial NOT NULL,
    creation_date timestamp(6) with time zone,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    price numeric(38,2),
    update_date timestamp(6) with time zone,
    url_image character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

INSERT INTO products
(creation_date, description, name, update_date, url_image)
VALUES
    ('2023-12-28T10:00:00Z', 'Descripción del producto test 1', 'Producto test 1', '2023-12-28T10:00:00Z', '/default_product.png');
