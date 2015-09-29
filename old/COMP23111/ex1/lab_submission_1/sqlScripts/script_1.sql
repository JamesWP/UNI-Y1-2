-- set echo ON to show SQL statements
SET ECHO ON

-- set width of a line (before wrapping to the next line)
SET PAGESIZE 1000
-- changes the display format of the column
COLUMN title FORMAT a25
COLUMN createdby FORMAT a23

SPOOL ../sqlOutputs/LabEx1Out.lst

-- part1
SET LIN 50
DESCRIBE Album;
DESCRIBE Manager;
DESCRIBE MasterTrack;
SET LIN 150

-- part2
COLUMN constraint_name FORMAT a20
COLUMN table_name FORMAT a20
COLUMN search_condition FORMAT a20
COLUMN column_name FORMAT a20

SELECT 
	uc.table_name, 
	case uc.constraint_type 
		when 'P' then 'Primary Key'
		when 'R' then 'Foreign Key' 
    else ''
  end key_type,
	ucc.column_name,
	uc.constraint_name 
FROM user_constraints uc
LEFT JOIN user_cons_columns ucc 
	on ucc.constraint_name = uc.constraint_name 
WHERE uc.constraint_type in ('P','R');

--part3
COLUMN telephone FORMAT a14
SELECT
	manager_id,
  name
from Manager;

SELECT
	manager_id,
	telephone
from ManPhone;

SELECT
	hasContract,
  date_from,
	date_to,
	duration
from ContractInfo;

SPOOL OFF
