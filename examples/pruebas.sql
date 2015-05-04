%
% Pruebas
%
% SQL Formulation
%

/multiline on


create or replace table employee(name string PRIMARY KEY, department string, salary int);
insert into employee values('anderson','accounting',1200);
insert into employee values('andrews','accounting',1200);
insert into employee values('arlingon','accounting',1000);
insert into employee values('nolan','accounting',null);
insert into employee values('norton','accounting',null);
insert into employee values('randall','resources',800);
insert into employee values('sanders','sales',null);
insert into employee values('silver','sales',1000);
insert into employee values('smith','sales',1000);
insert into employee values('steel','sales',1020);
insert into employee values('sullivan','sales',null);
insert into employee values('a-1', 'a',null);
insert into employee values('r-1', 'f',null);
insert into employee values('s-1', 'j',null);
select * from employee;
select count(*) from employee;
select count(salary) from employee;
select count(*),count(salary),min(salary),max(salary),sum(salary),avg(salary),times(salary) 
  from employee;
select * from employee where name = (select min(name) from employee);
select min(name),max(name),avg(salary) from employee;
select department,count(salary),sum(salary),avg(salary),min(salary),max(salary)
 from employee 
 group by department;
create or replace table parking(name string, lot string);
insert into parking values('anderson','a-1');
insert into parking values('randall','r-1');
insert into parking values('silver','s-1');
insert into parking values('andrews','accounting');
insert into parking values('arlingon','accounting');
insert into parking values('nolan','accounting');
insert into parking values('norton','accounting');
insert into parking values('sanders','sales');
insert into parking values('smith','sales');
insert into parking values('steel','sales');
insert into parking values('sullivan','sales');
insert into parking values('a-1', 'a');
insert into parking values('r-1', 'f');
insert into parking values('s-1', 'j');
create or replace table car(lot string PRIMARY KEY, matrikel string);
insert into car values('anderson','a-1');
insert into car values('randall','r-1');
insert into car values('silver','s-1');
insert into car values('andrews','accounting');
insert into car values('arlingon','accounting');
insert into car values('nolan','accounting');
insert into car values('norton','accounting');
insert into car values('sanders','sales');
insert into car values('smith','sales');
insert into car values('steel','sales');
insert into car values('sullivan','sales');
insert into car values('a-1', 'a');
insert into car values('r-1', 'f');
insert into car values('s-1', 'j');
create or replace view ds(a,b) as
  select department,max(salary) 
  from employee 
  group by department;
select * from ds;
select * from employee natural inner join parking;
select department,max(lot)
  from employee natural inner join parking 
  group by department;
/development off
/dbschema
/development on
/dbschema
/development off
/multiline off
