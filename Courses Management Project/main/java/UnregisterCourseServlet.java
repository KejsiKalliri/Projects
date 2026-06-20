import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UnregisterCourseServlet
 */
@WebServlet("/UnregisterCourseServlet")
public class UnregisterCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnregisterCourseServlet() {
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
		courseDatabase cd=new courseDatabase(Connector.getConnection());
		
		String button = request.getParameter("button");
		String courseName = cd.findCourseName(button);
		

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("login");
		
		String email = user.getEmail();

		
		cd.unregisterCourse(courseName, email);
		
		try {
			
			decrementNrOfStudents(courseName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("myCourses.html");
		
	}
	
	
      private  void decrementNrOfStudents(String courseName) throws SQLException {
  		String query1 = "select * from courses where Course = '" + courseName + "'";
		
  		Connection con = Connector.getConnection();
  		PreparedStatement pt = con.prepareStatement(query1);
  		pt.execute(query1);
          ResultSet rs = pt.getResultSet();
          
          int nrOfStudents=0;
          
          if(rs.next()) {
          	nrOfStudents = rs.getInt(6);
          }
         
          nrOfStudents-=1;
          
          String query2 = "update courses set NrOfStudents=? where Course =?";
          pt = con.prepareStatement(query2);
          pt.setInt(1, nrOfStudents);
          pt.setString(2, courseName);
          
          pt.executeUpdate();
	}

}
