CREATE TABLE review
(
    id         bigint              not null auto_increment primary key,
    content    text(500)           not null,
    review_img varchar(255),
    star       integer   default 0 not null,
    member_id  bigint,
    product_id bigint,
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP,
    foreign key (member_id) references member (id),
    foreign key (product_id) references product (id)
);
