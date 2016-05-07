package com.client;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.net.*;
import java.io.*;
 

class Login extends JWindow
{
	JLabel lMain,lGreed,lIP;
	static JLabel lError;
	JPanel pName,pPass,pButton;
	static JTextField tfName;
	static JPasswordField tfPass;
	JButton jSignIn, jSignUp;
	static JFrame f;
	static String ip=null;
	static JDialog d;
 
	public static void dialog(int i)
	{
		d= new JDialog(f);
		if (i==1)
		{
			d.setTitle("Help");
			d.add( new ImageImplement(new ImageIcon("logo1.JPg").getImage()),"Center");
			d.setSize(250,470);
			d.setResizable(false);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}
		if(i==2)
		{
			String s="TERMS AND CONDITIONS OF USE Policy\n\n"+
					"INTRODUCTION AND ACCEPTANCE\n\n"+
					"you agree to the following Terms and\n"+ 
					"Conditions of Use Policy without limitation\n"+ 
					"or qualification.Please read these terms\n"+ 
					"and conditions carefully before using\n"+
					"Lab Portal portal.If you do not agree with\n"+ 
					"the Terms and Conditions of Use Policy\n"+
					"you are not granted permission to use Lab\n"+
					"Portal portal.\n\n"+
					"GENERAL USE OF THE PORTAL\n\n"+
					"1.In order to access certain features of\n"+ 
					"Lab portals, you may be required to provide\n"+ 
					"information about yourself (such as\n"+ 
					"identification or contact details) as part of\n"+
					 "the registration process, including to obtain\n"+
					 "a account to access certain sections of the\n"+
					 "portal, or as part of your continued use of\n"+
					"such  portal. You agree that any registration\n"+ 
					"information you give to us will always be\n"+ 
					"accurate, correct, and up to date.\n\n"+

					"2.You must be a human. accounts\n"+ 
					"registered by “bots” or other automated\n"+ 
					"methods are not permitted.\n\n"+

					"3. Your account may only be used by\n"+ 
					"one person – a single account shared by\n"+
					 "multiple people is not permitted.\n\n"+

					"4. You agree that you will be solely\n"+ 
					"responsible to for all activities that occur\n"+ 
					"under your account.\n\n"+

					"5. If you become aware of any\n"+ 
					"unauthorized use of your account or\n"+
					 "password, you agree to notify\n"+ 
					"us immediately.\n\n"+

					"UPDATED\n"+
					"Dec. 1, 2015\n\n"+
					 
					"HOW TO CONTACT US\n"+
					"GEHU CS/IT Department";
					
			d.setTitle("Terms and Condition");
			JTextArea ta;
			d.add(new JScrollPane(ta=new JTextArea(s)));
			ta.setEditable(false);
			d.setSize(250,470);
			d.setResizable(false);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}	
	}

