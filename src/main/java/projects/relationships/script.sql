create table engine
(
    id   serial primary key,
    name varchar
);

create table history_owner
(
    id        serial primary key,
    name      varchar
);


create table car
(
    id        serial primary key,
    name      varchar,
    engine_id int references engine (id),
    history_owner_id int references history_owner (id)
);

create table driver
(
    id        serial primary key,
    name      varchar
);

create table cars_drivers
(
    id        serial primary key,
    car_id    int references driver (id),
    driver_id int references car (id)
);
