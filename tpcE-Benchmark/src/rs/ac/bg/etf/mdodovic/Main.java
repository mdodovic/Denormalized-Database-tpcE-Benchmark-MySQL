package rs.ac.bg.etf.mdodovic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.etf.mdodovic.schema.create.NormalizedSchemaCreator;
import rs.ac.bg.etf.mdodovic.schema.loaddata.NormalizedChemaLoader;

public class Main {

	public static final String USER = "root";
	public static final String PASSWORD = "Kraljice.Natalije.37";

	private Connection connection;

	public static final String pathToResultFolderNormalized = "./src/time_results/normalized/";
	
	public static List<String> transactionMixFilesList = new ArrayList<String>();
	public static List<String> outputResultFileList = new ArrayList<String>();
	
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
	
	public static void tpcENormalized(String inputDataFile, String outputResultFile) {
		
		long start = System.nanoTime();

		Main database = new Main();		
		database.connectToMySQL();
		
		try {
/*		try (FileWriter fw1 = new FileWriter(pathToResultFolderNormalized + outputResultFile +"_timestamp.txt");
				PrintWriter timestamp = new PrintWriter(fw1);
					FileWriter fw2 = new FileWriter(pathToResultFolderNormalized + outputResultFile + "_difference.txt");
					PrintWriter difference = new PrintWriter(fw2)){
*/
			
			// Drop normalized schema 
			NormalizedSchemaCreator.dropNormalizedDatabaseChema(database.getConnection());
			System.out.println("Dropping database schema ... finished\n");
			
			// Create normalized schema 
			NormalizedSchemaCreator.createNormalizedDatabaseSchema(database.getConnection());
			System.out.println("Database schema creation ... finished\n");
			
//			NormalizedChemaLoader.loadData(database.getConnection());
//			System.out.println("Loading data ... finished");

			// Create foreign keys
//			NormalizedChemaLoader.loadData(database.getConnection());
//			System.out.println("Loading data ... finished");

			// Create indexes
//			NormalizedChemaLoader.loadData(database.getConnection());
//			System.out.println("Loading data ... finished");

			long coldStart = System.nanoTime() - start;
			System.out.println("Cold start ... finished after " + (coldStart / 1e9) + " seconds");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			database.disconnectFromMySQL();
		}

		
	}

	
	public static void main(String[] args) {
		
		tpcENormalized("", "");
			

	}  
	
}
