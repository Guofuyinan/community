create table comment
(
	id bigint auto_increment,
	parent_id bigint null,
	type int null,
	commentator bigint null,
	gmt_create bigint null,
	gmt_modified bigint null,
	content varchar(1024) null,
	comment_count int default 0 null,
	constraint comment_pk
		primary key (id)
);

