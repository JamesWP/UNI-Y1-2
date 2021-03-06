-- COMP23111 Fundamentals of Databases
-- 2014-2015
-- AAAF: Tue 13 Aug 2013 09:53:06 BST
-- Edited by: Klitos on Tue 16 Sep 2014 18:42:12 GMT

-- create all the tables

CREATE TABLE Manager(
	manager_ID	INTEGER,
	name		VARCHAR2(50) NOT NULL,
	CONSTRAINT 	m_id_pk PRIMARY KEY(manager_ID));

CREATE TABLE ManPhone(
	manager_ID	INTEGER,
	telephone	VARCHAR2(40),
	CONSTRAINT 	mp_id_pk PRIMARY KEY(manager_ID, telephone),
	CONSTRAINT 	mp_id_fk FOREIGN KEY(manager_ID) REFERENCES Manager(manager_ID));

CREATE TABLE Artist(
	artistic_name   VARCHAR2(40),
	genre		VARCHAR2(20) NOT NULL,
	managedBy	INTEGER NOT NULL,
	CONSTRAINT 	a_artistic_name_pk PRIMARY KEY(artistic_name),
	CONSTRAINT 	a_managedBy_fk FOREIGN KEY(managedBy) REFERENCES Manager(manager_ID));

CREATE TABLE ContractInfo(
	hasContract	VARCHAR2(40),
	date_from	DATE NOT NULL,
	date_to		DATE NOT NULL,
	duration	INTEGER,
	CONSTRAINT 	ci_contract_id_pk PRIMARY KEY(hasContract, date_from, date_to),
	CONSTRAINT 	ci_hasContract_fk FOREIGN KEY(hasContract) REFERENCES Artist(artistic_name));
	
 CREATE TABLE Album (
	album_ID	VARCHAR2(5),
	title		VARCHAR2(80),
	createdBy	VARCHAR2(40),
	CONSTRAINT 	a_album_id_pk PRIMARY KEY(album_ID),
	CONSTRAINT 	a_createdBy_fk FOREIGN KEY(createdBy) REFERENCES Artist(artistic_name));

CREATE TABLE SoundEngineer(
	sound_eng_ID	INTEGER,
	name	VARCHAR2(50) NOT NULL,
	CONSTRAINT 	se_sound_eng_id_pk PRIMARY KEY(sound_eng_ID));

CREATE TABLE MasterTrack (
	track_ID	INTEGER,
	working_title	VARCHAR2(40),
	working_length	VARCHAR2(40),
	recordedBy	VARCHAR2(40),
	editedBy	INTEGER,
	CONSTRAINT 	mt_track_id_pk PRIMARY KEY(track_ID),
	CONSTRAINT 	mt_recordedBy_fk FOREIGN KEY(recordedBy) REFERENCES Artist(artistic_name),
	CONSTRAINT 	mt_editedBy_fk FOREIGN KEY(editedBy) REFERENCES SoundEngineer(sound_eng_ID));

CREATE TABLE FinishedTrack (
	originatesFrom   INTEGER,
	version  INTEGER,
	released_title	VARCHAR2(40),
	final_length	VARCHAR2(40),
	CONSTRAINT 	ft_id_pk PRIMARY KEY(originatesFrom, version),
	CONSTRAINT 	ft_originatesFrom_fk FOREIGN KEY(originatesFrom) REFERENCES MasterTrack(track_ID));

CREATE TABLE AlbumTrack (
  album_ID VARCHAR2(5),
  track_ID INTEGER,
  version INTEGER,
  sequence INTEGER,
  CONSTRAINT at_pk PRIMARY KEY(album_ID,track_ID,version),
  CONSTRAINT at_album_fk FOREIGN KEY(album_ID) REFERENCES Album(album_ID),
  CONSTRAINT at_track_fk FOREIGN KEY(track_ID,version) REFERENCES FinishedTrack(originatesFrom,version));

