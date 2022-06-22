SELECT DT_CA_ID AS CA_ID, DT_CA_BAL AS CA_BAL, (sum(DT_HS_QTY * DT_LT_PRICE)) as RES_SUM
	FROM tpce_mysql.DTT2T3T8
    WHERE DT_CA_C_ID = 4300000007 
		AND DT_HS_S_SYMB IS NOT NULL
	GROUP BY DT_CA_ID, DT_CA_BAL
    ORDER BY 3 ASC