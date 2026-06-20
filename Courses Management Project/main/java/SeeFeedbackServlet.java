

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
 * Servlet implementation class SeeFeedbackServlet
 */
@WebServlet("/seefeedbackservlet")
public class SeeFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeeFeedbackServlet() {
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
		String courseName = (String)session.getAttribute("feedbackCourse");
		
		String query = "select * from feedback where Course = '" + courseName +"'";
		
		ArrayList<String> contents = new ArrayList<String>();  
		ArrayList<String> feedback ;
		
		PreparedStatement pt;
		try {
			
			pt = con.prepareStatement(query);
			pt.execute(query);
	        ResultSet rs = pt.getResultSet();
	        
	        while(rs.next()) {
               feedback = new ArrayList<String>();
	        	
	        	for(int i=1;i<=5;i++) { 
	        		feedback.add(rs.getString(i));
	        	}
	        	
	        	addCourseContent(feedback,contents);
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
	
   public void addCourseContent(ArrayList<String> course,ArrayList<String> contents) throws IOException {
		
		String content = "<div class='course'><h3>"+course.get(2)+"</h3><p>Date: "+course.get(4)+"</p><p>Comment: "+course.get(1)+"</p><p>Rate: "+course.get(3)+"</p></div>";

		contents.add(content);
		
	}

}