CREATE TABLE Catalogue (
  release_date DATE NOT NULL,
  album_ID VARCHAR2(5),
  price NUMBER(10,2),
  stock INTEGER,
  CONSTRAINT ca_pk PRIMARY KEY(release_date,album_ID),
  CONSTRAINT ca_album_pk FOREIGN KEY(album_ID) REFERENCES Album(album_ID));

CREATE TABLE Buyer (
  buyer_ID INTEGER,
  date_registered DATE NOT NULL,
  name VARCHAR2(40),
  CONSTRAINT bu_pk PRIMARY KEY(buyer_id));

CREATE TABLE CatOrder (
  order_no INTEGER,
  date_placed DATE NOT NULL,
  date_dispatched DATE NOT NULL,
  buyer_ID INTEGER,
  CONSTRAINT or_pk PRIMARY KEY (order_no),
  CONSTRAINT or_buyer_fk FOREIGN KEY (buyer_ID) REFERENCES Buyer(buyer_ID));

CREATE TABLE CatOrderItem (
  order_no INTEGER,
  release_date DATE NOT NULL,
  album_ID VARCHAR2(5),
  CONSTRAINT oi_pk PRIMARY KEY(order_no,release_date,album_ID),
  CONSTRAINT oi_order_fk FOREIGN KEY(order_no) REFERENCES CatOrder(order_no),
  CONSTRAINT oi_catalogue_fk FOREIGN KEY(release_date,album_ID) REFERENCES Catalogue(release_date,album_ID));

CREATE TABLE SoloArtist (
  artistic_name VARCHAR2(40),
  date_first_performed DATE NOT NULL,
  real_name VARCHAR2(40),
  CONSTRAINT sa_pk PRIMARY KEY (artistic_name),
  CONSTRAINT sa_artist_fk FOREIGN KEY (artistic_name) REFERENCES Artist(artistic_name));

CREATE TABLE GroupArtist (
  artistic_name VARCHAR2(40),
  date_formed DATE NOT NULL,
  CONSTRAINT ga_pk PRIMARY KEY (artistic_name),
  CONSTRAINT ga_artist_fk FOREIGN KEY (artistic_name) REFERENCES Artist(artistic_name));

CREATE TABLE MemberOf (
  solo_artistic_name VARCHAR2(40),
  group_artistic_name VARCHAR2(40),
  date_joined DATE NOT NULL,
  CONSTRAINT mo_pk PRIMARY KEY (solo_artistic_name,group_artistic_name),
  CONSTRAINT mo_solo_artist_fk FOREIGN KEY (solo_artistic_name) REFERENCES Artist(artistic_name),
  CONSTRAINT mo_group_artist_fk FOREIGN KEY (group_artistic_name) REFERENCES Artist(artistic_name));

CREATE TABLE VinylAlbum (
  album_ID VARCHAR2(5),
  CONSTRAINT va_pk PRIMARY KEY (album_ID),
  CONSTRAINT va_album_fk FOREIGN KEY (album_ID) REFERENCES Album(album_ID));

CREATE TABLE VinylAlbumColor (
  album_ID VARCHAR2(5),
  color VARCHAR(10),
  CONSTRAINT vac_pk PRIMARY KEY (album_ID,color),
  CONSTRAINT vac_vinyl_album_fk FOREIGN KEY (album_ID) REFERENCES VinylAlbum(album_ID));

CREATE TABLE CDAlbum (
  album_ID VARCHAR2(5),
  CONSTRAINT cd_pk PRIMARY KEY (album_ID),
  CONSTRAINT cd_album_fk FOREIGN KEY (album_ID) REFERENCES Album(album_ID));

CREATE TABLE CDAlbumExtra (
  album_ID VARCHAR2(5),
  extra VARCHAR(10),
  CONSTRAINT cde_pk PRIMARY KEY (album_ID,extra),
  CONSTRAINT cde_album_fk FOREIGN KEY (album_ID) REFERENCES CDAlbum(album_ID));

