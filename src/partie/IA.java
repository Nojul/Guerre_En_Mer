package partie;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import composantsDessin.ZoneBateau;
import geometrie.Missile;

/**
 * Classe qui controle l'IA qui joue contre le joueur
 * 
 * @author Gabriel Maurice
 */
public class IA extends JPanel {
	/** Identifiant unique pour la sérialisation **/
	private static final long serialVersionUID = 1L;
	/** La zone de bateau du joueur, ou l'IA tire **/
	private ZoneBateau zoneIA;
	/** La largeur de l'eau **/
	private int width;
	/** La hauteur de l'eau **/
	private int height;
	/** Les points possible ou l'IA peut tirer **/
	private ArrayList<Point2D.Double> pointsPossibles;
	/** Le point parmis les points possibles choisi par l'IA **/
	private Point2D.Double pointChoisi;
	/** L'aire representant les bateaux visibles par l'IA **/
	Area aireBateauxVisibles;
	/** Une ellipse dessinee sur le point choisi **/
	private Ellipse2D.Double pointChoisiEllipse;
	/** L'angle horizontale du lanceur de missile **/
	private int angleHor = 0;
	/** L'angle vertical du lanceur de missile **/
	private int angleVer = 15;
	/** La vitesse initiale du missile **/
	private int vitesse0 = 10;
	/** L'acceleration gravitationnelle **/
	private double accelGrav;
	/** La marge d'erreur pour la precision du tir **/
	private final int EPSILON = 5;

	/**
	 * Constructeur de la classe IA
	 */
//	Gabriel Maurice
	public IA() {
		aireBateauxVisibles = new Area();
		pointsPossibles = new ArrayList<Point2D.Double>();
		pointChoisiEllipse = new Ellipse2D.Double(-2, -2, 4, 4);
	}

	/**
	 * Methode qui cree l'aire qui represente les bateaux visible par l'IA
	 */
//	Gabriel Maurice
	public void calculerAireBateauxJoueur() {
		ArrayList<Shape> trous = new ArrayList<Shape>(zoneIA.getTrous());
		Area aireTrous = new Area();

		ArrayList<Shape> bateaux = new ArrayList<Shape>(zoneIA.getShapes());
		Area aireBateaux = new Area();

		for (Shape trou : trous) {
			aireTrous.add(new Area(trou));
		}

		for (Shape bateau : bateaux) {
			aireBateaux.add(new Area(bateau));
		}

		aireBateauxVisibles = aireTrous;
		aireBateauxVisibles.intersect(aireBateaux);
	}

	/**
	 * Methode qui cree la liste de tout les points sur l'aire des bateaux visibles
	 */
//	Gabriel Maurice
	public void creerListePointsPossibles() {
		pointsPossibles.clear();

		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= height; j++) {
				if (aireBateauxVisibles.contains(i, j)) {
					pointsPossibles.add(new Point2D.Double(i, j));
				}
			}
		}
	}

	/**
	 * Methode qui choisi un point aleatoire parmis la liste de points
	 */
//	Gabriel Maurice
	public void choisirPointAleatoire() {
		Random rng = new Random();

		if (pointsPossibles.isEmpty()) {
			pointChoisi = new Point2D.Double(rng.nextInt(width), rng.nextInt(height));
		} else {
			pointChoisi = pointsPossibles.get(rng.nextInt(pointsPossibles.size()));
		}
		pointChoisiEllipse.setFrame(pointChoisi.x, pointChoisi.y, 5, 5);
	}

	/**
	 * Methode qui choisi les parametres de lancements (angle horizontal, vitesse
	 * initial)
	 */
//	Gabriel Maurice
	public void choisirParametresLancement() {
		angleHor = (int) -Math.toDegrees(Math.atan((pointChoisi.x - width / 2) / pointChoisi.y));
		angleHor /= 5;
		angleHor *= 5;
		angleVer = 45;

		choisirV0();
	}

	/**
	 * Methode qui choisi la vitesse initial du missile pour que ce dernier touche
	 * pres du point choisi
	 */
//	Gabriel Maurice
	private void choisirV0() {
		vitesse0 = 10;

		int distance = (int) Math.sqrt(Math.pow(pointChoisi.x - width / 2, 2) + Math.pow(pointChoisi.y, 2));
		while (predictionDistanceMissile() < distance - EPSILON) {
			vitesse0 += 1;
		}
		Missile.setVitesse0(vitesse0);
	}

	/**
	 * Methode qui retourne la distance que le missile parcourrait avec les
	 * parametres actuels
	 * 
	 * @return la distance que le missile parcourrait avec les parametres actuels
	 */
//	Gabriel Maurice
	private double predictionDistanceMissile() {
		double t = -2 * vitesse0 * Math.sin(angleVer) / accelGrav;
		return vitesse0 * Math.cos(angleVer) * t;
	}

	/**
	 * Methode qui retourne la zone de bateau du joueur, ou l'IA tire
	 * 
	 * @return la zone de bateau du joueur, ou l'IA tire
	 */
//	Gabriel Maurice
	public ZoneBateau getZoneIA() {
		return zoneIA;
	}

	/**
	 * Methode qui change la zone de bateau du joueur, ou l'IA tire
	 * 
	 * @param zoneIA la zone de bateau du joueur, ou l'IA tire
	 */
