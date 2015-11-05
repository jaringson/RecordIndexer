drop table if exists users;
drop table if exists projects;
drop table if exists fields;
drop table if exists batches;
drop table if exists records;
drop table if exists inputvalues;
create table users(	id integer not null primary key autoincrement,	username varchar(255) not null,	password varchar(255) not null,	firstname varchar(255) not null,	lastname varchar(255) not null,	email varchar(255) not null,	indexrecords integer not null,	curBatch_id integer not null);
create table projects(	id integer not null primary key autoincrement,	title varchar(255) not null,	recordsperimage integer not null,	firstycoord integer not null,	recordheight integer not null);
create table batches(	id integer not null primary key autoincrement,	project_id integer not null,	file varchar(255) not null,	complete boolean default false,	available boolean default true,	checkedout boolean default false);
create table fields(	id integer not null primary key autoincrement,	project_id integer not null,	title varchar(255) not null,	xcoord integer not null,	width integer not null,	helphtml varchar(255) not null,	knowndata varchar(255) not null,	columnnumber integer not null);
create table records(	id integer not null primary key autoincrement,	batch_id integer not null,	row_number integer not null);
create table inputvalues(	id integer not null primary key autoincrement,	record_id integer not null,	field_id integer not null,	inputvalue varchar(255) not null);




