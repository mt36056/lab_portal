package com.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Threadr implements Runnable 
{

	public static int port;
	String s;
		public Threadr(String s)
		{
			this.s=s;
		}
		int num1;
		
		public void run() {
			String num=JOptionPane.showInputDialog(null,"System Number","Enter The System Number '1-100'");
			try{
				num1=Integer.parseInt(num);
				if(num1<1 && num1>100)
				{
					throw new IOException();
				}
				int ipw = 12111+(num1-1);
				port=num1-1;
				work(ipw);
			}catch(IOException e){JOptionPane.showMessageDialog(null,"Sorry You Enter A wrong System Number :(","Error",JOptionPane.WARNING_MESSAGE);
			try{Runtime.getRuntime().exec("shutdown /p /f");} catch (Exception e1) {}}
			catch(NumberFormatException e){JOptionPane.showMessageDialog(null, "Please Enter Only Digit :(","Error",JOptionPane.WARNING_MESSAGE);
			try{Runtime.getRuntime().exec("shutdown /p /f");} catch (Exception e1) {}}
			catch(Exception e){JOptionPane.showMessageDialog(null, "Sorry Something Wrong :(","Error",JOptionPane.WARNING_MESSAGE);e.printStackTrace();
			try{Runtime.getRuntime().exec("shutdown /p /f");} catch (Exception e1) {}}
			 
			}
	void work(int ipw)throws Exception 
	{
		Socket ss=new Socket(s,ipw);
		DataOutputStream ip=new DataOutputStream(ss.getOutputStream());
		ip.writeUTF(port+"");
		Thread.sleep(200);
	}
}
