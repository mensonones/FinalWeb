--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.10
-- Dumped by pg_dump version 9.6.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: _product; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._product (
                                 id text,
                                 created_at text,
                                 name text,
                                 price text,
                                 quantity text,
                                 updated_at text,
                                 imagem text
);


ALTER TABLE public._product OWNER TO rebasedata;

--
-- Name: _role; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._role (
                              id text,
                              name text
);


ALTER TABLE public._role OWNER TO rebasedata;

--
-- Name: _user; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._user (
                              id text,
                              email text,
                              username text,
                              password text,
                              cpf text,
                              name text,
                              nascimento text,
                              endereco text
);


ALTER TABLE public._user OWNER TO rebasedata;

--
-- Name: _users_roles; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._users_roles (
                                     user_id text,
                                     role_id text
);


ALTER TABLE public._users_roles OWNER TO rebasedata;

--
-- Data for Name: _product; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._product (id, created_at, name, price, quantity, updated_at, imagem) FROM stdin;
\.


--
-- Data for Name: _role; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._role (id, name) FROM stdin;
\.


--
-- Data for Name: _user; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._user (id, email, username, password, cpf, name, nascimento, endereco) FROM stdin;
\.


--
-- Data for Name: _users_roles; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._users_roles (user_id, role_id) FROM stdin;
\.


--
-- PostgreSQL database dump complete
--
