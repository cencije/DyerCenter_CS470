package com.dyerspot.servlet;

import java.io.IOException; 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dyerspot.ejb.MailSenderBean;

import java.io.PrintWriter;


/**
 * Servlet implementation class MailDispatcherServlet
 */
@WebServlet(name="MailDispatcherServlet", urlPatterns="/MailDispatcherServlet")

public class MailDispatcherServlet extends HttpServlet {
	
	private MailSenderBean mailSender;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String toEmail = request.getParameter("email");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		
		// properties should be read from a database, external file, or server properties
		
		String fromEmail = "dummyDyer@gmail.com";
		String userName = "dummyDyer";
		String password = "dummypassword";
	
		try(PrintWriter out = response.getWriter()) {
			
			// call to mail sender
			mailSender.sendEmail(fromEmail, userName, password, toEmail, subject, message);
			
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Mail Status</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Mail Status</h1>");
			out.println("<b>Mail Sent Successfully</b>");
			out.println("Click <a href='emailEmployee.jsp'>here</a> to go back");
			out.println("</body>");
			out.println("</html>");
		}
	
	
	};
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailDispatcherServlet() {
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
		doGet(request, response);
	}

}
