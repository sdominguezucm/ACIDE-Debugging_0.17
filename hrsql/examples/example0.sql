numbers(n integer) :=
  SELECT 0
    UNION 
  SELECT n+1
  FROM numbers
  WHERE n<100;