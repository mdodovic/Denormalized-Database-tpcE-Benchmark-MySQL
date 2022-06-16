select 
    CA_ID, CA_BAL, (sum(HS_QTY * LT_PRICE))
  from
    tpce_mysql.CUSTOMER_ACCOUNT 
		left outer join 
					(tpce_mysql.HOLDING_SUMMARY inner join tpce_mysql.LAST_TRADE on LT_S_SYMB = HS_S_SYMB)   
                    on HS_CA_ID = CA_ID
  group by
    CA_ID, CA_BAL