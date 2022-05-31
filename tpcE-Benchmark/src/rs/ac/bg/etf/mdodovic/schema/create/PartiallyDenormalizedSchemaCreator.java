package rs.ac.bg.etf.mdodovic.schema.create;

import java.sql.Connection;
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
	
	public static void dropFullyDenormalizedDatabaseChema(Connection connection) throws SQLException {
		
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



	
}
