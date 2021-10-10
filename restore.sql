--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 14.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE IF EXISTS ONLY public.card_rating DROP CONSTRAINT IF EXISTS user_id;
ALTER TABLE IF EXISTS ONLY public.card_enroll DROP CONSTRAINT IF EXISTS user_id;
ALTER TABLE IF EXISTS ONLY public.session DROP CONSTRAINT IF EXISTS tutor_id;
ALTER TABLE IF EXISTS ONLY public.service DROP CONSTRAINT IF EXISTS tutor_id;
ALTER TABLE IF EXISTS ONLY public.session DROP CONSTRAINT IF EXISTS subject_id;
ALTER TABLE IF EXISTS ONLY public.card DROP CONSTRAINT IF EXISTS subject_id;
ALTER TABLE IF EXISTS ONLY public.session_student DROP CONSTRAINT IF EXISTS student_id;
ALTER TABLE IF EXISTS ONLY public.request DROP CONSTRAINT IF EXISTS student_id;
ALTER TABLE IF EXISTS ONLY public.card_enroll DROP CONSTRAINT IF EXISTS status_id;
ALTER TABLE IF EXISTS ONLY public.session DROP CONSTRAINT IF EXISTS session_type_id;
ALTER TABLE IF EXISTS ONLY public.card_session_type DROP CONSTRAINT IF EXISTS session_type_id;
ALTER TABLE IF EXISTS ONLY public.card_enroll_session_type DROP CONSTRAINT IF EXISTS session_type_id;
ALTER TABLE IF EXISTS ONLY public.session_student DROP CONSTRAINT IF EXISTS session_id;
ALTER TABLE IF EXISTS ONLY public.session DROP CONSTRAINT IF EXISTS session_format_id;
ALTER TABLE IF EXISTS ONLY public.card_session_format DROP CONSTRAINT IF EXISTS session_format_id;
ALTER TABLE IF EXISTS ONLY public.card_enroll_session_format DROP CONSTRAINT IF EXISTS session_format_id;
ALTER TABLE IF EXISTS ONLY public.service DROP CONSTRAINT IF EXISTS card_id;
ALTER TABLE IF EXISTS ONLY public.request DROP CONSTRAINT IF EXISTS card_id;
ALTER TABLE IF EXISTS ONLY public.card_session_type DROP CONSTRAINT IF EXISTS card_id;
ALTER TABLE IF EXISTS ONLY public.card_session_format DROP CONSTRAINT IF EXISTS card_id;
ALTER TABLE IF EXISTS ONLY public.card_rating DROP CONSTRAINT IF EXISTS card_id;
ALTER TABLE IF EXISTS ONLY public.card_enroll DROP CONSTRAINT IF EXISTS card_id;
ALTER TABLE IF EXISTS ONLY public.card_enroll_session_type DROP CONSTRAINT IF EXISTS card_enroll_id;
ALTER TABLE IF EXISTS ONLY public.card_enroll_session_format DROP CONSTRAINT IF EXISTS card_enroll_id;
DROP INDEX IF EXISTS public.user_user_id_uindex;
DROP INDEX IF EXISTS public.user_email_uindex;
DROP INDEX IF EXISTS public.subject_subject_id_uindex;
DROP INDEX IF EXISTS public.subject_name_uindex;
DROP INDEX IF EXISTS public.session_type_session_type_id_uindex;
DROP INDEX IF EXISTS public.session_type_name_uindex;
DROP INDEX IF EXISTS public.session_student_session_student_id_uindex;
DROP INDEX IF EXISTS public.session_session_id_uindex;
DROP INDEX IF EXISTS public.session_format_session_format_id_uindex;
DROP INDEX IF EXISTS public.session_format_name_uindex;
DROP INDEX IF EXISTS public.service_service_id_uindex;
DROP INDEX IF EXISTS public.request_request_id_uindex;
DROP INDEX IF EXISTS public.enrollment_status_status_uindex;
DROP INDEX IF EXISTS public.enrollment_status_status_id_uindex;
DROP INDEX IF EXISTS public.card_session_type_card_session_type_id_uindex;
DROP INDEX IF EXISTS public.card_session_format_card_session_format_id_uindex;
DROP INDEX IF EXISTS public.card_rating_card_rating_id_uindex;
DROP INDEX IF EXISTS public.card_enroll_session_type_card_enroll_session_type_id_uindex;
DROP INDEX IF EXISTS public.card_enroll_session_format_card_enroll_session_format_id_uindex;
DROP INDEX IF EXISTS public.card_enroll_card_enroll_id_uindex;
DROP INDEX IF EXISTS public.card_card_id_uindex;
ALTER TABLE IF EXISTS ONLY public."user" DROP CONSTRAINT IF EXISTS user_pk;
ALTER TABLE IF EXISTS ONLY public.subject DROP CONSTRAINT IF EXISTS subject_pk;
ALTER TABLE IF EXISTS ONLY public.session_type DROP CONSTRAINT IF EXISTS session_type_pk;
ALTER TABLE IF EXISTS ONLY public.session_student DROP CONSTRAINT IF EXISTS session_student_pk;
ALTER TABLE IF EXISTS ONLY public.session DROP CONSTRAINT IF EXISTS session_pk;
ALTER TABLE IF EXISTS ONLY public.session_format DROP CONSTRAINT IF EXISTS session_format_pk;
ALTER TABLE IF EXISTS ONLY public.service DROP CONSTRAINT IF EXISTS service_pk;
ALTER TABLE IF EXISTS ONLY public.request DROP CONSTRAINT IF EXISTS request_pk;
ALTER TABLE IF EXISTS ONLY public.enrollment_status DROP CONSTRAINT IF EXISTS enrollment_status_pk;
ALTER TABLE IF EXISTS ONLY public.card_session_type DROP CONSTRAINT IF EXISTS card_session_type_pk;
ALTER TABLE IF EXISTS ONLY public.card_session_format DROP CONSTRAINT IF EXISTS card_session_format_pk;
ALTER TABLE IF EXISTS ONLY public.card_rating DROP CONSTRAINT IF EXISTS card_rating_pk;
ALTER TABLE IF EXISTS ONLY public.card DROP CONSTRAINT IF EXISTS card_pk;
ALTER TABLE IF EXISTS ONLY public.card_enroll_session_type DROP CONSTRAINT IF EXISTS card_enroll_session_type_pk;
ALTER TABLE IF EXISTS ONLY public.card_enroll_session_format DROP CONSTRAINT IF EXISTS card_enroll_session_format_pk;
ALTER TABLE IF EXISTS ONLY public.card_enroll DROP CONSTRAINT IF EXISTS card_enroll_pk;
ALTER TABLE IF EXISTS public."user" ALTER COLUMN user_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.subject ALTER COLUMN subject_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.session_type ALTER COLUMN session_type_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.session_student ALTER COLUMN session_student_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.session_format ALTER COLUMN session_format_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.session ALTER COLUMN session_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.service ALTER COLUMN service_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.request ALTER COLUMN request_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.enrollment_status ALTER COLUMN status_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.card_session_type ALTER COLUMN card_session_type_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.card_session_format ALTER COLUMN card_session_format_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.card_rating ALTER COLUMN card_rating_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.card_enroll_session_type ALTER COLUMN card_enroll_session_type_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.card_enroll_session_format ALTER COLUMN card_enroll_session_format_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.card_enroll ALTER COLUMN card_enroll_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.card ALTER COLUMN card_id DROP DEFAULT;
DROP SEQUENCE IF EXISTS public.user_user_id_seq;
DROP TABLE IF EXISTS public."user";
DROP SEQUENCE IF EXISTS public.subject_subject_id_seq;
DROP TABLE IF EXISTS public.subject;
DROP SEQUENCE IF EXISTS public.session_type_session_type_id_seq;
DROP TABLE IF EXISTS public.session_type;
DROP SEQUENCE IF EXISTS public.session_student_session_student_id_seq;
DROP TABLE IF EXISTS public.session_student;
DROP SEQUENCE IF EXISTS public.session_session_id_seq;
DROP SEQUENCE IF EXISTS public.session_format_session_format_id_seq;
DROP TABLE IF EXISTS public.session_format;
DROP TABLE IF EXISTS public.session;
DROP SEQUENCE IF EXISTS public.service_service_id_seq;
DROP TABLE IF EXISTS public.service;
DROP SEQUENCE IF EXISTS public.request_request_id_seq;
DROP TABLE IF EXISTS public.request;
DROP SEQUENCE IF EXISTS public.enrollment_status_status_id_seq;
DROP TABLE IF EXISTS public.enrollment_status;
DROP SEQUENCE IF EXISTS public.card_session_type_card_session_type_id_seq;
DROP TABLE IF EXISTS public.card_session_type;
DROP SEQUENCE IF EXISTS public.card_session_format_card_session_format_id_seq;
DROP TABLE IF EXISTS public.card_session_format;
DROP SEQUENCE IF EXISTS public.card_rating_card_rating_id_seq;
DROP TABLE IF EXISTS public.card_rating;
DROP SEQUENCE IF EXISTS public.card_enroll_session_type_card_enroll_session_type_id_seq;
DROP TABLE IF EXISTS public.card_enroll_session_type;
DROP SEQUENCE IF EXISTS public.card_enroll_session_format_card_enroll_session_format_id_seq;
DROP TABLE IF EXISTS public.card_enroll_session_format;
DROP SEQUENCE IF EXISTS public.card_enroll_card_enroll_id_seq;
DROP TABLE IF EXISTS public.card_enroll;
DROP SEQUENCE IF EXISTS public.card_card_id_seq;
DROP TABLE IF EXISTS public.card;
DROP SCHEMA IF EXISTS public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: card; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.card (
    card_id bigint NOT NULL,
    subject_id bigint NOT NULL,
    description character varying(1024),
    creation_date timestamp without time zone,
    last_update timestamp without time zone,
    hidden boolean NOT NULL
);


