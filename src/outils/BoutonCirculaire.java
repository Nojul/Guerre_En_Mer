package outils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.Serializable;

import javax.swing.JButton;

import interfaces.Selectionnable;

/** Classe permettant de dessiner un bouton circulaire contenant du texte. 
 * @author Théo Fourteau
 * 
 * **/
public class BoutonCirculaire extends JButton implements Selectionnable, Serializable {

	/** Identifiant unique de sérialisation **/
	private static final long serialVersionUID = 8415755844489781997L;
	/** Booléen qui permet de déterminer si la souris est au dessus du bouton **/
	private boolean mouseOver = false;
	
	

	/**
	 * Constructeur de la classe. Le constructeur contient tous les écouteurs liés à
	 * la souris.
	 * 
	 * @param texte          le texte que l'on souhaite mettre dans le bouton
	 */
	// Théo Fourteau
	public BoutonCirculaire(String texte) {
		super(texte);
		setOpaque(false);
		setFocusPainted(false);
		setBorderPainted(false);

		MouseAdapter mouseListener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (contient(e.getX(), e.getY())) {
					
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mouseOver = false;
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseOver = contient(e.getX(), e.getY());
				repaint();
			}
		};

		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}

	/**
	 * Méthode qui permet de créer la géométrie du bouton et de le dessiner. La
	 * méthode dessine un cercle et le texte centré au milieu du bouton.
	 * 
	 * @param g2d le contexte graphique.
	 */
	// Théo Fourteau
	@Override
	public void paintComponent(Graphics g2d) {
		Graphics2D g2dPriv = (Graphics2D) g2d.create();
		g2dPriv.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2dPriv.setStroke(new BasicStroke(1));

		int diametre = getDiametre();
		int rayon = diametre / 2;

		g2dPriv.setColor(new Color(122, 142, 155));

		g2dPriv.fillOval(getWidth() / 2 - rayon, getHeight() / 2 - rayon, diametre, diametre); 

		if (mouseOver) {
			g2dPriv.setColor(new Color(19, 44, 58));
		} else {
			g2dPriv.setColor(new Color(226, 189, 121));
		}
		
		g2dPriv.drawOval(getWidth() / 2 - rayon, getHeight() / 2 - rayon, diametre, diametre);

		g2dPriv.setColor(Color.BLACK);
		g2dPriv.setFont(new Font("Graphik", Font.BOLD, 15));
		FontMetrics metrics = g2dPriv.getFontMetrics(new Font("Graphik", Font.BOLD, 15));
		int stringWidth = metrics.stringWidth(getText());
		int stringHeight = metrics.getHeight();
		g2dPriv.drawString(getText(), getWidth() / 2 - stringWidth / 2, getHeight() / 2 + stringHeight / 4);

	}

	/**
	 * Méthode qui retourne le booléen qui confirme si oui ou non le pointeur de la
	 * souris est à l'intérieur du cercle du bouton.
	 * @param xPix la coordonnée en X de la souris, en pixels
	 * @param yPix la coordonnée en Y de la souris, en pixels
	 * @return si oui ou non le pointeur de la souris se trouve à l'intérieur du
	 *         carré contenant le bouton
	 */
	@Override
	// Théo Fourteau
	public boolean contient(double xPix, double yPix) {
		int rayon = getDiametre() / 2;
		return Point2D.distance(xPix, yPix, getWidth() / 2, getHeight() / 2) < rayon;
	}

	/**
	 * Méthode permettant d'obtenir le diametre du cercle à partir de la taille du
	 * composant.
	 * 
	 * @return diametre le diamètre du bouton circulaire
	 */
	// Théo Fourteau
	private int getDiametre() {
		int diametre = Math.min(getWidth(), getHeight());
		return diametre;
	}

}