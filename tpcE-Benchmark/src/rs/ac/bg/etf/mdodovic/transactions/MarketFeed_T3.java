package rs.ac.bg.etf.mdodovic.transactions;

import java.sql.Connection;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;

public abstract class MarketFeed_T3 {

	protected Connection connection;

	// Input parameters for T3
	
	protected double[] price_quote;
	//private String status_submitted;
	protected String[] symbol;
	protected long[] trade_qty;
	//private String type_limit_buy;
	//private String type_limit_sell;
	//private String type_stop_loss;

	
	public MarketFeed_T3(Connection connection) {
		this.connection = connection;
	}
		
	public void setInputTransactionParameters(double[] price_quote, String status_submitted, 
			String[] symbol, long[] trade_qty, String type_limit_buy, 
			String type_limit_sell, String type_stop_loss) {
		
		this.price_quote = price_quote;
		this.symbol = symbol;
		this.trade_qty = trade_qty;
	}

	public void startTransaction() throws TransactionError {
		// Transaction - tpcE:T3 transaction is taken from the tpcE documentation

		// Frame 1:
		invokeMarketFeedFrame1_T3F1();

	}
	
	public abstract void invokeMarketFeedFrame1_T3F1() throws TransactionError;

}
