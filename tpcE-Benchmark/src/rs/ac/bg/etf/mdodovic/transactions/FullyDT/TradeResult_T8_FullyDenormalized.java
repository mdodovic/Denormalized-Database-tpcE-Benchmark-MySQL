package rs.ac.bg.etf.mdodovic.transactions.FullyDT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.transactions.TradeResult_T8;

public class TradeResult_T8_FullyDenormalized extends TradeResult_T8 {

	public TradeResult_T8_FullyDenormalized(Connection connection) {
		super(connection);
	}

	@Override
	public void invokeTradeResultFrame2_T8F2() throws TransactionError {

		String updateHoldingSummary = "UPDATE tpce_mysql.DTT2T3T8 "
										+ "	SET DT_HS_QTY = ? + ? "
										+ " WHERE DT_CA_ID = ? "
										+ "		AND DT_HS_S_SYMB = ? ";
	
		// Index is established already: PK = (HS_CA_ID, HS_S_SYMB);
		try (PreparedStatement stmt = connection.prepareStatement(updateHoldingSummary)){

			connection.setAutoCommit(false);
			
			stmt.setInt(1, hs_qty);
			stmt.setInt(2, trade_qty);			
			stmt.setLong(3, acct_id);
			stmt.setString(4, symbol);			

			stmt.executeUpdate();				
			//System.out.println("TRF2: " + acct_id + " + " + symbol + " = " + hs_qty + " + " + trade_qty);
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new TransactionError(e1.toString());
			}
			throw new TransactionError(e.toString());
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new TransactionError(e.toString());
			}
		}
		
	}

	@Override
	public void invokeTradeResultFrame6_T8F6() throws TransactionError {

		String updateLastTrade = "UPDATE tpce_mysql.DTT2T3T8 "
								+ "	SET DT_CA_BAL = DT_CA_BAL + ? "
								+ "	WHERE DT_CA_ID = ? ";

		// Index is established already: PK = (CA_ID);
		
		try (PreparedStatement stmt = connection.prepareStatement(updateLastTrade)){

			connection.setAutoCommit(false);
			
			stmt.setDouble(1, se_amount);
			stmt.setLong(2, acct_id);			

			stmt.executeUpdate();				
			//System.out.println("TRF6: " + acct_id);
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
