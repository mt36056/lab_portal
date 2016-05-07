package com.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class LoginListen extends Thread 
{
	public static Socket cSocket ;
	public static DataOutputStream o;
	public static Statement stmt;public static Connection con;
	public static DataInputStream inFromClient;
	public static Socket connectionSocket; 
	public static ServerSocket welcomeSocket;
	static String s;
	public static void close()
	{
		try{
		welcomeSocket.close();
		connectionSocket.close();
		inFromClient.close();
		stmt.close();
		connect();
		}catch(Exception e){}
	}
	
	public static void connect()throws Exception
	{
		System.out.println("LoginlISTEN CONNECT");
		Class.forName("com.mysql.jdbc.Driver");   
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root",InterFace.s);   
		stmt=con.createStatement();
		welcomeSocket = new ServerSocket(6661+Threadr.port);
		connectionSocket = welcomeSocket.accept();
		inFromClient =new DataInputStream(connectionSocket.getInputStream());
		if((s=inFromClient.readUTF())!=null)
		{	
			new Send(s).run();
		}
		
	}
	public void run()
	{
		System.out.println("LOGINLISTEN");
		try
		{
			connect();
			Thread.sleep(200);
		}
		catch(Exception e)
		{
				close();
		}
	}
}		

