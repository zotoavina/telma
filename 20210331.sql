alter table forfaits add column typeduree varchar(20) check( typeduree = 'heure' or typeduree = 'jour');
alter table forfaitdatas add valmin time not null default '00:00:01';
alter table forfaitdatas add valmax time not null default '23:59:59';

create unique index nomdata on datas (nomdata);
create view v_tarifpardefaut as
  select tc.*, da.nomdata from tarifCredit tc join datas da
  on tc.iddata = da.iddata;
 