ALTER TABLE public.card OWNER TO postgres;

--
-- Name: card_card_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.card_card_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_card_id_seq OWNER TO postgres;

--
-- Name: card_card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.card_card_id_seq OWNED BY public.card.card_id;


--
-- Name: card_enroll; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.card_enroll (
    card_enroll_id bigint NOT NULL,
    card_id bigint NOT NULL,
    user_id bigint NOT NULL,
    status_id bigint NOT NULL,
    creation_date timestamp without time zone,
    last_update timestamp without time zone
);


ALTER TABLE public.card_enroll OWNER TO postgres;

--
-- Name: card_enroll_card_enroll_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.card_enroll_card_enroll_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_enroll_card_enroll_id_seq OWNER TO postgres;

--
-- Name: card_enroll_card_enroll_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.card_enroll_card_enroll_id_seq OWNED BY public.card_enroll.card_enroll_id;


--
-- Name: card_enroll_session_format; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.card_enroll_session_format (
    card_enroll_id bigint NOT NULL,
    session_format_id bigint NOT NULL,
    card_enroll_session_format_id bigint NOT NULL
);


ALTER TABLE public.card_enroll_session_format OWNER TO postgres;

--
-- Name: card_enroll_session_format_card_enroll_session_format_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.card_enroll_session_format_card_enroll_session_format_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_enroll_session_format_card_enroll_session_format_id_seq OWNER TO postgres;

