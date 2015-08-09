-- Aggregates:

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

max_travel_time(total float) := SELECT SUM(time) FROM link;

limited_travel(ori varchar(10), des varchar(10), time float) :=
  SELECT link.ori, link.des, link.time FROM link
    UNION
  SELECT link.ori, limited_travel.des, link.time + limited_travel.time
  FROM link, limited_travel, max_travel_time
  WHERE link.des = limited_travel.ori AND
    link.time + limited_travel.time <= max_travel_time.total;

ASSUME SELECT 'RES','SPC',1 IN flight SELECT * FROM limited_travel;
