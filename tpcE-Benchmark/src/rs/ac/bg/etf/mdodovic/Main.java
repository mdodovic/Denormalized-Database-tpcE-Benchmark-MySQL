package rs.ac.bg.etf.mdodovic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		
		try{  

			Class.forName("com.mysql.cj.jdbc.Driver");  
			
			Connection con = DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/tpce_mysql","root","Kraljice.Natalije.37");  
			//here sonoo is database name, root is username and password  
			
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM tpce_mysql.tabela1");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2) + "\n");  
			
			con.close();  
	
		} catch(Exception e) { 
			System.out.println(e);
		}  

	}  
	
}