--
-- Name: card_enroll_session_format_card_enroll_session_format_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.card_enroll_session_format_card_enroll_session_format_id_seq OWNED BY public.card_enroll_session_format.card_enroll_session_format_id;


--
-- Name: card_enroll_session_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.card_enroll_session_type (
    card_enroll_id bigint NOT NULL,
    session_type_id bigint NOT NULL,
    card_enroll_session_type_id bigint NOT NULL
);


ALTER TABLE public.card_enroll_session_type OWNER TO postgres;

--
-- Name: card_enroll_session_type_card_enroll_session_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.card_enroll_session_type_card_enroll_session_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_enroll_session_type_card_enroll_session_type_id_seq OWNER TO postgres;

--
-- Name: card_enroll_session_type_card_enroll_session_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.card_enroll_session_type_card_enroll_session_type_id_seq OWNED BY public.card_enroll_session_type.card_enroll_session_type_id;


--
-- Name: card_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.card_rating (
    card_rating_id bigint NOT NULL,
    card_id bigint NOT NULL,
    user_id bigint NOT NULL,
    mark integer NOT NULL,
    creation_date timestamp without time zone,
    last_update timestamp without time zone
);


ALTER TABLE public.card_rating OWNER TO postgres;

--
-- Name: card_rating_card_rating_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.card_rating_card_rating_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_rating_card_rating_id_seq OWNER TO postgres;

