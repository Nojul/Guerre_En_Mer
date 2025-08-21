package interfaces;

/**
 * Interface pour les objets interactifs. Elle oblige ces objets à implémenter
 * la manière dont cet objet détermine si une certaine coordonnée (px) est
 * contenue dans cet objet.
 *
 * @author Inconnu
 */

public interface Selectionnable {

	/**
	 * Retourne vrai si le point passé en paramètre fait partie de l'objet
	 * dessinable sur lequel cette méthode sera appelée
	 * 
	 * @param xPix abscisse du point (px)
	 * @param yPix ordonnée du point (px)
	 * @return l'état d'appartenance du point dans l'objet
	 */
	public boolean contient(double xPix, double yPix);

}
