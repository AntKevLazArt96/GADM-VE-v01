drop schema public cascade;
create schema public authorization postgres;
set search_path to public;


CREATE TABLE Img_VE (
                id_img SERIAL NOT NULL,
                nombre_img VARCHAR NOT NULL,
                img BYTEA NOT NULL,
                CONSTRAINT img_ve_pk PRIMARY KEY (id_img)
);


CREATE TABLE Quorum_VE (
                id_quorum SERIAL NOT NULL,
                fecha_quorum DATE NOT NULL,
                estado_quorum VARCHAR,
                CONSTRAINT quorum_ve_pk PRIMARY KEY (id_quorum)
);


CREATE TABLE User_VE (
                id_user SERIAL NOT NULL,
                cedula_user VARCHAR NOT NULL,
                position_user VARCHAR NOT NULL,
                name_user VARCHAR NOT NULL,
                username_user VARCHAR NOT NULL,
                password_user VARCHAR NOT NULL,
                id_img INTEGER,
                cod_huella VARCHAR,
    			isLogged int,
                CONSTRAINT user_ve_pk PRIMARY KEY (id_user)
);



CREATE TABLE OrdenDia_VE (
                id_ordenDia SERIAL NOT NULL,
                convocatoria_sesion VARCHAR NOT NULL,
                numPunto_ordenDia INTEGER NOT NULL,
                descrip_ordenDia VARCHAR NOT NULL,
                id_user INTEGER,
                si_ordenDia INTEGER,
                no_ordenDia INTEGER,
                blanco_ordenDia INTEGER,
                salvo_ordenDia INTEGER,
                estado_ordenDia VARCHAR,--por defecto null --- APROBADO, RECHADO, NOVOTO
    			verifica_ordenDia VARCHAR,--por defecto se guarda por PENDIENTE,PROGRESO,TERMINADO
                CONSTRAINT ordendia_ve_pk PRIMARY KEY (id_ordenDia)
);

CREATE TABLE ResolucionPunto_VE (
                id_resolucion SERIAL NOT NULL,
                descrip_resolucion VARCHAR NOT NULL,
                id_ordendia INTEGER NOT NULL,
                CONSTRAINT resolucion_punto_ve_pk PRIMARY KEY (id_resolucion)
);


CREATE TABLE Pdf_VE (
                id_pdf SERIAL NOT NULL,
                id_ordenDia INTEGER,
                nombre_pdf VARCHAR NOT NULL,
                archivo_pdf BYTEA NOT NULL,
                CONSTRAINT pdf_ve_pk PRIMARY KEY (id_pdf)
);

CREATE TABLE acta_ve(
              id_pdf serial,
              nombre_pdf character varying NOT NULL,
              archivo_pdf bytea NOT NULL,
              CONSTRAINT acta_ve_pk PRIMARY KEY (id_pdf) 
);

CREATE TABLE Sesion_VE (
                convocatoria_sesion VARCHAR NOT NULL,
                description_sesion VARCHAR NOT NULL,
                tipo_sesion VARCHAR NOT NULL,
                register_sesion DATE NOT NULL,
                intervention_sesion DATE NOT NULL,
                hour_sesion VARCHAR NOT NULL,
                id_quorum INTEGER,
                id_pdf INTEGER NOT NULL,
    			estado_sesion VARCHAR,
                CONSTRAINT sesion_ve_pk PRIMARY KEY (convocatoria_sesion)
);


CREATE TABLE NotasPdf_VE (
                id_user INTEGER NOT NULL,
                id_pdf INTEGER NOT NULL,
                descripcion_notas VARCHAR NOT NULL
);

CREATE TABLE Asistencia_VE (
                id_user INTEGER NOT NULL,
                id_quorum INTEGER NOT NULL,
                estado_asistencia VARCHAR NOT NULL
);
CREATE TABLE configuracion_VE (
                id_confi integer primary key not null,
                ipRmi_confi varchar not null,
                ipSocket_confi varchar not null,
                puertoRmi_confi integer not null,
                puertoSocket_confi integer not null,
                nombreBD_confi varchar not null,
                userDB_confi varchar not null,
                passBD_confi varchar not null
 );

