odd(x integer) := SELECT 0; -- Void definition for mutual recursive relations

even(x integer) :=
  SELECT 0
    UNION 
  SELECT odd.x+1 FROM odd WHERE odd.x<100;
  
odd(x integer) := 
  SELECT even.x+1 FROM even WHERE even.x<100;
