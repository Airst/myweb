drop table content;
create table content(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER,
	gmt_create DATETIME,
	gmt_modified DATETIME,
	version INTEGER
);

drop table reply;
create table reply(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER,
	gmt_create DATETIME,
	gmt_modified DATETIME,
	version INTEGER,
	content_id INTEGER,
	author_id INTEGER,
	floor INTEGER,
	parent_id INTEGER,
	reply_type INTEGER
);

drop table thread;
create table thread(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER,
	gmt_create DATETIME,
	gmt_modified DATETIME,
	version INTEGER,
	title VARCHAR(100),
	content_id INTEGER,
	author_id INTEGER,
	hit INTEGER,
	reply_count INTEGER,
	like_count INTEGER,
	last_reply_date DATETIME,
	level INTEGER,
	tags INTEGER
);

drop table user;
create table user(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER,
	gmt_create DATETIME,
	gmt_modified DATETIME,
	version INTEGER,
	account VARCHAR(100),
	password VARCHAR(100),
	name VARCHAR(100),
	email VARCHAR(100),
	phone VARCHAR(100),
	age INTEGER,
	gender INTEGER,
	level INTEGER
);

