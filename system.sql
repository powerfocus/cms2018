drop database if exists jcms2018;
create database jcms2018 charset utf8;
use jcms2018;
create table article(
	id int unsigned primary key auto_increment,
    title varchar(120) not null,
    author varchar(45) not null,
    source varchar(80) not null,
    publishdatetime datetime,
    shorttitle varchar(50),
    isvarify tinyint,
    columntype varchar(50) not null,
    content longtext
)engine=myisam;

create table columntype(
	id mediumint unsigned primary key auto_increment,
    pid mediumint unsigned not null,
    typename varchar(50),
    col_describe varchar(120)
)engine=innodb;

create table role(
	id mediumint unsigned primary key auto_increment,
    name varchar(45),
    user varchar(45)
)engine=innodb;

create table source(
	id mediumint unsigned primary key auto_increment,
    name varchar(80) not null,
    info text
)engine=innodb;

create table user(
	id int unsigned primary key auto_increment,
    username varchar(45) not null,
    password char(32) not null,
    ulevel tinyint not null,
    lastlogin datetime not null,
    info text,
    islock tinyint not null,
    role varchar(45) not null
)engine=innodb;

insert into role values(null, 'ADMINUSER', 'admin');
insert into user values(null, 'admin', md5('123'), 0, now(), 'admin user', 0, 'ADMINUSER');
