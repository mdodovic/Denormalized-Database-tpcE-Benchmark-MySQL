DROP TABLE IF EXISTS tpce_mysql.DTT2T3T8 CASCADE;

CREATE TABLE tpce_mysql.DTT2T3T8 (
	DT_CA_ID bigint Not Null,
	DT_CA_BAL decimal(12,2) Not Null, 
    DT_HS_S_SYMB CHAR(15) Not Null, 
	DT_HS_QTY int Not Null, 
	DT_CA_C_ID bigint Not Null, 
    DT_LT_PRICE decimal(10,2) Not Null
); 
ALTER TABLE tpce_mysql.DTT2T3T8 ADD CONSTRAINT D2T2T3T8_PK PRIMARY KEY (DT_CA_ID, DT_CA_BAL, DT_HS_S_SYMB);
