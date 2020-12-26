CREATE TABLE public.usr
(
    user_id serial PRIMARY KEY,
    name character varying(30) DEFAULT 'DefaultName',
    surname character varying(30) DEFAULT 'DefaultSurname',
    email character varying(255) UNIQUE NOT NULL,
    role character varying(15) NOT NULL,
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
    parameter_key_id integer REFERENCES parameter_key NOT NULL,
    data_set_id integer REFERENCES data_set ON DELETE CASCADE NOT NULL
);


CREATE TABLE public.test_scenario
(
    test_scenario_id serial PRIMARY KEY,
    name character varying(127) NOT NULL,
    description text,
    activated boolean NOT NULL DEFAULT true,
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
    creator_id integer REFERENCES usr NOT NULL,
    starter_id integer REFERENCES usr,
    test_scenario_id integer REFERENCES test_scenario NOT NULL
);


CREATE TABLE public.action_inst
(
    action_inst_id serial PRIMARY KEY,
    action_id integer REFERENCES action NOT NULL,
    compound_id integer REFERENCES action,
    result character varying(2000),
    test_case_id integer REFERENCES test_case ON DELETE CASCADE NULL,
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

INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (11, 'Anna', 'Savchuk', 'savchukanna16@gmail.com', 'ROLE_ENGINEER', true, 'https://ca.slack-edge.com/T01EE9RQ39T-U01EPJ1931A-20771af6e37f-512', '$2a$10$Yk68g17Pc554qd9ORtlsTek/dyqfqdl2vlPUtnFCYGvE3P0Iu/Ceq', '2020-12-25 13:37:36.011433', null, '8cec7a47-e3a8-4cc0-b9e8-6eb6db739391', '2020-12-25 14:37:36.597000');
INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (10, 'Danya', 'Kotlov', 'quantum13man@gmail.com', 'ROLE_ENGINEER', true, 'https://ca.slack-edge.com/T01EE9RQ39T-U01EHKBSGKD-6e8a33949323-512', '$2a$10$mMy6khquell60OPIr6oDcOe0B79DaOd2g.Y9lus.xOLkmchKOdwpa', '2020-12-25 13:37:09.341401', null, '8be3c981-76cf-4955-87ad-ea2938c7e834', '2020-12-25 14:37:09.933000');
INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (7, 'Ivan', 'Ivanishyn', 'i.v.ivanishyn@gmail.com', 'ROLE_ENGINEER', true, 'https://ca.slack-edge.com/T01EE9RQ39T-U01EHKBURSP-ee292d224472-512', '$2a$10$hXjWcILnVzzTIgNlVtH61uXXovadFmLpaBm6oA1xHXCRndQthCmSi', '2020-12-25 13:34:25.608304', null, 'b052ab63-8ae8-4d89-81a8-7f47ff290f72', '2020-12-25 14:34:26.218000');
INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (1, 'King', 'Powerful', 'admin@gmail.com', 'ROLE_ADMIN', true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlUPUys_nRzLac_pRDMzZt8dXWFFc0lDNCPw&usqp=CAU', '$2a$10$2KvhC0uIf.wQd2TVqmlSzO05QugllZfx.VOYycKtDGTnfLGVCh.W.', '2001-10-05 00:00:00.000000', 'pass: adminadmin', null, null);
INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (4, 'Oleksandr', 'Kozin', 'manager1@gmail.com', 'ROLE_MANAGER', true, 'https://ca.slack-edge.com/T01EE9RQ39T-U01EPH4AS68-9415eac6117a-512', '$2a$10$2KvhC0uIf.wQd2TVqmlSzO05QugllZfx.VOYycKtDGTnfLGVCh.W.', '2020-12-22 06:47:38.882840', 'pass: adminadmin', null, null);
INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (5, 'Gleb', 'Dory', 'manager2@gmail.com', 'ROLE_MANAGER', true, 'https://static.wikia.nocookie.net/disney/images/3/36/Profile_-_Dory.png/revision/latest?cb=20190316200551', '$2a$10$2KvhC0uIf.wQd2TVqmlSzO05QugllZfx.VOYycKtDGTnfLGVCh.W.', '2020-12-22 06:47:39.022442', 'pass: adminadmin', 'd798d61c-ef75-47ac-9463-dcc43dc97a21', '2020-12-25 14:30:26.685000');
INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (6, 'Viacheslav', 'Demianenko', 'slavadem100500@gmail.com', 'ROLE_ENGINEER', true, 'https://ca.slack-edge.com/T01EE9RQ39T-U01EAM5NJKG-4b0d3d74b642-192', '$2a$10$w5yZw6QvT1wbIw87dd/M1eW7CaJ.hADvlNI1QVgA9cXY.8C0uUhim', '2020-12-25 13:32:35.401579', 'pass: adminadmin', 'f1a2e16d-0ba6-48cb-a1fe-5f9926aa9491', '2020-12-25 15:07:47.983000');
INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (9, 'Valeria', 'Demenkova', 'lera145dem@gmail.com', 'ROLE_ENGINEER', true, null, '$2a$10$GU/HQ4Ink0Ndc4ZrAXovRuHCqSTi.rT.UmNJtf05yXSa6.WeWq6MG', '2020-12-25 13:36:50.700588', null, '37d87dc0-6533-4f57-a4b4-e1b98f5fc577', '2020-12-25 14:36:51.295000');
INSERT INTO public.usr (user_id, name, surname, email, role, activated, image_link, pass, reg_date, about_me, email_code, code_expire_date) VALUES (8, 'Eugene', 'Chmutov', 'garret.evg@gmail.com', 'ROLE_ENGINEER', true, 'https://ca.slack-edge.com/T01EE9RQ39T-U01EEANKFPX-b48f409536a9-512', '$2a$10$vbNJ5VEB0JAudHbHwNruIOsxGTkCgLwb0g3cLQwbpNm11ewGsM3CO', '2020-12-25 13:36:33.177548', null, '0b0260c7-317e-4865-b886-da7ff31ae84b', '2020-12-25 14:36:33.735000');

INSERT INTO public.project (project_id, name, link, date, activated, user_id) VALUES (1, 'Wikipedia', 'https://en.wikipedia.org', '2020-12-22 08:21:59.865109', true, 1);
INSERT INTO public.project (project_id, name, link, date, activated, user_id) VALUES (2, 'Yahoo', 'https://www.yahoo.com', '2020-12-25 14:11:39.362053', true, 6);
INSERT INTO public.project (project_id, name, link, date, activated, user_id) VALUES (3, 'Citrus store', 'https://www.citrus.ua', '2020-12-26 10:03:48.746152', true, 6);

INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (4, 'wiki test string 1');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (5, 'wiki test string 2');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (6, 'context var 1');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (7, 'context var 2');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (8, 'context var 3');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (9, 'context var 4');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (10, 'context var 5');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (11, 'context var 6');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (12, 'context var 7');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (13, 'context var 8');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (14, 'context var 9');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (15, 'context var 10');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (2, 'wiki search button id');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (1, 'wiki search field id');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (3, 'wiki search result heading id');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (33, 'wiki test string 3');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (34, 'wiki test string 4');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (35, 'wiki test string 5');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (36, 'wiki test string 6');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (38, 'yahoo search field id');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (39, 'yahoo search button Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (44, 'Citrus search button Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (42, 'citrus search field id');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (49, 'citrus product price on cart Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (63, 'citrus product name 1');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (47, 'citrus product price on product page Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (46, 'citrus first product link Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (45, 'citrus first product price Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (69, 'citrus product price in order Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (67, 'citrus buy button on cart popup Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (48, 'citrus buy button on product page Xpath');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (74, 'citrus product name 2');
INSERT INTO public.parameter_key (parameter_key_id, key) VALUES (77, 'citrus product name 3');

INSERT INTO public.data_set (data_set_id, name, description, user_id) VALUES (1, 'wiki main page elements selectors', 'dataset of ids or other selectors to locate elements in wikipedia main page', 1);
INSERT INTO public.data_set (data_set_id, name, description, user_id) VALUES (2, 'wiki search input strings', 'data set with some test search requests', 1);
INSERT INTO public.data_set (data_set_id, name, description, user_id) VALUES (3, 'context data set', 'system data set for context keys', 1);
INSERT INTO public.data_set (data_set_id, name, description, user_id) VALUES (6, 'yahoo main page elements selectors', 'element selectors on yahoo main page', 6);
INSERT INTO public.data_set (data_set_id, name, description, user_id) VALUES (7, 'Citrus element selectors data set', 'element selectors of citrus project', 6);
INSERT INTO public.data_set (data_set_id, name, description, user_id) VALUES (8, 'citrus product search strings', 'data set with product names for citrus store', 6);

INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (1, 'searchInput', 1, 1);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (2, 'searchButton', 2, 1);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (3, 'firstHeading', 3, 1);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (4, 'Happiness', 4, 2);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (5, 'Java', 5, 2);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (6, 'context var 1', 6, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (7, 'context var 2', 7, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (8, 'context var 3', 8, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (9, 'context var 4', 9, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (10, 'context var 5', 10, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (11, 'context var 6', 11, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (12, 'context var 7', 12, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (13, 'context var 8', 13, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (14, 'context var 9', 14, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (16, 'context var 10', 15, 3);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (17, 'Coronavirus', 33, 2);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (18, 'Sleep', 34, 2);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (19, 'Netcracker Technology', 35, 2);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (20, 'Fail', 36, 2);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (22, 'ybar-sbq', 38, 6);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (23, '//*[@id="ybar-sf"]/input[2]', 39, 6);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (24, 'search-input', 42, 7);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (25, '//*[@id="header"]/div[3]/div/div/div[3]/form/button[3]', 44, 7);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (26, '//*[@id="__layout"]/div/section/div[1]/div[2]/div[2]/div[1]/div[2]/div/div[1]/div/div/div[1]/div[5]/div/div[2]/span', 45, 7);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (27, '//*[@id="__layout"]/div/section/div[1]/div[2]/div[2]/div[1]/div[2]/div/div[1]/div/div/div[1]/div[1]/a/span', 46, 7);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (28, '//*[@id="buy-block"]/div[3]/div[1]/div/div[2]/span/span', 47, 7);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (29, '//*[@id="buy-block"]/div[3]/div[1]/button', 48, 7);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (31, 'Apple MacBook Pro', 63, 8);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (32, '//*[@id="__layout"]/div/div[2]/div/div[2]/div/div/div/div[2]/div[2]/a', 67, 7);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (33, '/html/body/div[1]/div[2]/section/div/div[2]/div[3]/div/div[2]/div[7]/div/div/div[3]/span/span[1]', 69, 7);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (34, 'OnePlus 8T', 74, 8);
INSERT INTO public.parameter (parameter_id, value, parameter_key_id, data_set_id) VALUES (35, 'Apple iPhone 12', 77, 8);

INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (1, 'Click', 'simple click on element', 'CLICK', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (2, 'Send text', 'send text into some input', 'SEND_KEYS', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (3, 'Wiki find search input field by id', 'find wiki search input', 'FIND_ELEMENT_BY_ID', 1);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (4, 'Wiki find search button by id', 'Find search button in wiki project', 'FIND_ELEMENT_BY_ID', 2);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (5, 'Wiki find search result heading by id', 'find result heading to test if wiki searched correctly', 'FIND_ELEMENT_BY_ID', 3);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (7, 'Compare previous value with string', 'Compares previous context value with given string', 'COMPARE_WITH_STRING', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (8, 'wiki search compound', 'compound that contains actions to search some in wikipedia', 'COMPOUND', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (9, 'wiki compare compound', 'check if header of search result contains what we searched', 'COMPOUND', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (10, 'Yahoo find search input field by id', 'find search input in yahoo project', 'FIND_ELEMENT_BY_ID', 38);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (11, 'Yahoo find search button by Xpath', 'find search button on yahoo by xpath', 'FIND_ELEMENT_BY_XPATH', 39);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (12, 'Citrus find search input field by id', 'find search input field on citrus main page', 'FIND_ELEMENT_BY_ID', 42);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (13, 'citrus find search button by Xpath', 'find search button on citrus main page', 'FIND_ELEMENT_BY_XPATH', 44);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (14, 'citrus find product price in search results', 'find first product price in results page', 'FIND_ELEMENT_BY_XPATH', 45);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (15, 'find first product link in search results', 'find link to first product in results page', 'FIND_ELEMENT_BY_XPATH', 46);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (16, 'citrus find product price on page with product', 'find product price on page with product details', 'FIND_ELEMENT_BY_XPATH', 47);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (17, 'citrus find buy button on product page', 'find big buy button on product page', 'FIND_ELEMENT_BY_XPATH', 48);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (19, 'Citrus search compound', 'search in citrus store compound', 'COMPOUND', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (23, 'citrus compare prices after search', 'compare prices on search results and product page', 'COMPOUND', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (24, 'citrus find buy button on cart popup', 'find button to complete order', 'FIND_ELEMENT_BY_XPATH', 67);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (25, 'Switch tab', 'simple switching to next tab', 'SWITCH_TAB', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (26, 'scroll page to end', 'simple scrolling page down to end', 'SCROLL_PAGE_TO_END', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (27, 'citrus find product price in order page', 'find product price in order page', 'FIND_ELEMENT_BY_XPATH', 69);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (28, 'Citrus create order and compare price', 'order product and compare price in receipt', 'COMPOUND', null);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (20, 'Compare previous value with context value 1', 'compare previous save to context value with context value 1', 'COMPARE_WITH_CONTEXT_VALUE', 6);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (6, 'Save element text to context 1', 'Retrieves element text and saves it to context 1', 'SAVE_ELEMENT_TEXT_TO_CONTEXT', 6);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (29, 'Save element text to context 2', 'Retrieves element text and saves it to context 2', 'SAVE_ELEMENT_TEXT_TO_CONTEXT', 7);
INSERT INTO public.action (action_id, name, description, type, parameter_key_id) VALUES (30, 'Save element text to context 3', 'Retrieves element text and saves it to context 3', 'SAVE_ELEMENT_TEXT_TO_CONTEXT', 8);

INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (3, 8, 1, 1);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (1, 8, 2, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (2, 8, 3, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (4, 8, 4, 2);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (1, 8, 5, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (5, 9, 1, 3);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (6, 9, 2, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (7, 9, 3, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (12, 19, 1, 42);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (1, 19, 2, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (2, 19, 3, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (13, 19, 4, 44);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (1, 19, 5, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (14, 23, 1, 45);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (6, 23, 2, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (15, 23, 3, 46);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (1, 23, 4, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (16, 23, 5, 47);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (20, 23, 7, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (17, 28, 1, 48);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (1, 28, 2, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (24, 28, 3, 67);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (1, 28, 4, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (25, 28, 5, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (26, 28, 6, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (27, 28, 7, 69);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (20, 28, 9, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (29, 23, 6, null);
INSERT INTO public.compound_action (action_id, compound_id, order_num, parameter_key_id) VALUES (30, 28, 8, null);

INSERT INTO public.test_scenario (test_scenario_id, name, description, activated, user_id, project_id) VALUES (1, 'Wiki search and check one time', 'performs a search in wikipedia and compares result with search request', true, 1, 1);
INSERT INTO public.test_scenario (test_scenario_id, name, description, activated, user_id, project_id) VALUES (2, 'Wiki search and check 5 times', 'complex scenario that tests multiple search requests', true, 1, 1);
INSERT INTO public.test_scenario (test_scenario_id, name, description, activated, user_id, project_id) VALUES (3, 'Yahoo scenario with search', 'simple test scenario for test xpath selector', true, 6, 2);
INSERT INTO public.test_scenario (test_scenario_id, name, description, activated, user_id, project_id) VALUES (5, 'Comparing prices in Citrus store', 'Compares product prices in different stages of ordering', true, 6, 3);

INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (1, 8, 1, 1);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (2, 9, 1, 2);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (3, 8, 2, 1);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (4, 9, 2, 2);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (5, 8, 2, 3);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (6, 9, 2, 4);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (7, 8, 2, 5);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (8, 9, 2, 6);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (9, 8, 2, 7);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (10, 9, 2, 8);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (11, 8, 2, 9);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (12, 9, 2, 10);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (13, 10, 3, 1);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (14, 1, 3, 2);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (15, 2, 3, 3);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (16, 11, 3, 4);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (17, 1, 3, 5);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (21, 19, 5, 1);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (22, 23, 5, 2);
INSERT INTO public.test_sc_action (test_sc_action_id, action_id, test_scenario_id, order_num) VALUES (23, 28, 5, 3);

INSERT INTO public.test_case (test_case_id, parent_test_case_id, name, creation_date, start_date, finish_date, status, description, recurring_time, iterations_amount, creator_id, starter_id, test_scenario_id) VALUES (6, null, 'Yahoo simple test case', '2020-12-25 14:31:14.434000', null, null, 'READY', 'simple test case for testing xpath selector', null, null, 6, null, 3);
INSERT INTO public.test_case (test_case_id, parent_test_case_id, name, creation_date, start_date, finish_date, status, description, recurring_time, iterations_amount, creator_id, starter_id, test_scenario_id) VALUES (5, null, 'complex test case that will fali', '2020-12-22 14:18:08.718000', null, null, 'READY', 'failed test case', null, null, 1, null, 2);
INSERT INTO public.test_case (test_case_id, parent_test_case_id, name, creation_date, start_date, finish_date, status, description, recurring_time, iterations_amount, creator_id, starter_id, test_scenario_id) VALUES (2, null, 'Test case 1 with simple data', '2020-12-22 08:43:51.356000', null, null, 'READY', 'try to find some simple word', null, null, 1, null, 1);
INSERT INTO public.test_case (test_case_id, parent_test_case_id, name, creation_date, start_date, finish_date, status, description, recurring_time, iterations_amount, creator_id, starter_id, test_scenario_id) VALUES (3, null, 'Test case 2 with another data', '2020-12-22 13:29:21.328000', null, null, 'READY', 'try scenario with another parameter', null, null, 1, null, 1);
INSERT INTO public.test_case (test_case_id, parent_test_case_id, name, creation_date, start_date, finish_date, status, description, recurring_time, iterations_amount, creator_id, starter_id, test_scenario_id) VALUES (12, null, 'Test Apple iPhone 12 price', '2020-12-26 14:55:12.274000', null, null, 'READY', 'test price of Apple iPhone 12 in different places', null, null, 6, null, 5);
INSERT INTO public.test_case (test_case_id, parent_test_case_id, name, creation_date, start_date, finish_date, status, description, recurring_time, iterations_amount, creator_id, starter_id, test_scenario_id) VALUES (10, null, 'Test MacBook Pro price', '2020-12-26 14:04:56.644000', null, null, 'READY', 'price comparing scenario on MacBook Pro', null, null, 6, null, 5);
INSERT INTO public.test_case (test_case_id, parent_test_case_id, name, creation_date, start_date, finish_date, status, description, recurring_time, iterations_amount, creator_id, starter_id, test_scenario_id) VALUES (11, null, 'Test OnePlus 8T price', '2020-12-26 14:29:12.317000', null, null, 'READY', 'price comparing scenario with OnePlus 8T', null, null, 6, null, 5);
INSERT INTO public.test_case (test_case_id, parent_test_case_id, name, creation_date, start_date, finish_date, status, description, recurring_time, iterations_amount, creator_id, starter_id, test_scenario_id) VALUES (4, null, 'complex test case with 5 searches', '2020-12-22 14:14:24.902000', null, null, 'READY', 'test case with 5 different input strings', null, null, 1, null, 2);

INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (98, 1, null, 'null', 6, null, null, 'UNKNOWN', 2);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (202, 15, 23, 'null', 12, 7, 46, 'UNKNOWN', 8);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (203, 1, 23, 'null', 12, null, null, 'UNKNOWN', 9);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (200, 14, 23, 'null', 12, 7, 45, 'UNKNOWN', 6);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (99, 2, null, 'null', 6, 2, 4, 'UNKNOWN', 3);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (97, 10, null, 'null', 6, 6, 38, 'UNKNOWN', 1);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (100, 11, null, 'null', 6, 6, 39, 'UNKNOWN', 4);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (101, 1, null, 'null', 6, null, null, 'UNKNOWN', 5);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (201, 6, 23, 'null', 12, 3, 6, 'UNKNOWN', 7);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (206, 20, 23, 'null', 12, 3, 6, 'UNKNOWN', 12);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (204, 16, 23, 'null', 12, 7, 47, 'UNKNOWN', 10);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (205, 29, 23, 'null', 12, 3, 7, 'UNKNOWN', 11);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (195, 12, 19, 'null', 12, 7, 42, 'UNKNOWN', 1);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (198, 13, 19, 'null', 12, 7, 44, 'UNKNOWN', 4);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (199, 1, 19, 'null', 12, null, null, 'UNKNOWN', 5);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (196, 1, 19, 'null', 12, null, null, 'UNKNOWN', 2);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (197, 2, 19, 'null', 12, 8, 77, 'UNKNOWN', 3);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (170, 26, 28, 'null', 10, null, null, 'UNKNOWN', 18);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (171, 27, 28, 'null', 10, 7, 69, 'UNKNOWN', 19);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (168, 1, 28, 'null', 10, null, null, 'UNKNOWN', 16);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (169, 25, 28, 'null', 10, null, null, 'UNKNOWN', 17);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (172, 6, 28, 'null', 10, 3, 8, 'UNKNOWN', 20);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (173, 20, 28, 'null', 10, 3, 6, 'UNKNOWN', 21);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (162, 16, 23, 'null', 10, 7, 47, 'UNKNOWN', 10);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (163, 6, 23, 'null', 10, 3, 7, 'UNKNOWN', 11);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (160, 15, 23, 'null', 10, 7, 46, 'UNKNOWN', 8);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (161, 1, 23, 'null', 10, null, null, 'UNKNOWN', 9);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (210, 1, 28, 'null', 12, null, null, 'UNKNOWN', 16);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (211, 25, 28, 'null', 12, null, null, 'UNKNOWN', 17);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (208, 1, 28, 'null', 12, null, null, 'UNKNOWN', 14);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (209, 24, 28, 'null', 12, 7, 67, 'UNKNOWN', 15);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (214, 30, 28, 'null', 12, 3, 8, 'UNKNOWN', 20);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (215, 20, 28, 'null', 12, 3, 6, 'UNKNOWN', 21);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (212, 26, 28, 'null', 12, null, null, 'UNKNOWN', 18);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (213, 27, 28, 'null', 12, 7, 69, 'UNKNOWN', 19);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (207, 17, 28, 'null', 12, 7, 48, 'UNKNOWN', 13);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (194, 20, 28, 'null', 11, 3, 6, 'UNKNOWN', 21);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (192, 27, 28, 'null', 11, 7, 69, 'UNKNOWN', 19);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (193, 6, 28, 'null', 11, 3, 8, 'UNKNOWN', 20);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (167, 24, 28, 'null', 10, 7, 67, 'UNKNOWN', 15);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (164, 20, 23, 'null', 10, 3, 6, 'UNKNOWN', 12);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (165, 17, 28, 'null', 10, 7, 48, 'UNKNOWN', 13);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (154, 1, 19, 'null', 10, null, null, 'UNKNOWN', 2);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (155, 2, 19, 'null', 10, 8, 63, 'UNKNOWN', 3);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (153, 12, 19, 'null', 10, 7, 42, 'UNKNOWN', 1);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (158, 14, 23, 'null', 10, 7, 45, 'UNKNOWN', 6);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (159, 6, 23, 'null', 10, 3, 6, 'UNKNOWN', 7);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (156, 13, 19, 'null', 10, 7, 44, 'UNKNOWN', 4);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (157, 1, 19, 'null', 10, null, null, 'UNKNOWN', 5);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (96, 7, 9, 'null', 5, 2, 36, 'UNKNOWN', 40);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (90, 1, 8, 'null', 5, null, null, 'UNKNOWN', 34);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (91, 2, 8, 'null', 5, 2, 36, 'UNKNOWN', 35);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (88, 7, 9, 'null', 5, 2, 34, 'UNKNOWN', 32);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (89, 3, 8, 'null', 5, 1, 1, 'UNKNOWN', 33);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (94, 5, 9, 'null', 5, 1, 3, 'UNKNOWN', 38);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (95, 6, 9, 'null', 5, null, null, 'UNKNOWN', 39);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (92, 4, 8, 'null', 5, 1, 2, 'UNKNOWN', 36);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (93, 1, 8, 'null', 5, null, null, 'UNKNOWN', 37);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (86, 5, 9, 'null', 5, 1, 3, 'UNKNOWN', 30);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (87, 6, 9, 'null', 5, null, null, 'UNKNOWN', 31);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (79, 6, 9, 'null', 5, null, null, 'UNKNOWN', 23);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (76, 4, 8, 'null', 5, 1, 2, 'UNKNOWN', 20);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (56, 7, 9, 'null', 4, 2, 35, 'UNKNOWN', 40);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (62, 5, 9, 'null', 5, 1, 3, 'UNKNOWN', 6);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (63, 6, 9, 'null', 5, null, null, 'UNKNOWN', 7);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (60, 4, 8, 'null', 5, 1, 2, 'UNKNOWN', 4);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (61, 1, 8, 'null', 5, null, null, 'UNKNOWN', 5);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (50, 1, 8, 'null', 4, null, null, 'UNKNOWN', 34);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (51, 2, 8, 'null', 4, 2, 35, 'UNKNOWN', 35);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (48, 7, 9, 'null', 4, 2, 34, 'UNKNOWN', 32);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (186, 17, 28, 'null', 11, 7, 48, 'UNKNOWN', 13);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (187, 1, 28, 'null', 11, null, null, 'UNKNOWN', 14);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (184, 6, 23, 'null', 11, 3, 7, 'UNKNOWN', 11);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (185, 20, 23, 'null', 11, 3, 6, 'UNKNOWN', 12);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (190, 25, 28, 'null', 11, null, null, 'UNKNOWN', 17);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (191, 26, 28, 'null', 11, null, null, 'UNKNOWN', 18);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (188, 24, 28, 'null', 11, 7, 67, 'UNKNOWN', 15);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (189, 1, 28, 'null', 11, null, null, 'UNKNOWN', 16);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (178, 1, 19, 'null', 11, null, null, 'UNKNOWN', 5);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (179, 14, 23, 'null', 11, 7, 45, 'UNKNOWN', 6);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (176, 2, 19, 'null', 11, 8, 74, 'UNKNOWN', 3);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (177, 13, 19, 'null', 11, 7, 44, 'UNKNOWN', 4);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (182, 1, 23, 'null', 11, null, null, 'UNKNOWN', 9);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (183, 16, 23, 'null', 11, 7, 47, 'UNKNOWN', 10);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (180, 6, 23, 'null', 11, 3, 6, 'UNKNOWN', 7);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (181, 15, 23, 'null', 11, 7, 46, 'UNKNOWN', 8);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (174, 12, 19, 'null', 11, 7, 42, 'UNKNOWN', 1);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (175, 1, 19, 'null', 11, null, null, 'UNKNOWN', 2);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (166, 1, 28, 'null', 10, null, null, 'UNKNOWN', 14);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (82, 1, 8, 'null', 5, null, null, 'UNKNOWN', 26);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (83, 2, 8, 'null', 5, 2, 34, 'UNKNOWN', 27);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (80, 7, 9, 'null', 5, 2, 33, 'UNKNOWN', 24);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (81, 3, 8, 'null', 5, 1, 1, 'UNKNOWN', 25);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (84, 4, 8, 'null', 5, 1, 2, 'UNKNOWN', 28);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (85, 1, 8, 'null', 5, null, null, 'UNKNOWN', 29);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (74, 1, 8, 'null', 5, null, null, 'UNKNOWN', 18);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (75, 2, 8, 'null', 5, 2, 33, 'UNKNOWN', 19);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (72, 7, 9, 'null', 5, 2, 5, 'UNKNOWN', 16);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (73, 3, 8, 'null', 5, 1, 1, 'UNKNOWN', 17);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (78, 5, 9, 'null', 5, 1, 3, 'UNKNOWN', 22);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (77, 1, 8, 'null', 5, null, null, 'UNKNOWN', 21);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (66, 1, 8, 'null', 5, null, null, 'UNKNOWN', 10);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (67, 2, 8, 'null', 5, 2, 5, 'UNKNOWN', 11);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (64, 7, 9, 'null', 5, 2, 4, 'UNKNOWN', 8);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (65, 3, 8, 'null', 5, 1, 1, 'UNKNOWN', 9);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (70, 5, 9, 'null', 5, 1, 3, 'UNKNOWN', 14);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (71, 6, 9, 'null', 5, null, null, 'UNKNOWN', 15);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (68, 4, 8, 'null', 5, 1, 2, 'UNKNOWN', 12);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (69, 1, 8, 'null', 5, null, null, 'UNKNOWN', 13);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (58, 1, 8, 'null', 5, null, null, 'UNKNOWN', 2);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (59, 2, 8, 'null', 5, 2, 4, 'UNKNOWN', 3);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (57, 3, 8, 'null', 5, 1, 1, 'UNKNOWN', 1);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (49, 3, 8, 'null', 4, 1, 1, 'UNKNOWN', 33);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (54, 5, 9, 'null', 4, 1, 3, 'UNKNOWN', 38);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (55, 6, 9, 'null', 4, null, null, 'UNKNOWN', 39);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (52, 4, 8, 'null', 4, 1, 2, 'UNKNOWN', 36);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (53, 1, 8, 'null', 4, null, null, 'UNKNOWN', 37);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (42, 1, 8, 'null', 4, null, null, 'UNKNOWN', 26);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (43, 2, 8, 'null', 4, 2, 34, 'UNKNOWN', 27);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (40, 7, 9, 'null', 4, 2, 33, 'UNKNOWN', 24);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (41, 3, 8, 'null', 4, 1, 1, 'UNKNOWN', 25);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (46, 5, 9, 'null', 4, 1, 3, 'UNKNOWN', 30);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (47, 6, 9, 'null', 4, null, null, 'UNKNOWN', 31);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (44, 4, 8, 'null', 4, 1, 2, 'UNKNOWN', 28);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (45, 1, 8, 'null', 4, null, null, 'UNKNOWN', 29);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (34, 1, 8, 'null', 4, null, null, 'UNKNOWN', 18);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (35, 2, 8, 'null', 4, 2, 33, 'UNKNOWN', 19);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (32, 7, 9, 'null', 4, 2, 5, 'UNKNOWN', 16);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (33, 3, 8, 'null', 4, 1, 1, 'UNKNOWN', 17);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (38, 5, 9, 'null', 4, 1, 3, 'UNKNOWN', 22);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (39, 6, 9, 'null', 4, null, null, 'UNKNOWN', 23);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (36, 4, 8, 'null', 4, 1, 2, 'UNKNOWN', 20);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (37, 1, 8, 'null', 4, null, null, 'UNKNOWN', 21);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (26, 1, 8, 'null', 4, null, null, 'UNKNOWN', 10);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (27, 2, 8, 'null', 4, 2, 5, 'UNKNOWN', 11);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (24, 7, 9, 'null', 4, 2, 4, 'UNKNOWN', 8);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (25, 3, 8, 'null', 4, 1, 1, 'UNKNOWN', 9);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (30, 5, 9, 'null', 4, 1, 3, 'UNKNOWN', 14);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (31, 6, 9, 'null', 4, null, null, 'UNKNOWN', 15);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (28, 4, 8, 'null', 4, 1, 2, 'UNKNOWN', 12);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (29, 1, 8, 'null', 4, null, null, 'UNKNOWN', 13);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (18, 1, 8, 'null', 4, null, null, 'UNKNOWN', 2);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (19, 2, 8, 'null', 4, 2, 4, 'UNKNOWN', 3);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (16, 7, 9, 'null', 3, 2, 5, 'UNKNOWN', 8);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (17, 3, 8, 'null', 4, 1, 1, 'UNKNOWN', 1);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (22, 5, 9, 'null', 4, 1, 3, 'UNKNOWN', 6);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (23, 6, 9, 'null', 4, null, null, 'UNKNOWN', 7);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (20, 4, 8, 'null', 4, 1, 2, 'UNKNOWN', 4);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (21, 1, 8, 'null', 4, null, null, 'UNKNOWN', 5);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (10, 1, 8, 'null', 3, null, null, 'UNKNOWN', 2);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (11, 2, 8, 'null', 3, 2, 5, 'UNKNOWN', 3);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (8, 7, 9, 'null', 2, 2, 4, 'UNKNOWN', 8);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (9, 3, 8, 'null', 3, 1, 1, 'UNKNOWN', 1);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (14, 5, 9, 'null', 3, 1, 3, 'UNKNOWN', 6);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (15, 6, 9, 'null', 3, null, null, 'UNKNOWN', 7);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (12, 4, 8, 'null', 3, 1, 2, 'UNKNOWN', 4);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (13, 1, 8, 'null', 3, null, null, 'UNKNOWN', 5);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (2, 1, 8, 'null', 2, null, null, 'UNKNOWN', 2);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (3, 2, 8, 'null', 2, 2, 4, 'UNKNOWN', 3);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (1, 3, 8, 'null', 2, 1, 1, 'UNKNOWN', 1);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (6, 5, 9, 'null', 2, 1, 3, 'UNKNOWN', 6);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (7, 6, 9, 'null', 2, null, null, 'UNKNOWN', 7);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (4, 4, 8, 'null', 2, 1, 2, 'UNKNOWN', 4);
INSERT INTO public.action_inst (action_inst_id, action_id, compound_id, result, test_case_id, data_set_id, parameter_key_id, status, order_num) VALUES (5, 1, 8, 'null', 2, null, null, 'UNKNOWN', 5);

INSERT INTO public.watcher (user_id, test_case_id) VALUES (1, 2);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (1, 4);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (1, 3);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (1, 5);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (1, 6);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (6, 6);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (6, 10);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (6, 11);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (6, 3);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (6, 5);
INSERT INTO public.watcher (user_id, test_case_id) VALUES (6, 12);

ALTER SEQUENCE action_action_id_seq RESTART WITH 100;
ALTER SEQUENCE action_inst_action_inst_id_seq RESTART WITH 1000;
ALTER SEQUENCE data_set_data_set_id_seq RESTART WITH 100;
ALTER SEQUENCE parameter_key_parameter_key_id_seq RESTART WITH 100;
ALTER SEQUENCE parameter_parameter_id_seq RESTART WITH 100;
ALTER SEQUENCE project_project_id_seq RESTART WITH 100;
ALTER SEQUENCE test_case_test_case_id_seq RESTART WITH 100;
ALTER SEQUENCE test_sc_action_test_sc_action_id_seq RESTART WITH 100;
ALTER SEQUENCE test_scenario_test_scenario_id_seq RESTART WITH 100;
ALTER SEQUENCE usr_user_id_seq RESTART WITH 100;

