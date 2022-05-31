package rs.ac.bg.etf.mdodovic.transactions.FullyDT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.transactions.CustomerPosition_T2;

public class CustomerPozition_T2_FullyDenormalized extends CustomerPosition_T2 {

	public CustomerPozition_T2_FullyDenormalized(Connection connection) {
		super(connection);
	}

	@Override
	public void invokeCustomerPositionFrame1_T2F1() throws TransactionError {

	
		String getCustomerAccountInfo = "SELECT "
				+ " 		DT_CA_ID AS CA_ID, DT_CA_BAL AS CA_BAL, (sum(DT_HS_QTY * DT_LT_PRICE)) AS RES_SUM "
				+ "		FROM tpce_mysql.DTT2T3T8 "
				+ "		WHERE DT_CA_C_ID = ? "
				+ "			  AND DT_HS_S_SYMB IS NOT NULL "
				+ "		GROUP BY DT_CA_ID, DT_CA_BAL "
				+ "		ORDER BY 3 ASC; ";
		
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
