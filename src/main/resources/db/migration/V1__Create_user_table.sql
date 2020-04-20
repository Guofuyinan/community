create table user
(
	id bigint auto_increment,
	open_id varchar(100) null,
	nickname varchar(50) null,
	token varchar(36) null,
	gmt_create bigint null,
	gmt_modified bigint null,
	figureurl_qq_1 varchar(100) null,
	avatar_url varchar(100) null,
	constraint user_pk
		primary key (id)
);
