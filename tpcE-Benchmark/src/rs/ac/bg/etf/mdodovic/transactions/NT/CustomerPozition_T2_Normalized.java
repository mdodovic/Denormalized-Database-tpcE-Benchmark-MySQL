package rs.ac.bg.etf.mdodovic.transactions.NT;

import java.sql.Connection;

import rs.ac.bg.etf.mdodovic.transactions.CustomerPosition_T2;

public class CustomerPozition_T2_Normalized extends CustomerPosition_T2 {

	public CustomerPozition_T2_Normalized(Connection connection, long cust_id, String tax_id, int get_history,
			long acct_idx) {
		super(connection, cust_id, tax_id, get_history, acct_idx);
	}

	@Override
	public void invokeCustomerPositionFrame1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void invokeCustomerPositionFrame2() {
		// TODO Auto-generated method stub

	}

}