CREATE TABLE notasActa_ve(
              id_user integer NOT NULL,
              id_acta integer NOT NULL,
              descripcion_notas character varying NOT NULL
);
INSERT INTO configuracion_VE(id_confi,ipRmi_confi,ipSocket_confi,puertoRmi_confi,puertoSocket_confi,nombreBD_confi,userDB_confi,passBD_confi) 
 VALUES(1,'192.168.1.6','192.168.1.6',8888,6666,'gad_voto','postgres','1234');

ALTER TABLE User_VE ADD CONSTRAINT img_ve_user_ve_fk
FOREIGN KEY (id_img)
REFERENCES Img_VE (id_img)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE Sesion_VE ADD CONSTRAINT quorum_ve_sesion_ve_fk
FOREIGN KEY (id_quorum)
REFERENCES Quorum_VE (id_quorum)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;


ALTER TABLE Asistencia_VE ADD CONSTRAINT quorum_ve_asistencia_ve_fk
FOREIGN KEY (id_quorum)
REFERENCES Quorum_VE (id_quorum)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE Asistencia_VE ADD CONSTRAINT user_ve_asistencia_ve_fk
FOREIGN KEY (id_user)
REFERENCES User_VE (id_user)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE NotasPdf_VE ADD CONSTRAINT user_ve_notaspdf_ve_fk
FOREIGN KEY (id_user)
REFERENCES User_VE (id_user)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE OrdenDia_VE ADD CONSTRAINT user_ve_ordendia_ve_fk
FOREIGN KEY (id_user)
REFERENCES User_VE (id_user)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE Pdf_VE ADD CONSTRAINT ordendia_ve_pdf_ve_fk
FOREIGN KEY (id_ordenDia)
REFERENCES OrdenDia_VE (id_ordenDia)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE NotasPdf_VE ADD CONSTRAINT pdf_ve_notaspdf_ve_fk
FOREIGN KEY (id_pdf)
REFERENCES Pdf_VE (id_pdf)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE Sesion_VE ADD CONSTRAINT pdf_ve_sesion_ve_fk
FOREIGN KEY (id_pdf)
REFERENCES acta_ve (id_pdf)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE sesion_ve ADD CONSTRAINT session_ve_acta 
FOREIGN KEY (id_pdf)
REFERENCES public.acta_ve (id_pdf) 
MATCH SIMPLE
ON UPDATE CASCADE 
ON DELETE CASCADE;
      
ALTER TABLE OrdenDia_VE ADD CONSTRAINT sesion_ve_ordendia_ve_fk
FOREIGN KEY (convocatoria_sesion)
REFERENCES Sesion_VE (convocatoria_sesion)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE notasActa_ve ADD CONSTRAINT acta_ve_notaspdf_ve_fk 
FOREIGN KEY (id_acta)
REFERENCES acta_ve (id_pdf) 
MATCH SIMPLE
ON UPDATE CASCADE 
ON DELETE CASCADE;

ALTER TABLE notasActa_ve ADD CONSTRAINT user_ve_notasacta_ve_fk 
FOREIGN KEY (id_user)
REFERENCES user_ve (id_user) 
MATCH SIMPLE
ON UPDATE CASCADE 
ON DELETE CASCADE;

ALTER TABLE ResolucionPunto_ve ADD CONSTRAINT resolucion_ve_ordendia_ve_fk 
FOREIGN KEY (id_ordendia)
REFERENCES ordendia_ve (id_ordendia) 
MATCH SIMPLE
ON UPDATE CASCADE 
ON DELETE CASCADE;
 
--insert into notasActa_ve (id_user, id_acta, descripcion_notas)values(2,10,'sa');

--drop function verificar_usuario(varchar, varchar);

create or replace function verificar_usuario(in character varying,in character varying,out integer, out character varying,out bytea, out character varying,out integer)
RETURNS SETOF record AS
$$
begin
	return query select id_user, name_user, img, position_user,islogged from User_VE u inner join Img_VE i on u.id_img=i.id_img where username_user=$1 and password_user=$2;
	
end
$$
LANGUAGE plpgsql VOLATILE COST 100;

select *from verificar_usuario('admin','1234');

create or replace function obtener_img(in character varying,out integer, out character varying,out bytea)
RETURNS SETOF record AS
$$
begin
	return query select id_user, name_user, img from User_VE u inner join Img_VE i on u.id_img=i.id_img where username_user=$1;
	
