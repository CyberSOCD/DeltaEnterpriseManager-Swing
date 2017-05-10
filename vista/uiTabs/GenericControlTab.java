package uiTabs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class GenericControlTab extends JPanel {

	private Image img;

	public GenericControlTab() {

	}
	
	public abstract void loadComponent();  

	public GenericControlTab(Image image) {
		this.img = image;
		Dimension size = new Dimension(this.getWidth(), this.getHeight());
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public GenericControlTab(String img) {
		this(new ImageIcon(img).getImage());
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(img, 0, 0, null);
	}
}