CREATE TABLE TapeAlbum (
  album_ID VARCHAR2(5),
	label VARCHAR2(10),
  CONSTRAINT ta_pk PRIMARY KEY (album_ID),
  CONSTRAINT ta_album_fk FOREIGN KEY (album_ID) REFERENCES Album(album_ID));


-- populate all the tables

INSERT INTO Manager VALUES (1, 'Freddy Glitter');
INSERT INTO Manager VALUES (2, 'Perry Soft');
INSERT INTO Manager VALUES (3, 'Axel Management Inc');
INSERT INTO Manager VALUES (4, 'New Talent Corp Inc');
INSERT INTO Manager VALUES (5, 'Manchester Events');

INSERT INTO ManPhone VALUES (1, '01612750987');
INSERT INTO ManPhone VALUES (1, '07865058978');
INSERT INTO ManPhone VALUES (2, '01615679876');
INSERT INTO ManPhone VALUES (2, '07789039894');
INSERT INTO ManPhone VALUES (3, '01623728473');
INSERT INTO ManPhone VALUES (3, '07837287492');
INSERT INTO ManPhone VALUES (4, '01789403940');
INSERT INTO ManPhone VALUES (5, '01616374847');
INSERT INTO ManPhone VALUES (5, '01563828248');
INSERT INTO ManPhone VALUES (5, '09495849869');

INSERT INTO Artist VALUES ('Goldfrat', 'Indie', 1);
INSERT INTO Artist VALUES ('Simon Palm', 'RB', 1);
INSERT INTO Artist VALUES ('Flut', 'Soul', 2);
INSERT INTO Artist VALUES ('John Cliff', 'CW', 3);
INSERT INTO Artist VALUES ('Jay Blancard', 'Soul', 3);
INSERT INTO Artist VALUES ('Palmer John', 'Indie', 4);
INSERT INTO Artist VALUES ('Zero7', 'Techno', 4);
INSERT INTO Artist VALUES ('JZ', 'Techno', 4);
INSERT INTO Artist VALUES ('Scandal', 'Dance', 5);
INSERT INTO Artist VALUES ('JK Rowl', 'Dance', 5);
INSERT INTO Artist VALUES ('PJ Blox', 'Dance', 5);

INSERT INTO ContractInfo VALUES ('Goldfrat', '10-oct-2010', '09-oct-2014', 0);
INSERT INTO ContractInfo VALUES ('Simon Palm', '20-aug-2005', '19-aug-2007', 0);
INSERT INTO ContractInfo VALUES ('Simon Palm', '20-sep-2012', '19-aug-2014', 0);
INSERT INTO ContractInfo VALUES ('Flut', '10-aug-2009', '09-sep-2014', 0);
INSERT INTO ContractInfo VALUES ('John Cliff', '10-mar-2006', '10-apr-2012', 0);
INSERT INTO ContractInfo VALUES ('Jay Blancard', '01-jan-2004', '20-jun-2014', 0);
INSERT INTO ContractInfo VALUES ('Palmer John', '12-feb-2006', '01-jun-2008', 0);
INSERT INTO ContractInfo VALUES ('Zero7', '31-dec-2011', '26-feb-2016', 0);
INSERT INTO ContractInfo VALUES ('JZ', '24-mar-2005', '30-dec-2007', 0);
INSERT INTO ContractInfo VALUES ('Scandal', '22-oct-2012', '19-nov-2016', 0);
INSERT INTO ContractInfo VALUES ('JK Rowl', '19-apr-2003', '06-jan-2009', 0);
INSERT INTO ContractInfo VALUES ('PJ Blox', '21-jun-2008', '16-jul-2013', 0);

