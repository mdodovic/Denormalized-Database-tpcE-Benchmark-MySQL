CREATE INDEX DTT2T3T8_Index_DT_CA_C_ID ON tpce_mysql.DTT2T3T8 (DT_CA_C_ID) USING BTREE;

CREATE INDEX DTT2T3T8_Index_DT_HS_S_SYMB__DT_CA_ID ON tpce_mysql.DTT2T3T8 (DT_HS_S_SYMB, DT_CA_ID) USING BTREE;