package composantsDessin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

/**
 * Classe qui permet de créer des bulles de dialogue contenant un texte précis.
 * Les bulles sont affichées dans la fenêtre Tutoriel afin de guider
 * l'utilisateur.
 * 
 * @author Théo Fourteau
 */
public class BulleDialogue extends JPanel {
	/** Indice unique de sérialisation **/
	private static final long serialVersionUID = 2759599384280670887L;
	/** Coordonnée du coin en haut à gauche en x **/
	private int coorX;
	/** Coordonnée du coin en haut à gauche en y **/
	private int coorY;
	/** Largeur de la bulle de dialogue en pixels **/
	private int largeur;
	/** Hauteur de la bulle de dialogue en pixels **/
	private int hauteur;
	/** Texte présent dans la bulle de dialogue **/
	private String texteBulle;
	/** Chaine de caractères qui définit l'emplacement de l'encoche de la bulle **/
	private String emplacementEncoche = "";
	/** Point 1 de la bulle **/
	Point2D.Double point1 = new Point2D.Double();
	/** Point 2 de la bulle **/
	Point2D.Double point2 = new Point2D.Double();
	/** Point 3 de la bulle **/
	Point2D.Double point3 = new Point2D.Double();
	/** Path reliant les points de l'encoche **/
	Path2D.Double pathEncoche;

	/**
	 * Constructeur de la bulle de dialogue
	 * 
	 * @param x        la coordonnée du coin en haut à gauche en x
	 * @param y        la coordonnée du coin en haut à gauche en y
	 * @param largeur la largeur de la bulle de dialogue
	 * @param hauteur la hauteur de la bulle de dialogue
	 * @param encoche  la position désirée de l'encoche
	 * @param texte    le texte désiré dans la bulle
	 * 
	 **/
	// Théo Fourteau
	public BulleDialogue(int x, int y, int largeur, int hauteur, String encoche, String texte) {
		coorX = x;
		coorY = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		emplacementEncoche = encoche;
		texteBulle = texte;

	}// fin constructeur

	/**
	 * Permet de dessiner la bulle de dialogue et le texte à l'intérieur.
	 * 
	 * @param g le contexte graphique
	 */
	// Théo Fourteau
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2dPriv = (Graphics2D) g.create(); // Copie du composant graphique.
		g2dPriv.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2dPriv.setBackground(null);

		Ellipse2D.Double bulle = new Ellipse2D.Double(coorX + 20, coorY + 20, largeur, hauteur);

		g2dPriv.setColor(new Color(255, 159, 28));

		switch (emplacementEncoche) {
		case "gauche":

			point1.x = bulle.getCenterX();
			point1.y = bulle.getMinY();

			point2.x = bulle.getCenterX();
			point2.y = bulle.getMaxY();

			point3.x = bulle.getMinX() - 10;
			;
			point3.y = bulle.getCenterY();

			break;

		case "droite":

			point1.x = bulle.getCenterX();
			point1.y = bulle.getMinY();

			point2.x = bulle.getCenterX();
			point2.y = bulle.getMaxY();

			point3.x = bulle.getMaxX() + 10;
			;
			point3.y = bulle.getCenterY();
			break;

		case "bas":

			point1.x = bulle.getMinX();
			point1.y = bulle.getCenterY();

			point2.x = bulle.getMaxX();
			point2.y = bulle.getCenterY();

			point3.x = bulle.getCenterX();
			point3.y = bulle.getMaxY() + 10;
			break;

		case "haut":

			point1.x = bulle.getMinX();
			point1.y = bulle.getCenterY();

			point2.x = bulle.getMaxX();
			point2.y = bulle.getCenterY();

			point3.x = bulle.getCenterX();
			point3.y = bulle.getMinY() - 10;
			break;

		default:
			break;

		}

		pathEncoche = new Path2D.Double();
		pathEncoche.moveTo(point1.getX(), point1.getY());
		pathEncoche.lineTo(point2.getX(), point2.getY());
		pathEncoche.lineTo(point3.getX(), point3.getY());
		pathEncoche.closePath();

		Shape encoche = pathEncoche.createTransformedShape(null);
		g2dPriv.fill(encoche);
		g2dPriv.fill(bulle);

		g2dPriv.setColor(Color.BLACK);
		g2dPriv.setFont(new Font("Graphik", Font.BOLD, 10));

		int x = (int) bulle.getMinX() + 40; // Coordonnée X initiale
		int y = (int) bulle.getMinY() + 30; // Coordonnée Y initiale

		switch (texteBulle) {
		case "bateaux":
			// Première ligne
			g2dPriv.drawString("  Utilise ta souris pour", x, y);

			// Deuxième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("faire glisser les bateaux", x, y);

			// Troisième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("dans la zone des bateaux.", x, y);

			// Quatrième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("", x, y);
			// Cinquième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("Maintiens ta souris", x, y);

			// Sixième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("enfoncée et presse", x, y);

			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("la touche R pour tourner", x, y);
			
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("un bateau", x, y);
			break;

		case "tir":
			// Première ligne
			g2dPriv.drawString(" Presse la touche Espace ", x, y);

			// Deuxième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("pour faire varier", x, y);

			// Troisième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("la vitesse du missile.", x, y);
			break;

		case "graphique":
			// Première ligne
			g2dPriv.drawString("Consulte les données ", x, y);

			// Deuxième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("liées à la ", x, y);

			// Troisième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("trajectoire du missile.", x, y);
			break;

		case "canon":
			// Première ligne
			g2dPriv.drawString("Utilise les touches ", x, y);

			// Deuxième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("des flèches du clavier", x, y);

			// Troisième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("pour tourner le canon.", x, y);
			break;

		case "habiletes":
			// Première ligne
			g2dPriv.drawString(" Ajoute des habiletés ", x, y);

			// Deuxième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("spéciales pour modifier", x, y);

			// Troisième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("la trajectoire du missile.", x, y);
			break;

		case "commandes":
			// Première ligne
			g2dPriv.drawString(" Clique R pour Tourner", x, y);

			// Deuxième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("et G pour aggrandir", x, y);

			// Troisième ligne
			y += g2dPriv.getFontMetrics().getHeight(); // Incrémenter Y pour passer à la ligne suivante
			g2dPriv.drawString("les habiletés spéciales.", x, y);
			break;

		}

	}

}