INSERT INTO Album VALUES ('1c', '20 People in a Field', 'Goldfrat');
INSERT INTO Album VALUES ('1v', '20 People in a Field', 'Goldfrat');
INSERT INTO Album VALUES ('1t', '20 People in a Field', 'Goldfrat');
INSERT INTO Album VALUES ('2c', 'My Feet', 'Flut');
INSERT INTO Album VALUES ('2v', 'My Feet', 'Flut');
INSERT INTO Album VALUES ('3c', 'Debut', 'Jay Blancard');

INSERT INTO SoundEngineer VALUES (1, 'Max Spring');
INSERT INTO SoundEngineer VALUES (2, 'Jay Verbal');
INSERT INTO SoundEngineer VALUES (3, 'Carole Hart');
INSERT INTO SoundEngineer VALUES (4, 'Brix Musta');
INSERT INTO SoundEngineer VALUES (5, 'Karen Croft');

INSERT INTO MasterTrack VALUES (103, 'Hello', '3 mins', 'Goldfrat', 1);
INSERT INTO MasterTrack VALUES (104, 'Me', '3 mins 10 secs', 'Goldfrat', 1);
INSERT INTO MasterTrack VALUES (105, 'You', '3 mins 40 secs', 'Goldfrat', 1);
INSERT INTO MasterTrack VALUES (106, 'Them', '2 mins', 'Goldfrat', 2);
INSERT INTO MasterTrack VALUES (107, 'Us', '50 secs', 'Goldfrat', 1);
INSERT INTO MasterTrack VALUES (108, 'How', '7 mins', 'Goldfrat', 4);
INSERT INTO MasterTrack VALUES (109, 'Be', '8 mins', 'Goldfrat', 1);
INSERT INTO MasterTrack VALUES (110, 'Peace', '4 mins', 'Goldfrat', 1);
INSERT INTO MasterTrack VALUES (111, 'Smile', '2 mins', 'Goldfrat', 1);
INSERT INTO MasterTrack VALUES (203, 'Wow', '3 mins', 'Flut', 3);
INSERT INTO MasterTrack VALUES (204, 'Whizzy', '3 mins 15 secs', 'Flut', 3);
INSERT INTO MasterTrack VALUES (205, 'Who Are You?', '3 mins 30 secs', 'Flut', 3);
INSERT INTO MasterTrack VALUES (206, 'Place and Time', '5 mins', 'Flut', 4);
INSERT INTO MasterTrack VALUES (207, 'Milko', '58 secs', 'Flut', 1);
INSERT INTO MasterTrack VALUES (208, 'Mad', '1 min', 'Flut', 2);
INSERT INTO MasterTrack VALUES (209, 'Bling', '59 secs', 'Flut', 3);
INSERT INTO MasterTrack VALUES (210, 'J', '2 mins 10 secs', 'Flut', 1);
INSERT INTO MasterTrack VALUES (211, 'Jan', '100 secs', 'Flut', 1);
INSERT INTO MasterTrack VALUES (301, 'Power', '2 mins', 'Jay Blancard', 3);
INSERT INTO MasterTrack VALUES (302, 'What is your name?', '4 mins 15 secs', 'Jay Blancard', 3);
INSERT INTO MasterTrack VALUES (303, 'Mix', '3 mins 12 secs', 'Jay Blancard', 3);
INSERT INTO MasterTrack VALUES (304, 'Manic', '2 mins', 'Jay Blancard', 2);
INSERT INTO MasterTrack VALUES (305, 'Streets', '30 secs', 'Jay Blancard', 2);
INSERT INTO MasterTrack VALUES (306, 'Road', '1 min 40 secs', 'Jay Blancard', 5);
INSERT INTO MasterTrack VALUES (307, 'Forest', '60 secs', 'Jay Blancard', 5);
INSERT INTO MasterTrack VALUES (308, 'Deep', '2 mins 40 secs', 'Jay Blancard', 5);
INSERT INTO MasterTrack VALUES (309, 'Danger', '110 secs', 'Jay Blancard', 5);

