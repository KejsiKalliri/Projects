import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class Connector {

	 private static Connection con;
	    
	    public static Connection getConnection(){
	        try{
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/universitydb","root","kejsikalliri");
	            
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	            System.err.println("MySQL JDBC driver not found");
	            
	        } 
	        catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("Error connecting to the database");
	            
	        }
	        
	        return con;
	    }

}
