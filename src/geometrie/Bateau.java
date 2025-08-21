package geometrie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import javax.swing.JLabel;
import interfaces.Dessinable;
import outils.Vecteur3D;

/**
 * Classe Bateau: Représentation d'un bateau à l’aide d’images. Un bateau
 * mémorise sa position en Vecteur3D, sa taille et son image *
 * 
 * @author Noé Julien
 * @author Gabriel Maurice
 * @author Théo Fourteau
 */
public class Bateau implements Dessinable, Serializable {
	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;
	/** Entier représentant la largeur du bateau */
	private int largeur;
	/** Entier représentant la hauteur du bateau */
	private int hauteur;
	/** Entier représentant la largeur de une case d'un bateau */
	private int largeur1case = 15;
	/** Entier représentant l'angle de rotation du bateau */
	private int angle = 0;
	/** Matrice de transformation affine pour apporter des modification à l'image */
	AffineTransform mat = new AffineTransform();
	/** Vecteur qui représente la position du bateau en X et en Y */
	private Vecteur3D position = new Vecteur3D(0, 0, 0);
	/** Double statique pour le facteur de redimensionnement en pixels par mètre **/
	public static double pixelsParMetre;
	/** Valeur booléen indiquant si le bateau est touché **/
	public boolean touche = false;
	/** Étiquette qui permet d'afficher l'angle **/
	private JLabel lblAngle = new JLabel();
	/** Valeur booléen statique indiquant si l'angle doit être affiché **/
	private static boolean affichAngle = true;
	/** Entier représentant la largeur et la hauteur de l'étiquette **/
	private int largeurHauteurLbl = 20;
	/** Valeur booléen indiquant si le bateau doit être dessiné **/
	private boolean dessine = true;
	/** Type de bateau à dessiner **/
	private String typeBateau;
	/** Type de dessin pour le bateau */
	private DessinTypeBateau bateau1;

	/**
	 * Constructeur de bateau, Le constructeur donne la taille choisi du bateau et
	 * fait le choix de l'image et choisit ses valeurs en fonction du choix de la
	 * taille
	 * 
	 * @param choix entier qui indique la taille du bateau
	 */
	// Noé Julien
	public Bateau(int choix) {

		// Fait le choix entre les différents type de bateaux
		switch (choix) {
		case 2:
			this.largeur = choix * largeur1case;
			this.hauteur = largeur1case;
			typeBateau = "frigate";
			// img = OutilsImage.lireImage("bateau2.png");
			break;
		case 3:
			this.largeur = choix * largeur1case;
			this.hauteur = largeur1case;
			typeBateau = "destroyer";
			// img = OutilsImage.lireImage("bateau3.png");
			break;
		case 31:
			int choix1 = 3;
			this.largeur = choix1 * largeur1case;
			this.hauteur = largeur1case;
			typeBateau = "sous-marin";
			// img = OutilsImage.lireImage("bateau31.png");
			break;
		case 4:
			this.largeur = choix * largeur1case;
			this.hauteur = largeur1case;
			typeBateau = "cruiser";
			// img = OutilsImage.lireImage("bateau4.png");
			break;
		case 5:
			this.largeur = choix * largeur1case;
			this.hauteur = largeur1case;
			typeBateau = "porte-avion";
			// img = OutilsImage.lireImage("bateau5.png");
			break;
		}

		lblAngle.setForeground(Color.BLACK);
		lblAngle.setFont(new Font("Serif", Font.BOLD, 10));

	}

	/**
	 * Méthode qui permet de dessiner un bateau avec une rotation selon la valeur de
	 * son angle.
	 * 
	 * @param g2d contexte graphique
	 */
	// Noé Julien
	public void dessiner(Graphics2D g2d) {

		if (dessine) {
			Graphics2D g2dPrive = (Graphics2D) g2d.create();
			AffineTransform originaleTransform = g2dPrive.getTransform();
//
			g2dPrive.setColor(Color.black);
			g2dPrive.rotate(Math.toRadians(angle), this.getPositionX() + this.largeur / 2,
					this.getPositionY() + this.hauteur / 2);
			if (!touche) {
				bateau1 = new DessinTypeBateau((int) position.getX(), (int) position.getY(), largeur, hauteur,
						typeBateau);
				bateau1.dessiner(g2dPrive);

//				if (typeBateau.equals("destroyer")) {
//					bateau1 = new DessinTypeBateau((int) position.getX(), (int) position.getY(), largeur,hauteur, "destroyer");
//					bateau1.dessiner(g2dPrive);
//				} else {
//
//					g2dPrive.fillRect((int) position.getX(), (int) position.getY(), largeur, hauteur);
//				}
			}
			if (affichAngle) {
				g2dPrive.setTransform(originaleTransform);
				lblAngle.setBounds(0, 0, largeurHauteurLbl, largeurHauteurLbl);
				lblAngle.setText(angle + "°");
				g2dPrive.translate(position.getX() - lblAngle.getWidth(), position.getY());
				lblAngle.paint(g2dPrive);
			}
			g2dPrive.dispose();
		}

	}

	/**
	 * Modifie le ratio de pixels par mètre (px/m)
	 * 
	 * @param pixelsParMetre le nouveau ratio de pixels par mètre (px/m)
	 */
	// Noé Julien
	public static void setPixelsParMetre(double pixelsParMetre) {
		Bateau.pixelsParMetre = pixelsParMetre;
	}

	/**
	 * Méthode qui permet de donner un nouveau angle au bateau quand il est appellé
	 */
	// Noé Julien
	public void addRotation() {
		this.angle += 10;
		if (angle >= 350) {
			angle = 0;
		}
	}

	/**
	 * Retourne l'angle du bateau
	 * 
	 * @return l'angle du bateau en degré.
	 */
	// Noé Julien
	public int getAngle() {
		return angle;
	}

	/**
	 * Définit la valeur de l'angle du bateau
	 * 
	 * @param angle la nouvelle valeur entière de l'angle
	 */
	// Noé Julien
	public void setAngle(int angle) {
		this.angle = angle;
	}

	/**
	 * Retourne la position du bateau en X
	 * 
	 * @return La position du bateau en X
	 */
	// Noé Julien
	public double getPositionX() {
		return position.getX();
	}

	/**
	 * Retourne la position du bateau en Y
	 * 
	 * @return La position du bateau en Y
	 */
	// Noé Julien
	public double getPositionY() {
		return position.getY();
	}

	/**
	 * Définit la position du bateau en X et Y
	 * 
	 * @param x La nouvelle position en X
	 * @param y La nouvelle position en Y
	 */
	// Noé Julien
	public void setPosition(double x, double y) {
		position.setX(x);
		position.setY(y);
	}

	/**
	 * Définit si l'angle doit être affiché
	 *
	 * @param choix Valeur booléenne indiquant si l'angle doit être affiché
	 */
	// Noé Julien
	public static void setAffichAngle(boolean choix) {
		affichAngle = choix;
	}

	/**
	 * Définit si le bateau doit être dessiné
	 * 
	 * @param choix Valeur booléenne indiquant si le bateau doit être affiché
	 */
	// Noé Julien
	public void setDessinable(boolean choix) {
		dessine = choix;
	}

	/**
	 * Renvoie si le bateau est touché ou non
	 *
	 * @return true si le bateau est touché, sinon false
	 */
	// Gabriel Maurice
	public boolean getTouche() {
		return touche;
	}

	/**
	 * Définit si le bateau est touché ou non
	 *
	 * @param touche Valeur booléenne indiquant si le bateau est touché
	 */
	// Gabriel Maurice
	public void setTouche(boolean touche) {
		this.touche = touche;
	}
}
