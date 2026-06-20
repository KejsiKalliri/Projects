import java.io.PrintWriter;
import java.time.*;
import java.util.Calendar;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CommentDatabase{
	Connection con ;
	
	public CommentDatabase(Connection con) {
		this.con=con;
	}
	
	public boolean saveComment(String subject,String comment,String email, int rate, Date date) {
		boolean set=false;
		
		try {
			
		  if (!commentExists(email, subject)) {
			
			String query="INSERT INTO feedback (Course, Comment ,Email ,Rate ,Date) VALUES (?, ? ,? ,? ,?) ";
			PreparedStatement pt = this.con.prepareStatement(query);
			pt.setString(1, subject);
			pt.setString(2, comment);
			pt.setString(3, email);
			pt.setDouble(4, rate);
			pt.setDate(5, date);
			pt.executeUpdate();  
			set=true;
		   }
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return set;
	}
	
	
	
	private boolean commentExists(String email, String courseName) {
	    try {
	        String query = "SELECT COUNT(*) FROM Feedback WHERE Email = ? AND Course = ?";
	        PreparedStatement pt = this.con.prepareStatement(query);
	        pt.setString(1, email);
	        pt.setString(2, courseName);

	        ResultSet rs = pt.executeQuery();
	        if (rs.next()) {	        	
	            int count = rs.getInt(1);
	            return count > 0;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	
	public double averageRate(String subject) {
		  int sum=0;
		  int counter=0;
		  double average=0;
		try {
			
			String query="SELECT Rate FROM Feedback WHERE Course = ?";  
			PreparedStatement pt = this.con.prepareStatement(query);
			pt.setString(1, subject);
			
			ResultSet rs = pt.executeQuery();
			
			while(rs.next()) {
				int rate=rs.getInt(1);
				sum+=rate;
				counter++;
			}
			
			average = sum/counter;
			saveAverage(subject,average);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return average;
	}
	
	private void saveAverage(String subject,double average) {
		
		try {
			
			String query = "update courses set AvrgRate = ? where Course = ?";
			PreparedStatement pt = this.con.prepareStatement(query);
			pt.setDouble(1, average);
			pt.setString(2, subject);

			pt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void removeOldFeedback() {
		
		try {
			LocalDate now = LocalDate.now();
			String DateNow = now.toString();
			String[] nowTokens = DateNow.split("-");
			int yearNow = Integer.parseInt(nowTokens[0]);
			int monthNow = Integer.parseInt(nowTokens[1]);
			
			System.out.println(yearNow + " " + monthNow);
			
			String query = "SELECT Date FROM Feedback";
			
			 PreparedStatement pt = this.con.prepareStatement(query);
			 
			 ResultSet rs = pt.executeQuery();
			 if (rs.next()) {
				 Date date=rs.getDate(1);
				 String StringDate=convertToString(date);
				 
				 String[] tokens=StringDate.split("/");
			     
				 int year = Integer.parseInt(tokens[0]);
				 int month = Integer.parseInt(tokens[1]);
				 
				 
				 int yearDiff = yearNow - year;
				 int monthDiff = monthNow - month;
				 
				 if(yearDiff != 0 && monthDiff > 0) {
					removeFeedback(date);
				 }
		        }
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void removeFeedback(Date date) {
		
		try {
		String DeleteQuery = "DELETE FROM Feedback WHERE Date = ?";
		PreparedStatement pt = this.con.prepareStatement(DeleteQuery);
		
		   // Set the date parameter using setDate
        pt.setDate(1, new java.sql.Date(date.getTime()));
        
          // Execute the delete query
        pt.executeUpdate();
        
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String convertToString(Date date) {
		String pattern = "yyyy/MM/dd";

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
      
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String todayAsString = df.format(date);
		
		return todayAsString;
	}
	

}