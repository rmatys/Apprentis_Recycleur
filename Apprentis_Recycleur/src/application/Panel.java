package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import objets.Poubelles;
import objets.Trash;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

public class Panel extends JPanel implements Runnable, Serializable {
	private static final long serialVersionUID = -3422389399040540538L;
	
	private boolean premiereFois = true;
	private boolean enCoursAnim = false;
	private boolean dragging = false;
	private boolean falling = false;
	
	private int iterDepuisChute = 0;
	
	public ArrayList<Poubelles> listPoubelles = new ArrayList<Poubelles>();
	public Trash trashAJeter = new Trash("", Systems.TypeDechet.BIO);

	/**
	 * Create the panel.
	 */
	public Panel() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				dragging = true;
				
				if (!falling) {
					trashAJeter.setPoint(e.getX(), -e.getY() + getHeight());				
					repaint();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				trashAJeter.setPoint(e.getX(), -e.getY() + getHeight());				
				repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (dragging) {
					demarrerAnim();
					dragging = false;
					falling = true;
				}
			}
		});
		setLayout(null);
        setBackground(Color.gray);
	}
	
	private void initialization() {
		premiereFois = false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (premiereFois) {
			initialization();
		}
		
		g2d.translate(0, getHeight());
		
		for (int i = 0; i < listPoubelles.size(); i++) {
			Poubelles poubelle = listPoubelles.get(i);
			Point2D.Double p = trashAJeter.getPoint();
			double d = trashAJeter.getDiametre();
			
			poubelle.dessiner(g2d);
			
			// Caroline Houle professeur en SIM au collège de Maisonneuve
			Image img = OutilsImage.lireImage("bin1.jpeg");
			
			g2d.drawImage(img, null, getFocusCycleRootAncestor());
			
			if (poubelle.getRectangle().contains(p.x, p.y + d, d, d)) {
				//				poubelle.getId();
				trashAJeter.setPoint(getWidth() / 2,  4 * getHeight() / 5);
				arretAnim();
			}
		}
		
		trashAJeter.dessiner(g2d);
		
		g2d.setColor(Color.yellow);
		g2d.drawString("aaaaaaaaaaaaaaaaaaaaaaaa", 50, 50);
	}
	
	@Override
	public void run() {
		while (enCoursAnim) {
			double gravity = 1.7;
			
			Point2D.Double p = trashAJeter.getPoint();
			trashAJeter.setPoint(p.x, p.y - gravity * iterDepuisChute);
			iterDepuisChute += 1;
			
			if (p.y < 0) {
				arretAnim();
			}
			
			repaint();
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void demarrerAnim() {
		if (!enCoursAnim) {
			enCoursAnim = true;
			Thread proc = new Thread(this);
			proc.start();
		}
	}
	
	public void arretAnim() {
		enCoursAnim = false;
		falling = false;
		iterDepuisChute = 0;
	}

}
