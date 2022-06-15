use tpce_mysql;
select max(C), avg(C) from
(select DT_HS_S_SYMB, count(*) C
from DTT2T3T8
group by DT_HS_S_SYMB) X;
 
select max(C), avg(C) from
(select DT_CA_ID, count(*) C
from DTT2T3T8
group by DT_CA_ID) X; 


select max(DT.C), avg(DT.C) 
from (
	select DT_CA_C_ID, count(*) C
	from DTT2T3T8
	group by DT_CA_C_ID
	) DT;

        
        