--
-- Name: card_rating_card_rating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.card_rating_card_rating_id_seq OWNED BY public.card_rating.card_rating_id;


--
-- Name: card_session_format; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.card_session_format (
    card_id bigint NOT NULL,
    session_format_id bigint NOT NULL,
    card_session_format_id bigint NOT NULL
);


ALTER TABLE public.card_session_format OWNER TO postgres;

--
-- Name: card_session_format_card_session_format_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.card_session_format_card_session_format_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_session_format_card_session_format_id_seq OWNER TO postgres;

--
-- Name: card_session_format_card_session_format_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.card_session_format_card_session_format_id_seq OWNED BY public.card_session_format.card_session_format_id;


--
-- Name: card_session_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.card_session_type (
    card_id bigint NOT NULL,
    session_type_id bigint NOT NULL,
    card_session_type_id bigint NOT NULL
);


ALTER TABLE public.card_session_type OWNER TO postgres;

--
-- Name: card_session_type_card_session_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.card_session_type_card_session_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_session_type_card_session_type_id_seq OWNER TO postgres;

--
-- Name: card_session_type_card_session_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.card_session_type_card_session_type_id_seq OWNED BY public.card_session_type.card_session_type_id;


--
-- Name: enrollment_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enrollment_status (
    status_id bigint NOT NULL,
    status character varying(64) NOT NULL,
    creation_date timestamp without time zone,
    last_update timestamp without time zone
);


ALTER TABLE public.enrollment_status OWNER TO postgres;

--
-- Name: enrollment_status_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.enrollment_status_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.enrollment_status_status_id_seq OWNER TO postgres;

--
-- Name: enrollment_status_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.enrollment_status_status_id_seq OWNED BY public.enrollment_status.status_id;


--
-- Name: request; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.request (
    student_id bigint NOT NULL,
    card_id bigint NOT NULL,
    request_id bigint NOT NULL
);


ALTER TABLE public.request OWNER TO postgres;

--
-- Name: request_request_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.request_request_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.request_request_id_seq OWNER TO postgres;

--
-- Name: request_request_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.request_request_id_seq OWNED BY public.request.request_id;


--
-- Name: service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.service (
    tutor_id bigint NOT NULL,
    card_id bigint NOT NULL,
    service_id bigint NOT NULL
);


ALTER TABLE public.service OWNER TO postgres;

--
-- Name: service_service_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.service_service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.service_service_id_seq OWNER TO postgres;

--
-- Name: service_service_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.service_service_id_seq OWNED BY public.service.service_id;


--
-- Name: session; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.session (
    session_id bigint NOT NULL,
    tutor_id bigint NOT NULL,
    subject_id bigint NOT NULL,
    session_format_id bigint NOT NULL,
    session_type_id bigint NOT NULL,
    date date NOT NULL,
    start_time time without time zone NOT NULL,
    end_time time without time zone NOT NULL,
    description character varying(1024),
    creation_date timestamp without time zone,
    last_update timestamp without time zone
);