INSERT INTO FinishedTrack VALUES (103, 2, 'Hello', '3 mins');
INSERT INTO FinishedTrack VALUES (103, 3, 'Extended Hello', '6 mins');
INSERT INTO FinishedTrack VALUES (104, 2, 'Me', '3 mins 10 secs');
INSERT INTO FinishedTrack VALUES (105, 2, 'You', '3 mins 40 secs');
INSERT INTO FinishedTrack VALUES (106, 2, 'Them', '2 mins');
INSERT INTO FinishedTrack VALUES (107, 2, 'Us', '50 secs');
INSERT INTO FinishedTrack VALUES (108, 2, 'How', '7 mins');
INSERT INTO FinishedTrack VALUES (109, 2, 'Be', '8 mins');
INSERT INTO FinishedTrack VALUES (110, 2, 'Peace', '4 mins');
INSERT INTO FinishedTrack VALUES (111, 2, 'Smile', '2 mins');
INSERT INTO FinishedTrack VALUES (203, 2, 'Wow', '3 mins');
INSERT INTO FinishedTrack VALUES (204, 2, 'Whizzy', '3 mins 15 secs');
INSERT INTO FinishedTrack VALUES (205, 2, 'Who Are You?', '3 mins 30 secs');
INSERT INTO FinishedTrack VALUES (206, 2, 'Place and Time', '5 mins');
INSERT INTO FinishedTrack VALUES (207, 2, 'Milko', '58 secs');
INSERT INTO FinishedTrack VALUES (208, 2, 'Mad', '1 min');
INSERT INTO FinishedTrack VALUES (209, 2, 'Bling', '59 secs');
INSERT INTO FinishedTrack VALUES (210, 2, 'J', '2 mins 10 secs');
INSERT INTO FinishedTrack VALUES (211, 2, 'Jan', '100 secs');
INSERT INTO FinishedTrack VALUES (301, 2, 'Power', '2 mins');
INSERT INTO FinishedTrack VALUES (302, 2, 'What is your name?', '4 mins 15 secs');
INSERT INTO FinishedTrack VALUES (303, 2, 'Mix', '3 mins 12 secs');
INSERT INTO FinishedTrack VALUES (304, 2, 'Manic', '2 mins');
INSERT INTO FinishedTrack VALUES (305, 2, 'Streets', '30 secs');
INSERT INTO FinishedTrack VALUES (306, 2, 'Road', '1 min 40 secs');
INSERT INTO FinishedTrack VALUES (307, 2, 'Forest', '60 secs');
INSERT INTO FinishedTrack VALUES (308, 2, 'Deep', '2 mins 40 secs');
INSERT INTO FinishedTrack VALUES (309, 2, 'Danger', '110 secs');
INSERT INTO FinishedTrack VALUES (309, 3, 'Danger Remix', '210 secs');

INSERT INTO Buyer VALUES (1,'28-Oct-2009','Simon Plant');
INSERT INTO Buyer VALUES (2,'04-Aug-2009','Julie Last');
INSERT INTO Buyer VALUES (3,'10-Mar-2012','Rina Blue');
INSERT INTO Buyer VALUES (4,'20-Dec-2009','Kay Srabonian');
INSERT INTO Buyer VALUES (5,'10-Jan-2008','Yos Willis');

INSERT INTO SoloArtist VALUES ('Simon Palm','09-Jan-2004','Fred Maynard');
INSERT INTO SoloArtist VALUES ('John Cliff','12-Dec-2005','John Cliff');
INSERT INTO SoloArtist VALUES ('Jay Blancard','09-Jun-2003','Mervin James');
INSERT INTO SoloArtist VALUES ('Palmer John','12-Feb-2006','Samantha Jules');
INSERT INTO SoloArtist VALUES ('JZ','09-Mar-2004','Julie Zeb');
INSERT INTO SoloArtist VALUES ('JK Rowl','11-Jan-2013','Marisa Jaher');
INSERT INTO SoloArtist VALUES ('PJ Blox','04-Apr-2007','Pandar Rahoux');

