-- set echo ON to show SQL statements
SET ECHO ON

-- set width of a line (before wrapping to the next line)
SET LIN 150

-- changes the display format of the column
COLUMN title FORMAT a25
COLUMN createdby FORMAT a23

SPOOL ../sqlOutputs/LabEx5Part1Out.lst

DROP TRIGGER ContractInfo_duration_calc;

UPDATE ContractInfo SET duration = 0;
DELETE FROM ContractInfo WHERE HasContract = 'Goldfrat' and date_from = '10-oct-2008' and date_to = '9-oct-2010';

CREATE OR REPLACE TRIGGER ContractInfo_duration_calc 
BEFORE
UPDATE OR INSERT
ON ContractInfo
FOR EACH ROW
DECLARE
BEGIN
	:NEW.duration := :NEW.date_to - :NEW.date_from;
END ContractInfo_duration_calc;
/

SHOW ERRORS TRIGGER ContractInfo_duration_calc

-- SHOW IT WORKS
SELECT * FROM ContractInfo;

UPDATE ContractInfo
SET duration = duration;

INSERT INTO ContractInfo VALUES
('Goldfrat', '10-oct-2008', '09-oct-2010', 0);

SELECT * FROM ContractInfo;



SPOOL OFF
