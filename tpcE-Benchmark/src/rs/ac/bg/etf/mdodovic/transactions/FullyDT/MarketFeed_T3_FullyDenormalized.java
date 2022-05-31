package rs.ac.bg.etf.mdodovic.transactions.FullyDT;

import java.sql.Connection;
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
		
		int rows_updated = 0;
		for(int i = 0; i < price_quote.length/*Constraints.max_feed_len*/; i++) {
			// must be as a signle transaction with rollback mechanism

			String updateLastTrade = "UPDATE tpce_mysql.DTT2T3T8 "
									+ "	SET DT_LT_PRICE = ? "
									+ "	WHERE DT_HS_S_SYMB = ? ";
									
			try (PreparedStatement stmt = connection.prepareStatement(updateLastTrade)){

				connection.setAutoCommit(false);
				
				stmt.setDouble(1, price_quote[i]);
				stmt.setString(2, symbol[i]);			

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
