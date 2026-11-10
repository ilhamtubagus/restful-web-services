-- Can only be used in in-memory database like h2

insert into user_details (id, birth_date, name)
values (10001, current_date(), 'Ilham');

insert into user_details (id, birth_date, name)
values (10002, current_date(), 'Tubagus');

insert into user_details (id, birth_date, name)
values (10003, current_date(), 'Arfian');

insert into post (id, description, user_id)
values (20001, 'Learn Java Spring Boot', 10001);

insert into post (id, description, user_id)
values (20002, 'Learn AWS', 10001);

insert into post (id, description, user_id)
values (20003, 'Learn Google Cloud', 10001);

insert into post (id, description, user_id)
values (20004, 'Learn Java Spring Boot', 10002);