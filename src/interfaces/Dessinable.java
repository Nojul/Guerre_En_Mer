package interfaces;

import java.awt.Graphics2D;

/**
 * Interface pour les objets qui ne sont pas dessinés directement dans la classe
 * du composant d'animation ou de dessin. Elle définit la méthode que ces objets
 * doit implémenter afin de se dessiner lorsqu'appellée dans le composant de
 * d'illustration principal.
 * 
 * @author Inconnu
 */

public interface Dessinable {

	/**
	 * Dessine les formes constituant l'objet
	 * 
	 * @param g2d contexte graphique du composant sur lequel dessiner
	 */
	public void dessiner(Graphics2D g2d);
}
