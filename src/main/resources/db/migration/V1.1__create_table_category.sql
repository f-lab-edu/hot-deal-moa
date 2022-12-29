CREATE TABLE category
(
    id         bigint not null auto_increment primary key,
    name       varchar(20),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP
);

INSERT INTO category(name)
VALUES ('Fashion'),
       ('Beauty'),
       ('Sports'),
       ('Food'),
       ('IT'),
       ('Furniture'),
       ('Home & Health'),
       ('Pet'),
       ('Book'),
       ('Travel'),
       ('Ticket');