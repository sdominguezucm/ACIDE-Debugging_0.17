/abolish
/log f.log
%%%%%%%%%%%%
% DATALOG
%%%%%%%%%%%%

-- :-type(flight(origin:string,destination:string,time:real)).

-- /assert flight(london,ny,9).
-- /assert flight(mad,par,1.5).
-- /assert flight(par,ny,10.0).

-- :-type(travel(origin:string,destination:string,time:real)).
-- /assert travel(X,Y,T) :- flight(X,Y,T).
-- /assert travel(X,Y,T) :- flight(X,Z,T1), travel(Z,Y,T2), T=T1+T2.

-- % Assuming a flight between Madrid and London in 2 hours, how long will it take to reach New York from Madrid?
-- % ?- flight(mad,london,2) => travel(mad,ny,T).


%%%%%%%%%%%%
% SQL
%%%%%%%%%%%%

----------------------------------------------------
-- PRIMERA PROPUESTA: SOBRECARGA DE WITH
----------------------------------------------------


/hypothetical on
/show_compilations on
/compact_listings on

CREATE OR REPLACE TABLE flight(origin string, destination string, time real)
--CREATE OR REPLACE TABLE travel(origin string, destination string, time real)

INSERT INTO flight VALUES('london','ny',9.0);
INSERT INTO flight VALUES('mad','par',1.5);
INSERT INTO flight VALUES('par','ny',10.0);


--CREATE VIEW travel(origin,destination,time) AS SELECT * FROM flight;
CREATE OR REPLACE VIEW travel(origin,destination,time) AS WITH connected(origin,destination,time) AS SELECT * FROM flight UNION SELECT flight.origin,connected.destination,flight.time+connected.time FROM flight,connected WHERE flight.destination = connected
.origin SELECT * FROM connected;

----------------------------------------------------
-- EJEMPLO 1
----------------------------------------------------
-- % ?- flight(mad,london,2) => travel(mad,ny,T).
-- % Con esquema
-- WITH flight(origin,destination,flight) AS 
--        SELECT 'mad','london',2.0 
-- SELECT time 
-- FROM travel 
-- WHERE origin='mad' AND destination='ny'

WITH flight(origin,destination,flight) AS SELECT 'mad','london',2.0 SELECT time FROM travel WHERE origin='mad' AND destination='ny'

-- % Sin esquema
-- WITH flight AS SELECT 'mad','london',2.0 
-- SELECT time 
-- FROM travel 
-- WHERE origin='mad' AND destination='ny'

-- % M�s en general
-- WITH Schema1 AS AssumedRelation1, 
--      ... ,
--      SchemaN AS AssumedRelationN
-- SELECT ProjList 
-- FROM Relation1, ... , RelationM

% Notas:
% - Todas las relaciones nuevas se asumen temporalmente en el WITH seg�n el est�ndar SQL
% - En la propuesta, para a�adir extensional o intensionalmente tuplas a una relaci�n, 
%   se usa el mismo nombre de una relaci�n existente. El est�ndar SQL no lo soporta, claro

----------------------------------------------------
-- EJEMPLO 2
----------------------------------------------------
% Otro ejemplo.
% Si s�lo existe la relaci�n flight y asumimos la relaci�n travel:

DROP VIEW travel

-- WITH travel(origin,destination,time) AS 
-- (SELECT * FROM flight) 
-- UNION 
-- (SELECT flight.origin,travel.destination,flight.time+travel.time
--  FROM flight, travel 
--  WHERE flight.destination = travel.origin
-- )
-- SELECT * FROM travel;

/hypothetical off

WITH travel(origin,destination,time) AS (SELECT * FROM flight) UNION (SELECT flight.origin,travel.destination,flight.time+travel.time FROM flight, travel WHERE flight.destination = travel.origin) SELECT * FROM travel;

-- % Pero esto ya funciona en SQL est�ndar. 

----------------------------------------------------
-- EJEMPLO 3
----------------------------------------------------

--Otro caso es que s�lo existiese la vista:
CREATE VIEW travel(origin,destination,time) AS SELECT * FROM flight

-- % A�adir la cl�usula recursiva ser�a:
/hypothetical on

-- WITH travel(origin,destination,time) AS 
-- (SELECT flight.origin,travel.destination,flight.time+travel.time 
--  FROM flight,travel 
--  WHERE flight.destination = travel.origin
-- )
-- SELECT * FROM travel;
WITH travel(origin,destination,time) AS (SELECT flight.origin,travel.destination,flight.time+travel.time FROM flight,travel  WHERE flight.destination = travel.origin) SELECT * FROM travel;

% El sistema tendr�a que construir la vista asumida como la uni�n de la nueva 
% definici�n y la antigua para procesar la consulta hipot�tica (y despu�s 
% recuperar la vista original)

/nolog

----------------------------------------------------
-- SEGUNDA PROPUESTA: NUEVA CONSTRUCCI�N ASSUME
----------------------------------------------------

----------------------------------------------------
-- CASO 1
----------------------------------------------------

% 
% Se asume un �tomo A para la relaci�n R en una consulta G
% G ::= D => G
% D ::= A 

% ASSUME A' IN R G'

-- EJEMPLO 1

-- ASSUME 
--   SELECT 'mad','london',2.0 
-- IN 
--   flight(origin,destination,time) 
-- SELECT * 
-- FROM travel;

ASSUME SELECT 'mad','london',2.0 IN flight(origin,destination,time) SELECT * FROM travel;

----------------------------------------------------
-- CASO 2
----------------------------------------------------

% Se asume una cl�usula D para la relaci�n R en una consulta G
% G ::= D => G
% D ::= G => A

% ASSUME D' IN R G'

-- EJEMPLO 3

ASSUME
  (SELECT flight.origin,travel.destination,flight.time+travel.time 
   FROM flight,travel  
   WHERE flight.destination = travel.origin)
IN
  travel(origin,destination,time)
SELECT * 
FROM travel;

ASSUME
  (SELECT flight.origin,connected.destination
   FROM flight,connected  
   WHERE flight.destination = connected.origin)
IN
  connected(origin,destination)
SELECT * 
FROM connected;

ASSUME (SELECT flight.origin,connected.destination FROM flight,connected WHERE flight.destination = connected.origin) IN connected(origin,destination) SELECT * FROM connected;

