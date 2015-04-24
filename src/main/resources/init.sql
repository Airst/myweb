drop table reply;
create table reply(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER,
	gmt_create DATETIME,
	gmt_modified DATETIME,
	version INTEGER not null default '0',
	content_path VARCHAR(100),
	author_id INTEGER,
	floor INTEGER,
	thread_id INTEGER,
	parent_id INTEGER,
	reply_type INTEGER,
	reply_count INTEGER
);

drop table thread;
create table thread(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER,
	gmt_create DATETIME,
	gmt_modified DATETIME,
	version INTEGER not null default '0',
	title VARCHAR(100),
	content_path VARCHAR(100),
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
	version INTEGER not null default '0',
	account VARCHAR(100),
	password VARCHAR(100),
	name VARCHAR(100),
	email VARCHAR(100),
	phone VARCHAR(100),
	age INTEGER,
	gender INTEGER,
	level INTEGER
);

drop table image;
create table image(
	id INTEGER not null auto_increment primary key,
	feature VARCHAR(100),
	options INTEGER,
	is_deleted INTEGER,
	gmt_create DATETIME,
	gmt_modified DATETIME,
	version INTEGER not null default '0',
	filepath VARCHAR(100),
	user_id INTEGER,
	type INTEGER,
	parent_id INTEGER
);

