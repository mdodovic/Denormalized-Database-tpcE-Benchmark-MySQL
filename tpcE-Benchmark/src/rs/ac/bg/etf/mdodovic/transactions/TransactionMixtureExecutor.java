package rs.ac.bg.etf.mdodovic.transactions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.stream.Stream;

import rs.ac.bg.etf.mdodovic.Main;
import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.transactions.NT.CustomerPozition_T2_Normalized;

public class TransactionMixtureExecutor {
	
	private int status;

	private Connection connection;
	
	private CustomerPosition_T2 T2;
	
	public TransactionMixtureExecutor(Connection connection, String schemaModelName) {
		this.connection = connection;
		
		switch (schemaModelName) {
			case "NT": 
				T2 = new CustomerPozition_T2_Normalized(connection);
				break;

			case "FullDT": 
				break;

			case "PartialNT": 
				break;
		}
		
	}
	
	public void startTransactionMixture(String transactionMixFile) throws IOException, TransactionError {

		long transactionsNumber = Files.lines(Paths.get(transactionMixFile), StandardCharsets.UTF_8).count();
	
		long readTransactionCounter = 0;
		long writeTransactionCounter = 0;

		long currentTransaction = 0;
		
		System.out.println("Transaction mixture start");		
		long transactionMixtureTime = System.nanoTime();
		
		// TODO: close
		FileReader fr = new FileReader(transactionMixFile);
		BufferedReader br = new BufferedReader(fr);

		String s;
		while((s = br.readLine()) != null){
			String[] parsedTransaction = s.split(" ");
			
			if ("CustomerPositionFrame1".equals(parsedTransaction[1])) {

				String[] data = parsedTransaction[2].split(",");
				long cust_id = Long.parseLong(data[0]);
				String tax_id = data[1];
				int get_history = 0;
				int acct_idx = -1;
//				System.out.println(cust_id + ", " + tax_id + ", " +  get_history + ", " + acct_idx);

				long startTransaction = System.nanoTime();
				T2.setInputTransactionParameters(cust_id, tax_id, get_history, acct_idx);
				T2.startTransaction();

//				difference.write("" + (System.nanoTime() - startTransaction) + "\n");
				//System.out.println((System.nanoTime() - startTransaction));
				readTransactionCounter++;

			} 
//			if ("MarketFeedFrame1".equals(parsedTransaction[1])) {
//
//				String[] data = parsedTransaction[2].split(",");
//
//				double[] price_quote = new double[] {Double.parseDouble(data[0])};
//				String status_submitted = data[1];
//				String[] symbol = new String[]{data[2]};
//				long[] trade_qty = new long[] {Long.parseLong(data[3])};
//				String type_limit_buy = "";
//				String type_limit_sell = "";
//				String type_stop_loss = "";
//				
//				long startTransaction = System.nanoTime();
//				
//				startMarketFeedTransaction(price_quote, status_submitted, 
//						symbol, trade_qty, type_limit_buy, type_limit_sell,type_stop_loss);					
//
//				difference.write("" + (System.nanoTime() - startTransaction) + "\n");
//
//				writeTransactionCounter++;
//			}
//			if ("TradeResultFrame2".equals(parsedTransaction[1])) {
//
//				String[] data = parsedTransaction[2].split(",");
//				
//				long acct_id = Long.parseLong(data[0]);
//				int hs_qty = Integer.parseInt(data[1]);				
//				String symbol = data[2];
//				int trade_qty = Integer.parseInt(data[3]);
//
//				long startTransaction = System.nanoTime();
//				
//				startTradeResult(acct_id, symbol, hs_qty, trade_qty, -1., 2);
//
//				difference.write("" + (System.nanoTime() - startTransaction) + "\n");
//				
//				writeTransactionCounter++;
//			} 					
//			if ("TradeResultFrame6".equals(parsedTransaction[1])) {
//
//				String[] data = parsedTransaction[2].split(",");
//				long acct_id = Long.parseLong(data[0]);
//				double se_amount = Double.parseDouble(data[1]);
//
//				long startTransaction = System.nanoTime();
//				
//				startTradeResult(acct_id, "", -1, -1, se_amount, 6);
//
//				difference.write("" + (System.nanoTime() - startTransaction) + "\n");
//
//				writeTransactionCounter++;
//			} 		
			
			currentTransaction ++;
			if(currentTransaction % 1000 == 0) {
				System.out.println("Finished " + 
						String.format("%.2f", 100. * currentTransaction / transactionsNumber) 
						+ "% transactions ( w: " + writeTransactionCounter + 
						"; r: " + readTransactionCounter + ")");
			}
//			timestamp.write("" + System.nanoTime() + "\n");

		}
			
		
		transactionMixtureTime = System.nanoTime() - transactionMixtureTime ;
		System.out.println("Transaction mixture end after " + (transactionMixtureTime / 1e9) + " seconds");

		System.out.println("Read transactions executed: " + readTransactionCounter);
		System.out.println("Write transactions executed: " + writeTransactionCounter);

	}	
//
//	public void startTransactionMixture(String pathToData, PrintWriter timestamp, PrintWriter difference) {
//
//		long totalLineCounter = 0;
//		try (Stream<String> stream = Files.lines(Paths.get(Main.inputDataFile), StandardCharsets.UTF_8)) {
//			totalLineCounter = stream.count();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		long readTransactionCounter = 0;
//		long writeTransactionCounter = 0;
//
//		long lineCounter = 0;
//		long currentTime = System.nanoTime();
//		
//		timestamp.write("" + System.nanoTime() + "\n");
//		
//		try (FileReader fr = new FileReader(pathToData);
//			BufferedReader br = new BufferedReader(fr)){
//			String s;
//			while((s = br.readLine()) != null){
//				String[] parsedTransaction = s.split(" ");
//				
//				if ("CustomerPositionFrame1".equals(parsedTransaction[1])) {
//
//					String[] data = parsedTransaction[2].split(",");
//					long cust_id = Long.parseLong(data[0]);
//					String tax_id = data[1];
//					int get_history = 0;
//					int acct_idx = -1;
////					System.out.println(cust_id + ", " + tax_id + ", " +  get_history + ", " + acct_idx);
//
//					long startTransaction = System.nanoTime();
//
//					startCustomerPositionTransaction(cust_id, tax_id, get_history, acct_idx);
//
//					difference.write("" + (System.nanoTime() - startTransaction) + "\n");
//					//System.out.println((System.nanoTime() - startTransaction));
//					readTransactionCounter++;
//
//				} 
//				if ("MarketFeedFrame1".equals(parsedTransaction[1])) {
//
//					String[] data = parsedTransaction[2].split(",");
//
//					double[] price_quote = new double[] {Double.parseDouble(data[0])};
//					String status_submitted = data[1];
//					String[] symbol = new String[]{data[2]};
//					long[] trade_qty = new long[] {Long.parseLong(data[3])};
//					String type_limit_buy = "";
//					String type_limit_sell = "";
//					String type_stop_loss = "";
//					
//					long startTransaction = System.nanoTime();
//					
//					startMarketFeedTransaction(price_quote, status_submitted, 
//							symbol, trade_qty, type_limit_buy, type_limit_sell,type_stop_loss);					
//
//					difference.write("" + (System.nanoTime() - startTransaction) + "\n");
//
//					writeTransactionCounter++;
//				}
//				if ("TradeResultFrame2".equals(parsedTransaction[1])) {
//
//					String[] data = parsedTransaction[2].split(",");
//					
//					long acct_id = Long.parseLong(data[0]);
//					int hs_qty = Integer.parseInt(data[1]);				
//					String symbol = data[2];
//					int trade_qty = Integer.parseInt(data[3]);
//
//					long startTransaction = System.nanoTime();
//					
//					startTradeResult(acct_id, symbol, hs_qty, trade_qty, -1., 2);
//
//					difference.write("" + (System.nanoTime() - startTransaction) + "\n");
//					
//					writeTransactionCounter++;
//				} 					
//				if ("TradeResultFrame6".equals(parsedTransaction[1])) {
//
//					String[] data = parsedTransaction[2].split(",");
//					long acct_id = Long.parseLong(data[0]);
//					double se_amount = Double.parseDouble(data[1]);
//
//					long startTransaction = System.nanoTime();
//					
//					startTradeResult(acct_id, "", -1, -1, se_amount, 6);
//	
//					difference.write("" + (System.nanoTime() - startTransaction) + "\n");
//
//					writeTransactionCounter++;
//				} 					
//				lineCounter ++;
//				if(lineCounter % 1000 == 0) {
//					System.out.println("Finished " + 
//							String.format("%.2f", 100. * lineCounter / totalLineCounter) 
//							+ "% transactions ( w: " + writeTransactionCounter + 
//							"; r: " + readTransactionCounter + ")");
//				}
//				timestamp.write("" + System.nanoTime() + "\n");
//
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		currentTime = System.nanoTime() - currentTime;
//		System.out.println("Finish after: " + currentTime + " nanoseconds");
//
//		System.out.println("Read transactions: " + readTransactionCounter);
//		System.out.println("Write transactions: " + writeTransactionCounter);
//
//	}
//	


}
