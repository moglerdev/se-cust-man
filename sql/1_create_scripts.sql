create table customer
(
    id       serial
        constraint customer_pk
            primary key,
    name     varchar(255)      not null,
    email    varchar(255)      not null
        constraint customer_pk2
            unique,
    phone    varchar(255)      not null
        constraint customer_pk3
            unique,
    address  varchar(255)      not null,
    _deleted integer default 0 not null
);
alter table customer
    owner to scm;

create table project
(
    id          serial
        constraint project_pk
            primary key,
    customer_id integer           not null,
    title       varchar(255)      not null,
    description text,
    _deleted    integer default 0 not null
);
alter table customer
    owner to scm;

create table task
(
    id          serial
        constraint task_pk
            primary key,
    project_id  integer           not null,
    title       varchar(255)      not null,
    description text,
    _deleted    integer default 0 not null
);
alter table customer
    owner to scm;

commit;

