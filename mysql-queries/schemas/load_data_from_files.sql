LOAD DATA 
	LOCAL INFILE  'C:/Users/matij/Desktop/HoldingSummary.txt' 	
    INTO TABLE tpce_mysql.HOLDING_SUMMARY	
    FIELDS TERMINATED BY '|' 	
    LINES TERMINATED BY '\n';
        
LOAD DATA 
	LOCAL INFILE 	'C:/Users/matij/Desktop/LastTrade.txt' 	
    INTO TABLE tpce_mysql.LAST_TRADE	
    FIELDS TERMINATED BY '|' 	
    LINES TERMINATED BY '\n';
    
LOAD DATA 
	LOCAL INFILE  'C:/Users/matij/Desktop/CustomerAccount.txt' 	
    INTO TABLE tpce_mysql.CUSTOMER_ACCOUNT	
    FIELDS TERMINATED BY '|' 	
    LINES TERMINATED BY '\n';
        
LOAD DATA 
	LOCAL INFILE  'C:/Users/matij/Desktop/Customer.txt' 	
    INTO TABLE tpce_mysql.CUSTOMER	
    FIELDS TERMINATED BY '|' 	
    LINES TERMINATED BY '\n';