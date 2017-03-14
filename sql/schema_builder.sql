drop database if exists sql_register;
create database if not exists sql_register;
use sql_register;

drop table if exists students_table;
create table students_table
(
	Id integer not null auto_increment primary key,
    FirstName varchar(30) not null,
    LastName varchar(30) not null,
    DateOfBirth date not null,
    ParentEmail varchar(255),
    Street varchar(50),
    City varchar(50),
    PostalCode varchar(6),
    HouseNumber integer
);

drop table if exists subjects_table;
create table subjects_table
(
	Id integer not null auto_increment primary key,
    Subject varchar(30) not null
);

drop table if exists subjects_subscriptions_table;
create table if not exists subjects_subscriptions_table
(
	StudentId integer,
    SubjectId integer,
    Grade integer,
    constraint foreign key (StudentId) references students_table(Id),
    constraint foreign key (SubjectId) references subjects_table(Id),
    constraint primary key (StudentId, SubjectId)
);

drop trigger if exists validate_parent_email;
delimiter //
create trigger validate_parent_email 
	before insert on students_table for each row 
	begin
		if new.ParentEmail not like '%_@%_.__%' then
			signal sqlstate value '45000' set message_text='[table:person] - email column is not valid';
		end if;
	end //
delimiter ;

drop trigger if exists validate_postal_code;
delimiter //
create trigger validate_postal_code 
	before insert on students_table for each row 
	begin
		if new.PostalCode not like '__-___' then
			signal sqlstate value '45000' set message_text='[table:person] - postal code column is not valid';
		end if;
	end//
delimiter ;