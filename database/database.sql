drop table if exists Users;
drop table if exists Project;
drop table if exists Batch;
drop table if exists Record;
drop table if exists Field;
drop table if exists FieldValue;

create table Users (
	userId integer not null primary key autoincrement,
	userName varchar(255) not null,
	firstName varchar(255) not null,
	lastName varchar(255) not null,
	password varchar(255) not null,
	email varchar(255) not null,
	currentBatch integer,
	indexedRecords integer
);

create table Project (
	projectId integer not null primary key autoincrement,
	title varchar(255) not null,
	recordsPerImage integer,
	firstYCoord integer,
	recordHeight integer
);

create table Batch (
	batchId integer not null primary key autoincrement,
	projectId integer not null,
	imageFile varchar(255),
	isIndexed boolean,
	assignedUser integer not null
);

create table Record (
	recordId integer not null primary key autoincrement,
	batchId integer not null,
	recordNum integer not null
);

create table FieldValue (
	valueId integer not null primary key autoincrement,
	fieldId integer not null,
	recordId integer not null,
	value varchar(255)
);

create table Field (
	fieldId integer not null primary key autoincrement,
	projectId integer not null,
	fieldNum integer not null,
	title varchar(255),
	xCoord integer,
	width integer,
	helpUrl varchar(255),
	knownData varchar(255)
);
