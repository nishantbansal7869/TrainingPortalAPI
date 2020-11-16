drop database expensetrackerdb;
drop user expensetracker;
create user expensetracker with password 'password';
create database expensetrackerdb with template=template0 owner=expensetracker;
\connect expensetrackerdb;
alter default privileges grant all on tables to expensetracker;
alter default privileges grant all on sequences to expensetracker;

create table TP_USER(
USER_ID integer primary key not null,
FIRST_NAME varchar(20) not null,
LAST_NAME varchar(20) not null,
EMAIL varchar(40) not null,
PASSWORD text not null
);

create table TP_COURSE(
COURSE_ID integer primary key not null,
USER_ID integer not null,
COURSE_NAME varchar(30) not null
);

alter table TP_COURSE add constraint cou_users_fk
foreign key (USER_ID) references TP_USER(USER_ID);

create table TP_COURSE_ENROLLED(
ENROLLMENT_ID integer primary key not null,
USER_ID integer not null,
COURSE_ID integer not null,
PROGRESS integer not null,
SCORE integer not null,
ENROLLED integer not null
);

alter table TP_COURSE_ENROLLED add constraint enr_users_fk
foreign key (USER_ID) references TP_USER(USER_ID);

alter table TP_COURSE_ENROLLED add constraint enr_course_fk
foreign key (COURSE_ID) references TP_COURSE(COURSE_ID);

create sequence TP_USERS_SEQ increment 1 start 1;
create sequence TP_COURSES_SEQ increment 1 start 1;
create sequence TP_ENROLLMENT_SEQ increment 1 start 1000;
