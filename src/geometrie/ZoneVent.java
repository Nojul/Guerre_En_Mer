package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Classe qui permet de créer des objets de type ZoneVent et qui peuvent être
 * placés dans la zone des bateaux de manière à orienter le missile dans une
 * certaine direction La zone de vent est carrée.
 * 
 * @author Théo Fourteau
 * @author Gabriel Maurice
 */
public class ZoneVent extends Habiletes implements Serializable {

	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;

	// constantes
	/** Longueur du côté du carré en pixels */
	private final int LONGUEUR_COTE = 30;

	/** Longueur de la flèche en pixels **/
	private final int LONG_FLECHE = 16;

	/** Largeur de la flèche en pixles **/
	private final int LARG_FLECHE = 8;

	/**
	 * Rectangle composant la zone de vent
	 */
	private Rectangle2D.Double recZoneVent;

	/** Angle de rotation défini par l'utilisateur, en degrés **/
	private double angleVent = 0;

	/** La force du vent **/
	private static final double forceVent = 10000;

	/** Point 1 de la flèche **/
	Point2D.Double point1 = new Point2D.Double();
	/** Point 2 de la flèche **/
	Point2D.Double point2 = new Point2D.Double();
	/** Point 3 de la flèche **/
	Point2D.Double point3 = new Point2D.Double();
	/** Point 4 de la flèche **/
	Point2D.Double point4 = new Point2D.Double();
	/** Point 5 de la flèche **/
	Point2D.Double point5 = new Point2D.Double();
	/** Point 6 de la flèche **/
	Point2D.Double point6 = new Point2D.Double();
	/** Point 7 de la flèche **/
	Point2D.Double point7 = new Point2D.Double();

	/** Path reliant les points de la flèche **/
	Path2D.Double pathFleche;

	/**
	 * Constructeur
	 * 
	 * @param x la coordonée en X du centre de la zone, en pixels
	 * @param y la coordonnée en Y du centre de la zone, en pixels.
	 */
	// Théo Fourteau
	public ZoneVent(int x, int y) {
		super(x, y);
		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Méthode qui permet de confirmer si oui ou non le composant contient les
	 * coordonnées xPix et yPix
	 * 
	 * Méthode utlisée pour les intersections avec le missile
	 * 
	 * @param xPix la coordonnée en x de la souris, en pixels
	 * @param yPix la coordonnée en y de la souris, en pixels
	 */
	// Théo Fourteau
	public boolean contient(double xPix, double yPix) {
		double xMin = recZoneVent.getMinX();
		double xMax = recZoneVent.getMaxX();
		double yMin = recZoneVent.getMinY();
		double yMax = recZoneVent.getMaxY();

		return xPix >= xMin && xPix <= xMax && yPix >= yMin && yPix <= yMax;
	}

	/**
	 * Méthode qui permet de créer la géométrie de la zone de vent. Elle est
	 * rappelée à chaque fois que les coordonnées du centre de la zone de vent sont
	 * modifiées.
	 */
	// Théo Fourteau
	public void creerLaGeometrie() {
		recZoneVent = new Rectangle2D.Double(super.getCentreX() - LONGUEUR_COTE / 2,
				super.getCentreY() - LONGUEUR_COTE / 2, LONGUEUR_COTE, LONGUEUR_COTE);

//		// Création de la flèche
		point1 = new Point2D.Double(super.getCentreX(), super.getCentreY() - LONG_FLECHE / 2);
		point2 = new Point2D.Double(super.getCentreX() + LARG_FLECHE, super.getCentreY());
		point3 = new Point2D.Double(super.getCentreX() + LARG_FLECHE / 3, super.getCentreY());
		point4 = new Point2D.Double(super.getCentreX() + LARG_FLECHE / 3, super.getCentreY() + LONG_FLECHE / 2);
		point5 = new Point2D.Double(super.getCentreX() - LARG_FLECHE / 3, super.getCentreY() + LONG_FLECHE / 2);
		point6 = new Point2D.Double(super.getCentreX() - LARG_FLECHE / 3, super.getCentreY());
		point7 = new Point2D.Double(super.getCentreX() - LARG_FLECHE, super.getCentreY());

		pathFleche = new Path2D.Double();
		pathFleche.moveTo(point1.getX(), point1.getY());
		pathFleche.lineTo(point2.getX(), point2.getY());
		pathFleche.lineTo(point3.getX(), point3.getY());
		pathFleche.lineTo(point4.getX(), point4.getY());
		pathFleche.lineTo(point5.getX(), point5.getY());
		pathFleche.lineTo(point6.getX(), point6.getY());
		pathFleche.lineTo(point7.getX(), point7.getY());
		pathFleche.closePath(); // Pour fermer le chemin
	}

	/**
	 * Méthode qui permet de déplacer le centre de la zone de vent. Méthode qui sera
	 * utilisée lorsque l'utlisateur cliquera sur la zone pour la déplacer. Les
	 * coordonnées en entrée sont des doubles car il faut diviser la coordonnée en x
	 * et en y par le nombre de pixels par metre.
	 * 
	 * @param x la nouvelle coordonnée du centre de la zone en X
	 * @param y la nouvelle coordonnée du centre de la zone en Y
	 */
	// Théo Fourteau
	public void deplacer(double x, double y) {
		super.setCentreX((int) x);
		super.setCentreY((int) y);
		creerLaGeometrie();
	}

	/**
	 * Méthode servant à dessiner le mur.
	 * 
	 * @param g2d le contexte graphique
	 */
	// Théo Fourteau
	public void dessiner(Graphics2D g2d) {

		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Dessin du rectangle
		g2dPrive.setColor(new Color(180, 210, 231));
		g2dPrive.fill(recZoneVent);

		AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angleVent), super.getCentreX(),
				super.getCentreY());
		Shape fleche = rotation.createTransformedShape(pathFleche);

