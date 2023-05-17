ROLLBACK;
BEGIN;
create table account
(
    id               integer      not null,
    username         varchar(255) not null,
    email            varchar(255) not null,
    hashed_password varchar(255) not null,
    name             varchar(255) not null,
    constraint user_pk
        primary key (),
    constraint user_username_unique
        unique (),
    constraint user_email_unique
        unique ()
);

create unique index user_pk
    on account (id);

create unique index user_username_unique
    on account (username);

create unique index user_email_unique
    on account (email);


----------------------------------------------------------------------


create table address
(
    id       integer      not null
        constraint address_pk
            primary key,
    street   varchar(255) not null,
    zip      varchar(16)  not null,
    city     varchar(255) not null,
    iso_code varchar(8)   not null
);


----------------------------------------------------------------------


create table customer
(
    id         integer      not null,
    name       varchar(255) not null,
    address_id integer      not null,
    email      varchar(255) not null,
    phone      varchar(255) not null,
    constraint customer_pk
        primary key ()
);

create unique index customer_pk
    on customer (id);


----------------------------------------------------------------------


create table history
(
    id         integer                 not null,
    objectname varchar(255)            not null,
    objectid   integer                 not null,
    action     char
        constraint history_action_enum
            check (action = ANY (ARRAY ['N'::bpchar, 'S'::bpchar, 'D'::bpchar])),
    account_id integer                 not null,
    time_stamp timestamp default now() not null,
    constraint history_pk
        primary key ()
);

create unique index history_pk
    on history (id);


----------------------------------------------------------------------


create table history_change
(
    id         integer      not null
        constraint history_change_pk
            primary key,
    history_id integer      not null
        constraint history_change_fk_history
            references ??? (),
    field_name varchar(255) not null,
    old_value  text         not null
);


----------------------------------------------------------------------


create table project
(
    id          integer                                     not null,
    title       varchar(255)                                not null,
    description varchar(1024) default ''::character varying not null,
    deadline    date          default now()                 not null,
    customer_id integer,
    constraint project_pk
        primary key ()
);

create unique index project_pk
    on project (id);


----------------------------------------------------------------------


create table task
(
    id             integer                                     not null,
    title          varchar(255)                                not null,
    description    varchar(1024) default ''::character varying not null,
    project_id     integer
        constraint task_fk_project
            references project (id),
    estimated_time double precision                            not null,
    required_time  double precision
);

create unique index task_pk
    on task (id);


COMMIT;
END;