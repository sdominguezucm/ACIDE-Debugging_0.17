###############################################################
# HR-SQL 2.0
# Python script for:
#   - Database Filename   : C:\fernan\research\BDDEDUC\HHC\prole15\wiki\HRSQL.2.0\hrsql\examples\example4.sql
#   - This Python Filename: script.py
#   - ODBC Connection     : mysql
#   - RDBMS               : MySQL
#   - Prolog System       : swi
#   - Date                : 2015-5-19
#   - Time                : 15:5:16
#
# Run this script with the ODBC connection defined already at 
#   the OS level and the database system up and running.
###############################################################

# Importing modules:
import pyodbc
import sys

# Try to open the ODBC connection:
try:
  conn = pyodbc.connect("DSN=mysql")
except:
  print("Error opening connection 'mysql'.")
  sys.exit()

# Cursor definition:
cursor=conn.cursor()

# Creating RN\RN' Tables:
try:
  cursor.execute("DROP TABLE boat;")
except:
  pass
cursor.execute("CREATE TABLE boat(ori varchar(10), des varchar(10), time FLOAT);")

try:
  cursor.execute("DROP TABLE bus;")
except:
  pass
cursor.execute("CREATE TABLE bus(ori varchar(10), des varchar(10), time FLOAT);")

try:
  cursor.execute("DROP TABLE flight;")
except:
  pass
cursor.execute("CREATE TABLE flight(ori varchar(10), des varchar(10), time FLOAT);")

try:
  cursor.execute("DROP TABLE link;")
except:
  pass
cursor.execute("CREATE TABLE link(ori varchar(10), des varchar(10), time FLOAT);")

try:
  cursor.execute("DROP TABLE reachable;")
except:
  pass
cursor.execute("CREATE TABLE reachable(ori varchar(10), des varchar(10));")

try:
  cursor.execute("DROP TABLE departfrommad;")
except:
  pass
cursor.execute("CREATE TABLE departFromMad(ori varchar(10), des varchar(10));")

try:
  cursor.execute("DROP TABLE avoidmad;")
except:
  pass
cursor.execute("CREATE TABLE avoidMad(ori varchar(10), des varchar(10));")

try:
  cursor.execute("DROP TABLE avoidmad2;")
except:
  pass
cursor.execute("CREATE TABLE avoidMad2(ori varchar(10), des varchar(10));")

try:
  cursor.execute("DROP TABLE hyp_travel1;")
except:
  pass
cursor.execute("CREATE TABLE hyp_travel1(ori varchar(10), des varchar(10), time FLOAT);")

try:
  cursor.execute("DROP TABLE travel;")
except:
  pass
cursor.execute("CREATE TABLE travel(ori varchar(10), des varchar(10), time FLOAT);")

try:
  cursor.execute("DROP TABLE hyp_travel2;")
except:
  pass
cursor.execute("CREATE TABLE hyp_travel2(ori varchar(10), des varchar(10), time FLOAT);")

try:
  cursor.execute("DROP TABLE val_to_mad;")
except:
  pass
cursor.execute("CREATE TABLE val_to_mad(time FLOAT);")

# Creating RN' Temporary Tables:
cursor.execute("CREATE TABLE boat_2(ori varchar(10), des varchar(10), time FLOAT);")
cursor.execute("CREATE TABLE link_0_4(ori varchar(10), des varchar(10), time FLOAT);")
cursor.execute("CREATE TABLE boat_7(ori varchar(10), des varchar(10), time FLOAT);")
cursor.execute("CREATE TABLE link_5_8(ori varchar(10), des varchar(10), time FLOAT);")
cursor.execute("CREATE TABLE travel_6_9(ori varchar(10), des varchar(10), time FLOAT);")
cursor.execute("CREATE TABLE link_10(ori varchar(10), des varchar(10), time FLOAT);")
cursor.execute("CREATE TABLE travel_11(ori varchar(10), des varchar(10), time FLOAT);")

# Stratum 1:
cursor.execute("INSERT INTO boat(ori,des,time) SELECT ALL * FROM (SELECT ALL 'SPC','TFN',2 FROM dual  UNION ALL SELECT ALL 'TFS','GMZ',1 FROM dual  UNION ALL SELECT ALL 'GMZ','VDE',1.5 FROM dual  ) AS alias2")

# Stratum 2:
cursor.execute("INSERT INTO bus(ori,des,time) SELECT ALL * FROM (SELECT ALL 'TFN','TFS',2.5 FROM dual  UNION ALL SELECT ALL 'LPA','MP',2 FROM dual  UNION ALL SELECT ALL 'VDE','RES',1 FROM dual  ) AS alias3")

# Stratum 3:
cursor.execute("INSERT INTO flight(ori,des,time) SELECT ALL * FROM (SELECT ALL 'MAD','TFN',2 FROM dual  UNION ALL SELECT ALL 'MAD','LPA',3 FROM dual  UNION ALL SELECT ALL 'MP','VDE',1 FROM dual  ) AS alias4")

