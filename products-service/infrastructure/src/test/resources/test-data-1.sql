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

CREATE TABLE IF NOT EXISTS public.platforms
(
    key_id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT platforms_pkey PRIMARY KEY (key_id)
);

CREATE TABLE IF NOT EXISTS public.key_products
(
    key_id bigserial NOT NULL,
    key_code character varying(255) COLLATE pg_catalog."default",
    active_date timestamp(6) with time zone,
    create_date timestamp(6) with time zone,
    inactive_date timestamp(6) with time zone,
    plattform_id bigint NOT NULL,
    product_id bigint NOT NULL,
    sold_date timestamp(6) with time zone,
    status character varying(10) COLLATE pg_catalog."default",
    CONSTRAINT key_products_pkey PRIMARY KEY (key_id),
    CONSTRAINT UX_KEYCODE UNIQUE (key_code),
    CONSTRAINT FK_KEYPRODUCTS_PRODUCT FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT FK_KEYPRODUCTS_PLATTFORM FOREIGN KEY (plattform_id)
        REFERENCES public.platforms (key_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO platforms(
	key_id, name)
	VALUES (1, 'Steam');

INSERT INTO platforms(
	key_id, name)
	VALUES (2, 'EA Apps');

INSERT INTO platforms(
	key_id, name)
	VALUES (3, 'Ubisoft Connect');

INSERT INTO platforms(
	key_id, name)
	VALUES (4, 'Epic Games Store');


INSERT INTO products
(creation_date, description, price, name, update_date, url_image)
VALUES
    ('2023-12-28T10:00:00Z', 'Descripción del producto test 1', 20.0, 'Producto test 1', '2023-12-28T10:00:00Z', '/default_product.png');

INSERT INTO products
(creation_date, description, price, name, update_date, url_image)
VALUES
    ('2023-12-28T10:00:00Z', 'Descripción del producto test 2', 30.0, 'Producto test 2', '2023-12-28T10:00:00Z', '/default_product.png');

INSERT INTO products
(creation_date, description, price, name, update_date, url_image)
VALUES
    ('2023-12-28T10:00:00Z', 'Descripción del producto test 3', 40.0, 'Producto test 3', '2023-12-28T10:00:00Z', '/default_product.png');

INSERT INTO public.key_products(
	key_code, active_date, create_date, inactive_date, plattform_id, product_id, sold_date, status)
	VALUES ('DS43wqrF45523r423234f2358g0', '2023-12-28T10:00:00Z', '2023-12-28T10:00:00Z', NULL, 1, 1, NULL, 'ACTIVE');

INSERT INTO public.key_products(
	key_code, active_date, create_date, inactive_date, plattform_id, product_id, sold_date, status)
	VALUES ('RS43wqrF45523r423234f2358KM', '2023-12-28T10:00:00Z', '2023-12-28T10:00:00Z', NULL, 1, 2, NULL, 'ACTIVE');

INSERT INTO public.key_products(
	key_code, active_date, create_date, inactive_date, plattform_id, product_id, sold_date, status)
	VALUES ('LU63thyT47728f454634f2358aP', '2023-12-28T10:00:00Z', '2023-12-28T10:00:00Z', NULL, 1, 3, NULL, 'ACTIVE');

