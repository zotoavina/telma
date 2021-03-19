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



------------------------------------------------------------------


{
		"nomOffre" : "Be",
        "code" : "225*",
        "description" : "dsghje",
		"interne" : 0.5,
		"autres" : 1.2,
		"international" : 1.6
	}
 