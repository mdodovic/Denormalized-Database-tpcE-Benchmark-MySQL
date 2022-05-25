package rs.ac.bg.etf.mdodovic.transactions.NT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.transactions.CustomerPosition_T2;

public class CustomerPozition_T2_Normalized extends CustomerPosition_T2 {

	public CustomerPozition_T2_Normalized(Connection connection) {
		super(connection);
	}

	@Override
	public void invokeCustomerPositionFrame1() throws TransactionError {

/* Not interested with the view of measurement!
 		if(cust_id == 0) {
			
			String getIdFromTaxIdQuery = "select C_ID from CUSTOMER where C_TAX_ID = ?";
			try (PreparedStatement stmt = databaseConnection.prepareStatement(getIdFromTaxIdQuery)){
				stmt.setString(1, tax_id);
				try(ResultSet rs = stmt.executeQuery()){
				
					if(rs.next() == true) {
						cust_id = rs.getLong(1);
					}
				}
			} catch(SQLException e) { 
				e.printStackTrace(); 
			}
			
		}
		// Now we only operate with cust_id 

		// Fetch all customer data:
		String getCustomerInfo = "SELECT C_ST_ID,C_L_NAME,C_F_NAME,C_M_NAME,C_GNDR,C_TIER,C_DOB,C_AD_ID,C_CTRY_1,C_AREA_1,\r\n" + 
				"	C_LOCAL_1,C_EXT_1,C_CTRY_2,C_AREA_2,C_LOCAL_2,C_EXT_2,C_CTRY_3,C_AREA_3,C_LOCAL_3,\r\n" + 
				"	C_EXT_3,C_EMAIL_1,C_EMAIL_2\r\n" + 
				"FROM CUSTOMER\r\n" + 
				"WHERE C_ID = ?;";
		
		try (PreparedStatement stmt = databaseConnection.prepareStatement(getCustomerInfo)){
			stmt.setLong(1, cust_id);
			try(ResultSet rs = stmt.executeQuery()){
								
				fillDataToCustomerRows(rs);
				
				//for (Long key : this.customerRowData.keySet()) {
				//    System.out.println(customerRowData.get(key));
				//}
			}
		} catch(SQLException e) { 
			e.printStackTrace(); 
		}
*/
		// Fetch account info:
		
		String getCustomerAccountInfo = "select CA_ID, CA_BAL, ((sum(HS_QTY * LT_PRICE))) as RES_SUM "
				+ "	from tpce_mysql.CUSTOMER_ACCOUNT "
				+ "		left outer join ( "
				+ "						tpce_mysql.HOLDING_SUMMARY inner join tpce_mysql.LAST_TRADE on (LT_S_SYMB = HS_S_SYMB) "
				+ "                        ) on (HS_CA_ID = CA_ID) "
				+ "	where CA_C_ID = ? "
				+ "	group by CA_ID, CA_BAL "
				+ "	order by 3 asc ";
		
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
