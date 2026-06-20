

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AfishoProv
 */
@WebServlet("/ongoingcourses")
public class OngoingCoursesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OngoingCoursesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
	     Connection con = Connector.getConnection();
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("login");

	    String email = user.getEmail();
		
		String query = "select Course from coursesandstudents where EmailOfStudent='"+email+"' and isFinished = '0'" ;
		PreparedStatement pt;
		ArrayList<String> contents = new ArrayList<String>();
		ArrayList<String> course ;
		
		try {
			
			pt = con.prepareStatement(query);
			pt.execute(query);
	        ResultSet rs = pt.getResultSet();
	        
	        CoursesContent cc = new CoursesContent();
	        String courseName;
	        while(rs.next()) {

	        	course = new ArrayList<String>();
	        	courseName = rs.getString(1);
	        	course.add(courseName);
	        	
	        	cc.addCourseDetails(courseName,course,con);
	        	cc.addOngoingCoursesContent(response,course,contents);
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		for(int i=0; i<contents.size();i++) {
			out.println(contents.get(i));
		}
		
		out.close();
	}
	
}
