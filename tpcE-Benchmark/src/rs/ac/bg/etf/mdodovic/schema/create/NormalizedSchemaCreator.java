package rs.ac.bg.etf.mdodovic.schema.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.schema.Tables;

public class NormalizedSchemaCreator {
	
	public static void createNormalizedDatabaseSchema(Connection connection) throws SQLException {

		Statement stmt;
		
		// drop foreign keys

		dropNormalizedDatabaseChema(connection);

		String createSchemaQuery = "";
		for (String tableName: Tables.normalizedTableNames) {

			createSchemaQuery = createTableQuery(tableName);
			stmt = connection.createStatement();
			stmt.executeUpdate(createSchemaQuery);
			
		}
		
		String primaryKeyConstraintsQuery = "";
		for (String tableName: Tables.normalizedTableNames) {

			primaryKeyConstraintsQuery = createPrimeryKeyConstraintsTableQuerry(tableName);
			stmt = connection.createStatement();
			stmt.executeUpdate(primaryKeyConstraintsQuery);
			
		}
		
		String foreignKeyConstraintsQuery = "";
		for(String tableName: Tables.normalizedTableNames) {
			
			foreignKeyConstraintsQuery = createForeignKeyConstraintsTableQuerry(tableName);
			if(foreignKeyConstraintsQuery != null) {
				stmt = connection.createStatement();
				stmt.executeUpdate(foreignKeyConstraintsQuery);
			}
		}
		
		
	}

	
	public static void dropNormalizedDatabaseChema(Connection connection) throws SQLException {
		
		String dropQueryPattern = "DROP TABLE IF EXISTS ### CASCADE;";

		String dropQuery;
		Statement stmt;
		
		for(String tableName: Tables.normalizedTableNames) {
			dropQuery = dropQueryPattern.replace("###",  tableName);
		
			stmt = connection.createStatement();
			stmt.executeUpdate(dropQuery);

			System.out.println("Table: " + tableName + " successfully deleted");

		}

		System.out.println("------------------------------------------------------------");

	}
	
	
	private static String createForeignKeyConstraintsTableQuerry(String tableName) {

		switch(tableName) {
			
			case "HOLDING_SUMMARY": return 
					"ALTER TABLE tpce_mysql.HOLDING_SUMMARY ADD CONSTRAINT FK_HOLDING_SUMMARY_HS_CA_ID_CUSTOMER_ACCOUNT_CA_ID FOREIGN KEY (HS_CA_ID) REFERENCES CUSTOMER_ACCOUNT(CA_ID);";

			case "LAST_TRADE": return null;

			case "CUSTOMER_ACCOUNT": return 
					"ALTER TABLE tpce_mysql.CUSTOMER_ACCOUNT ADD CONSTRAINT FK_CUSTOMER_ACCOUNT_CA_C_ID_CUSTOMER_C_ID FOREIGN KEY (CA_C_ID) REFERENCES CUSTOMER(C_ID);\r\n";
	
			case "CUSTOMER": return null;					
			
			default: return null;	
		}
			
	}
	
	private static String createPrimeryKeyConstraintsTableQuerry(String tableName) {

		switch(tableName) {
			
			case "HOLDING_SUMMARY": return 
					"ALTER TABLE tpce_mysql.HOLDING_SUMMARY ADD CONSTRAINT HOLDING_SUMMARY_PK PRIMARY KEY (HS_CA_ID,HS_S_SYMB); ";

			case "LAST_TRADE": return 
					"ALTER TABLE tpce_mysql.LAST_TRADE ADD CONSTRAINT LAST_TRADE_PK PRIMARY KEY (LT_S_SYMB);";

			case "CUSTOMER_ACCOUNT": return 
					"ALTER TABLE tpce_mysql.CUSTOMER_ACCOUNT ADD CONSTRAINT CUSTOMER_ACCOUNT_PK PRIMARY KEY (CA_ID);";
	
			case "CUSTOMER": return 
					"ALTER TABLE tpce_mysql.CUSTOMER ADD CONSTRAINT CUSTOMER_PK PRIMARY KEY (C_ID);";
		
			
			default: return null;	
		}
			
	}

	
	private static String createTableQuery(String tableName) {
		
		switch(tableName) {

			case "HOLDING_SUMMARY": return "CREATE TABLE tpce_mysql.HOLDING_SUMMARY ( "
					+ "	HS_CA_ID bigint Not Null, "
					+ "	HS_S_SYMB CHAR(15) Not Null, "
					+ "	HS_QTY int Not Null "
					+ "); ";
			
			case "LAST_TRADE": return "CREATE TABLE tpce_mysql.LAST_TRADE (" + 
					"	LT_S_SYMB CHAR(15) Not Null, " + 
					"	LT_DTS DATETIME Not Null, " + 
					"	LT_PRICE decimal(10,2) Not Null, " + 
					"	LT_OPEN_PRICE decimal(10,2) Not Null, " + 
					"	LT_VOL bigint Not Null " + 
					"); "; 

			case "CUSTOMER_ACCOUNT": return "CREATE TABLE tpce_mysql.CUSTOMER_ACCOUNT (" + 
					"	CA_ID bigint Not Null, " + 
					"	CA_B_ID bigint Not Null, " + 
					"	CA_C_ID bigint Not Null, " + 
					"	CA_NAME CHAR(50), " + 
					"	CA_TAX_ST smallint Not Null, " + 
					"	CA_BAL decimal(12,2) Not Null " + 
					"); "; 

			case "CUSTOMER": return "CREATE TABLE tpce_mysql.CUSTOMER (" + 
					"	C_ID bigint Not Null, " + 
					"	C_TAX_ID CHAR(20) Not Null, " + 
					"	C_ST_ID CHAR(4) Not Null, " + 
					"	C_L_NAME CHAR(25) Not Null, " + 
					"	C_F_NAME CHAR(20) Not Null, " + 
					"	C_M_NAME CHAR(1) , " + 
					"	C_GNDR CHAR(1) , " + 
					"	C_TIER smallint Not Null, " + 
					"	C_DOB DATE Not Null, " + 
					"	C_AD_ID bigint Not Null, " + 
					"	C_CTRY_1 CHAR(3) , " + 
					"	C_AREA_1 CHAR(3) , " + 
					"	C_LOCAL_1 CHAR(10) , " + 
					"	C_EXT_1 CHAR(5) , " + 
					"	C_CTRY_2 CHAR(3) , " + 
					"	C_AREA_2 CHAR(3) , " + 
					"	C_LOCAL_2 CHAR(10) , " + 
					"	C_EXT_2 CHAR(5) , " + 
					"	C_CTRY_3 CHAR(3) , " + 
					"	C_AREA_3 CHAR(3) , " + 
					"	C_LOCAL_3 CHAR(10) , " + 
					"	C_EXT_3 CHAR(5) , " + 
					"	C_EMAIL_1 CHAR(50) , " + 
					"	C_EMAIL_2 CHAR(50)  " + 
					");";
			
			default: return null;	
		}
		
	}
	
	
}
