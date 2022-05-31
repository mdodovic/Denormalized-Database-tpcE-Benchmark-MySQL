package rs.ac.bg.etf.mdodovic.transactions.PartiallyDT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.transactions.CustomerPosition_T2;

public class CustomerPozition_T2_PartiallyDenormalized extends CustomerPosition_T2 {

	public CustomerPozition_T2_PartiallyDenormalized(Connection connection) {
		super(connection);
	}

	@Override
	public void invokeCustomerPositionFrame1_T2F1() throws TransactionError {

	
		String getCustomerAccountInfo = "SELECT "
				+ " 		tpce_mysql.DTT2T3T8F2.DT_CA_ID AS CA_ID, DT_CA_BAL AS CA_BAL, (sum(DT_HS_QTY * DT_LT_PRICE)) as RES_SUM "
				+ "		FROM tpce_mysql.DTT2T3T8F2 "
				+ "			INNER JOIN tpce_mysql.DTT8F6 ON tpce_mysql.DTT2T3T8F2.DT_CA_ID = tpce_mysql.DTT8F6.DT_CA_ID "
				+ "    	WHERE DT_CA_C_ID = ? "
				+ "			AND DT_HS_S_SYMB IS NOT NULL "
				+ "		GROUP BY tpce_mysql.DTT2T3T8F2.DT_CA_ID, DT_CA_BAL "
				+ "		ORDER BY 3 asc; ";
		
		//long timeBefore = System.nanoTime();
		
		try (PreparedStatement stmt = connection.prepareStatement(getCustomerAccountInfo)){

			stmt.setLong(1, cust_id);
			try(ResultSet rs = stmt.executeQuery();) {
				
				//long timeAfter = System.nanoTime();
				
				fillDataToCustomerAccountRows(rs);
				
				/*System.out.println(cust_id);
				for (Long key : this.customerAccountRowData.keySet()) {
				    System.out.println(key + ". " + customerAccountRowData.get(key));
				}*/
			}

		} catch(SQLException e) { 
			throw new TransactionError(e.toString()); 
		}
		/*
		if (get_history == 1) {
			
			this.acct_id = customerAccountRowData.get(acct_idx + 1).CA_ID;
			
		}
		*/
		acc_len = customerAccountRowData.size();
		
	}

	@Override
	public void invokeCustomerPositionFrame2() {
		// TODO: implement 
	}

}
