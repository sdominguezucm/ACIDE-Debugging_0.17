a(a int):=select 1;
c(a int):=select 1; -- Void def. for mutual recursive relations
b(a int):=select * from a union select * from c; 
c(a int):=select * from b;                       
d(a int):=select * from c;                      
e(a int):=select * from c;
f(a int):=select 1 except select * from a;       
g(a int):=select * from f;
