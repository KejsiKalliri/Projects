import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

public class CoursesContent {
	
	public void addAllCoursesContent(HttpServletResponse response, ArrayList<String> course,ArrayList<String> contents,String email,Connection con) throws IOException, SQLException {
		String courseName = course.get(0);
		String content = "<div class='course'><h2>"+courseName+"</h2><p>Description: "+course.get(1)+"</p><p>Lecturer: "+course.get(2)+"</p><p>Time: "+course.get(3)+"</p><p>Hall: "+course.get(4)+"</p><p>Registered Students: "+course.get(5)+"</p><p>Average Rate: "+course.get(6)+"</p><button class='button' name='seeFeedback' value='"+course.get(0)+"'>See Feedback</button>";

		if(!isEnrolled(course.get(0),email,con)){
			
			content += "<button class='button' name='button' value='"+courseName+"'>Enroll</button>";
		}
		
		content += "</div>";
		
		
		contents.add(content);
		
	}
	
	
	public boolean isEnrolled(String courseName , String email ,Connection con) throws SQLException {
		String query = "select * from coursesandstudents where EmailOfStudent = '" + email +"' and Course = '" + courseName + "'" ;
		PreparedStatement pt = con.prepareStatement(query);

        pt.execute(query);
        ResultSet rs = pt.getResultSet();
        if(rs.next()) return true;
        
        return false;
	}
	
	
	public void addCourseDetails(String courseName, ArrayList course, Connection con) throws SQLException {
		String query = "select * from courses where Course = '" + courseName + "'";
		PreparedStatement pt = con.prepareStatement(query);

        pt.execute(query);
        ResultSet rs = pt.getResultSet();
        rs.next();

        for(int i=2; i<=7 ;i++) {  //1-emri kursit (e kemi mar)  
        	course.add(rs.getString(i));  
        }
	}
	
	
	public void addOngoingCoursesContent(HttpServletResponse response, ArrayList<String> course,ArrayList<String> contents) throws IOException {
		
		String content = "<div class='course'><h2>"+course.get(0)+"</h2><p>Description: "+course.get(1)+"</p><p>Lecturer: "+course.get(2)+"</p><p>Time: "+course.get(3)+"</p><p>Hall: "+course.get(4)+"</p><p>Registered Students: "+course.get(5)+"</p><p>Average Rate: "+course.get(6)+"</p><button class='button' name='button' value='"+course.get(0)+"'>Drop Out</button></div>";

		contents.add(content);
		
	}
	
	
	public void addFinishedCoursesContent(HttpServletResponse response, ArrayList<String> course,ArrayList<String> contents) throws IOException {
	
	String content = "<div class='course'><h2>"+course.get(0)+"</h2><p>Description: "+course.get(1)+"</p><p>Lecturer: "+course.get(2)+"</p><p>Time: "+course.get(3)+"</p><p>Hall: "+course.get(4)+"</p><p>Registered Students: "+course.get(5)+"</p><p>Average Rate: "+course.get(6)+"</p><button class='button' name='button' value='"+course.get(0)+"'>Leave Feedback</button></div>";

	contents.add(content);
	
}
	
	
	public void addTopCoursesContent(ArrayList<String> course,ArrayList<String> contents) throws IOException {
		
		String content = "<div class='course'><h2>"+course.get(0)+"</h2><p>Description: "+course.get(1)+"</p><p>Lecturer: "+course.get(2)+"</p><p>Time: "+course.get(3)+"</p><p>Hall: "+course.get(4)+"</p><p>Registered Students: "+course.get(5)+"</p><p>Average Rate: "+course.get(6)+"</p></div>";

		contents.add(content);
		
	}
	
	
}
