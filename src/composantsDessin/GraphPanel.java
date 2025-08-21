package composantsDessin;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Classe qui permet d'afficher un graphique de vitesse ou de hauteur du missile
 * en fonction du temps (dans l'animation). Le type de graphique est déterminé
 * dans le constructeur.
 * 
 * @author Théo Fourteau
 */
public class GraphPanel extends JPanel {

	/** Identifiant pour la sérialisation */
	private static final long serialVersionUID = 1L;

	/** Liste contenant les points de la courbe **/
	private List<Point2D.Double> pointsGraph = new ArrayList<>();
	/**
	 * Chaine qui permet de déterminer le type de graphique pour l'ajout d'un
	 * nouveau point
	 **/
	private String typeGraphique;

	/** Police pour les graduations du graphique **/
	Font policeGraphique = new Font("Graphik", Font.PLAIN, 8);
	/** Police pour les titres des axes des graphiques **/
	Font policeAxes = new Font("Graphik", Font.PLAIN, 12);
	/** Valeur maximale en Y (Vitesse ou hauteur) **/
	private double maxY;
	/** Valeur maximale en X (Temps en s) **/
	private double maxX;
	/** Écart entre deux lignes horizontales en pixels **/
	private int deltaY;
	/** Écart entre deux lignes verticales en pixels **/
	private int deltaX;
	
	/** Taille des flèches des axes en pixels **/
	private final int TAILLE_FLECHE = 5; 
	

	/**
	 * Constructeur du graphique
	 * 
	 * @param typeGraphique chaine de caractère qui définit le type de graphique que
	 *                      l'on veut générer.
	 */
	// Théo Fourteau
	public GraphPanel(String typeGraphique) {
		this.typeGraphique = typeGraphique;
		this.setBackground(Color.BLACK);

	} // fin constructeur

	/**
	 * Dessin du graphique (axes et points)
	 * 
	 * @param g le contexte graphique.
	 */
	// Théo Fourteau
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Graphics2D g2dPriv = (Graphics2D) g2d.create(); // Copie du composant graphique.
		g2dPriv.setFont(policeGraphique);

		// Couleur de la grille
		g2dPriv.setColor(new Color(0, 40, 0));

		maxX = 2 * InfoGuidageMissile.getTempsVol();

		if (typeGraphique == "vitesse") {
			maxY = 2 * (InfoGuidageMissile.getVitesseI());
			;
		} else {
			maxY = (InfoGuidageMissile.getHauteurMissile());

		}

		// Lignes horizontales
		deltaY = (int) ((getHeight() - 20) / (maxY)); // Espacement vertical entre les lignes
		System.out.println("delta Y:" + deltaY);
		
		if(deltaY == 0) { // Empêche que les lignes se superposent.
			deltaY = 1;
		}
		for (int i = (int) maxY; i >= 0; i--) {
			int y = getHeight() - i * deltaY - 20;

			
				if (i % 5 == 0) {
					g2dPriv.drawLine(0, y, getWidth(), y); // Dessiner une ligne horizontale
				}
		}
		
		
		// Lignes verticales
		deltaX = (int) ((getWidth() - 20) / (maxX)); // Espacement horizontal entre les lignes
		for (int i = 0; i <= deltaX; i++) {
			int x = i * deltaX + 20;
			
			
			g2dPriv.drawLine(x, 0, x, getHeight()); // Dessiner une ligne verticale
			
		}

		g2dPriv.setColor(new Color(20, 76, 43));
		
		// Dessiner la flèche pour l'axe horizontal (X)
		int flecheY = this.getHeight() - 20;
		int debutX = 0; // Position horizontale de début de l'axe horizontal
		int finX = getWidth(); // Position horizontale de fin de l'axe horizontal
		g2dPriv.drawLine(debutX, flecheY, finX, flecheY); // Dessiner l'axe horizontal

		// Dessiner la tête de la flèche pour l'axe horizontal
		Polygon teteFlecheX = new Polygon();
		teteFlecheX.addPoint(finX - TAILLE_FLECHE, flecheY - TAILLE_FLECHE);
		teteFlecheX.addPoint(finX, flecheY);
		teteFlecheX.addPoint(finX - TAILLE_FLECHE, flecheY + TAILLE_FLECHE);
		g2dPriv.fillPolygon(teteFlecheX);

		// Dessiner les graduations axe horizontal
		for (int i = 1; i <= maxX; i++) {
			int x = i * deltaX + 20;
			g2dPriv.setColor(new Color(1, 55, 17));
			g2dPriv.drawLine(x, flecheY - 5, x, flecheY + 5);

		}

		g2dPriv.setColor(new Color(20, 76, 43));

		// Dessiner la flèche pour l'axe vertical (Y)
		int flecheX = 20; // Position horizontale de la flèche (décalée de 20 pixels vers la droite)
		int debutY = 0; // Position verticale de début de l'axe vertical
		int finY = getHeight(); // Position verticale de fin de l'axe vertical
		g2dPriv.drawLine(flecheX, debutY, flecheX, finY); // Dessiner l'axe vertical

		// Dessiner la tête de la flèche pour l'axe vertical
		Polygon teteFlecheY = new Polygon();
		teteFlecheY.addPoint(flecheX - TAILLE_FLECHE, debutY + TAILLE_FLECHE);
		teteFlecheY.addPoint(flecheX, debutY);
		teteFlecheY.addPoint(flecheX + TAILLE_FLECHE, debutY + TAILLE_FLECHE);
		g2dPriv.fillPolygon(teteFlecheY);

		// Dessiner les graduations axe vertical
		for (int i = (int) maxY; i >= 0; i--) {
			int y = getHeight() - i * deltaY - 20;
			g2dPriv.setColor(new Color(1, 55, 17));
  
			if (i % 10 == 0) {
			g2dPriv.drawLine(flecheX - 5, y, flecheX + 5, y); // Dessiner une ligne horizontale
			}
		}
		FontMetrics fontMetrics = g2dPriv.getFontMetrics();

		g2dPriv.setColor(new Color(50, 106, 73));

		// Nombre maximum de graduations à afficher en fonction du nombre total de
		// lignes
		int maxGraduationsAfficheesX = 10;

		// Calculer le pas entre chaque graduation en fonction du nombre total de
		// graduations
		int pasGraduationX = (int) Math.ceil(maxX / maxGraduationsAfficheesX);

		// Lignes verticales
		for (int i = 1; i <= maxX + 1; i++) {
			int x = i * deltaX + 20; // Position horizontale de la graduation

			if (i % pasGraduationX == 0) { // Afficher une graduation uniquement si elle correspond au pas de graduation
				double valeurX = i; // Calculer la valeur correspondante
				String texteX = String.format("%.0f", valeurX); // Formater le texte avec 0 décimale
				g2dPriv.drawString(texteX, x - texteX.length() / 2, flecheY + 10); // Dessiner le texte centré à gauche
																					// de la graduation
			}
		}

		// Nombre maximum de graduations à afficher en fonction du nombre total de
		// lignes
		int maxGraduationsAfficheesY = 10;

		// Calculer le pas entre chaque graduation en fonction du nombre total de
		// graduations
		int pasGraduationY = (int) Math.ceil(maxY / maxGraduationsAfficheesY);

		// Lignes horizontales
		for (int i = (int) maxY; i >= 0; i--) {
			int y = getHeight() - i * deltaY - 20; // Position verticale de la graduation (inversée)

			if (i % pasGraduationY == 0) { // Afficher une graduation uniquement si elle correspond au pas de graduation
				double valeurY = i; // Calculer la valeur correspondante
				String texteY = String.format("%.0f", valeurY); // Formater le texte avec 0 décimale
				g2dPriv.drawString(texteY, 2, y + fontMetrics.getHeight() / 4); // Dessiner le texte centré à gauche de
																				// la graduation
			}
		}

		// TITRES DES AXES
		g2dPriv.setFont(policeAxes);
		FontMetrics fontMetrics2 = g2dPriv.getFontMetrics();

		// Titre de l'axe horizontal
		String titreX = "Temps (s)";
		int titreXLong = fontMetrics2.stringWidth(titreX);
		g2dPriv.drawString(titreX, getWidth() - titreXLong, flecheY - 10);

		// titre de l'axe vertical
		String titreY;
		if (typeGraphique == "vitesse") {
			titreY = "Module de la vitesse (m/s)";
		} else {
			titreY = "Hauteur (m)";
		}

		g2dPriv.drawString(titreY, flecheX + 10, 10);

		// Décalage du g2d pour que le point (0,0) du graphique soit le point
		// d'intersection entre les deux flèches.
		g2dPriv.translate(20, this.getHeight() - 20);

		g2dPriv.setColor(new Color(5, 215, 27));

		for (int i = 0; i < pointsGraph.size(); i++) {
			int x = (int) Math.round(pointsGraph.get(i).getX() * deltaX);
			int y = (int) Math.round(-pointsGraph.get(i).getY() * deltaY);

			if (i == 0) {
				g2dPriv.drawLine(0, 0, 0, 0);
			} else {
				int prevX = (int) Math.round(pointsGraph.get(i - 1).getX() * deltaX);
				int prevY = (int) Math.round(-pointsGraph.get(i - 1).getY() * deltaY);
				g2dPriv.drawLine(prevX, prevY,  x, y);
			}
		}

	}

	/**
	 * Méthode qui crée un nouveau point pour un graphique de vitesse ou de hauteur
	 * en fonction du temps et l'ajoute à la liste des points
	 * 
	 * @param temps   le temps écoulé (valeur en x) en secondes
	 * @param hauteur la hauteur en mètres du missile
	 * @param vitesse le module de la vitesse du missile en m/s (valeur en y)
	 */
	// Théo Fourteau
	public void nouvPoint(double temps, double hauteur, double vitesse) {
		if (hauteur > 0) { // Si le missile n'a pas atteri
			Point2D.Double pointTemp;
			if (typeGraphique.equals("vitesse")) { // Si c'est un graphique de vitesse

				pointTemp = new Point2D.Double(temps, vitesse);

			} else { // Si c'est un graphique de hauteur
				pointTemp = new Point2D.Double(temps, hauteur);
			}
			pointsGraph.add(pointTemp);
			repaint();

		}
	}

	/**
	 * Méthode qui permet de supprimer la courbe présentement dessinée sur le
	 * graphique.
	 */
	// Théo Fourteau
	public void supprimerCourbe() {
		pointsGraph.clear();
		repaint();
	}
}
