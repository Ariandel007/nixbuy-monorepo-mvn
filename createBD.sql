------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------BD DE SECURITY--------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

CREATE DATABASE nixbuy_security;

CREATE TABLE public.user_applications (
	id bigserial NOT NULL,
	account_creation_date timestamptz(6) NULL,
	attemps int2 NOT NULL,
	auth_type varchar(255) NULL,
	birth_date timestamptz(6) NULL,
	is_blocked bool NOT NULL,
	city varchar(255) NOT NULL,
	country varchar(255) NOT NULL,
	is_deleted bool NOT NULL,
	email varchar(255) NULL,
	firstname varchar(255) NOT NULL,
	lastname varchar(255) NOT NULL,
	"password" varchar(255) NULL,
	photo_url varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT uk_fqfle34iymi1biph58uysi8qr UNIQUE (username),
	CONSTRAINT user_applications_pkey PRIMARY KEY (id)
);



CREATE TABLE public.password_history (
	id bigserial NOT NULL,
	creation_date timestamptz(6) NULL,
	id_user_app int8 NOT NULL,
	password_stored varchar(255) NOT NULL,
	CONSTRAINT password_history_pkey PRIMARY KEY (id)
);

ALTER TABLE public.password_history ADD CONSTRAINT fkhvvvfn8vmjmh2k3sh6jxgel2o FOREIGN KEY (id_user_app) REFERENCES public.user_applications(id);


CREATE TABLE public.roles_applications (
	id int8 NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT roles_applications_pkey PRIMARY KEY (id)
);

CREATE TABLE public.users_roles_applications (
	user_app_id int8 NOT NULL,
	role_app_id int8 NOT NULL
);

ALTER TABLE public.users_roles_applications ADD CONSTRAINT fkan82xl2556smf03iano5hcep6 FOREIGN KEY (role_app_id) REFERENCES public.roles_applications(id);
ALTER TABLE public.users_roles_applications ADD CONSTRAINT fkfvs9tqm7srhpxbocm59ybe8bi FOREIGN KEY (user_app_id) REFERENCES public.user_applications(id);


INSERT INTO public.roles_applications
(id, "name")
VALUES(1, 'ROLE_USER');

INSERT INTO public.roles_applications
(id, "name")
VALUES(2, 'ROLE_ADMIN');

INSERT INTO public.user_applications
(account_creation_date, attemps, auth_type, birth_date, is_blocked, city, country, is_deleted, email, firstname, lastname, "password", photo_url, username)
VALUES('2023-08-03 23:41:53.475', 0, 'email_registered', '1999-02-20 09:48:00.000', false, 'Ciudad Ejemplo', 'Ejemplolandia', false, 'ejemplo@correo.com', 'Ejemplo', 'Usuario', '$2a$10$UlQqJqg4PVb6deWkoyNBVOdKJO7WmBS7WTOLyEYLKSMFkDwIfRTF2', NULL, 'ejemplo_usuario2');