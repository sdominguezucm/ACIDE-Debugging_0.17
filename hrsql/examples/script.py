###############################################################
# HR-SQL 2.0
# Python script for:
#   - Database Filename   : C:\fernan\research\BDDEDUC\HHC\prole15\wiki\HRSQL.2.0\hrsql\examples\example2.sql
#   - This Python Filename: script.py
#   - ODBC Connection     : mysql
#   - RDBMS               : MySQL
#   - Prolog System       : sicstus
#   - Date                : 2015-5-20
#   - Time                : 12:59:39
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
  cursor.execute("DROP TABLE fib;")
except:
  pass
cursor.execute("CREATE TABLE fib(n INTEGER, f INTEGER);")

try:
  cursor.execute("DROP TABLE fib1;")
except:
  pass
cursor.execute("CREATE TABLE fib1(n INTEGER, f INTEGER);")

try:
  cursor.execute("DROP TABLE fib2;")
except:
  pass
cursor.execute("CREATE TABLE fib2(n INTEGER, f INTEGER);")

# Creating RN' Temporary Tables:

# Stratum 1:
cursor.execute("INSERT INTO fib(n,f) SELECT ALL * FROM (SELECT ALL 0,1 FROM dual  UNION SELECT ALL 1,1 FROM dual ) AS alias2")

# Views for DBMS without EXCEPT:
try:
  cursor.execute("DROP VIEW fib2_temp;")
except:
  pass
cursor.execute("CREATE VIEW fib2_temp(n,f) AS SELECT ALL fib.n,fib.f FROM fib")

try:
  cursor.execute("DROP VIEW fib1_temp;")
except:
  pass
cursor.execute("CREATE VIEW fib1_temp(n,f) AS SELECT ALL fib.n,fib.f FROM fib")

try:
  cursor.execute("DROP VIEW fib_temp;")
except:
  pass
cursor.execute("CREATE VIEW fib_temp(n,f) AS SELECT ALL fib1.n + 1,fib1.f + fib2.f FROM fib1, fib2 WHERE (fib1.n = fib2.n + 1 AND fib1.n < 10)")

ch = True
while ch:
  newTuples = 0
  cursor.execute("INSERT INTO fib2(n,f) SELECT ALL * FROM (SELECT ALL * FROM fib2_temp WHERE (n,f) NOT IN ( SELECT ALL * FROM fib2 AS alias0 )) AS alias1")
  newTuples = newTuples + cursor.rowcount
  cursor.execute("INSERT INTO fib1(n,f) SELECT ALL * FROM (SELECT ALL * FROM fib1_temp WHERE (n,f) NOT IN ( SELECT ALL * FROM fib1 AS alias0 )) AS alias1")
  newTuples = newTuples + cursor.rowcount
  cursor.execute("INSERT INTO fib(n,f) SELECT ALL * FROM (SELECT ALL * FROM fib_temp WHERE (n,f) NOT IN ( SELECT ALL * FROM fib AS alias0 )) AS alias1")
  newTuples = newTuples + cursor.rowcount
  if (newTuples == 0): ch = False
# Dropping Temp Views:
cursor.execute("DROP VIEW fib2_temp;")
cursor.execute("DROP VIEW fib1_temp;")
cursor.execute("DROP VIEW fib_temp;")

# Dropping RN' Temporary Tables:

# Commit changes:
conn.commit()

# If successful, print Success:
print("Success.")
