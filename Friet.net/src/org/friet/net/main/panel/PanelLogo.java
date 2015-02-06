package org.friet.net.main.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelLogo extends JPanel {
	
	public BufferedImage logo;
	
	public PanelLogo() {
		try {
			logo = ImageIO.read(new File("src/res/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logo, 0, 0, 200, 200, null);
	}
}
