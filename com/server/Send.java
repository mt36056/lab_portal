package com.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

public class Send extends Thread
{
	public static	ResultSet rs; byte flag=0;
	String clientSentence;
	int collg_id;
	InetAddress ip;
	public Send(String readUTF)
	{
		clientSentence=readUTF;
	}
	public void run()
	{
		try{	
				System.out.println(clientSentence);
				rs=LoginListen.stmt.executeQuery(clientSentence);
				while(rs.next())
				{
				collg_id=rs.getInt(1);
				flag=1;
				ip=LoginListen.connectionSocket.getInetAddress();
				LoginListen.cSocket = new Socket(ip.getHostAddress(),6666);
				LoginListen.o=new DataOutputStream(LoginListen.cSocket.getOutputStream());
				LoginListen.o.writeUTF("Found");
				Thread tData=new Thread("Data")
				{
					public void run()
					{
						try {
							java.sql.Connection con;Statement stmt;
							
							Socket ss=new Socket(ip.getHostAddress(),12056);
							DataOutputStream op=new DataOutputStream(ss.getOutputStream());
							Class.forName("com.mysql.jdbc.Driver");   
							con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root",InterFace.s);   
							stmt=con.createStatement();
							int hour = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);    
							if (hour < 12)  
								op.writeUTF("Good Morning!");  
							else if (hour < 17 && !(hour == 12))  
								op.writeUTF("Good Afternoon");  
							else if (hour == 12)  
								op.writeUTF("Good Noon");  
							else  
								op.writeUTF("Good Evening");  
							ResultSet rs=stmt.executeQuery("Select name,collg_id,course,branch,semester,section from new_user WHERE collg_id="+collg_id+";");//sql
							while(rs.next())
							{
								op.writeUTF(rs.getString(1));
								op.writeUTF(rs.getInt(2)+"");
								op.writeUTF(rs.getString(3));
								op.writeUTF(rs.getString(4));
								op.writeUTF(rs.getInt(5)+"");
								op.writeUTF(rs.getString(6));
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				};
				tData.start();
				}
				if(flag==0)
				{
					InetAddress ip=LoginListen.connectionSocket.getInetAddress();
					LoginListen.cSocket = new Socket(ip.getHostAddress(),6666);
					LoginListen.o=new DataOutputStream(LoginListen.cSocket.getOutputStream());
					LoginListen.o.writeUTF("NOTFound");
					LoginListen.close();
					}
				
			Thread.sleep(200);
		}catch(Exception e2){try {
			e2.printStackTrace();
			InetAddress ip=LoginListen.connectionSocket.getInetAddress();
			LoginListen.cSocket = new Socket(ip.getHostAddress(),6666);
			LoginListen.o=new DataOutputStream(LoginListen.cSocket.getOutputStream());
			LoginListen.o.writeUTF("NOTFound");
			LoginListen.close();
			
		} 
		catch (Exception e)
		{	
			e.printStackTrace();
		} 
		}
		}
}
