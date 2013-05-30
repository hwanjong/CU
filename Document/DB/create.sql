create table users(
id varchar(30) primary key,
pwd varchar(30) not null,
name varchar(30) not null
);

create table score(
id varchar(30) primary key,
score integer(10) not null,
grade varchar(30) not null
);

create table participate(
id varchar(30) primary key,
room_num integer(10) not null
);

create table room(
room_num integer(10) auto_increment primary key ,
room_name varchar(30) not null,
play integer(1) not null,
current_users integer(4) not null
);

alter table score add constraint
score_fk foreign key(id) references users(id)
on update cascade;

alter table participate add constraint
participate_fk foreign key(id) references users(id)
on update cascade;

alter table participate add constraint
participate_fk2 foreign key(room_num) references room(room_num)
on update cascade;

alter table room add constraint
room_check check(play = 0 or play=1);