ALTER TABLE public.session OWNER TO postgres;

--
-- Name: session_format; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.session_format (
    session_format_id bigint NOT NULL,
    name character varying(64) NOT NULL,
    creation_date timestamp without time zone,
    last_update timestamp without time zone
);


ALTER TABLE public.session_format OWNER TO postgres;

--
-- Name: session_format_session_format_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.session_format_session_format_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.session_format_session_format_id_seq OWNER TO postgres;

--
-- Name: session_format_session_format_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.session_format_session_format_id_seq OWNED BY public.session_format.session_format_id;


--
-- Name: session_session_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.session_session_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.session_session_id_seq OWNER TO postgres;

--
-- Name: session_session_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.session_session_id_seq OWNED BY public.session.session_id;


--
-- Name: session_student; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.session_student (
    session_id bigint NOT NULL,
    student_id bigint NOT NULL,
    session_student_id bigint NOT NULL
);


ALTER TABLE public.session_student OWNER TO postgres;

--
-- Name: session_student_session_student_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.session_student_session_student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.session_student_session_student_id_seq OWNER TO postgres;

--
-- Name: session_student_session_student_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.session_student_session_student_id_seq OWNED BY public.session_student.session_student_id;


--
-- Name: session_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.session_type (
    session_type_id bigint NOT NULL,
    name character varying(64) NOT NULL,
    creation_date timestamp without time zone,
    last_update timestamp without time zone
);


ALTER TABLE public.session_type OWNER TO postgres;

--
-- Name: session_type_session_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.session_type_session_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.session_type_session_type_id_seq OWNER TO postgres;

--
-- Name: session_type_session_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.session_type_session_type_id_seq OWNED BY public.session_type.session_type_id;


--
-- Name: subject; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subject (
    subject_id bigint NOT NULL,
    name character varying(64) NOT NULL,
    creation_date timestamp without time zone,
    last_update timestamp without time zone
);


ALTER TABLE public.subject OWNER TO postgres;

--
-- Name: subject_subject_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subject_subject_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subject_subject_id_seq OWNER TO postgres;

--
-- Name: subject_subject_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subject_subject_id_seq OWNED BY public.subject.subject_id;


--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    user_id bigint NOT NULL,
    name character varying(64) NOT NULL,
    surname character varying(64) NOT NULL,
    email character varying(64) NOT NULL,
    password character varying(64),
    contacts character varying(256),
    description character varying(1024),
    creation_date timestamp without time zone,
    last_update timestamp without time zone,
    picture character varying(512) NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Name: user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_user_id_seq OWNER TO postgres;

--
-- Name: user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_user_id_seq OWNED BY public."user".user_id;


--
-- Name: card card_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card ALTER COLUMN card_id SET DEFAULT nextval('public.card_card_id_seq'::regclass);


--
-- Name: card_enroll card_enroll_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll ALTER COLUMN card_enroll_id SET DEFAULT nextval('public.card_enroll_card_enroll_id_seq'::regclass);


--
-- Name: card_enroll_session_format card_enroll_session_format_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll_session_format ALTER COLUMN card_enroll_session_format_id SET DEFAULT nextval('public.card_enroll_session_format_card_enroll_session_format_id_seq'::regclass);


--
-- Name: card_enroll_session_type card_enroll_session_type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll_session_type ALTER COLUMN card_enroll_session_type_id SET DEFAULT nextval('public.card_enroll_session_type_card_enroll_session_type_id_seq'::regclass);


--
-- Name: card_rating card_rating_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_rating ALTER COLUMN card_rating_id SET DEFAULT nextval('public.card_rating_card_rating_id_seq'::regclass);


--
-- Name: card_session_format card_session_format_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_session_format ALTER COLUMN card_session_format_id SET DEFAULT nextval('public.card_session_format_card_session_format_id_seq'::regclass);


