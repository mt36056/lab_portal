package com.server;

import java.awt.Container;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.client.ImageImplement;

public class ServerInterface extends JWindow {

    static boolean isRegistered;
    private static JProgressBar progressBar = new JProgressBar();
    private static ServerInterface execute;
    private static int count;
    private static Timer timer1;

    public ServerInterface() {

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

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                count++;

                progressBar.setValue(count);

                if (count == 65) {
                	runExcute();
                    execute.setVisible(false);
                    timer1.stop();
                }

            }

			private void runExcute()
			{
				new InterFace().start();
        			
			}

           
        };
        timer1 = new Timer(50, al);
        timer1.start();
    }

    public static void main(String[] args) {
        execute = new ServerInterface();
    }
};