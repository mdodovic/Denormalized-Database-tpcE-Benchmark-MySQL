package rs.ac.bg.etf.mdodovic.transactions.FullyDT;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.transactions.MarketFeed_T3;

public class MarketFeed_T3_FullyDenormalized extends MarketFeed_T3 {

	public MarketFeed_T3_FullyDenormalized(Connection connection) {
		super(connection);
	}

	@Override
	public void invokeMarketFeedFrame1_T3F1() throws TransactionError {
		long millis = System.currentTimeMillis();
		Date currentDate = new Date(millis); // current date
		/*
		TradeRequestBuffer[]
		long req_price_quote S_PRICE_T
		long req_trade_id TRADE_T
		long req_trade_qty S_QTY_T
		String req_trade_type 
		int rows_sent;
		*/
		int rows_updated = 0;
		
		
		for(int i = 0; i < price_quote.length/*Constraints.max_feed_len*/; i++) {
			// must be as a signle transaction with rollback mechanism

			String updateLastTrade = "update tpce_mysql.LAST_TRADE "
									+ "	set LT_PRICE = ?, LT_VOL = LT_VOL + ?, LT_DTS = ? "
									+ "	where LT_S_SYMB = ? ";
									
			try (PreparedStatement stmt = connection.prepareStatement(updateLastTrade)){

				connection.setAutoCommit(false);
				
				stmt.setDouble(1, price_quote[i]);
				stmt.setLong(2, trade_qty[i]);			
				stmt.setDate(3, currentDate);
				stmt.setString(4, symbol[i]);			

				int row_count = stmt.executeUpdate();				
				rows_updated = rows_updated + row_count;
				//System.out.println("MF: " + symbol[i]);
				connection.commit();
				
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					throw new TransactionError(e1.toString());
				}
				throw new TransactionError(e.toString());
			}finally {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					throw new TransactionError(e.toString());
				}
			}
			
		}
		
	}

}
