package rs.ac.bg.etf.mdodovic.schema.loaddata;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.schema.Tables;

public class FullyDenormalizedSchemaLoader {

	public static void loadData(Connection connection) throws TransactionError, SQLException {
		
		
		String insertDataQuery = "INSERT INTO tpce_mysql." + Tables.fullyDenormalizedTableName
							+ " (DT_CA_ID, DT_CA_BAL, DT_HS_S_SYMB, DT_HS_QTY, DT_CA_C_ID, DT_LT_PRICE) "
							+ "	SELECT CA_ID, CA_BAL, HS_S_SYMB, HS_QTY, C_ID, LT_PRICE "
							+ "		FROM tpce_mysql.CUSTOMER_ACCOUNT "
							+ "			LEFT OUTER JOIN tpce_mysql.HOLDING_SUMMARY ON HS_CA_ID = CA_ID, "
							+ "				tpce_mysql.LAST_TRADE, "
							+ "				tpce_mysql.CUSTOMER "
							+ "		WHERE LT_S_SYMB = HS_S_SYMB "
							+ "			  AND C_ID = CA_C_ID; ";
		
		try (Statement stmt = connection.createStatement();) {
			
			int numberOfRowsFullyDenormalizedTable = stmt.executeUpdate(insertDataQuery);

			System.out.println("Data (" + numberOfRowsFullyDenormalizedTable + " rows) for " + Tables.fullyDenormalizedTableName + " successfully inserted from normalized table");

		} catch(SQLException e) { 
			throw new TransactionError(e.toString());
		}

		System.out.println("------------------------------------------------------------");		

	}

}
