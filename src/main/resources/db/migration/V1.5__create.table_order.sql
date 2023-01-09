CREATE TABLE orders
(
    id              bigint              not null auto_increment primary key,
    order_status    varchar(30) default 'ordered' not null,
    payment_price   integer   default 0 not null,
    product_count   integer   default 0 not null,
    request_address varchar(100)        not null,
    request_message text(500),
    coupon_id       bigint,
    member_id       bigint,
    product_id      bigint,
    created_at      timestamp default CURRENT_TIMESTAMP,
    updated_at      timestamp default CURRENT_TIMESTAMP
);