package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import interfaces.Dessinable;

/**
 * Classe qui permet de dessiner les bateaux de manière esthétique et réaliste.
 * La est appelée dans la méthode bateau. Chaque bateau a une allure et un nom
 * différent
 * 
 * @author Théo Fourteau
 **/
public class DessinTypeBateau implements Dessinable, Serializable {

	/** identifiant unique de sérialisation **/
	private static final long serialVersionUID = -3797151143409123991L;
	/** Longueur du bateau en pixels **/
	private double longueurBateau;
	/** Largeur du bateau en pixels **/
	private double largeurBateau;
	/** Type de bateau que l'on veut dessiner **/
	private String typeBateau;
	/** Coordonnée en x du coin supérieur gauche du bateau **/
	private int coorX;
	/** Coordonnée en y du coin supérieur gauche du bateau **/
	private int coorY;
	/** Largeur de la cheminée du bateau en pixels **/
	private final int LARGEUR_CHEMINEE = 5;
	/** Largeur de la cabine de pilotage en pixels **/
	private final int LARGEUR_CABINE = 3;
	/** Espace entre les bords et les composants placés sur le bateau **/
	private final int ESPACE_AVEC_BORDS = 1;
	/** Largeur des avions en pixels **/
	private final double LARGEUR_AVION = 5;
	/** Longueur des avions en pixels **/
	private final double LONGUEUR_AVION = 8;

	// Composants du bateau
	/** Poupe du bateau **/
	private Rectangle2D.Double poupe;
	/** Proue du bateau (triangle) **/
	private Path2D.Double proue;
	/** Centre du bateau **/
	private Rectangle2D.Double corps;
	/** Cheminée du bateau **/
	private Rectangle2D.Double rectangleCheminee;
	/** Trou de la cheminée **/
	private Ellipse2D.Double trouCheminee;
	/** Partie retangulaire de la cabine de pilotage **/
	private Rectangle2D.Double rectangleCabine;
	/** Partie en forme de trapèze de la cabine **/
	private Path2D.Double cabineTrapeze;
	/** Ellipse formant la base du canon présent sur le destroyer et le cruiser **/
	private Ellipse2D.Double baseCanon;
	/** Rectangle formant le canon présent sur le destroyer et le cruiser **/
	private Rectangle2D.Double canon;

	// Composants particuliers des bateaux
	/** Cargaison frigate **/
	private Rectangle2D.Double rectangleCargaisonFrigate;
	/** Héliport présent sur le destroyer **/
	private Ellipse2D.Double heliportDestroyer;

	// Aires
	/** Aire regroupant les composants formant la coque du bateau **/
	private transient Area coque;
	/** Aire regroupant les parties de la cabine **/
	private transient Area cabine;
	/** Aire de la poupe du bateau **/
	private transient Area airePoupe;
	/** Aire du corps du bateau **/
	private transient Area aireCorps;
	/** Aire de la proue du bateau **/
	private transient Area aireProue;
	/** Aire du trapèze de la cabine **/
	private transient Area aireCabineTrapeze;
	/** Aire de la lettre H à dessiner sur le héliport **/
	private transient Area lettreH;
	/** Aire du canon **/
	private transient Area aireCanon;
	/** Aire de la tour du sous-marin **/
	private transient Area tourSousMarin;
	/** Aire de la deuxième cabine pour le cruiser **/
	private transient Area deuxiemeCabine;
	/** Aire de stockage du cruiser **/
	private transient Area aireDeStockage;
	/** Aire contenant les deux lignes du porte avion **/
	private transient Area aireLignesPA;
	/** Aire contenant les avions du porte avion **/
	private transient Area aireAvions;

	// Points
	/** Point servant à dessiner le triangle de la proue 1/3 **/
	private Point2D.Double p1;
	/** Point servant à dessiner le triangle de la proue 2/3 **/
	private Point2D.Double p2;
	/** Point servant à dessiner le triangle de la proue 3/3 **/
	private Point2D.Double p3;
	/** Point servant à dessiner le trapèze de la cabine 1/4 **/
	private Point2D.Double p4;
	/** Point servant à dessiner le trapèze de la cabine 2/4 **/
	private Point2D.Double p5;
	/** Point servant à dessiner le trapèze de la cabine 3/4 **/
	private Point2D.Double p6;
	/** Point servant à dessiner le trapèze de la cabine 4/4 **/
	private Point2D.Double p7;

