CREATE TABLE coupon
(
    id             bigint                  not null auto_increment primary key,
    title          varchar(50)             not null,
    discount_price integer   default 0     not null,
    expired_at     timestamp               not null,
    is_used        boolean   default false not null,
    member_id      bigint,
    created_at     timestamp default CURRENT_TIMESTAMP,
    updated_at     timestamp default CURRENT_TIMESTAMP,
    foreign key (member_id) references member (id)
);