--
-- Name: card_session_type card_session_type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_session_type ALTER COLUMN card_session_type_id SET DEFAULT nextval('public.card_session_type_card_session_type_id_seq'::regclass);


--
-- Name: enrollment_status status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment_status ALTER COLUMN status_id SET DEFAULT nextval('public.enrollment_status_status_id_seq'::regclass);


--
-- Name: request request_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request ALTER COLUMN request_id SET DEFAULT nextval('public.request_request_id_seq'::regclass);


--
-- Name: service service_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service ALTER COLUMN service_id SET DEFAULT nextval('public.service_service_id_seq'::regclass);


--
-- Name: session session_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session ALTER COLUMN session_id SET DEFAULT nextval('public.session_session_id_seq'::regclass);


--
-- Name: session_format session_format_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session_format ALTER COLUMN session_format_id SET DEFAULT nextval('public.session_format_session_format_id_seq'::regclass);


--
-- Name: session_student session_student_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session_student ALTER COLUMN session_student_id SET DEFAULT nextval('public.session_student_session_student_id_seq'::regclass);


--
-- Name: session_type session_type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session_type ALTER COLUMN session_type_id SET DEFAULT nextval('public.session_type_session_type_id_seq'::regclass);


--
-- Name: subject subject_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject ALTER COLUMN subject_id SET DEFAULT nextval('public.subject_subject_id_seq'::regclass);


--
-- Name: user user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN user_id SET DEFAULT nextval('public.user_user_id_seq'::regclass);


--
-- Data for Name: card; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: card_enroll; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: card_enroll_session_format; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: card_enroll_session_type; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: card_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: card_session_format; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: card_session_type; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: enrollment_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.enrollment_status VALUES (1, 'requested', NULL, NULL);
INSERT INTO public.enrollment_status VALUES (2, 'accepted', NULL, NULL);
INSERT INTO public.enrollment_status VALUES (3, 'rejected', NULL, NULL);


--
-- Data for Name: request; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: session; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: session_format; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.session_format VALUES (1, 'offline', NULL, NULL);
INSERT INTO public.session_format VALUES (2, 'online', NULL, NULL);


--
-- Data for Name: session_student; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: session_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.session_type VALUES (1, 'private', NULL, NULL);
INSERT INTO public.session_type VALUES (2, 'group', NULL, NULL);


--
-- Data for Name: subject; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: card_card_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.card_card_id_seq', 18, true);


--
-- Name: card_enroll_card_enroll_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.card_enroll_card_enroll_id_seq', 14, true);


--
-- Name: card_enroll_session_format_card_enroll_session_format_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.card_enroll_session_format_card_enroll_session_format_id_seq', 14, true);


--
-- Name: card_enroll_session_type_card_enroll_session_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.card_enroll_session_type_card_enroll_session_type_id_seq', 14, true);


--
-- Name: card_rating_card_rating_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.card_rating_card_rating_id_seq', 3, true);


--
-- Name: card_session_format_card_session_format_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.card_session_format_card_session_format_id_seq', 53, true);


--
-- Name: card_session_type_card_session_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.card_session_type_card_session_type_id_seq', 43, true);


--
-- Name: enrollment_status_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enrollment_status_status_id_seq', 3, true);


--
-- Name: request_request_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.request_request_id_seq', 4, true);


--
-- Name: service_service_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.service_service_id_seq', 9, true);


--
-- Name: session_format_session_format_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.session_format_session_format_id_seq', 2, true);


--
-- Name: session_session_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.session_session_id_seq', 4, true);


--
-- Name: session_student_session_student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.session_student_session_student_id_seq', 6, true);


--
-- Name: session_type_session_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.session_type_session_type_id_seq', 2, true);


--
-- Name: subject_subject_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.subject_subject_id_seq', 7, true);


--
-- Name: user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_user_id_seq', 6, true);


