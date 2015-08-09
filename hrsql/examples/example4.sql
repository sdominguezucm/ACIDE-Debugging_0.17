
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

travel(ori varchar(10), des varchar(10), time float):=
  SELECT * FROM link
    UNION 
  SELECT link.ori, travel.des, link.time + travel.time
  FROM link, travel 
  WHERE link.des=travel.ori;

reachable(ori varchar(10), des varchar(10)) :=
  SELECT link.ori, link.des FROM link
    UNION
  SELECT link.ori, reachable.des 
  FROM link,reachable 
  WHERE link.des = reachable.ori;

departFromMad(ori varchar(10), des varchar(10)) :=
  SELECT reachable.ori, reachable.des 
  FROM reachable
  WHERE (reachable.ori = 'MAD' OR reachable.des = 'MAD');
  
avoidMad(ori varchar(10), des varchar(10)) :=
  SELECT reachable.ori, reachable.des FROM reachable
    EXCEPT
  SELECT departFromMad.ori, departFromMad.des FROM departFromMad;
  
avoidMad2(ori varchar(10), des varchar(10)) :=
  SELECT * FROM reachable
    EXCEPT
  SELECT * FROM reachable
  WHERE (reachable.ori = 'MAD' OR reachable.des = 'MAD');

val_to_mad(time float) :=
  ASSUME
    SELECT * FROM boat WHERE boat.time > 1 NOT IN link
  SELECT travel.time FROM travel WHERE travel.ori = 'MAD' and travel.des = 'VDE';

hyp_travel1(ori varchar(10), des varchar(10), time float) :=
  ASSUME
    (SELECT * FROM bus WHERE bus.ori = 'VDE' UNION SELECT * FROM flight) NOT IN link,
     SELECT 'RES','SPC',1.5 IN boat
  SELECT * FROM link
    UNION
  SELECT link.ori, hyp_travel1.des, link.time + hyp_travel1.time 
  FROM link, hyp_travel1
  WHERE link.des = hyp_travel1.ori;
 
hyp_travel2(ori varchar(10), des varchar(10), time float) :=
  ASSUME
    SELECT * FROM bus WHERE bus.ori = 'VDE' UNION SELECT * FROM flight NOT IN link,
    SELECT 'RES','SPC',1.5 IN boat
  SELECT * FROM travel;
