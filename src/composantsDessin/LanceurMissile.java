package composantsDessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import geometrie.Missile;

/**
 * Classe qui represente le lanceur de missile dont l'utilisateur peut modifier
 * l'orientation verticale, horizontale ainsi que la vitesse initale que le
 * missile aura. Ces paramètres sont modifiés à l'aide des touches du clavier.
 * 
 * @author Theo Fourteau
 * @author Gabriel Maurice
 */
public class LanceurMissile extends JPanel {
	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;

	// Constantes
	/** Angle maximal que le canon peut avoir pour effectuer son tir **/
	private final int ANGLE_VERTICAL_MAX = 70;
	/** Angle minimal que le canon peut avoir pour effectuer son tir **/
	private final int ANGLE_VERTICAL_MIN = 15;
	/** Orientation maximale que le canon peut avoir pour effectuer son tir **/
	private final int ANGLE_HORIZONTAL_MAX = 85;
	/** Orientation minimale que le canon peut avoir pour effectuer son tir **/
	private final int ANGLE_HORIZONTAL_MIN = -85;

	/**
	 * Longueur maximale que le canon peut atteindre. Elle est définie quand le
	 * canon est dessiné pour la première fois.
	 */
	private int longueurCanonMax;

	// Formes de construction du canon

	/** Ellipse formant la base du canon **/
	private Ellipse2D baseCirculaire;
	/** Ellipse couvrant le début de chaque canon à titre visuellement cohérent **/
	private Ellipse2D baseCanons;

	/** Rectangle représentant le canon du centre **/
	private Rectangle2D canonCentre;

	/**
	 * Ellipse permettant de donner un semblant de profondeur au bout du canon du
	 * centre
	 **/
	private Ellipse2D boutCanonCentre;

	// Variables
	/**
	 * Variable entière représentant l'orientation verticale du canon. Sa valeur est
	 * modifiable avec les touches des flèches verticales du clavier ou les touches
	 * w et s selon le mode sélectionné.
	 */
	private int angleVertical = 15;
	/**
	 * Variable entière représentant l'orientation verticale du canon. Sa valeur est
	 * modifiable avec les touches des flèches horizontales du clavier ou les
	 * touches a et d selon le mode sélectionné.
	 */
	private int angleHorizontal = 0;

	/** Variable entière représentant la largeur du composant en pixels **/
	private int largeurComposant;
	/** Variable entière représentant la hauteur du composant en pixels **/
	private int hauteurComposant;

	/**
	 * Variable entière représentant la longueur du rayon de l'ellipse de la base
	 * circulaire du canon
	 **/
	private int rayonBaseCirculaire;

	/**
	 * Variable entière représentant la longueur du rayon de l'ellipse de la base du
	 * canon
	 **/
	private int rayonBaseCanon;

	/**
	 * variable entière représentant la longueur du rectangle formant le canon
	 * central du lance-missile
	 **/
	private int longueurRecCanonC;

	/**
	 * variable entière représentant la largeur des rectangles formant les 3 canons
	 * du lance-missile
	 **/
	private int largeurRecCanon;

	/**
	 * Variable entière représentant le rayon vertical de l'ellipse qui illustre le
	 * bout du canon
	 */
	private int hauteurBoutCanon = 0;

	/**
	 * Variable représentant la position en X du coin en haut à gauche de l'ellipse
	 * servant de base au canon
	 **/
	private int posXInit;

	/**
	 * Variable représentant la position en Y du coin en haut à gauche de l'ellipse
	 * servant de base au canon
	 **/
	private int posYInit;

	// Variables de couleur des composants de dessin
	/**
	 * Couleur personnalisée de gris foncé pour remplir certaines formes du
	 * lance-missile
	 */
	private Color grisFonce = new Color(98, 109, 101);
	/**
	 * Couleur personnalisée de gris clair pour remplir certaines formes du
	 * lance-missile
	 */
	private Color grisClair = new Color(125, 132, 124);
	/**
	 * Couleur personnalisée de gris très foncé pour faire le contour des formes.
	 */
	private Color couleurContour = new Color(69, 69, 69);

	/**
	 * Booléen qui confirme si oui ou non il s'agit de la première fois que le canon
	 * est dessiné.
	 */
	private boolean premiereFois = true;

