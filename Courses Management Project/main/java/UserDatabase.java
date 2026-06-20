import java.sql.*;
import java.sql.Connection;

public class UserDatabase {
	Connection con ;

    public UserDatabase(Connection con) {
        this.con = con;
    }
    
    
    public boolean saveUser(User user){
        boolean set = false;
        try{
          
            String query = "INSERT INTO students (firstname,lastname,email, password) VALUES (?, ?, ?, ?)";  
           
           PreparedStatement pt = this.con.prepareStatement(query);    

           pt.setString(1, user.getFirstName());
           pt.setString(2, user.getLastName());
           pt.setString(3, user.getEmail());   
           pt.setString(4, user.getPassword());  
           
           pt.executeUpdate();
           set = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return set;
    }
    
    
    public boolean nameSurnameRegistered(User user) {
    	
    	String name = user.firstname;
    	String surname = user.lastname;

    	String query = "SELECT * FROM students WHERE FirstName = ? AND LastName = ?";
    	try {
    		PreparedStatement pt = this.con.prepareStatement(query);
    	    pt.setString(1, name);
    	    pt.setString(2, surname);

    	    ResultSet resultSet = pt.executeQuery();
    	        if (resultSet.next()) {
    	            return true;   //gjendet
    	        }
    	    }
    	 catch (SQLException e) {
    	    e.printStackTrace(); 
    	}
    	return false; //nuk gjendet
    }
    
    
    public boolean emailRegistered(User user) {
    	String email = user.email;

    	String query = "SELECT * FROM students WHERE Email = ?";
    	try {
    		PreparedStatement pt = this.con.prepareStatement(query);
    	    pt.setString(1, email);

    	    ResultSet resultSet = pt.executeQuery();
    	        if (resultSet.next()) {
    	            return true;   //gjendet
    	        }
    	    }
    	 catch (SQLException e) {
    	    e.printStackTrace(); 
    	}
    	return false; //nuk gjendet
    }


public User login(String email, String password){
    User usr=null;

    try{
        String query ="SELECT * FROM students WHERE Email = ? AND Password = ?";
        

        
        PreparedStatement pst = this.con.prepareStatement(query);
        pst.setString(1, email);
        pst.setString(2, password);
        
        ResultSet rs = pst.executeQuery(); 
        
        if(rs.next()){ 
       	
            usr = new User();
            usr.setFirstName(rs.getString("FirstName"));
            usr.setLastName(rs.getString("LastName"));
            usr.setEmail(rs.getString("Email"));
            usr.setPassword(rs.getString("Password"));
            
        }
        
        
    }catch(Exception e){
        e.printStackTrace();
    }
    return usr;
  }


    public boolean validPassword(String email, String password) { 
    	
    	String query = "select Email,Password from students where Password=? and Email=?";  
    	
    	try {
    		PreparedStatement pt = this.con.prepareStatement(query);
    	    pt.setString(1, password);
    	    pt.setString(2, email);


    	    ResultSet resultSet = pt.executeQuery();
    	        if (resultSet.next()) {
    	            return true;   
    	        }
    	    }
    	 catch (SQLException e) {
    	    e.printStackTrace(); 
    	}
    	return false; 
    }

}



