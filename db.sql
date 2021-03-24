-- back office
--- show table : \dt+
CREATE ROLE zotoavina  login password'123456';

create database telma ;

ALTER DATABASE telma OWNER TO zotoavina;

psql -U zotoavina telma;

----------- admin --------------- admin ---------------
create table operateurs(
    idoperateur serial primary key not null,
    nomoperateur varchar(20) not null,
    datecreation timestamp,
    predicat varchar(5) not null
);
create unique index fkpredicat on operateurs (predicat);
insert into operateurs values(1, 'telma', '2010-01-01 12:00:00','034');

create table admins(
    idadmin serial primary key not null,
    idoperateur int not null,
    nom varchar(30) not null,
    prenom varchar(30),
    email varchar(50) not null,
    mdp VARCHAR(50) not null
); 
create unique index fkemail on admins (email);
alter table admins add constraint fk_operateurs foreign key (idoperateur) references operateurs(idoperateur);
insert into admins(idoperateur, nom, prenom, email, mdp) values(1, 'Rasoaharisoa', 'zotoavina', 'zotoavina@gmail.com', '123456');


create table clients(
    idclient serial primary key not null,
    idoperateur int not null default 1,
    nom varchar(30) not null,
    prenom varchar(30),
    numero varchar(15) not null,
	mdp varchar(20) not null,
    solde decimal not null default 0,
	credit decimal not null default 0,
    dateadhesion timestamp not null default current_timestamp
);
alter table clients add constraint fk_operateur foreign key (idoperateur) references operateurs(idoperateur);
create unique index numtelephone on clients (numero);
insert into clients (nom, prenom, numero, mdp) values('Rahalinjanahary','martinah','8818232','123456');


create table typeactions(
    idtypeaction serial primary key not null,
    nomtypeaction varchar(20) not null
);
create unique index fknomtypeaction on typeactions (nomtypeaction);

insert into typeactions values(1, 'depot');
insert into typeactions values(2, 'retrait');
insert into typeactions values(3, 'achat credit');


create table actions(
    idaction serial primary key not null,
    idtypeaction int not null,
    idclient int not null,
    montant decimal not null,
    etat int not null ,
	dateaction timestamp not null default current_timestamp
);
alter table actions add constraint fk_typeactions foreign key (idtypeaction) references typeactions(idtypeaction);
alter table actions add constraint fk_clients foreign key (idclient) references clients(idclient);

insert into actions(idtypeaction, idclient, montant ,etat) values(1, 1, 2000, 0); 
insert into actions(idtypeaction, idclient, montant ,etat) values(1, 1, 3000, 0); 
insert into actions(idtypeaction, idclient, montant ,etat) values(1, 1, 4000, 0); 


create table appels(
	idappel serial primary key not null,
	idclient int not null,
	numero varchar(15) not null,
	duree int not null,
	dateappel timestamp not null default current_timestamp
);
alter table appels add constraint fk_clients foreign key (idclient) references clients(idclient);
insert into appels(idclient, numero, duree) values(1, 0340035600, 60);

create table offres(
	idoffre serial primary key not null,
	nomoffre varchar(15) not null,
	code varchar(5) not null,
	interne decimal(6,2) not null,
	autres decimal(6,2) not null,
	international decimal(6,2) not null,
	datecreation timestamp not null default current_timestamp,
	active int not null default 1,
	description varchar(50) not null
);
create unique index index_nomoffre on offres(nomoffre);
insert into offres(idoffre, nomoffre, code, interne, autres, international, description) values 
(1, 'Yellow', '#224#', 0.5, 1, 1.5, 'bla bal bla');
alter table offres ADD priorite int check(priorite > 0)

update offres set nomoffre = 'yellow' , code ='#111#', interne = 0.5, autres = 1, international = 1.5, 
active = 1, description = 'sgh' where idoffre = 1;


create table forfaits(
	idforfait serial primary key not null,
	idoffre int not null,
	nomforfait varchar(15) not null,
	code varchar(5) not null,
	prix decimal(10,2) not null CHECK (price > 0),
	validite int not null,
	dateCreation timestamp not null default current_timestamp,
	active int not null default 1,
	description varchar(50) not null
);
alter table forfaits add constraint fk_forfaits foreign key (idoffre) references offres(idoffre);
create unique index index_nomforfaits on forfaits(nomforfait);

insert into forfaits(idoffre, nomforfait, code, prix, validite, description)
values(1, 'Be dimy', '224*5', 500, 1, 'dshgerrjh');

create table datas(
	iddata serial primary key not null,
	nomdata varchar(30) not null,
	active int not null check (active = 1 or active = 0),
	datecreation timestamp not null default current_timestamp
);

