-- Database: gad_voto

-- DROP DATABASE gad_voto;

--CREATE DATABASE gad_voto;
--drop table usuario;
create table usuario(
id serial,
num_cedula varchar(11),
nombre varchar(50),
cargo varchar(50),
usuario varchar(20),
contracena varchar(20),
num_telefono varchar(10),
codigo_huella varchar(20) null
);
--ingresar usuario 

create or replace function ingresar_usuario(varchar,varchar, varchar, varchar, varchar, varchar,varchar)
returns void as 
$$
begin 
	insert into usuario(num_cedula,nombre,cargo,usuario,contracena,num_telefono,codigo_huella) values
				($1,$2,$3,$4,$5,$6,$7);
end;
$$
LANGUAGE plpgsql;

select ingresar_usuario('131560461','Ing. Christian Gonzalo Pinargote Garcia','Consejal 1','christian.pinargote','123456789','0992323734','');
select * from usuario;

--secretaria
select ingresar_usuario('131560461','Lcda. Maria Xiomara Arteaga Rivas','Secretaria','secretaria','1234','','');


--eliminar usuario por id
create or replace function eliminar_usuario(integer)
returns void as 
$$
begin 
	delete from usuario where id=$1;
end;
$$
LANGUAGE plpgsql;

--select eliminar_usuario(1)
select * from usuario;

--eliminar usuario por cedula
create or replace function eliminar_usuario_ced(varchar)
returns void as 
$$
begin 
	delete from usuario where num_cedula=$1;
end;
$$
LANGUAGE plpgsql;
select ingresar_usuario('131560462','Ing. Christian Gonzalo Pinargote Garcia','Consejal 1','christian.pinargote','123456789','0992323734','');
--select eliminar_usuario_ced('131560462')
select * from usuario;


--modificar usuario por id
create or replace function modificar_usuario(integer,varchar,varchar, varchar, varchar, varchar, varchar,varchar)
returns void as 
$$
begin 
	update usuario set num_cedula=$2,nombre=$3,cargo=$4,usuario=$5,contracena=$6,num_telefono=$7,codigo_huella=$8 where id=$1;
end;
$$
LANGUAGE plpgsql;

select modificar_usuario(2,'131560461','Ing.Christian Gonzalo Pinargote García','Consejal 1','christian.pinargote','123456789','0992323734','a');
select * from usuario;

drop function verificar_usuario(varchar,varchar);

create or replace function verificar_usuario(in character varying,in character varying,out integer, out varchar,out varchar, out varchar, out varchar, out varchar, out varchar,out varchar)
RETURNS SETOF record AS
$$
begin
	return query select * from usuario where usuario=$1 and contracena=$2;
	
end
$$
LANGUAGE plpgsql VOLATILE;
select * from verificar_usuario('concejal1','1234');



--mostrar usuario
--DROP FUNCTION consulta_usuario(integer)
create or replace function consulta_usuario(in integer,out integer, out varchar,out varchar, out varchar, out varchar, out varchar, out varchar,out varchar)
RETURNS SETOF record AS
$$
begin
	return query select * from usuario where id=$1;
end
$$
LANGUAGE plpgsql VOLATILE;
select * from consulta_usuario(2);

--mostrar usuario cedul
--DROP FUNCTION consulta_usuario_ced(varchar)
create or replace function consulta_usuario_ced(in varchar,out integer, out varchar,out varchar, out varchar, out varchar, out varchar, out varchar,out varchar)
RETURNS SETOF record AS
$$
begin
	return query select * from usuario where num_cedula=$1;
end
$$
LANGUAGE plpgsql VOLATILE;

select *from consulta_usuario_ced('123456789');
create or replace function consulta_usuario_user(in varchar,out integer, out varchar,out varchar, out varchar, out varchar, out varchar, out varchar,out varchar)
RETURNS SETOF record AS
$$
begin
	return query select id, num_cedula,nombre,cargo,usuario,contracena,num_telefono,codigo_huella from usuario where usuario=$1;
end
$$
LANGUAGE plpgsql VOLATILE;
select *
from consulta_usuario_user('Anthony96');



--drop table sesion;
create table sesion(
id serial,
fecha_registro date,
fecha_intervencion date,
hora_intervencion date,
convocatoria varchar,
titulo varchar(500)
);

--ingresar secion 
drop function ingresar_sesion(integer,date,date,integer,varchar);


create or replace function ingresar_sesion(date,date,date,varchar,varchar)
returns void as 
$$
begin 
	insert into sesion(fecha_registro,fecha_intervencion,hora_intervencion,convocatoria,titulo) values
				($1,$2,$3,$4,$5);
end;
$$
LANGUAGE plpgsql;

select *from ingresar_sesion('2008/12/31','2008/12/31','2008/12/31','014-2017','SESIÓN ORDINARIA  DEL CONCEJO DEL GOBIERNO AUTÓNOMO DESCENTRALIZADO MUNICIPAL DEL CANTÓN MANTA, CORRESPONDIENTE AL DÍA  LUNES 6 DE NOVIEMBRE DEL 2017, A LAS 16H00 EN EL SALON DE ACTOS DEL GADMC-MANTA ');
select * from sesion;
select *from ingresar_sesion('','','','','');



--eliminar sesion por id
create or replace function eliminar_sesion(integer)
returns void as 
$$
begin 
	delete from sesion where id=$1;
