fib(n integer, f integer) := SELECT 0,1;
fib1(n integer, f integer) := SELECT fib.n, fib.f FROM fib;
fib2(n integer, f integer) := SELECT fib.n, fib.f FROM fib;
fib(n integer, f integer) := 
  SELECT 0,1
    UNION 
  SELECT 1,1
    UNION 
  SELECT fib1.n+1, fib1.f+fib2.f
  FROM fib1, fib2
  WHERE fib1.n=fib2.n+1 AND fib1.n<10;  
