
boat(ori varchar(10),des varchar(10), time float) :=
  SELECT 'SPC','TFN',2   FROM dual
    UNION ALL
  SELECT 'TFS','GMZ',1   FROM dual
    UNION ALL
  SELECT 'GMZ','VDE',1.5 FROM dual;

flight(ori varchar(10),des varchar(10), time float) :=
  SELECT 'MAD','TFN',2   FROM dual
    UNION ALL
  SELECT 'MAD','LPA',3   FROM dual
    UNION ALL
  SELECT 'MP','VDE',1    FROM dual;

bus(ori varchar(10),des varchar(10), time float) :=
  SELECT 'TFN','TFS',2.5 from dual
    UNION ALL
  SELECT 'LPA','MP',2    from dual
    UNION ALL
  SELECT 'VDE','RES',1   from dual;

link(ori varchar(10),des varchar(10), time float) := 
  SELECT * FROM flight 
    UNION ALL 
  SELECT * FROM boat
    UNION ALL 
  SELECT * FROM bus;

hyp_travel1(ori varchar(10), des varchar(10), time float) :=
  ASSUME
    (SELECT * FROM bus WHERE bus.ori = 'VDE' UNION SELECT * FROM flight) NOT IN link,
     SELECT 'RES','SPC',1.5 IN boat
  SELECT * FROM link
    UNION
  SELECT link.ori, hyp_travel1.des, link.time + hyp_travel1.time 
  FROM link, hyp_travel1
  WHERE link.des = hyp_travel1.ori;
 
min_travel(ori varchar(10), des varchar(10), time float) :=
  SELECT ori, des, MIN(time)
  FROM hyp_travel1
  GROUP BY ori, des;
  