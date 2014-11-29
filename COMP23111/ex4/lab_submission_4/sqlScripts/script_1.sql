-- set echo ON to show SQL statements
SET ECHO ON

-- set width of a line (before wrapping to the next line)
SET LIN 100
SET PAGESIZE 10000

-- changes the display format of the column
SPOOL LabEx4Out.lst

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
SELECT a.*
FROM Artist a
JOIN MemberOf mog ON mog.solo_artistic_name = a.artistic_name
JOIN MemberOf mos ON mos.solo_artistic_name = a.artistic_name
WHERE mog.group_artistic_name = 'Goldfrat'
AND mos.group_artistic_name = 'Scandal';

SPOOL OFF
