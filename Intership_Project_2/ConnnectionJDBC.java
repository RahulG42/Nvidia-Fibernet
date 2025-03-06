package Intership_Project_2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnnectionJDBC {
	
	Connection c ;
	Statement s ;
	
	public ConnnectionJDBC() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/qspider" , "root" , "1857752002") ;
			
			s = c.createStatement() ;
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 public Connection getConnection() {
	        return c;
	    }
	
	

}