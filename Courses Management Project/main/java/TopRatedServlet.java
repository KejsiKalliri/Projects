

import java.io.FileWriter;
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

/**
 * Servlet implementation class TopRatedServlet
 */
@WebServlet("/topratedservlet")
public class TopRatedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopRatedServlet() {
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
		
		String query = "select * from courses order by AvrgRate desc";
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
	        	
	        	for(int i=1;i<=7;i++) {
	        		course.add(rs.getString(i));
	        	}
	        	
	        	cc.addTopCoursesContent(course,contents);
	        }
	        
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		for(int i=0; i<contents.size();i++) {
			out.println(contents.get(i));
		}
		
		out.close();

	}

}
