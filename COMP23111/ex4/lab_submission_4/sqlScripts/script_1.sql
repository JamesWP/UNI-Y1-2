-- set echo ON to show SQL statements
SET ECHO ON

-- set width of a line (before wrapping to the next line)
SET LIN 100
SET PAGESIZE 10000

-- changes the display format of the column
SPOOL ../sqlOutputs/LabEx4Out.lst

--1
COLUMN recordedby FORMAT a15
COLUMN name FORMAT a13
COLUMN working_length FORMAT a14
COLUMN working_title FORMAT a19

SELECT *
FROM MasterTrack mt
JOIN SoundEngineer se ON se.sound_eng_ID = mt.editedBy;

--2
COLUMN artistic_name FORMAT a16
COLUMN real_name FORMAT a19

SELECT sa.*
FROM SoloArtist sa
JOIN MemberOf mo ON mo.solo_artistic_name = sa.artistic_name
WHERE mo.group_artistic_name = 'Goldfrat';

--3
COLUMN telephone FORMAT a16
COLUMN name FORMAT a20

SELECT mp.telephone, m.name
FROM ManPhone mp
LEFT OUTER JOIN Manager m ON m.manager_ID = mp.manager_ID;

--4
SELECT count(1) as Total_Tracks
FROM MasterTrack;

--5
SELECT a.*
FROM Artist a
JOIN MemberOf mo ON mo.solo_artistic_name = a.artistic_name
WHERE mo.group_artistic_name IN ('Goldfrat','Scandal');

--6
SELECT a.artistic_name
FROM Artist a
JOIN MemberOf mo ON mo.solo_artistic_name = a.artistic_name
WHERE mo.group_artistic_name = 'Scandal'
INTERSECT
SELECT a.artistic_name
FROM Artist a
JOIN MemberOf mo ON mo.solo_artistic_name = a.artistic_name
WHERE mo.group_artistic_name = 'Goldfrat';

--7
SELECT a.artistic_name
FROM Artist a
JOIN MemberOf mo ON mo.solo_artistic_name = a.artistic_name
WHERE mo.group_artistic_name = 'Goldfrat'
INTERSECT
SELECT a.artistic_name
FROM Artist a
JOIN MemberOf mo ON mo.solo_artistic_name = a.artistic_name
WHERE mo.group_artistic_name != 'Scandal';

--8 
CREATE OR REPLACE VIEW Scandal_Group AS
SELECT a.artistic_name
FROM Artist a
JOIN MemberOf mo ON mo.solo_artistic_name = a.artistic_name
WHERE mo.group_artistic_name = 'Scandal';

--9
CREATE OR REPLACE VIEW Goldfrat_Group AS
SELECT a.artistic_name
FROM Artist a
JOIN MemberOf mo ON mo.solo_artistic_name = a.artistic_name
WHERE mo.group_artistic_name = 'Goldfrat';

--10
SELECT artistic_name
FROM Goldfrat_Group
INTERSECT
SELECT artistic_name
FROM Scandal_Group;

--11
SELECT price
FROM Catalogue
WHERE stock in (
	SELECT max(stock) as max_stock
	FROM Catalogue
);

--12
SELECT buyer_id, name
FROM Buyer
WHERE buyer_id in (
	SELECT buyer_id
  FROM CatOrder
);

--13
CREATE OR REPLACE VIEW Album_20_People_In_A_Field AS
SELECT ft.released_title,at.sequence
FROM AlbumTrack at
JOIN FinishedTrack ft on ft.originatesFrom = at.track_id AND ft.version = at.version
WHERE at.album_id = '1c'
ORDER BY at.sequence;

--14
SELECT sequence, released_title
FROM Album_20_People_in_a_field;

--Part3

--1
SELECT ft.released_title,at.sequence
FROM AlbumTrack at
JOIN FinishedTrack ft on ft.originatesFrom = at.track_id AND ft.version = at.version
WHERE at.album_id = '2c'
ORDER BY at.sequence;

--2
UPDATE Catalogue
SET stock = stock - 1
WHERE release_date = '20-JAN-10' AND album_id = '2v' AND stock>0;

UPDATE Catalogue
SET stock = stock - 1
WHERE release_date = '24-DEC-10' AND album_id = '1t' AND stock>0;


SPOOL OFF
