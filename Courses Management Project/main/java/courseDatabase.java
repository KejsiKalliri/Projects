
import java.beans.Statement;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;


public class courseDatabase extends LoginServlet{
	
	Connection con;
	
	public courseDatabase(Connection con) {
		this.con=con;
	}
	
	public boolean registerCourse(String courseName,String email) {
		 boolean set = false;
	        try{
	            
	            String query = "insert into coursesandstudents(Course,EmailOfStudent,IsFinished) values(?,?,?)";

	           PreparedStatement pt = this.con.prepareStatement(query);
	           pt.setString(1, courseName);
	           pt.setString(2, email);
	           pt.setBoolean(3, false);
	

	           pt.executeUpdate();
	           set = true;

	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return set;
	}
	
	
	public boolean unregisterCourse(String courseName,String email) {
		boolean set = false;
        try{
          
        	String query = "delete from coursesandstudents where EmailOfStudent=? AND Course=?";
           
           PreparedStatement pt = this.con.prepareStatement(query);
           pt.setString(1, email);
           pt.setString(2, courseName);

           
           pt.executeUpdate();
           set = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return set;
	}
	
	
	public String findCourseName(String button) {
		String courseName = null;
		if(button.equals("Calculus 1")) courseName = "Calculus 1";
		else if(button.equals("Linear Algebra")) courseName = "Linear Algebra";
		else if(button.equals("Physics")) courseName = "Physics";
		else if(button.equals("C Programming")) courseName = "C Programming";
		else if(button.equals("Object Oriented Programming")) courseName = "Object Oriented Programming";
		else if(button.equals("Computer Organization")) courseName = "Computer Organization";
		else if(button.equals("Data Structure")) courseName = "Data Structure";
		else if(button.equals("Electrical & Electronic Circuits")) courseName = "Electrical & Electronic Circuits";
		else if(button.equals("Signals and Systems")) courseName = "Signals and Systems";
		else if(button.equals("Computer Networks")) courseName = "Computer Networks";
		else if(button.equals("Web Technologies and Programming")) courseName = "Web Technologies and Programming";
		else if(button.equals("Algorithms and Advanced Programming")) courseName = "Algorithms and Advanced Programming";
		else if(button.equals("Data Base")) courseName = "Data Base";
		else if(button.equals("Operating Systems")) courseName = "Operating Systems";
		
		return courseName;
	}	
	
}


