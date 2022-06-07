package rs.ac.bg.etf.mdodovic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import rs.ac.bg.etf.mdodovic.errors.TransactionError;
import rs.ac.bg.etf.mdodovic.schema.create.FullyDenormalizedSchemaCreator;
import rs.ac.bg.etf.mdodovic.schema.create.NormalizedSchemaCreator;
import rs.ac.bg.etf.mdodovic.schema.create.PartiallyDenormalizedSchemaCreator;
import rs.ac.bg.etf.mdodovic.schema.loaddata.FullyDenormalizedSchemaLoader;
import rs.ac.bg.etf.mdodovic.schema.loaddata.NormalizedSchemaLoader;
import rs.ac.bg.etf.mdodovic.schema.loaddata.PartiallyDenormalizedSchemaLoader;
import rs.ac.bg.etf.mdodovic.singleQueries.SingleQueries;
import rs.ac.bg.etf.mdodovic.transactions.TransactionMixtureExecutor;

public class Main {

	public static final String USER = "root";
	public static final String PASSWORD = "Kraljice.Natalije.37";

	private Connection connection;
	
	
	private TransactionMixtureExecutor transactionMixtureExecutor;


	public static String inputDataFile;
	public static String outputResultFile;
	
	public Main(String schemaModelName) {

		connectToMySQL();

		transactionMixtureExecutor = new TransactionMixtureExecutor(connection, schemaModelName);

	}
	
