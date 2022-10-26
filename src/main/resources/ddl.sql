create database `all-in-search`;
use `all-in-search`;

create table `all-in-search`.`user`
(
    id         int          not null auto_increment,
    created_at datetime,
    updated_at datetime,
    telphone   varchar(40)  not null default '',
    password   varchar(200) not null default '',
    nickname   varchar(40)  not null default '',
    gender     int          not null default 0,
    primary key (id),
    unique telphone_unique_index using btree (telphone)
);

CREATE TABLE `all-in-search`.`seller`  (
    `id` int(0) NOT NULL AUTO_INCREMENT,
    `name` varchar(80) NOT NULL DEFAULT '',
    `created_at` datetime,
    `updated_at` datetime,
    `remark_score` decimal(2, 1) NOT NULL DEFAULT 0,
    `disabled_flag` int(0) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE `all-in-search`.`category`
(
    `id`         int(0) NOT NULL AUTO_INCREMENT,
    `created_at` datetime(0),
    `updated_at` datetime(0),
    `name`       varchar(20)  NOT NULL DEFAULT '',
    `icon_url`   varchar(200) NOT NULL DEFAULT '',
    `sort`       int(0) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_unique_index`(`name`) USING BTREE
);

CREATE TABLE `all-in-search`.`shop`
(
    `id`            int(0) NOT NULL AUTO_INCREMENT,
    `created_at`    datetime(0),
    `updated_at`    datetime(0),
    `name`          varchar(80)    NOT NULL DEFAULT '',
    `remark_score`  decimal(2, 1)  NOT NULL DEFAULT 0,
    `price_per_man` int(0) NOT NULL DEFAULT 0,
    `latitude`      decimal(10, 6) NOT NULL DEFAULT 0,
    `longitude`     decimal(10, 6) NOT NULL DEFAULT 0,
    `category_id`   int(0) NOT NULL DEFAULT 0,
    `tags`          varchar(2000)  NOT NULL DEFAULT '',
    `start_time`    varchar(200)   NOT NULL DEFAULT '',
    `end_time`      varchar(200)   NOT NULL DEFAULT '',
    `address`       varchar(200)   NOT NULL DEFAULT '',
    `seller_id`     int(0) NOT NULL DEFAULT 0,
    `icon_url`      varchar(100)   NOT NULL DEFAULT '',
    PRIMARY KEY (`id`)
);
