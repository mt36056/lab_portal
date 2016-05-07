package com.server;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class InterFace extends Thread
{
static String s;
	public void run() {
		System.out.println("interface");
		try
		{
			final JPasswordField tf;
			JButton submit;
			
			final JDialog d=new JDialog();
			d.setTitle("Login");
			d.setLayout(new FlowLayout());
			d.setSize(215,140);
			d.setLocationRelativeTo(null);
			d.setResizable(false);
			d.add(new JLabel("Enter The Administrator Password"),"Center");
			d.add(tf=new JPasswordField(15),"Center");
			d.add(submit=new JButton("Submit"),"Center");
			submit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					try{
						
					 s=JOptionPane.showInputDialog(null,"Enter MySql Password","MYSQL",JOptionPane.DEFAULT_OPTION);
						Class.forName("com.mysql.jdbc.Driver");
						Connection con=null;
						try{
							
						con=DriverManager.getConnection("jdbc:mysql://localhost:3306/portal","root",s);   
						}
						catch(SQLException e){
						JOptionPane.showMessageDialog(null,"mysql Problem..Please Check Your sql Password or Contect Devlopers..  :(","error",JOptionPane.ERROR_MESSAGE);
						System.exit(0);
						}Statement stmt=con.createStatement();
						ResultSet rs=stmt.executeQuery("select * from admin_pass");
					while(rs.next()){
					if(tf.getText().equals(rs.getString(1)))
					{
						d.dispose();
						new serverFrame();			
						}else
							JOptionPane.showMessageDialog(null, "Password not found","error",JOptionPane.ERROR_MESSAGE);
					} }catch (Exception e){
						
						}
			}
			});
			d.setVisible(true);
			d.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			});Thread.sleep(200);
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		
	}

}
