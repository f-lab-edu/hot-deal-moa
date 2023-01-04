CREATE TABLE product
(
    id           bigint              not null auto_increment primary key,
    title        varchar(50)         not null,
    content      text(500)           not null,
    detail_img   varchar(255),
    main_img     varchar(255),
    stock        integer   default 0 not null,
    total_price  integer   default 0 not null,
    delivery_fee integer   default 0 not null,
    start_at     timestamp,
    end_at       timestamp,
    category_id  bigint,
    seller_id    bigint,
    created_at   timestamp default CURRENT_TIMESTAMP,
    updated_at   timestamp default CURRENT_TIMESTAMP
);
