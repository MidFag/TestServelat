package com.midfag;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String s="<table border=0>";
		
		for (int j=0; j<20; j++)
		{
			s+="<tr>";
			for (int i=0; i<20; i++)
			{
				String color="";
				
				for (int c=0; c<3; c++)
				{
					color+=(int)(Math.random()*80)+19;
				}
				s+="<td width=20 height=15 bgcolor=#"+color+"></td>";
			}
			s+="</tr>";
		}
		
		s+="</table>";
		response.getWriter().append(s+"");
	}

}


