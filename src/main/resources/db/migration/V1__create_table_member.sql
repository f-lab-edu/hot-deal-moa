CREATE TABLE member
(
    id           bigint       not null auto_increment primary key,
    email        varchar(30)  not null,
    name         varchar(20)  not null,
    password     varchar(100)  not null,
    user_role    varchar(20) default 'ROLE_USER',
    phone_number varchar(20)  not null,
    address      varchar(100) not null,
    created_at   timestamp   default CURRENT_TIMESTAMP,
    updated_at   timestamp   default CURRENT_TIMESTAMP
);

# 관리자 계정 추가
INSERT INTO member(email, name, password, user_role, phone_number, address)
VALUES ('admin@hotdealmoa.com', 'manager', '$2a$10$5cV/k.d3AXoryInEUyzcseKdSp5PgBVWhS.X3wifLhWGH2Mz5f6Fq',
        'ROLE_ADMIN', '010-1234-1234', 'seoul');
