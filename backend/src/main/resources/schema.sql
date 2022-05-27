-- CREATE user localDeuMarket;

-- SELECT * FROM user;

-- DROP DATABASE deuMarketplace;
-- CREATE DATABASE deuMarketplace DEFAULT CHARACTER SET utf8;

-- SHOW DATABASES;

-- GRANT ALL privileges ON deuMarketplace.* TO localDeuMarket@'%' IDENTIFIED BY '';

-- FLUSH PRIVILEGES;

-- SHOW GRANTs FOR localDeuMarket;


-- ------------------------------------------------------------------

drop table member_refresh_token;
DROP TABLE post_comment;
DROP TABLE post_recommend;
DROP TABLE post_img;
DROP TABLE post;
DROP TABLE item_deal;
DROP TABLE chat_log;
DROP TABLE chat_room;
DROP TABLE wish_item;
DROP TABLE item_img;
DROP TABLE item;
DROP TABLE post_category;
DROP TABLE item_category;
DROP TABLE lecture;
DROP TABLE member;
-- -------------------------------------------------
create table item_category
(
    item_category_id bigint auto_increment
        primary key,
    category_name    varchar(30) not null
);

create table lecture
(
    lecture_id     bigint auto_increment
        primary key,
    lecture_name   varchar(40) not null,
    professor_name varchar(30) null
);

create table member
(
    member_id          bigint auto_increment
        primary key,
    name               varchar(20)  not null,
    email              varchar(40)  not null,
    nickname           varchar(20)  not null,
    certification      tinyint(1)   not null,
    created_date       timestamp    not null,
    last_modified_date timestamp    not null,
    oauth_id           varchar(50)  not null,
    role               varchar(20)  not null,
    token              varchar(150) null
);

create table item
(
    item_id            bigint auto_increment
        primary key,
    member_id          bigint        not null,
    item_category_id   bigint        not null,
    title              varchar(255)  not null,
    lecture_id         bigint        null,
    write_state        varchar(30)   null,
    surface_state      varchar(30)   null,
    regular_price      int           null,
    price              int           not null,
    description        varchar(1000) not null,
    classification     varchar(10)   not null,
    created_date       timestamp     not null,
    last_modified_date timestamp     not null,
    constraint item_ibfk_1
        foreign key (member_id) references member (member_id),
    constraint item_ibfk_2
        foreign key (item_category_id) references item_category (item_category_id),
    constraint item_ibfk_3
        foreign key (lecture_id) references lecture (lecture_id)
);

create table chat_room
(
    chat_room_id        bigint auto_increment
        primary key,
    item_id             bigint       not null,
    requested_member_id bigint       not null,
    socket              varchar(100) not null,
    created_date        timestamp    not null,
    last_modified_date  timestamp    not null,
    constraint chat_room_ibfk_1
        foreign key (item_id) references item (item_id),
    constraint chat_room_ibfk_2
        foreign key (requested_member_id) references member (member_id)
);

create table chat_log
(
    chat_log_id        bigint auto_increment
        primary key,
    chat_room_id       bigint       not null,
    sender_id          bigint       not null,
    recipient_id       bigint       not null,
    content            varchar(255) not null,
    created_date       timestamp    not null,
    last_modified_date timestamp    not null,
    is_read            tinyint(1)   not null,
    constraint chat_log_ibfk_1
        foreign key (chat_room_id) references chat_room (chat_room_id),
    constraint chat_log_ibfk_2
        foreign key (sender_id) references member (member_id),
    constraint chat_log_ibfk_3
        foreign key (recipient_id) references member (member_id)
);

create index chat_room_id
    on chat_log (chat_room_id);

create index recipient_id
    on chat_log (recipient_id);

create index sender_id
    on chat_log (sender_id);

create index item_id
    on chat_room (item_id);

create index requested_member_id
    on chat_room (requested_member_id);

create index item_category_id
    on item (item_category_id);

create index lecture_id
    on item (lecture_id);

