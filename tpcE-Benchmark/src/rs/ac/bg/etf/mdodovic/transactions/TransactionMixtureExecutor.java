package rs.ac.bg.etf.mdodovic.transactions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.transactions.NT.CustomerPozition_T2_Normalized;
import rs.ac.bg.etf.mdodovic.transactions.NT.MarketFeed_T3_Normalized;
import rs.ac.bg.etf.mdodovic.transactions.NT.TradeResult_T8_Normalized;

public class TransactionMixtureExecutor {
	
	private CustomerPosition_T2 T2;
	private TradeResult_T8 T8;
	private MarketFeed_T3 T3;
	
	public TransactionMixtureExecutor(Connection connection, String schemaModelName) {

		switch (schemaModelName) {
			case "NT": 
				T2 = new CustomerPozition_T2_Normalized(connection);
				T8 = new TradeResult_T8_Normalized(connection);
				T3 = new MarketFeed_T3_Normalized(connection);
				break;

			case "FullDT": 
				break;

			case "PartialNT": 
				break;
		}
		
	}
	
	public void startTransactionMixture(String transactionMixFile, PrintWriter timestampResultFile, PrintWriter differenceResultFile) throws IOException, TransactionError {

		long transactionsNumber = Files.lines(Paths.get(transactionMixFile), StandardCharsets.UTF_8).count();
	
		long readTransactionCounter = 0;
		long writeTransactionCounter = 0;

		long currentTransaction = 0;
		
		System.out.println("Transaction mixture start");		
		long transactionMixtureTime = System.nanoTime();
		
		try (FileReader fr = new FileReader(transactionMixFile);
				BufferedReader br = new BufferedReader(fr);) {

			String s;
			int i = 0;
			while((s = br.readLine()) != null){
				String[] parsedTransaction = s.split(" ");
				i++;
				if ("CustomerPositionFrame1".equals(parsedTransaction[1])) {
					
					// T2 input data:
					
					String[] data = parsedTransaction[2].split(",");
					long cust_id = Long.parseLong(data[0]);
					String tax_id = data[1];
					int get_history = 0;
					int acct_idx = -1;
	//				System.out.println(cust_id + ", " + tax_id + ", " +  get_history + ", " + acct_idx);
					
					// Invoke transaction
					long startTransaction = System.nanoTime();
					T2.setInputTransactionParameters(cust_id, tax_id, get_history, acct_idx);
					T2.startTransaction();
	
					differenceResultFile.write("" + (System.nanoTime() - startTransaction) + "\n");
					//System.out.println((System.nanoTime() - startTransaction));
					readTransactionCounter++;
	
				} 
				if ("MarketFeedFrame1".equals(parsedTransaction[1])) {
	
					// T3 input data:
					String[] data = parsedTransaction[2].split(",");	
					double[] price_quote = new double[] {Double.parseDouble(data[0])};
					String status_submitted = data[1];
					String[] symbol = new String[]{data[2]};
					long[] trade_qty = new long[] {Long.parseLong(data[3])};
					String type_limit_buy = "";
					String type_limit_sell = "";
					String type_stop_loss = "";
					
					long startTransaction = System.nanoTime();
					
					T3.setInputTransactionParameters(price_quote, status_submitted, 
							symbol, trade_qty, type_limit_buy, type_limit_sell, 
							type_stop_loss);					
					T3.startTransaction();
	
					differenceResultFile.write("" + (System.nanoTime() - startTransaction) + "\n");
	
					writeTransactionCounter++;
				}
				if ("TradeResultFrame2".equals(parsedTransaction[1])) {
	
					// T8 input data:
					String[] data = parsedTransaction[2].split(",");
					long acct_id = Long.parseLong(data[0]);
					int hs_qty = Integer.parseInt(data[1]);				
					String symbol = data[2];
					int trade_qty = Integer.parseInt(data[3]);
	
					long startTransaction = System.nanoTime();
					
					T8.setInputTransactionParameters(acct_id, symbol, hs_qty, trade_qty, -1.);
					T8.startTransaction(2);
	
					differenceResultFile.write("" + (System.nanoTime() - startTransaction) + "\n");
					
					writeTransactionCounter++;
				} 					
				if ("TradeResultFrame6".equals(parsedTransaction[1])) {
	
					// T8 input data:
					String[] data = parsedTransaction[2].split(",");
					long acct_id = Long.parseLong(data[0]);
					double se_amount = Double.parseDouble(data[1]);
					
					long startTransaction = System.nanoTime();

					T8.setInputTransactionParameters(acct_id, "", -1, -1, se_amount);
					T8.startTransaction(6);

					differenceResultFile.write("" + (System.nanoTime() - startTransaction) + "\n");
	
					writeTransactionCounter++;
				} 		
				
				currentTransaction ++;
				if(currentTransaction % 1000 == 0) {
					System.out.println("Finished " + 
							String.format("%.2f", 100. * currentTransaction / transactionsNumber) 
							+ "% transactions ( w: " + writeTransactionCounter + 
							"; r: " + readTransactionCounter + ")");
				}
				timestampResultFile.write("" + System.nanoTime() + "\n");
	
			}
		} catch (FileNotFoundException e) {
			throw new TransactionError(e.toString());
		} catch (IOException e) {
			throw new TransactionError(e.toString());
		} catch (TransactionError te) {
			throw new TransactionError(te.toString());			
		}
		
		transactionMixtureTime = System.nanoTime() - transactionMixtureTime ;
		System.out.println("Transaction mixture end after " + (transactionMixtureTime / 1e9) + " seconds");

		System.out.println("Read transactions executed: " + readTransactionCounter);
		System.out.println("Write transactions executed: " + writeTransactionCounter);

	}

}
