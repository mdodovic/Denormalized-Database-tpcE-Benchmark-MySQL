SELECT COUNT(*) 
	FROM (
		SELECT tpce_mysql.DTT2T3T8F2.DT_CA_ID AS CA_ID, DT_CA_BAL AS CA_BAL
			FROM tpce_mysql.DTT2T3T8F2 inner join tpce_mysql.DTT8F6 ON tpce_mysql.DTT2T3T8F2.DT_CA_ID = tpce_mysql.DTT8F6.DT_CA_ID  
			WHERE DT_HS_S_SYMB IS NOT NULL
	)