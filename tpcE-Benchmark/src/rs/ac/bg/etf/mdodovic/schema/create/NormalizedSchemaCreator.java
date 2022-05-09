package rs.ac.bg.etf.mdodovic.schema.create;

import java.sql.Connection;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.schema.Tables;

public class NormalizedSchemaCreator {
	
	public static void createNormalizedDatabaseSchema(Connection connection) {

		Statement stmt;
		String createSchemaQuery;
		String createConstraintQuery;

		
		
		// drop foreign keys
		// drop schema
		
		createSchemaQuery = "";
		for (String tableName: Tables.normalizedTableNames) {
			
			createSchemaQuery = createTableQuery(tableName) + "\r\n";
			
		}
		
		
	}

	
	
	private static String createTableQuery(String tableName) {
		
		switch(tableName) {

			case "HOLDING_SUMMARY": return "CREATE TABLE HOLDING_SUMMARY (\r\n" + 
					"	HS_CA_ID bigint Not Null,\r\n" + 
					"	HS_S_SYMB CHAR(15) Not Null,\r\n" + 
					"	HS_QTY int Not Null\r\n" + 
					");\r\n" + 
					"ALTER TABLE HOLDING_SUMMARY ADD CONSTRAINT HOLDING_SUMMARY_PK PRIMARY KEY (HS_CA_ID,HS_S_SYMB); \r\n";
	
			case "LAST_TRADE": return "CREATE TABLE LAST_TRADE (\r\n" + 
					"	LT_S_SYMB CHAR(15) Not Null,\r\n" + 
					"	LT_DTS DATETIME Not Null,\r\n" + 
					"	LT_PRICE decimal(10,2) Not Null,\r\n" + 
					"	LT_OPEN_PRICE decimal(10,2) Not Null,\r\n" + 
					"	LT_VOL bigint Not Null\r\n" + 
					");\r\n" + 
					"ALTER TABLE LAST_TRADE ADD CONSTRAINT LAST_TRADE_PK PRIMARY KEY (LT_S_SYMB); \r\n";
			
			case "CUSTOMER_ACCOUNT": return "CREATE TABLE CUSTOMER_ACCOUNT (\r\n" + 
					"	CA_ID bigint Not Null,\r\n" + 
					"	CA_B_ID bigint Not Null,\r\n" + 
					"	CA_C_ID bigint Not Null,\r\n" + 
					"	CA_NAME CHAR(50) ,\r\n" + 
					"	CA_TAX_ST smallint Not Null,\r\n" + 
					"	CA_BAL decimal(12,2) Not Null\r\n" + 
					");\r\n" + 
					"ALTER TABLE CUSTOMER_ACCOUNT ADD CONSTRAINT CUSTOMER_ACCOUNT_PK PRIMARY KEY (CA_ID); \r\n";

			case "CUSTOMER": return "CREATE TABLE CUSTOMER (\r\n" + 
					"	C_ID bigint Not Null,\r\n" + 
					"	C_TAX_ID CHAR(20) Not Null,\r\n" + 
					"	C_ST_ID CHAR(4) Not Null,\r\n" + 
					"	C_L_NAME CHAR(25) Not Null,\r\n" + 
					"	C_F_NAME CHAR(20) Not Null,\r\n" + 
					"	C_M_NAME CHAR(1) ,\r\n" + 
					"	C_GNDR CHAR(1) ,\r\n" + 
					"	C_TIER smallint Not Null,\r\n" + 
					"	C_DOB DATE Not Null,\r\n" + 
					"	C_AD_ID bigint Not Null,\r\n" + 
					"	C_CTRY_1 CHAR(3) ,\r\n" + 
					"	C_AREA_1 CHAR(3) ,\r\n" + 
					"	C_LOCAL_1 CHAR(10) ,\r\n" + 
					"	C_EXT_1 CHAR(5) ,\r\n" + 
					"	C_CTRY_2 CHAR(3) ,\r\n" + 
					"	C_AREA_2 CHAR(3) ,\r\n" + 
					"	C_LOCAL_2 CHAR(10) ,\r\n" + 
					"	C_EXT_2 CHAR(5) ,\r\n" + 
					"	C_CTRY_3 CHAR(3) ,\r\n" + 
					"	C_AREA_3 CHAR(3) ,\r\n" + 
					"	C_LOCAL_3 CHAR(10) ,\r\n" + 
					"	C_EXT_3 CHAR(5) ,\r\n" + 
					"	C_EMAIL_1 CHAR(50) ,\r\n" + 
					"	C_EMAIL_2 CHAR(50) \r\n" + 
					");\r\n" + 
					"ALTER TABLE CUSTOMER ADD CONSTRAINT CUSTOMER_PK PRIMARY KEY (C_ID); \r\n";
	
			default: return null;	
		}
		
	}
	
	
}
