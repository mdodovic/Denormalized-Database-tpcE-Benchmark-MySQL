package rs.ac.bg.etf.mdodovic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.schema.create.NormalizedSchemaCreator;
import rs.ac.bg.etf.mdodovic.schema.loaddata.NormalizedSchemaLoader;
import rs.ac.bg.etf.mdodovic.transactions.TransactionMixtureExecutor;

public class Main {

	public static final String USER = "root";
	public static final String PASSWORD = "Kraljice.Natalije.37";

	private Connection connection;

	public static final String pathToResultFolderNormalized = "./src/time_results/normalized/";
	
	public static List<String> transactionMixFilesList = new ArrayList<String>();
	public static List<String> outputResultFileList = new ArrayList<String>();
	
	private TransactionMixtureExecutor transactionMixtureExecutor;
	
	static {
		transactionMixFilesList.add("inputData/T2T3T8_T2F1_read_130k.sql");
		transactionMixFilesList.add("inputData/T2T3T8_T3F1_write_130k.sql");
		transactionMixFilesList.add("inputData/T2T3T8_T8F2_write_130k.sql");
		transactionMixFilesList.add("inputData/T2T3T8_T8F6_write_130k.sql");
		transactionMixFilesList.add("inputData/T2T3T8_T3T8_all_write_130k.sql");

		outputResultFileList.add("T2F1_read_130k");
		outputResultFileList.add("T3F1_write_130k");
		outputResultFileList.add("T8F2_write_130k");
		outputResultFileList.add("T8F6_write_130k");
		outputResultFileList.add("T3T8_all_write_130k");

	}

	public static String inputDataFile;
	public static String outputResultFile;
	
	public Main(String schemaModelName) {

		connectToMySQL();

		transactionMixtureExecutor = new TransactionMixtureExecutor(connection, schemaModelName);

	}
	
	public void startTransactionMixture(String transactionMixFile) throws IOException, TransactionError {
		transactionMixtureExecutor.startTransactionMixture(transactionMixFile);
	}		
	
	public void connectToMySQL() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
		
			String connectionString = "jdbc:mysql://localhost:3306/";
			
			String databaseName = "tpce_mysql";
			connectionString += databaseName;

			String connectionProperties = "?allowLoadLocalInfile=true"
										+ "&allowMultiQueries=true";						
			connectionString += connectionProperties;
			
			connection = DriverManager.getConnection(connectionString, USER, PASSWORD);

			System.out.println("Connected to MySQL");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void disconnectFromMySQL() {
		try {
			
			if(connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void tpcENormalized(String transactionMixFile, String outputResultFile) {
		
		long applicationTime = System.nanoTime();
		
		Main database = new Main("NT");		
		
		try {
/*		try (FileWriter fw1 = new FileWriter(pathToResultFolderNormalized + outputResultFile +"_timestamp.txt");
				PrintWriter timestamp = new PrintWriter(fw1);
					FileWriter fw2 = new FileWriter(pathToResultFolderNormalized + outputResultFile + "_difference.txt");
					PrintWriter difference = new PrintWriter(fw2)){
*/


			// Drop normalized schema 
			NormalizedSchemaCreator.dropNormalizedDatabaseChema(database.getConnection());
			System.out.println("Dropping database schema finished\n");
			
			// Create normalized schema 
			NormalizedSchemaCreator.createNormalizedDatabaseSchema(database.getConnection());
			System.out.println("Database schema creation finished\n");
			
			NormalizedSchemaLoader.loadData(database.getConnection());
			System.out.println("Loading data finished\n");

			// Raise foreign keys
			NormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			System.out.println("Foreign keys rising finished\n");

//			// Raise indexes - not necessary
//			NormalizedSchemaCreator.raiseIndexes(database.getConnection());
//			System.out.println("Loading data ... finished");

			long coldStartTime = System.nanoTime() - applicationTime;
			System.out.println("Cold start finished after " + (coldStartTime / 1e9) + " seconds");

			database.startTransactionMixture(transactionMixFile);
			
			applicationTime = System.nanoTime() - applicationTime;
			System.out.println("Application finished after: " + (applicationTime / 1e9) + " seconds");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransactionError e) {
			e.printStackTrace();
		} finally {
			database.disconnectFromMySQL();
		}

		
	}

	public static void tpcEFullyDenormalized(String transactionMixFile, String outputResultFile) {
		
		long applicationTime = System.nanoTime();
		
		Main database = new Main("FullDT");		
		
		try {
/*		try (FileWriter fw1 = new FileWriter(pathToResultFolderNormalized + outputResultFile +"_timestamp.txt");
				PrintWriter timestamp = new PrintWriter(fw1);
					FileWriter fw2 = new FileWriter(pathToResultFolderNormalized + outputResultFile + "_difference.txt");
					PrintWriter difference = new PrintWriter(fw2)){
*/


			// Drop normalized schema 
			NormalizedSchemaCreator.dropNormalizedDatabaseChema(database.getConnection());
			System.out.println("Dropping database schema finished\n");
			
			// Create normalized schema 
			NormalizedSchemaCreator.createNormalizedDatabaseSchema(database.getConnection());
			System.out.println("Database schema creation finished\n");
			
			NormalizedSchemaLoader.loadData(database.getConnection());
			System.out.println("Loading data finished\n");

			// Raise foreign keys
			NormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			System.out.println("Foreign keys rising finished\n");

//			// Raise indexes - not necessary
//			NormalizedSchemaCreator.raiseIndexes(database.getConnection());
//			System.out.println("Loading data ... finished");

			long coldStartTime = System.nanoTime() - applicationTime;
			System.out.println("Cold start finished after " + (coldStartTime / 1e9) + " seconds");

			database.startTransactionMixture(transactionMixFile);
			
			applicationTime = System.nanoTime() - applicationTime;
			System.out.println("Application finished after: " + (applicationTime / 1e9) + " seconds");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransactionError e) {
			e.printStackTrace();
		} finally {
			database.disconnectFromMySQL();
		}

		
	}
	
	
	
	public static void main(String[] args) {
		
		Main.inputDataFile = Main.transactionMixFilesList.get(0);
		Main.outputResultFile = Main.outputResultFileList.get(0);
		
//		tpcENormalized(Main.inputDataFile, Main.outputResultFile);
		tpcEFullyDenormalized(Main.inputDataFile, Main.outputResultFile);
	

	}  
	
}