# Stratum 4:
cursor.execute("INSERT INTO link(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM flight  UNION ALL SELECT ALL * FROM boat  UNION ALL SELECT ALL * FROM bus  ) AS alias5")

# Stratum 5:
cursor.execute("INSERT INTO reachable(ori,des) SELECT ALL * FROM (SELECT ALL link.ori,link.des FROM link) AS alias6")

# Views for DBMS without EXCEPT:
try:
  cursor.execute("DROP VIEW reachable_temp;")
except:
  pass
cursor.execute("CREATE VIEW reachable_temp(ori,des) AS SELECT ALL link.ori,reachable.des FROM link, reachable WHERE link.des = reachable.ori")

ch = True
while ch:
  newTuples = 0
  cursor.execute("INSERT INTO reachable(ori,des) SELECT ALL * FROM (SELECT ALL * FROM reachable_temp WHERE (ori,des) NOT IN ( SELECT ALL * FROM reachable AS alias0 )) AS alias1")
  newTuples = newTuples + cursor.rowcount
  if (newTuples == 0): ch = False
# Dropping Temp Views:
cursor.execute("DROP VIEW reachable_temp;")

# Stratum 6:
cursor.execute("INSERT INTO departFromMad(ori,des) SELECT ALL * FROM (SELECT ALL reachable.ori,reachable.des FROM reachable WHERE (reachable.ori = 'MAD' OR reachable.des = 'MAD')) AS alias2")

# Stratum 7:
cursor.execute("CREATE VIEW avoidMad_temp_12(ori,des) AS SELECT ALL reachable.ori,reachable.des FROM reachable")
cursor.execute("INSERT INTO avoidMad(ori,des) SELECT ALL * FROM (SELECT ALL * FROM avoidMad_temp_12 WHERE (ori,des) NOT IN ( SELECT ALL departFromMad.ori,departFromMad.des FROM departFromMad )) AS alias3")
cursor.execute("DROP VIEW avoidmad_temp_12;")

# Stratum 8:
cursor.execute("CREATE VIEW avoidMad2_temp_13(ori,des) AS SELECT ALL * FROM reachable")
cursor.execute("INSERT INTO avoidMad2(ori,des) SELECT ALL * FROM (SELECT ALL * FROM avoidMad2_temp_13 WHERE (ori,des) NOT IN ( SELECT ALL * FROM reachable WHERE (reachable.ori = 'MAD' OR reachable.des = 'MAD') )) AS alias4")
cursor.execute("DROP VIEW avoidmad2_temp_13;")

# Stratum 9:
cursor.execute("INSERT INTO boat_2(ori,des,time) SELECT ALL * FROM (SELECT ALL 'SPC','TFN',2 FROM dual  UNION ALL SELECT ALL 'TFS','GMZ',1 FROM dual  UNION ALL SELECT ALL 'GMZ','VDE',1.5 FROM dual    UNION SELECT ALL 'RES','SPC',1.5 FROM dual ) AS alias5")

# Stratum 10:
cursor.execute("CREATE VIEW link_0_4_temp_14(ori,des,time) AS SELECT ALL * FROM flight  UNION ALL SELECT ALL * FROM boat_2  UNION ALL SELECT ALL * FROM bus  ")
cursor.execute("INSERT INTO link_0_4(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM link_0_4_temp_14 WHERE (ori,des,time) NOT IN ( SELECT ALL * FROM bus WHERE bus.ori = 'VDE'  UNION SELECT ALL * FROM flight  )) AS alias6")
cursor.execute("DROP VIEW link_0_4_temp_14;")

# Stratum 11:
cursor.execute("INSERT INTO hyp_travel1(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM link_0_4) AS alias7")

# Views for DBMS without EXCEPT:
try:
  cursor.execute("DROP VIEW hyp_travel1_temp;")
except:
  pass
cursor.execute("CREATE VIEW hyp_travel1_temp(ori,des,time) AS SELECT ALL link_0_4.ori,hyp_travel1.des,link_0_4.time + hyp_travel1.time FROM link_0_4, hyp_travel1 WHERE link_0_4.des = hyp_travel1.ori")

ch = True
while ch:
  newTuples = 0
  cursor.execute("INSERT INTO hyp_travel1(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM hyp_travel1_temp WHERE (ori,des,time) NOT IN ( SELECT ALL * FROM hyp_travel1 AS alias0 )) AS alias1")
  newTuples = newTuples + cursor.rowcount
  if (newTuples == 0): ch = False
# Dropping Temp Views:
cursor.execute("DROP VIEW hyp_travel1_temp;")

# Stratum 12:
cursor.execute("INSERT INTO boat_7(ori,des,time) SELECT ALL * FROM (SELECT ALL 'SPC','TFN',2 FROM dual  UNION ALL SELECT ALL 'TFS','GMZ',1 FROM dual  UNION ALL SELECT ALL 'GMZ','VDE',1.5 FROM dual    UNION SELECT ALL 'RES','SPC',1.5 FROM dual ) AS alias2")

