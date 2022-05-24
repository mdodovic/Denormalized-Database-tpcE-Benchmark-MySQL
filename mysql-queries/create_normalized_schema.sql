DROP TABLE IF EXISTS HOLDING_SUMMARY CASCADE;
DROP TABLE IF EXISTS LAST_TRADE CASCADE;
DROP TABLE IF EXISTS CUSTOMER_ACCOUNT CASCADE;
DROP TABLE IF EXISTS CUSTOMER CASCADE;

CREATE TABLE tpce_mysql.HOLDING_SUMMARY (
	HS_CA_ID bigint Not Null, 	
    HS_S_SYMB CHAR(15) Not Null, 	
    HS_QTY int Not Null
); 
ALTER TABLE tpce_mysql.HOLDING_SUMMARY ADD CONSTRAINT HOLDING_SUMMARY_PK PRIMARY KEY (HS_CA_ID, HS_S_SYMB); 

CREATE TABLE tpce_mysql.LAST_TRADE (	
	LT_S_SYMB CHAR(15) Not Null, 	
    LT_DTS DATETIME Not Null, 	
    LT_PRICE decimal(10,2) Not Null, 	
    LT_OPEN_PRICE decimal(10,2) Not Null, 	
    LT_VOL bigint Not Null
);  
ALTER TABLE tpce_mysql.LAST_TRADE ADD CONSTRAINT LAST_TRADE_PK PRIMARY KEY (LT_S_SYMB); 

CREATE TABLE tpce_mysql.CUSTOMER_ACCOUNT (	
	CA_ID bigint Not Null, 	
    CA_B_ID bigint Not Null, 	
    CA_C_ID bigint Not Null, 	
    CA_NAME CHAR(50), 	
    CA_TAX_ST smallint Not Null, 	
    CA_BAL decimal(12,2) Not Null 
);  
ALTER TABLE tpce_mysql.CUSTOMER_ACCOUNT ADD CONSTRAINT CUSTOMER_ACCOUNT_PK PRIMARY KEY (CA_ID); 

CREATE TABLE tpce_mysql.CUSTOMER (	
	C_ID bigint Not Null, 	
    C_TAX_ID CHAR(20) Not Null, 	
    C_ST_ID CHAR(4) Not Null, 	
    C_L_NAME CHAR(25) Not Null, 	
    C_F_NAME CHAR(20) Not Null, 	
    C_M_NAME CHAR(1), 	
    C_GNDR CHAR(1), 	
    C_TIER smallint Not Null, 	
    C_DOB DATE Not Null, 	
    C_AD_ID bigint Not Null, 	
    C_CTRY_1 CHAR(3), 	
    C_AREA_1 CHAR(3), 	
    C_LOCAL_1 CHAR(10), 	
    C_EXT_1 CHAR(5), 	
    C_CTRY_2 CHAR(3), 	
    C_AREA_2 CHAR(3), 	
    C_LOCAL_2 CHAR(10), 	
    C_EXT_2 CHAR(5), 	
    C_CTRY_3 CHAR(3), 	
    C_AREA_3 CHAR(3), 	
    C_LOCAL_3 CHAR(10), 	
    C_EXT_3 CHAR(5), 	
    C_EMAIL_1 CHAR(50), 	
    C_EMAIL_2 CHAR(50)  
); 
ALTER TABLE tpce_mysql.CUSTOMER ADD CONSTRAINT CUSTOMER_PK PRIMARY KEY (C_ID); 