insert into user_accounts (id, creation_time, modification_time, version, email, first_name, last_name, password, role, sign_in_provider) values (null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'a@a.com', '태현', '정', '$2a$10$BrFElaF/ClvH0SN1/Ej5reqSx3OYCTxfE5UzRUL7AHnh3yPEDoi2y', 'ROLE_USER', null);
insert into category (creation_time, modification_time, version, description, name) values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, '회화', '회화');
insert into category (creation_time, modification_time, version, description, name) values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, '조각', '조각');
insert into category (creation_time, modification_time, version, description, name) values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, '공예', '공예');
insert into category (creation_time, modification_time, version, description, name) values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, '취미', '취미');