	// Couleurs

	/** Couleur de la coque des bateaux **/
	Color couleurCoque = new Color(210, 210, 210);
	/** Couleur de la cabine des bateaux **/
	Color couleurCabine = new Color(131, 131, 131);
	/** Couleur de l'héliport **/
	Color couleurHeliport = new Color(138, 201, 128);
	/** Couleur de la coque du sous-marin **/
	Color couleurCoqueSousMarin = new Color(66, 63, 56);
	/** Couleur des lignes du porte avion **/
	Color couleurLignesPA = new Color(253, 249, 114);

	/**
	 * Constructeur de la classe
	 * 
	 * @param x            la coordonnée en X du coin supéreur gauche du bateau
	 * @param y            la coordonnée en Y du coin supéreur gauche du bateau
	 * @param longueur     la longueur du bateau en pixels
	 * @param largeur      la largeur du bateau en pixels
	 * @param typeDeBateau le type de bateau que l'on veut dessiner
	 */
	// Théo Fourteau
	public DessinTypeBateau(int x, int y, double longueur, double largeur, String typeDeBateau) {
		longueurBateau = longueur;
		largeurBateau = largeur;
		coorX = x;
		coorY = y;
		typeBateau = typeDeBateau;

		creerLaGeometrie();

	}

	/**
	 * Méthode permettant de créer la géométrie des bateaux, le dessin dépend du
	 * type de bateau.
	 **/
	// Théo Fourteau
	public void creerLaGeometrie() {

		switch (typeBateau) {

		case "frigate":

			// Forme de la coque
			poupe = new Rectangle2D.Double(coorX, coorY + ESPACE_AVEC_BORDS, ESPACE_AVEC_BORDS,
					largeurBateau - 2 * ESPACE_AVEC_BORDS);
			airePoupe = new Area(poupe);
			corps = new Rectangle2D.Double(poupe.getMaxX(), coorY, longueurBateau / 1.5f, largeurBateau);
			aireCorps = new Area(corps);

			p1 = new Point2D.Double(corps.getMaxX(), coorY);
			p2 = new Point2D.Double(coorX + longueurBateau, corps.getCenterY());
			p3 = new Point2D.Double(corps.getMaxX(), coorY + largeurBateau);

			proue = new Path2D.Double();
			proue.moveTo(p1.getX(), p1.getY());
			proue.lineTo(p2.getX(), p2.getY());
			proue.lineTo(p3.getX(), p3.getY());
			proue.closePath();
			aireProue = new Area(proue);

			coque = new Area(airePoupe);
			coque.add(aireCorps);
			coque.add(aireProue);

			// Forme de la cabine de pilotage
			rectangleCabine = new Rectangle2D.Double(p1.getX() - LARGEUR_CABINE, coorY + ESPACE_AVEC_BORDS,
					LARGEUR_CABINE, largeurBateau - 2 * ESPACE_AVEC_BORDS);
			cabine = new Area(rectangleCabine);

			p4 = new Point2D.Double(rectangleCabine.getMaxX(), rectangleCabine.getMinY());
			p5 = new Point2D.Double(p4.getX() + LARGEUR_CABINE, p4.getY() + rectangleCabine.getHeight() / 3);
			p6 = new Point2D.Double(p5.getX(), p5.getY() + rectangleCabine.getHeight() / 3);
			p7 = new Point2D.Double(rectangleCabine.getMaxX(), rectangleCabine.getMaxY());

			cabineTrapeze = new Path2D.Double();
			cabineTrapeze.moveTo(p4.getX(), p4.getY());
			cabineTrapeze.lineTo(p5.getX(), p5.getY());
			cabineTrapeze.lineTo(p6.getX(), p6.getY());
			cabineTrapeze.lineTo(p7.getX(), p7.getY());
			cabineTrapeze.closePath();
			aireCabineTrapeze = new Area(cabineTrapeze);
			cabine.add(aireCabineTrapeze);

			// Forme de la cargaison du bateau
			rectangleCargaisonFrigate = new Rectangle2D.Double(corps.getMinX(), poupe.getMinY(), 6, poupe.getHeight());

			// Forme de la cheminée
			rectangleCheminee = new Rectangle2D.Double(rectangleCabine.getMinX() - LARGEUR_CHEMINEE,
					rectangleCabine.getMinY() + 2, LARGEUR_CHEMINEE, rectangleCabine.getHeight() - 4);
			double largeurEllipse = 3 * rectangleCheminee.getWidth() / 5;
			double hauteurEllipse = 4 * rectangleCheminee.getHeight() / 5;
			trouCheminee = new Ellipse2D.Double(rectangleCheminee.getCenterX() - largeurEllipse / 2,
					rectangleCheminee.getCenterY() - hauteurEllipse / 2, largeurEllipse, hauteurEllipse);

		case "destroyer":

			// Forme de la coque
			poupe = new Rectangle2D.Double(coorX, coorY + ESPACE_AVEC_BORDS, ESPACE_AVEC_BORDS,
					largeurBateau - 2 * ESPACE_AVEC_BORDS);
			airePoupe = new Area(poupe);
			corps = new Rectangle2D.Double(poupe.getMaxX(), coorY, longueurBateau / 1.5f, largeurBateau);
			aireCorps = new Area(corps);

			p1 = new Point2D.Double(corps.getMaxX(), coorY);
			p2 = new Point2D.Double(coorX + longueurBateau, corps.getCenterY());
			p3 = new Point2D.Double(corps.getMaxX(), coorY + largeurBateau);

			proue = new Path2D.Double();
			proue.moveTo(p1.getX(), p1.getY());
			proue.lineTo(p2.getX(), p2.getY());
			proue.lineTo(p3.getX(), p3.getY());
			proue.closePath();
			aireProue = new Area(proue);

			coque = new Area(airePoupe);
			coque.add(aireCorps);
			coque.add(aireProue);

			// Forme de la cabine de pilotage
			rectangleCabine = new Rectangle2D.Double(p1.getX() - 2.5 * LARGEUR_CABINE, coorY + ESPACE_AVEC_BORDS,
					LARGEUR_CABINE, largeurBateau - 2 * ESPACE_AVEC_BORDS);
			cabine = new Area(rectangleCabine);

			p4 = new Point2D.Double(rectangleCabine.getMaxX(), rectangleCabine.getMinY());
			p5 = new Point2D.Double(p4.getX() + LARGEUR_CABINE, p4.getY() + rectangleCabine.getHeight() / 3);
			p6 = new Point2D.Double(p5.getX(), p5.getY() + rectangleCabine.getHeight() / 3);
			p7 = new Point2D.Double(rectangleCabine.getMaxX(), rectangleCabine.getMaxY());

			cabineTrapeze = new Path2D.Double();
			cabineTrapeze.moveTo(p4.getX(), p4.getY());
			cabineTrapeze.lineTo(p5.getX(), p5.getY());
			cabineTrapeze.lineTo(p6.getX(), p6.getY());
			cabineTrapeze.lineTo(p7.getX(), p7.getY());
			cabineTrapeze.closePath();
			aireCabineTrapeze = new Area(cabineTrapeze);
			cabine.add(aireCabineTrapeze);

			// Forme de la cheminée
			rectangleCheminee = new Rectangle2D.Double(rectangleCabine.getMinX() - LARGEUR_CHEMINEE,
					rectangleCabine.getMinY() + 2, LARGEUR_CHEMINEE, rectangleCabine.getHeight() - 4);
			largeurEllipse = 3 * rectangleCheminee.getWidth() / 5;
			hauteurEllipse = 4 * rectangleCheminee.getHeight() / 5;
			trouCheminee = new Ellipse2D.Double(rectangleCheminee.getCenterX() - largeurEllipse / 2,
					rectangleCheminee.getCenterY() - hauteurEllipse / 2, largeurEllipse, hauteurEllipse);

			// Héliport
			heliportDestroyer = new Ellipse2D.Double(corps.getMinX() + 2 * ESPACE_AVEC_BORDS,
					corps.getMinY() + 2 * ESPACE_AVEC_BORDS, corps.getHeight() - 4 * ESPACE_AVEC_BORDS,
					corps.getHeight() - 4 * ESPACE_AVEC_BORDS);

			// Lettre H de l'héliport
			// Nombres ici => objectif esthétique
			Rectangle2D.Double rect1 = new Rectangle2D.Double(corps.getMinX() + 5.5 * ESPACE_AVEC_BORDS,
					corps.getMinY() + 5 * ESPACE_AVEC_BORDS, heliportDestroyer.getWidth() / 3,
					heliportDestroyer.getHeight() / 8);
			Rectangle2D.Double rect2 = new Rectangle2D.Double(rect1.getCenterX() - 0.5f * ESPACE_AVEC_BORDS,
					rect1.getMaxY(), ESPACE_AVEC_BORDS, 3 * ESPACE_AVEC_BORDS);
			Rectangle2D.Double rect3 = new Rectangle2D.Double(corps.getMinX() + 5.5 * ESPACE_AVEC_BORDS,
					corps.getMinY() + 8 * ESPACE_AVEC_BORDS, heliportDestroyer.getWidth() / 3,
					heliportDestroyer.getHeight() / 8);

			Area rectH1 = new Area(rect1);
			Area rectH2 = new Area(rect2);
			Area rectH3 = new Area(rect3);
			lettreH = rectH1;
			lettreH.add(rectH2);
			lettreH.add(rectH3);

			// Canon
			baseCanon = new Ellipse2D.Double(p1.getX(), p5.getY(), p6.getY() - p5.getY(), p6.getY() - p5.getY());
			canon = new Rectangle2D.Double(baseCanon.getCenterX(), baseCanon.getCenterY() - ESPACE_AVEC_BORDS,
					1.5f * baseCanon.getWidth(), baseCanon.getHeight() / 3);

			Area baseCanonAire = new Area(baseCanon);
			Area canonAire = new Area(canon);

			aireCanon = baseCanonAire;
			aireCanon.add(canonAire);

			break;
		case "sous-marin":

			// Trapèze correspondant à l'arrière du sous-marin
			p1 = new Point2D.Double(coorX, coorY + largeurBateau / 3);
			p2 = new Point2D.Double(coorX + 5 * ESPACE_AVEC_BORDS, coorY + ESPACE_AVEC_BORDS);
			p3 = new Point2D.Double(coorX + 5 * ESPACE_AVEC_BORDS, coorY + largeurBateau - ESPACE_AVEC_BORDS);
			p4 = new Point2D.Double(coorX, coorY + 2 * largeurBateau / 3);

			// Dans ce cas, la proue est en réalité l'arrière du bateau (pour ne pas créer
			// d'autres variables)
			proue = new Path2D.Double();
			proue.moveTo(p1.getX(), p1.getY());
			proue.lineTo(p2.getX(), p2.getY());
			proue.lineTo(p3.getX(), p3.getY());
			proue.lineTo(p4.getX(), p4.getY());
			proue.closePath();
			aireProue = new Area(proue);

			corps = new Rectangle2D.Double(coorX + 5 * ESPACE_AVEC_BORDS, coorY + ESPACE_AVEC_BORDS,
					longueurBateau - 12 * ESPACE_AVEC_BORDS, largeurBateau - 2 * ESPACE_AVEC_BORDS);
			aireCorps = new Area(corps);

			Ellipse2D.Double partieArrondieAvant = new Ellipse2D.Double(corps.getMaxX() - 4 * ESPACE_AVEC_BORDS,
					coorY + ESPACE_AVEC_BORDS, 10 * ESPACE_AVEC_BORDS, corps.getHeight());
			Area partieAvant = new Area(partieArrondieAvant);
			coque = aireProue;
			coque.add(aireCorps);
			coque.add(partieAvant);

			// Trapèze correspondant aux ailerons du sous marin
			p1 = new Point2D.Double(corps.getCenterX(), coorY);
			p2 = new Point2D.Double(corps.getCenterX() + 5, corps.getMinY());
			p3 = new Point2D.Double(corps.getCenterX() + 5, corps.getMaxY());
			p4 = new Point2D.Double(corps.getCenterX(), coorY + largeurBateau);

			Path2D.Double ailerons = new Path2D.Double();
			ailerons.moveTo(p1.getX(), p1.getY());
			ailerons.lineTo(p2.getX(), p2.getY());
			ailerons.lineTo(p3.getX(), p3.getY());
			ailerons.lineTo(p4.getX(), p4.getY());
			ailerons.closePath();

			Area aileronsAire = new Area(ailerons);
			coque.add(aileronsAire);

			// Tour sous marin
			Ellipse2D.Double partie1Tour = new Ellipse2D.Double(corps.getCenterX() + 3, corps.getCenterY() - 2, 10, 4);
			Area tourPartie1Aire = new Area(partie1Tour);
			// Triangle correspondant aux ailerons de la tour
			p1 = new Point2D.Double(partie1Tour.getCenterX() - 2, corps.getMinY() + 2);
			p2 = new Point2D.Double(partie1Tour.getCenterX() + 2, partie1Tour.getCenterY());
			p3 = new Point2D.Double(partie1Tour.getCenterX() - 2, corps.getMaxY() - 2);

			Path2D.Double aileronsTour = new Path2D.Double();
			aileronsTour.moveTo(p1.getX(), p1.getY());
			aileronsTour.lineTo(p2.getX(), p2.getY());
			aileronsTour.lineTo(p3.getX(), p3.getY());
			aileronsTour.closePath();
			Area aileronsTourAire = new Area(aileronsTour);

			tourSousMarin = tourPartie1Aire;
			tourSousMarin.add(aileronsTourAire);

			break;

		case "cruiser":

			// Forme de la coque
			poupe = new Rectangle2D.Double(coorX, coorY + ESPACE_AVEC_BORDS, ESPACE_AVEC_BORDS,
					largeurBateau - 2 * ESPACE_AVEC_BORDS);
			airePoupe = new Area(poupe);
			corps = new Rectangle2D.Double(poupe.getMaxX(), coorY, longueurBateau / 1.5f, largeurBateau);
			aireCorps = new Area(corps);

			p1 = new Point2D.Double(corps.getMaxX(), coorY);
			p2 = new Point2D.Double(coorX + longueurBateau, corps.getCenterY());
			p3 = new Point2D.Double(corps.getMaxX(), coorY + largeurBateau);

			proue = new Path2D.Double();
			proue.moveTo(p1.getX(), p1.getY());
			proue.lineTo(p2.getX(), p2.getY());
			proue.lineTo(p3.getX(), p3.getY());
			proue.closePath();
			aireProue = new Area(proue);

			coque = new Area(airePoupe);
			coque.add(aireCorps);
			coque.add(aireProue);

			p1 = new Point2D.Double(corps.getMaxX() + 5, coorY + 4);
			p2 = new Point2D.Double(coorX + longueurBateau - 5, corps.getCenterY());
			p3 = new Point2D.Double(corps.getMaxX() + 5, coorY + largeurBateau - 4);

			Path2D.Double partieStockage = new Path2D.Double();
			partieStockage.moveTo(p1.getX(), p1.getY());
			partieStockage.lineTo(p2.getX(), p2.getY());
			partieStockage.lineTo(p3.getX(), p3.getY());
			partieStockage.closePath();

			aireDeStockage = new Area(partieStockage);

			// Forme de la cabine de pilotage
			rectangleCabine = new Rectangle2D.Double(corps.getCenterX() - 7 * ESPACE_AVEC_BORDS,
					coorY + ESPACE_AVEC_BORDS, 8 * LARGEUR_CABINE, largeurBateau - 2 * ESPACE_AVEC_BORDS);
			cabine = new Area(rectangleCabine);

			// Cabine bas
			p4 = new Point2D.Double(rectangleCabine.getMaxX(), rectangleCabine.getMinY());
			p5 = new Point2D.Double(p4.getX() + LARGEUR_CABINE, p4.getY() + rectangleCabine.getHeight() / 3);
			p6 = new Point2D.Double(p5.getX(), p5.getY() + rectangleCabine.getHeight() / 3);
			p7 = new Point2D.Double(rectangleCabine.getMaxX(), rectangleCabine.getMaxY());

			cabineTrapeze = new Path2D.Double();
			cabineTrapeze.moveTo(p4.getX(), p4.getY());
			cabineTrapeze.lineTo(p5.getX(), p5.getY());
			cabineTrapeze.lineTo(p6.getX(), p6.getY());
			cabineTrapeze.lineTo(p7.getX(), p7.getY());
			cabineTrapeze.closePath();
			aireCabineTrapeze = new Area(cabineTrapeze);
			cabine.add(aireCabineTrapeze);

			// Forme de la cheminée
			rectangleCheminee = new Rectangle2D.Double(rectangleCabine.getMinX() + ESPACE_AVEC_BORDS,
					rectangleCabine.getMinY() + 2, LARGEUR_CHEMINEE, rectangleCabine.getHeight() - 4);
			largeurEllipse = 3 * rectangleCheminee.getWidth() / 5;
			hauteurEllipse = 4 * rectangleCheminee.getHeight() / 5;
			trouCheminee = new Ellipse2D.Double(rectangleCheminee.getCenterX() - largeurEllipse / 2,
					rectangleCheminee.getCenterY() - hauteurEllipse / 2, largeurEllipse, hauteurEllipse);

			// Canon
			baseCanon = new Ellipse2D.Double(rectangleCabine.getMinX() - 10, corps.getCenterY() - 2.5, 5, 5);
			canon = new Rectangle2D.Double(coorX + 1, baseCanon.getCenterY() - ESPACE_AVEC_BORDS,
					1.5f * baseCanon.getWidth(), baseCanon.getHeight() / 3);
			Area canonAireCruiser = new Area(canon);

			aireCanon = new Area(baseCanon);
			aireCanon.add(canonAireCruiser);

			// Cabine haut
			rectangleCabine = new Rectangle2D.Double(corps.getCenterX() + 5, coorY + 2 * ESPACE_AVEC_BORDS,
					2 * LARGEUR_CABINE, largeurBateau - 4 * ESPACE_AVEC_BORDS);
			deuxiemeCabine = new Area(rectangleCabine);

			p4 = new Point2D.Double(rectangleCabine.getMaxX(), rectangleCabine.getMinY());
			p5 = new Point2D.Double(p4.getX() + LARGEUR_CABINE, p4.getY() + rectangleCabine.getHeight() / 3);
			p6 = new Point2D.Double(p5.getX(), p5.getY() + rectangleCabine.getHeight() / 3);
			p7 = new Point2D.Double(rectangleCabine.getMaxX(), rectangleCabine.getMaxY());

			cabineTrapeze = new Path2D.Double();
			cabineTrapeze.moveTo(p4.getX(), p4.getY());
			cabineTrapeze.lineTo(p5.getX(), p5.getY());
			cabineTrapeze.lineTo(p6.getX(), p6.getY());
			cabineTrapeze.lineTo(p7.getX(), p7.getY());
			cabineTrapeze.closePath();
			aireCabineTrapeze = new Area(cabineTrapeze);
			deuxiemeCabine.add(aireCabineTrapeze);

			break;

		case "porte-avion":
			corps = new Rectangle2D.Double(coorX, coorY, longueurBateau, 2 * largeurBateau / 3);
			aireCorps = new Area(corps);

			// Trapèze correspondant aux ailerons du sous marin
			p1 = new Point2D.Double(coorX, corps.getMaxY());
			p2 = new Point2D.Double(coorX + 5, coorY + largeurBateau);
			p3 = new Point2D.Double(coorX + longueurBateau - 10, coorY + largeurBateau);
			p4 = new Point2D.Double(coorX + longueurBateau, corps.getMaxY());

			Path2D.Double resteCoque = new Path2D.Double();
			resteCoque.moveTo(p1.getX(), p1.getY());
			resteCoque.lineTo(p2.getX(), p2.getY());
			resteCoque.lineTo(p3.getX(), p3.getY());
			resteCoque.lineTo(p4.getX(), p4.getY());
			resteCoque.closePath();
			coque = new Area(resteCoque);
			coque.add(aireCorps);

			// Forme de la cabine de pilotage
			rectangleCabine = new Rectangle2D.Double(coorX + corps.getWidth() / 4, p2.getY() - 6, 15, 6);
			cabine = new Area(rectangleCabine);

			// Lignes de la piste de décollage
			Rectangle2D.Double ligne1 = new Rectangle2D.Double(coorX + 1, coorY + 1, longueurBateau - 5, 0.5);
			Area ligne1A = new Area(ligne1);
			Rectangle2D.Double ligne2 = new Rectangle2D.Double(coorX + 1, rectangleCabine.getMinY() - 2,
					longueurBateau - 5, 0.5);

			aireLignesPA = new Area(ligne2);
			aireLignesPA.add(ligne1A);

			Area avion1 = avion(rectangleCabine.getMaxX() + 2, rectangleCabine.getMinY() - 2);
			Area avion2 = avion(avion1.getBounds().getMaxX() + 2, rectangleCabine.getMinY() - 2);
			Area avion3 = avion(avion2.getBounds().getMaxX() + 2, rectangleCabine.getMinY() - 2);
			Area avion4 = avion(avion3.getBounds().getMaxX() + 2, rectangleCabine.getMinY() - 2);

			aireAvions = new Area(avion1);
			aireAvions.add(avion2);
			aireAvions.add(avion3);
			aireAvions.add(avion4);
		}

	}