end
$$
LANGUAGE plpgsql VOLATILE COST 100;
select *from user_ve;

create or replace function ingresar_usuario(varchar,varchar, varchar, varchar, varchar, integer)
returns void as 
$$
begin 
	insert into User_VE(cedula_user,position_user,name_user,username_user,password_user,id_img,islogged) values
				($1,$2,$3,$4,$5,$6,0);
end;
$$
LANGUAGE plpgsql;

select * from Img_VE;

---insertar usuarios
insert into Img_VE(nombre_img,img)values('holi','holi');
--update user_ve set isLogged=0 where username_user='secretaria';
select ingresar_usuario('123456789','Administrador','ING. CARLOS MANOSALVAS','admin','1234',1);
--select ingresar_usuario('123456789','concejal','LCDA. VERONICA ABAD ARTEAGA','concejal1','1234','C://IMG/secretaria.png');
--select ingresar_usuario('123456789','concejal','LCDA. LADY GARCÍA MEJIA','concejal2','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','SRA. ESTEFANIA MACIAS SUAREZ','concejal3','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','LCDA. ESTER MARGARITA MEJIA CUADROS','concejal4','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','AB. FERNANDO PICO ARTEAGA','concejal5','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','AB. WILTER LENIN PILAY SUAREZ','concejal6','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','SR. ISIDORO MONTALVAN FLORES','concejal7','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','ECON. JOHNNY MERA CHÁVEZ','concejal8','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','ING. PEDRO LOAIZA FORTTY','concejal9','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','LCDA. SELENE SUAREZ MAJAO','concejal10','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','LCDO. RAUL TRAMPUZ RIVERA','concejal11','1234','C://IMG/secretaria.png','V4443V2242');
--select ingresar_usuario('123456789','concejal','LCDO. EDUARDO VELÁSQUEZ GARCÍA','concejal12','1234','C://IMG/secretaria.png','V4443V2242');

create or replace function eliminar_usuario(integer)
returns void as 
$$
begin 
	delete from User_VE where id_user=$1;
end;
$$
LANGUAGE plpgsql;

create or replace function eliminar_usuario_ced(varchar)
returns void as 
$$
begin 
	delete from User_VE where cedula_user=$1;
end;
$$
LANGUAGE plpgsql;

select * from User_VE;

--create or replace function modificar_usuario(integer,varchar,varchar, varchar, varchar, varchar, varchar,varchar)
--returns void as 
--$$
--begin 
--	update usuario set cedula_user=$2,position_user=$3,name_user=$4,username_user=$5,password_user=$6,img_user=$7,cod_huella=$8 where id_user=$1;
--end;
--$$
--LANGUAGE plpgsql;


--drop function consulta_usuario(integer);
create or replace function consulta_usuario(in integer,out integer, out varchar,out varchar, out varchar, out varchar, out varchar, out integer,out varchar,out integer)
RETURNS SETOF record AS
$$
begin
	return query select * from User_VE where id_user=$1;
end
$$
LANGUAGE plpgsql VOLATILE;

select * from consulta_usuario(2);

create or replace function consulta_usuario_ced(in varchar,out integer, out varchar,out varchar, out varchar, out varchar, out varchar, out integer,out varchar,out integer)
RETURNS SETOF record AS
$$
begin
	return query select * from User_VE where cedula_user=$1;
end
$$
LANGUAGE plpgsql VOLATILE;

select * from consulta_usuario_ced('123456789');

create or replace function consulta_usuario_user(in varchar,out integer, out varchar,out varchar, out varchar, out varchar, out varchar, out integer,out varchar,out integer)
RETURNS SETOF record AS
$$
begin
	return query select * from User_VE where username_user=$1;
end
$$
LANGUAGE plpgsql VOLATILE;


create or replace function consulta_usuario_name(in varchar,out integer, out varchar, out bytea)
RETURNS SETOF record AS
$$
begin
	return query select id_user,position_user,img from User_VE u inner join Img_VE i on u.id_img=i.id_img where name_user=$1;
end
$$
LANGUAGE plpgsql VOLATILE;

select * from consulta_usuario_name('Ab. José Pico Arteaga');

create or replace function consulta_usuarios(out integer, out varchar, out bytea)
RETURNS SETOF record AS
$$
begin
	return query select id_user,position_user,img from User_VE u inner join Img_VE i on u.id_img=i.id_img where position_user='Concejal Principal' ;
