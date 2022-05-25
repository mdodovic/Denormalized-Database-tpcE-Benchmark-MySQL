package rs.ac.bg.etf.mdodovic.schema.create;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.schema.Tables;

public class FullyDenormalizedSchemaCreator {

	public static void createFullyDenormalizedDatabaseSchema(Connection connection) throws SQLException {

		String createSchemaQuery;
		Statement stmt;		
		
		for (String tableName: Tables.fullyDenormalizedTableNames) {

			createSchemaQuery = createTableQuery(tableName);
			stmt = connection.createStatement();
			stmt.executeUpdate(createSchemaQuery);
			
			System.out.println("Table: " + tableName + " successfully created");
			
		}
		
		System.out.println("------------------------------------------------------------");
		
	}

	public static void dropFullyDenormalizedDatabaseChema(Connection connection) throws SQLException {
		
		String dropQueryPattern = "DROP TABLE IF EXISTS ### CASCADE;";

		String dropQuery;
		Statement stmt;
		
		for(String tableName: Tables.fullyDenormalizedTableNames) {
			dropQuery = dropQueryPattern.replace("###",  tableName);
		
			stmt = connection.createStatement();
			stmt.executeUpdate(dropQuery);

			System.out.println("Table: " + tableName + " successfully deleted");

		}

		System.out.println("------------------------------------------------------------");

	}
	
	public static void raiseForeignKeyConstraints(Connection connection) throws SQLException {
		
		String foreignKeyConstraintsQuery;
		Statement stmt; 
		
		for(String tableName: Tables.fullyDenormalizedTableNames) {
			
			foreignKeyConstraintsQuery = createForeignKeyConstraintsTableQuerry(tableName);
			if(foreignKeyConstraintsQuery != null) {
				stmt = connection.createStatement();
				stmt.executeUpdate(foreignKeyConstraintsQuery);
				
				System.out.println("Foreign key(s) on table " + tableName + " successfully raised");

			}
		}

		System.out.println("------------------------------------------------------------");

	}	

	// Not used - not implemented well
	public static void raiseIndexes(Connection connection) throws SQLException {

		String indexName;
		String tableName;
		String columnName;
		
		String hasIndexOnTableQuery;

		String dropIndexPattern = "DROP INDEX #1# ON tpce_mysql.#2#";
		String dropIndexQuery;
		String createIndexPattern = "CREATE INDEX #1# ON tpce_mysql.#2# (#3#) USING BTREE";
		String createIndexQuery;

		Statement stmt;
		PreparedStatement pStmt;
		ResultSet rs;
		
		tableName = "CUSTOMER_ACCOUNT";
		indexName = "Customer_Account_Index_CA_C_ID";
		columnName = "CA_C_ID";
				
		hasIndexOnTableQuery = "	SELECT * "
			+ "    FROM INFORMATION_SCHEMA.STATISTICS "
			+ "	WHERE INDEX_SCHEMA = ? AND TABLE_NAME = ? AND INDEX_NAME = ? ";
		
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
			
	}
	
	public static String createForeignKeyConstraintsTableQuerry(String tableName) {

		switch(tableName) {
			
			case "DTT2T3T8": return null;

			default: return null;	
		}
			
	}

	
	
	
	
}
