package rs.ac.bg.etf.mdodovic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static final String USER = "root";
	public static final String PASSWORD = "Kraljice.Natalije.37";

	private Connection connection;

	
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
		
		try {

			Statement stmt = database.getConnection().createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM tpce_mysql.tabela1");  
			while(rs.next())  
				System.out.println(rs.getInt(1)+"  "+rs.getString(2) + "\n");  
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.disconnectFromMySQL();
		}

		
	}

	
	public static void main(String[] args) {
		
		tpcENormalized("", "");
			

	}  
	
}
