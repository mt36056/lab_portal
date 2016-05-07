package com.client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

public class LoginCheck implements Runnable
{
	 DataOutputStream op;DataInputStream ip;
	static String receive =new String();
	Socket cs;ServerSocket cs1;
	JLabel lName,lGreet,lCollege,lBranch,lSem,aCollege,aBranch,aSem,aSec,lSec,lCourse,aCourse,lMonitor;
	JPanel pGreet,pTop,pName,pCollege,pBranch,pCourse,pSem,pSec,pLeft,pRight,pMiddle,pGreetAndClock,pMonitor;
	String s;
	Socket csnew;
	static JFrame frame3;
	public LoginCheck(Socket cs, ServerSocket cs1,String s)
	{
		this.cs=cs;
		this.cs1=cs1;
		this.s=s;
	}
	void close()
	{try{
		cs.close();
		cs1.close();
		}catch(Exception e){}
	}
	public void run()
	{
		try
		{
		op=new DataOutputStream(cs.getOutputStream());
		op.writeUTF("SELECT * FROM new_user WHERE collg_id="+Login.tfName.getText()+" and password='"+Login.tfPass.getText()+"';");
			Socket connso=cs1.accept();
			ip=new DataInputStream(connso.getInputStream());
			
			receive=ip.readUTF();
			
			
			if (receive.equals("Found"))
				{
					close(); 
					csnew = new Socket (s,8500+Threadr.port);
					op=new DataOutputStream(csnew.getOutputStream()); 
					op.writeUTF("INSERT INTO active VALUES("+Login.tfName.getText()+",current_timestamp,'"+InetAddress.getLocalHost()+"');");
					ServerSocket ss=new ServerSocket(12056);
				 	Login.f.setVisible(false);
					frame3=new JFrame("Welcome");
					frame3.setLayout(new GridLayout(3,1));
					pLeft=new JPanel();
					pRight=new JPanel();
					pGreetAndClock=new JPanel(new GridLayout(2,1));
					pMiddle=new JPanel(new GridLayout(1,2));
					pLeft.setLayout(new GridLayout(7,1));
					frame3.setSize(800,400);
					frame3.setResizable(false);
				    pTop=new JPanel(new FlowLayout());
				    pGreet=new JPanel(new FlowLayout());
					pGreet.add(lGreet=new JLabel());
					lGreet.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
					lGreet.setForeground(Color.RED);
					 lGreet.setText("                           ");
					pGreetAndClock.add( new DigitalClock());
				    pTop.add(pGreet);
				    pTop.add(lName=new JLabel("                                      "));
				    lName.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
				    lName.setForeground(Color.DARK_GRAY);
				    pGreetAndClock.add(pTop);
				    pCourse=new JPanel(new GridLayout(1,2));
				    pCourse.add(lCourse=new JLabel("Course:"));
				    pCourse.add(aCourse=new JLabel("                                 "));
				    
				    pCollege=new JPanel(new GridLayout(1,2));
				    pCollege.add(lCollege=new JLabel("Enrollment No.:"));
				    pCollege.add(aCollege=new JLabel("                                 "));
				    pLeft.add(pCollege);
				    pLeft.add(pCourse);

				    pBranch=new JPanel(new GridLayout(1,2));
				    pBranch.add(lBranch=new JLabel("Branch:"));
				    pBranch.add(aBranch=new JLabel("                                 "));
				    pLeft.add(pBranch);

				    pSem=new JPanel(new GridLayout(1,2));
				    pSem.add(lSem=new JLabel("Sem.:"));
				    pSem.add(aSem=new JLabel("                                 "));
				    pLeft.add(pSem);

				    pSec=new JPanel(new GridLayout(1,2));
				    pSec.add(lSec=new JLabel("Section:"));
				    pSec.add(aSec=new JLabel("                                 "));
				    pLeft.add(pSec);
				    frame3.add(pGreetAndClock);
				    pMiddle.add(pLeft);
				    pMiddle.add(pRight);
				    frame3.add(pMiddle);
					
					Socket s1=ss.accept();
					DataInputStream ip=new DataInputStream(s1.getInputStream());
					lGreet.setText(ip.readUTF());
					lName.setText(ip.readUTF());
					aCollege.setText(ip.readUTF());
					aCourse.setText(ip.readUTF());
					aBranch.setText(ip.readUTF());
					aSem.setText(ip.readUTF());
					aSec.setText(ip.readUTF());
					pMonitor=new JPanel(new FlowLayout());
					pMonitor.add(lMonitor=new JLabel("You Are Under Monitoring"),"Center");
					lMonitor.setForeground(Color.red);
					frame3.add(pMonitor);
					
					frame3.addWindowListener(new WindowAdapter()
				     {
				         public void windowClosing(WindowEvent we)
				         { 	
				        	 	int n=JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Meassage",	JOptionPane.YES_NO_OPTION );
					    		if(n == JOptionPane.YES_OPTION) 
					    		{
					    			try 
					    			{
										Socket csnew = new Socket (s,5000+Threadr.port);
										DataOutputStream op=new DataOutputStream(csnew.getOutputStream()); 
										op.writeUTF(InetAddress.getLocalHost()+"");
										csnew.close();
										try {Runtime.getRuntime().exec("shutdown /p /f");} catch (IOException e) {}
									} catch (Exception e) {
										e.printStackTrace();
									}
					    			
					    			System.exit(0);
					    		} 
					    		else
									try {
										new Login(Login.ip);
									
									} catch (Exception e) 
					    			{
										e.printStackTrace();
									}
				         }
				     });
				}
			else if (receive.equals("NOTFound")){
			close();
			Login.dialog(1);	
			JOptionPane.showMessageDialog(null,"Something Wrong :(","error",JOptionPane.ERROR_MESSAGE);
				receive=null;
			}
			Thread.sleep(200);
		}
		catch(Exception e3)
		{
			JOptionPane.showMessageDialog(null,e3,"error",JOptionPane.ERROR_MESSAGE);e3.printStackTrace();
		}
	}
   }