--
-- Name: card_enroll card_enroll_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll
    ADD CONSTRAINT card_enroll_pk PRIMARY KEY (card_enroll_id);


--
-- Name: card_enroll_session_format card_enroll_session_format_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll_session_format
    ADD CONSTRAINT card_enroll_session_format_pk PRIMARY KEY (card_enroll_session_format_id);


--
-- Name: card_enroll_session_type card_enroll_session_type_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll_session_type
    ADD CONSTRAINT card_enroll_session_type_pk PRIMARY KEY (card_enroll_session_type_id);


--
-- Name: card card_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_pk PRIMARY KEY (card_id);


--
-- Name: card_rating card_rating_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_rating
    ADD CONSTRAINT card_rating_pk PRIMARY KEY (card_rating_id);


--
-- Name: card_session_format card_session_format_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_session_format
    ADD CONSTRAINT card_session_format_pk PRIMARY KEY (card_session_format_id);


--
-- Name: card_session_type card_session_type_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_session_type
    ADD CONSTRAINT card_session_type_pk PRIMARY KEY (card_session_type_id);


--
-- Name: enrollment_status enrollment_status_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment_status
    ADD CONSTRAINT enrollment_status_pk PRIMARY KEY (status_id);


--
-- Name: request request_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_pk PRIMARY KEY (request_id);


--
-- Name: service service_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pk PRIMARY KEY (service_id);


--
-- Name: session_format session_format_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session_format
    ADD CONSTRAINT session_format_pk PRIMARY KEY (session_format_id);


--
-- Name: session session_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session
    ADD CONSTRAINT session_pk PRIMARY KEY (session_id);


--
-- Name: session_student session_student_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session_student
    ADD CONSTRAINT session_student_pk PRIMARY KEY (session_student_id);


--
-- Name: session_type session_type_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session_type
    ADD CONSTRAINT session_type_pk PRIMARY KEY (session_type_id);


--
-- Name: subject subject_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject
    ADD CONSTRAINT subject_pk PRIMARY KEY (subject_id);


--
-- Name: user user_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (user_id);


--
-- Name: card_card_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX card_card_id_uindex ON public.card USING btree (card_id);


--
-- Name: card_enroll_card_enroll_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX card_enroll_card_enroll_id_uindex ON public.card_enroll USING btree (card_enroll_id);


--
-- Name: card_enroll_session_format_card_enroll_session_format_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX card_enroll_session_format_card_enroll_session_format_id_uindex ON public.card_enroll_session_format USING btree (card_enroll_session_format_id);


--
-- Name: card_enroll_session_type_card_enroll_session_type_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX card_enroll_session_type_card_enroll_session_type_id_uindex ON public.card_enroll_session_type USING btree (card_enroll_session_type_id);


--
-- Name: card_rating_card_rating_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX card_rating_card_rating_id_uindex ON public.card_rating USING btree (card_rating_id);


--
-- Name: card_session_format_card_session_format_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX card_session_format_card_session_format_id_uindex ON public.card_session_format USING btree (card_session_format_id);


--
-- Name: card_session_type_card_session_type_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX card_session_type_card_session_type_id_uindex ON public.card_session_type USING btree (card_session_type_id);


--
-- Name: enrollment_status_status_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX enrollment_status_status_id_uindex ON public.enrollment_status USING btree (status_id);


--
-- Name: enrollment_status_status_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX enrollment_status_status_uindex ON public.enrollment_status USING btree (status);


--
-- Name: request_request_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX request_request_id_uindex ON public.request USING btree (request_id);


--
-- Name: service_service_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX service_service_id_uindex ON public.service USING btree (service_id);


--
-- Name: session_format_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX session_format_name_uindex ON public.session_format USING btree (name);


--
-- Name: session_format_session_format_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX session_format_session_format_id_uindex ON public.session_format USING btree (session_format_id);


--
-- Name: session_session_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX session_session_id_uindex ON public.session USING btree (session_id);