	/**
	 * Constructeur de la classe LanceurMissile
	 **/
	// Theo Fourteau
	public LanceurMissile() {

		this.setOpaque(false);

		Missile.setAngleHor(angleHorizontal);
		Missile.setAngleVert(angleVertical);

	}// fin du constructeur

	/**
	 * Permet de dessiner la géométrie du canon
	 * 
	 * @param g le contexte graphique
	 */
	// Théo Fourteau
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Graphics2D g2dPriv = (Graphics2D) g2d.create(); // Copie du composant graphique.

		// Définition des paramètres
		if (premiereFois) {
			rayonBaseCirculaire = this.getWidth() / 6;
			rayonBaseCanon = rayonBaseCirculaire / 3;
			largeurRecCanon = (int) (rayonBaseCirculaire / 1.5);

			longueurCanonMax = this.getHeight() / 2;
			longueurRecCanonC = (int) (longueurCanonMax * Math.cos(Math.toRadians(angleVertical)));
			hauteurBoutCanon = (int) (largeurRecCanon * Math.sin(Math.toRadians(15)));
			

		}
		premiereFois = false;

		double diametreBaseCirculaire = 2 * rayonBaseCirculaire;

		posXInit = this.getWidth() / 2 - rayonBaseCirculaire;
		posYInit = this.getHeight() / 2;

		// Création de la géométrie
		baseCirculaire = new Ellipse2D.Double(posXInit, posYInit, diametreBaseCirculaire, diametreBaseCirculaire);
		baseCanons = new Ellipse2D.Double(posXInit, posYInit + 2 * rayonBaseCanon, diametreBaseCirculaire,
				2 * rayonBaseCanon);

		canonCentre = new Rectangle2D.Double(baseCirculaire.getCenterX() - largeurRecCanon / 2,
				posYInit + 3 * rayonBaseCanon - longueurRecCanonC, largeurRecCanon, longueurRecCanonC);

		boutCanonCentre = new Ellipse2D.Double(canonCentre.getMinX(), canonCentre.getMinY() - hauteurBoutCanon / 2,
				largeurRecCanon, hauteurBoutCanon);

		// Rotation horizontale
		AffineTransform rotationHorizontale = AffineTransform.getRotateInstance(Math.toRadians(angleHorizontal),
				baseCirculaire.getCenterX(), baseCirculaire.getCenterY());

		Shape baseCanonsS = rotationHorizontale.createTransformedShape(baseCanons);
		Shape canonCentreS = rotationHorizontale.createTransformedShape(canonCentre);
		Shape boutCanonCentreS = rotationHorizontale.createTransformedShape(boutCanonCentre);

		// Dessin de la géométrie du canon (Remplissage et contour, dans un ordre précis

		g2dPriv.setColor(grisFonce);
		g2dPriv.fill(baseCirculaire);

		g2dPriv.setColor(couleurContour);
		g2dPriv.draw(baseCirculaire);

		g2dPriv.setColor(grisClair);

		g2dPriv.fill(canonCentreS);

		g2dPriv.setColor(couleurContour);

		g2dPriv.draw(canonCentreS);

		g2dPriv.setColor(grisClair);
		g2dPriv.fill(baseCanonsS);

		g2dPriv.setColor(couleurContour);
		g2dPriv.draw(baseCanonsS);

		g2dPriv.setColor(grisFonce);

		g2dPriv.fill(boutCanonCentreS);

		g2dPriv.setColor(couleurContour);

