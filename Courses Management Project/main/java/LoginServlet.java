

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.security.NoSuchAlgorithmException;  
import java.security.MessageDigest; 

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
//    private String logEmail;
    
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
		String email=request.getParameter("email");
		String password=request.getParameter("Password");
		
		Security s = new Security();
		password = s.hashPassword(password);
		UserDatabase db =  new UserDatabase(Connector.getConnection());
		User user = new User(email,password);
		
		if(validInputs(email,password,user,db,response)) {  //nqs te dhenat vihen sakt logohemi

			try {
				
				user = db.login(email, password);
				
				HttpSession session = request.getSession();
				session.setAttribute("login", user);
				response.sendRedirect("Home.html"); 
					
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}
	
	
	
	
	public boolean validInputs(String email, String password,User user,UserDatabase  db,HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
		String content = "<!DOCTYPE html><html><body><div style='outline : dashed 2px black ;'>";
		
		if(!db.emailRegistered(user)) {
			
			content += "<h1 style='color:red ; text-align:center;'>This user does not exist !</h1>";
			content += "</div></body></html>";
			out.println(content);

			return false;
		}else {  //nqs emaili vihet sakt
			
			if(!db.validPassword(email, password)) {
				content += "<h1 style='color:red ; text-align:center;'>Incorrect password !</h1>";
				content += "</div></body></html>";
				out.println(content);

				return false;
			}
		}
		
		return true;
	}
	
}

