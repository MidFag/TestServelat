package com.midfag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/MySQL")
public class MySQL extends HttpServlet {

	private static List<UserData> user_data_list = new ArrayList<UserData>();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		    System.out.println("Where is your MySQL JDBC Driver?");
		    e.printStackTrace();
		    return;
		}
		

		Connection dbConnection = null;
		
		try 
		{
			String url = "jdbc:mysql://localhost:3306/schema";
			Properties info = new Properties();
			info.put("user", "root");
			info.put("password", "root");
			dbConnection = DriverManager.getConnection(url, info);
			
			if (dbConnection != null)
			{
				System.out.println("Successfully connected to MySQL database test");
				Statement myStmt = dbConnection.createStatement();
				ResultSet ress=myStmt.executeQuery("SELECT * FROM `schema`.user;");
				
				user_data_list.add(new UserData());
				
				while (ress.next())
				{
					System.out.println("имя <"+ress.getString("name")+">\tфамилия <"+ress.getString("sureName")+">");
					
					UserData ud=new UserData();
						ud.user_name=ress.getString("name");
						ud.user_sure_name=ress.getString("sureName");
						ud.id=Integer.parseInt(ress.getString("userId"));
						
						user_data_list.add(ud);
				}
				
				ress=myStmt.executeQuery("SELECT * FROM `schema`.account;");
				while (ress.next())
				{
					int holder_id=ress.getInt("userId");
					
					AccountData ad=new AccountData();
						ad.money=ress.getDouble("account");
						ad.master=user_data_list.get(holder_id);
					
					System.out.println("СУММА: ["+ress.getDouble("account")+"]\t\tвладелец: имя <"+user_data_list.get(holder_id).user_name+">.......фамилия <"+user_data_list.get(holder_id).user_sure_name+">");
					
					user_data_list.get(holder_id).user_account=ad;
					
				}
				
				/*
				System.out.println("Запрос: "+request.getParameter("user_id"));
				System.out.println("Кнопка: "+request.getParameter("submit_id"));*/
			}
		}
		catch (SQLException ex)
		{
			System.out.println("An error occurred while connecting MySQL databse");
			ex.printStackTrace();
		}

		int req = 1;
		if (!request.getParameter("user_id").equals(""))
		{
			try {
				req = Integer.parseInt(request.getParameter("user_id"));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String output="";
		
		if (request.getParameter("submit_id").equals("get_user_by_id"))
		{
			output="<h2>Информация о пользователе номер {"+req+"}</h2>";
			output+=get_user_info_from_id(req);
		}
		
		if (request.getParameter("submit_id").equals("get_all_users"))
		{
			output="<h2>Информация о всех пользователях</h2>";
			
			for (int i=1; i<user_data_list.size(); i++)
			{output+=get_user_info_from_id(i)+"<p><hr>";}
		}
		
		if (request.getParameter("submit_id").equals("get_rich_user"))
		{
			output="<h2>Самый богатый пользователь</h2>";
			
			double max=0;
			int id=0;
			
			for (UserData udl:user_data_list)
			{
				if ((udl.user_account!=null)&&(udl.user_account.money>max)) {id=udl.id; max=udl.user_account.money;}
			}
			
			output+=get_user_info_from_id(id)+"<p><hr>";
		}
		
		response.getWriter().append(output);

		
		
	}
	
	public String get_user_info_from_id(int _id)
	{
		String s="";
		s+=	"<table border=1 cellpadding=7 bgcolor=#DDEEFF bordercolor=#EEFFFF>"
		+	"<tr> <td bordercolor=#CCDDEE><b>Имя, фамилия:</b> "+user_data_list.get(_id).user_name+" "+user_data_list.get(_id).user_sure_name;
		s+="<tr><td bordercolor=#EEDDCC bgcolor=#FFFFEE><b>Баланс:</b> "+user_data_list.get(_id).user_account.money+"";
		s+="</td></tr></table>";
		
		return s;
	}

}


