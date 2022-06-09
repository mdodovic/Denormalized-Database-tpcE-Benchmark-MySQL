update tpce_mysql.HOLDING_SUMMARY
	set HS_QTY = 7200 + (-1100)
	where HS_CA_ID = 43000000001 and HS_S_SYMB = 'CHTT'
