-- set echo ON to show SQL statements
SET ECHO ON

-- set width of a line (before wrapping to the next line)
SET LIN 150

-- changes the display format of the column
COLUMN title FORMAT a25
COLUMN createdby FORMAT a23
SET SERVEROUTPUT ON

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
	album_type IN VARCHAR2,
	album_name IN VARCHAR2
)
IS
	sel_album_id VARCHAR2(5);
BEGIN
  SELECT a.album_id INTO sel_album_id
	FROM Album a
	JOIN AlbumDistribution ad on ad.album_id = a.album_id
	WHERE ad.title LIKE album_name and ad.is_distributed_as = album_type and ROWNUM = 1;
	
  DECLARE
		CURSOR tracks IS
			SELECT ft.released_title trackName,a.title albumName,at.sequence
			FROM AlbumTrack at
			JOIN Album a on a.album_id = at.album_id
			JOIN FinishedTrack ft ON ft.originatesFrom = at.track_id AND ft.version = at.version
			WHERE at.album_id = sel_album_id;
	BEGIN
	
		FOR track IN tracks
		LOOP
			DBMS_OUTPUT.put_line (
         track.sequence || ', ' || track.trackName || ' > ' || track.albumName);
		END LOOP;
	END;	
END;
/

SHOW ERRORS PROCEDURE Album_PlayList

EXEC Album_PlayList('c','%F%')

SPOOL OFF
