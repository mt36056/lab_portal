
package com.server;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Close extends Thread
{

	static Statement stmt,stmt1;static Connection con;
	static ServerSocket welcomeSocket;
	static Socket connectionSocket;
	static DataInputStream inFromClient;
	int port;
	
	public void run()
	{
		connect();
		System.out.println("close");
		while(true){
		try
		{
				String s=inFromClient.readUTF();
				ResultSet rs=stmt.executeQuery("select new_user.collg_id,name,course,branch,ip_add,t_o_login from new_user join active on new_user.collg_id=active.collg_id and  ip_add='"+s+"';");
				stmt1=con.createStatement();
				while(rs.next())
				{
					stmt1.execute("Insert into previous_session values("+rs.getInt(1)+",'"+rs.getString(2)+"','"+rs.getString(3)+"','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"');");
				}
				stmt.execute("Delete from active where ip_add='"+s+"';");
				close();
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
			stmt1.close();
			con.close();
			LoginListen.close();
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
		stmt1=con.createStatement();
		welcomeSocket = new ServerSocket(5000+Threadr.port);
		connectionSocket = welcomeSocket.accept(); 
		inFromClient =new DataInputStream(connectionSocket.getInputStream());
	
		}catch(Exception e){}}
}
