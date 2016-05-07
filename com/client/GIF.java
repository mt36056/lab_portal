package com.client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
 
public class GIF extends JPanel {
 
  Image image;
 
  public GIF(String s) 
  {
    image = Toolkit.getDefaultToolkit().createImage(s);
  }
  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (image != null) {
	      g.drawImage(image, 0, 0, this);
	    }
  }
}