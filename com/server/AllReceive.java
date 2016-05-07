
package com.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AllReceive extends Thread
{
	static Statement stmt;static Connection con;
	static ServerSocket welcomeSocket;
	static Socket connectionSocket;
	static DataInputStream inFromClient;
	int port;
	static String query;
	public void receive() throws Exception
	{
		query=inFromClient.readUTF();
		stmt.execute(query);
	}
	public void run()
	{
		System.out.println("All Receive");
		connect();
		while(true){
		try
		{
			receive();
			Thread.sleep(200);
			
		}catch(Exception e)
		{
			close();
			continue;
		}}
	}

	public static  void close() 
	{
		try
		{
			welcomeSocket.close();
			connectionSocket.close();
			inFromClient.close();
			stmt.close();
			con.close();	
			connect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	static public void connect()
	{
		try{
		Class.forName("com.mysql.jdbc.Driver");   
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root",InterFace.s);   
		stmt=con.createStatement();
		welcomeSocket = new ServerSocket(8500+Threadr.port);
		connectionSocket = welcomeSocket.accept(); 
		inFromClient =new DataInputStream(connectionSocket.getInputStream());
		}catch(Exception e){}
	}
}