INSERT INTO MemberOf VALUES ('Simon Palm','Goldfrat','23-Nov-2010');
INSERT INTO MemberOf VALUES ('Simon Palm','Scandal','12-Nov-2012');
INSERT INTO MemberOf VALUES ('John Cliff','Goldfrat','10-Oct-2012');
INSERT INTO MemberOf VALUES ('Jay Blancard','Goldfrat','10-Oct-2012');
INSERT INTO MemberOf VALUES ('Palmer John','Flut','08-Jun-2009');
INSERT INTO MemberOf VALUES ('Palmer John','Scandal','20-Oct-2012');
INSERT INTO MemberOf VALUES ('JZ','Flut','10-Dec-2009');
INSERT INTO MemberOf VALUES ('JZ','Scandal','20-Oct-2012');
INSERT INTO MemberOf VALUES ('JK Rowl','Flut','08-Jun-2009');
INSERT INTO MemberOf VALUES ('JK Rowl','Zero7','15-Dec-2011');
INSERT INTO MemberOf VALUES ('PJ Blox','Zero7','15-Dec-2011');

INSERT INTO AlbumTrack VALUES ('1c',103,2,1);
INSERT INTO AlbumTrack VALUES ('1c',103,3,2);
INSERT INTO AlbumTrack VALUES ('1c',104,2,3);
INSERT INTO AlbumTrack VALUES ('1c',105,2,4);
INSERT INTO AlbumTrack VALUES ('1c',106,2,5);
INSERT INTO AlbumTrack VALUES ('1c',107,2,6);
INSERT INTO AlbumTrack VALUES ('1c',108,2,7);
INSERT INTO AlbumTrack VALUES ('1c',109,2,8);
INSERT INTO AlbumTrack VALUES ('1c',110,2,9);
INSERT INTO AlbumTrack VALUES ('1c',111,2,10);

INSERT INTO AlbumTrack VALUES ('1v',103,2,1);
INSERT INTO AlbumTrack VALUES ('1v',103,3,2);
INSERT INTO AlbumTrack VALUES ('1v',104,2,3);
INSERT INTO AlbumTrack VALUES ('1v',105,2,4);
INSERT INTO AlbumTrack VALUES ('1v',106,2,5);
INSERT INTO AlbumTrack VALUES ('1v',107,2,6);
INSERT INTO AlbumTrack VALUES ('1v',108,2,7);

INSERT INTO AlbumTrack VALUES ('1t',103,2,1);
INSERT INTO AlbumTrack VALUES ('1t',103,3,2);
INSERT INTO AlbumTrack VALUES ('1t',104,2,3);
INSERT INTO AlbumTrack VALUES ('1t',105,2,4);
INSERT INTO AlbumTrack VALUES ('1t',106,2,5);
INSERT INTO AlbumTrack VALUES ('1t',107,2,6);
INSERT INTO AlbumTrack VALUES ('1t',108,2,7);

INSERT INTO AlbumTrack VALUES ('2c',203,2,1);
INSERT INTO AlbumTrack VALUES ('2c',204,2,2);
INSERT INTO AlbumTrack VALUES ('2c',205,2,3);
INSERT INTO AlbumTrack VALUES ('2c',206,2,4);
INSERT INTO AlbumTrack VALUES ('2c',207,2,5);
INSERT INTO AlbumTrack VALUES ('2c',208,2,6);
INSERT INTO AlbumTrack VALUES ('2c',209,2,7);

