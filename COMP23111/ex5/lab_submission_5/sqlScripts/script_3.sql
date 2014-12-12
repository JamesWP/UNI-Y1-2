-- set echo ON to show SQL statements
SET ECHO ON

-- set width of a line (before wrapping to the next line)
SET LIN 150

-- changes the display format of the column
COLUMN title FORMAT a25
COLUMN createdby FORMAT a23

SPOOL ../sqlOutputs/LabEx5Part2.1Out.lst

CREATE OR REPLACE VIEW AlbumDistribution AS
SELECT al.album_id, al.title,'v' is_distributed_as
FROM Album al
JOIN VinylAlbum va ON va.album_id = al.album_id
UNION
SELECT al.album_id, al.title,'c' is_distributed_as
FROM Album al
JOIN CDAlbum ca ON ca.album_id = al.album_id
UNION
SELECT al.album_id, al.title,'t' is_distributed_as
FROM Album al
JOIN TapeAlbum ta ON ta.album_id = al.album_id;

--SHOW IT WORKS
COLUMN is_distributed_as FORMAT a20
SELECT * FROM AlbumDistribution;

CREATE OR REPLACE PROCEDURE Album_PlayList
(
	
)

SPOOL OFF
