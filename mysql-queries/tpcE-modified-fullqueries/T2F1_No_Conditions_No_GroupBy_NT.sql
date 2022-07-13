SELECT CA_ID, CA_BAL
	FROM tpce_mysql.CUSTOMER_ACCOUNT 
		LEFT OUTER JOIN (
					tpce_mysql.HOLDING_SUMMARY 
						INNER JOIN tpce_mysql.LAST_TRADE ON (LT_S_SYMB = HS_S_SYMB)
					) ON HS_CA_ID = CA_ID
