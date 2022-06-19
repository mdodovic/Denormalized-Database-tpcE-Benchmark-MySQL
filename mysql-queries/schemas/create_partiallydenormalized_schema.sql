DROP TABLE IF EXISTS tpce_mysql.DTT2T3T8F2 CASCADE;
DROP TABLE IF EXISTS tpce_mysql.DTT8F6 CASCADE;

CREATE TABLE tpce_mysql.DTT2T3T8F2 (
	DT_CA_ID bigint Not Null, 
    DT_HS_S_SYMB CHAR(15) Not Null, 
	DT_HS_QTY int Not Null, 
    DT_CA_C_ID bigint Not Null, 
    DT_LT_PRICE decimal(10,2) Not Null
); 
ALTER TABLE tpce_mysql.DTT2T3T8F2 ADD CONSTRAINT DTT2T3T8F2_PK PRIMARY KEY (DT_CA_ID, DT_HS_S_SYMB); 

CREATE TABLE tpce_mysql.DTT8F6 (
	DT_CA_ID bigint Not Null, 
    DT_CA_BAL decimal(12,2) Not Null
);
ALTER TABLE tpce_mysql.DTT8F6 ADD CONSTRAINT DTT8F6_PK PRIMARY KEY (DT_CA_ID); 