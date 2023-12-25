CREATE TABLE IF NOT EXISTS public.products
(
    id bigserial NOT NULL,
    creation_date timestamp(6) with time zone,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    update_date timestamp(6) with time zone,
    url_image character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT products_pkey PRIMARY KEY (id)
)