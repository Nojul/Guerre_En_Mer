package geometrie;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import interfaces.Dessinable;

/**
 * Classe permettant de dessiner les deux missiles du jeu. Les méthodes de
 * dessin sont appelées dans la classe Missile.
 * 
 * @author Theo Fourteau
 */
public class DessinTypeMissile implements Dessinable, Serializable {

	/** Identifiant unique de sérialisation **/
	private static final long serialVersionUID = 7681607646231198467L;
	/** Longueur du missile en pixels **/
	private double longueurMissile;
	/** Largeur du missile en pixels **/
	private double largeurMissile;
	/** Type de missile que l'on veut dessiner **/
	private String typeMissile;
	/** Coordonnée en x de la pointe du missile **/
	private int coorX;
	/** Coordonnée en y de la pointe du missile **/
	private int coorY;

	// Composants du missile

	/** Rectangle représentant le centre du missile **/
	private Rectangle2D.Double corps;
	/** Triangle représentant la pointe du missile **/
	private Path2D.Double pointe;
	/** Trapèze représentant les ailerons du missile **/
	private Path2D.Double ailerons;
	/** Triangle représentant le feu propulsé par le missile **/
	private Path2D.Double propulsion;

	// Aires
	/** Aire de la pointe du missile **/
	private transient Area airePointe;
	/** Aire des ailerons missile **/
	private transient Area aireAilerons;
	/** Aire des ailerons missile **/
	private transient Area airePropulsion;

	/**
	 * Constructeur de la classe
	 * 
	 * @param x             la coordonnée en X du coin supéreur gauche du missile
	 * @param y             la coordonnée en Y du coin supéreur gauche du missile
	 * @param longueur      la longueur du missile en pixels
	 * @param largeur       la largeur du missile en pixels
	 * @param typeDeMissile le type de missile que l'on veut dessiner
	 */
	// Théo Fourteau
	public DessinTypeMissile(int x, int y, double longueur, double largeur, String typeDeMissile) {
		longueurMissile = longueur;
		largeurMissile = largeur;
		coorX = x;
		coorY = y;
		typeMissile = typeDeMissile;

		creerLaGeometrie();
	}

	/**
	 * Méthode permettant de créer la géométrie des missiles, le dessin dépend du
	 * type de missile.
	 **/
	// Théo Fourteau
	public void creerLaGeometrie() {

		Point2D.Double p1 = new Point2D.Double(coorX + largeurMissile / 4, coorY + longueurMissile / 3);
		Point2D.Double p2 = new Point2D.Double(coorX + largeurMissile / 2, coorY);
		Point2D.Double p3 = new Point2D.Double(coorX + 3 * largeurMissile / 4, coorY + longueurMissile / 3);

		pointe = new Path2D.Double();
		pointe.moveTo(p1.getX(), p1.getY());
		pointe.lineTo(p2.getX(), p2.getY());
		pointe.lineTo(p3.getX(), p3.getY());
		pointe.closePath();

		airePointe = new Area(pointe);

		corps = new Rectangle2D.Double(p1.getX(), p1.getY(), largeurMissile / 2, longueurMissile / 2);

		Point2D.Double p5 = new Point2D.Double(coorX, coorY + 4 * longueurMissile / 5);
		Point2D.Double p6 = new Point2D.Double(coorX + 2, coorY + 4 * longueurMissile / 5 - 8);
		Point2D.Double p7 = new Point2D.Double(coorX + largeurMissile - 2, coorY + 4 * longueurMissile / 5 - 8);
		Point2D.Double p8 = new Point2D.Double(coorX + largeurMissile, coorY + 4 * longueurMissile / 5);

		ailerons = new Path2D.Double();
		ailerons.moveTo(p5.getX(), p5.getY());
		ailerons.lineTo(p6.getX(), p6.getY());
		ailerons.lineTo(p7.getX(), p7.getY());
		ailerons.lineTo(p8.getX(), p8.getY());
		ailerons.closePath();

		aireAilerons = new Area(ailerons);

		Point2D.Double p9 = new Point2D.Double(coorX + largeurMissile / 4, coorY + 4 * longueurMissile / 5);
		Point2D.Double p10 = new Point2D.Double(coorX + largeurMissile / 2, coorY + longueurMissile);
		Point2D.Double p11 = new Point2D.Double(coorX + 3 * largeurMissile / 4, coorY + 4 * longueurMissile / 5);

		propulsion = new Path2D.Double();
		propulsion.moveTo(p9.getX(), p9.getY());
		propulsion.lineTo(p10.getX(), p10.getY());
		propulsion.lineTo(p11.getX(), p11.getY());
		propulsion.closePath();

		airePropulsion = new Area(propulsion);

	}

	/**
	 * Méthode qui dessine le missile en fonction du type sélectionné
	 */
	// Théo Fourteau
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPriv = (Graphics2D) g2d.create();
		g2dPriv.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (typeMissile == "Basique") {

			g2dPriv.setColor(Color.RED);
			g2dPriv.fill(airePointe);

			g2dPriv.setColor(Color.DARK_GRAY);
			g2dPriv.fill(aireAilerons);

			g2dPriv.setColor(Color.LIGHT_GRAY);
			g2dPriv.fill(corps);

			GradientPaint gp = new GradientPaint((float) (coorX + longueurMissile / 2),
					(float) (coorY + 3 * longueurMissile / 5), Color.RED, (float) (coorX + longueurMissile / 2),
					(float) (coorY + longueurMissile), Color.YELLOW);
			g2dPriv.setPaint(gp);
			g2dPriv.fill(airePropulsion);

		} else {

			g2dPriv.setColor(Color.GREEN);
			g2dPriv.fill(airePointe);

			g2dPriv.setColor(Color.DARK_GRAY);
			g2dPriv.fill(aireAilerons);

			g2dPriv.setColor(Color.LIGHT_GRAY);
			g2dPriv.fill(corps);

			GradientPaint gp = new GradientPaint((float) (coorX + longueurMissile / 2),
					(float) (coorY + 3 * longueurMissile / 5), Color.PINK, (float) (coorX + longueurMissile / 2),
					(float) (coorY + longueurMissile), Color.GREEN);
			g2dPriv.setPaint(gp);
			g2dPriv.fill(airePropulsion);
		}

	}
}
