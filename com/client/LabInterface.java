package com.client;


import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.NoRouteToHostException;
import java.text.ParseException;

import javax.swing.*;


import com.client.ImageImplement;

public class LabInterface extends JWindow {

    static boolean isRegistered;
    private static JProgressBar progressBar = new JProgressBar();
    private static LabInterface execute;
    private static int count;
    private static Timer timer1;
   static Login execute1;
    public LabInterface() {

        Container container = getContentPane();
        container.setLayout(null);

        JPanel panel = new JPanel(null);
        panel.setBounds(10,10,400,150);
        panel.add( new ImageImplement(new ImageIcon("gehu.JPg").getImage()),"Center");
        container.add(panel);
        progressBar.setMaximum(50);
        progressBar.setBounds(10, 150, 380, 150);
        container.add(progressBar);
        loadProgressBar();
        setSize(400, 170);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadProgressBar() {
        ActionListener al = new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt){
                count++;

                progressBar.setValue(count);

                if (count == 65) {
                	execute.setVisible(false);
                    timer1.stop();
                	try
            		{
            			String s=JOptionPane.showInputDialog(null,"Server IP Address","eg 172.16.48.1");
            			
            			if(s.equals("eg 172.16.48.1")==true || s.equals(""))
            				throw new Exception();
            				
            			else
            			{	
            				new Threadr(s).run();
            				execute1=new Login(s);new IP(s).run();
            				}     
            		}
            		catch(NoRouteToHostException e)
            		{
            			JOptionPane.showMessageDialog(null,"Server Not Responding","error",JOptionPane.ERROR_MESSAGE);
            			try {Runtime.getRuntime().exec("shutdown /p /f");} catch (IOException e1) {} 
            		}
            		catch(IOException e)
            		{
            			JOptionPane.showMessageDialog(null,"IO Problem","Error",JOptionPane.ERROR_MESSAGE);
            			try {Runtime.getRuntime().exec("shutdown /p /f");} catch (IOException e1) {} 
            		} catch (ParseException e) {
            			JOptionPane.showMessageDialog(null,"Please Enter a Valid IP Address","Error",JOptionPane.ERROR_MESSAGE);
            			try {Runtime.getRuntime().exec("shutdown /p /f");} catch (IOException e1) {} 
            		} catch (Exception e) {
            			JOptionPane.showMessageDialog(null,e,"Error",JOptionPane.ERROR_MESSAGE);
            			try {Runtime.getRuntime().exec("shutdown /p /f");} catch (IOException e1) {} 
            		} 
                }
            }
        };
        timer1 = new Timer(50, al);
        timer1.start();
    }
    public static void main(String[] args) {
        execute = new LabInterface();
    }
};