package com.server;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Threadr extends Thread
{
	static ServerSocket s[]=new ServerSocket[100];
	static Socket ss[]=new Socket[100];
	static DataInputStream ip[]=new DataInputStream[100];
	static Thread t[]=new Thread[100];
public static int port;
public static int i;

static void close(int port) throws Exception
{
	s[port].close();
	ss[port].close();
	ip[port].close();
}
	public void run()
	{	
		try {
			for(i=0;i<100;i++)
			{
				s[i]=new ServerSocket(12111+i);
				t[i]=new Thread(i+"")
				{
					public void run()
					{
						try{
								int k=i;
								ss[k]=s[k].accept();
								System.out.println("Threadr"+((12111+k)));
								ip[k]=new DataInputStream(ss[k].getInputStream());
								port=Integer.parseInt(ip[k].readUTF());
								
								Thread t2=new Thread(new Drive());
								Thread t3=new Thread(new FormListen());
								Thread t4=new Thread(new LoginListen());
								Thread t1=new Thread(new Close());
								Thread t6=new Thread(new AllReceive());
								t2.setPriority(10);t3.setPriority(10);t4.setPriority(10);t1.setPriority(10);t6.setPriority(10);
								t2.start ();t3.start();  t4.start();t1.start();t6.start();
						
						} catch (Exception e) {
												e.printStackTrace();
						}
					}
				};
				t[i].setPriority(10);
				t[i].start();
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

