drop table reply;
create table reply(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER not null default 0,
	gmt_create DATETIME not null,
	gmt_modified DATETIME not null,
	version INTEGER not null default 0,
	content_path VARCHAR(100) not null,
	author_id INTEGER not null,
	author_account VARCHAR(100) not null,
	floor INTEGER not null,
	thread_id INTEGER not null,
	parent_id INTEGER not null,
	reply_type INTEGER not null default 0,
	reply_count INTEGER not null default 0
);

drop table thread;
create table thread(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER not null default 0,
	gmt_create DATETIME not null,
	gmt_modified DATETIME not null,
	version INTEGER not null default 0,
	title VARCHAR(100) not null,
	content_path VARCHAR(100) not null,
	author_id INTEGER not null,
	author_account VARCHAR(100) not null,
	hit INTEGER not null default 0,
	reply_count INTEGER not null default 0,
	like_count INTEGER not null default 0,
	last_reply_date DATETIME,
	level INTEGER not null default 0,
	tags INTEGER
);

drop table user;
create table user(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER not null default 0,
	gmt_create DATETIME not null,
	gmt_modified DATETIME not null,
	version INTEGER not null default 0,
	account VARCHAR(100) not null,
	password VARCHAR(100) not null,
	name VARCHAR(100) not null,
	email VARCHAR(100) not null,
	phone VARCHAR(100) not null,
	age INTEGER,
	gender INTEGER,
	level INTEGER
);

drop table image;
create table image(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER not null,
	gmt_create DATETIME not null,
	gmt_modified DATETIME not null,
	version INTEGER not null default 0,
	filepath VARCHAR(100) not null,
	user_id INTEGER not null,
	type INTEGER not null,
	parent_id INTEGER not null
);

drop table message;
create table message(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER not null,
	gmt_create DATETIME not null,
	gmt_modified DATETIME not null,
	version INTEGER not null default '0',
	from_user_id INTEGER,
	from_account VARCHAR(100),
	to_user_id INTEGER not null,
	to_account VARCHAR(100) not null,
	content VARCHAR(100) not null,
	type INTEGER not null,
	status INTEGER not null
);