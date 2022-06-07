package rs.ac.bg.etf.mdodovic.singleQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class SingleQueries {
	
	public void T2F1_NT_NoConditions_NoGroupBy(Connection connection) {
		
		String query = "SELECT CA_ID, CA_BAL "
				+ "	FROM tpce_mysql.CUSTOMER_ACCOUNT "
				+ "		LEFT OUTER JOIN ( "
				+ "					tpce_mysql.HOLDING_SUMMARY  "
				+ "						INNER JOIN tpce_mysql.LAST_TRADE ON (LT_S_SYMB = HS_S_SYMB) "
				+ "					) ON HS_CA_ID = CA_ID ";
		
		rowid = 1;
		rows.clear();
		long timeBefore = System.nanoTime();
		
		try (PreparedStatement stmt = connection.prepareStatement(query)){

			try(ResultSet rs = stmt.executeQuery();) {
				
				while(rs.next()) {
					T2F1_ReducedREsult r = new T2F1_ReducedREsult();
					r.CA_ID = rs.getLong(1);
					r.CA_BAL = rs.getDouble(2);
					rows.put(rowid, r);
					rowid += 1;
				}
				
			}

		} catch(SQLException e) { 
			e.printStackTrace();
		}

		long queryTime = System.nanoTime() - timeBefore; 
		System.out.println("[T2F1_NT_NoConditions_NoGroupBy]: " + (queryTime / 1e9) + " seconds");
		
		
	}
	
	public void T2F1_FullyDT_NoConditions_NoGroupBy(Connection connection) {
		
		String query = "SELECT DT_CA_ID AS CA_ID, DT_CA_BAL AS CA_BAL "
						+ "	FROM tpce_mysql.DTT2T3T8 "
						+ "    WHERE DT_HS_S_SYMB IS NOT NULL ";

		rowid = 1;
		rows.clear();		
		long timeBefore = System.nanoTime();
		
		try (PreparedStatement stmt = connection.prepareStatement(query)){

			try(ResultSet rs = stmt.executeQuery();) {
				
				while(rs.next()) {
					T2F1_ReducedREsult r = new T2F1_ReducedREsult();
					r.CA_ID = rs.getLong(1);
					r.CA_BAL = rs.getDouble(2);
					rows.put(rowid, r);
					rowid += 1;
				}
				
			}

		} catch(SQLException e) { 
			e.printStackTrace();
		}

		long queryTime = System.nanoTime() - timeBefore; 
		System.out.println("[T2F1_FullyDT_NoConditions_NoGroupBy]: " + (queryTime / 1e9) + " seconds");
		
		
	}

	public void T2F1_PartiallyDT_NoConditions_NoGroupBy(Connection connection) {
		
		String query = "SELECT tpce_mysql.DTT2T3T8F2.DT_CA_ID AS CA_ID, DT_CA_BAL AS CA_BAL "
						+ "	FROM tpce_mysql.DTT2T3T8F2 "
						+ "		INNER JOIN tpce_mysql.DTT8F6 ON (tpce_mysql.DTT2T3T8F2.DT_CA_ID = tpce_mysql.DTT8F6.DT_CA_ID) "
						+ " WHERE DT_HS_S_SYMB IS NOT NULL ";
		rowid = 1;
		rows.clear();
		long timeBefore = System.nanoTime();
		
		try (PreparedStatement stmt = connection.prepareStatement(query)){
	
			try(ResultSet rs = stmt.executeQuery();) {
				
				while(rs.next()) {
					T2F1_ReducedREsult r = new T2F1_ReducedREsult();
					r.CA_ID = rs.getLong(1);
					r.CA_BAL = rs.getDouble(2);
					rows.put(rowid, r);
					rowid += 1;
				}
				
			}
	
		} catch(SQLException e) { 
			e.printStackTrace();
		}
	
		long queryTime = System.nanoTime() - timeBefore; 
		System.out.println("[T2F1_PartiallyDT_NoConditions_NoGroupBy]: " + (queryTime / 1e9) + " seconds");
		
		
	}
	
	private long rowid = 1; // identity(1,1): start value is 1 and it is incremented by 1 every time
	private Map<Long, T2F1_ReducedREsult> rows = new HashMap<Long, T2F1_ReducedREsult>();
	
	
	public class T2F1_ReducedREsult {

		private long CA_ID;	
		private double CA_BAL; 
		
		@Override
		public String toString() {
			return CA_ID + " " 
					+ CA_BAL;

		}
	}
	
}
