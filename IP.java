package com.client;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.server.Threadr;

public class IP implements Runnable
{
	String s;
		public IP(String s) {
		this.s=s;
	}

		public void run()
		{
			try 
			{
				Socket cs1=new Socket(s,3000+Threadr.port);
				System.out.println(3000+Threadr.port);
				DataOutputStream op=new DataOutputStream(cs1.getOutputStream());
				op.writeUTF("insert into ip values('"+InetAddress.getLocalHost().getHostAddress()+"');");
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