insert into datas values( 1, 'Appel', 1, current_timestamp);
insert into datas values( 2, 'Sms', 1, current_timestamp);
insert into datas values( 3, 'Facebook', 1, current_timestamp);
insert into datas values( 4, 'Instagram', 1, current_timestamp);
insert into datas values( 5, 'Internet', 1, current_timestamp);


create table forfaitdatas(
	idfdata serial primary key not null,
	idforfait int not null,
	iddata int not null,
	quantite decimal(8,2) not null
);

insert into forfaitdatas(idforfait,iddata,quantite) values(1,1,500);

create table achatforfaits(
	idachat serial primary key ,
	idclient int not null,
	idforfait int not null,
	modepaiement varchar(10) not null check( modepaiement = 'mvola' or modepaiement = 'credit'),
	dateachat timestamp not null default current_timestamp
);
alter table achatforfaits add constraint fk_clients foreign key (idclient) references clients(idclient);
insert into achatforfaits(idclient, idforfait, dateachat) values (1, 1, '2020-10-10 12:00:00');

create table dataclients(
	iddataclient serial primary key,
	idclient int not null,
	idforfait int not null,
	iddata int not null,
	quantite decimal(10,2) not null,
	dateachat timestamp not null default current_timestamp,
	validite int not null,
	expiration timestamp not null
);
alter table dataclients add constraint fk_clients foreign key (idclient) references clients(idclient);
alter table dataclients add constraint fk_datas foreign key (iddata) references datas(iddata);
alter table dataclients add constraint fk_forfaits foreign key (idforfait) references forfaits(idforfait);

insert into dataclients(idclient, idforfait, iddata, quantite, dateachat , validite, expiration)
 values( 1, 1, 1, 500, current_timestamp, 3, '2021-03-27 12:00:00');

create table consommations(
	idconsommation serial primary key,
	idclient int not null,
	iddata int not null,
	quantite decimal(10,2) not null check(quantite > 0),
	dateconsommation timestamp not null default current_timestamp
);
alter table consommations add constraint fk_clients foreign key (idclient) references clients(idclient);
alter table consommations add constraint fk_datas foreign key (iddata) references datas(iddata);

insert into consommations(idclient, iddata, quantite, dateconsommation) values 
 (1 ,1 ,100, '2021-03-24 12:00:00');

insert into consommations(idclient, iddata, quantite, dateconsommation) values 
 (2 ,1 ,100, '2021-03-20 12:00:00');

create table consommationdetails(
	iddetails serial primary key,
	idconsommation int not null,
	iddataclient int,
	idforfait int,
	iddata int null,
	quantite decimal(10,2) not null check(quantite > 0),
	modeconsommation varchar(10) not null check( modeconsommation = 'credit' or modeconsommation = 'forfait')
);
alter table consommationdetails add constraint fk_cons foreign key (idconsommation) references consommations(idconsommation);
alter table consommationdetails add constraint fk_datas foreign key (iddata) references datas(iddata);
alter table consommationdetails add constraint fk_forfaits foreign key (idforfait) references forfaits(idforfait);
alter table consommationdetails add constraint fk_dataclients foreign key (iddataclient) references dataclients(iddataclient);

insert into consommationdetails (idconsommation, idforfait, iddata, quantite, modeconsommation) values
 (1, 9, 1, 100, 'forfait');
 
insert into consommationdetails (idconsommation, idforfait, iddata, quantite, modeconsommation) values
 (2, 9, 1, 200, 'forfait');



create view v_dataclients as
 select dc.* 
 from dataclients dc join forfaits forf on	
 dc.idforfait = forf.idforfait order by expiration asc;
 
create view v_consommations as
 select c.idclient, c.dateconsommation, cd.* from consommations c 
 join consommationdetails cd on 
 c.idconsommation = cd.idconsommation;
 
create view v_consommationforfaits as
 select * from v_consommations where modeconsommation = 'forfait';

--- Requete data client actuelle
create or replace function f_dataclients( dateconsommation timestamp)
 returns table( idforfait int, iddata int, quantite decimal(10,2),dateachat timestamp , expiration timestamp) as
 $func$
 Begin
	return Query
	select dc.idforfait,  dc.iddata,  sum(dc.quantite) as quantite, min (dc.dateachat) as dateachat, dc.expiration
		from v_dataclients dc where ( dc.dateachat < dateconsommation and  dc.expiration > dateconsommation) 
		group by dc.idforfait, dc.iddata, dc.expiration;
	End
	$func$ LANGUAGE plpgsql;
		

