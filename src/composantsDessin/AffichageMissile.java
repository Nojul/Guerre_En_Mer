package composantsDessin;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import geometrie.DessinTypeMissile;

/**
 * Classe d'un panel pour dessiner le missile dans FenetreTerrainJeu
 * 
 * @author Noé Julien
 */
public class AffichageMissile extends JPanel {
	/** Identifiant unique pour la sérialisation **/
	private static final long serialVersionUID = 1L;
	/** Représente le type de dessin pour le missile */
	private DessinTypeMissile dessinTypeMissile;
	/** Représente le nom du missile, par défaut "Basique" */
	private String nomMissile = "Basique";

	/**
	 * Constructeur pour pouvoir dessiner un missile
	 */
	// Noé Julien
	public AffichageMissile() {
		setLayout(null);
		creerObjetsDessinables();
		repaint();
	}

	/**
	 * Creation des divers objets dessinables
	 */
	// Noé Julien
	private void creerObjetsDessinables() {
		dessinTypeMissile = new DessinTypeMissile(13, 0, 90, 30, nomMissile);

	}

	/**
	 * Méthode pour dessiner le missile dans le contexte graphique
	 * 
	 * @param g contexte graphique
	 */
	// Noé Julien
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		dessinTypeMissile.dessiner(g2d);
	}

	/**
	 * Permet de changer le type de missile qui sera dessiné
	 * 
	 * @param nomMissile nom du type de missile
	 */
	// Noé Julien
	public void setNomMissile(String nomMissile) {
		if (nomMissile == "R-360-1") {
			this.nomMissile = "Basique";
		} else {
			this.nomMissile = "b";
		}
		creerObjetsDessinables();
	}

}