create index member_id
    on item (member_id);

create table item_deal
(
    item_deal_id          bigint auto_increment
        primary key,
    item_id               bigint       not null,
    target_member         bigint       not null,
    appointment_date_time timestamp    not null,
    meeting_place         varchar(100) not null,
    deal_state            varchar(20)  not null,
    created_date          timestamp    not null,
    last_modified_date    timestamp    not null,
    constraint item_deal_ibfk_1
        foreign key (item_id) references item (item_id),
    constraint item_deal_ibfk_2
        foreign key (target_member) references member (member_id)
);

create index item_id
    on item_deal (item_id);

create index target_member
    on item_deal (target_member);

create table item_img
(
    item_img_id        bigint auto_increment
        primary key,
    item_id            bigint       not null,
    img_file           varchar(100) not null,
    img_seq            int          not null,
    created_date       timestamp    not null,
    last_modified_date timestamp    not null,
    constraint item_img_ibfk_1
        foreign key (item_id) references item (item_id)
);

create index item_id
    on item_img (item_id);

create table member_refresh_token
(
    refresh_token_id bigint auto_increment
        primary key,
    member_id        bigint       not null,
    refresh_token    varchar(255) not null,
    constraint member_refresh_token_ibfk_1
        foreign key (member_id) references member (member_id)
);

create index member_id
    on member_refresh_token (member_id);

create table post_category
(
    post_category_id bigint auto_increment
        primary key,
    category_name    varchar(30) not null
);

create table post
(
    post_id            bigint auto_increment
        primary key,
    post_category_id   bigint        not null,
    writer_id          bigint        not null,
    title              varchar(100)  not null,
    content            varchar(1000) not null,
    created_date       timestamp     not null,
    last_modified_date timestamp     not null,
    constraint post_ibfk_1
        foreign key (post_category_id) references post_category (post_category_id),
    constraint post_ibfk_2
        foreign key (writer_id) references member (member_id)
);

create index post_category_id
    on post (post_category_id);

create index writer_id
    on post (writer_id);

create table post_comment
(
    post_comment_id    bigint auto_increment
        primary key,
    post_id            bigint       not null,
    member_id          bigint       not null,
    content            varchar(200) not null,
    created_date       timestamp    not null,
    last_modified_date timestamp    not null,
    constraint post_comment_ibfk_1
        foreign key (post_id) references post (post_id),
    constraint post_comment_ibfk_2
        foreign key (member_id) references member (member_id)
);

create index member_id
    on post_comment (member_id);

create index post_id
    on post_comment (post_id);

create table post_img
(
    post_img_id        bigint auto_increment
        primary key,
    post_id            bigint       not null,
    img_file           varchar(100) not null,
    img_seq            int          not null,
    created_date       timestamp    not null,
    last_modified_date timestamp    not null,
    constraint post_img_ibfk_1
        foreign key (post_id) references post (post_id)
);

create index post_id
    on post_img (post_id);

create table post_recommend
(
    post_recommend_id  bigint auto_increment
        primary key,
    post_id            bigint    not null,
    member_id          bigint    not null,
    created_date       timestamp not null,
    last_modified_date timestamp not null,
    constraint post_recommend_ibfk_1
        foreign key (post_id) references post (post_id),
    constraint post_recommend_ibfk_2
        foreign key (member_id) references member (member_id)
);

create index member_id
    on post_recommend (member_id);

create index post_id
    on post_recommend (post_id);

create table wish_item
(
    wish_item_id       bigint auto_increment
        primary key,
    wished_member_id   bigint    not null,
    item_id            bigint    not null,
    created_date       timestamp not null,
    last_modified_date timestamp not null,
    constraint wish_item_ibfk_1
        foreign key (wished_member_id) references member (member_id),
    constraint wish_item_ibfk_2
        foreign key (item_id) references item (item_id)
);

create index item_id
    on wish_item (item_id);

create index wished_member_id
    on wish_item (wished_member_id);

