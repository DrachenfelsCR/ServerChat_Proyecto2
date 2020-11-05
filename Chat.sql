CREATE DATABASE Chat;

use Chat;

create table Usuario (
       id  varchar(10)  not null,
       nombre varchar(10) not null,  
       clave varchar(10) not null,
       Primary Key (id)         
     );
     
insert into Usuario (id,nombre,clave) values ('davidjqr','david','111');
