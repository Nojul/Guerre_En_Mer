package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

import application.FenetreTerrainJeu;
import outils.Vecteur3D;

/**
 * Classe qui permet de créer des objets de type champ electrique et qui peuvent
 * être placés dans la zone des bateaux de manière à faire dévier le missile en
 * lui appliquant une certaine force.
 * 
 * @author Théo Fourteau
 * @author Gabriel Maurice
 */

public class ChampElectrique extends Habiletes implements Serializable {
	/** Indentifiant unique pour la sérialisation **/
	private static final long serialVersionUID = -9106086116488008914L;

	/**
	 * Taille maximale du rayon du champ electrique en pixels
	 */
	private int rayonMax = 22;
	/**
	 * Taille minimale du rayon du champ electrique en pixels
	 */
	private int rayonMin = 15;

	/** Constante de Coulomb **/
	private final double K = 9E9;

	/**
	 * Ellipse contenant une image de champ electrique pour apporter un réalisme qui
	 * n'est pas accessible en dessin.
	 */
	private Ellipse2D.Double contenantChamp;
	/**
	 * Valeur représentant le rayon du champ electrique en pixels sur le composant
	 * de dessin
	 */
	private int rayonChamp;

	/**
	 * Booléen qui confirme si oui ou non le champ electrique est en train d'être
	 * redimensionné
	 */
	private boolean estEnTrainDeRedimmensionner;

	/**
	 * Booléen qui confirme si oui ou non le champ electrique était en train d'être
	 * agrandi
	 */
	private boolean augmentationTaille;

	/** Densite lineique de charge du champ **/
	private double lambda = -0.005;

	/** Charge du missile **/
	private double chargeMissile;
	/** Vecteur du champ electrique **/
	private Vecteur3D vecteurChamp;
	/**
	 * La distance maximal entre le missile et le centre du missile, pour eviter de
	 * diviser par un trop petit nombre ou par 0
	 **/
	private final int DISTANCE_MAX = 5;

//	/**
//	 * Liste contenant tous les champs electriques qui seront dessinés dans la zone
//	 * des bateaux.
//	 */
//	private static List<ChampElectrique> champsElectriques = new ArrayList<>();
//	/**
//	 * Compteur du nombre de champs electriques Initialisé à 0.
//	 */
//	private static int nombreChamps = 0;

	/**
	 * Constructeur
	 * 
	 * @param x la coordonnée en x du centre du champ electrique
	 * @param y la coordonnée en y du centre du champ electrique
	 */
	// Théo Fourteau
	public ChampElectrique(int x, int y) {
		super(x, y);
		rayonChamp = rayonMin;
		if (FenetreTerrainJeu.isModeTesteur()) {
			rayonMin = 10;
			rayonMax = 100;
		}

		creerLaGeometrie();

	}// fin constructeur

