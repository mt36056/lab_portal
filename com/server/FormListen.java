package com.server;

import java.sql.*;
import java.io.*;
import java.net.*;

public class FormListen extends Thread 
{
	static ServerSocket ss;
	static Socket s;
	static DataInputStream ds;
	static Connection con;
	static Statement stmt;
	
	public void run()
	{
		
			connect(); 
			
			while(true)
			{try{
				String query=ds.readUTF();
				if(query.equals("exit"))
				{
					close();
				}
				else
					stmt.execute(query);
				}
			catch (Exception e) {
				close();continue;
			}
			} 
	}
	static public void close() 
	{try{
		ss.close();
		con.close();
		stmt.close();
		ds.close();
		s.close();
		connect();}catch(Exception e){}
	}
	public static void connect() 
	{
		try{
		ss=new ServerSocket(12345+Threadr.port);
		Socket s=ss.accept();
		ds=new DataInputStream(s.getInputStream());
		Class.forName("com.mysql.jdbc.Driver");   
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root",InterFace.s); 
		stmt=con.createStatement();
		}catch(Exception e){}
	}
}
