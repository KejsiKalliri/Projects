import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginServletTest{
	
	@Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    private LoginServlet servlet;
    private UserDatabase userService;

    public LoginServletTest() throws ServletException {
    	setUp();
    }
    
   @Before
   public void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        servlet = new LoginServlet();
        userService = mock(UserDatabase.class);

        servlet.init();
     }
    
   
   @Test
   public void testEmailIsNotInDB() throws ServletException, IOException {
	    when(request.getParameter("email")).thenReturn("kejsii.kalliri@fti.edu.al");
	    when(request.getParameter("password")).thenReturn("12345"); 
 	    servlet.doPost(request, response);
 	 
 	   verify(response, times(0)).sendRedirect("Home.html"); 
  }
   
   
   @Test
 public void testCorrectPassword() throws ServletException, IOException {
   when(request.getParameter("email")).thenReturn("kejsi.kalliri@fti.edu.al");
   when(request.getParameter("password")).thenReturn("12345");

   servlet.doPost(request, response);
 
   verify(response, times(1)).sendRedirect("Home.html"); 
}
   
   
   @Test
 public void testIncorrectPassword() throws ServletException, IOException {
   when(request.getParameter("email")).thenReturn("kejsi.kalliri@fti.edu.al");
   when(request.getParameter("password")).thenReturn("password");

   servlet.doPost(request, response);
 
   verify(response, times(0)).sendRedirect("Home.html"); 
}
   

}
