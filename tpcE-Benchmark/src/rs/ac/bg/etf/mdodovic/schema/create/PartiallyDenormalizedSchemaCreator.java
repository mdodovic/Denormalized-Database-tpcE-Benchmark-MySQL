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

	
}