	/**
	 * Méthode qui permet de confirmer si oui ou non le composant contient les
	 * coordonnées xPix et yPix
	 * 
	 * @param xPix la coordonnée en x de la souris, en pixels
	 * @param yPix la coordonnée en y de la souris, en pixels
	 */
	// Théo Fourteau
	@Override
	public boolean contient(double xPix, double yPix) {
		if (contenantChamp.contains(xPix, yPix)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode qui permet de créer la géométrie du champ electrique. Elle est
	 * appelée à chaque fois que la taille du champ electrique ou si ses coordonnées
	 * sont modifiées
	 */
	// Théo Fourteau
	public void creerLaGeometrie() {
		contenantChamp = new Ellipse2D.Double(super.getCentreX() - rayonChamp, super.getCentreY() - rayonChamp,
				2 * rayonChamp, 2 * rayonChamp);

		try {
			// vecteurChamp = new Vecteur3D(centreX, centreY,
			// 0).normalise().multiplie(intensite);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Méthode qui permet de déplacer le centre du champ electrique Méthode qui sera
	 * utilisée lorsque l'utlisateur cliquera sur le champ pour le déplacer. Les
	 * coordonnées en entrée sont des doubles car il faut diviser la coordonnée en x
	 * et en y par le nombre de pixels par metre.
	 * 
	 * @param x la nouvelle coordonnée du centre du champ en X
	 * @param y la nouvelle coordonnée du centre du champ en Y
	 */
	// Théo Fourteau
	public void deplacer(double x, double y) {
		super.setCentreX((int) x);
		super.setCentreY((int) y);

//		centreX = (int) x;
//		centreY = (int) y;

		creerLaGeometrie();
	}

	/**
	 * Méthode servant à dessiner le champ electrique.
	 * 
	 * @param g2d le contexte graphique
	 */
	// Théo Fourteau
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color jaune = new Color(255, 211, 0);
		Color bleu = new Color(6, 138, 178);

		// Création du dégradé radial avec les nouvelles couleurs
		Color[] couleurs = { jaune, bleu };

		Point2D centre = new Point2D.Double(this.getCentreX(), this.getCentreY());

		// Fractions des couleurs
		float[] fractions = { 0.0f, 1.0f };

		// Création du dégradé radial
		RadialGradientPaint gradient = new RadialGradientPaint(centre, this.rayonChamp, fractions, couleurs);

		// Application du dégradé sur l'ellipse
		g2d.setPaint(gradient);
		g2d.fill(contenantChamp);

	}

	/**
	 * Méthode qui retourne si oui ou non la forme a été sélectionnée par
	 * l'utilisateur dans le composant de zone de dessin
	 * 
	 * @return the estSelectionne le booléen qui confirme si oui ou non le composant
	 *         est sélectionné
	 */
	// Théo Fourteau
	public boolean getEstSelectionne() {
		return super.getEstSelectionne();
	}

	/**
	 * Méthode qui permet de changer l'état de sélection du champ electrique
	 * 
	 * @param estSelectionne le nouvel état de sélection du champ electrique
	 */
	// Théo Fourteau
	public void setEstSelectionne(boolean estSelectionne) {
		super.setEstSelectionne(estSelectionne);
	}

	/**
	 * Méthode qui permet de faire varier la taille du rayon du champ electrique
	 * entre le minimum et le maximum.
	 * 
	 */
	// Théo Fourteau
	public void redimmensionQuandClique() {
		if (!estEnTrainDeRedimmensionner) {
			estEnTrainDeRedimmensionner = true;
			if (augmentationTaille) {
				if (rayonChamp < rayonMax) {
					rayonChamp += 1;
//					System.out.println("augmentation");
				} else {
					augmentationTaille = false;
				}
			} else {
				if (rayonChamp > rayonMin) {
					rayonChamp -= 1;
//					System.out.println("diminution");
				} else {
					augmentationTaille = true;
				}
			}
			estEnTrainDeRedimmensionner = false;
			creerLaGeometrie();
		}
	}

	/**
	 * Méthode qui retourne la densite lineique de charge du champ electrique
	 * 
	 * @return lambda la densite lineque de charge du champ electrique
	 */
//	Gabriel Maurice
	public double getLambda() {
		return lambda;
	}

	/**
	 * Méthode permettant de retourner le vecteur du champ electrique
	 * 
	 * @return vecteurChamp le vecteur du champ electrique
	 */
	// Théo Fourteau
	public Vecteur3D getVecteurChamp() {
		return vecteurChamp;
	}

	/**
	 * Méthode statique qui permet de créer un nouveau champ electrique et de
	 * l'ajouter directement dans la liste des champs présents sur le composant de
	 * dessin.
	 * 
	 * @param x la coordonnée en x du centre du champ, en pixels
	 * @param y la coordonnée en y du centre du champ, en pixels
	 * @return champ le nouveau champ electrique
	 */
	// Théo Fourteau
	public static ChampElectrique createChampElectrique(double x, double y) {
		ChampElectrique champ = new ChampElectrique((int) x, (int) y);
		return champ;
	}

	/**
	 * Méthode qui retourne la coordonnée en X du centre du composant en pixels
	 * 
	 * @return la coordonnée en X du centre du composant en pixels.
	 */
	// Théo Fourteau
	public double getCentreX() {
		return super.getCentreX();
	}

	/**
	 * Méthode qui retourne la coordonnée en Y du centre du composant en pixels
	 * 
	 * @return la coordonnée en Y du centre du composant en pixels.
	 */
	// Théo Fourteau
	public double getCentreY() {
		return super.getCentreY();
	}

	@Override
	// Théo Fourteau
	public void rotationQuandClique() {
		// Pas de rotation pour le champ electrique
	}

	/**
	 * Methode qui calcule la force electrique applique sur le missile
	 * 
	 * @param x le x du missile
	 * @param y le y du missile
	 * @return vecteurChamp le vecteur de force electrique
	 */
//	Gabriel Maurice
	public Vecteur3D ForceChampElectrique(double x, double y) {
		this.setChargeMissile(Missile.getCharge());

		double distanceX = getCentreX() - x;
		double distanceY = getCentreY() - y;

		double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		if (distance < DISTANCE_MAX) {
			distance = DISTANCE_MAX;
		}

		double angle = Math.atan(distanceY / distanceX);

		int directionChampX = 1;
		int directionChampY = 1;

		if (distanceX <= 0) {
			directionChampX = -1;
		}

		if (distanceY <= 0) {
			directionChampY = -1;
		}

		lambda /= Math.PI * Math.pow(rayonChamp, 2);
		double forceElectriqueScalaire = 2 * K * lambda * chargeMissile * Missile.getMasse() / distance;

		vecteurChamp = new Vecteur3D(directionChampX * Math.cos(angle) * forceElectriqueScalaire,
				directionChampY * Math.sin(angle) * forceElectriqueScalaire, 0);

		return vecteurChamp;
	}

	/**
	 * Méthode permettant d'obtenir la Shape de l'ellipse du champ électrique
	 */
	// Gabriel Maurice
	public Shape getShape() {
		return (Shape) contenantChamp;
	}

	/**
	 * Méthode permettant de modifier la charge du missile.
	 * 
	 * @param chargeMissile la nouvelle charge du missile
	 */
	// Gabriel Maurice
	public void setChargeMissile(double chargeMissile) {
		this.chargeMissile = chargeMissile;
	}

	/**
	 * Methode qui retourne le numero correspondant aux habiletes
	 * 
	 * @return nombre 1 representant le champ electrique
	 */
	// Cedric Thai
	public int getNumHabilete() {
		return 1;
	}
}