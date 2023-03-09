package com.uniquedeveloper.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegistrationServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//read the form data
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String umobile=request.getParameter("contact");
		
		//create printWriter object
		PrintWriter pw=response.getWriter();
		pw.println("uname:"+uname.toString());
		pw.println("uemail:"+uemail.toString());
		pw.println("upwd:"+upwd.toString());
		pw.println("umobile:"+umobile.toString());
		
		RequestDispatcher dis=null;
		Connection con=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("Jdbc:mysql:///userinfo","root","123456789");
			PreparedStatement ps=con.prepareStatement("insert into users(uname,uemail,upwd,umobile)values(?,?,?,?)");
			ps.setString(1, uname);
			ps.setString(2, uemail);
			ps.setString(3, upwd);
			ps.setString(4, umobile);
			
			int rowCount=ps.executeUpdate();
			dis=request.getRequestDispatcher("registration.jsp");
			if(rowCount>0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "false");
			}
			dis.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
