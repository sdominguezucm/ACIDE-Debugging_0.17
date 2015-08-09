-- Attribute "time" changed to "t" due to SQL Anywhere

boat(ori varchar(10),des varchar(10), t float) :=
  SELECT 'SPC','TFN',2   FROM dual
    UNION ALL
  SELECT 'TFS','GMZ',1   FROM dual
    UNION ALL
  SELECT 'GMZ','VDE',1.5 FROM dual;

flight(ori varchar(10),des varchar(10), t float) :=
  SELECT 'MAD','TFN',2   FROM dual
    UNION ALL
  SELECT 'MAD','LPA',3   FROM dual
    UNION ALL
  SELECT 'MP','VDE',1    FROM dual;

bus(ori varchar(10),des varchar(10), t float) :=
  SELECT 'TFN','TFS',2.5 from dual
    UNION ALL
  SELECT 'LPA','MP',2    from dual
    UNION ALL
  SELECT 'VDE','RES',1   from dual;

link(ori varchar(10),des varchar(10), t float) := 
  SELECT * FROM flight 
    UNION ALL 
  SELECT * FROM boat
    UNION ALL 
  SELECT * FROM bus;

travel(ori varchar(10), des varchar(10), t float):=
  SELECT * FROM link
    UNION 
  SELECT link.ori, travel.des, link.t + travel.t
  FROM link, travel 
  WHERE link.des=travel.ori;
