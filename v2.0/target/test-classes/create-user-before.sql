delete from user_roles;
delete from users;

insert into users (user_id, username, password, active, account, phone, address, email)
values (1, 'qwe', 'qwe', true, 10, '123456', 'Berlin app. 20', 'qwe@email.com'),
       (2, 'qwer', 'qwer', true, 200, '789012', 'London app. 31', 'qwer@email.com');
insert into user_roles (user_id, roles)
values (1, 'USER'),
       (2, 'USER');