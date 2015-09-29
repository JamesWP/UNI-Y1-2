-- set echo ON to show SQL statements
SET ECHO ON

-- set width of a line (before wrapping to the next line)
SET LIN 150

-- changes the display format of the column
COLUMN title FORMAT a25
COLUMN createdby FORMAT a23

SPOOL LabExNOut.lst

SELECT * FROM album;

-- set width of a line (before wrapping to the next line)
SET LIN 30

DESCRIBE album;

-- other SQL statements here

SPOOL OFF