	/**
	 * Méthode permettant de dessiner le bateau.
	 * 
	 * @param g2d le contexte graphique
	 */
	// Théo Fourteau
	@Override
	public void dessiner(Graphics2D g2d) {

		Graphics2D g2dPriv = (Graphics2D) g2d.create();
		g2dPriv.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		switch (typeBateau) {

		case "frigate":
			g2dPriv.setColor(couleurCoque);
			g2dPriv.fill(coque);

			g2dPriv.setColor(couleurCabine);
			g2dPriv.fill(cabine);

			g2dPriv.setColor(Color.LIGHT_GRAY);
			g2dPriv.fill(rectangleCheminee);

			g2dPriv.setColor(Color.DARK_GRAY);
			g2dPriv.fill(rectangleCargaisonFrigate);
			g2dPriv.fill(trouCheminee);

			break;

		case "destroyer":
			g2dPriv.setColor(couleurCoque);
			g2dPriv.fill(coque);

			g2dPriv.setColor(couleurCabine);
			g2dPriv.fill(cabine);

			g2dPriv.setColor(Color.LIGHT_GRAY);
			g2dPriv.fill(rectangleCheminee);

			g2dPriv.setColor(Color.DARK_GRAY);
			g2dPriv.fill(trouCheminee);
			g2dPriv.fill(aireCanon);

			// Dessin de l'héliport
			g2dPriv.setColor(couleurHeliport);
			g2dPriv.fill(heliportDestroyer);
			g2dPriv.setColor(Color.WHITE);
			g2dPriv.draw(heliportDestroyer);
			g2dPriv.fill(lettreH);

			break;
		case "sous-marin":

			g2dPriv.setColor(couleurCoqueSousMarin);
			g2dPriv.fill(coque);
			g2dPriv.setColor(Color.BLACK);
			g2dPriv.fill(tourSousMarin);

			break;

		case "cruiser":
			g2dPriv.setColor(couleurCoque);
			g2dPriv.fill(coque);

			g2dPriv.setColor(couleurCabine);
			g2dPriv.fill(cabine);

			g2dPriv.setColor(Color.WHITE);
			g2dPriv.fill(deuxiemeCabine);

			g2dPriv.setColor(Color.LIGHT_GRAY);
			g2dPriv.fill(rectangleCheminee);

			g2dPriv.setColor(Color.DARK_GRAY);
			g2dPriv.fill(trouCheminee);
			g2dPriv.fill(aireCanon);
			g2dPriv.fill(aireDeStockage);

			break;

		case "porte-avion":
			g2dPriv.setColor(couleurCoque);
			g2dPriv.fill(coque);

			g2dPriv.setColor(couleurCabine);
			g2dPriv.fill(cabine);

			g2dPriv.setColor(couleurLignesPA);
			g2dPriv.fill(aireLignesPA);

			g2dPriv.setColor(Color.WHITE);
			g2dPriv.fill(aireAvions);

		}

	}