INSERT INTO AlbumTrack VALUES ('2v',203,2,1);
INSERT INTO AlbumTrack VALUES ('2v',204,2,2);
INSERT INTO AlbumTrack VALUES ('2v',205,2,3);
INSERT INTO AlbumTrack VALUES ('2v',206,2,4);
INSERT INTO AlbumTrack VALUES ('2v',207,2,5);
INSERT INTO AlbumTrack VALUES ('2v',208,2,6);
INSERT INTO AlbumTrack VALUES ('2v',209,2,7);

INSERT INTO AlbumTrack VALUES ('3c',301,2,1);
INSERT INTO AlbumTrack VALUES ('3c',302,2,2);
INSERT INTO AlbumTrack VALUES ('3c',303,2,3);
INSERT INTO AlbumTrack VALUES ('3c',304,2,4);
INSERT INTO AlbumTrack VALUES ('3c',305,2,5);
INSERT INTO AlbumTrack VALUES ('3c',306,2,6);
INSERT INTO AlbumTrack VALUES ('3c',307,2,7);
INSERT INTO AlbumTrack VALUES ('3c',308,2,8);
INSERT INTO AlbumTrack VALUES ('3c',309,2,9);
INSERT INTO AlbumTrack VALUES ('3c',309,3,10);

INSERT INTO Catalogue VALUES ('24-Dec-2010','1t',10.99,9);
INSERT INTO Catalogue VALUES ('15-Jan-2011','1c',14.99,2);
INSERT INTO Catalogue VALUES ('10-Jan-2011','1v',12.99,5);
INSERT INTO Catalogue VALUES ('15-Nov-2010','2c',14.99,1);
INSERT INTO Catalogue VALUES ('20-Jan-2010','2v',12.99,0);
INSERT INTO Catalogue VALUES ('10-Dec-2008','3c',14.99,1);

INSERT INTO CatOrder VALUES (1,'17-Jan-2011','20-Jan-2011',1);
INSERT INTO CatOrder VALUES (2,'12-Mar-2012','15-MAr-2012',3);
INSERT INTO CatOrder VALUES (3,'06-Dec-2011','10-Dec-2011',1);

INSERT INTO CatOrderItem VALUES(1,'15-Jan-2011','1c');
INSERT INTO CatOrderItem VALUES(2,'10-Jan-2011','1v');
INSERT INTO CatOrderItem VALUES(2,'20-Jan-2010','2v');
INSERT INTO CatOrderItem VALUES(3,'10-Jan-2011','1v');
INSERT INTO CatOrderItem VALUES(3,'15-Nov-2010','2c');
INSERT INTO CatOrderItem VALUES(3,'10-Dec-2008','3c');

INSERT INTO VinylAlbum VALUES ('1v');
INSERT INTO VinylAlbum VALUES ('2v');
INSERT INTO CDAlbum VALUES ('1c');
INSERT INTO CDAlbum VALUES ('2c');
INSERT INTO CDAlbum VALUES ('3c');
INSERT INTO TapeAlbum VALUES ('1t','Fancy');

INSERT INTO CDAlbumExtra VALUES ('1c','Web');
INSERT INTO CDAlbumExtra VALUES ('1c','Video');
INSERT INTO CDAlbumExtra VALUES ('2c','Game');
INSERT INTO CDAlbumExtra VALUES ('3c','Video');
INSERT INTO CDAlbumExtra VALUES ('3c','Game');

INSERT INTO VinylAlbumColor VALUES ('1v','Red');
INSERT INTO VinylAlbumColor VALUES ('1v','Black');
INSERT INTO VinylAlbumColor VALUES ('1v','Green');
INSERT INTO VinylAlbumColor VALUES ('2v','Red');

-- create appropriate indices

CREATE INDEX m_idx on Manager(name);
CREATE INDEX mp_idx on ManPhone(telephone);
CREATE INDEX se_idx on SoundEngineer(name);
CREATE INDEX a_idx on Artist(genre);
CREATE INDEX mt_idx on MasterTrack(working_title);
CREATE INDEX ft_idx on FinishedTrack(released_title);

-- end of script
