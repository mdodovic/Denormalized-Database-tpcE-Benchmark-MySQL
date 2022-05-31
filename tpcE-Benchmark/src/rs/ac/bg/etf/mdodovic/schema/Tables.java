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
	 * Full denormalization made for transactions: T2F1, T3, T8F2 and T8F6 (DTT2T3T8)
	 */
	public static final String fullyDenormalizedTableName = "DTT2T3T8";
	
			
	/**
	 * Partial denormalization made for transactions: T2F1, T3, T8F2 (DTT2T3T8F2) and T8F6 (DTT8F6)
	 * This table covers T2F1, T3 and T8F2 transactions
	 */
	public static final String partiallyDenormalizedTableT2T3T8F2 = "DTT2T3T8F2";

	/**
	 * Partial denormalization made for transactions: T2F1, T3, T8F2 (DTT2T3T8F2) and T8F6 (DTT8F6)
	 * This table covers T8F6 transaction
	 */
	public static final String partiallyDenormalizedTableT8F6= "DTT8F6";


}