--
-- Name: session_student_session_student_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX session_student_session_student_id_uindex ON public.session_student USING btree (session_student_id);


--
-- Name: session_type_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX session_type_name_uindex ON public.session_type USING btree (name);


--
-- Name: session_type_session_type_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX session_type_session_type_id_uindex ON public.session_type USING btree (session_type_id);


--
-- Name: subject_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX subject_name_uindex ON public.subject USING btree (name);


--
-- Name: subject_subject_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX subject_subject_id_uindex ON public.subject USING btree (subject_id);


--
-- Name: user_email_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX user_email_uindex ON public."user" USING btree (email);


--
-- Name: user_user_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX user_user_id_uindex ON public."user" USING btree (user_id);


--
-- Name: card_enroll_session_format card_enroll_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll_session_format
    ADD CONSTRAINT card_enroll_id FOREIGN KEY (card_enroll_id) REFERENCES public.card_enroll(card_enroll_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_enroll_session_type card_enroll_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll_session_type
    ADD CONSTRAINT card_enroll_id FOREIGN KEY (card_enroll_id) REFERENCES public.card_enroll(card_enroll_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_enroll card_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll
    ADD CONSTRAINT card_id FOREIGN KEY (card_id) REFERENCES public.card(card_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_rating card_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_rating
    ADD CONSTRAINT card_id FOREIGN KEY (card_id) REFERENCES public.card(card_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_session_format card_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_session_format
    ADD CONSTRAINT card_id FOREIGN KEY (card_id) REFERENCES public.card(card_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_session_type card_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_session_type
    ADD CONSTRAINT card_id FOREIGN KEY (card_id) REFERENCES public.card(card_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: request card_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT card_id FOREIGN KEY (card_id) REFERENCES public.card(card_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: service card_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT card_id FOREIGN KEY (card_id) REFERENCES public.card(card_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_enroll_session_format session_format_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll_session_format
    ADD CONSTRAINT session_format_id FOREIGN KEY (session_format_id) REFERENCES public.session_format(session_format_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_session_format session_format_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_session_format
    ADD CONSTRAINT session_format_id FOREIGN KEY (session_format_id) REFERENCES public.session_format(session_format_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: session session_format_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session
    ADD CONSTRAINT session_format_id FOREIGN KEY (session_format_id) REFERENCES public.session_format(session_format_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: session_student session_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session_student
    ADD CONSTRAINT session_id FOREIGN KEY (session_id) REFERENCES public.session(session_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_enroll_session_type session_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll_session_type
    ADD CONSTRAINT session_type_id FOREIGN KEY (session_type_id) REFERENCES public.session_type(session_type_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_session_type session_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_session_type
    ADD CONSTRAINT session_type_id FOREIGN KEY (session_type_id) REFERENCES public.session_type(session_type_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: session session_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session
    ADD CONSTRAINT session_type_id FOREIGN KEY (session_type_id) REFERENCES public.session_type(session_type_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_enroll status_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll
    ADD CONSTRAINT status_id FOREIGN KEY (status_id) REFERENCES public.enrollment_status(status_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: request student_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT student_id FOREIGN KEY (student_id) REFERENCES public."user"(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: session_student student_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session_student
    ADD CONSTRAINT student_id FOREIGN KEY (student_id) REFERENCES public."user"(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card subject_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT subject_id FOREIGN KEY (subject_id) REFERENCES public.subject(subject_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: session subject_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session
    ADD CONSTRAINT subject_id FOREIGN KEY (subject_id) REFERENCES public.subject(subject_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: service tutor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT tutor_id FOREIGN KEY (tutor_id) REFERENCES public."user"(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: session tutor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session
    ADD CONSTRAINT tutor_id FOREIGN KEY (tutor_id) REFERENCES public."user"(user_id) ON DELETE CASCADE;


--
-- Name: card_enroll user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_enroll
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES public."user"(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: card_rating user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card_rating
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES public."user"(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

