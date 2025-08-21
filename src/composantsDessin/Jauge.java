package composantsDessin;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import geometrie.Missile;

/**
 * Classe qui permet de dessiner une jauge de puissance visible dans la fenêtre
 * Terrain de jeu de l'application et qui permet donc à l'utilisateur de
 * visualiser la puissance (vitesse initale) du missile avant chaque tir.
 * 
 * @author Théo Fourteau
 */
public class Jauge extends JPanel {
	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;

	// Constantes

	/**
	 * Coefficient maximal par lequel la hauteur de la jauge est multipliée pour
	 * obtenir la hauteur du rectangle blanc couvrant le gradient.
	 */
	private final double COEFF_MAX = 1;

	/**
	 * Vitesse minimale en m/s que l'utilisateur peut choisir pour tirer son missile
	 **/
	private final double VITESSE_MIN = 10;

	/**
	 * Vitesse maximale en m/s que l'utilisateur peut choisir pour tirer son missile
	 **/
	private final double VITESSE_MAX = 50;

	/**
	 * Variation du coefficient quand la touche espace est pressée.
	 */
	private final double VAR_COEFF = 0.025;

	// Variables

	/**
	 * Vitesse initiale du  missile au moment du tir
	 */
	private double vitesseInitiale = 30;

	/**
	 * Variable qui permet de déterminer si la touche espace est pressée (et pas
	 * relachée ) ou non.
	 */
	private boolean espacePresse = false;

	/**
	 * Variable qui permet de déterminer si la taille du rectangle augmente ou
	 * diminue (la jauge semble plus grande ou plus petite). Elle est initialisée à
	 * false car quand on commence, quand on appuie sur espace, le rectangleCache
	 * semble aller vers le haut.
	 */
	private boolean directionBas = false;

	/**
	 * Coefficient initial par lequel la hauteur de la jauge est multipliée pour
	 * obtenir la hauteur du rectangle blanc couvrant le gradient. Est initialisé à
	 * sa valeur maximale.
	 */
	private double coefficient = COEFF_MAX;

	/**
	 * Rectangle de hauteur prédéfinie qui est rempli par un radient de vert à rouge
	 * pour visualiser la vitesse initiale
	 */
	private Rectangle2D.Double rectangleJauge;
	/**
	 * Rectangle de hauteur variable qui est de la même couleur que l'arriere plan
	 * de l'application et qui cache une partie du rectangleJauge selon le temps où
	 * la touche espace est pressée.
	 */
	private Rectangle2D.Double rectangleCache;

	/**
	 * Coordonnée initiale en x des deux rectangles qui forment le composant.
	 */
	private int xInitial = 0;
	/**
	 * Coordonnée initiale en y des deux rectangles qui forment le composant.
	 */
	private int yInitial = 0;

	// Couleurs personnalisées pour la jauge
	/**
	 * Couleur rouge personnalisée pour la jauge
	 */
	private Color rouge = new Color(204, 0, 0); // définir les couleurs
	/**
	 * Couleur verte personnalisée pour la jauge
	 */
	private Color vert = new Color(106, 168, 79);

	/**
	 * Constructeur de la classe Jauge
	 */
	// Théo Fourteau
	public Jauge() {
		Missile.setVitesse0(vitesseInitiale);
		coefficient = COEFF_MAX/2;
	} // Fin constructeur

	/**
	 * Permet de dessiner la jauge avec un gradient du vert au rouge dont les
	 * dimmensions (semblent) changer en fonction de si la touche espace est
	 * pressée.
	 * 
	 * @param g le contexte graphique
	 */
	// Théo Fourteau
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2dPriv = (Graphics2D) g.create(); // Copie du composant graphique.
		g2dPriv.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		GradientPaint gp = new GradientPaint(this.getWidth() / 2, 0, rouge, this.getWidth() / 2, this.getHeight(),
				vert);

		rectangleJauge = new Rectangle2D.Double(xInitial, yInitial, this.getWidth(), this.getHeight());

		rectangleCache = new Rectangle2D.Double(xInitial, yInitial, this.getWidth(), this.getHeight() * coefficient);

		g2dPriv.setPaint(gp);

		g2dPriv.fill(rectangleJauge);

		g2dPriv.setColor(Color.WHITE);
		g2dPriv.fill(rectangleCache);

	}

	/**
	 * Méthode qui permet de retourner la valeur de la variable booléenne
	 * espacePresse
	 * 
	 * @return espacePresse la valeur de variable espacePresse
	 */
	// Théo Fourteau
	public boolean isEspacePresse() {
		return espacePresse;
	}

	/**
	 * Méthode qui permet de changer la taille du rectangle couvrant le gradient.
	 * Cela semble donc modifier sa taille. Quand le maximum ou le minimum sont
	 * atteints, la progression s'inverse. La méthode redessine aussi la jauge à
	 * chaque fois que la touche espace est pressée.
	 * 
	 * @param espacePresse la nouvelle valeur d'espacePresse
	 */
	// Théo Fourteau
	public void setEspacePresse(boolean espacePresse) {
		this.espacePresse = espacePresse;
		if (espacePresse) {

			if (coefficient <= 0 || coefficient >= COEFF_MAX) {
				directionBas = !directionBas; // Inverser la direction
			}

			if (directionBas) {
				coefficient -= VAR_COEFF;

			} else {
				coefficient += VAR_COEFF;
			}

			//vitesseInitiale = (COEFF_MAX - coefficient) * VITESSE_MAX + VITESSE_MIN;
			vitesseInitiale = (COEFF_MAX-coefficient)*(VITESSE_MAX - VITESSE_MIN) + VITESSE_MIN ;
			Missile.setVitesse0(vitesseInitiale);
			System.out.println("" + vitesseInitiale);

			repaint();
			
			InfoGuidageMissile.changeVitesse(vitesseInitiale);

		}
	}

	/**
	 * Méthode qui permet d'obtenir la vitesse initiale du missile en m/s
	 * 
	 * @return vitesseInitiale la vitesse initiale du missile en m/s
	 */
	// Théo Fourteau
	public double getVitesseInitiale() {
		return vitesseInitiale;
	}

	/**
	 * Méthode qui permet de modifier la vitesse initiale du missile
	 * 
	 * @param nouvVitesseInitiale la nouvelle vitesse initale du missile en m/s
	 */
	// Théo Fourteau
	public void setVitesseInitiale(double nouvVitesseInitiale) {
		this.vitesseInitiale = nouvVitesseInitiale;
		repaint();
		InfoGuidageMissile.changeVitesse(vitesseInitiale);
	}
	
	/** Méthode qui permet de remettre la jauge à la moitié de sa capacité **/
	// Théo Fourteau
	public void remettreA0() {
		coefficient = COEFF_MAX/2;
		vitesseInitiale = 30;
		repaint();
		InfoGuidageMissile.changeVitesse(30);
		
	}

}

