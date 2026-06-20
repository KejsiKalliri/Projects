import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Before;
//import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RegisterServletTest {
	
	  @Mock
	    private HttpServletRequest request;

	    @Mock
	    private HttpServletResponse response;

	    @Mock
	    private HttpSession session;

	    @Mock
	    private RequestDispatcher dispatcher;

	    private RegisterServlet servlet;
	    private UserDatabase userService;
	
	    public RegisterServletTest() throws ServletException {
	    	setUp();
	    }
	    
	@Before
	 public void setUp() throws ServletException {
	        MockitoAnnotations.openMocks(this);
	        servlet = new RegisterServlet();
	        userService = mock(UserDatabase.class);

	        servlet.init();
	    }
	    
	    @Test
	    public void testNameNumber() throws ServletException, IOException {
	    	
	    	when(request.getParameter("name")).thenReturn("Klea1");
	    	when(request.getParameter("surname")).thenReturn("Kalliri");
		    when(request.getParameter("email")).thenReturn("klea1.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(0)).sendRedirect("login.html");  // 1 ndshta duhet 0
	    	 // times(1) dmth nese eshte thirrur njeher sendRedirect(login.html)
	    	 //kontrrollon nqs shkojm te login.html sepse nqs shkojm do te thot logini i suksesshem dhe nese nuk shkojm do te thot logini jo i sukseshem 
	    }
	    
	    @Test
	    public void testNameChar() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Klea@");
	    	when(request.getParameter("surname")).thenReturn("Kalliri");
		    when(request.getParameter("email")).thenReturn("klea1.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(0)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testCorrectName() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Kleaa");
	    	when(request.getParameter("surname")).thenReturn("Kalliri");
		    when(request.getParameter("email")).thenReturn("klea1.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(1)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testSurnameNumber() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Kleaa");
	    	when(request.getParameter("surname")).thenReturn("Kalliri1");
		    when(request.getParameter("email")).thenReturn("kleaa.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(0)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testSurnameChar() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Klea");
	    	when(request.getParameter("surname")).thenReturn("Kalliri@");
		    when(request.getParameter("email")).thenReturn("kleaa.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(0)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testCorrectSurname() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Kleea");
	    	when(request.getParameter("surname")).thenReturn("Kalliri");
		    when(request.getParameter("email")).thenReturn("kleaa.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(1)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testNameSurnameIsInDB() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Kejsi");
	    	when(request.getParameter("surname")).thenReturn("Kalliri");
		    when(request.getParameter("email")).thenReturn("kejsi1.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(0)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testNameSurnameIsNotInDB() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Kllea");
	    	when(request.getParameter("surname")).thenReturn("Kallirii");
		    when(request.getParameter("email")).thenReturn("klea2.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(1)).sendRedirect("login.html"); 
	    }

	    @Test
	    public void testEmailInDB() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Kleeaa");
	    	when(request.getParameter("surname")).thenReturn("Kalliri");
		    when(request.getParameter("email")).thenReturn("kejsi.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(0)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testEmailNotInDB() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Kleak");
	    	when(request.getParameter("surname")).thenReturn("Kallirik");
		    when(request.getParameter("email")).thenReturn("klea3.kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(1)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testEqualPass() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Kleaak");
	    	when(request.getParameter("surname")).thenReturn("Kalliri");
		    when(request.getParameter("email")).thenReturn("Klea4.Kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("password");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(1)).sendRedirect("login.html"); 
	    }
	    
	    @Test
	    public void testDifferentPass() throws ServletException, IOException {
	    	when(request.getParameter("name")).thenReturn("Klara");
	    	when(request.getParameter("surname")).thenReturn("Kalliri");
		    when(request.getParameter("email")).thenReturn("Klea4.Kalliri@fti.edu.al");
		    when(request.getParameter("password")).thenReturn("password");
		    when(request.getParameter("repeatPassword")).thenReturn("passwor");
	    	 servlet.doPost(request, response);
	    	 
	    	 verify(response, times(0)).sendRedirect("login.html"); 
	    }
	    
	}
