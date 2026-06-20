

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		

		String name = request.getParameter("firstname");
		String surname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Security s = new Security();
		password = s.hashPassword(password);
		String repeatPassword = request.getParameter("repeatPassword");
		repeatPassword = s.hashPassword(repeatPassword);
		
		User user = new User(name, surname, email, password);

		UserDatabase regUser = new UserDatabase(Connector.getConnection());
		
		
		if(validInputs(name,surname,email,password,repeatPassword,user,regUser,response)){  

			regUser.saveUser(user);
			response.sendRedirect("login.html"); 

		}
	}
	
	
	private boolean validInputs(String name, String surname, String email, String password, String repeatPassword, User user, UserDatabase regUser,HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		boolean correctInputs = true;
		String content = "<!DOCTYPE html><html><body><div style='outline : dashed 2px black ;'>";
		
		if(containDigit(name) || containSpecialChar(name)) {
			content += "<h1 style='color:red ; text-align:center;'>Name should not contain numbers or special characters !</h1>";
			correctInputs = false;
		}
		if(containDigit(surname) || containSpecialChar(surname)) {
			content += "<h1 style='color:red ; text-align:center;'>Surname should not contain numbers or special characters !</h1>";
			correctInputs = false;
		}
		if(regUser.nameSurnameRegistered(user)) {
			content += "<h1 style='color:red ; text-align:center;'>The Name and Surname given, are used !</h1>";
			correctInputs = false;
		}
		if(regUser.emailRegistered(user)) {
			content+="<h1 style='color:red ; text-align:center;'>This Email exist !</h1>";
			correctInputs = false;
		}
		if(!password.equals(repeatPassword)){
			content+="<h1 style='color:red ; text-align:center;'>RepeatPassword and Password are not the same !</h1>";
			correctInputs = false;
		}
		
		content += "</div></body></html>";
		out.println(content);
		
		return correctInputs;
	}
	
	private boolean containDigit(String str) {
		for (char c : str.toCharArray()) {
		      if (Character.isDigit(c)) {
		         return true;
		      }
		   }
		return false;
	}
	
	private boolean containSpecialChar(String str) {
		
		Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
		
		 return special.matcher(str).find();
	}

}
