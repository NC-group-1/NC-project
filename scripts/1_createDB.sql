CREATE TABLE public.usr
(
    user_id serial PRIMARY KEY,
    name character varying(30),
    surname character varying(30),
    email character varying(255) UNIQUE NOT NULL,
    role character varying(10) NOT NULL,
    activated boolean NOT NULL DEFAULT true,
    image_link character varying(2000),
    pass character varying(255),
    reg_date timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    about_me text,
    email_code character varying(255),
    code_expire_date timestamp with time zone
);

CREATE TABLE public.parameter_key
(
    parameter_key_id serial PRIMARY KEY,
    key character varying(127) UNIQUE NOT NULL
);

CREATE TABLE public.project
(
    project_id serial PRIMARY KEY,
    name character varying(255) NOT NULL,
    link character varying(2000) NOT NULL,
    date timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activated boolean NOT NULL DEFAULT true,
    user_id integer REFERENCES usr NOT NULL
);

CREATE TABLE public.action
(
    action_id serial PRIMARY KEY,
    name character varying(127) NOT NULL,
    description text,
    type character varying(30) NOT NULL,
    parameter_key_id integer REFERENCES parameter_key
);

CREATE TABLE public.data_set
(
    data_set_id serial PRIMARY KEY,
    name character varying(127) NOT NULL,
    description text,
    user_id integer REFERENCES usr NOT NULL
);
CREATE TABLE public.compound_action
(
    action_id integer REFERENCES action ON DELETE CASCADE NOT NULL,
    compound_id integer REFERENCES action ON DELETE CASCADE NOT NULL,
    order_num integer NOT NULL,
    parameter_key_id integer REFERENCES parameter_key
);

CREATE TABLE public.parameter
(
    parameter_id serial PRIMARY KEY,
    value character varying(255) NOT NULL,
    activated boolean NOT NULL DEFAULT false,
    parameter_key_id integer REFERENCES parameter_key NOT NULL,
    data_set_id integer REFERENCES data_set NOT NULL
);


CREATE TABLE public.test_scenario
(
    test_scenario_id serial PRIMARY KEY,
    name character varying(127) NOT NULL,
    description text,
    user_id integer REFERENCES usr NOT NULL,
    project_id integer REFERENCES project NOT NULL
);

CREATE TABLE public.test_sc_action
(
    test_sc_action_id serial PRIMARY KEY,
    action_id integer REFERENCES action NOT NULL,
    test_scenario_id integer REFERENCES test_scenario NOT NULL,
    order_num integer NOT NULL
);

CREATE TABLE public.test_case
(
    test_case_id serial PRIMARY KEY,
    parent_test_case_id integer REFERENCES test_case,
    name character varying(127) NOT NULL,
    creation_date timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    start_date timestamp with time zone,
    finish_date timestamp with time zone,
    status character varying(30) NOT NULL,
    description text NOT NULL,
    recurring_time interval,
    iterations_amount integer DEFAULT 0,
    project_id integer REFERENCES project NOT NULL,
    creator_id integer REFERENCES usr NOT NULL,
    starter_id integer REFERENCES usr,
    test_scenario_id integer REFERENCES test_scenario NOT NULL
);


CREATE TABLE public.action_inst
(
    action_inst_id serial PRIMARY KEY,
    action_id integer REFERENCES action NOT NULL,
    compound_id integer REFERENCES action,
    test_case_id integer REFERENCES test_case NOT NULL,
    data_set_id integer REFERENCES data_set,
    parameter_key_id integer REFERENCES parameter_key,
    status character varying(30) NOT NULL,
    order_num integer NOT NULL
);

CREATE TABLE public.notification
(
    notification_id serial PRIMARY KEY,
    test_case_id integer REFERENCES test_case NOT NULL,
    date timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    type character varying(10) NOT NULL
);

CREATE TABLE public.watcher
(
    user_id integer REFERENCES usr NOT NULL,
    test_case_id integer REFERENCES test_case NOT NULL
);

CREATE TABLE public.is_read
(
    user_id integer REFERENCES usr NOT NULL,
    notification_id integer REFERENCES notification NOT NULL,
    is_read boolean NOT NULL DEFAULT false
);

