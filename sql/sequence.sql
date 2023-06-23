CREATE SEQUENCE account_id_seq;
ALTER SEQUENCE account_id_seq owned by account.id;
alter table account
    alter column id set default nextval('account_id_seq');

CREATE SEQUENCE address_id_seq;
ALTER SEQUENCE address_id_seq owned by address.id;
alter table address
    alter column id set default nextval('address_id_seq');

CREATE SEQUENCE customer_id_seq;
ALTER SEQUENCE customer_id_seq owned by customer.id;
alter table customer
    alter column id set default nextval('customer_id_seq');

CREATE SEQUENCE history_id_seq;
ALTER SEQUENCE history_id_seq owned by history.id;
alter table history
    alter column id set default nextval('history_id_seq');

CREATE SEQUENCE history_change_id_seq;
ALTER SEQUENCE history_change_id_seq owned by history_change.id;
alter table history_change
    alter column id set default nextval('history_change_id_seq');

CREATE SEQUENCE project_id_seq;
ALTER SEQUENCE project_id_seq owned by project.id;
alter table project
    alter column id set default nextval('project_id_seq');

CREATE SEQUENCE task_id_seq;
ALTER SEQUENCE task_id_seq owned by task.id;
alter table task
    alter column id set default nextval('task_id_seq');
