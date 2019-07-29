create role book_manager with LOGIN encrypted password 'book_manager_pass';

create database books owner = book_manager;

create table book(
	id serial primary key,
	title character varying(255),
	author character varying(255),
	price double precision,
	summary text,
	date_of_publication date
);