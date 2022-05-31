package rs.ac.bg.etf.mdodovic.schema.loaddata;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.schema.Tables;

public class PartiallyDenormalizedSchemaLoader {

	public static void loadData(Connection connection) throws TransactionError, SQLException {
		
		
		String insertDataToDTT2T3T8F2 = "INSERT INTO tpce_mysql." + Tables.partiallyDenormalizedTableT2T3T8F2
							+ " (DT_CA_ID, DT_HS_S_SYMB, DT_HS_QTY, DT_CA_C_ID, DT_LT_PRICE)"
							+ "	SELECT CA_ID, HS_S_SYMB, HS_QTY, C_ID, LT_PRICE "
							+ "		FROM tpce_mysql.CUSTOMER_ACCOUNT "
							+ "			LEFT OUTER JOIN tpce_mysql.HOLDING_SUMMARY ON HS_CA_ID = CA_ID, "
							+ "				tpce_mysql.LAST_TRADE, "
							+ "				tpce_mysql.CUSTOMER "
							+ "		WHERE LT_S_SYMB = HS_S_SYMB "
							+ "			  AND C_ID = CA_C_ID; ";
		
		try (Statement stmt = connection.createStatement();) {
			
			int numberOfRowsFullyDenormalizedTable = stmt.executeUpdate(insertDataToDTT2T3T8F2);

			System.out.println("Data (" + numberOfRowsFullyDenormalizedTable + " rows) for " + Tables.partiallyDenormalizedTableT2T3T8F2 + " successfully inserted from normalized table");

		} catch(SQLException e) { 
			throw new TransactionError(e.toString());
		}


		String insertDataToDTT8F6 = "INSERT INTO tpce_mysql." + Tables.partiallyDenormalizedTableT8F6
							+ " (DT_CA_ID, DT_CA_BAL) "
							+ "	SELECT CA_ID, CA_BAL "
							+ "		FROM tpce_mysql.CUSTOMER_ACCOUNT "
							+ "			LEFT OUTER JOIN tpce_mysql.HOLDING_SUMMARY ON HS_CA_ID = CA_ID, "
							+ "				tpce_mysql.LAST_TRADE, "
							+ "				tpce_mysql.CUSTOMER "
							+ "		WHERE LT_S_SYMB = HS_S_SYMB "
							+ "			  AND C_ID = CA_C_ID "
							+ "		GROUP BY CA_ID, CA_BAL; ";

		try (Statement stmt = connection.createStatement();) {
		
			int numberOfRowsFullyDenormalizedTable = stmt.executeUpdate(insertDataToDTT8F6);
		
			System.out.println("Data (" + numberOfRowsFullyDenormalizedTable + " rows) for " + Tables.partiallyDenormalizedTableT8F6 + " successfully inserted from normalized table");
		
		} catch(SQLException e) { 
			throw new TransactionError(e.toString());
		}
		
		System.out.println("------------------------------------------------------------");		

	}

}