	public void startTransactionMixture(String transactionMixFile, PrintWriter timestampResultFile, PrintWriter differenceResultFile) throws IOException, TransactionError {
		transactionMixtureExecutor.startTransactionMixture(transactionMixFile, timestampResultFile, differenceResultFile);
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
				System.out.println("Disconect from MySQL");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void tpcENormalized(String transactionMixFile, String outputResultFile) {
		
		long applicationTime = System.nanoTime();
		
		Main database = new Main("NT");		
		
		try (FileWriter fw1 = new FileWriter(FilesManagement.pathToResultFolderNormalized + outputResultFile +"_timestamp.txt");
				PrintWriter timestampResultFile = new PrintWriter(fw1);
					FileWriter fw2 = new FileWriter(FilesManagement.pathToResultFolderNormalized + outputResultFile + "_difference.txt");
					PrintWriter differenceResultFile = new PrintWriter(fw2)){

			// Drop normalized schema 
			NormalizedSchemaCreator.dropNormalizedDatabaseChema(database.getConnection());
			// Drop fully denormalized schema 
			FullyDenormalizedSchemaCreator.dropFullyDenormalizedDatabaseChema(database.getConnection());
			// Drop partially denormalized schema 
			PartiallyDenormalizedSchemaCreator.dropPartiallyDenormalizedDatabaseChema(database.getConnection());
			System.out.println("Dropping database schema finished\n");
			
			// Create normalized schema 
			NormalizedSchemaCreator.createNormalizedDatabaseSchema(database.getConnection());
			System.out.println("Database schema creation finished\n");
			
			// Load data to normalized schema
			NormalizedSchemaLoader.loadData(database.getConnection());
			System.out.println("Loading data finished\n");

			// Raise foreign keys
			NormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			System.out.println("Foreign keys raising finished\n");

			// Raise indexes [no indexes on NT]
			NormalizedSchemaCreator.raiseIndexes(database.getConnection());
			System.out.println("Indexes raising finished");

			long coldStartTime = System.nanoTime() - applicationTime;
			System.out.println("Cold start finished after " + (coldStartTime / 1e9) + " seconds");

			database.startTransactionMixture(transactionMixFile, timestampResultFile, differenceResultFile);
			
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
		
		try (FileWriter fw1 = new FileWriter(FilesManagement.pathToResultFolderFullyDenormalized + outputResultFile +"_timestamp.txt");
				PrintWriter timestampResultFile = new PrintWriter(fw1);
					FileWriter fw2 = new FileWriter(FilesManagement.pathToResultFolderFullyDenormalized + outputResultFile + "_difference.txt");
					PrintWriter differenceResultFile = new PrintWriter(fw2)){

			// Drop normalized schema 
			NormalizedSchemaCreator.dropNormalizedDatabaseChema(database.getConnection());
			// Drop fully denormalized schema 
			FullyDenormalizedSchemaCreator.dropFullyDenormalizedDatabaseChema(database.getConnection());
			// Drop partially denormalized schema 
			PartiallyDenormalizedSchemaCreator.dropPartiallyDenormalizedDatabaseChema(database.getConnection());
			System.out.println("Dropping database schema finished\n");
			
			// Create normalized schema 
			NormalizedSchemaCreator.createNormalizedDatabaseSchema(database.getConnection());
			// Create fully denormalized schema 
			FullyDenormalizedSchemaCreator.createFullyDenormalizedDatabaseSchema(database.getConnection());
			System.out.println("Database schema creation finished\n");
			
			// Load data to normalized schema
			NormalizedSchemaLoader.loadData(database.getConnection());
			// Load data to normalized schema
			FullyDenormalizedSchemaLoader.loadData(database.getConnection());
			System.out.println("Loading data finished\n");

			// Raise foreign keys on normalized schema
			NormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			// Raise foreign keys on fully denormalized schema [no foreign keys on FullyDT]
			FullyDenormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			System.out.println("Foreign keys raising finished\n");

			// Raise indexes
			FullyDenormalizedSchemaCreator.raiseIndexes(database.getConnection());
			System.out.println("Indexes raising finished");

			long coldStartTime = System.nanoTime() - applicationTime;
			System.out.println("Cold start finished after " + (coldStartTime / 1e9) + " seconds");

			database.startTransactionMixture(transactionMixFile, timestampResultFile, differenceResultFile);
			
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
	

	public static void tpcEPartiallyDenormalized(String transactionMixFile, String outputResultFile) {
		
		long applicationTime = System.nanoTime();
		
		Main database = new Main("PartialDT");		
		
		try (FileWriter fw1 = new FileWriter(FilesManagement.pathToResultFolderPartiallyDenormalized + outputResultFile +"_timestamp.txt");
				PrintWriter timestampResultFile = new PrintWriter(fw1);
					FileWriter fw2 = new FileWriter(FilesManagement.pathToResultFolderPartiallyDenormalized + outputResultFile + "_difference.txt");
					PrintWriter differenceResultFile = new PrintWriter(fw2)){

			// Drop normalized schema 
			NormalizedSchemaCreator.dropNormalizedDatabaseChema(database.getConnection());
			// Drop fully denormalized schema 
			FullyDenormalizedSchemaCreator.dropFullyDenormalizedDatabaseChema(database.getConnection());
			// Drop partially denormalized schema 
			PartiallyDenormalizedSchemaCreator.dropPartiallyDenormalizedDatabaseChema(database.getConnection());
			System.out.println("Dropping database schema finished\n");
			
			// Create normalized schema 
			NormalizedSchemaCreator.createNormalizedDatabaseSchema(database.getConnection());
			// Create fully denormalized schema 
			PartiallyDenormalizedSchemaCreator.createPartiallyDenormalizedDatabaseSchema(database.getConnection());
			System.out.println("Database schema creation finished\n");
			
			// Load data to normalized schema
			NormalizedSchemaLoader.loadData(database.getConnection());
			// Load data to normalized schema
			PartiallyDenormalizedSchemaLoader.loadData(database.getConnection());
			System.out.println("Loading data finished\n");

			// Raise foreign keys on normalized schema
			NormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			// Raise foreign keys on fully denormalized schema [no foreign keys on FullyDT]
			PartiallyDenormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			System.out.println("Foreign keys raising finished\n");

			// Raise indexes
			PartiallyDenormalizedSchemaCreator.raiseIndexes(database.getConnection());
			System.out.println("Indexes raising finished");

			long coldStartTime = System.nanoTime() - applicationTime;
			System.out.println("Cold start finished after " + (coldStartTime / 1e9) + " seconds");

			database.startTransactionMixture(transactionMixFile, timestampResultFile, differenceResultFile);
			
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
		

//		// Normalized schema
//		for(int i = 0; i < FilesManagement.transactionMixFilesList.size(); i++) {
//			
//			System.out.println("[Schema]: Normalized");
//			
//			Main.inputDataFile = FilesManagement.transactionMixFilesList.get(i);
//			Main.outputResultFile = FilesManagement.outputResultFileNameList.get(i);
//			
//			System.out.println("[File]: " + Main.inputDataFile.split("/")[Main.inputDataFile.split("/").length - 1]);
//						
//			tpcENormalized(Main.inputDataFile, Main.outputResultFile);
//			
//			System.out.println();
//			
//		}
//
//		// Fully denormalized schema
//		for(int i = 0; i < FilesManagement.transactionMixFilesList.size(); i++) {
//
//			System.out.println("[Schema]: Fully denormalized");
//
//			Main.inputDataFile = FilesManagement.transactionMixFilesList.get(i);
//			Main.outputResultFile = FilesManagement.outputResultFileNameList.get(i);
//
//			System.out.println("[File]: " + Main.inputDataFile.split("/")[Main.inputDataFile.split("/").length - 1]);
//			
//			tpcEFullyDenormalized(Main.inputDataFile, Main.outputResultFile);
//
//			System.out.println();
//			
//		}
//
//		// Partially denormalized schema
//		for(int i = 0; i < FilesManagement.transactionMixFilesList.size(); i++) {
//			
//			System.out.println("[Schema]: Partially denormalized");
//
//			Main.inputDataFile = FilesManagement.transactionMixFilesList.get(i);
//			Main.outputResultFile = FilesManagement.outputResultFileNameList.get(i);
//
//			System.out.println("[File]: " + Main.inputDataFile.split("/")[Main.inputDataFile.split("/").length - 1]);
//			
//			tpcEPartiallyDenormalized(Main.inputDataFile, Main.outputResultFile);
//
//			System.out.println();
//			
//		}
		
//		tpcECompleteSchema();
		executeSingleQueries();
	}

	public static void executeSingleQueries() {
		Main database;
		
		/* ... */
		database = new Main("");
		
		new SingleQueries().T2F1_NT_NoConditions_NoGroupBy(database.getConnection());
		new SingleQueries().T2F1_FullyDT_NoConditions_NoGroupBy(database.getConnection());
		new SingleQueries().T2F1_PartiallyDT_NoConditions_NoGroupBy(database.getConnection());
		
		database.disconnectFromMySQL();
		
		/* ... */
		database = new Main("");
		
		new SingleQueries().T2F1_FullyDT_NoConditions_NoGroupBy(database.getConnection());
		new SingleQueries().T2F1_NT_NoConditions_NoGroupBy(database.getConnection());
		new SingleQueries().T2F1_PartiallyDT_NoConditions_NoGroupBy(database.getConnection());
		
		database.disconnectFromMySQL();

		/* ... */ 
		database = new Main("");		
		new SingleQueries().T2F1_PartiallyDT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();

		database = new Main("");		
		new SingleQueries().T2F1_NT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();

		database = new Main("");		
		new SingleQueries().T2F1_FullyDT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();


		/* ... */ 
		database = new Main("");		
		new SingleQueries().T2F1_NT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();

		database = new Main("");		
		new SingleQueries().T2F1_PartiallyDT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();

		database = new Main("");		
		new SingleQueries().T2F1_FullyDT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();

		
		/* ... */ 
		database = new Main("");		
		new SingleQueries().T2F1_FullyDT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();

		database = new Main("");		
		new SingleQueries().T2F1_NT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();

		database = new Main("");		
		new SingleQueries().T2F1_PartiallyDT_NoConditions_NoGroupBy(database.getConnection());		
		database.disconnectFromMySQL();

	}

	public static void tpcECompleteSchema() {

		long applicationTime = System.nanoTime();
		
		Main database = new Main("");		

		try {
			// Drop schema
			NormalizedSchemaCreator.dropNormalizedDatabaseChema(database.getConnection());
			FullyDenormalizedSchemaCreator.dropFullyDenormalizedDatabaseChema(database.getConnection());
			PartiallyDenormalizedSchemaCreator.dropPartiallyDenormalizedDatabaseChema(database.getConnection());
			System.out.println("Dropping database schema finished\n");
			
			// Create normalized schema 
			NormalizedSchemaCreator.createNormalizedDatabaseSchema(database.getConnection());
			FullyDenormalizedSchemaCreator.createFullyDenormalizedDatabaseSchema(database.getConnection());
			PartiallyDenormalizedSchemaCreator.createPartiallyDenormalizedDatabaseSchema(database.getConnection());
			System.out.println("Database schema creation finished\n");
			
			// Load data
			NormalizedSchemaLoader.loadData(database.getConnection());
			FullyDenormalizedSchemaLoader.loadData(database.getConnection());
			PartiallyDenormalizedSchemaLoader.loadData(database.getConnection());
			System.out.println("Loading data finished\n");
	
			// Raise foreign keys 
			NormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			FullyDenormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			PartiallyDenormalizedSchemaCreator.raiseForeignKeyConstraints(database.getConnection());
			System.out.println("Foreign keys raising finished\n");
	
			// Raise indexes
			FullyDenormalizedSchemaCreator.raiseIndexes(database.getConnection());
			PartiallyDenormalizedSchemaCreator.raiseIndexes(database.getConnection());
			System.out.println("Indexes raising finished");
						
			long coldStartTime = System.nanoTime() - applicationTime;
			System.out.println("Finished after " + (coldStartTime / 1e9) + " seconds");
						
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (TransactionError e) {
			e.printStackTrace();
		} finally {
			database.disconnectFromMySQL();
		}

		
	}  
	
}