	/**
	 * Méthode permettant d'obtenir l'indice unique de sérialisaion de la classe
	 * 
	 * @return serialversionuid l'indice unique de sérialisation de la classe.
	 */
	// Théo Fourteau
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Méthode permettant de créer l'aire d'un avion qui peut être dessiné sur le
	 * porte-avion.
	 * 
	 * @param coorX la coordonnée du coin supérieur en x de l'avion
	 * @param coorY la coordonnée du coin supérieur en y de l'avion
	 * @return aireAvion l'aire de l'avion que l'on souhaite dessiner.
	 */
	// Théo Fourteau
	private Area avion(double coorX, double coorY) {

		// Géométrie de l'avion
		Point2D.Double p1 = new Point2D.Double(coorX, coorY + 2 * LONGUEUR_AVION / 3);
		Point2D.Double p2 = new Point2D.Double(coorX + 1, coorY + LONGUEUR_AVION / 3);
		
		// Cockpit
		Point2D.Double p3 = new Point2D.Double(coorX + 1.5, coorY + LONGUEUR_AVION / 3);
		Point2D.Double p4 = new Point2D.Double(coorX + (LARGEUR_AVION / 2), coorY);
		Point2D.Double p5 = new Point2D.Double(coorX + LARGEUR_AVION - 1.5, coorY + LONGUEUR_AVION / 3);
		
		// Ailes
		Point2D.Double p6 = new Point2D.Double(coorX + LARGEUR_AVION - 1, coorY + LONGUEUR_AVION / 3);
		Point2D.Double p7 = new Point2D.Double(coorX + LARGEUR_AVION, coorY + 2 * LONGUEUR_AVION / 3);
		Point2D.Double p8 = new Point2D.Double(coorX + LARGEUR_AVION - 1.5, coorY + 2 * LONGUEUR_AVION / 3);
		Point2D.Double p9 = new Point2D.Double(coorX + LARGEUR_AVION - 2, coorY + LONGUEUR_AVION);
		Point2D.Double p10 = new Point2D.Double(coorX + 2, coorY + LONGUEUR_AVION);
		Point2D.Double p11 = new Point2D.Double(coorX + 1.5, coorY + 2 * LONGUEUR_AVION / 3);

		Path2D.Double traceAvion = new Path2D.Double();
		traceAvion.moveTo(p1.getX(), p1.getY());
		traceAvion.lineTo(p2.getX(), p2.getY());
		traceAvion.lineTo(p3.getX(), p3.getY());
		traceAvion.lineTo(p4.getX(), p4.getY());
		traceAvion.lineTo(p5.getX(), p5.getY());
		traceAvion.lineTo(p6.getX(), p6.getY());
		traceAvion.lineTo(p7.getX(), p7.getY());
		traceAvion.lineTo(p8.getX(), p8.getY());
		traceAvion.lineTo(p9.getX(), p9.getY());
		traceAvion.lineTo(p10.getX(), p10.getY());
		traceAvion.lineTo(p11.getX(), p11.getY());
		traceAvion.closePath();

		Area aireAvion = new Area(traceAvion);

		return aireAvion;
	}

}
