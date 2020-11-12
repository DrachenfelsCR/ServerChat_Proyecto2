CREATE DATABASE Chat;

use Chat;

create table Usuario (
       id  varchar(10)  not null,
       nombre varchar(10) not null,  
       clave varchar(10) not null,
       Primary Key (id)         
     );
     
insert into Usuario (id,nombre,clave) values ('dqr','David','111');
insert into Usuario (id,nombre,clave) values ('jperez','Juan','111');
insert into Usuario (id,nombre,clave) values ('mreyes','Maria','111');
insert into Usuario (id,nombre,clave) values ('beto','Alberto','111');
insert into Usuario (id,nombre,clave) values ('tidael','Andres','111');

