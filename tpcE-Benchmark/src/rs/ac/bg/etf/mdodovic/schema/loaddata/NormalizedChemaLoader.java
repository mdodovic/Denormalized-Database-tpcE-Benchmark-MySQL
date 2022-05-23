package rs.ac.bg.etf.mdodovic.schema.loaddata;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.schema.Tables;

public class NormalizedChemaLoader {

//	private static final String pathToData = "D:\\flatOut_dataForDatabase\\flat_out\\";
	private static final String pathToData = "C:\\Users\\matij\\Desktop\\";
	
	public static void loadData(Connection connection) throws SQLException {
		
		
		String bulk = "LOAD DATA LOCAL INFILE 'C:/Users/matij/Desktop/LastTrade.txt' "
				+ "	INTO TABLE tpce_mysql.last_trade "
				+ "	FIELDS TERMINATED BY '|' "
				+ "	LINES TERMINATED BY '\\n' ";

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(bulk);
			//System.out.println("Data added to table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
//		String bulkLoadPattern = "LOAD DATAtpcE.dbo.#1# \r\n" + 
//				"FROM '" + pathToData + "#2#" + ".txt' \r\n" + 
//				"WITH (BATCHSIZE = 20000, FIELDTERMINATOR = '|', ROWTERMINATOR = '\\n') \r\n";
//		
//		for(String tableName : Tables.normalizedTableNames) {
//
//			
//			String[] name = tableName.split("_");
//			
//			String fileName = name[0].substring(0, 1) + name[0].substring(1).toLowerCase();
//			if(name.length == 2)
//				fileName += name[1].substring(0, 1) + name[1].substring(1).toLowerCase();
//			
//			
//			String bulkLoadQuery = bulkLoadPattern.replace("#1#", tableName);
//			bulkLoadQuery = bulkLoadQuery.replace("#2#", fileName);
//			/*
//			Statement stmt;
//			try {
//				stmt = connection.createStatement();
//				stmt.executeUpdate(bulkLoadQuery);
//				//System.out.println("Data added to table " + tableName);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			*/
//			
//			System.out.println("Data for " + tableName + " successfully loaded from file " + fileName);
//
//		}
		System.out.println("------------------------------------------------------------");		
		
	}
	
}
