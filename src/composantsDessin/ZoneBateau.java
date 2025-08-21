package composantsDessin;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import geometrie.Bateau;
import geometrie.ChampElectrique;
import geometrie.Habiletes;
import geometrie.Missile;
import geometrie.Mur;
import geometrie.ZoneVent;
import outils.AjoutSon;
import outils.OutilsImage;
import outils.Vecteur3D;

/**
 * Classe ZoneBateau: Scène qui permet d'afficher les bateaux dans la scène
 * ainsi que de modifier leurs positions et leurs rotations si le mode d'édition
 * est sélectioné.
 * 
 * @author Noé Julien
 * @author Gabriel Maurice
 * @author Théo Fourteau
 */
public class ZoneBateau extends JPanel implements Runnable, Serializable {
	/** Identifiant unique pour la sérialisation **/
	private static final long serialVersionUID = 1L;
	/** Largeur de la scène (m) **/
	private double largeurDuComposantEnMetres = 425;
	/** Variable entière pour la hauteur de la zone en fond de la scène (m) **/
	private int hauteurDuFond = 150;
	/** Double statique pour le facteur de redimensionnement en pixels par mètre **/
	private static double pixelsParMetre;
	/** Valeur booléenne de dessin pour la première exécution **/
	private boolean premiereFois = true;
	/** Valeur booléenne de dessin pour mettre la scène en mode d'édition **/
	private boolean editeur = false;
	/**
	 * Valeur booléenne de dessin pour mettre la scène en mode d'édition pour les
	 * habiletés spéciales
	 **/
	private boolean editeurHabiletes = false;
	/** Valeur booléenne qui représente si un bateau est sélectioné **/
	private boolean bateauSelect = false;
	/** Valeur booléenne qui représente si l'animation est en cours **/
	private boolean enCoursDAnimation = false;
	/** Liste des différents objets rectangle utilisés par chaque bateau **/
	private List<Rectangle> rectangles = new ArrayList<>();
	/** Liste des différents objets de forme utilisés par chaque bateau **/
	private List<Shape> shapes = new ArrayList<>();
	/** Liste contenant les différents bateaux de la zones **/
	private List<Bateau> bateaux = new ArrayList<>();///////////////////
	/** Liste contenant les différentes largeurs des bateaux **/
	private List<Integer> largeurs = new ArrayList<>();
	/** Liste contenant les différentes largeurs des bateaux **/
	private List<Vecteur3D> positions = new ArrayList<>();
	/** Liste contenant les différents angles des rotations **/
	private List<Integer> angles = new ArrayList<>();
	/** Liste contenant les différentes positions en Y si en mode éditeur **/
	private List<Integer> positionsYEditeur = new ArrayList<>();

	/** Liste contenant les habiletés spéciales présente dans la zone de bateaux **/
	private List<Habiletes> habiletesSpeciales = new ArrayList<>();

	/** Liste contenant les trous dans les nuages **/
	private List<Shape> trous = new ArrayList<>();

	/** Variable entière pour représenter le nombre de bateau placés **/
	private int nbBateauxPlace;

	/** Variable entière pour de représenter la hateur de la zone d'eau (m) **/
	private int hauteurRectangles = 150;
	/** objet bateau qui est d'une longeur de 2 cases **/
	private Bateau bateau2 = new Bateau(2);
	/** objet bateau qui est d'une longeur de 3 cases **/
	private Bateau bateau3 = new Bateau(3);
	/** Deuxième objet bateau qui est d'une longeur de 3 cases **/
	private Bateau bateau31 = new Bateau(31);
	/** objet bateau qui est d'une longeur de 4 cases **/
	private Bateau bateau4 = new Bateau(4);
	/** objet bateau qui est d'une longeur de 5 cases **/
	private Bateau bateau5 = new Bateau(5);

	/** Vecteur position bateau de 2 cases en X et Y et Z **/
	private Vecteur3D position2 = new Vecteur3D(0, 0, 0);
	/** Vecteur position bateau de 3 cases en X et Y et Z **/
	private Vecteur3D position3 = new Vecteur3D(0, 0, 0);
	/** Deuxième vecteur position bateau de 3 cases en X et Y et Z **/
	private Vecteur3D position31 = new Vecteur3D(0, 0, 0);
	/** Vecteur position bateau de 4 cases en X et Y et Z **/
	private Vecteur3D position4 = new Vecteur3D(0, 0, 0);
	/** Vecteur position bateau de 5 cases en X et Y et Z **/
	private Vecteur3D position5 = new Vecteur3D(0, 0, 0);

	/** Valeur entière pour la position initale en X des bateaux en mode éditeur **/
	private int posXInital = 300;

	/** Rectangle représentant la zone pour les bateaux en mode éditeur **/
	private Rectangle zoneEdit;
	/** Objet rectangle représentant un bateau d'une longeur de 2 cases **/
	private Rectangle rect2 = new Rectangle();
	/** Objet rectangle représentant un bateau d'une longeur de 3 cases **/
	private Rectangle rect3 = new Rectangle();
	/** Objet rectangle représentant un deuxième bateau de 3 cases **/
	private Rectangle rect31 = new Rectangle();
	/** Objet rectangle représentant un bateau d'une longeur de 4 cases */
	private Rectangle rect4 = new Rectangle();
	/** Objet rectangle représentantun bateau d'une longeur de 5 cases */
	private Rectangle rect5 = new Rectangle();
	/** Objet rectangle représentant un bateau temporairement */
	private Rectangle rectangleTemp = new Rectangle();

	/** Objet forme qui représente la zone d'eau **/
	private Shape eau;
	/** Objet forme qui représente une forme temporaire **/
	private Shape shapeTemp;
	/** Objet forme qui représente un bateau d'une longeur de 2 cases */
	private Shape shape2 = new Rectangle();
	/** Objet forme qui représente un bateau d'une longeur de 3 cases */
	private Shape shape3 = new Rectangle();
	/** Objet forme qui représente un deuxième bateau d'une longeur de 3 cases */
	private Shape shape31 = new Rectangle();
	/** Objet forme qui représente un bateau d'une longeur de 4 cases */
	private Shape shape4 = new Rectangle();
	/** Objet forme qui représente un bateau d'une longeur de 5 cases */
	private Shape shape5 = new Rectangle();

	/** Valeur entiere pour la position de l'habilete appuye dans sa liste **/
	private int positionAppuyeHab;
	/** Valeur entière pour la position de l'object appuyé dans sa liste **/
	private int positionObjectAppuyer;
	/** Valeur entière représentant la rotation du bateau de deux cases **/
	private int angle2;
	/** Valeur entière représentant la rotation du bateau de trois cases **/
	private int angle3;
	/** Deuxième entier représentant la rotation du bateau de trois cases **/
	private int angle31;
	/** Valeur entière représentant la rotation du bateau de quatre cases **/
	private int angle4;
	/** Valeur entière représentant la rotation du bateau de cinq cases **/
	private int angle5;

	/** Valeur entière représentant l'écart de position entre des objets **/
	private int ecartPositionY = 8;
	/** Valeur entière pour déterminer déterminer la largeur des bateaux **/
	private int largeur1case = 15;
	/** Objet missile qui représente le missle dans le composant dessin **/
	private Missile missile;
	/** temps entre chaque frame **/
	private static double deltaT = 0.05;
	/** Champ electrique qui lorsque cliquée créé un nouveau champ electrique **/
	private ChampElectrique champElectriqueCliquable;
	/** Mur de protection qui lorsque cliqué crée un nouveau mur **/
	private Mur murCliquable;
	/** Zone de vent qui lorsque cliqué crée une nouvelle zone de vent **/
	private ZoneVent zoneVentCliquable;
	/**
	 * Variable String qui représente le terrain de jeu sélectionné venant des
	 * fichiers
	 **/
	private String terrainDeJeuFichier;
	/** Graphique du terrain de jeu **/
	private Image terrainImage;
	/** Graphique du terrain de jeu (ihcier **/
	private Image terrainImageFichier;
	/**
	 * Valeur booléenne qui représente si la zone doit être inversé à l'affichage
	 **/
	private boolean inverse;
	/** Forme rectangulaire servant à créer l'aire de la zone de nuages **/
	private Shape nuagesForme;
	/**
	 * Aire rectangulaire couvrant la zone des bateaux, qui est découverte quand le
	 * missile atteint la hauteur de 0m
	 */
	private transient Area nuages;
	/** Diamètre du cercle de l'impact du missile **/
	private final int DIAMETRE_IMPACT = 100;
	/** Valeur entière statique pour la largeur de la zone d'eau en mètre **/
	private static final int EAU_WIDTH = 250;
	/** Valeur boolean indiquant si la zoneBateau est passee par un fichier **/
	private boolean passerFichier = false;

	/** si le missile est dans les aires **/
	private boolean missileAire = false;

