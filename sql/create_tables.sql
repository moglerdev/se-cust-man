ROLLBACK;
BEGIN;

create table account
(
    id               integer      not null CONSTRAINT user_pk PRIMARY KEY,
    username         varchar(255) not null CONSTRAINT user_u_username UNIQUE,
    email            varchar(255) not null CONSTRAINT user_u_email UNIQUE,
    hashed_password varchar(255) not null,
    name             varchar(255) not null
);

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
    id         integer      not null CONSTRAINT customer_pk PRIMARY KEY,
    name       varchar(255) not null,
    address_id integer      not null CONSTRAINT customer_fk_address REFERENCES address(id),
    email      varchar(255) not null,
    phone      varchar(255) not null
);

----------------------------------------------------------------------

create table history
(
    id         integer                 not null CONSTRAINT history_pk PRIMARY KEY,
    objectname varchar(255)            not null,
    objectid   integer                 not null,
    action     char
        constraint history_action_enum
            check (action IN ('N', 'S', 'D')),
    account_id integer                 not null CONSTRAINT history_fk_account REFERENCES account(id),
    time_stamp timestamp default now() not null
);

----------------------------------------------------------------------

create table history_change
(
    id         integer      not null
        constraint history_change_pk
            primary key,
    history_id integer      not null
        constraint history_change_fk_history
            references history(id),
    field_name varchar(255) not null,
    old_value  text         not null
);

----------------------------------------------------------------------

create table project
(
    id          integer                                     not null CONSTRAINT project_pk PRIMARY KEY,
    title       varchar(255)                                not null,
    description varchar(1024) default '' not null,
    deadline    date          default now()                 not null,
    customer_id integer CONSTRAINT project_fk_customer REFERENCES customer(id)
);

----------------------------------------------------------------------

create table task
(
    id             integer                                     not null CONSTRAINT task_pk PRIMARY KEY,
    title          varchar(255)                                not null,
    description    varchar(1024) default ''  not null,
    project_id     integer
        constraint task_fk_project
            references project (id),
    estimated_time double precision                            not null,
    required_time  double precision
);

COMMIT;
END;