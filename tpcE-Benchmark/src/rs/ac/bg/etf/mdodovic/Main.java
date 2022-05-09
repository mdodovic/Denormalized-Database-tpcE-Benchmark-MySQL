package rs.ac.bg.etf.mdodovic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.etf.mdodovic.schema.create.NormalizedChemaCreator;

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
		
			String connectionString = "jdbc:mysql://localhost:3306/tpce_mysql";
			
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
		
		try (FileWriter fw1 = new FileWriter(pathToResultFolderNormalized + outputResultFile +"_timestamp.txt");
				PrintWriter timestamp = new PrintWriter(fw1);
					FileWriter fw2 = new FileWriter(pathToResultFolderNormalized + outputResultFile + "_difference.txt");
					PrintWriter difference = new PrintWriter(fw2)){

			
			// Create normalized schema (first drop whole schema, then create full schema)
			NormalizedChemaCreator.createNormalizedDatabaseChema(database.getConnection());
			System.out.println("Database schema creation ... finished");

/*			
			Statement stmt = database.getConnection().createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM tpce_mysql.tabela1");  
			while(rs.next())  
				System.out.println(rs.getInt(1)+"  "+rs.getString(2) + "\n");  
*/			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			database.disconnectFromMySQL();
		}

		
	}

	
	public static void main(String[] args) {
		
		tpcENormalized("", "");
			

	}  
	
}