	/** Aire des baetaux visibles par l'IA (pour les tests) **/
	private Area aireIA;
	/**
	 * Ellipse dessinee sur le point choisi par l'IA (pour voir la precision du tire
	 * de l'IA
	 **/
	private Ellipse2D.Double pointIA;
	/** Si le match est joue contre l'IA **/
	private boolean matchContreIA;
	/** Si le match a debute **/
	private boolean matchCommence;
	/** Si c'est le tour de l'IA de tirer **/
	private boolean tourIA;
	/** Booléen qui indique si un bateau est touché par le missile **/
	private boolean touche = false;
	/** Booléen qui indique si l'eau est touchée par le missile **/
	private boolean aLeau = false;

	/**
	 * Minuteur qui montre les secondes restantes avant le tour du prochain joueur
	 **/
	private Timer timer;
	/** Temps maximal pour la pause avant de changer de tour **/
	private final int TEMPS_MAX_TOUR = 5;
	/** Valeur entière qui définie le temps restant au minuteur en secondes */
	private int tempsRestant = TEMPS_MAX_TOUR;
	/** Valeur boolean qui indique si les tours sont en transition **/
	private boolean transitionEntreTours = false;
	/** Valeur boolean qui indique si la zone masquee a un minuteur **/
	private boolean masquerZoneMinuteur = false;
	/** le tour du joueur **/
	private int tourDuJoueur;
	/** Accélération gravitationnelle en m/s² */
	private double accelGrav;
	/** Valeur boolean qui permet l'utilisation d'une seule habilete **/
	private boolean habileteMax = true;
	/** Indique si l'habileté est déplaçable ou non */
	private boolean habileteBougeable = false;
	/** Rectangle utilisé pour l'échelle. */
	private Rectangle rectScale = new Rectangle();
	/** Largeur du rectangle de l'échelle. */
	private int rectScaleLargeur = 40;
	/** Position X du rectangle de l'échelle. */
	private int rectScaleX = 207;
	/** Position Y du rectangle de l'échelle. */
	private int rectScaleY = 143;
	/** Hauteur du rectangle de l'échelle. */
	private int rectScaleHauteur = 5;
	/** Valeur boolean qui determine si l'habilete a ete touchee **/
	private boolean habileteTouchee = false;
	/** Valeur entiere qui determine la derniere habilete apparue **/
	private int derniereHabilete;
	/** Valeur boolean qui determine si on dessine l'habilete passee **/
	private boolean dessinHabiletePasse = false;
	/** Valeur boolean qui determine si le mode testeur est active **/
	private boolean modeTesteur;
	/** Valeur entier qui represente l'habilete selectionnee dans le cycle **/
	private int cycleHabilete = 0;
	/** Valeur boolean qui determine si les habiletes sont debloquee **/
	private boolean debloquerHabiletes = false;
	/** Image de fond des nuages **/
	private BufferedImage nuagesImage;
	/** Valeur boolean qui determine si l'habilete doit etre replacee **/
	private boolean replacerHab = false;
	/** Valeur entiere qui indique le type de l'habilete appuyee **/
	private int habileteCorrespondant;
	/**
	 * Valeur entiere qui represente l'ajustement a faire pour centrer la
	 * description du champ electrique
	 **/
	private final int AJUSTEMENT_TEXT_CHAMP = 220;
	/**
	 * Valeur entiere qui represente l'ajustement a faire pour centrer la
	 * description du mur
	 **/
	private final int AJUSTEMENT_TEXT_MUR = 180;
	/**
	 * Valeur entiere qui represente l'ajustement a faire pour centrer la
	 * description du vent
	 **/
	private final int AJUSTEMENT_TEXT_VENT = 255;
	/** Objet forme qui représente une forme temporaire(habilete) **/
	private Shape shapeHabileteTemp;
	/** Valeur boolean qui determine si l'habilete peut etre relachee **/
	private boolean relacheHab = false;
	/** Valeur boolean qui determine si l'animation est en arrêt **/
	private boolean arret = false;
	/** Valeur boolean qui determine si la zone est en mode tutoriel **/
	private boolean tutoriel = false;

