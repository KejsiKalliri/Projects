

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CommentsServlet
 */
@WebServlet("/CommentsServlet")
public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsServlet() {
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
		
		HttpSession session = request.getSession();
		
		String courseName = (String)session.getAttribute("course");
		String comment = request.getParameter("comment");
		
		String rateStar = request.getParameter("rate");
		int rate = Integer.parseInt(findRateValue(rateStar));
		
	    java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); 
		
		User user = (User) session.getAttribute("login");
		
		CommentDatabase cd = new CommentDatabase(Connector.getConnection());

		
		
		PrintWriter out=response.getWriter();
		String content = "<!DOCTYPE html><html><body><div style='outline : dashed 2px black ;'>";
		
			
		if(!cd.saveComment(courseName, comment ,user.getEmail(), rate, currentDate)) {  
				  
			content += "<h1 style='color:red ; text-align:center;'>You have already submit a feedback for this course !</h1>";
			content += "</div></body></html>";
			out.println(content);
			return;
		          
			}else {
			   cd.averageRate(courseName);				
			   response.sendRedirect("myCourses.html");   
			}
		
	}
	
	public String findRateValue(String rateStar) {
		if(rateStar == null) return "0";
		
		String rate = null;
		
		if(rateStar.equals("1")) rate="1";
		else if(rateStar.equals("2")) rate="2";
		else if(rateStar.equals("3")) rate="3";
		else if(rateStar.equals("4")) rate="4";
		else if(rateStar.equals("5")) rate="5";
		
		return rate;
	}
	

}
