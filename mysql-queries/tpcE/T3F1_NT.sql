update tpce_mysql.LAST_TRADE
	set LT_PRICE = 31.2, LT_VOL = LT_VOL + 1, LT_DTS = SYSDATE()
	WHERE LT_S_SYMB = 'A'
