import java.beans.Statement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.Document;
import javax.swing.text.html.HTML;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Servlet implementation class RegisterCourse
 */
@WebServlet("/RegisterCourseServlet")
public class RegisterCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

			
		}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
//		String courseName = request.getParameter("name");
		
		courseDatabase cd=new courseDatabase(Connector.getConnection());
		
		String seeFeedback = request.getParameter("seeFeedback");
		
		if(seeFeedback != null ) {    //nqs eshte klikuar
			String feedbackOfCourse = cd.findCourseName(seeFeedback);
//			transferName(response,feedbackOfCourse);
			HttpSession session = request.getSession();
			session.setAttribute("feedbackCourse", seeFeedback);
			response.sendRedirect("seeFeedback.html");
			return;
		}
		
				
		String button = request.getParameter("button");
		String courseName = cd.findCourseName(button);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("login");
		
		String email=null;
	    email = user.getEmail();
        
		cd.registerCourse(courseName, email);
		
		try {
			incrementNrOfStudents(courseName);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    response.sendRedirect("courses.html");



	}

	private void incrementNrOfStudents(String courseName) throws SQLException {
		String query1 = "select * from courses where Course = '" + courseName + "'";
		
		Connection con = Connector.getConnection();
		PreparedStatement pt = con.prepareStatement(query1);
		pt.execute(query1);
        ResultSet rs = pt.getResultSet();
        
        int nrOfStudents=0;
        
        if(rs.next()) {
        	nrOfStudents = rs.getInt(6);
        }
       
        nrOfStudents+=1;
        
        String query2 = "update courses set NrOfStudents=? where Course =?";
        pt = con.prepareStatement(query2);
        pt.setInt(1, nrOfStudents);
        pt.setString(2, courseName);
        
        pt.executeUpdate();

	}

}