	/**
	 * Méthode contructeur qui permet de créer les différents bateaux dans la scène
	 * avec un mode éditeur si besoin et un mode non éditeur qui a des positions
	 * différentes. Il construit aussi les rectangle et le missle qui est dans la
	 * fennêtre. De plus, les MotionListener et les MouseListener sont créés.
	 * 
	 * @param editeur      valeur booléenne qui détermine si le mode éditeur doit
	 *                     être activé
	 * @param terrainDeJeu terrain de jeu selectionné en String
	 * 
	 * @param modeTesteur  valeur booléenne qui détermine si le mode testeur est
	 *                     activee
	 */
	// Noé Julien
	public ZoneBateau(boolean editeur, String terrainDeJeu, boolean modeTesteur) {

		terrainDeJeuFichier = terrainDeJeu;
		this.modeTesteur = modeTesteur;

		addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {
				sourisTraine(e);
				sourisTraineHabiletes(e);
			}

		});

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				sourisRoueCycle(e);
			}
		});
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				sourisAppuyer(e);
				sourisAppuyeeHabiletes(e);
			}

			public void mouseReleased(MouseEvent e) {
				sourisRelache();
				sourisRelacheeHabiletes();

			}

		});
		shapes.add(shape2);
		shapes.add(shape3);
		shapes.add(shape31);
		shapes.add(shape4);
		shapes.add(shape5);
		positions.add(position2);
		positions.add(position3);
		positions.add(position31);
		positions.add(position4);
		positions.add(position5);
		largeurs.add(2 * largeur1case);
		largeurs.add(3 * largeur1case);
		largeurs.add(3 * largeur1case);
		largeurs.add(4 * largeur1case);
		largeurs.add(5 * largeur1case);
		rectangles.add(rect2);
		rectangles.add(rect3);
		rectangles.add(rect31);
		rectangles.add(rect4);
		rectangles.add(rect5);
		bateaux.add(bateau2);
		bateaux.add(bateau3);
		bateaux.add(bateau31);
		bateaux.add(bateau4);
		bateaux.add(bateau5);
		angles.add(angle2);
		angles.add(angle3);
		angles.add(angle31);
		angles.add(angle4);
		angles.add(angle5);
		positionsYEditeur.add(ecartPositionY);
		positionsYEditeur.add(ecartPositionY * 2 + largeur1case);
		positionsYEditeur.add(ecartPositionY * 3 + 2 * largeur1case);
		positionsYEditeur.add(ecartPositionY * 4 + 3 * largeur1case);
		positionsYEditeur.add(ecartPositionY * 5 + 4 * largeur1case);

		for (int i = 0; i < bateaux.size(); i++) {
			Bateau bateau = bateaux.get(i);
			Rectangle rect = rectangles.get(i);
			Vecteur3D vect = positions.get(i);

			bateau.setPosition(vect.getX(), vect.getY());
			rect.setBounds((int) vect.getX(), (int) vect.getY(), largeurs.get(i), largeur1case);
		}

		this.setEditeur(editeur);
		this.zoneEdit = new Rectangle(275, 0, hauteurDuFond, hauteurDuFond);
		this.eau = new Rectangle(0, 0, EAU_WIDTH, hauteurDuFond);
		missile = new Missile();
		missile.setPosition(new Vecteur3D(eau.getBounds().getWidth() / 2, 0, 0));

		// IMPORTANT
		// CHANGER LES COORDONNÉES POUR DES VARIABLES AU LIEU DES NOMBRES!!
		champElectriqueCliquable = new ChampElectrique((int) (zoneEdit.getMinX() + zoneEdit.getWidth() / 8),
				(int) (zoneEdit.getMaxY() - zoneEdit.getHeight() / 10));
		murCliquable = new Mur((int) (zoneEdit.getCenterX()), (int) (zoneEdit.getMaxY() - zoneEdit.getHeight() / 10));
		zoneVentCliquable = new ZoneVent((int) (zoneEdit.getMaxX() - zoneEdit.getWidth() / 8),
				(int) (zoneEdit.getMaxY() - zoneEdit.getHeight() / 10));

		creerTerrainImage(terrainDeJeu);

		afficherPositionBateaux();

		URL urlNuages = getClass().getClassLoader().getResource("ImageNuages.png");

		try {
			nuagesImage = ImageIO.read(urlNuages);
		} catch (Exception e) {
			System.out.println("Erreur de lecture du fichier de texture");
		}

	}

	/**
	 * Dessine une scène de montrant les bateaux, l'espace d'eau ainsi que la
	 * section d'édition si sélectioné
	 * 
	 * @param g contexte graphique
	 */
	// Noé Julien
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		AffineTransform transformInitial = g2d.getTransform();

		if (inverse) {
			double angleRadians = Math.PI;
			double centerX = EAU_WIDTH * pixelsParMetre / 2.0;
			double centerY = hauteurRectangles * pixelsParMetre / 2.0;
			AffineTransform rotationTransform = AffineTransform.getRotateInstance(angleRadians, centerX, centerY);
			g2d.transform(rotationTransform);

		}

		// Si la première fois
		if (premiereFois) {
			pixelsParMetre = getWidth() / largeurDuComposantEnMetres;
			premiereFois = false;
			Bateau.pixelsParMetre = pixelsParMetre;
			System.out.println("pixels par mètre: " + pixelsParMetre);

			nuagesForme = new Rectangle2D.Double(0, 0, 250, hauteurRectangles);
			nuages = new Area(nuagesForme);

			if (passerFichier) {
				for (int i = 0; i < trous.size(); i++) {
					Area aireCercleImpact = new Area(trous.get(i));
					nuages.subtract(aireCercleImpact);
				}
			}

		}

		g2d.scale(pixelsParMetre, pixelsParMetre);

		if (passerFichier) {
			g2d.drawImage(terrainImageFichier, 0, 0, EAU_WIDTH, hauteurRectangles, null);
		} else {
			g2d.drawImage(terrainImage, 0, 0, EAU_WIDTH, hauteurRectangles, null);
		}

		String titreChamp = "Bouclier électrique";
		String champDescription = "Dispositif de grande envergure\npour perturber la trajectoire\ndes missiles ennemis. Plus le\nmissile s'approche du centre, plus\nsa trajectoire est modifiée.";

		String titreMur = "Ligne de défense";
		String murDescription = "Mécanisme permettant de contrer\nles missiles adverses. Son épaisseur\net son renforcement en titane permet\nd’absorber entièrement la collision avec\nun missile. Bien positionné: il peut\nse révéler très efficace.";

		String titreVent = "Zone de tempête";
		String ventDescription = "Système de pointe ayant comme fonction\n d’appliquer une énorme force éolienne\n dans une direction souhaitée. Sa puissance\n permet de ralentir voire propulser\n le missile dans une direction donnée.";

		if (editeurHabiletes) {

			if (!habileteMax) {
				int habileteADessinerSelonCas;

				if (modeTesteur) {
					habileteADessinerSelonCas = cycleHabilete;
				} else {

					if (dessinHabiletePasse) {
						habileteADessinerSelonCas = derniereHabilete;
					} else {
						Random hasard = new Random();
						int nombreAleatoire = hasard.nextInt(3) + 1;
						habileteADessinerSelonCas = nombreAleatoire;
					}
				}
				if (replacerHab) {
					habileteADessinerSelonCas = habileteCorrespondant;
				}
				switch (habileteADessinerSelonCas) {

				case 1:

					Font lettresTitreHabilete = new Font("verdana", Font.BOLD, 10);
					g2d.setFont(lettresTitreHabilete);

					g2d.setColor(Color.BLUE);
					FontMetrics fmChamp = g2d.getFontMetrics();
					int largeurTextChamp = fmChamp.stringWidth(titreChamp);
					int hauteurTextChamp = fmChamp.getAscent();
					int centreXChamp = (int) (zoneEdit.getX() + (zoneEdit.getWidth() - largeurTextChamp) / 2);
					int centreYChamp = (int) (zoneEdit.getY() + ((zoneEdit.getHeight() - hauteurTextChamp) / 2)
							+ fmChamp.getAscent());
					g2d.drawString(titreChamp, centreXChamp, centreYChamp - 70);

					Font lettresHabileteDs = new Font("verdana", Font.BOLD, 7);
					g2d.setFont(lettresHabileteDs);

					g2d.setColor(Color.WHITE);

					FontMetrics fmChampDs = g2d.getFontMetrics();
					int largeurTextChampDs = fmChampDs.stringWidth(champDescription);
					int hauteurTextChampDs = fmChampDs.getAscent();
					int centreXChampDs = (int) (zoneEdit.getX() + (zoneEdit.getWidth() - largeurTextChampDs) / 2);
					int centreYChampDs = (int) (zoneEdit.getY() + ((zoneEdit.getHeight() - hauteurTextChampDs) / 2)
							+ fmChamp.getAscent());

					// on separe le texte par \n. de cette facon, dessiner un string pour chaque
					// ligne de texte"
					String[] nbDeLigneChamp = champDescription.split("\n");
					int hauteurLigneChamp = g2d.getFontMetrics().getHeight();

					for (int i = 0; i < nbDeLigneChamp.length; i++) {
						g2d.drawString(nbDeLigneChamp[i], centreXChampDs + AJUSTEMENT_TEXT_CHAMP,
								centreYChampDs - 50 + i * hauteurLigneChamp);
					}

					derniereHabilete = 1;
					champElectriqueCliquable.dessiner(g2d);
					break;

				case 2:
					lettresTitreHabilete = new Font("verdana", Font.BOLD, 10);
					g2d.setFont(lettresTitreHabilete);

					g2d.setColor(Color.RED);
					FontMetrics fmMur = g2d.getFontMetrics();
					int largeurTextMur = fmMur.stringWidth(titreMur);
					int hauteurTextMur = fmMur.getAscent();
					int centreXMur = (int) (zoneEdit.getX() + (zoneEdit.getWidth() - largeurTextMur) / 2);
					int centreYMur = (int) (zoneEdit.getY() + ((zoneEdit.getHeight() - hauteurTextMur) / 2)
							+ fmMur.getAscent());
					g2d.drawString(titreMur, centreXMur, centreYMur - 70);

					lettresHabileteDs = new Font("verdana", Font.BOLD, 6);
					g2d.setFont(lettresHabileteDs);

					g2d.setColor(Color.WHITE);

					FontMetrics fmMurDs = g2d.getFontMetrics();
					int largeurTextMurDs = fmMurDs.stringWidth(champDescription);
					int hauteurTextMurDs = fmMurDs.getAscent();
					int centreXMurDs = (int) (zoneEdit.getX() + (zoneEdit.getWidth() - largeurTextMurDs) / 2);
					int centreYMurDs = (int) (zoneEdit.getY() + ((zoneEdit.getHeight() - hauteurTextMurDs) / 2)
							+ fmMurDs.getAscent());

					// on separe le texte par \n. de cette facon, dessiner un string pour chaque
					// ligne de texte"
					String[] nbDeLigneMur = murDescription.split("\n");
					int hauteurLigneMur = g2d.getFontMetrics().getHeight();

					for (int i = 0; i < nbDeLigneMur.length; i++) {
						g2d.drawString(nbDeLigneMur[i], centreXMurDs + AJUSTEMENT_TEXT_MUR,
								centreYMurDs - 50 + i * hauteurLigneMur);
					}

					derniereHabilete = 2;
					murCliquable.dessiner(g2d);
					break;

				case 3:
					lettresTitreHabilete = new Font("verdana", Font.BOLD, 10);
					g2d.setFont(lettresTitreHabilete);

					g2d.setColor(Color.CYAN);
					FontMetrics fmVent = g2d.getFontMetrics();
					int largeurTextVent = fmVent.stringWidth(titreVent);
					int hauteurTextVent = fmVent.getAscent();
					int centreXVent = (int) (zoneEdit.getX() + (zoneEdit.getWidth() - largeurTextVent) / 2);
					int centreYVent = (int) (zoneEdit.getY() + ((zoneEdit.getHeight() - hauteurTextVent) / 2)
							+ fmVent.getAscent());
					g2d.drawString(titreVent, centreXVent, centreYVent - 70);

					lettresHabileteDs = new Font("verdana", Font.BOLD, 6);
					g2d.setFont(lettresHabileteDs);

					g2d.setColor(Color.WHITE);

					FontMetrics fmVentDs = g2d.getFontMetrics();
					int largeurTextVentDs = fmVentDs.stringWidth(ventDescription);
					int hauteurTextVentDs = fmVentDs.getAscent();
					int centreXVentDs = (int) (zoneEdit.getX() + (zoneEdit.getWidth() - largeurTextVentDs) / 2);
					int centreYVentDs = (int) (zoneEdit.getY() + ((zoneEdit.getHeight() - hauteurTextVentDs) / 2)
							+ fmVentDs.getAscent());

					String[] nbDeLigneVent = ventDescription.split("\n");
					int hauteurLigneVent = g2d.getFontMetrics().getHeight();

					for (int i = 0; i < nbDeLigneVent.length; i++) {
						g2d.drawString(nbDeLigneVent[i], centreXVentDs + AJUSTEMENT_TEXT_VENT,
								centreYVentDs - 50 + i * hauteurLigneVent);
					}

					derniereHabilete = 3;
					zoneVentCliquable.dessiner(g2d);
					break;
				}

			}
		}

		// Dessin des habiletés spéciales
		int nbHabiletes = habiletesSpeciales.size();
		if (nbHabiletes > 0) {
			for (int i = 0; i < nbHabiletes; i++) {
				habiletesSpeciales.get(i).dessiner(g2d);
			}
		}

		Bateau.setAffichAngle(editeur);
		g2d.setColor(Color.pink);
		for (int i = 0; i < bateaux.size(); i++) {
			Bateau bateau = bateaux.get(i);
			bateau.dessiner(g2d);
			g2d.setColor(new Color(0.7f, 0.5f, 0.5f, 0.5f));
		}

		if (inverse) {

			TexturePaint texturePaintNuages = new TexturePaint(nuagesImage, nuages.getBounds());
			g2d.setPaint(texturePaintNuages);
			g2d.fill(nuages);

			missile.dessiner(g2d);

			if (touche) {
				Image explosion = OutilsImage.lireImage("explosion1.png");

				g2d.drawImage(explosion,
						(int) missile.getPosition().getX() - explosion.getWidth(getFocusCycleRootAncestor()) / 2,
						(int) missile.getPosition().getY() - explosion.getHeight(getFocusCycleRootAncestor()) / 2,
						null);

			} else if (aLeau) {
				Image splash = null;

				switch (terrainDeJeuFichier) {

				case "Terre.jpg":
					splash = OutilsImage.lireImage("splashEau1.png");
					break;

				case "Vesperion Prime.jpg":
					splash = OutilsImage.lireImage("splashVesperion.png");
					break;

				case "Celestia-47B.jpg":
					splash = OutilsImage.lireImage("splashCelestia.png");
					break;
				} // fin switch

				g2d.drawImage(splash,
						(int) missile.getPosition().getX() - splash.getWidth(getFocusCycleRootAncestor()) / 2,
						(int) missile.getPosition().getY() - splash.getHeight(getFocusCycleRootAncestor()) / 2, null);

			}
		}
		if (matchContreIA && matchCommence && tourIA) {
			g2d.setColor(new Color(1f, 0f, 0f, 0.8f));
			g2d.fill(aireIA);
			g2d.setColor(Color.green);
			g2d.fill(pointIA);

		}
		creerShapeBateaux();

		if (transitionEntreTours) {
			Rectangle2D.Double masquerTerrain = new Rectangle2D.Double(0, 0, 250, hauteurRectangles);
			g2d.setColor(Color.BLACK);
			g2d.fill(masquerTerrain);

			if (masquerZoneMinuteur) {
				String indicateur = "Tour du joueur " + tourDuJoueur + " dans...";
				if (tourDuJoueur == 1) {
					g2d.setColor(Color.BLUE);
				} else {
					g2d.setColor(Color.RED);
				}
				AffineTransform rotationTransformIndi = AffineTransform.getRotateInstance(Math.PI, 125,
						hauteurRectangles / 2 - 20);
				g2d.transform(rotationTransformIndi);
				Font lettresJoueur = new Font("verdana", Font.BOLD, 10);
				g2d.setFont(lettresJoueur);

				FontMetrics fm = g2d.getFontMetrics();
				int largeurTextIndi = fm.stringWidth(indicateur);
				int hauteurTextIndi = fm.getAscent();

				int centreXIndi = (int) (masquerTerrain.getX() + (masquerTerrain.getWidth() - largeurTextIndi) / 2);
				int centreYIndi = (int) (masquerTerrain.getY() + ((masquerTerrain.getHeight() - hauteurTextIndi) / 2)
						+ fm.getAscent());

				g2d.drawString(indicateur + "", centreXIndi, centreYIndi - 40);

				int secondes = tempsRestant % 60;
				g2d.setColor(Color.WHITE);
				AffineTransform rotationTransformSec = AffineTransform.getRotateInstance(0, 125, hauteurRectangles / 2);
				g2d.transform(rotationTransformSec);

				Font lettresSeconde = new Font("verdana", Font.BOLD, 50);
				g2d.setFont(lettresSeconde);

				FontMetrics fm2 = g2d.getFontMetrics();
				int largeurTextSec = fm2.stringWidth(Integer.toString(secondes));
				int hauteurTextSec = fm2.getAscent();

				int centreXSec = (int) (masquerTerrain.getX() + (masquerTerrain.getWidth() - largeurTextSec) / 2);
				int centreYSec = (int) (masquerTerrain.getY() + (masquerTerrain.getHeight() - hauteurTextSec) / 2)
						+ fm2.getAscent();

				g2d.drawString(secondes + "", centreXSec, centreYSec);

			}
		}

		g2d.setTransform(transformInitial);
		g2d.scale(pixelsParMetre, pixelsParMetre);

		g2d.setColor(Color.black);
		rectScale.setBounds(rectScaleX, rectScaleY, rectScaleLargeur, rectScaleHauteur);
		g2d.fill(rectScale);

		int x = rectScaleX + rectScaleLargeur / 2;
		int y = rectScaleY + rectScaleHauteur / 2;

		g2d.setColor(new Color(11, 207, 20));
		Font lettresScale = new Font("verdana", Font.BOLD, 5);
		g2d.setFont(lettresScale);

		int largeurString = 4;
		int hauteurString = 2;

		g2d.drawString(rectScaleLargeur + "m", x - largeurString, y + hauteurString);

		g2d.translate(0, 0);
		int longueurRecNbBateaux = 130;
		int largeurRecNbBateaux = 10;

		// AlphaComposite => sert à changer l'opacité du rectangle qui fait un contraste
		// léger avec l'eau.
		// Cela permet donc de mieux voir le texte même s'il est noir.
		g2d.setColor(Color.WHITE);
		AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g2d.setComposite(alphaComposite);

		Rectangle2D.Double recNbBateaux = new Rectangle2D.Double(0, 0, longueurRecNbBateaux, largeurRecNbBateaux);
		g2d.fill(recNbBateaux);

		g2d.setColor(Color.BLACK);
		alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g2d.setComposite(alphaComposite);

		g2d.setFont(new Font("Graphik", Font.BOLD, 8));
		g2d.drawString("Nombre de bateaux restant: " + bateaux.size() + "/5", 3, 8);

	}

	/**
	 * Creer et demarrer le thread
	 */
	// Gabriel Maurice
	public void demarrer() {
		missileAire = true;
		enCoursDAnimation = true;

		if (tutoriel) {
			missile.lancerMissile();
			missile.setAccel(new Vecteur3D(0, 0, accelGrav));
			System.out.println("tutoriel: true ");
		} else if (!arret) {
			missile.lancerMissile();
			missile.setAccel(new Vecteur3D(0, 0, accelGrav));
			System.out.println("!arret");
		}
		Thread processusAnim = new Thread(this);
		processusAnim.start();
		missile.printVitesseEtPosition();

	}

	/**
	 * La methode qui sera executee quand le thread sera demarre Gere les collision
	 * (regarde a chaque frame s'il y a collision avec le sol, les murs, les nuages
	 * ou les differentes habiletes)
	 */
	// Gabriel Maurice
	public void run() {
		arret = false;
		touche = false;
		aLeau = false;
		boolean lance = true;

		while (enCoursDAnimation) {
			sonLancement(lance);
			lance = false;
			missile.calculerUnFrame(deltaT);
			collisionBateaux();
			interactionElements();

			// Collision avec l'eau
			if (missile.getPosition().getZ() <= 0 && touche == false) {
				aLeau = true;
				repaint();
				sonSplash();

				// Pause de 1 sec
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Processus interrompu!");
				}
				aLeau = false;
			}

			if (arret) {
				break;
			}
			repaint();

			// pause
			try {
				Thread.sleep((int) (deltaT * 1000));
			} catch (InterruptedException e) {
				System.out.println("Processus interrompu!");
			}
		}
	}

	/**
	 * Méthode pour gérer l'interaction entre le missile et les bateaux
	 */
	// Gabriel Maurice
	public void collisionBateaux() {
		// collision bateaux
		for (int i = 0; i < shapes.size(); i++) {
			Area aireBateau = new Area(shapes.get(i));

			if (aireBateau.contains(missile.getMissilePoint()) && missile.getCollisionSol()) {

				touche = true;
				repaint();
				sonExplosion();

				// Pause de 2 sec
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					System.out.println("Processus interrompu!");
				}
				touche = false;
				bateaux.get(i).setTouche(true);
				bateaux.remove(i);
				shapes.remove(i);
				rectangles.remove(i);
				largeurs.remove(i);
				positions.remove(i);
				angles.remove(i);

			}
		}
	}

	/**
	 * Méthode pour gérer l'interaction entre le missile et les éléments présents
	 */
	// Gabriel Maurice
	public void interactionElements() {
		// interaction avec les habiletes
		missile.setAccel(new Vecteur3D(0, 0, accelGrav));
		Iterator<Habiletes> iterator = habiletesSpeciales.iterator();
		while (iterator.hasNext()) {
			Habiletes h = iterator.next();

			// interaction zone de vent
			if (h instanceof ZoneVent) {
				ZoneVent z = new ZoneVent((int) h.getCentreX(), (int) h.getCentreY());

				if (z.contient(missile.getMissilePoint().getX(), missile.getMissilePoint().getY())) {
					Vecteur3D a = missile.getAccel();
					missile.appliquerForce(new Vecteur3D(ZoneVent.getForceVent() * Math.cos(z.getAngleVent()),
							ZoneVent.getForceVent() * Math.sin(z.getAngleVent()), 0));
					missile.setAccel(Vecteur3D.additionne(missile.getAccel(), a));
				}
			}
			// collision mur
			if (h instanceof Mur) {
				Mur m = new Mur((int) h.getCentreX(), (int) h.getCentreY());
				if (m.contient(missile.getPosition().getX(), missile.getPosition().getY())
						&& missile.getPosition().getZ() <= m.getHauteurMur()) {
					iterator.remove(); // Utilisation de l'itérateur pour supprimer l'élément
					missile.collisionMur();
				}
			}

			// interaction champ electrique
			if (h instanceof ChampElectrique) {
				ChampElectrique e = new ChampElectrique((int) h.getCentreX(), (int) h.getCentreY());
				if (e.contient(missile.getMissilePoint().getX(), missile.getMissilePoint().getY())) {
					Vecteur3D a = missile.getAccel();
					missile.appliquerForce(
							e.ForceChampElectrique(missile.getPosition().getX(), missile.getPosition().getY()));
					missile.setAccel(Vecteur3D.additionne(missile.getAccel(), a));
				}
			}
		}

		// collision avec les murs de gauche et droite
		if ((missile.getPosition().getX() <= 0 || missile.getPosition().getX() >= eau.getBounds().width)) {
			missile.collisionMurLat();
		}

		// collision avec le mur du fond
		if (missile.getPosition().getY() >= eau.getBounds().height || missile.getPosition().getY() <= 0) {
			missile.collisionMurFond();
		}

		// collsion pour les nuages
		if (missile.getCollisionSol()) {
			trouNuages();
			missileAire = false;
			arreter();
		}

	}

	/**
	 * Méthode qui permet d'avancer d'un pas
	 */
	// Noé Julien
	public void avancerUnPas() {
		missile.calculerUnFrame(deltaT);
		collisionBateaux();
		interactionElements();
		if (missile.getPosition().getZ() <= 0 && touche == false) {
			aLeau = true;
			repaint();
			sonSplash();
			aLeau = false;
		}
		repaint();
	}

	/**
	 * Méthode pour savoir si la zoneBateau est en arrêt
	 * 
	 * @param arret variable qui indique si la zone est en arrêt
	 */
	// Noé Julien
	public void setArret(boolean arret) {
		this.arret = arret;
	}

	/**
	 * Méthode qui permet de faire le bruit approprié au début du lancement
	 * 
	 * @param b vraie sur premiere boucle de l'animation
	 */
	// Noé Julien
	public void sonLancement(boolean b) {
		if (b) {
			AjoutSon.joueSon("MissileLance.wav");
		}
	}

	/**
	 * Méthode qui permet de faire le bruit de l'explosion si le missile touche un
	 * bateau
	 **/
	// Noé Julien
	public void sonExplosion() {

		AjoutSon.joueSon("Explosion.wav");

	}

	/**
	 * Méthode qui permet de faire le bruit de l'éclaboussure si le missile touche
	 * l'eau
	 **/
	// Noé Julien
	public void sonSplash() {

		switch (terrainDeJeuFichier){
		case "Terre.jpg":
			AjoutSon.joueSon("Splash.wav");
			break;
		default:
			AjoutSon.joueSon("Meteorite.wav");
		}
		
	}

	/**
	 * Causer la fin du thread
	 */
	// Gabriel Maurice
	public void arreter() {
		arret = true;
		enCoursDAnimation = false;
		touche = false;
		repaint();
	}

	/**
	 * Méthode qui permet d'envoyer une rotation dans le bateau sélectioné
	 */
	// Noé Julien
	public void executerRotation() {
		System.out.println("bateauSelect: " + bateauSelect);
		System.out.println("Éditeur: " + editeur);

		if (bateauSelect) {
			Bateau bateau = bateaux.get(positionObjectAppuyer);
			bateau.addRotation();
			repaint();
		}
	}

	/**
	 * Méthode qui permet de placer un bateau à sa position initiale si place est
	 * faux et de le placer à la position de rectTemp si place est vrai
	 * 
	 * @param place    valeur booléenne pour faire le choix de où placer le bateau
	 * @param rectTemp rectangle qui représente un bateau pour le actionListener
	 * 
	 */
	// Noé Julien
	public void mettrePosition(boolean place, Rectangle rectTemp) {
		double dx;
		double dy;
		Bateau bateau = bateaux.get(positionObjectAppuyer);
		rectangleTemp = rectangles.get(positionObjectAppuyer);

		if (place) {
			dx = rectTemp.getX();
			dy = rectTemp.getY();
		} else {
			bateau.setAngle(0);
			dx = posXInital;
			dy = positions.get(positionObjectAppuyer).getY();
		}

		bateau.setPosition(dx, dy);
		rectangleTemp.setLocation((int) dx, (int) dy);

		afficherPositionBateaux();
		repaint();
	}

	/**
	 * Détermine si le curseur est enfoncé sur un rectangle
	 * 
	 * @param e évènement relié à la souris
	 */
	// Noé Julien
	private void sourisAppuyer(MouseEvent e) {
		if (!editeurHabiletes) {
			if (editeur) {
				for (int i = 0; i < shapes.size(); i++) {
					Shape shape = shapes.get(i);
					if (shape.contains(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre)) {
						positionObjectAppuyer = i;
						bateauSelect = true;
						rectangleTemp = rectangles.get(positionObjectAppuyer);
						repaint();
						break;
					}
				}
			}
		}
	}

	/**
	 * Détermine si le curseur est enfoncé et traîné pour changer la position du
	 * rectangle et du bateau selectioné.
	 * 
	 * @param e évènement relié à la souris
	 */
	// Noé Julien
	private void sourisTraine(MouseEvent e) {
		if (!editeurHabiletes) {
			double dx;
			double dy;
			if (bateauSelect) {

				dx = (e.getX() / pixelsParMetre) - rectangleTemp.getWidth() / 2;
				dy = (e.getY() / pixelsParMetre) - rectangleTemp.getHeight() / 2;

				// modifie la position du rectangle
				rectangleTemp.setLocation((int) dx, (int) dy);

				// modifie la position du bateau
				mettrePosition(true, rectangleTemp);
				repaint();
			}
		}
	}

	/**
	 * Détermine si le curseur est relâché et regarde si il y a collision entre le
	 * rectangle selectioné et les autres bateaux.
	 */
	// Noé Julien
	private void sourisRelache() {
		if (!editeurHabiletes) {
			boolean bateauMis = false;
			if (bateauSelect) {
				bateauMis = true;
				bateauSelect = false;
				for (int k = 0; k < shapes.size(); k++) {
					shapeTemp = shapes.get(positionObjectAppuyer);
					Shape shape = shapes.get(k);

					Area areaSelect = new Area(shapeTemp);
					Area areaSelect2 = new Area(shapeTemp);

					Area area2 = new Area(shape);
					Area area3 = new Area(eau);//
					areaSelect.intersect(area2);
					areaSelect2.subtract(area3);//

					// si il y a collision
					if (!areaSelect.isEmpty() && shapeTemp != shape || !areaSelect2.isEmpty()) {
						bateauMis = false;
						mettrePosition(false, rectangleTemp);
					}
					rectangleTemp = null;
				}
			}
			if (bateauMis) {
				switch (terrainDeJeuFichier){
				case "Terre.jpg":
					AjoutSon.joueSon("WaterDrop.wav");
					break;
				default:
					System.out.println("bateau place");
					AjoutSon.joueSon("Clang.wav");
					break;
				}
			}

			repaint();
		}
	}

	/**
	 * Méthode qui place aléatoirement les bateaux dans la zone bateau en mode
	 * éditeur.
	 */
	// Noé Julien
	public void placeAleatoirement() {
		
		switch (terrainDeJeuFichier){
		case "Terre.jpg":
			AjoutSon.joueSon("WaterDrop.wav");
			break;
		default:
			System.out.println("bateau place");
			AjoutSon.joueSon("Clang.wav");
			break;
		}
		
		for (int i = 0; i < bateaux.size(); i++) {
			boolean bienMis = false;
			Random random = new Random();
			while (!bienMis) {
				Bateau bateau = bateaux.get(i);
				Rectangle rect = rectangles.get(i);
				int largeur = largeurs.get(i);

				// place le bateau
				bateau.setAngle(random.nextInt(360));
				int x = random.nextInt(EAU_WIDTH - largeur);
				int y = random.nextInt(hauteurRectangles - largeur);
				bateau.setPosition(x, y);
				rect.setLocation(x, y);
				creerShapeBateaux();
				if (collisionDetect(i) == false) {
					bienMis = true;
				}
			}
		}
		repaint();
	}

	/**
	 * Méthode qui permet de détecter si il y a une collision entre un bateau et
	 * tout les autres
	 * 
	 * @param i La position du bateau dans le array list
	 * @return Vrai si il y a une collision et faux si il n'y en a pas
	 */
	// Noé Julien
	public boolean collisionDetect(int i) {
		Shape shapeTemp = shapes.get(i);
		for (int k = 0; k < shapes.size(); k++) {
			if (k != i) {
				Shape shape = shapes.get(k);
				Area areaSelect = new Area(shapeTemp);
				Area areaSelect2 = new Area(shapeTemp);
				Area area2 = new Area(shape);
				Area area3 = new Area(eau);
				areaSelect.intersect(area2);
				areaSelect2.subtract(area3);

				if (!areaSelect.isEmpty() || !areaSelect2.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Méthode qui créer les shapes pour tout les bateau, avec la rotation
	 */
	// Noé Julien
	public void creerShapeBateaux() {
		for (int i = 0; i < bateaux.size(); i++) {
			Bateau bateau = bateaux.get(i);
			Rectangle rect = rectangles.get(i);
			Shape shape = shapes.get(i);

			AffineTransform mat = new AffineTransform();
			mat.rotate(Math.toRadians(bateau.getAngle()), rect.getCenterX(), rect.getCenterY());
			shape = mat.createTransformedShape(rect);
			shapes.set(i, shape);
		}
	}

	/**
	 * Méthode qui permet d'obtenir le nombre de pixels par m dans le composant zone
	 * bateau.
	 * 
	 * @return pixelsParMetre le nombre de pixels par mètres
	 */
	// Noé Julien
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}

	/**
	 * Méthode qui permet de modifier le nombre de pixels par mètres dans la zone
	 * des bateaux
	 * 
	 * @param pixelsParMetre le nouveau nombre de pixels par m
	 */
	// Noé julien
	public void setPixelsParMetre(double pixelsParMetre) {
		ZoneBateau.pixelsParMetre = pixelsParMetre;
	}

	/**
	 * Retourne la valeur de deltaT
	 *
	 * @return La valeur de deltaT
	 */
	// Noé Julien
	public static double getDeltaT() {
		return deltaT;
	}

	/**
	 * Change la valeur de deltaT
	 * 
	 * @param valeur La nouvelle valeur de deltaT.
	 */
	// Noé Julien
	public static void setDeltaT(double valeur) {
		deltaT = valeur;
	}

	/**
	 * Méthode qui appelle la méthode de redimension des classes d'habiletés et qui
	 * teste pour tous les composants d'habiletés présents dans la zone de dessin.
	 */
	// Théo Fourteau
	public void redimensionHabiletes() {

		int nbHabiletes = habiletesSpeciales.size();
		if (nbHabiletes > 0) {
			for (int i = 0; i < nbHabiletes; i++) {
				if (habiletesSpeciales.get(i).getEstSelectionne()) {
					habiletesSpeciales.get(i).redimmensionQuandClique();
				}
			}
		}

		repaint();

	}

	/**
	 * Méthode qui appelle la méthode de rotation des murs et qui teste pour tous
	 * les composants Mur placés dans la zone des bateaux.
	 */
	// Théo Fourteau
	public void rotationHabiletes() {

		int nbHabiletes = habiletesSpeciales.size();
		if (nbHabiletes > 0) {
			for (int i = 0; i < nbHabiletes; i++) {
				if (habiletesSpeciales.get(i).getEstSelectionne()) {
					habiletesSpeciales.get(i).rotationQuandClique();
				}
			}
		}
		repaint();
	}

	/**
	 * 
	 * Change les paramêtres de positions des bateaux dépendamment de si le mode
	 * d'édition est activé
	 * 
	 * @param opt valeur booléenne qui détermine si le mode éditeur doit être activé
	 */
	// Noé Julien
	public void setEditeur(boolean opt) {
		this.editeur = opt;

		for (int i = 0; i < bateaux.size(); i++) {
			Bateau bateau = bateaux.get(i);
			Rectangle rect = rectangles.get(i);
			Vecteur3D vect = positions.get(i);
			int largeur = largeurs.get(i);

			if (editeur) {
				vect.setX(posXInital);
				vect.setY(positionsYEditeur.get(i));
			} else {
				Vecteur3D vectZero = new Vecteur3D(bateau.getPositionX(), bateau.getPositionY(), 0);
				vect.setComposantes(vectZero);
			}

			bateau.setPosition(vect.getX(), vect.getY());
			rect.setBounds((int) vect.getX(), (int) vect.getY(), largeur, largeur1case);
		}
		repaint();
	}

	/**
	 * Méthode qui retourne si la zone est en mode éditeur
	 * 
	 * @return vrai si en mode éditeur et faux sinon
	 */
	// Noé Julien
	public boolean isEditeur() {
		return editeur;
	}

	/**
	 * Définit si en mode tutoriel
	 * 
	 * @param tutoriel indique si en mode tutoriel
	 */
	// Noé Julien
	public void setTutoriel(boolean tutoriel) {
		this.tutoriel = tutoriel;
	}

	/**
	 * Cette methode lit un fichier image du terrain de jeu et le conserve en tant
	 * qu'image (fichier options)
	 * 
	 * @param terrainDeJeu le terrain de jeu en String
	 */
	// Cedric Thai
	public void creerTerrainImage(String terrainDeJeu) {
		// terrainImage = OutilsImage.lireImage("ocean.jpg"); // pour acceder aux
		// windowBuilder fenetreTerrainJeu
		terrainImage = OutilsImage.lireImage(terrainDeJeu);

	} // fin methode

	/**
	 * Méthode qui affiche les positions des différents bateaux pour le sprint 1.
	 * Pour constater les accomplissements en testant, avant même de regarder le
	 * code.
	 * 
	 */
	// Noé Julien
	private void afficherPositionBateaux() {
		for (int i = 0; i < bateaux.size(); i++) {
			Bateau bateau = bateaux.get(i);
			int angle = angles.get(i);
			String string = null;

			string = (i == 0) ? "Bateau2"
					: (i == 1) ? "Bateau3" : (i == 2) ? "Bateau31" : (i == 3) ? "Bateau4" : "Bateau5";

			System.out.println("Position " + string + ", x: " + bateau.getPositionX() + ",m  y: "
					+ bateau.getPositionY() + "m, angle: " + angle + "°");
		}

		System.out.println("\n" + "---------------------------------------------- " + "\n");
	}

	/**
	 * Méthode qui détermine si la souris est pressée et trainée sur le composant de
	 * dessin, afin de modifier l'emplacement des habiletés spéciales.
	 * 
	 * @param e l'évènement lié à la souris
	 */
	// Théo Fourteau
	private void sourisTraineHabiletes(MouseEvent e) {

		int nbHabiletes = habiletesSpeciales.size();
		if (nbHabiletes > 0) {
			for (int i = 0; i < nbHabiletes; i++) {
				if (habiletesSpeciales.get(i).getEstSelectionne()) {
					habiletesSpeciales.get(i).deplacer(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre);

				}
			}
		}

		if (habileteBougeable) {
			repaint();
		}
	}

	/**
	 * Méthode qui détermine si la roue de la souris est tournee pour pouvoir
	 * acceder aux 3 types d'habiletes disponibles
	 * 
	 * @param e l'évènement lié à la souris
	 */
	// Cedric Thai
	private void sourisRoueCycle(MouseWheelEvent e) {
		if (modeTesteur && debloquerHabiletes) {
			int rotation = e.getWheelRotation();
			if (rotation < 0) {
				cycleHabilete++;
				if (cycleHabilete > 3) {
					cycleHabilete = 1;
				}
			} else {
				cycleHabilete--;
				if (cycleHabilete < 1) {
					cycleHabilete = 3;
				}
			}
			replacerHab = false;
			repaint();
		}
	}

	/**
	 * Méthode qui désélectionne les habiletés spéciales quand la souris est
	 * relâchée et qui le remet a la position initial si mal placee
	 * 
	 * @param e L'événement de la souris associé à l'appui.
	 */
	// Cedric Thai
	private void sourisAppuyeeHabiletes(MouseEvent e) {
		if (!habileteMax) {
			if (champElectriqueCliquable.contient(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre)) {
				habiletesSpeciales.add(
						ChampElectrique.createChampElectrique(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre));
				if (!modeTesteur) {
					habileteMax = true;
				}
				habileteBougeable = true;
				habileteTouchee = true;

			}

			if (murCliquable.contientDeplacement(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre)) {
				habiletesSpeciales.add(Mur.createMur(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre));
				if (!modeTesteur) {
					habileteMax = true;
				}
				habileteBougeable = true;
				habileteTouchee = true;

			}

			if (zoneVentCliquable.contient(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre)) {
				habiletesSpeciales.add(ZoneVent.createZoneVent(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre));
				if (!modeTesteur) {
					habileteMax = true;
				}
				habileteBougeable = true;
				habileteTouchee = true;
			}

		}
		for (int i = 0; i < habiletesSpeciales.size(); i++) {
			Shape shape = habiletesSpeciales.get(i).getShape();
			if (shape.contains(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre)) {
				switch (habiletesSpeciales.get(i).getNumHabilete()) {
				case 1:
					habileteCorrespondant = 1;
					break;
				case 2:
					habileteCorrespondant = 2;
					break;
				case 3:
					habileteCorrespondant = 3;
					break;

				}
				habileteBougeable = true;
				positionAppuyeHab = i;
				relacheHab = true;
				replacerHab = false;

			}
		}

		int nbHabiletes = habiletesSpeciales.size();
		if (nbHabiletes > 0) {
			for (int i = 0; i < nbHabiletes; i++) {
				if (habiletesSpeciales.get(i).contient(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre)) {
					habiletesSpeciales.get(i).setEstSelectionne(true);
				}
			}
		}

		if (habileteBougeable) {
			repaint();
		}
	}

	/**
	 * Méthode qui désélectionne les habiletés spéciales quand la souris est
	 * relâchée et qui le remet a la position initial si mal placee
	 */
	// Cedric Thai
	private void sourisRelacheeHabiletes() {
		if (relacheHab) {
			habiletesSpeciales.get(positionAppuyeHab).setEstSelectionne(false);
			shapeHabileteTemp = habiletesSpeciales.get(positionAppuyeHab).getShape();

			Area areaSelect = new Area(shapeHabileteTemp);
			Area areaZone = new Area(eau);

			Area areaBateaux = new Area();
			for (Shape shape : shapes) {
				areaBateaux.add(new Area(shape));
			}

			areaSelect.intersect(areaBateaux);
			if (!areaSelect.isEmpty() && habileteCorrespondant == 2) {
				cycleHabilete = 2;
				habiletesSpeciales.remove(positionAppuyeHab);
				habileteMax = false;
				habileteTouchee = false;
				replacerHab = true;
				repaint();
			}

			areaSelect = new Area(shapeHabileteTemp);
			areaSelect.subtract(areaZone);

			if (!areaSelect.isEmpty()) {

				switch (habileteCorrespondant) {
				case 1:
					cycleHabilete = 1;
					habiletesSpeciales.remove(positionAppuyeHab);
					habileteMax = false;
					habileteTouchee = false;
					replacerHab = true;

					repaint();
					break;
				case 2:
					cycleHabilete = 2;
					habiletesSpeciales.remove(positionAppuyeHab);
					habileteMax = false;
					habileteTouchee = false;
					replacerHab = true;
					repaint();
					break;
				case 3:
					cycleHabilete = 3;
					habiletesSpeciales.remove(positionAppuyeHab);
					habileteMax = false;
					habileteTouchee = false;
					replacerHab = true;
					repaint();
					break;
				}
			}

			habileteBougeable = false;
			relacheHab = false;

			if (habileteBougeable) {
				repaint();
			}
		}
	}

	/**
	 * Méthode qui permet de réinitialiser les différents paramètres du missile.
	 */
	// Gabriel Maurice
	public void resetMissile() {
		missile.setCollisionSol(false);
		missile.setPosition(new Vecteur3D(eau.getBounds().getWidth() / 2, 0, 0));
		missile.setVitesse(new Vecteur3D(0, 0, 0));

	}

	/**
	 * Méthode qui permet de savoir si les cinq bateaux sont placés
	 * 
	 * @return true si les cinq bateaux sont placé, false sinon
	 */
	// Noé Julien
	public boolean bateauxPlace() {
		nbBateauxPlace = 0;
		for (int k = 0; k < bateaux.size(); k++) {
			Bateau bateau = bateaux.get(k);

			System.out.println("Position X: " + bateau.getPositionX());
			System.out.println("Initial Position X: " + posXInital);
			System.out.println("Number of Boats Placed: " + nbBateauxPlace + "\n");

			if (bateau.getPositionX() != posXInital) {
				nbBateauxPlace++;
			}
		}

		if (nbBateauxPlace == 5) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode qui permet de ne pas dessiner les bateaux
	 * 
	 * @param choix valeur booléenne qui est donné dans la méthode setDessinable
	 *              dans chaques bateaux
	 */
	// Noé Julien
	public void cacheBateaux(boolean choix) {
		for (int i = 0; i < bateaux.size(); i++) {
			Bateau bateau = bateaux.get(i);
			bateau.setDessinable(choix);
		}
	}

	/**
	 * Permet de faire tourner la zone
	 * 
	 * @param inverse vrai pour rotation inverse, sinon faux
	 */
	// Noé Julien
	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	/**
	 * Methode qui retourne la collision du missile
	 * 
	 * @return la collision du missile
	 */
	// Gabriel Maurice
	public boolean getCollision() {
		return missile.getCollisionSol();
	}

	/**
	 * Méthode qui permet de modifier la valeur du boolean editeurHabiletes.
	 * 
	 * @param b la nouvelle valeur du booléen
	 */
	// Théo Fourteau
	public void setEditeurHabiletes(boolean b) {
		editeurHabiletes = b;
		repaint();
	}

	/**
	 * Méthode qui permet de retourner la valeur du boolean editeurHabiletes.
	 * 
	 * @return editeurHabiletes si oui ou non la zone de bateau est en mode editeur
	 *         des habiletés.
	 */
	// Théo Fourteau
	public boolean getEditeurHabiletes() {
		return editeurHabiletes;

	}

	/**
	 * Méthode qui permet d'inverser les coordonnées des habiletés quand la zone de
	 * bateaux est inversée.
	 */
	// Théo Fourteau
	public void inverserHabiletes() {
		int nbHabiletes = habiletesSpeciales.size();
		if (nbHabiletes > 0) {
			for (int i = 0; i < nbHabiletes; i++) {
				habiletesSpeciales.get(i).deplacer(this.getWidth() - habiletesSpeciales.get(i).getCentreX(),
						this.getHeight() - habiletesSpeciales.get(i).getCentreY());
			}
		}
		repaint();

	}

	/**
	 * Méthode qui retire des aire circulaires à l'aire des nuages de manière à
	 * créer l'aspect visuel de l'impact du missile dans la zone des bateaux.
	 * L'utilisateur peut désormais voir au travers des nuages, à l'endroit où il a
	 * tiré.
	 * 
	 * @return aireCercleImpact le cercle qui a été soustrait à la zone de nuages.
	 */
	// Théo Fourteau
	public Area trouNuages() {
		double coorX = missile.getMissilePoint().getX();
		double coorY = missile.getMissilePoint().getY();

		double diametreImpact;

		if (modeTesteur) {
			diametreImpact = DIAMETRE_IMPACT;

		} else {
			diametreImpact = DIAMETRE_IMPACT / 2;
		}

		Shape cercleImpact = new Ellipse2D.Double(coorX - (diametreImpact / 2), coorY - (diametreImpact / 2),
				diametreImpact, diametreImpact);

		trous.add(cercleImpact);

		Area aireCercleImpact = new Area(cercleImpact);
		nuages.subtract(aireCercleImpact);

		System.out.println("coordonnée centre de l'impact: " + coorX + ", " + coorY);

		return aireCercleImpact;

		// repaint();

	}

	/**
	 * Méthode qui replace les bateaux à leur positions initales en mode éditeur
	 */
	// Noé Julien
	public void replacePositionInit() {
		for (int i = 0; i < bateaux.size(); i++) {
			Bateau bateau = bateaux.get(i);
			bateau.setAngle(0);
		}
		setEditeur(true);
	}

	/**
	 * Met à jour la géométrie du missile
	 */
	// Noé Julien
	public void miseAJourMissile() {
		if (!enCoursDAnimation) {
			missile.creerLaGeometrie();
		}
	}

	/**
	 * Methode qui retourne la liste de bateaux
	 * 
	 * @return la liste de bateaux de la zone correspondante
	 */
	// Cedric Thai
	public List<Bateau> getListBateaux() {
		return this.bateaux;
	}

	/**
	 * Methode qui modifie la liste de bateux
	 * 
	 * @param bateaux La liste de bateaux
	 */
	// Cedric Thai
	public void setListBateaux(Object bateaux) {
		this.bateaux = (List<Bateau>) bateaux;
		repaint();
	}

	/**
	 * Methode qui retourne la liste de trous
	 * 
	 * @return la liste de trous de la zone correspondante
	 */
	// Cedric Thai
	public List<Shape> getTrous() {
		return this.trous;
	}

	/**
	 * Methode qui modifie la liste de trous
	 * 
	 * @param trous La liste de trous
	 */
	// Cedric Thai
	public void setTrous(Object trous) {
		this.passerFichier = true;
		this.trous = (List<Shape>) trous;
		repaint();
	}

	/**
	 * Methode qui modifie la liste des habiletes speciales
	 * 
	 * @return la liste d'habiletes speciales
	 */
	// Cedric Thai
	public List<Habiletes> getHabiletesSpeciales() {
		return this.habiletesSpeciales;
	}

	/**
	 * Methode qui modifie la liste des habiletes speciales
	 * 
	 * @param habiletesSpeciales la liste des habiletes speciales
	 */
	// Cedric Thai
	public void setHabiletesSpeciales(Object habiletesSpeciales) {
		this.habiletesSpeciales = (List<Habiletes>) habiletesSpeciales;
		repaint();
	}

	/**
	 * Methode qui retourne le terrain de jeu
	 * 
	 * @return le terrain de jeu en String
	 */
	// Cedric Thai
	public String getTerrainDeJeuFichier() {
		return terrainDeJeuFichier;
	}

	/**
	 * Methode qui modifie le terrain de jeu avec celui venant du fichier
	 * 
	 * @param terrainDeJeuFichier le terrain de jeu venant du fichier en String
	 */
	// Cedric Thai
	public void setTerrainDeJeuFichier(String terrainDeJeuFichier) {
		this.passerFichier = true;
		creerTerrainImageFichier(terrainDeJeuFichier);
		repaint();
	}

	/**
	 * Cette methode lit un fichier image du terrain de jeu et le conserve en tant
	 * qu'image (fichier sauvegarde)
	 * 
	 * @param terrainDeJeuFichier le terrain de jeu en String
	 */
	// Cedric Thai
	public void creerTerrainImageFichier(String terrainDeJeuFichier) {
		terrainImageFichier = OutilsImage.lireImage(terrainDeJeuFichier);

	} // fin methode

	/**
	 * Methode qui retourne si le missile est dans les aires
	 * 
	 * @return missileAire si le missile est dans les aires
	 */
	// Gabriel Maurice
	public boolean getMissileAire() {
		return missileAire;
	}

	/**
	 * Methode qui retourne l'objet eau
	 * 
	 * @return eau l'objet eau
	 */
	// Gabriel Maurice
	public Shape getEau() {
		return eau;
	}

	/**
	 * Methode qui retourne la liste des shapes
	 * 
	 * @return shapes la liste des shapes
	 */
	// Gabriel Maurice
	public List<Shape> getShapes() {
		return shapes;
	}

	/**
	 * Methode qui retourne l'aire des bateaux visibles par l'IA
	 * 
	 * @return aireIA l'aire des bateaux visibles par l'IA
	 */
	// Gabriel Maurice
	public Area getAireIA() {
		return aireIA;
	}

	/**
	 * Methode qui change l'aire de bateaux visibles par l'IA
	 * 
	 * @param aireIA l'aire de bateaux visibles par l'IA
	 */
	// Gabriel Maurice
	public void setAireIA(Area aireIA) {
		this.aireIA = aireIA;
	}

	/**
	 * Methode qui retourne l'ellipse representant le point choisi par l'IA pour
	 * tirer
	 * 
	 * @return pointIA l'ellipse representant le point choisi par l'IA pour tirer
	 */
	// Gabriel Maurice
	public Ellipse2D.Double getPointIA() {
		return pointIA;
	}

	/**
	 * Methode qui change l'ellipse representant le point choisi par l'IA pour tirer
	 * 
	 * @param pointIA l'ellipse representant le point choisi par l'IA pour tirer
	 */
	// Gabriel Maurice
	public void setPointIA(Ellipse2D.Double pointIA) {
		this.pointIA = pointIA;
	}

	/**
	 * Methode qui retourne si le match est joue contre l'IA
	 * 
	 * @return matchContreIA si le match est joue contre l'IA
	 */
	// Gabriel Maurice
	public boolean isMatchContreIA() {
		return matchContreIA;
	}

	/**
	 * Methode qui change si le match est joue contre l'IA
	 * 
	 * @param matchContreIA si le match est joue contre l'IA
	 */
	// Gabriel Maurice
	public void setMatchContreIA(boolean matchContreIA) {
		this.matchContreIA = matchContreIA;
	}

	/**
	 * Methode qui permet de creer un minuteur entre les tours des jouers. Les
	 * joueurs ne pourront pas voir les terrains durant cette transition
	 * 
	 * @param masquerZoneMinuteur boolean qui determine si la zone avec le minuteur
	 *                            doit etre cache
	 * @param tourDuJoueur        le tour du joueur
	 */
	// Cedric Thai
	public void transitionEntreTours(boolean masquerZoneMinuteur, int tourDuJoueur) {

		if (!transitionEntreTours) {
			this.transitionEntreTours = true;
			this.masquerZoneMinuteur = masquerZoneMinuteur;
			this.tourDuJoueur = tourDuJoueur;
			this.timer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tempsRestant--;
					repaint();
					if (tempsRestant <= 0) {
						timer.stop();
						reinitialisationTimer();
					}
				}
			});
			timer.start();
			// touche = false;
		}

	}

	/**
	 * Méthode qui permet de réinitialiser le temps du minuteur au temps maximal
	 */
	// Cedric Thai
	public void reinitialisationTimer() {
		tempsRestant = TEMPS_MAX_TOUR;
		timer.stop();
		this.transitionEntreTours = false;
	}

	/**
	 * Methode qui retourne si le match est commence
	 * 
	 * @return matchCommence si le match est commence
	 */
	// Gabriel Maurice
	public boolean isMatchCommence() {
		return matchCommence;
	}

	/**
	 * Methode qui change si le match est commence
	 * 
	 * @param matchCommence si le match est commence
	 */
	// Gabriel Maurice
	public void setMatchCommence(boolean matchCommence) {
		this.matchCommence = matchCommence;
	}

	/**
	 * Methode qui retourne si le match est joue contre l'IA
	 * 
	 * @return si le match est joue contre l'IA
	 */
	// Gabriel Maurice
	public boolean isTourIA() {
		return tourIA;
	}

	/**
	 * Methode qui change si le match est joue contre l'IA
	 * 
	 * @param tourIA si le match est joue contre l'IA
	 */
	// Gabriel Maurice
	public void setTourIA(boolean tourIA) {
		this.tourIA = tourIA;
	}

	/**
	 * Methode qui retourne l'acceleration gravitationnelle
	 * 
	 * @return accelGrav l'acceleration gravitationnelle
	 */
	// Gabriel Maurice
	public double getAccelGrav() {
		return accelGrav;
	}

	/**
	 * Methode qui change l'acceleration gravitationnelle
	 * 
	 * @param accelGrav l'acceleration gravitationnelle
	 */
	// Gabriel Maurice
	public void setAccelGrav(double accelGrav) {
		this.accelGrav = accelGrav;
	}

	/**
	 * Methode qui permet de reinitialiser la limite des habiletes
	 * 
	 * @param habileteMax boolean qui determine si le nombre d'habiletes est au
	 *                    maximum
	 */
	// Cedric Thai
	public void setHabileteMax(boolean habileteMax) {
		this.habileteMax = habileteMax;
		repaint();
	}

	/**
	 * Methode qui permet d'enlever l'habilete
	 */
	// Cedric Thai
	public void enleverHabilete() {
		for (int i = 0; i < habiletesSpeciales.size(); i++) {
			habiletesSpeciales.remove(i);

		}

		repaint();
	}

	/**
	 * Methode qui retourne la boolean habileteBougeable
	 * 
	 * @return boolean qui determine si l'habilete peut etre bouge
	 */
	// Cedric Thai
	public boolean getHabileteBougeable() {
		return this.habileteBougeable;
	}

	/**
	 * Methode qui reinitialise la boolean habileteBougeable
	 * 
	 * @param habileteBougeable boolean qui determine si l'habilete peut etre bouge
	 */
	// Cedric Thai
	public void setHabileteBougeable(boolean habileteBougeable) {
		this.habileteBougeable = habileteBougeable;
		repaint();
	}

	/**
	 * Methode qui retourne si l'habilete a ete touchee
	 * 
	 * @return valeur boolean qui represente si l'habilete a ete touchee
	 */
	// Cedric Thai
	public boolean getHabileteTouchee() {
		return this.habileteTouchee;
	}

	/**
	 * Methode qui modifie si l'habilete a ete touchee
	 * 
	 * @param habileteTouchee valeur boolean qui represente si l'habilete a ete
	 *                        touchee
	 */
	// Cedric Thai
	public void setHabileteTouchee(boolean habileteTouchee) {
		this.habileteTouchee = habileteTouchee;
		repaint();
	}

	/**
	 * Methode qui retourne la derniere habilete apparue
	 * 
	 * @return la derniere habilete apparue (1:Champ 2:Mur 3:Vent)
	 */
	// Cedric Thai
	public int getDerniereHabilete() {
		return this.derniereHabilete;
	}

	/**
	 * Methode qui modifie et qui dessine la derniere habilete enregistree
	 * 
	 * @param derniereHabilete la derniere habilete apparue (1:Champ 2:Mur 3:Vent)
	 */
	// Cedric Thai
	public void setDerniereHabilete(int derniereHabilete) {
		this.derniereHabilete = derniereHabilete;
		this.dessinHabiletePasse = true;
		repaint();
	}

	/**
	 * Methode qui permet de debloquer les habiletes (mode testeur)
	 */
	// Cedric Thai
	public void debloquerHabiletesTest() {
		this.debloquerHabiletes = true;
	}

}
