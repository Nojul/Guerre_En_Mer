package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Classe qui permet de créer des objets de type Mur et qui peuvent être placés
 * dans la zone des bateaux de manière à bloquer le missile en l'interceptant
 * avant que celui-ci ne touche un bateau
 * 
 * @author Théo Fourteau
 * @author Gabriel Maurice
 */
public class Mur extends Habiletes implements Serializable {

	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;

	// constantes

	/** Longueur maximale du mur en pixels */
	private final int LONGUEUR_MAX = 50;
	/**
	 * Longueur minimale du mur en pixels
	 */
	private final int LONGUEUR_MIN = 20;

	/**
	 * Largeur du mur en pixels. Elle ne peut pas être changée par l'utilisateur
	 */
	private final int LARGEUR = 3;

	/**
	 * Tolérance pour cliquer sur le composant de mur comme celui-ci est mince, en
	 * pixels
	 */
	private final double EPSILON = 3;

	/**
	 * Longueur du mur en pixels
	 */
	private int longueurMur;
	
	/** Hauteur du mur en pixel **/
	private int hauteurMur = 10;

	/**
	 * Rectangle composant le mur
	 */
	private Rectangle2D.Double recMur;
	/**
	 * Booléen qui confirme si oui ou non le mur est en train d'être redimensionné
	 */
	private boolean estEnTrainDeRedimmensionner;

	/**
	 * Booléen qui confirme si oui ou non le mur était en train d'être agrandi
	 */
	private boolean augmentationTaille;

	/**
	 * Angle de rotation défini par l'utilisateur
	 */
	private double angleRotation = 0;

	/**
	 * Constructeur
	 * 
	 * @param x la coordonée en X du centre du mur, en pixels
	 * @param y la coordonnée en Y du centre du mur, en pixels.
	 */
	// Théo Fourteau
	public Mur(int x, int y) {
		super(x, y);
		longueurMur = LONGUEUR_MIN;
		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Méthode qui permet de confirmer si oui ou non le composant contient les
	 * coordonnées xPix et yPix
	 * 
	 * Calcul avec la tolérance Epsilon Méthode utiliée pour le déplacement du mur.
	 * 
	 * @param xPix la coordonnée en x de la souris, en pixels
	 * @param yPix la coordonnée en y de la souris, en pixels
	 * @return le booléen qui confirme si oui ou non le composant (avec epsilon)
	 *         contient les coordonnées de la souris.
	 */
	// Théo Fourteau
	public boolean contientDeplacement(double xPix, double yPix) {
		double xMin = recMur.getMinX() - EPSILON;
		double xMax = recMur.getMaxX() + EPSILON;
		double yMin = recMur.getMinY() - EPSILON;
		double yMax = recMur.getMaxY() + EPSILON;

		return xPix >= xMin && xPix <= xMax && yPix >= yMin && yPix <= yMax;
	}

	/**
	 * Méthode qui permet de confirmer si oui ou non le composant contient les
	 * coordonnées xPix et yPix
	 * 
	 * Méthode utlisée pour les collisions avec le missile
	 * 
	 * @param xPix la coordonnée en x de la souris, en pixels
	 * @param yPix la coordonnée en y de la souris, en pixels
	 */
	// Théo Fourteau
	public boolean contient(double xPix, double yPix) {
		double xMin = recMur.getMinX();
		double xMax = recMur.getMaxX();
		double yMin = recMur.getMinY();
		double yMax = recMur.getMaxY();

		return xPix >= xMin && xPix <= xMax && yPix >= yMin && yPix <= yMax;
	};

	/**
	 * Méthode qui permet de créer la géométrie du mur. Elle est appelée à chaque
	 * fois que la taille du mur ou si ses coordonnées sont modifiées
	 */
	// Théo Fourteau
	public void creerLaGeometrie() {
		recMur = new Rectangle2D.Double(super.getCentreX() - longueurMur / 2, super.getCentreY() - LARGEUR / 2,
				longueurMur, LARGEUR);

	}

	/**
	 * Méthode qui permet de déplacer le centre du mur. Méthode qui sera utilisée
	 * lorsque l'utlisateur cliquera sur le mur pour le déplacer. Les coordonnées en
	 * entrée sont des doubles car il faut diviser la coordonnée en x et en y par le
	 * nombre de pixels par metre.
	 * 
	 * @param x la nouvelle coordonnée du centre du mur en X
	 * @param y la nouvelle coordonnée du centre du mur en Y
	 */
	// Théo Fourteau
	public void deplacer(double x, double y) {
		super.setCentreX((int) x);
		super.setCentreY((int) y);
		creerLaGeometrie();
	}

	/**
	 * Méthode servant à dessiner le mur avec la texture de brique.
	 * 
	 * @param g2d le contexte graphique
	 */
	// Théo Fourteau
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Color rouge = new Color(170, 29, 14); // Couleur brique 
		Color blanc = new Color(255, 255, 251);
		
       
        g2dPrive.translate(super.getCentreX(), super.getCentreY());
		g2dPrive.rotate(Math.toRadians(angleRotation));
		g2dPrive.translate(-super.getCentreX(), -super.getCentreY());
		
		int largeurBriqueRouge = 5; // Largeur d'une brique rouge
	    int largeurBriqueBlanc = 2; // Largeur d'une brique blanche
	    
	    
	    
	    for (int i = 0; i < recMur.getMaxX(); i++) {
	        int x = (int) (recMur.getX() + i * (largeurBriqueRouge + largeurBriqueBlanc));
	        int largeurRouge = Math.min(largeurBriqueRouge, (int) (recMur.getMaxX() - x));
	        int largeurBlanc = Math.min(largeurBriqueBlanc, (int) (recMur.getMaxX() - (x + largeurRouge)));
	        
	        // Dessin de la brique rouge
	        g2dPrive.setColor(rouge);
	        g2dPrive.fillRect(x, (int) recMur.getY(), largeurRouge, (int) recMur.getHeight());
	        
	        // Dessin de la brique blanche
	        g2dPrive.setColor(blanc);
	        g2dPrive.fillRect(x + largeurRouge, (int) recMur.getY(), largeurBlanc, (int) recMur.getHeight());
	    }
    }

	

