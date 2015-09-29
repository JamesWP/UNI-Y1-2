-- set echo ON to show SQL statements
SET ECHO ON

-- set width of a line (before wrapping to the next line)
SET LIN 150

-- changes the display format of the column
COLUMN title FORMAT a25
COLUMN createdby FORMAT a23

SPOOL ../sqlOutputs/LabEx5Part2Out.lst

CREATE OR REPLACE PROCEDURE ContractInfo_insert 
(
	newHasContract IN ContractInfo.hasContract%TYPE,
	newDate_from IN ContractInfo.date_from%TYPE,
	newDate_to IN ContractInfo.date_to%TYPE
)
IS
  no_contracts_clash NUMBER :=0;
BEGIN
	IF newDate_to > newDate_from
	THEN -- dates are in the correct order
		SELECT count(1) into no_contracts_clash
		FROM ContractInfo
		WHERE 
		(	newDate_to BETWEEN date_from and date_to
			OR newDate_from BETWEEN date_from and date_to)
		AND newHasContract = hasContract;
 		
		IF no_contracts_clash < 1 
    THEN
			INSERT INTO ContractInfo(hasContract,date_from,date_to)
			VALUES (newHasContract,newDate_from,newDate_to);
		END IF; 
  END IF;
END;
/

SHOW ERRORS PROCEDURE ContractInfo_insert

-- SHOW IT WORKS
DELETE FROM ContractInfo
WHERE hasContract = 'Coldfrat'
AND date_from = '10-oct-2008' 
AND date_to = '09-oct-2010';

SELECT * FROM ContractInfo;

EXEC ContractInfo_insert('Goldfrat','10-oct-2008','09-oct-2010');

SELECT * FROM ContractInfo;


SPOOL OFF
