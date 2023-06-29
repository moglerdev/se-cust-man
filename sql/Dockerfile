FROM postgres:alpine3.18 

# COPY ./db.secrets.txt /run/secrets/db_password
ADD ./1_create_scripts.sql /docker-entrypoint-initdb.d/
ADD ./2_seed_scripts.sql /docker-entrypoint-initdb.d/

RUN chmod a+r /docker-entrypoint-initdb.d/*
