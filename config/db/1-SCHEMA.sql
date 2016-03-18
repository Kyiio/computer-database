drop schema if exists `computer-database-db`;
  create schema if not exists `computer-database-db`;
  use `computer-database-db`;

  drop table if exists computer;
  drop table if exists company;
  drop table if exists users;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

	create table users (
	  username 					varchar(45) not NULL ,
	  passwd 					varchar(45) not NULL ,
	  enabled 					tinyint not NULL default 1 ,
	  constraint pk_users 		primary key (username));

	create table user_roles (
  	  user_role_id 				int(11) not NULL auto_increment,
	  username 					varchar(45) NOT NULL,
	  role 						varchar(45) NOT NULL,
	  constraint pk_user_roles primary key (user_role_id),
	  unique key uni_username_role (role,username),
	  key fk_username_idx (username),
	  constraint fk_username foreign key (username) references users (username));



  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);
