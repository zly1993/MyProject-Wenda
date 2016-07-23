drop table if exists `question`;
create table `question`(
	`id` int not null auto_increment,
    `title` varchar(225) not null,
    `conment` text null,
    `user_id` int not null,
    `created_date` datetime not null,
    `comment_count` int not null,
    primary key(`id`),
    index `date_index` (`created_date` asc));

drop table if exists `user`;
create table `user`(
	`id` int(11) unsigned not null auto_increment,
    `name` varchar(64) not null default '',
    `password` varchar(128) not null default '',
    `salt` varchar(32) not null default '',
	`head_url` varchar(256) not null default '',
     primary key (`id`),
     unique key `name` (`name`)
)engine=InnoDB default charset=utf8;