end;
$$
LANGUAGE plpgsql;

--select eliminar_sesion(1)
select * from sesion;

--eliminar sesion por numero de sesion
create or replace function eliminar_sesion_num(integer)
returns void as 
$$
begin 
	delete from sesion where num_sesion=$1;
end;
$$
LANGUAGE plpgsql;
select ingresar_sesion(2,'2017-11-01','2017-11-05',2,'Alcantarillado los electricos');
select eliminar_sesion_num(2);
select * from sesion;


--modificar sesion por id
create or replace function modificar_sesion(integer,date,date,integer, varchar)
returns void as 
$$
begin 
	update sesion set fecha_registro=$2,fecha_intervencion=$3,num_sesion=$4,tema=$5 where id=$1;
end;
$$
LANGUAGE plpgsql;

select modificar_sesion(1,'2017-11-01','2017-11-03',2,'Alcantarillado los electricos');
select * from sesion;


--mostrar sesion
--DROP FUNCTION consulta_sesion(integer);
create or replace function consulta_sesion(in integer,out integer,out date,out date,out integer, out varchar)
RETURNS SETOF record AS
$$
begin
	return query select * from sesion where id=$1;
end
$$
LANGUAGE plpgsql VOLATILE;
select * from consulta_sesion(1);


 DROP TABLE public.orden_dia;

CREATE TABLE public.orden_dia
(
  id integer ,
  id_sesion integer,
  numero_punto integer,
  tema_punto character varying(500),
  proponente integer,
  num_si integer,
  num_no integer,
  num_blanco integer,
  num_salvo integer,
  resultado_votacion integer
)


--ingresar OD 
create or replace function ingresar_orden_dia(integer,integer,varchar,varchar,varchar, integer, integer,integer, integer, integer)
returns void as 
$$
begin 
	INSERT INTO orden_dia(id_sesion,numero_punto, nombre_punto, descripcion_punto, documentacion, 
            num_si, num_no, num_blanco, num_salvo, resultado_votacion) VALUES ($1,$2,$3,$4,$5,$6,$7,$8,$9,10);

end;
$$
LANGUAGE plpgsql;
select * from sesion;
select ingresar_orden_dia(1,1,'Punto 1','Descripion del punto 1','C:\Users\chris\Desktop\documentos',0,0,0,0,0);

select ingresar_orden_dia(1,2,'Punto 1','Descripion del punto 1','C:\Users\chris\Desktop\documentos',0,0,0,0,0);

select ingresar_orden_dia(1,3,'Punto 1','Descripion del punto 1','C:\Users\chris\Desktop\documentos',0,0,0,0,0);
select * from orden_dia;


--eliminar sesion por id
create or replace function eliminar_orden(integer)
returns void as 
$$
begin 
	delete from orden_dia where id=$1;
end;
$$
LANGUAGE plpgsql;

select eliminar_orden(3);
select * from orden_dia;

--eliminar orden del dia por numero de punto
create or replace function eliminar_orden_numero(integer)
returns void as 
$$
begin 
	delete from orden_dia where numero_punto=$1;
end;
$$
LANGUAGE plpgsql;
select eliminar_orden_numero(2);
select * from orden_dia;


--modificar orden dia por id
create or replace function modificar_orden(integer,integer,integer,varchar,varchar,varchar, integer, integer,integer, integer, integer)
returns void as 
$$
begin 
	update orden_dia set id_sesion=$2,numero_punto=$3, nombre_punto=$4, descripcion_punto=$5, documentacion=$6, 
            num_si=$7, num_no=$8, num_blanco=$9, num_salvo=$10, resultado_votacion=$11 where id=$1;
end;
$$
LANGUAGE plpgsql;

select modificar_orden(1,1,1,'Punto 1','Descripion del punto 1','C:\Users\chris\Desktop\documentos',0,0,0,0,0);
select * from orden_dia;



--mostrar orden dia
--DROP FUNCTION consulta_oden(integer);
create or replace function consulta_oden(in integer,out integer,out integer,out integer,out varchar,out varchar,out varchar, out integer, 
		out integer,out integer, out integer, out integer)
RETURNS SETOF record AS
$$
begin
	return query select * from orden_dia where id=$1;
end
$$
LANGUAGE plpgsql VOLATILE;
select * from consulta_oden(1);



--mostrar secion completa
--DROP FUNCTION consulta_sesion_completa(date);
create or replace function consulta_sesion_completa(in date,out integer,out date,out date,out integer, out varchar, out integer,out integer,out integer,out varchar,out varchar,out varchar, out integer, 
		out integer,out integer, out integer, out integer)
RETURNS SETOF record AS
$$
begin
	return query select * from sesion as s
		inner join orden_dia as od on s.id=od.id_sesion where s.fecha_intervencion=$1;
end
$$
LANGUAGE plpgsql VOLATILE;
select * from consulta_sesion_completa('2017-11-03');



select numero_punto,tema_punto from sesion as s
		inner join orden_dia as od on s.id=od.id_sesion
		inner join usuario as us on us.id=od.proponente where s.fecha_intervencion='2017-11-14';


select * from sesion as s
		inner join orden_dia as od on s.id=od.id_sesion

select convocatoria,titulo from sesion where fecha_intervencion='2017-11-14';






