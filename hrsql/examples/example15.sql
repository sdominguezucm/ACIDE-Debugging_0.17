-- Aggregates:

boat(ori varchar(10),des varchar(10), time float) :=
  SELECT 'SPC','TFN',2   FROM dual
    UNION ALL
  SELECT 'TFS','GMZ',1   FROM dual
    UNION ALL
  SELECT 'GMZ','VDE',1.5 FROM dual;

ASSUME SELECT * FROM boat HAVING time = MIN(time) NOT IN boat SELECT AVG(time) FROM boat;