		// Ombre pour effet 3d
		g2dPrive.setColor(new Color(20, 27, 65, 50)); // Couleur de l'ombre avec une transparence de 100
		Area flecheArea = new Area(fleche);
		flecheArea.transform(AffineTransform.getTranslateInstance(1, 1)); // Décalage de l'ombre
		g2dPrive.fill(flecheArea);

		// Dessiner la flèche
		g2dPrive.setColor(new Color(96, 151, 210));
		g2dPrive.fill(fleche);

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
	 * Méthode qui permet de changer l'état de sélection de la zone de vent
	 * 
	 * @param estSelectionne le nouvel état de sélection de la zone de vent
	 */
	// Théo Fourteau
	public void setEstSelectionne(boolean estSelectionne) {
		super.setEstSelectionne(estSelectionne);
	}

	/**
	 * Méthode qui permet de faire changer l'angle de la direction du vent mur sur
	 * lui-même
	 */
	// Théo Fourteau
	public void rotationQuandClique() {
		angleVent += 5;
		System.out.println("angle du vent : " + angleVent);
		creerLaGeometrie();
	}

	/**
	 * Méthode statique qui permet de créer une nouvelle zone de vent et de
	 * l'ajouter directement dans la liste des zones de vent présentes sur le
	 * composant de dessin.
	 * 
	 * @param x la coordonnée en x du centre de la zone de vent, en pixels
	 * @param y la coordonnée en y du centre de la zone de vent, en pixels
	 * @return nouvVent la nouvelle zone de vent
	 */
	// Théo Fourteau
	public static ZoneVent createZoneVent(double x, double y) {
		ZoneVent nouvVent = new ZoneVent((int) x, (int) y);
		return nouvVent;
	}

	/**
	 * Méthode de la classe mère qui n'est pas utilisée pour la zone de vent
	 */
	@Override
	// Théo Fourteau
	public void redimmensionQuandClique() {
		// Pas de redimmension possible pour la zone de vent

	}

	/**
	 * Methode qui retourne l'angle du vent
	 * 
	 * @return angleVent l'angle du vent
	 */
//	Gabriel Maurice
	public double getAngleVent() {
		return angleVent;
	}

	/**
	 * Methode qui retourne la force du vent
	 * 
	 * @return angleVent la force du vent
	 */
//	Gabriel Maurice
	public static double getForceVent() {
		return ZoneVent.forceVent;
	}

	/**
	 * Méthode qui permet d'obtenir la Shape de l'habileté.
	 * 
	 * @return shape la Shape de l'habileté
	 */
	// Gabriel Maurice
	public Shape getShape() {
		return (Shape) recZoneVent;
	}
	
	/**
	 * Methode qui retourne le numero correspondant aux habiletes
	 * @return nombre 3 representant le vent
	 */
	// Cedric Thai
	public int getNumHabilete() {
		return 3;
	}
}