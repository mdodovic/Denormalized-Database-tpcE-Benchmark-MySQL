package rs.ac.bg.etf.mdodovic.schema;

public class Tables {
	
	/**
	 * Several tables from full tpcE model, chosen to cover interesting transactions: T2F1, T3, T8F2 and T8F6
	 */
	public static final String[] normalizedTableNames = new String[] {
			"HOLDING_SUMMARY",
			"LAST_TRADE",
			"CUSTOMER_ACCOUNT",
			"CUSTOMER",
		};
	
	/**
	 * Full denormalization made for transactions: T2F1, T3, T8F2 and T8F6 (all)
	 */
	public static final String[] fullyDenormalizedTableNames = new String[] {
			"DTT2T3T8"
		};
			
	/**
	 * Partial denormalization made for transactions: T2F1, T3, T8F2 (first table) and T8F6 (second table)
	 */
	public static final String[] partiallyDenormalizedTableNames = new String[] {
			"DTT2T3T8F2",
			"DTT8F6"
		};


}
