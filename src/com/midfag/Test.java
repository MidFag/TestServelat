package com.midfag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Test")
public class Test extends HttpServlet {
	private static float[][] matrix=new float[80][40];
	private static String[] indicate={"◇","◆","◆"};
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		
		String s="<font size=1>";
		
		for (int i=0; i<40; i++)
		for (int j=0; j<80; j++)
		{
			matrix[j][i]=(int)(Math.random()*3f);
		}
		
		for (int k=0; k<8; k++)
		for (int i=1; i<39; i++)
		for (int j=1; j<79; j++)
		{
			float summ=matrix[j][i];
			
			summ+=matrix[j+1][i];
			summ+=matrix[j-1][i];
			
			summ+=matrix[j][i+1];
			summ+=matrix[j][i-1];
			
			matrix[j][i]=summ/5f;
		}
		
		
		for (int i=0; i<40; i++)
		{
			
			for (int j=0; j<80; j++)
			{
				s+=indicate[(int) matrix[j][i]];
			}
			s+="<br>";
		}
		
		s+="</font>";
		
		/*
		String s="<table border=0>";
		
		for (int j=0; j<40; j++)
		{
			s+="<tr>";
			for (int i=0; i<40; i++)
			{
				String color="";
				
				for (int c=0; c<3; c++)
				{
					color+=(int)(Math.random()*80)+19;
				}
				s+="<td width=7 height=7 bgcolor=#"+color+"></td>";
			}
			s+="</tr>";
		}*/
		
		response.getWriter().append(s+"");
	}

}


