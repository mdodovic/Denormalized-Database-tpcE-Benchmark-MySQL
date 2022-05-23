package rs.ac.bg.etf.mdodovic.schema.loaddata;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.schema.Tables;

public class NormalizedSchemaLoader {

//	private static final String pathToData = "D:\\flatOut_dataForDatabase\\flat_out\\";
	private static final String pathToData = "C:/Users/matij/Desktop/";
	
	public static void loadData(Connection connection) throws SQLException {
				
		String bulkLoadPattern = "LOAD DATA LOCAL INFILE "
									+ " '" + pathToData + "#1#" + ".txt' "
									+ "	INTO TABLE tpce_mysql." + "#2#"
									+ "	FIELDS TERMINATED BY '|' "
									+ "	LINES TERMINATED BY '\\n' ";

		
		for(String tableName : Tables.normalizedTableNames) {
			
			String[] name = tableName.split("_");
			
			String fileName = name[0].substring(0, 1) + name[0].substring(1).toLowerCase();
			if(name.length == 2)
				fileName += name[1].substring(0, 1) + name[1].substring(1).toLowerCase();
			
			
			String bulkLoadQuery = bulkLoadPattern.replace("#1#", fileName);
			bulkLoadQuery = bulkLoadQuery.replace("#2#", tableName);

			Statement stmt = connection.createStatement();

			int numberOfLoadedRows = stmt.executeUpdate(bulkLoadQuery);
			
			System.out.println("Data (" + numberOfLoadedRows + " rows) for " + tableName + " successfully loaded from file " + fileName + ".txt");

		}
		System.out.println("------------------------------------------------------------");		
		
	}
	
}