	/**
	 * Méthode qui retourne si oui ou non la forme a été sélectionnée par
	 * l'utilisateur dans le composant de zone de dessin
	 * 
	 * @return estSelectionne le booléen qui confirme si oui ou non le composant est
	 *         sélectionné
	 */
	// Théo Fourteau
	public boolean getEstSelectionne() {
		return super.getEstSelectionne();
	}

	/**
	 * Méthode qui permet de changer l'état de sélection du mur
	 * 
	 * @param estSelectionne le nouvel état de sélection du mur
	 */
	// Théo Fourteau
	public void setEstSelectionne(boolean estSelectionne) {
		super.setEstSelectionne(estSelectionne);
	}

	/**
	 * Méthode qui permet de faire varier la taille de la longueur du mur entre le
	 * minimum et le maximum.
	 */
	// Théo Fourteau
	public void redimmensionQuandClique() {
		if (!estEnTrainDeRedimmensionner) {
			estEnTrainDeRedimmensionner = true;
			if (augmentationTaille) {
				if (longueurMur < LONGUEUR_MAX) {
					longueurMur += 1;
				} else {
					augmentationTaille = false;
				}
			} else {
				if (longueurMur > LONGUEUR_MIN) {
					longueurMur -= 1;
				} else {
					augmentationTaille = true;
				}
			}
			estEnTrainDeRedimmensionner = false;
			creerLaGeometrie();
		}
	}

	/**
	 * Méthode qui permet de faire tourner le mur sur lui-même
	 */
	// Théo Fourteau
	public void rotationQuandClique() {
		angleRotation += 5;
//		System.out.println("changement de l'angle!");
		creerLaGeometrie();
	}

	/**
	 * Méthode statique qui permet de créer un nouveau mur et de l'ajouter
	 * directement dans la liste des champs présents sur le composant de dessin.
	 * 
	 * @param x la coordonnée en x du centre du mur, en pixels
	 * @param y la coordonnée en y du centre du mur, en pixels
	 * @return nouvMur le nouveau mur
	 */
// Théo Fourteau
	public static Mur createMur(double x, double y) {
		Mur nouvMur = new Mur((int) x, (int) y);
		return nouvMur;
	}
	
	/** 
	 * Methode qui retourne la hauteur du mur
	 * @return hauteurMur la hauteur du mur
	 */

//	Gabriel Maurice
	public int getHauteurMur() {
		return hauteurMur;
	}
	
	/** Méthode qui permet d'obtenir la Shape de l'habileté.
	 * @return shape la Shape de l'habileté 
	 */
	// Gabriel Maurice
	public Shape getShape() {
		return (Shape)recMur;
	}
	/**
	 * Methode qui retourne le numero correspondant aux habiletes
	 * @return nombre 2 representant le mur
	 */
	// Cedric Thai
	public int getNumHabilete() {
		return 2;
	}
}