	public Login(final String s) throws IOException,ParseException
	{
	ip=s;
	Thread t1=new Thread("detect")
				{
				public void run()
				{
					try{
						
						Socket cs = new Socket (s,2000+Threadr.port);		
				        String[] letters = new
				        		String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
				        File[] drives = new File[letters.length];
				        boolean[] isDrive = new boolean[letters.length];
				        
				        for ( int i = 0; i < letters.length; ++i )
				            {
				            drives[i] = new File(letters[i]+":/");
				            isDrive[i] = drives[i].canRead();
				            }
				         while(true)
				            {
				            for ( int i = 0; i < letters.length; ++i )
				                {
				                boolean pluggedIn = drives[i].canRead();
				                DataOutputStream op=new DataOutputStream(cs.getOutputStream());
				                if ( pluggedIn != isDrive[i] )
				                    {
				                    if ( pluggedIn )
				                    {
				                    	
				                    	op.writeUTF("INSERT INTO drive VALUES('"+InetAddress.getLocalHost()+"','CONNECTED');");
				                    }
				                    else{
				                    	
				                    	op.writeUTF("INSERT INTO drive VALUES('"+InetAddress.getLocalHost()+"','DISCONNECTED');");
				                    
				                    }isDrive[i] = pluggedIn;
				                    }
				                }
				            }
				         }
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null,e,"ERROR",JOptionPane.ERROR_MESSAGE);
					}
					}
				};
		
		
		Thread t2=new Thread("shutdown")
		{
		public void run()
		{
			
			try{
				
				ServerSocket welcomeSocket = new ServerSocket(10288);
				Socket connectionSocket = welcomeSocket.accept(); 
				DataInputStream inFromClient =new DataInputStream(connectionSocket.getInputStream());
				
				if(inFromClient.readInt()==1){
					Runtime.getRuntime().exec("shutdown /p /f"); 
		         }}
			catch(Exception e)
			{
			}
			}
		};
		t1.start();
		t2.start();
		
		
		
		f=new JFrame();
		f.setTitle("Lab Portal");
		lGreed=new JLabel("Welcome To GEHU");lGreed.setForeground(Color.LIGHT_GRAY);lGreed.setFont(new Font("SANS_SERIF",0,20));
		JOptionPane.showMessageDialog(null,"If You Close Any Of The Window This PC Will Automatically Shut Down ;)","Information",JOptionPane.WARNING_MESSAGE);
		
		pName=new JPanel(new FlowLayout());
		pPass=new JPanel(new FlowLayout());
		pButton=new JPanel(new FlowLayout());
		f.setLayout(new FlowLayout());
		lMain=new JLabel("Lab Portal");lMain.setForeground(Color.CYAN);lMain.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
		
		lIP=new JLabel("Server IP Address: "+s);lIP.setForeground(Color.RED);lGreed.setFont(new Font("SANS_SERIF",Font.ITALIC,20));
		f.add(lIP);
		
		InetAddress.getByName(s);
		
		pName.add(new JLabel("User Name    "));
		pName.add(tfName=new JTextField(10));
		pPass.add(new JLabel("Password     "));
		pPass.add(tfPass=new JPasswordField(10));
		tfName.setToolTipText("Enter College ID number");
		pButton.add(jSignIn=new JButton("Sign IN"));
		pButton.add(jSignUp=new JButton("Sign UP"));
		tfPass.setToolTipText("Enter Password");
		
		
		f.add(lGreed,"Center");
		f.add(lMain,"Center");
		f.add( new ImageImplement(new ImageIcon("logo.png").getImage()),"Center");
		f.add(pName,"Center");
		f.add(pPass,"Center");
		f.add(pButton,"Center");
		f.add(lError=new JLabel("                                           "),"Center");
		f.setSize(290,500);
		f.setVisible(true);
		
		f.setResizable(false);
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				int n=JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Meassage",JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION) 
				{try{	Runtime.getRuntime().exec("shutdown /p /f");} catch (Exception e) {} 
				}
				else
				{
					try
					{
						new Login(ip);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		jSignIn.addActionListener(new ActionListener()
		{	

			boolean isRegistered;
		    private JProgressBar progressBar = new JProgressBar();
		    private  int count;
		    private  Timer timer1;
			private void show()
			{
				Container container = getContentPane();
				container.add(new GIF("loading50.gif"));
				progressBar.setMaximum(50);
				loadProgressBar();
				setSize(380, 300);
				setLocationRelativeTo(null);
				setVisible(true);
		    }

		    private void loadProgressBar() 
		    {
		        ActionListener al = new ActionListener() 
		        {
		            public void actionPerformed(java.awt.event.ActionEvent evt)
		            {
		                count++;
		                progressBar.setValue(count);
		                if (count == 65) 
		                {
		                	LabInterface.execute1.setVisible(false);	
		                	timer1.stop();		                	
		                	LoginCheck.frame3.setVisible(true);
		                }
		            }
		        };
		        timer1 = new Timer(50, al);
		        timer1.start();
		    }	
		    
			public void actionPerformed(ActionEvent e) 
			{	
				try
				{		
    				Socket cs = new Socket (s,6661+Threadr.port);
    				ServerSocket cs1 = new ServerSocket (6666);
    				new LoginCheck(cs,cs1,s).run();
    				if(LoginCheck.receive.equals("Found"))
    					show();
    			}catch(Exception e6){JOptionPane.showMessageDialog(null,e6,"error",JOptionPane.ERROR_MESSAGE);}
				}
			});
		
		jSignUp.addActionListener(new ActionListener()
			{
				Thread tfrom=new Thread("form");
				public JLabel lName,lPassword,lCourse,lBranch,lMain,lGen,lerror,lClg;
				public JTextField tName,tPassword,tClg;
				public JPanel p1,p2,p3,p4,p5,p6,p7,p8,p9;
				public JRadioButton jbox;
				public JButton submit,read;
				byte flag=0;
				String gender1,branch1;
				@SuppressWarnings("rawtypes")
				public JComboBox c1,branch;
				public void actionPerformed(ActionEvent arg0)
				{
						f.setVisible(false);
						p1=new JPanel();
						p2=new JPanel();
						p3=new JPanel();
						p4=new JPanel();
						p5=new JPanel();
						p6=new JPanel();
						p7=new JPanel();
						p8=new JPanel();
						p9=new JPanel();
						
						p9.setLayout(new GridLayout(1,2));
						p1.setLayout(new GridLayout(1,2));
						p2.setLayout(new GridLayout(1,2));
						p3.setLayout(new GridLayout(1,2));
						p4.setLayout(new GridLayout(1,2));
						p5.setLayout(new GridLayout(1,2));
						p6.setLayout(new GridLayout());
						lMain=new JLabel("Plaease Fill This Form ");
						lMain.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
						lMain.setForeground(Color.red);
						p6.add(lMain,BorderLayout.CENTER);
						
						lerror=new JLabel();
						lerror.setForeground(Color.RED);
						
						final JFrame frame2 =new JFrame("New User");
					    frame2.setVisible(true);
					    frame2.setSize(820,400);
					   frame2.setResizable(false);
					    frame2.setLayout(new GridLayout(12,1));
					    

					    
					    lName=new JLabel("Name*");
					    p1.add(lName);
					    p1.add(tName=new JTextField("",15));
					
					    tName.addCaretListener(new CaretListener()
					    {
							public void caretUpdate(CaretEvent arg0)
							{
								lerror.setText("Enter Your Name");
							if( tName.getText().equals(""))
								{
									lName.setForeground(Color.RED);
									submit.setEnabled(false);
									flag=0;
								}
								else
								{
									lName.setForeground(Color.BLACK);
									flag=1;
								}
							}
					    });
					    
					    lPassword=new JLabel("Password*");
					    p2.add(lPassword);
					    p2.add(tPassword=new JPasswordField(10));
					    tPassword.addCaretListener(new CaretListener()
					    {
					    	
							public void caretUpdate(CaretEvent arg0)
							{
									lerror.setText("Enter Your Password");
									if(tPassword.getText().length()<=4 )
									{
										lPassword.setForeground(Color.RED);
										submit.setEnabled(false);
										flag=0;
									}
									else
									{
									lPassword.setForeground(Color.BLACK);
										flag=1;
									}
							}
					    });
					    
					    
					    lClg=new JLabel("College ID Number*");
					    p3.add(lClg);
					    p3.add(tClg=new JTextField(10));
					    tClg.addActionListener(new ActionListener()
					    {
							public void actionPerformed(ActionEvent arg0)
							{
								dialog(1);
								
							}
					    	
					    });
					    tClg.addCaretListener(new CaretListener()
					    {
							public void caretUpdate(CaretEvent arg0)
							{
								lerror.setText("Enter Your College Id Number");
								if(tClg.getText().length()<=7)
								{
									lClg.setForeground(Color.RED);
									submit.setEnabled(false);
									flag=0;
								}
								else
								{
									lClg.setForeground(Color.BLACK);
									flag=1;
								}
								
							}
					    });
					    
					    
					    lCourse=new JLabel("Course*");
					    p4.add(lCourse);
					    final JComboBox tCourse;
					    final String Course[]={"Select","B.Tech","BBA","BCA","B.Sc(IT)","B.Com(Hons)","MBA","BHM","BJMC","BSc(A & Gaming)","B.Arch","MCA","M.Tech","B.Sc(Math)","BA(Eng)","B.Sc(Physics)","Fine Arts","B.Des","MJMC","M.Sc(IT)","M.SC(BT)","M.Sc(Micro-Biology)"};
					   
					    p4.add(tCourse=new JComboBox(Course)); 
					    tCourse.addActionListener(new ActionListener()
					    {
							public void actionPerformed(ActionEvent arg0)
							{lerror.setText("Select Your Course");
								if(tCourse.getSelectedIndex()==0)
								{
									lCourse.setForeground(Color.RED);
									submit.setEnabled(false);
									flag=0;
								}
								else 
								{
									lCourse.setForeground(Color.BLACK);
									flag=1;
								}
								
							}
					    });
					   
					    
					    lBranch=new JLabel("Branch");
					    p5.add(lBranch);
					    final String bran[]={"Select If Any","C.S.E.","E.C.","C.E.","E.E.E.","M.E.","I.T."};
					    p5.add(branch=new JComboBox(bran));
					    
					    branch.addActionListener(new ActionListener()
					    {
							public void actionPerformed(ActionEvent arg0) 
							{
								lerror.setText("Select Your Branch (only for b.tech)");
								lBranch.setForeground(Color.BLACK);
								flag=1;
							}
					    });
					    
					    final String gender[]={"Select","Male","Female"};
					    lGen=new JLabel("Gender*");
					    c1=new JComboBox(gender);
					    c1.setSelectedIndex(0);
					    c1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent a) 
							{
								
									lerror.setText("Select Your Gender");
								if(c1.getSelectedIndex()==0)
								{
									lGen.setForeground(Color.RED);
									submit.setEnabled(false);
									flag=0;
								}
								else 
								{
									lGen.setForeground(Color.BLACK);
									flag=1;
								}
							}});
					    p9.setLayout(new GridLayout(1,2));
					    p9.add(lGen);
					    p9.add(c1);
					    
					    read=new JButton("read");
					    jbox=new JRadioButton("Accept Terms AND Conditions",false);
					    jbox.addActionListener(new ActionListener()
					    {
					    	public void actionPerformed(ActionEvent e)
					    	{
					    		if(jbox.isSelected() && flag==1)
					    		{
					    			submit.setEnabled(true);
					    		}
					    		else submit.setEnabled(false);	
					    	}
					    });
					    p7.add(jbox);
					    p7.add(read);
					    
					    read.addActionListener(new ActionListener()
					    {
					    	public void actionPerformed(ActionEvent e)
					    	{
					    		dialog(2);
					    	}
					    });
					    
					    p8.setLayout(new GridLayout(2,1));
					    submit=new JButton("Submit");
					    submit.setEnabled(false);
					    p8.add(submit);
					    final String sec[]={"SELECT","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","I","S","T","U","V","W","X","Y","Z"};
						   final JComboBox cbSec=new JComboBox(sec);
						   final String sem[]={"SELECT","1","2","3","4","5","6","7","8"};
						   final JComboBox cbSem=new JComboBox(sem);
						   
					    submit.addActionListener(new ActionListener()
					    		{
									public void actionPerformed(ActionEvent arg0)
									{
										DataOutputStream op = null;DataInputStream ip=null;
										try{
											Socket cs = new Socket (s,12345+Threadr.port);
											
											op=new DataOutputStream(cs.getOutputStream());
											
										}catch(Exception e3){JOptionPane.showMessageDialog(null,"Server Not Responding","Error",JOptionPane.ERROR_MESSAGE);
										try {
											Runtime.getRuntime().exec("shutdown /p /f");
										} catch (IOException e) {
										
											e.printStackTrace();
										} 
										}
										int n=JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Meassage",	JOptionPane.YES_NO_OPTION );
										if(n == JOptionPane.YES_OPTION) 
										{
											String qsql = null;
											try {
												if(bran[branch.getSelectedIndex()].equals("Select If Any"))
												{
													if(sec[cbSec.getSelectedIndex()].equals("SELECT"))
													{
														qsql="INSERT INTO new_user VALUES("+tClg.getText()+",'"+tName.getText().toUpperCase()+"','"+gender[c1.getSelectedIndex()]+"','"+tPassword.getText()+"','"+Course[tCourse.getSelectedIndex()]+"','"+""+"','"+""+"','"+sem[cbSem.getSelectedIndex()]+"');";
													}
													else
													{
														qsql="INSERT INTO new_user VALUES("+tClg.getText()+",'"+tName.getText().toUpperCase()+"','"+gender[c1.getSelectedIndex()]+"','"+tPassword.getText()+"','"+Course[tCourse.getSelectedIndex()]+"','"+""+"','"+sec[cbSec.getSelectedIndex()]+"','"+sem[cbSem.getSelectedIndex()]+"');";
													}
												}
												else 
												{
													if(sec[cbSec.getSelectedIndex()].equals("SELECT"))
													{
														qsql="INSERT INTO new_user VALUES("+tClg.getText()+",'"+tName.getText().toUpperCase()+"','"+gender[c1.getSelectedIndex()]+"','"+tPassword.getText()+"','"+Course[tCourse.getSelectedIndex()]+"','"+bran[branch.getSelectedIndex()]+"','"+""+"','"+sem[cbSem.getSelectedIndex()]+"');";
													}
													else 
													{
														qsql="INSERT INTO new_user VALUES("+tClg.getText()+",'"+tName.getText().toUpperCase()+"','"+gender[c1.getSelectedIndex()]+"','"+tPassword.getText()+"','"+Course[tCourse.getSelectedIndex()]+"','"+bran[branch.getSelectedIndex()]+"','"+sec[cbSec.getSelectedIndex()]+"','"+sem[cbSem.getSelectedIndex()]+"');";
													}
												}
										
												op.writeUTF(qsql);
												}catch(Exception e)
												{
													e.printStackTrace();}
													
											frame2.setVisible(false);
											f.setVisible(true);
										} else
											try {
												new Login(s);
											} 
											catch (Exception e) {
												e.printStackTrace();
											}	
									}
					    		
					    		});
					    final JLabel l11;
						final JLabel l12;
					    
					   JPanel p11=new JPanel(new GridLayout(1,2));
					   p11.add(l11=new JLabel("Select Section *"));
					   p11.add(cbSec);
					   String sec1;
					   cbSec.addActionListener(new ActionListener()
					   {
						   public void actionPerformed(ActionEvent e)
						   {
							   lerror.setText("Please Select Section(if any)");
							    l11.setForeground(Color.BLACK);flag=1;
							   
						   }
					   });
					  JPanel p12=new JPanel(new GridLayout(1,2));
					   
					   p12.add(l12=new JLabel("Select Sem"));
					   p12.add(cbSem);
					   cbSem.addActionListener(new ActionListener()
					   {
						   public void actionPerformed(ActionEvent e)
						   {
							   lerror.setText("Please Select Sem");
							   int n=cbSem.getSelectedIndex();
							   if(n==0)
							   {
								   
								   l12.setForeground(Color.red);
								   submit.setEnabled(false);
									flag=0;
							   }
							   else{l12.setForeground(Color.BLACK);
							   flag=1;}
						   }
					   });
					   p8.setLayout(new FlowLayout());
					    frame2.add(p6);
					    frame2.add(p3); 
					    frame2.add(p1);
					    frame2.add(p9);
					    frame2.add(p2);
					    frame2.add(p4);
					    frame2.add(p5);
					    frame2.add(p11);
					    frame2.add(p12);
					    frame2.add(p7);
					    frame2.add(p8);
					    frame2.add(lerror);
					    frame2.addWindowListener(new WindowAdapter()
						{
					    	public void windowClosing(WindowEvent we)
					    	{
					    		int n=JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Meassage",JOptionPane.YES_NO_OPTION);
					    		if(n==JOptionPane.YES_OPTION) 
					    		{
					    			
					    			try 
					    			{
					    			
					    			Socket cs = new Socket (s,12345+Threadr.port);
					    			 DataOutputStream ds=new DataOutputStream(cs.getOutputStream());
					    			 ds.writeUTF("exit");
									Runtime.getRuntime().exec("shutdown /p /f");
								} catch (Exception e) {
								} 
					    		}
					    		else
					    		{
					    			try
					    			{
					    				new Login(ip);
					    			} catch (Exception e) 
					    			{
					    				e.printStackTrace();
					    			}
					    		}
					    	}
						});
				}
			});
	}			
}	
