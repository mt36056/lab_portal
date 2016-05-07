package com.server;


import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

public class serverFrame  
{
	static byte flag=0;
	JFrame frame;
	JDialog d;
	JPanel pTop,pGreet,pList,pActive,pPrevious,pNew,pShutDown,pChangeA,pChangeS,pPerticular,pClear,pMiddle,pCheck;
	JButton bActive,bPrevious,bShutDown,bChangeA,bChangeS,bClear,bPerticular,bCheck;
	JLabel lGreet,lActive,lPrevious,lShutDown,lChangeA,lChangeS,lClear,lPerticular,lCheck;
	Connection con;Statement stmt;
	serverFrame() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");   
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root",InterFace.s);   
			stmt=con.createStatement();

			pTop=new JPanel(new FlowLayout());
			pMiddle=new JPanel(new GridLayout(1,2));
			pGreet=new JPanel(new FlowLayout());
			pList=new JPanel(new GridLayout(6,1));
			pClear=new JPanel(new FlowLayout());
			pNew=new JPanel(new GridLayout(2,1));
			pCheck=new JPanel(new FlowLayout());
			frame=new JFrame("Server");
			
			
			pGreet.add(lGreet=new JLabel());
			lGreet.setFont(new Font("SANS_SERIF", Font.BOLD, 50));
			lGreet.setForeground(Color.RED);
			Thread tGreet=new Thread("Greet")
			{ 
				  int hour = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);    
				  public void run(){
				  if (hour < 12)  
					  lGreet.setText("Good Morning!");  
				  else if (hour < 17 && !(hour == 12))  
					  lGreet.setText("Good Afternoon");  
				  else if (hour == 12)  
					  lGreet.setText("Good Noon");  
				  else  
					  lGreet.setText("Good Evening");}  
			};
			tGreet.start();
			
			new IP().start();
		    pTop.add(pGreet);
		    
		    pTop.add( new DigitalClock());
		    
		    pActive=new JPanel(new FlowLayout());
		    pActive.add(lActive=new JLabel("->Active Users                                                      "));
		    pActive.add(bActive=new JButton("Show"));
		    pList.add(pActive);
		    bActive.addActionListener(new ActionListener()
		    {
				public void actionPerformed(ActionEvent arg0) 
				{
					dialog(0);
				}
		    });
		    pPrevious=new JPanel(new FlowLayout());
		    pPrevious.add(lPrevious=new JLabel("->Previous Session                                             "));
		    pPrevious.add(bPrevious=new JButton("Show"));
		    pList.add(pPrevious);
		    bPrevious.addActionListener(new ActionListener()
		    {
				public void actionPerformed(ActionEvent arg0) 
				{
					dialog(1);
				}
		    });
		    pShutDown=new JPanel(new FlowLayout());
		    pShutDown.add(lPrevious=new JLabel("->ShutDown All Active PC                           "));
		    pShutDown.add(bShutDown=new JButton("ShutDown"));
		    pList.add(pShutDown);
		    bShutDown.addActionListener(new ActionListener()
		    {
				public void actionPerformed(ActionEvent arg0) 
				{
					try{    
					ResultSet rs=stmt.executeQuery("select * from ip;");  
					while(rs.next())
					{
						Socket cs = new Socket (rs.getString(1),10288);
						DataOutputStream op=new DataOutputStream(cs.getOutputStream());
						op.writeInt(1);
					}}
					catch(Exception e){}
				}
		    });
		    pChangeA=new JPanel(new FlowLayout());
		    pChangeA.add(lChangeA=new JLabel("->Change Your Password                                "));
		    pChangeA.add(bChangeA=new JButton("Change"));
		    pList.add(pChangeA);
		    bChangeA.addActionListener(new ActionListener()
		    {
				public void actionPerformed(ActionEvent arg0) 
				{
					dialog(5);
				}
		    });
		    pChangeS=new JPanel(new FlowLayout());
		    pChangeS.add(lChangeS=new JLabel("->Change The Password Of A Student         "));
		    pChangeS.add(bChangeS=new JButton("Change"));
		    pList.add(pChangeS);
		    bChangeS.addActionListener(new ActionListener()
		    {
				public void actionPerformed(ActionEvent arg0) 
				{
					dialog(6);
				}
		    });
		    pPerticular=new JPanel(new FlowLayout());
		    pPerticular.add(lPerticular=new JLabel("->ShutDown A Particular PC                        "));
		    pPerticular.add(bPerticular=new JButton("ShutDown"));
		    pList.add(pPerticular);
		    bPerticular.addActionListener(new ActionListener()
		    {
				public void actionPerformed(ActionEvent arg0) 
				{
					try{    
							String s=JOptionPane.showInputDialog(null,"enter the ip of the pc","Input",JOptionPane.OK_CANCEL_OPTION);
							
							Socket cs = new Socket (s,10288);
							DataOutputStream op=new DataOutputStream(cs.getOutputStream());
							op.writeInt(1);
						}
						catch(Exception e){}
				}
		    });
			frame.add(pTop);
			pMiddle.add(pList);
			pClear.add(lClear=new JLabel("->Clear All DataBase"));
			
			JPanel Ptemp=new JPanel();
			Ptemp.add(bClear=new JButton("Clear"));
			pClear.add(Ptemp);
			pNew.add(pClear);
			
			pCheck.add(lCheck=new JLabel("->Check In DataBase"));
			JPanel Ptemp1=new JPanel();
			Ptemp1.add(bCheck=new JButton("Check"));
			pCheck.add(Ptemp1);
			pNew.add(pCheck);
			pMiddle.add(pNew);
			
			bCheck.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent arg0)
						{
							dialog(7);
						}
					});
			bClear.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root",InterFace.s);
					Statement stmt=con.createStatement();
					stmt.execute("DELETE FROM newuser;");
					stmt.execute("DELETE FROM active;");
					stmt.execute("DELETE FROM drive;");
					stmt.execute("DELETE FROM previous_session;");
					JOptionPane.showMessageDialog(null,"Operation Completed!!!","Done",JOptionPane.INFORMATION_MESSAGE);
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null,"Failed !!","error",JOptionPane.ERROR_MESSAGE);
					}
		}});
			frame.add(pMiddle);
			frame.setSize(710,500);
			frame.setResizable(false);
		    frame.setLayout(new GridLayout(2,1));
			frame.setVisible(true);
		    
			frame.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					try {
						
						ResultSet rs=stmt.executeQuery("select new_user.collg_id,name,course,branch,ip_add,t_o_login from new_user join active on new_user.collg_id=active.collg_id ;");
						Statement stmt1=con.createStatement();
						while(rs.next())
						{
							stmt1.execute("Insert into previous_session values("+rs.getInt(1)+",'"+rs.getString(2)+"','"+rs.getString(3)+"','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"');");
						
						}
						stmt.execute("DELETE FROM active;");
						stmt.execute("DELETE FROM drive;");
						stmt.execute("DELETE FROM ip;");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					System.exit(0);
				}
			});
			Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}	 
	 void dialog(final int i)
	{
		
		d= new JDialog();
		if(i==0)
		{
			d.setTitle("Select One");
			d.setLayout(new GridLayout(4,1));
			d.setSize(250,150);
			final JLabel lerr=new JLabel("                                                         ");
			final JRadioButton jActive;
			final JRadioButton jPeripheral;
			final ButtonGroup b=new ButtonGroup();
			b.add(jActive=new JRadioButton("Show Active Users"));
			b.add(jPeripheral=new JRadioButton("Show the Connected Peripheral Devices"));
			d.add(jActive);d.add(jPeripheral);
			JPanel pButton=new JPanel(new FlowLayout());
			JButton bsub=new JButton("Next");
			pButton.add(bsub);
			d.add(pButton);
			d.add(lerr);
			d.setVisible(true);
			
			bsub.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(jActive.isSelected())
					{
						lerr.setText("                                  ");
						dialog(4);
					}
					else if(jPeripheral.isSelected())
					{
						lerr.setText("                                  ");
						dialog(2);
					}
					else {
						lerr.setForeground(Color.RED);
						lerr.setText("Please Select One");
					}
					
				}
				});
		}
		else if(i==1)
		{
			
			d.setTitle("Fill The Details");
			d.setLayout(new FlowLayout());
			d.setSize(220,260);
			final JButton bsub=new JButton("Submit");
			final JLabel lCourse;
			final JLabel lSec;
			final JLabel lSem;
			final JLabel lDate;
			JLabel lerr=new JLabel("                                                  ");
			final JTextField tfSec;
			final JComboBox liSem;
			final JComboBox liMonth;
			final JComboBox liDay;
			final JComboBox liYear;
			final JComboBox tfCourse;
			lCourse=new JLabel("Enter Course");
			lSec=new JLabel("Enter Section");
			lSem=new JLabel("Select Samester");
			lDate=new JLabel("Select Date");
			
			 String Course[]={"Select","B.Tech","BBA","BCA","B.Sc(IT)","B.Com(Hons)","MBA","BHM","BJMC","BSc(A & Gaming)","B.Arch","MCA","M.Tech","B.Sc(Math)","BA(Eng)","B.Sc(Physics)","Fine Arts","B.Des","MJMC","M.Sc(IT)","M.SC(BT)","M.Sc(Micro-Biology)"};
			
			 tfCourse=new JComboBox(Course);
			tfCourse.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(tfCourse.getSelectedIndex()==0)
					{
						lCourse.setForeground(Color.RED);
						bsub.setEnabled(false);
						flag=1;
					}
					else{
						lCourse.setForeground(Color.BLACK);
						flag=0;
					}
				}
			});
			final String[] sec={"SELECT","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
			final JComboBox cbSec=new JComboBox(sec);

			final String sem[]={"***","1","2","3","4","5","6","7","8"};
			liSem=new JComboBox(sem);
			liSem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(liSem.getSelectedIndex()==0)
					{
						lSem.setForeground(Color.RED);
						bsub.setEnabled(false);
						flag=1;
					}
					else{
						lSem.setForeground(Color.BLACK);
						flag=0;
					}
				}
			});
			UtilDateModel model=new UtilDateModel();
			Properties p=new Properties();
			p.put("text.today","Today");
			p.put("text.month","Month");
			p.put("text.year","Year");
			JDatePanelImpl datePanel=new JDatePanelImpl(model,p);
			final JDatePickerImpl datePicker=new JDatePickerImpl(datePanel,new DateLabelFormatter());
			d.add(lCourse);d.add(tfCourse);d.add(lSec);d.add(cbSec);d.add(lSem);d.add(liSem);d.add(lDate);d.add(datePicker);
			d.add(bsub,"Center");
			d.add(lerr);
		
			d.setVisible(true);
			bsub.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					d.dispose();
					
						String sql="select new_user.collg_id,new_user.name,previous_session.ip_add,previous_session.t_o_login,new_user.course,new_user.branch,new_user.semester,new_user.section from new_user join previous_session where new_user.course=previous_session.course and new_user.section='"+sec[cbSec.getSelectedIndex()]+"' and semester='"+sem[liSem.getSelectedIndex()]+"' and date(t_o_login)='"+datePicker.getModel().getYear()+"-"+(datePicker.getModel().getMonth()+1)+"-"+datePicker.getModel().getDay()+"';" ;
						try {
							dialog(3,sql);
						} catch (SQLException e) {
							
							e.printStackTrace();
						}	
				}});
		}
		else if(i==2)
		{
			d.setTitle("USB");
			d.setLayout(new FlowLayout());
			d.setSize(300,300);
			JTextArea tPeripheral=new JTextArea();
			JScrollPane jsp=new JScrollPane(tPeripheral);
			tPeripheral.setEditable(false);
			tPeripheral.append("  Name\t\tPeripheral  ");
			tPeripheral.append("\n________________________________________\n");
			try {
				ResultSet rs=stmt.executeQuery("Select * from drive;");
				while(rs.next())
				{
					tPeripheral.append(rs.getString(1)+"\t"+rs.getString(2)+"\n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			
			
			d.add(jsp);
			d.setVisible(true);
		}
		
		else if(i==4)
		{
			d.setTitle("Active Users");
			d.setSize(700,600);
			d.setLayout(new BorderLayout());
			final JTextArea tActive=new JTextArea();
			JScrollPane jsp=new JScrollPane(tActive);
			tActive.setEditable(false);
		
			
			d.getContentPane().add(jsp);
			d.setVisible(true);
			
			tActive.append("COLLEGEID\tNAME\t\tIP\t            LOGIN TIME\t       COURSE\tBRANCH");
			tActive.append("\n___________________________________________________________________________________________________\n");
			try {
				ResultSet rs=stmt.executeQuery("select new_user.collg_id,name,ip_add,t_o_login,course,branch from new_user join active on new_user.collg_id=active.collg_id;");
				while(rs.next())
				{
					tActive.append(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+"\n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		else if(i==5)
		{
				final JPasswordField tf;
				JButton submit;
				
				d.setTitle("Change");
				d.setLayout(new FlowLayout());
				d.setSize(215,140);
				d.setLocationRelativeTo(null);
				d.add(new JLabel("Enter Your Old Password"),"Center");
				d.add(tf=new JPasswordField(15),"Center");
				d.add(submit=new JButton("Submit"),"Center");
				d.setVisible(true);
				submit.addActionListener(new ActionListener()
				{
					
					public void actionPerformed(ActionEvent arg0) 
					{
						try 
						{  
							Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root","12345");   
							Statement stmt=con.createStatement();
							ResultSet rs=stmt.executeQuery("select * from admin_pass");
						 
						while(rs.next()){
							if(tf.getText().equals(rs.getString(1)))
							{
								d.dispose();
								String n=JOptionPane.showInputDialog(null, "Enter NEW Password","Password",JOptionPane.OK_CANCEL_OPTION);
								if(n.equals(""))
								{
									JOptionPane.showInputDialog(null,"This can not be null","error",JOptionPane.ERROR_MESSAGE);	
								}
								else
								{
									stmt.execute("update admin_pass set password='"+n+"';");
								}
							}
							else
								JOptionPane.showMessageDialog(null, "Password not found","error",JOptionPane.ERROR_MESSAGE);
						}
						} catch (Exception e) {
						
							JOptionPane.showMessageDialog(null,"Hurray!!!Password Changed Successfully!!","Done",JOptionPane.ERROR_MESSAGE);

						}}
				});}
				else if(i==6)
				{try{
					String s=JOptionPane.showInputDialog(null,"Enter College ID Number","Input",JOptionPane.QUESTION_MESSAGE);
					String spass=JOptionPane.showInputDialog(null,"Enter New Password","Input",JOptionPane.QUESTION_MESSAGE);
					
					if(spass.equals(""))
					{JOptionPane.showMessageDialog(null,"This can not be null","error",JOptionPane.ERROR_MESSAGE);}
					else
					{
						Class.forName("com.mysql.jdbc.Driver");   
						 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root","12345");   
						stmt=con.createStatement();
						
						stmt.execute("update new_user set password='"+spass+"' where collg_id="+s+";");
					}
					}catch(Exception e){JOptionPane.showMessageDialog(null,e,"error",JOptionPane.ERROR_MESSAGE);}
					}
				else if(i==7)
				{
					d.setTitle("Check");
					String s=JOptionPane.showInputDialog(null,"Enter College Roll No.","Enter");
					d.setSize(700,600);
					d.setLayout(new BorderLayout());
					final JTextArea tActive=new JTextArea();
					JScrollPane jsp=new JScrollPane(tActive);
					tActive.setEditable(false);
			
					
					d.getContentPane().add(jsp);
					d.setVisible(true);
					try {
						ResultSet rs=stmt.executeQuery("select * from new_user where collg_id="+s+";");
						while(rs.next())
						{
							tActive.append("\nName:="+rs.getString(2));
							tActive.append("\nGen:="+rs.getString(3));
							tActive.append("\nCourse:="+rs.getString(5));
							tActive.append("\nBranch:="+rs.getString(6));
							tActive.append("\nSection:="+rs.getString(7));
							tActive.append("\nSem.:="+rs.getInt(8)+"\n");
						}
						rs=stmt.executeQuery(" select ip_add,t_o_login from previous_session where collg_id="+s+";");

						tActive.append("\n\nIP ADD.\tTIME OF LOGIN\n ");
						tActive.append("_______________________________________\n");
						while(rs.next())
						{
							tActive.append(rs.getString(1)+"\t"+rs.getString(2)+"\n");;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				d.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				int n=JOptionPane.showConfirmDialog(null, "Do You Want To Close This Dialog Box","Confermation box",JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION)
				{d.dispose();}
				else{dialog(i);}
				
			}
		});
		d.setResizable(false);
		d.setLocationRelativeTo(null);
		
	}
	 public void fileDialog(String query)
	 {
		 try{
             FileDialog fd=new FileDialog(frame,"Save File",FileDialog.SAVE);
             fd.setVisible(true);
             String dir=fd.getDirectory();
             String fname=fd.getFile()+".txt";
             BufferedWriter br=new BufferedWriter(new FileWriter(dir+fname));
             ResultSet rs=stmt.executeQuery(query);
             String s;
             br.write("ID CODE\t          NAME\t  SYSTEM INFO\t\t\t       LOGIN TIME\tCOURSE\t\tBRANCH\t   SEMESTER    SECTION");
             br.newLine();
             br.write("_______________________________________________________________________________________________________________________________");
             br.newLine();
             while(rs.next())
             {
            	 s=rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t  "+rs.getString(5)+"\t  "+rs.getString(6)+"\t"+rs.getInt(7)+"\t"+rs.getString(8);
            	 br.write(s);
            	 br.newLine();
            	 
             }
             rs.close();
             br.close();
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,e,"error",JOptionPane.ERROR_MESSAGE);
		}
	 }
	void  dialog(int i,final String quary) throws SQLException
	 {
		 if(i==3)
			{
			 	JDialog d=new JDialog();
				d.setTitle("Previous Users");
				d.setSize(800,600);
				d.setLayout(new BorderLayout());
				JPanel p=new JPanel(new FlowLayout());
				JPanel p1=new JPanel(new GridLayout());
				final JTextArea tPrevious;
				p.add(tPrevious=new JTextArea());
				JScrollPane jsp=new JScrollPane(tPrevious);
				tPrevious.setEditable(false);
				JButton bSave=new JButton("Save Result");
				p.add(bSave,"Center");
				d.add(p,BorderLayout.NORTH);
				p1.add(jsp);
				d.add(p1,BorderLayout.CENTER);
				d.setVisible(true);
				tPrevious.append("COLLEGEID\t  NAME\t  SYSTEM INFO\t\t       LOGIN TIME\tCOURSE\tBRANCH   SEMESTER    SECTION");
				tPrevious.append("\n_______________________________________________________________________________________________________________________\n");
				ResultSet rs;
				rs=stmt.executeQuery(quary);
				while(rs.next())
				{
					tPrevious.append(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t  "+rs.getString(5)+"\t  "+rs.getString(6)+"\t    "+rs.getInt(7)+"   "+rs.getString(8)+"\n");
				}
				bSave.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0)
					{
						fileDialog(quary);
					}});
			}
	 }
}