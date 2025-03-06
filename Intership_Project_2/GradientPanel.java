package Intership_Project_2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.GradientPaint;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GradientPanel extends JPanel{

	private Image backgroundImage ;
	
	 public GradientPanel(String imagePath) {
	        setOpaque(false);
	        URL imageURL = getClass().getResource(imagePath);
	        if (imageURL != null) {
	            backgroundImage = new ImageIcon(imageURL).getImage();
	        } else {
	            System.err.println("Image Not Found");
	        }
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        
	        if (backgroundImage != null) {
	            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	        }
	        
	        int w = getWidth();
	        int h = getHeight();
	        Color color1 = new Color(76, 161, 175, 200);
	        Color color2 = new Color(196, 224, 229, 200);
	        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
	        g2d.setPaint(gp);
	        g2d.fillRect(0, 0, w, h);
	    }
}