select dc.idforfait,  f.nomforfait, dc.iddata, 
    sum(dc.quantite - coalesce(c.quantite, 0)) as quantite, d.nomdata
	from v_dataclients dc left join v_consommationforfaits c on  dc.idforfait = c.idforfait
	and dc.iddata = c.iddata and ( dc.dateachat < current_timestamp and  dc.expiration > current_timestamp) 
	and ( dc.dateachat < c.dateconsommation and  dc.expiration > c.dateconsommation) 
	join datas d on dc.iddata = d.iddata join forfaits f on 
	dc.idforfait = f.idforfait where dc.idclient = 1 
	group by dc.idforfait, dc.iddata, d.nomdata, f.nomforfait;
	
 
select dc.idforfait,  f.nomforfait, dc.iddata, 
    sum(dc.quantite - coalesce(c.quantite, 0)) as quantite, d.nomdata
	from v_dataclients dc left join v_consommationforfaits c on  dc.idforfait = c.idforfait
	and dc.iddata = c.iddata and ( dc.dateachat < current_timestamp and  dc.expiration > current_timestamp) 
	and ( dc.dateachat < c.dateconsommation and  dc.expiration > c.dateconsommation) 
	join datas d on dc.iddata = d.iddata join forfaits f on 
	dc.idforfait = f.idforfait where dc.idclient = 1 and dc.iddata = 1
	group by dc.idforfait, dc.iddata, d.nomdata, f.nomforfait;
	


	




create table credits(
    idcredits serial primary key not null,
    valmin decimal 
);

------------------------ Vues

--- Action + clients
create view actionclient as
select cl.* , act.idAction, act.idtypeaction,
act.montant as montantaction, act.etat,
act.dateaction, ta.nomtypeaction
from clients cl join actions act on cl.idclient = act.idclient
join typeactions ta on act.idtypeaction = ta.idtypeaction

-- forfaitdatas + datas
create view v_forfaitdatas as
select fd.* , d.nomdata  from
forfaitdatas fd join datas d on fd.iddata = d.iddata;





--------------------------------------------------------------- MONGO DB
use telma

db.offres.insert(
	{
		nomOffre : "Mora",
		dateCreation : Date(),
		code : "224*",
		active : true,
		description : "bla bla bla",
		tarifs : {
			telma : 0.6,
			autres : 1,
			international : 1.5
		},
		forfaits : []
	}
);

db.appels.insert(
	{
		idClient : 1,
		numero : "0340089189",
		duree : 5 ,    
		dates : new Date()
	}
)

db.sms.insert(
	{
		idClient : 1,
		numero : "",
		message : "djhghjekjsenmsing", 
		dates : new Date()
	}
)

{
        "_id" : ObjectId("60564d02d937f8584a8eb414"),
        "duree" : 30,
        "idClient" : 1,
        "receveur" : "0340089189",
        "activeEnvoyeur" : 0,
        "activeReceveur" : 1,
        "date" : ISODate("2021-03-20T19:29:06.257Z"),
        "_class" : "com.mobile.telma.domains.Appel"
}



------------------------------------------------------------------


{
		"nomOffre" : "Be",
        "code" : "225*",
        "description" : "dsghje",
		"interne" : 0.5,
		"autres" : 1.2,
		"international" : 1.6
	}
	
-----------------------  Ajout forfait a une offre	
	
	{
            "nomforfait": "Be antsika",
            "code": "224*6",
            "prix": 1000.0,
            "validite": 4,
            "description": "dshgerrjh",
            "datas": [
                {
                    "idData": 1,
                    "quantite": 500.0,
                    "nomData": "Appel"
                }
            ]
        }
	
 
 --------------------------------------
 
 ----------- gestion offres  atao mongodb --------------- gestion offres ---------------

-- create table categories(
--     idcategolrie serial primary key not null,
--     nomcategorie varchar(30) not null,
--     description varchar() 
-- );

-- create table unites(
--     idunite serial primary key not null,
--     unite varchar(20)
-- );

-- -- ex offres : yellow

-- create table offres(
--     idoffre serial primary key not null,
--     nomoffre varchar(20),
--     description varchar(300)
-- );

-- -- ex forfait : yellow faceboobaka
-- create table forfaits(
--     idforfait serial primary key not null,
--     idoffre int not null,
--     nomforfait varchar(20) not null,
--     prix decimal not null,
--     validite int not null,
--     code varchar(20),
--     description varchar(300)
-- );
-- alter table forfaits add constraint fk_offres foreign key (idoffres) references offres(idoffres);


-- -- ex dataforfait appel /s    internet /mo

-- create table dataforfaits(
--     iddataforfait serial primary key not null,
--     nomdataforfait varchar(20),
--     idunite int not null,
-- );
-- alter table dataforfaits add constraint fk_unites foreign key (idunite) references unites(idunite);

