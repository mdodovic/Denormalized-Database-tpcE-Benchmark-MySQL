package rs.ac.bg.etf.mdodovic.schema.create;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.schema.Tables;

public class PartiallyDenormalizedSchemaCreator {

	public static void createPartiallyDenormalizedDatabaseSchema(Connection connection) throws SQLException {

		String createSchemaQuery;
		Statement stmt;				
		String tableName;

		tableName = Tables.partiallyDenormalizedTableT2T3T8F2;

		createSchemaQuery = createTableQuery(tableName);
		stmt = connection.createStatement();
		stmt.executeUpdate(createSchemaQuery);
		
		System.out.println("Table: " + tableName + " successfully created");
			
		tableName = Tables.partiallyDenormalizedTableT8F6;

		createSchemaQuery = createTableQuery(tableName);
		stmt = connection.createStatement();
		stmt.executeUpdate(createSchemaQuery);
		
		System.out.println("Table: " + tableName + " successfully created");

		
		System.out.println("------------------------------------------------------------");
		
	}
	
	public static void dropPartiallyDenormalizedDatabaseChema(Connection connection) throws SQLException {
		
		String dropQueryPattern = "DROP TABLE IF EXISTS ### CASCADE;";

		String dropQuery;
		Statement stmt;
		String tableName;

		tableName = Tables.partiallyDenormalizedTableT2T3T8F2;
		dropQuery = dropQueryPattern.replace("###",  tableName);
	
		stmt = connection.createStatement();
		stmt.executeUpdate(dropQuery);

		System.out.println("Table: " + tableName + " successfully deleted");

		tableName = Tables.partiallyDenormalizedTableT8F6;
		dropQuery = dropQueryPattern.replace("###",  tableName);
	
		stmt = connection.createStatement();
		stmt.executeUpdate(dropQuery);

		System.out.println("Table: " + tableName + " successfully deleted");

		
		System.out.println("------------------------------------------------------------");

	}
	
	public static void raiseForeignKeyConstraints(Connection connection) throws SQLException {
		
		String foreignKeyConstraintsQuery;
		Statement stmt; 
		String tableName;

		tableName = Tables.partiallyDenormalizedTableT2T3T8F2;
		
		foreignKeyConstraintsQuery = createForeignKeyConstraintsTableQuerry(tableName);
		if(foreignKeyConstraintsQuery != null) {
			stmt = connection.createStatement();
			stmt.executeUpdate(foreignKeyConstraintsQuery);
			
			System.out.println("Foreign key(s) on table " + tableName + " successfully raised");

		}

		tableName = Tables.partiallyDenormalizedTableT8F6;
		
		foreignKeyConstraintsQuery = createForeignKeyConstraintsTableQuerry(tableName);
		if(foreignKeyConstraintsQuery != null) {
			stmt = connection.createStatement();
			stmt.executeUpdate(foreignKeyConstraintsQuery);
			
			System.out.println("Foreign key(s) on table " + tableName + " successfully raised");

		}

		System.out.println("------------------------------------------------------------");

	}	

	public static void raiseIndexes(Connection connection) throws SQLException {

		String indexName;
		String tableName;
		String columnName;
		
		String hasIndexOnTableQuery = "	SELECT * "
									+ "    FROM INFORMATION_SCHEMA.STATISTICS "
									+ "	   WHERE INDEX_SCHEMA = ? "
									+ "		  AND TABLE_NAME = ? "
									+ "		  AND INDEX_NAME = ? ";

		String dropIndexPattern = "DROP INDEX #1# ON tpce_mysql.#2#";
		String dropIndexQuery;
		String createIndexPattern = "CREATE INDEX #1# ON tpce_mysql.#2# (#3#) USING BTREE";
		String createIndexQuery;

		Statement stmt;
		PreparedStatement pStmt;
		ResultSet rs;
		
		tableName = "DTT2T3T8F2";
		indexName = "DTT2T3T8F2_Index_DT_CA_C_ID";
		columnName = "DT_CA_C_ID";
						
		pStmt = connection.prepareStatement(hasIndexOnTableQuery);  
		pStmt.setString(1, "tpce_mysql");
		pStmt.setString(2, tableName);
		pStmt.setString(3, indexName);
		
		rs = pStmt.executeQuery();
		if(rs.next()) {

			dropIndexQuery = dropIndexPattern.replace("#2#", tableName);
			dropIndexQuery = dropIndexQuery.replace("#1#", indexName);

			stmt = connection.createStatement();
			stmt.executeUpdate(dropIndexQuery);
			
		} 
				
		createIndexQuery = createIndexPattern.replace("#2#", tableName);
		createIndexQuery = createIndexQuery.replace("#1#", indexName);
		createIndexQuery = createIndexQuery.replace("#3#", columnName);
		
		stmt = connection.createStatement();

		stmt.execute(createIndexQuery);

		System.out.println("Index: " + indexName + " successfully created");

		
		tableName = "DTT2T3T8F2";
		indexName = "DTT2T3T8F2_Index_DT_HS_S_SYMB__DT_CA_ID";
		columnName = "DT_HS_S_SYMB, DT_CA_ID";
						
		pStmt = connection.prepareStatement(hasIndexOnTableQuery);  
		pStmt.setString(1, "tpce_mysql");
		pStmt.setString(2, tableName);
		pStmt.setString(3, indexName);
		
		rs = pStmt.executeQuery();
		if(rs.next()) {

			dropIndexQuery = dropIndexPattern.replace("#2#", tableName);
			dropIndexQuery = dropIndexQuery.replace("#1#", indexName);

			stmt = connection.createStatement();
			stmt.executeUpdate(dropIndexQuery);
			
		} 
				
		createIndexQuery = createIndexPattern.replace("#2#", tableName);
		createIndexQuery = createIndexQuery.replace("#1#", indexName);
		createIndexQuery = createIndexQuery.replace("#3#", columnName);
		
		stmt = connection.createStatement();

		stmt.execute(createIndexQuery);

		System.out.println("Index: " + indexName + " successfully created");

		System.out.println("------------------------------------------------------------");

		
	}

	public static String createForeignKeyConstraintsTableQuerry(String tableName) {

		switch(tableName) {
			
			case "DTT2T3T8F2": return null;
			
			case "DTT8F6": return null;

			default: return null;	
		}
			
	}

	private static String createTableQuery(String tableName) {
		
		switch(tableName) {

			case "DTT2T3T8F2": return "CREATE TABLE tpce_mysql.DTT2T3T8F2 ( "
									+ "		DT_CA_ID bigint Not Null, "
									+ "    	DT_HS_S_SYMB CHAR(15) Not Null, "
									+ "		DT_HS_QTY int Not Null, "
									+ "    	DT_CA_C_ID bigint Not Null, "
									+ "    	DT_LT_PRICE decimal(10,2) Not Null "
									+ "); "
									+ "ALTER TABLE tpce_mysql.DTT2T3T8F2 ADD CONSTRAINT DTT2T3T8F2_PK PRIMARY KEY (DT_CA_ID, DT_HS_S_SYMB); ";
	
			case "DTT8F6": return "CREATE TABLE tpce_mysql.DTT8F6 ( "
								+ "		DT_CA_ID bigint Not Null, "
								+ "		DT_CA_BAL decimal(12,2) Not Null "
								+ " ); "
								+ " ALTER TABLE tpce_mysql.DTT8F6 ADD CONSTRAINT DTT8F6_PK PRIMARY KEY (DT_CA_ID); ";
			
			default: return null;	
		}
		
	}

	
}
