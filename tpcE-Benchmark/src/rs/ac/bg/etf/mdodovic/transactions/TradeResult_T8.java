package rs.ac.bg.etf.mdodovic.transactions;

import java.sql.Connection;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;

public abstract class TradeResult_T8 {

	protected Connection connection;
	
	// Input parameters for T8
	
	protected long acct_id;
	protected String symbol;
	protected int hs_qty;
	protected int trade_qty;
	protected double se_amount;

	
	public TradeResult_T8(Connection connection) {
		this.connection = connection;
	}


	public void setInputTransactionParameters(long acct_id, String symbol, int hs_qty, int trade_qty, double se_amount) {
		this.acct_id = acct_id;
		this.symbol = symbol;
		this.hs_qty = hs_qty;
		this.trade_qty = trade_qty;
		this.se_amount = se_amount;
	}

	public void startTransaction(int transactionNumber) throws TransactionError {
		// Transaction - tpcE:T8 transaction is taken from the tpcE documentation

		// Frame 1:
		// Frame 2:
		if(transactionNumber == 2)
			invokeTradeResultFrame2_T8F2();
		// Frame 3:
		// Frame 4:
		// Frame 5:
		// Frame 6:
		if(transactionNumber == 6)
			invokeTradeResultFrame6_T8F6();

	}	

		
	public abstract void invokeTradeResultFrame2_T8F2() throws TransactionError;
	public abstract void invokeTradeResultFrame6_T8F6() throws TransactionError;

}