end
$$
LANGUAGE plpgsql VOLATILE;

select *from consulta_usuarios();







select *from Sesion_VE;

select *from Pdf_VE;

--drop function agregar_pdf(varchar, bytea)
--create or replace function agregar_pdf(in varchar,in bytea,out integer, out varchar, out bytea)
CREATE OR REPLACE FUNCTION public.agregar_acta(
    character varying,
    bytea)
  RETURNS void AS
$BODY$
begin 
	insert into acta_ve(nombre_pdf,archivo_pdf) values
				($1,$2);
    --return query select * from Pdf_VE where nombre_pdf=$1;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


----------SESION--------------
create or replace function ingresar_sesion(varchar,varchar,varchar,date,date,varchar,integer)
returns void as 
$$
begin 
	insert into Sesion_VE(convocatoria_sesion, description_sesion,tipo_sesion,register_sesion, intervention_sesion, hour_sesion,id_pdf,estado_sesion) values
				($1,$2,$3,$4,$5,$6,$7,'PENDIENTE');
end;
$$
LANGUAGE plpgsql;
select * from Sesion_VE;

--ingresar
create or replace function ingresar_orden_dia(varchar,integer,varchar,integer)
returns void as 
$$
begin 
	INSERT INTO OrdenDia_VE(convocatoria_sesion,numpunto_ordendia, descrip_ordendia,id_user) VALUES ($1,$2,$3,$4);

end;
$$
LANGUAGE plpgsql;

select *from Sesion_VE;


select *from quorum_ve;
select *from Asistencia_VE;

select convocatoria_sesion,description_sesion ,id_pdf from Sesion_VE where intervention_sesion='26/12/2017';
select id_ordenDia,numpunto_ordendia,descrip_ordendia from Sesion_VE as s inner join OrdenDia_VE as od on s.convocatoria_sesion=od.convocatoria_sesion  where s.intervention_sesion='26/12/2017';
select id_ordenDia,numpunto_ordendia,descrip_ordendia from Sesion_VE as s inner join OrdenDia_VE as od on s.convocatoria_sesion=od.convocatoria_sesion  where s.intervention_sesion='26/12/2017';
select convocatoria_sesion,description_sesion ,id_pdf from Sesion_VE where intervention_sesion='26/12/2017';
--drop function asistencia_concejales(integer);
create or replace function asistencia_concejales(in integer, out integer,  out varchar, out varchar,out bytea)
RETURNS SETOF record AS
$$
begin
	return query select u.id_user, name_user ,estado_asistencia, img from User_VE u inner join 
                    Img_VE i on u.id_img=i.id_img inner join 
                    Asistencia_VE a on a.id_user=u.id_user inner join 
                    Quorum_VE q on q.id_quorum =a.id_quorum where q.id_quorum = $1;
end
$$
LANGUAGE plpgsql VOLATILE;

select *from asistencia_concejales(3);
select *from Sesion_VE;
select *from User_VE;
select convocatoria_sesion,description_sesion from Sesion_VE where intervention_sesion='23/11/2017';

update OrdenDia_VE set si_ordendia=null, no_ordendia=null, blanco_ordendia=null, salvo_ordendia=null, estado_ordendia=null where id_ordendia=15;
update OrdenDia_VE set si_ordendia=0, no_ordendia=0, blanco_ordendia=0, salvo_ordendia=0, estado_ordendia='APROBADO' where id_ordendia=15;
select * from Sesion_VE where intervention_sesion='07/12/2017' and estado_sesion='PENDIENTE';
select *from sesion_VE where estado_sesion='';
--select *from OrdenDia_VE
--select estado_ordendia from OrdenDia_VE where id_ordendia=16
--select estado_ordendia from OrdenDia_VE where id_ordendia=15;
--select * from OrdenDia_VE where convocatoria_sesion='090-2017';
--select estado_ordendia from OrdenDia_VE where convocatoria_sesion='090-2017' group by estado_ordendia;
select *from asistencia_concejales(1);

--insert into OrdenDia_VE (convocatoria_sesion,numpunto_ordendia,descrip_ordendia,id_user)values
--('090-2017',6,'Conocimiento de la no se que cosa que paso ayer',2);

--update User_VE set username_user='concejal2' where id_user = 3

select img from Img_VE;