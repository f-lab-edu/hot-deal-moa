CREATE TABLE MEMBER
(
    id           bigint       not null auto_increment,
    email        varchar(30)  not null,
    name         varchar(20)  not null,
    password     varchar(20)  not null,
    user_role    varchar(20) default 'ROLE_USER',
    phone_number varchar(20)  not null,
    address      varchar(100) not null,
    created_at   timestamp   default CURRENT_TIMESTAMP,
    updated_at   timestamp   default CURRENT_TIMESTAMP,
    primary key (id)
);

# 관리자 계정 추가
INSERT INTO MEMBER(email, name, password, user_role, phone_number, address)
VALUES ('admin@hotdealmoa.com', 'manager', 'Qwe123!@#', 'ROLE_ADMIN', '010-1234-1234', 'seoul');
