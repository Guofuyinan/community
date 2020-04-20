create table notification
(
	id bigint auto_increment,
	notifier bigint null,
	receiver bigint null,
	outerid bigint null,
	type int null,
	gmt_create bigint null,
	status int null,
	notifier_name varchar(100) null,
	outer_title varchar(256) null,
	constraint notification_pk
		primary key (id)
);

