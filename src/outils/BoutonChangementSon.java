package outils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.Serializable;

import javax.swing.JButton;

import interfaces.Selectionnable;

/**
 * Classe permettant de dessiner un icone de haut-parleur dans un Jpanel.
 * L'image dessinée dépend de l'état du son.
 * 
 * @author Théo Fourteau
 * 
 **/
public class BoutonChangementSon extends JButton implements Selectionnable, Serializable {
	/** Identifiant unique de sérialisation **/
	private static final long serialVersionUID = 8415755844489781997L;
	/** Booléen qui détermine si oui ou non le son est allumé **/
	private static boolean sonAllume = false;

	/** Image qui s'affiche sur le panel quand le son est allumé **/
	private Image imageSonAllume;
	/** Image qui s'affiche sur le panel quand le son est éteint **/
	private Image imageSonEteint;

	/**
	 * Constructeur de la classe. Le constructeur contient tous les écouteurs liés à
	 * la souris et initialise les images des icônes.
	 */
	// Théo Fourteau
	public BoutonChangementSon() {

		imageSonAllume = OutilsImage.lireImage("IconeSonAlume.png");
		imageSonEteint = OutilsImage.lireImage("IconeSonEteint.png");

		setOpaque(false);
		setFocusPainted(false);
		setBorderPainted(false);

		MouseAdapter mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (contient(e.getX(), e.getY())) {
				
					sonAllume = !sonAllume;
					AjoutSon.checkVolume();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				repaint();
			}

		};

		addMouseListener(mouseListener);
		
	}

	/**
	 * Méthode qui permet de créer la géométrie du bouton et de le dessiner. La
	 * méthode affiche l'icone du son qui varie selon si le son est activé ou non
	 * 
	 * @param g2d le contexte graphique.
	 */
	// Théo Fourteau
	@Override
	public void paintComponent(Graphics g2d) {
		Graphics2D g2dPriv = (Graphics2D) g2d.create();
		g2dPriv.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (sonAllume) {
			g2d.drawImage(imageSonAllume, 0, 0, getWidth(), getHeight(), this);
		} else {
			g2d.drawImage(imageSonEteint, 0, 0, getWidth(), getHeight(), this);
		}
	}

	/**
	 * Méthode qui retourne le booléen qui confirme si oui ou non le pointeur de la
	 * souris est à l'intérieur du panel du bouton.
	 * 
	 * @param xPix la coordonnée en X de la souris, en pixels
	 * @param yPix la coordonnée en Y de la souris, en pixels
	 * @return si oui ou non le pointeur de la souris se trouve à l'intérieur du
	 *         carré contenant le bouton
	 */
	@Override
	// Théo Fourteau
	public boolean contient(double xPix, double yPix) {
		return Point2D.distance(xPix, yPix, getWidth() / 2, getHeight() / 2) < getWidth()/2;
	}

	/**
	 * Méthode qui retourne si oui ou non le son du jeu est allumé
	 * 
	 * @return sonAllume le booleen qui détermine si oui ou non le son du jeu esta
	 *         activé
	 * 
	 */
	// Théo Fourteau
	public static boolean isSonAllume() {
		return sonAllume;
	}

}