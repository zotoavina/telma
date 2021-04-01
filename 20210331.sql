create unique index nomdata on datas (nomdata);
create view v_tarifpardefaut as
  select tc.*, da.nomdata from tarifCredit tc join datas da
  on tc.iddata = da.iddata;
 