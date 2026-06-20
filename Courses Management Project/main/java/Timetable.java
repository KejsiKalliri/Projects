
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
 * Servlet implementation class Timetable
 */
@WebServlet("/timetable")
public class Timetable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Timetable() {
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

		ArrayList<String> mycourses = new ArrayList<String>();
		
		String content = "            <table>\r\n"
				+ "                <thead>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <th></th>\r\n"
				+ "                        <th>9:00 - 11:00</th>\r\n"
				+ "                        <th>11:00 - 13:00</th>\r\n"
				+ "                        <th>13:00 - 15:00</th>\r\n"
				+ "                        <th>15:00 - 17:00</th>\r\n"
				+ "                    </tr>\r\n"
				+ "                </thead>\r\n"
				+ "                <tbody>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td>Monday</td>\r\n"	;
		
		try {
			
			pt = con.prepareStatement(query);
			pt.execute(query);
	        ResultSet rs = pt.getResultSet();
	        
	        while(rs.next()) {

	        	mycourses.add(rs.getString(1));

	        }
	
	        
	        content += addContent(con,mycourses);
			
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		content += " </tr>\r\n"
				+ " </tbody>\r\n"
				+ " </table>";
		 
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 out.println(content);
		 out.close();
		 
	}
	
private String addContent(Connection con,ArrayList<String> mycourses) throws IOException, SQLException {
	String content="";
		
	String query = "select Course from courses";
	PreparedStatement pt;
	
	pt = con.prepareStatement(query);
	pt.execute(query);
    ResultSet rs = pt.getResultSet();
    
    
    int i=0;
    
	while(rs.next() && i<=2) {
		if(!mycourses.contains(rs.getString(1))) {
			content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";
		}else {
			content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
		}
		
		i++;
	}
	
	content+=" <td class=\"course-title\"></td>\r\n"
			+ "</tr>\r\n"
			+ "<tr>\r\n"
			+ "<td>Tuesday</td>\r\n";
	
	
	
	if(!mycourses.contains(rs.getString(1))) {   //sepse eshte ber nje rs.next() nga cikli para por meqe nuk eshte plotsuar kushti 2 nuk eshte futur brenda ciklti 
		content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";  //dhe nese kalojm direkt tek cikli tjeter while i bie qe kalojm nje kurs pa marre.
	}else {
		content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
	}
	
	
	
	i=0;
	while(rs.next() && i<=2) {
		if(!mycourses.contains(rs.getString(1))) {
			content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";
		}else {
			content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
		}
		
		i++;
	}
	
	content +=  "</tr>\r\n"
			+ "<tr>\r\n"
			+ "<td>Wednesday</td>\r\n"
			+ " <td class=\"course-title\"></td>\r\n"
			+ " <td class=\"course-title\"></td>\r\n";
	
	
	if(!mycourses.contains(rs.getString(1))) {
		content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";
	}else {
		content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
	}
	
//	i=0;
//	
//	while(rs.next() && i<1) {  //dmth do exe vtm 1 here pra mund ta heqim fare 
	
	rs.next();
		if(!mycourses.contains(rs.getString(1))) {
			content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";
		}else {
			content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
		}
		
//		i++;
//	}
	
	
	content +=  "</tr>\r\n"
			+ "<tr>\r\n"
			+ "<td>Thursday</td>\r\n"
			+ " <td class=\"course-title\"></td>\r\n";
	
	i=0;
	
	while(rs.next() && i<=1) {
		if(!mycourses.contains(rs.getString(1))) {
			content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";
		}else {
			content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
		}
		
		i++;
	}
	
	
	content+= " <td class=\"course-title\"></td>\r\n"
			+ "</tr>\r\n"
			+ "<tr>\r\n"
			+ "<td>Friday</td>\r\n";
	
	if(!mycourses.contains(rs.getString(1))) {
		content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";
	}else {
		content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
	}
	
//	i=0;
//	
//	while(rs.next() && i<=1) { //dmth do exe vtm 1 here pra mund ta heqim fare
	
	rs.next();
		if(!mycourses.contains(rs.getString(1))) {
			content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";
		}else {
			content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
		}
		
//		i++;
//	}
	
	content+= " <td class=\"course-title\"></td>\r\n";
	
	while(rs.next()) {
		if(!mycourses.contains(rs.getString(1))) {
			content += " <td class=\"course-title\">"+rs.getString(1)+"</td>\r\n";
		}else {
			content += " <td class=\"course-title\" style='background-color:yellow;'>"+rs.getString(1)+"</td>\r\n";
		}
		
		i++;
	}
	
    return content;
 }


}
