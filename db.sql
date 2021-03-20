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
insert into offres(idoffre, nomoffre, code, interne, autres, international, description) values 
(1, 'Yellow', '#224#', 0.5, 1, 1.5, 'bla bal bla');

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

insert into forfaits(idoffre, nomforfait, code, prix, validite, description)
values(1, 'Be dimy', '224*5', 500, 1, 'dshgerrjh');

create table datas(
	iddata serial primary key not null,
	nomdata varchar(30),
	datecreation timestamp not null default current_timestamp
);

insert into datas values( 1, 'Appel', current_timestamp);
insert into datas values( 2, 'Sms', current_timestamp);
insert into datas values( 3, 'Facebook', current_timestamp);
insert into datas values( 4, 'Instagram', current_timestamp);
insert into datas values( 5, 'Internet', current_timestamp);

create table forfaitdatas(
	idfdata serial primary key not null,
	idforfait int not null,
	iddata int not null,
	quantite decimal(8,2) not null
);

insert into forfaitdatas(idforfait,iddata,quantite) values(1,1,500);

create table forfaitclients(
	idforfaitclient serial primary key not null,
	idclient int not null,
	idforfait int not null,
	appel decimal (8,2) not null default 0 check(appel >= 0),
	sms int not null default 0 check(sms >= 0),
	facebook decimal (8,2) not null default 0 check(facebook >= 0),
	instagram decimal (8,2) not null default 0 check(instagram >= 0),
	internet decimal (8,2) not null default 0 check(internet >= 0),
	modepaiement varchar(8) not null check( modepaiement = 'mvola' or modepaiement = 'credit'),
	dateachat timestamp not null default current_timestamp
);
alter table forfaitclients add constraint fk_clients foreign key (idclient) references clients(idclient);
alter table forfaitclients add constraint fk_forfaits foreign key (idforfait) references forfaits(idforfait);

insert into forfaitclients(idclient,idforfait,appel, sms, facebook,instagram,internet, modepaiement) values
(1, 1, 10, 10, 10, 10, 10, 'mvola');

update forfaitclients set appel = 20, sms = 20 ,facebook = 20, instagram = 20, internet = 20 where idforfaitclient = 1;



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
create view forfaitdatasetdatas as
select fd.* , d.nomdata from
forfaitdatas fd join datas d on fd.iddata = d.iddata;

select * from forfaitdatasetdatas where idforfait = 1;



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
	
 