package rs.ac.bg.etf.mdodovic.schema.loaddata;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.schema.Tables;

public class FullyDenormalizedSchemaLoader {

	public FullyDenormalizedSchemaLoader() {
		
	}

	public static void loadData(Connection connection) throws TransactionError, SQLException {
		
		
		String insertDataQuery = "INSERT INTO tpce_mysql.DTT2T3T8 "
				+ " (DT_CA_ID, DT_CA_BAL, DT_HS_S_SYMB, DT_HS_QTY, DT_CA_C_ID, DT_LT_PRICE) "
				+ "	SELECT CA_ID, CA_BAL, HS_S_SYMB, HS_QTY, C_ID, LT_PRICE "
				+ "		FROM tpce_mysql.CUSTOMER_ACCOUNT "
				+ "			LEFT OUTER JOIN tpce_mysql.HOLDING_SUMMARY ON HS_CA_ID = CA_ID, "
				+ "				tpce_mysql.LAST_TRADE, "
				+ "				tpce_mysql.CUSTOMER "
				+ "		WHERE LT_S_SYMB = HS_S_SYMB "
				+ "			  AND C_ID = CA_C_ID ";

		Statement stmt = connection.createStatement();
		int numberOfRowsFullyDenormalizedTable = stmt.executeUpdate(insertDataQuery);
		
//		System.out.println("Data (" + numberOfRowsFullyDenormalizedTable + " rows) for " + Tables.fullyDenormalizedTableNames + " successfully loaded from file " + fileName + ".txt");
//
//		System.out.println("------------------------------------------------------------");		

	}

		
/*
		// This will fill Denormalize schema with data from Normalized schema
		
		String getDataForDT = "SELECT CA_ID, HS_CA_ID, CA_BAL, HS_S_SYMB, "
									+ "	LT_S_SYMB, HS_QTY, C_ID, CA_C_ID, LT_PRICE "
								+ " FROM tpce_mysql.CUSTOMER_ACCOUNT "
									+ "	LEFT OUTER JOIN tpce_mysql.HOLDING_SUMMARY on HS_CA_ID = CA_ID, "
										+ "	tpce_mysql.LAST_TRADE, tpce_mysql.CUSTOMER "
								+ "	WHERE LT_S_SYMB = HS_S_SYMB and C_ID = CA_C_ID ";
		
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(getDataForDT)){
					
				int total = 0;
				int successfull = 0;
				while(rs.next()) {

					boolean successfullyInserted = insertIntoDatabase(connection, 
						rs.getLong("CA_ID"), rs.getDouble("CA_BAL"), 
						rs.getString("HS_S_SYMB"), rs.getInt("HS_QTY"), 
						rs.getLong("C_ID"), rs.getDouble("LT_PRICE"));
					
					total += 1;

					if(successfullyInserted) {
						successfull += 1;
					}
					
					if(successfull % 10000 == 0) {
						System.out.println("Loaded rows: " + successfull);
					}

				}

				System.out.println("Total loaded rows: " + successfull + " (" + 
						String.format("%.2f", 100. * successfull / total) + "%)");
							
				
				
			} catch(SQLException e) { 
				throw new TransactionError(e.toString());
			}
		
	
	}

	private static boolean insertIntoDatabase(Connection connection, 
			long ca_id, double ca_bal, String hs_s_symb,
			int hs_qty, long c_id, double lt_price) {
		
		

		return false;
	}
*/
}
