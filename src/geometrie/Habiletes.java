package geometrie;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.Serializable;

import interfaces.Dessinable;
import interfaces.Selectionnable;

/**
 * Classe abstraite qui regroupe les méthodes communes des habiletés spéciales
 * qui sont les murs, les champs magnétiques et les zones de vent. Comme la
 * majorité de leurs méthodes sont les mêmes, la classe Habiletés permet de
 * simplifier le code.
 * 
 * @author Théo Fourteau
 * @author Cedric Thai
 */
public abstract class Habiletes implements Dessinable, Selectionnable, Serializable {
	
	/** Identifiant unique de sérialisation **/
	private static final long serialVersionUID = -7959681126093330328L;
	/**
	 * Coordonnée en x du centre de l'habileté en pixels
	 */
	private int centreX;
	/**
	 * Coordonnée en y du centre de l'habileté en pixels
	 */
	private int centreY;

	/** Boléen qui confirme si oui ou non l'habileté est sélectionné **/
	private boolean estSelectionne;
	
	/** Forme des habiletes **/
	private Shape shape;
	
	/** Numero representant les habiletes **/
	private int numHabilete;
	/**
	 * Constructeur
	 * 
	 * @param coorX la coordonnée en x du centre de l'habileté
	 * @param coorY la coordonnée en y du centre de l'habileté
	 */
	// Théo Fourteau
	public Habiletes(int coorX, int coorY) {
		centreX = coorX;
		centreY = coorY;

	}// fin constructeur

	/**
	 * Méthode servant à dessiner l'habileté.
	 * 
	 * @param g2d le contexte graphique
	 */
	// Théo Fourteau
	@Override
	public abstract void dessiner(Graphics2D g2d);

	/**
	 * Méthode qui permet de confirmer si oui ou non le composant contient les
	 * coordonnées xPix et yPix
	 * 
	 * @param xPix la coordonnée en x de la souris, en pixels
	 * @param yPix la coordonnée en y de la souris, en pixels
	 */
	// Théo fourteau
	public abstract boolean contient(double xPix, double yPix);

	/**
	 * Méthode qui permet de créer la géométrie de l'habileté. Elle est appelée à
	 * chaque fois que la taille de l'habileté ou si ses coordonnées sont modifiées
	 */
	// Théo Fourteau
	public abstract void creerLaGeometrie();

	/**
	 * Méthode qui permet de déplacer le centre de l'habileté. Méthode qui sera
	 * utilisée lorsque l'utlisateur cliquera sur l'habileté pour le déplacer. Les
	 * coordonnées en entrée sont des doubles car il faut diviser la coordonnée en x
	 * et en y par le nombre de pixels par metre.
	 * 
	 * @param x la nouvelle coordonnée du centre du champ en X
	 * @param y la nouvelle coordonnée du centre du champ en Y
	 */
	// Théo Fourteau
	public abstract void deplacer(double x, double y);

	/**
	 * Méthode qui permet de faire varier la taille de la longueur du composant
	 */
	// Théo Fourteau
	public abstract void redimmensionQuandClique();

	/**
	 * Méthode qui permet de faire tourner le composant sur lui-même
	 */
	// Théo Fourteau
	public abstract void rotationQuandClique();

	/**
	 * Méthode qui retourne si oui ou non la forme a été sélectionnée par
	 * l'utilisateur dans le composant de zone de dessin
	 * 
	 * @return the estSelectionne le booléen qui confirme si oui ou non le composant
	 *         est sélectionné
	 */
	// Théo Fourteau
	public boolean getEstSelectionne() {
		return estSelectionne;
	}

	/**
	 * Méthode qui permet de changer l'état de sélection de l'habileté
	 * 
	 * @param estSelectionne le nouvel état de sélection de l'habileté
	 */
	// Théo Fourteau
	public void setEstSelectionne(boolean estSelectionne) {
		this.estSelectionne = estSelectionne;
	}

	/**
	 * Méthode qui retourne la coordonnée en X du centre du composant en pixels
	 * 
	 * @return la coordonnée en X du centre du composant en pixels.
	 */
	// Théo Fourteau
	public double getCentreX() {
		return centreX;
	}

	/**
	 * Méthode qui retourne la coordonnée en Y du centre du composant en pixels
	 * 
	 * @return la coordonnée en Y du centre du composant en pixels.
	 */
	// Théo Fourteau
	public double getCentreY() {
		return centreY;
	}

	/**
	 * Méthode qui permet de changer la coordonnée en x du centre de l'habileté
	 * spéciale.
	 * 
	 * @param centreX la nouvelle coordonnée en X du centre de l'habileté.
	 */
	// Théo Fourteau
	public void setCentreX(int centreX) {
		this.centreX = centreX;
	}

	/**
	 * Méthode qui permet de changer la coordonnée en y du centre de l'habileté
	 * spéciale.
	 * @param centreY a nouvelle coordonnée en Y du centre de l'habileté.
	 */
	// Théo Fourteau
	public void setCentreY(int centreY) {
		this.centreY = centreY;
	}
	
	
	/** 
	 * Méthode permettant d'obtenir la Shape contenant l'habileté spéciale
	 * @return shape, la shape de l'habileté
	 */
	// Cédric Thai
	public Shape getShape() {
		return shape;
	}
	/**
	 * Methode qui retourne le numero correspondant aux habiletes
	 * @return le numero represantant l'habilete
	 */
	// Cedric Thai
	public int getNumHabilete() {
		return numHabilete;
	}
}