		g2dPriv.draw(boutCanonCentreS);

	}

	/**
	 * Méthode permettant d'obtenir la largeur du composant
	 * 
	 * @return largeurComposant la largeur du composant en pixels
	 **/
	// Theo Fourteau
	public int getLargeurComposant() {
		return largeurComposant;
	}

	/**
	 * Méthode permettant de modifier la largeur du composant
	 * 
	 * @param largeurComposant la nouvelle largeur du composant en pixels
	 */
	// Theo Fourteau
	public void setLargeurComposant(int largeurComposant) {
		this.largeurComposant = largeurComposant;
	}

	/**
	 * Méthode permettant d'obtenir la hauteur du composant
	 * 
	 * @return hauteurComposant la hauteur du composant en pixels
	 **/
	// Theo Fourteau
	public int getHauteurComposant() {
		return hauteurComposant;
	}

	/**
	 * Méthode permettant de modifier la hauteur du composant
	 * 
	 * @param hauteurComposant la nouvelle hauteur du composant en pixels
	 */
	// Theo Fourteau
	public void setHauteurComposant(int hauteurComposant) {
		this.hauteurComposant = hauteurComposant;
	}

	/**
	 * Méthode qui permet de modifier la valeur de l'angle vertical change (+5°) et
	 * la perspective du canon aussi. La méthode redessine le canon et donne une
	 * nouvelle orientation au missile.
	 **/
	// Théo Fourteau
	public void flecheBasCliquee() {
		if (angleVertical < ANGLE_VERTICAL_MAX) {
			angleVertical += 5;

			longueurRecCanonC = (int) (longueurCanonMax * Math.cos(Math.toRadians(angleVertical)));

			// Calcul de la hauteur du bout du canon en fonction de la longueur actuelle du
			// canon
			hauteurBoutCanon = (int) (largeurRecCanon * Math.sin(Math.toRadians(angleVertical)));

			this.repaint();
			
			InfoGuidageMissile.changeAngle(angleVertical);

			Missile.setAngleVert(angleVertical);
			
		}
	}

	/**
	 * Méthode qui permet de modifier la valeur de l'angle vertical change (-5°)et
	 * la perspective du canon aussi. La méthode redessine le canon et donne une
	 * nouvelle orientation au missile.
	 **/
	// Théo Fourteau
	public void flecheHautCliquee() {
		if (angleVertical > ANGLE_VERTICAL_MIN) {
			angleVertical -= 5;

			longueurRecCanonC = (int) (longueurCanonMax * Math.cos(Math.toRadians(angleVertical)));

			// Calcul de la hauteur du bout du canon en fonction de la longueur actuelle du
			// canon
			hauteurBoutCanon = (int) (largeurRecCanon * Math.sin(Math.toRadians(angleVertical)));

			this.repaint();
			
			InfoGuidageMissile.changeAngle(angleVertical);

			Missile.setAngleVert(angleVertical);
		}
	}

	/**
	 * Méthode qui permet d'augmenter l'angle horizontal de 5° et de redessiner le
	 * canon.
	 **/
	// Théo Fourteau
	public void flecheDroiteCliquee() {
		if (angleHorizontal < ANGLE_HORIZONTAL_MAX) {
			angleHorizontal += 5;
			Missile.setAngleHor(angleHorizontal);
			this.repaint();
		}
	}

	/**
	 * Méthode qui permet de diminuer l'angle horizontal de 5° et de redessiner le
	 * canon.
	 **/
	// Théo Fourteau
	public void fFlecheGaucheCliquee() {
		if (angleHorizontal > ANGLE_HORIZONTAL_MIN) {
			angleHorizontal -= 5;

			Missile.setAngleHor(angleHorizontal);
			this.repaint();
		}

	}

	/**
	 * Methode qui permet d'obtenir la valeur de l'angle vertical du canon
	 * 
	 * @return angleVertical l'angle vertical en degrés
	 */
	// Théo Fourteau
	public int getAngleVertical() {
		return angleVertical;
	}
	
	/**
	 * Methode qui change l'angle vertical du lanceur
	 * @param angleVertical l'angle vertical
	 */
//	Gabriel Maurice
	public void setAngleVertical(int angleVertical) {
		this.angleVertical = angleVertical;
		InfoGuidageMissile.changeAngle(angleVertical);
		Missile.setAngleVert(angleVertical);
		this.repaint();
	}
	
	/**
	 * Methode qui permet d'obtenir la valeur de l'angle horizontal du canon
	 * 
	 * @return angleHorizontal l'angle horizontal en degrés
	 */
	// Théo Fourteau
	public int getAngleHorizontal() {
		return angleHorizontal;
	}


	/**
	 * Methode qui change l'angle horizontal du lanceur
	 * @param angleHorizontal l'angle horizontal
	 */
//	Gabriel Maurice
	public void setAngleHorizontal(int angleHorizontal) {
		this.angleHorizontal = angleHorizontal;
		Missile.setAngleHor(angleHorizontal);
		this.repaint();
	}
}
