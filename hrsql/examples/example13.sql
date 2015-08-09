R1(A integer) :=
  select 1 union select 2 union select 3;
  
R2(A integer):=
  (select 1 union select 3 union select 5)
  except 
  (select R1.A from R1 where R1.A=1 or R1.A=2);
  
R3(A integer) :=
  select R2.A from R2 
  union 
  select R3.A*2 from R3 where R3.A<5;

Rh(A integer) :=
  assume
    select R1.A from R1 where R1.A < 3 in R2,
    select 3 not in R2
  select R3.A from R3
  union 
  select Rh.A*3 from Rh where Rh.A < 3;