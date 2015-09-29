-- COMP23111 Fundamentals of Databases
-- 2014-2015
-- AAAF: Tue 13 Aug 2013 09:53:06 BST
-- Edited by: Klitos on Tue 16 Sep 2014 16:20:03 GMT

-- drop all the tables
--      following the dependency graph bottom-up
--      so that there is no need to CASCADE
--
--	(finishedtrack, mastertrack)
-- 	(mastertrack, soundengineer)
-- 	(mastertrack, artist)
-- 	(contractinfo, artist)
-- 	(album, artist)
-- 	(artist, manager)
-- 	(manphone, manager)

DROP TABLE Finishedtrack;
DROP TABLE Mastertrack;
DROP TABLE Soundengineer;
DROP TABLE Contractinfo;
DROP TABLE Album;
DROP TABLE Artist;
DROP TABLE Manphone;
DROP TABLE Manager;

-- end of script