# Stratum 13:
cursor.execute("CREATE VIEW link_5_8_temp_15(ori,des,time) AS SELECT ALL * FROM flight  UNION ALL SELECT ALL * FROM boat_7  UNION ALL SELECT ALL * FROM bus  ")
cursor.execute("INSERT INTO link_5_8(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM link_5_8_temp_15 WHERE (ori,des,time) NOT IN ( SELECT ALL * FROM bus WHERE bus.ori = 'VDE'  UNION SELECT ALL * FROM flight  )) AS alias3")
cursor.execute("DROP VIEW link_5_8_temp_15;")

# Stratum 14:
cursor.execute("INSERT INTO travel_6_9(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM link_5_8) AS alias4")

# Views for DBMS without EXCEPT:
try:
  cursor.execute("DROP VIEW travel_6_9_temp;")
except:
  pass
cursor.execute("CREATE VIEW travel_6_9_temp(ori,des,time) AS SELECT ALL link_5_8.ori,travel_6_9.des,link_5_8.time + travel_6_9.time FROM link_5_8, travel_6_9 WHERE link_5_8.des = travel_6_9.ori")

ch = True
while ch:
  newTuples = 0
  cursor.execute("INSERT INTO travel_6_9(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM travel_6_9_temp WHERE (ori,des,time) NOT IN ( SELECT ALL * FROM travel_6_9 AS alias0 )) AS alias1")
  newTuples = newTuples + cursor.rowcount
  if (newTuples == 0): ch = False
# Dropping Temp Views:
cursor.execute("DROP VIEW travel_6_9_temp;")

# Stratum 15:
cursor.execute("INSERT INTO hyp_travel2(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM travel_6_9) AS alias2")

# Stratum 16:
cursor.execute("CREATE VIEW link_10_temp_16(ori,des,time) AS SELECT ALL * FROM flight  UNION ALL SELECT ALL * FROM boat  UNION ALL SELECT ALL * FROM bus  ")
cursor.execute("INSERT INTO link_10(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM link_10_temp_16 WHERE (ori,des,time) NOT IN ( SELECT ALL * FROM boat WHERE boat.time > 1 )) AS alias3")
cursor.execute("DROP VIEW link_10_temp_16;")

# Stratum 17:
cursor.execute("INSERT INTO travel(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM link) AS alias4")

# Views for DBMS without EXCEPT:
try:
  cursor.execute("DROP VIEW travel_temp;")
except:
  pass
cursor.execute("CREATE VIEW travel_temp(ori,des,time) AS SELECT ALL link.ori,travel.des,link.time + travel.time FROM link, travel WHERE link.des = travel.ori")

ch = True
while ch:
  newTuples = 0
  cursor.execute("INSERT INTO travel(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM travel_temp WHERE (ori,des,time) NOT IN ( SELECT ALL * FROM travel AS alias0 )) AS alias1")
  newTuples = newTuples + cursor.rowcount
  if (newTuples == 0): ch = False
# Dropping Temp Views:
cursor.execute("DROP VIEW travel_temp;")

# Stratum 18:
cursor.execute("INSERT INTO travel_11(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM link_10) AS alias2")

# Views for DBMS without EXCEPT:
try:
  cursor.execute("DROP VIEW travel_11_temp;")
except:
  pass
cursor.execute("CREATE VIEW travel_11_temp(ori,des,time) AS SELECT ALL link_10.ori,travel_11.des,link_10.time + travel_11.time FROM link_10, travel_11 WHERE link_10.des = travel_11.ori")

ch = True
while ch:
  newTuples = 0
  cursor.execute("INSERT INTO travel_11(ori,des,time) SELECT ALL * FROM (SELECT ALL * FROM travel_11_temp WHERE (ori,des,time) NOT IN ( SELECT ALL * FROM travel_11 AS alias0 )) AS alias1")
  newTuples = newTuples + cursor.rowcount
  if (newTuples == 0): ch = False
# Dropping Temp Views:
cursor.execute("DROP VIEW travel_11_temp;")

# Stratum 19:
cursor.execute("INSERT INTO val_to_mad(time) SELECT ALL * FROM (SELECT ALL travel_11.time FROM travel_11 WHERE (travel_11.ori = 'MAD' AND travel_11.des = 'VDE')) AS alias2")

# Dropping RN' Temporary Tables:
cursor.execute("DROP TABLE boat_2;")
cursor.execute("DROP TABLE link_0_4;")
cursor.execute("DROP TABLE boat_7;")
cursor.execute("DROP TABLE link_5_8;")
cursor.execute("DROP TABLE travel_6_9;")
cursor.execute("DROP TABLE link_10;")
cursor.execute("DROP TABLE travel_11;")

# Commit changes:
conn.commit()

# If successful, print Success:
print("Success.")