//	Gabriel Maurice
	public void setZoneIA(ZoneBateau zoneIA) {
		this.zoneIA = zoneIA;
	}

	/**
	 * Methode qui retourne l'aire qui represente les bateaux visibles par l'IA
	 * 
	 * @return l'aire qui represente les bateaux visibles par l'IA
	 */
//	Gabriel Maurice
	public Area getAireBateauxVisibles() {
		return aireBateauxVisibles;
	}

	/**
	 * Methode qui change l'aire qui represente les bateaux visibles par l'IA
	 * 
	 * @param aireBateauxVisibles l'aire qui represente les bateaux visibles par
	 *                            l'IA
	 */
//	Gabriel Maurice
	public void setAireBateauxVisibles(Area aireBateauxVisibles) {
		this.aireBateauxVisibles = aireBateauxVisibles;
	}

	/**
	 * Methode qui retourne la liste de points possibles ou l'IA peut tirer
	 * 
	 * @return la liste de points possibles ou l'IA peut tirer
	 */
//	Gabriel Maurice
	public ArrayList<Point2D.Double> getPointsPossibles() {
		return pointsPossibles;
	}

	/**
	 * Methode qui change la liste de points possibles ou l'IA peut tirer
	 * 
	 * @param pointsPossibles la liste de points possibles ou l'IA peut tirer
	 */
//	Gabriel Maurice
	public void setPointsPossibles(ArrayList<Point2D.Double> pointsPossibles) {
		this.pointsPossibles = pointsPossibles;
	}

	/**
	 * Methode qui retourne le point choisi par l'IA
	 * 
	 * @return le point choisi par l'IA
	 */
//	Gabriel Maurice
	public Point2D.Double getPointChoisi() {
		return pointChoisi;
	}

	/**
	 * Methode qui change le point choisi par l'IA
	 * 
	 * @param pointChoisi le point choisi par l'IA
	 */
//	Gabriel Maurice
	public void setPointChoisi(Point2D.Double pointChoisi) {
		this.pointChoisi = pointChoisi;
	}

	/**
	 * Methode qui retourne l'ellipse dessinee sur le point choisi par l'IA
	 * 
	 * @return l'ellipse dessinee sur le point choisi par l'IA
	 */
//	Gabriel Maurice
	public Ellipse2D.Double getPointChoisiEllipse() {
		return pointChoisiEllipse;
	}

	/**
	 * Methode qui change l'ellipse dessinee sur le point choisi par l'IA
	 * 
	 * @param pointChoisiEllipse l'ellipse dessinee sur le point choisi par l'IA
	 */
//	Gabriel Maurice
	public void setPointChoisiEllipse(Ellipse2D.Double pointChoisiEllipse) {
		this.pointChoisiEllipse = pointChoisiEllipse;
	}

	/**
	 * Methode qui retourne la largeur du composant eau
	 * 
	 * @return la largeur du composant eau
	 */
//	Gabriel Maurice
	public int getWidth() {
		return width;
	}

	/**
	 * Methode qui change la largeur du composant eau
	 * 
	 * @param width la largeur du composant eau
	 */
//	Gabriel Maurice
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Methode qui retourne la hauteur du composant eau
	 * 
	 * @return la hauteur du composant eau
	 */
//	Gabriel Maurice
	public int getHeight() {
		return height;
	}

	/**
	 * Methode qui change la hauteur du composant eau
	 * 
	 * @param height la hauteur du composant eau
	 */
//	Gabriel Maurice
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Methode qui retourne l'angle horizontale du lanceur choisi par l'IA
	 * 
	 * @return l'angle horizontale du lanceur choisi par l'IA
	 */
//	Gabriel Maurice
	public int getAngleHor() {
		return angleHor;
	}

	/**
	 * Methode qui change l'angle horizontale du lanceur choisi par l'IA
	 * 
	 * @param angleHor l'angle horizontale du lanceur choisi par l'IA
	 */
//	Gabriel Maurice
	public void setAngleHor(int angleHor) {
		this.angleHor = angleHor;
	}

	/**
	 * Methode qui retourne l'angle vertical du lanceur choisi par l'IA
	 * 
	 * @return l'angle vertical du lanceur choisi par l'IA
	 */
//	Gabriel Maurice
	public int getAngleVer() {
		return angleVer;
	}

	/**
	 * Methode qui change l'angle vertical du lanceur choisi par l'IA
	 * 
	 * @param angleVer l'angle vertical du lanceur choisi par l'IA
	 */
//	Gabriel Maurice
	public void setAngleVer(int angleVer) {
		this.angleVer = angleVer;
	}

	/**
	 * Methode qui retourne l'acceleration gravitationnelle
	 * 
	 * @return l'acceleration gravitationnelle
	 */
//	Gabriel Maurice
	public double getAccelGrav() {
		return accelGrav;
	}

	/**
	 * Methode qui change l'acceleration gravitationnelle
	 * 
	 * @param accelGrav l'acceleration gravitationnelle
	 */
//	Gabriel Maurice
	public void setAccelGrav(double accelGrav) {
		this.accelGrav = accelGrav;
	}
}