-- create table detailforfaits(
--     iddetailforfait serial primary key not null,
--     idforfait int not null,
--     iddataforfait int not null,
--     quantite decimal not null,
--     priorite int not null default 0,
-- );

-- alter table detailforfait add constraint fk_forfaits foreign key (idforfait) references forfaits(idforfait);
-- alter table detailforfait add constraint fk_dataforfaits foreign key (iddataforfait) references dataforfaits(iddataforfait);


	-- validation mobile money
select dc.idforfait,  f.nomforfait, dc.iddata,  
 sum(dc.quantite - coalesce(c.quantite, 0)) as quantite, d.nomdata	
 from v_dataclients dc left join v_consommationforfaits c on
 dc.idforfait = c.idforfait	and dc.iddata = c.iddata	
  and dc.expiration >  '2021-25-03 12:00:00' join datas d on dc.iddata = d.iddata join forfaits f on 	
 dc.idforfait = f.idforfait where dc.idclient = 1 and dc.iddata = 1	group by dc.idforfait, dc.iddata, d.nomdata, f.nomforfait
	
	
------------------------------------------------------------------- Commande
drop table forfaitdatas cascade;
drop table forfaitdatas cascade;
drop view v_consommationforfaits;
drop table forfaitClients;
drop table datas;

localhost:8080/admin/datas









-- ------------------------------------------STATISTIQUE---------------------------------------------------



CREATE VIEW v_achatinfo AS
SELECT idachat,idforfait,dateachat,count(*) nbrachat,
EXTRACT(YEAR FROM dateachat) anne, EXTRACT(MONTH FROM dateachat) mois FROM achatforfaits
GROUP BY idachat,idforfait,dateachat,anne,mois;

CREATE VIEW v_statforfaits AS
SELECT idforfait,coalesce(sum(nbrachat),0) nbrachat,anne,mois
FROM  v_achatinfo GROUP BY idforfait,anne,mois;

CREATE VIEW v_statoffres AS
SELECT frt.idoffre,sum(frt.prix * st.nbrachat) montant ,
sum(nbrachat) nbrachat,st.anne,st.mois
FROM  forfaits frt JOIN  v_statforfaits st
ON frt.idforfait = st.idforfait
GROUP BY frt.idoffre,st.anne,st.mois;


-- STATISTIQUE NBR ACHAT DE FORFAIT
SELECT frt.idoffre,frt.idforfait,frt.nomforfait,coalesce(frt.prix * st.nbrachat,0) montant, 
coalesce(st.nbrachat,0) nbrachat,coalesce(st.anne,2021) anne, coalesce(st.mois,2) mois
FROM forfaits frt left JOIN v_statforfaits st
ON frt.idforfait = st.idforfait AND anne = 2021 and mois= 2 WHERE frt.idoffre = 12;


CREATE OR REPLACE FUNCTION f_statforfait(ido int,year int, month int)
  RETURNS TABLE (id int, name varchar(20),montant decimal(20,5),nbrachat decimal(20,5)) AS
$func$
BEGIN
   RETURN QUERY
    SELECT frt.idforfait,frt.nomforfait,coalesce(frt.prix * st.nbrachat,0) montant, 
    coalesce(st.nbrachat,0) nbrachat
    FROM forfaits frt left JOIN v_statforfaits st
    ON frt.idforfait = st.idforfait AND anne = year and mois= month WHERE frt.idoffre = ido;                   -- potential ambiguity 
END
$func$  LANGUAGE plpgsql;



-- STATISTIQUE NBR ACHAT OFFRES
SELECT frt.idoffre,frt.nomoffre,coalesce(st.montant,0) montant, 
coalesce(st.nbrachat,0) nbrachat,coalesce(st.anne,2021) anne, coalesce(st.mois,1) mois
FROM offres frt left JOIN v_statoffres st
ON frt.idoffre = st.idoffre AND st.anne = 2021 and st.mois= 1



CREATE OR REPLACE FUNCTION f_statoffre(year int, month int)
  RETURNS TABLE (id int, name varchar(20),montant decimal(20,5),nbrachat decimal(20,5)) AS
$func$
BEGIN
   RETURN QUERY
   SELECT frt.idoffre,frt.nomoffre,coalesce(st.montant,0) montant, 
   coalesce(st.nbrachat,0) nbrachat
   FROM offres frt left JOIN v_statoffres st
   ON frt.idoffre = st.idoffre AND st.anne = year and st.mois= month;                    -- potential ambiguity 
END
$func$  LANGUAGE plpgsql;


