package application;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import composantsDessin.GraphPanel;
import composantsDessin.InfoGuidageMissile;
import composantsDessin.Jauge;
import composantsDessin.LanceurMissile;
import composantsDessin.ZoneBateau;
import geometrie.Missile;
import outils.AjoutSon;
import outils.OutilsImage;
import partie.IA;
import partie.SystemeTours;
import javax.swing.JComboBox;
import composantsDessin.AffichageMissile;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe qui permet d'afficher la fenêtre de terrain de jeu, c'est-à-dire la
 * fenêtre sur laquelle se déroule la partie.
 * 
 * @author Théo Fourteau
 * @author Noé Julien
 * @author Cedric Thai
 * @author Gabriel Maurice
 */
public class FenetreTerrainJeu extends JFrame implements Runnable {

	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;

	/** instance de AppPrincipale33 pour retourner au menu principal **/
	private AppPrincipale33 retour;

	// Composants de l'application

	/** panneau contenant les composants de l'application **/
	private JPanel contentPane;

	/**
	 * Composant graphique du canon
	 */
	private LanceurMissile lanceur;

	/**
	 * Jauge servant à visualiser la vitesse intiale du missile au moment de chaque
	 * tir
	 **/
	private Jauge jauge;

	/**
	 * Variable booléenne qui permet de déterminer si le changement des orientations
	 * se fait avec les flèches (TRUE) ou avec les touches WASD (FALSE)
	 */
	private boolean modeFlecheActif = true;

	/** Variable double qui représente l'accelération gravitationnelle */
	private double accelerationGravitationnelle;

	/**
	 * Variable String qui représente le terrain de jeu sélectionné
	 */
	private static String terrainDeJeu;

	/**
	 * Variable booléenne qui détermine si le minuteur est affiché
	 */
	private boolean afficherMinuteur = true;

	/**
	 * Panneau conteanant les informations liées à la vitesse initale, l'angle
	 * vertical et l'amngle horizontal du missile,
	 */
	private JPanel panCommandesTir;
	/**
	 * Étiquette qui affiche «Vitesse initiale»
	 */
	private JLabel lblVitesseI;
	/**
	 * Étiquette qui affiche la vitesse initiale du missile en m/s
	 */
	private JLabel lblAfficheVitesseI;

	/**
	 * Étiquette qui affiche «angle horizontal» sur le panneau des infomations du
	 * missile
	 */
	private JLabel lblAngleH;
	/**
	 * Étiquette qui affiche la valeur de l'angle vertical et qui se modifie quand
	 * celle-ci est modifiée.
	 */
	private JLabel lblAfficheAngleV;
	/**
	 * Étiquette qui affiche la valeur de l'angle horizontale et qui se modifie
	 * quand celle-ci est modifiée.
	 */
	private JLabel lblAfficheAngleH;

	/**
	 * Étiquette qui affiche «angle vertical» sur le panneau des infomations du
	 * missile
	 */
	private JLabel lblAngleVert;
	/**
	 * Bouton servant à tirer un missile, au moment de son tir et après avoir choisi
	 * la vitesse initiale et les angles de tir du missile.
	 */
	private JButton btnTir;

	/**
	 * Composant graphique de la zone qui comporte les bateaux située en bas.
	 */
	private ZoneBateau zoneBateauA;

	/**
	 * Composant graphique de la zone qui comporte les bateaux située en haut.
	 */
	private ZoneBateau zoneBateauB;

	/** instance de FenetreOPtions pour pouvoir acceder aux options **/
	private FenetreOptions fenetreOptions;

	/**
	 * Panneau affichant le numéro du tour, le minuteur du temps restant au tour et
	 * le joueur à qui est le tour
	 */
	private SystemeTours panSystemeTours;

	/**
	 * Étiquette de sous-titre qui affiche «Commandes de tir : » sur le composant.
	 */
	private JLabel lblCommandesTir;

	/**
	 * Bouton servant à confirmer l'emplacement de ses bateaux. Le bouton est
	 * seulement visible avant que la partie commence
	 */
	private JButton boutonEmplacementBateaux;

	/**
	 * Panneau servant à afficher toutes les informations de sorties concerannt la
	 * physqiue du missile.
	 **/
	private JPanel panSortiesPhysique;
	/**
	 * Étiquette qui affiche dans le panneau des sorties «Guidage du missile»
	 */
	private JLabel lblGuidageMissile;
	/**
	 * Panneau qui sert de graphique affichant la variation de la vitesse du missile
	 * en fonction du temps.
	 */
	private static GraphPanel panGraphiqueVitesse;
	/**
	 * Panneau qui contient le graphique de la variation de la hauteur en fonction
	 * du temps.
	 */
	private JLabel lblPresGraphVit;

	/**
	 * Bouton qui sert à placer de façon aléatoire la position des bateaux
	 */
	private JButton btnPlaceAleatoir = new JButton();

	/**
	 * Bouton qui sert à replacer les bateaux à leurs positions initiale en mode
	 * éditeur
	 */
	private JButton btnReplace = new JButton();

	/**
	 * Panneau qui permet le choix d'un autre type de missile.
	 */
	private JPanel panChoixmissile;
	/**
	 * Étiquette du panneau de choix de missile qui affiche «Choix des munitions»
	 */
	private JLabel lblMunitions;
	/** Coordonnée Y de la zone de placement du haut */
	private int placementZoneHautY = 6;
	/** Coordonnée Y de la zone de placement du bas */
	private int placementZoneBasY = 453;
	/** Coordonnée X des zones de placements */
	private int placementZoneX = 429;

	/** Si le composant est en cours d'animation **/
	private boolean enCoursDAnimation;
	/** Bouton qui une fois cliqué ouvre la fenêtre d'enregistrement **/
	private JButton btnEnregistrer;
	/**
	 * Panneau servant de graphique de la hauteur du missile en fonction du temps
	 **/
	private static GraphPanel panGraphiqueHauteur;
	/** Glissière pour ajuster le pas de temps **/
	private JSlider sliderPasDeTemps;
	/** Étiquette affichant le pas de temp **/
	private JLabel lblPasDeTemp;
	/**
	 * Panneau servant à afficher l'estimation de la hauteur max, du temps de vol et
	 * de la distance max pouvant être parcourue par le missile. Est redessiné à
	 * chaque fois que le l'angle vertical du canon ou la vitesse initiale du
	 * missile change.
	 */
	private static InfoGuidageMissile panInfoGuidageMissile;
	/**
	 * Étiquette servant de sous-titre au panneau des informations du guidage du
	 * missile
	 **/
	private JLabel lblInfosMissile;

	/**
	 * Boolean qui indique le tour correspondant du jouer
	 */
	private boolean tourJoueurCorrespondant;

	/** Variable de classe pour garder une trace de l'état de la fenêtre **/
	static boolean estOuvert = false;
	/** instance de FenetreTouchesClavier pour obtenir de l'aide sur les touches **/
	private FenetreTouchesClavier touchesClavier;

	/**
	 * Boîte combo de chaine qui contient les différents choix des types de missile
	 **/
	private JComboBox<String> cmbChoixMissile = new JComboBox<>();
	/** Chaine qui représente le nom du type de missile choisi **/
	private String choixOption;
	/** si le match est joue contre l'IA **/
	private static boolean matchContreIA;
	/** Le joueur IA **/
	private IA ia;
	/** Valeur booléenne qui indique si le son de l'océan est activé ou non */
	private static boolean sonOcean = true;
	/** étiquette qui affiche la masse du missile **/
	private JLabel lblMasseMissile;
	/** Panneau qui permet d'afficher le type de missile utilisé **/
	private AffichageMissile affichageMissile;
	/** instance de fenetreVictoire **/
	private FenetreVictoire fenetreVictoire;
	/** Image de fond **/
	private Image imageFond;
	/** Compteur qui permet d'indiquer l'obtention des habiletes (Joueur 1) **/
	private static int compteurObtention1;
	/** Compteur qui permet d'indiquer l'obtention des habiletes (Joueur 2) **/
	private static int compteurObtention2;
	/** Compteur qui permet d'indiquer la duree de l'habilete (Joueur 1) **/
	private static int compteurDuree1;
	/** Compteur qui permet d'indiquer la duree de l'habilete (Joueur 2) **/
	private static int compteurDuree2;
	/** Représente l'étiquette pour la charge des missiles. */
	private JLabel lblChargeMissile;
	/** Représente l'étiquette pour indiquer les missiles. */
	private JLabel lblIndiqueMissile;
	/** Valeur boolean qui represente si le mode testeur est active **/
	private static boolean modeTesteur;
	/** Bouton pour avancer d'un pas dans le jeu */
	private JButton btnAvancerUnPas;
	/** Bouton pour reprendre le jeu */
	private JButton btnReprendre;
	/** Bouton pour arrêter le jeu */
	private JButton btnArreter;
	/** Bouton pour ouvrir les options **/
	private JButton btnOptions;

	/**
	 * Lancement de la fenêtre du terrain de jeu
	 * 
	 * @param args argument de création d'application
	 */
	// Théo Fourteau
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreTerrainJeu frame = new FenetreTerrainJeu("", false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * /** Création du panneau du terrain de jeu
	 * 
	 * @param nomFichier  nom du fichier d'une sauvegarde
	 * 
	 * @param modeTesteur valeur booléenne qui détermine si le mode testeur est
	 *                    activee
	 * 
	 * @throws ClassNotFoundException Cette exception indique qu'une classe
	 *                                spécifiée n'a pas pu être trouvée lors de
	 *                                l'exécution du programme.
	 */
	// Théo Fourteau
	public FenetreTerrainJeu(String nomFichier, boolean modeTesteur) throws ClassNotFoundException {

		imageFond = OutilsImage.lireImage("FondTerrainJeu.png");
		FenetreTerrainJeu.modeTesteur = modeTesteur;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 100, 1500, 850);
		this.setMaximizedBounds(getBounds());
		// Désactiver la possibilité de redimensionner la fenêtre
		setResizable(false);
		contentPane = new JPanel() {
			private static final long serialVersionUID = 4646591221974773456L;

			protected void paintComponent(Graphics g) {
				super.paintComponents(g);
				if (imageFond != null) {
					// Dessiner l'image en utilisant sa largeur et sa hauteur
					g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		estOuvert = true;
		FenetreTutoriel.setEstOuvert(false);

		setContentPane(contentPane);
		lectureFichierOptions();

		// Gestion du son dépendant du terrain de jeu
		switch (terrainDeJeu) {
		case "Terre.jpg":
			System.out.println("Le son devrait jouer");
			AjoutSon.sonOcean();
			break;
		default:
			System.out.println("Le son ne devrait pas jouer");
			AjoutSon.stopSonOcean();
			FenetreTerrainJeu.sonOcean = false;
		}

		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AjoutSon.stopSonOcean();
				retour = new AppPrincipale33();
				FenetreTerrainJeu.setEstOuvert(false);
				retour.setVisible(true);
				setVisible(false);
				dispose();
			}
		});

		btnRetour.setBounds(6, 6, 92, 23);
		contentPane.add(btnRetour);

		JButton btnTouches = new JButton("Touches");
		btnTouches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				touchesClavier = new FenetreTouchesClavier();
				touchesClavier.setVisible(true);
				touchesClavier.setAlwaysOnTop(true);
				btnTouches.setVisible(true);
				contentPane.requestFocusInWindow();
			}
		});

		btnTouches.setBounds(99, 6, 92, 23);
		contentPane.add(btnTouches);

		if (!modeTesteur) {
			btnOptions = new JButton("Options");
			btnOptions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ecritureFichierSauvegarde("temporaire");

						compteurObtention1=0;
						compteurObtention2=0;
						compteurDuree1=0;
						compteurDuree2=0;
						if (matchContreIA) {
							fenetreOptions = new FenetreOptions(true, panSystemeTours.getPartieCommencee(),true);
						}else {
							fenetreOptions = new FenetreOptions(true, panSystemeTours.getPartieCommencee(),false);
						}

					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					fenetreOptions.setVisible(true);
					setVisible(false);
				}
			});
			contentPane.setLayout(null);
			btnOptions.setBounds(1051, 17, 126, 35);
			contentPane.add(btnOptions);
		}

		btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enregistrer();
			}
		});
		if (modeTesteur) {
			JButton btnReiniHabileteA = new JButton("Enlever les habiletés (zone A)");
			btnReiniHabileteA.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					zoneBateauA.enleverHabilete();
				}
			});

			btnReiniHabileteA.setBounds(1030, 17, 200, 25);
			contentPane.add(btnReiniHabileteA);

			JButton btnReiniHabileteB = new JButton("Enlever les habiletés (zone B)");
			btnReiniHabileteB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					zoneBateauB.enleverHabilete();
				}
			});

			btnReiniHabileteB.setBounds(1030, 60, 200, 25);
			contentPane.add(btnReiniHabileteB);

			btnEnregistrer.setVisible(false);
		}
		btnEnregistrer.setEnabled(false);
		btnEnregistrer.setBounds(1051, 60, 126, 35);
		contentPane.add(btnEnregistrer);

		panChoixmissile = new JPanel();
		panChoixmissile.setBorder(new LineBorder(new Color(0, 0, 0)));
		panChoixmissile.setBounds(1051, 100, 343, 120);
		contentPane.add(panChoixmissile);
		panChoixmissile.setLayout(null);

		lanceur = new LanceurMissile();
		lanceur.setBounds(641, 300, 150, 100);
		lanceur.setBackground(null);
		contentPane.add(lanceur);

		lblAfficheAngleV = new JLabel(lanceur.getAngleVertical() + " °");

		lblAfficheAngleH = new JLabel(lanceur.getAngleHorizontal() + " °");

		zoneBateauA = new ZoneBateau(true, terrainDeJeu, modeTesteur); // terrainDeJeu
		zoneBateauA.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				confirmationVisible();
			}
		});

		zoneBateauA.setBounds(placementZoneX, placementZoneBasY, 965, 341);
		zoneBateauA.setOpaque(false);
		contentPane.add(zoneBateauA);

		zoneBateauB = new ZoneBateau(false, terrainDeJeu, modeTesteur); // terrainDeJeu
		zoneBateauB.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				confirmationVisible();
			}
		});

		zoneBateauB.setBackground(new Color(252, 255, 243));
		zoneBateauB.setOpaque(false);
		zoneBateauB.setBounds(placementZoneX, placementZoneHautY, 965, 341);
		zoneBateauB.cacheBateaux(false);
		contentPane.add(zoneBateauB);

		panSystemeTours = new SystemeTours(afficherMinuteur, zoneBateauA, zoneBateauB);
		panSystemeTours.setMinuteur(afficherMinuteur);
		panSystemeTours.setBorder(new LineBorder(new Color(0, 0, 0)));
		panSystemeTours.setBounds(1233, 17, 161, 67);
		panSystemeTours.setPartieCommencee(false, true);
		SystemeTours.placementBateau = 0;
		contentPane.add(panSystemeTours);

		contentPane.setFocusable(true); // Rendre le contenu de la fenêtre focusable
		contentPane.setLayout(null);

		panCommandesTir = new JPanel();
		panCommandesTir.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panCommandesTir.setBounds(1051, 230, 343, 211);
		panCommandesTir.setLayout(null);
		panCommandesTir.setVisible(false);
		contentPane.add(panCommandesTir);

		jauge = new Jauge();
		jauge.setBounds(21, 45, 51, 121);
		panCommandesTir.add(jauge);

		lblVitesseI = new JLabel("Vitesse initiale");
		lblVitesseI.setBounds(90, 31, 92, 16);
		panCommandesTir.add(lblVitesseI);

		lblAfficheVitesseI = new JLabel(String.format("%.0f m/s", jauge.getVitesseInitiale()));
		lblAfficheVitesseI.setHorizontalAlignment(SwingConstants.CENTER);
		lblAfficheVitesseI.setBounds(106, 59, 61, 16);
		panCommandesTir.add(lblAfficheVitesseI);

		lblAngleVert = new JLabel("Angle Vertical");
		lblAngleVert.setBounds(236, 96, 92, 16);
		panCommandesTir.add(lblAngleVert);

		lblAngleH = new JLabel("Angle horizontal");
		lblAngleH.setBounds(228, 31, 109, 16);
		panCommandesTir.add(lblAngleH);
		lblAfficheAngleV.setHorizontalAlignment(SwingConstants.CENTER);
		lblAfficheAngleV.setBounds(252, 124, 61, 16);
		panCommandesTir.add(lblAfficheAngleV);
		lblAfficheAngleH.setHorizontalAlignment(SwingConstants.CENTER);
		lblAfficheAngleH.setBounds(252, 59, 61, 16);
		panCommandesTir.add(lblAfficheAngleH);

		btnTir = new JButton("Tirer!");
		btnTir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panSystemeTours.getPartieCommencee()) {

					btnOptions.setEnabled(false);
					btnEnregistrer.setEnabled(false);

					gererHabileteNonUtilisee();
					if (panSystemeTours.isTourJoueur1()) {
						if (!enCoursDAnimation) {
							zoneBateauB.setArret(false);
							zoneBateauB.demarrer();
						}

					} else if (panSystemeTours.isTourJoueur2() && !matchContreIA) {
						if (!enCoursDAnimation) {
							zoneBateauA.setArret(false);
							zoneBateauA.demarrer();
						}

					}

					if (!enCoursDAnimation) {
						btnArreter.setVisible(true);
						demarrer();
					}
				} else {

				}

				// Permet de déselectionner le bouton et avoir accès aux autres commandes même
				// quand il a été pressé.
				btnTir.setFocusable(false);
				btnTir.setVisible(false);

			}

		});
		btnTir.setFocusable(true);
		btnTir.setBounds(113, 160, 117, 29);
		panCommandesTir.add(btnTir);

		lblCommandesTir = new JLabel("Commandes de tir :");
		lblCommandesTir.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblCommandesTir.setBounds(21, 6, 146, 16);
		panCommandesTir.add(lblCommandesTir);

		btnPlaceAleatoir.setBounds(1051, 414, 161, 41);
		btnPlaceAleatoir.setText("Placer aléatoirement");
		btnPlaceAleatoir.setVisible(true);
		contentPane.add(btnPlaceAleatoir);
		btnPlaceAleatoir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bateauPlaceAleatoire();
				confirmationVisible();
			}
		});

		boutonEmplacementBateaux = new JButton();
		confirmationVisible();
		boutonEmplacementBateaux.setBounds(1087, 372, 270, 41);
		boutonEmplacementBateaux.setText("Confirmer l'emplacement des bateaux");
		boutonEmplacementBateaux.setFocusable(true);
		contentPane.add(boutonEmplacementBateaux);

		panSortiesPhysique = new JPanel();
		panSortiesPhysique.setBackground(new Color(0, 0, 0));
		panSortiesPhysique.setBorder(new LineBorder(new Color(0, 0, 0)));
		panSortiesPhysique.setBounds(32, 55, 325, 739);
		contentPane.add(panSortiesPhysique);
		panSortiesPhysique.setLayout(null);

		lblGuidageMissile = new JLabel("Guidage du missile");
		lblGuidageMissile.setForeground(new Color(50, 106, 73));
		lblGuidageMissile.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGuidageMissile.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuidageMissile.setBounds(61, 11, 183, 19);
		panSortiesPhysique.add(lblGuidageMissile);

		panGraphiqueVitesse = new GraphPanel("vitesse");
		panGraphiqueVitesse.setBackground(new Color(0, 0, 0));
		panGraphiqueVitesse.setBounds(42, 71, 240, 164);
		panSortiesPhysique.add(panGraphiqueVitesse);

		panGraphiqueHauteur = new GraphPanel("hauteur");
		panGraphiqueHauteur.setBackground(new Color(0, 0, 0));
		panGraphiqueHauteur.setBounds(42, 272, 240, 164);
		panSortiesPhysique.add(panGraphiqueHauteur);

		lblPresGraphVit = new JLabel("Évolution de la vitesse du missile: ");
		lblPresGraphVit.setForeground(new Color(50, 106, 73));
		lblPresGraphVit.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPresGraphVit.setBounds(42, 55, 240, 9);
		panSortiesPhysique.add(lblPresGraphVit);

		JLabel lblPresGraphHaut = new JLabel("Évolution de la hauteur du missile: ");
		lblPresGraphHaut.setForeground(new Color(50, 106, 73));
		lblPresGraphHaut.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPresGraphHaut.setBounds(42, 256, 240, 9);
		panSortiesPhysique.add(lblPresGraphHaut);

		panInfoGuidageMissile = new InfoGuidageMissile();
		panInfoGuidageMissile.setBounds(42, 504, 240, 229);
		panInfoGuidageMissile.setOpaque(false);
		panSortiesPhysique.add(panInfoGuidageMissile);
		InfoGuidageMissile.reinitialiserA0();

		lblInfosMissile = new JLabel("Estimation de la trajectoire");
		lblInfosMissile.setForeground(new Color(50, 106, 73));
		lblInfosMissile.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblInfosMissile.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfosMissile.setBounds(42, 473, 240, 19);
		panSortiesPhysique.add(lblInfosMissile);

		sliderPasDeTemps = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
		sliderPasDeTemps.setOpaque(true);
		sliderPasDeTemps.setBackground(Color.BLACK);
		sliderPasDeTemps.setForeground(new Color(50, 105, 73));
		sliderPasDeTemps.setBounds(0, 698, 325, 41);
		sliderPasDeTemps.setMajorTickSpacing(3);
		sliderPasDeTemps.setMinorTickSpacing(1);
		sliderPasDeTemps.setPaintTicks(true);
		panSortiesPhysique.add(sliderPasDeTemps);
		sliderPasDeTemps.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				changePasDeTemps();
				contentPane.requestFocusInWindow();

			}
		});

		lblPasDeTemp = new JLabel("Pas de temps: " + (double) sliderPasDeTemps.getValue() / 100 + " secondes");
		lblPasDeTemp.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasDeTemp.setBounds(0, 676, 325, 16);
		lblPasDeTemp.setForeground(new Color(50, 106, 73));
		panSortiesPhysique.add(lblPasDeTemp);

		btnReplace.setBounds(1218, 414, 161, 41);
		btnReplace.setVisible(true);
		btnReplace.setText("Réinitialiser la position");
		contentPane.add(btnReplace);
		btnReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bateauReplace();
				confirmationVisible();
			}
		});

		lblMunitions = new JLabel("Choix des munitions :");
		lblMunitions.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblMunitions.setHorizontalAlignment(SwingConstants.LEFT);
		lblMunitions.setBounds(26, 6, 150, 27);
		panChoixmissile.add(lblMunitions);

		cmbChoixMissile.setBounds(188, 7, 149, 27);
		panChoixmissile.add(cmbChoixMissile);
		String[] options = { "R-360-1", "AGM-84-K2" };
		cmbChoixMissile.setModel(new DefaultComboBoxModel<>(options));

		lblMasseMissile = new JLabel("Masse: " + (int) Missile.getMasse() + " kg");
		lblMasseMissile.setBounds(64, 35, 107, 27);
		panChoixmissile.add(lblMasseMissile);

		affichageMissile = new AffichageMissile();
		panChoixmissile.add(affichageMissile);
		affichageMissile.setBounds(6, 25, 56, 89);

		lblChargeMissile = new JLabel("Charge: " + Missile.getCharge() + " C");
		lblChargeMissile.setBounds(64, 61, 120, 16);
		panChoixmissile.add(lblChargeMissile);

		lblIndiqueMissile = new JLabel("MISSILE EN UTILISATION");
		lblIndiqueMissile.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndiqueMissile.setBounds(74, 98, 252, 16);
		panChoixmissile.add(lblIndiqueMissile);
		affichageMissile.setVisible(true);
		affichageMissile.repaint();

		cmbChoixMissile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choixOption = cmbChoixMissile.getSelectedItem().toString();
				affichageMissile.setNomMissile(choixOption);
				Missile.setChoixImage(choixOption);
				zoneBateauA.miseAJourMissile();
				zoneBateauB.miseAJourMissile();
				lblMasseMissile.setText("Masse: " + (int) Missile.getMasse() + " kg");
				lblChargeMissile.setText(("Charge: " + Missile.getCharge()) + " C");
				affichageMissile.repaint();
				contentPane.requestFocusInWindow();
			}
		});




		if (modeTesteur) {
			zoneBateauA.setHabileteMax(false);
			zoneBateauB.setHabileteMax(false);
		}


		if (matchContreIA) {
			compteurObtention1=-1;
		}

		if (!modeTesteur) {
			if (!nomFichier.equals("")) {
				lectureFichierSauvegarde(nomFichier);
				if (matchContreIA) {
					ia = new IA();
					ia.setWidth((int) zoneBateauB.getEau().getBounds().getWidth());
					ia.setHeight((int) zoneBateauB.getEau().getBounds().getHeight());
					ia.setAccelGrav(-accelerationGravitationnelle);
				}
				demarrerPartieSauvegarde();
			}else {
				if (matchContreIA) {
					ia = new IA();
					ia.setWidth((int) zoneBateauB.getEau().getBounds().getWidth());
					ia.setHeight((int) zoneBateauB.getEau().getBounds().getHeight());
					ia.setAccelGrav(-accelerationGravitationnelle);
				}
			}
		}




		if (nomFichier.equals("temporaire")) {
			zoneBateauA.setTerrainDeJeuFichier(terrainDeJeu);
			zoneBateauB.setTerrainDeJeuFichier(terrainDeJeu);
		}

		donneeInitialeModeTesteur(modeTesteur);



		boutonEmplacementBateaux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionBtnConfirmation();
				debloquerEnregistrer();
			}
		});

		// Écouteur pour les flèches du clavier
		contentPane.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				int action = e.getKeyCode();

				executerRotation(e);

				if (action == KeyEvent.VK_SPACE) {

					if (!enCoursDAnimation) {
						jauge.setEspacePresse(true);
						// Formatage des décimales
						DecimalFormat df = new DecimalFormat("#.##");
						String vitesseFormatee = df.format(jauge.getVitesseInitiale());
						System.out.println("vitesse formatée: " + vitesseFormatee);

						// Affichage de la vitesse formattée
						lblAfficheVitesseI.setText(vitesseFormatee + " m/s");
					}

				} else if (modeFlecheActif) {

					if (!enCoursDAnimation && !(matchContreIA && panSystemeTours.isTourJoueur2())) {

						switch (action) {

						case KeyEvent.VK_UP:
							lanceur.flecheHautCliquee();
							break;
						case KeyEvent.VK_DOWN:
							lanceur.flecheBasCliquee();
							break;
						case KeyEvent.VK_RIGHT:
							lanceur.flecheDroiteCliquee();
							break;
						case KeyEvent.VK_LEFT:
							lanceur.fFlecheGaucheCliquee();

							break;
						}
					}

				} else {

					if (!enCoursDAnimation) {

						switch (action) {
						case KeyEvent.VK_W:
							lanceur.flecheHautCliquee();
							break;
						case KeyEvent.VK_S:
							lanceur.flecheBasCliquee();
							break;
						case KeyEvent.VK_D:
							lanceur.flecheDroiteCliquee();
							break;
						case KeyEvent.VK_A:
							lanceur.fFlecheGaucheCliquee();
							break;
						}

					}

				}
				lblAfficheAngleV.setText(lanceur.getAngleVertical() + "°");
				lblAfficheAngleH.setText(lanceur.getAngleHorizontal() + "°");

			}

			@Override
			public void keyReleased(KeyEvent e) {
				int action = e.getKeyCode();

				if (action == KeyEvent.VK_SPACE) {
					jauge.setEspacePresse(false);

				}
			}

		});


		zoneBateauA.setAccelGrav(-accelerationGravitationnelle);
		zoneBateauB.setAccelGrav(-accelerationGravitationnelle);

		btnArreter = new JButton("Pause");
		btnArreter.setBounds(429, 378, 93, 29);
		contentPane.add(btnArreter);

		btnReprendre = new JButton("Jouer");
		btnReprendre.setBounds(514, 378, 93, 29);
		contentPane.add(btnReprendre);

		btnAvancerUnPas = new JButton("Avancer d'un pas");
		btnAvancerUnPas.setBounds(429, 350, 178, 29);
		contentPane.add(btnAvancerUnPas);
		btnAvancerUnPas.setVisible(false);
		btnAvancerUnPas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneUtilisation().avancerUnPas();
			}
		});
		btnReprendre.setVisible(false);
		btnReprendre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneUtilisation().demarrer();
				btnReprendre.setVisible(false);
				btnAvancerUnPas.setVisible(false);
				btnArreter.setVisible(true);

			}
		});
		btnArreter.setVisible(false);
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneUtilisation().arreter();
				btnArreter.setVisible(false);
				btnReprendre.setVisible(true);
				btnAvancerUnPas.setVisible(true);
			}
		});

		Thread processusAnim = new Thread(this);
		processusAnim.start();
	}

	/**
	 * Methode qui demarre le thread
	 */
	// Gabriel Maurice
	public void demarrer() {
		enCoursDAnimation = true;
		btnTir.setFocusable(false);
	}

	/**
	 * La methode qui sera executee quand le thread sera demarre
	 */
	// Gabriel Maurice
	public void run() {
		while (true) {
			if (panSystemeTours.getPartieCommencee() && !zoneBateauA.getMissileAire() && !zoneBateauA.getMissileAire()
					&& matchContreIA && panSystemeTours.isTourJoueur2()) {
				// Pause de 1 sec

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Processus interrompu!");
				}
				zoneBateauA.setArret(false);
				btnArreter.setVisible(true);
				zoneBateauA.demarrer();

				demarrer();
			}

			if (enCoursDAnimation) {
				// btnTir.setVisible(false);
				// btnTir.setFocusable(afficherMinuteur);
				cmbChoixMissile.setVisible(false);
				btnTir.setVisible(false);
				btnTir.setFocusable(false);

				if (zoneBateauA.getCollision() || zoneBateauB.getCollision()) {

					if (modeTesteur) {
						if (zoneBateauB.getListBateaux().size() == 3) { // Bien mettre 3 ici (2 bateaux à couler)
							fenetreVictoire = new FenetreVictoire(1);
							fenetreVictoire.setVisible(true);
							fenetreVictoire.setAlwaysOnTop(true);
							//							setVisible(false);
							//							dispose();
						} else {
							if (zoneBateauA.getListBateaux().size() == 3) {
								fenetreVictoire = new FenetreVictoire(2);
								fenetreVictoire.setVisible(true);
								fenetreVictoire.setAlwaysOnTop(true);
								//								setVisible(false);
								//								dispose();
							}
						}
					} else {
						if (zoneBateauB.getListBateaux().size() == 1) {
							fenetreVictoire = new FenetreVictoire(1);
							fenetreVictoire.setVisible(true);
							setVisible(false);
							dispose();
						} else {
							if (zoneBateauA.getListBateaux().size() == 1) {
								fenetreVictoire = new FenetreVictoire(2);
								fenetreVictoire.setVisible(true);
								setVisible(false);
								dispose();
							}
						}

					}
					arreter();
					jauge.remettreA0();
					lblAfficheVitesseI.setText("30 m/s");
					btnArreter.setVisible(false);
					btnOptions.setEnabled(true);
					btnEnregistrer.setEnabled(true);

				}
			}

			btnTir.setVisible(true);
			btnTir.setFocusable(true);

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("Processus interrompu!");
			}
		}
	}

	/**
	 * Methode qui arrete le thread
	 */
	// Gabriel Maurice
	public void arreter() {
		btnReprendre.setVisible(false);
		btnAvancerUnPas.setVisible(false);
		btnArreter.setVisible(false);
		cmbChoixMissile.setVisible(true);

		// Pause de 3 secondes
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("Processus interrompu!");
		}

		try {
			gererTransitionEntreTours();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		supprimerCourbeGraph();
		panSystemeTours.modificationTours();
		InfoGuidageMissile.reinitialiserA0();
		zoneBateauA.resetMissile();
		zoneBateauB.resetMissile();
		switchZoneBateau();
		enCoursDAnimation = false;
		btnTir.setFocusable(true);
		btnTir.setVisible(true);
		gererObtentionHabilete();
		gererDureeHabilete();

	}

	/**
	 * Cette methode permet de lire le fichier venant des options. Si le fichier
	 * n'existe pas, il lira le fichier initial
	 * 
	 * @throws ClassNotFoundException Cette exception indique qu'une classe
	 *                                spécifiée n'a pas pu être trouvée lors de
	 *                                l'exécution du programme
	 */
	// Cedric Thai
	private void lectureFichierOptions() throws ClassNotFoundException {
		File fichierDeTravail;
		ObjectInputStream fluxEntree = null;

		String direction = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
		direction += File.separator + "Options" + File.separator + "modifie.txt";

		File fichierOptions = new File(direction);

		if (fichierOptions.exists()) {
			try {
				fichierDeTravail = new File(direction);
				fluxEntree = new ObjectInputStream(new FileInputStream(fichierDeTravail));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String directionIni = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
			directionIni += File.separator + "Initial" + File.separator + "depart.txt";
			File fichierInitial = new File(directionIni);
			if (fichierInitial.exists()) {
				try {
					fichierDeTravail = new File(directionIni);
					fluxEntree = new ObjectInputStream(new FileInputStream(fichierDeTravail));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		try {

			modeFlecheActif = fluxEntree.readBoolean();
			accelerationGravitationnelle = fluxEntree.readDouble();
			InfoGuidageMissile.setFacteurGravite(accelerationGravitationnelle);
			terrainDeJeu = fluxEntree.readObject().toString();
			afficherMinuteur = fluxEntree.readBoolean();
		} catch (FileNotFoundException e) {
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
			e.printStackTrace();
			System.exit(0);
		} finally {
			try {
				fluxEntree.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la fermeture!");
			}

		}
	}

	/**
	 * Méthode qui permet d'envoyer une rotation dans zoneBateau si la touche R est
	 * enfoncée.
	 * 
	 * @param e relié aux touches du clavier
	 */
	// Noé Julien
	public void executerRotation(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) {
			if (zoneBateauA.isEditeur()) {
				zoneBateauA.executerRotation();
			} else if (zoneBateauB.isEditeur()) {
				zoneBateauB.executerRotation();
			} else if (zoneBateauA.getEditeurHabiletes()) {
				zoneBateauA.rotationHabiletes();
			} else if (zoneBateauB.getEditeurHabiletes()) {
				zoneBateauB.rotationHabiletes();
			}

		} else if (e.getKeyCode() == KeyEvent.VK_G) {
			zoneBateauA.redimensionHabiletes();
			zoneBateauB.redimensionHabiletes();
		}
	}

	/**
	 * Méthode qui permet de confirmer le placement des 5 bateaux sur la zone de
	 * jeu, elle appelle aussi plusieurs autres méthodes.
	 */
	// Noé Julien
	public void actionBtnConfirmation() {
		SystemeTours.placementBateau++;
		if (SystemeTours.placementBateau == 1 && zoneBateauA.bateauxPlace()) {
			panSystemeTours.setTourJoueur2();
			zoneBateauB.cacheBateaux(true);
			zoneBateauB.setEditeur(true);
			zoneBateauA.setEditeur(false);
			zoneBateauA.setEditeurHabiletes(true);
			if (modeTesteur) {
				zoneBateauA.debloquerHabiletesTest();
			}
			switchZoneBateau();
			panSystemeTours.repaint();
		} else if (zoneBateauA.bateauxPlace() == false) {
			SystemeTours.placementBateau--;
		}

		if (SystemeTours.placementBateau == 2 && zoneBateauB.bateauxPlace()) {
			zoneBateauB.setEditeur(false);
			zoneBateauB.setEditeurHabiletes(true);
			if (modeTesteur) {
				zoneBateauB.debloquerHabiletesTest();
			}
			panSystemeTours.setPartieCommencee(true, true);
			boutonEmplacementBateaux.setVisible(false);
			boutonEmplacementBateaux.setFocusable(false);
			panCommandesTir.setVisible(true);
			btnReplace.setVisible(false);
			btnPlaceAleatoir.setVisible(false);
			switchZoneBateau();
			panSystemeTours.repaint();
			gererObtentionHabilete();
		} else if (!zoneBateauB.bateauxPlace() && SystemeTours.placementBateau >= 2) {
			SystemeTours.placementBateau--;
		}
		contentPane.requestFocusInWindow();
		confirmationVisible();
	}

	/**
	 * Méthode pour renvoyer quel zoneBateau est en animation
	 * 
	 * @return la zoneBateau qui est en animation
	 */
	// Noé Julien
	public ZoneBateau paneUtilisation() {
		if (panSystemeTours.isTourJoueur1()) {
			return zoneBateauB;
		} else if (panSystemeTours.isTourJoueur2()) {
			return zoneBateauA;
		}
		return null;
	}

	/**
	 * Méthode qui change les zones de placement en fonction du tour du joueur.
	 */
	// Noé Julien
	public void switchZoneBateau() {
		if (panSystemeTours.isTourJoueur1()) {
			zoneBateauB.setInverse(true);
			zoneBateauA.setInverse(false);

			if (panSystemeTours.getPartieCommencee()) {

				zoneBateauA.setEditeurHabiletes(panSystemeTours.isTourJoueur1());
				zoneBateauB.setEditeurHabiletes(panSystemeTours.isTourJoueur2());
			}

			zoneBateauA.setBounds(placementZoneX, placementZoneBasY, 965, 341);
			zoneBateauB.setBounds(placementZoneX, placementZoneHautY, 965, 341);
		} else {
			zoneBateauA.setInverse(true);
			zoneBateauB.setInverse(false);

			if (panSystemeTours.getPartieCommencee()) {

				zoneBateauA.setEditeurHabiletes(panSystemeTours.isTourJoueur1());
				zoneBateauB.setEditeurHabiletes(panSystemeTours.isTourJoueur2());
			}

			zoneBateauA.setBounds(placementZoneX, placementZoneHautY, 965, 341);
			zoneBateauB.setBounds(placementZoneX, placementZoneBasY, 965, 341);
		}

		jauge.remettreA0();
		lblAfficheVitesseI.setText("30 m/s");
		if (matchContreIA) {
			miseAJourIA();

		}

		contentPane.requestFocusInWindow();
	}

	/**
	 * Méthode envoie dans la zone bateau en mode éditeur de replacer les bateaux à
	 * leur positions initales en mode éditeur
	 */
	// Noé Julien
	private void bateauReplace() {
		if (zoneBateauA.isEditeur()) {
			zoneBateauA.replacePositionInit();
		} else if (zoneBateauB.isEditeur()) {
			zoneBateauB.replacePositionInit();
		}
		contentPane.requestFocusInWindow();
	}

	/**
	 * Méthode qui place aléatoirement les bateaux dans la zone bateau en mode
	 * éditeur.
	 */
	// Noé Julien
	private void bateauPlaceAleatoire() {
		if (zoneBateauA.isEditeur()) {
			zoneBateauA.placeAleatoirement();
		} else if (zoneBateauB.isEditeur()) {
			zoneBateauB.placeAleatoirement();
		}
		contentPane.requestFocusInWindow();
	}

	/**
	 * Méthode qui changé la valeur de si le son est à activer ou non
	 * 
	 * @param sonOcean nouvelle valeur booléenne
	 */
	// Noé Julien
	public static void setSonOcean(boolean sonOcean) {

		FenetreTerrainJeu.sonOcean = sonOcean;
		FenetreTerrainJeu.sonOcean = false;

	}

	/**
	 * Méthode qui permet de changer l'étiquette qui affiche le pas de temps et de
	 * renvoyer la nouveau pas de temp dans la classe ZoneBateau
	 */
	// Noé Julien
	private void changePasDeTemps() {
		lblPasDeTemp.setText("Pas de temps:" + (double) sliderPasDeTemps.getValue() / 100 + " secondes");
		ZoneBateau.setDeltaT((double) sliderPasDeTemps.getValue() / 100);
	}

	/**
	 * Méthode qui permet de mettre à jour l'affichage du bouton de confirmation
	 */
	// Noé Julien
	public void confirmationVisible() {
		if (zoneBateauA.isEditeur() && zoneBateauA.bateauxPlace()) {
			boutonEmplacementBateaux.setVisible(true);
		} else if (zoneBateauB.isEditeur() && zoneBateauB.bateauxPlace()) {
			boutonEmplacementBateaux.setVisible(true);
		} else if (zoneBateauB.isEditeur() && !zoneBateauB.bateauxPlace()) {
			boutonEmplacementBateaux.setVisible(false);
		} else {
			boutonEmplacementBateaux.setVisible(false);
		}
	}

	/**
	 * Méthode qui débloque le bouton enregistrer seulement après les placements des
	 * bateaux
	 */
	// Cedric Thai
	private void debloquerEnregistrer() {
		if (SystemeTours.placementBateau == 2) {
			btnEnregistrer.setEnabled(true);
		}
	}// fin methode

	/**
	 * Cette methode permet de sauvegarder l'etat du jeu
	 * 
	 * @param nomSauvegarde le nom de la sauvegarde
	 */
	// Cedric Thai
	public void ecritureFichierSauvegarde(String nomSauvegarde) {
		String nomFichierSauvegarde;
		if (nomSauvegarde.equals("temporaire")) {
			nomFichierSauvegarde = "temporaire";
		} else {
			nomFichierSauvegarde = nomSauvegarde + ".save";
		}
		// Creation dossier

		String direction;
		if (nomSauvegarde.equals("temporaire")) {
			direction = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
			direction += File.separator + "Temporaire";
		} else {
			direction = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
			direction += File.separator + "Sauvegarde";
		}

		File testDirection = new File(direction);

		if (testDirection.exists()) {
		} else if (testDirection.mkdirs()) {
		} else {
		}
		// Fin creation dossier

		File fichierDeTravail = new File(testDirection, nomFichierSauvegarde);
		ObjectOutputStream fluxSortie = null;
		try {
			try {
				fluxSortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichierDeTravail)));


				if (matchContreIA) {
					fluxSortie.writeBoolean(true);
				}else {
					fluxSortie.writeBoolean(false);
				}

				if (panSystemeTours.isTourJoueur1()) {
					fluxSortie.writeBoolean(true);
				} else {
					fluxSortie.writeBoolean(false);
				}

				fluxSortie.writeInt(panSystemeTours.getNbToursJoueur1());
				fluxSortie.writeInt(panSystemeTours.getNbToursJoueur2());

				fluxSortie.writeInt(panSystemeTours.getNbTours());
				fluxSortie.writeInt(panSystemeTours.getTempsRestant());

				fluxSortie.writeObject(zoneBateauA.getListBateaux());
				fluxSortie.writeObject(zoneBateauB.getListBateaux());

				fluxSortie.writeObject(zoneBateauA.getTrous());
				fluxSortie.writeObject(zoneBateauB.getTrous());

				fluxSortie.writeObject(zoneBateauA.getHabiletesSpeciales());
				fluxSortie.writeObject(zoneBateauB.getHabiletesSpeciales());

				fluxSortie.writeObject(zoneBateauA.getTerrainDeJeuFichier().toString());
				fluxSortie.writeObject(zoneBateauA.getTerrainDeJeuFichier().toString());

				fluxSortie.writeInt(compteurObtention1);
				fluxSortie.writeInt(compteurObtention2);

				fluxSortie.writeInt(compteurDuree1);
				fluxSortie.writeInt(compteurDuree2);

				fluxSortie.writeBoolean(zoneBateauA.getHabileteBougeable());
				fluxSortie.writeBoolean(zoneBateauB.getHabileteBougeable());

				fluxSortie.writeBoolean(zoneBateauA.getHabileteTouchee());
				if (compteurObtention1 == 0 && !zoneBateauA.getHabileteTouchee()) {
					fluxSortie.writeInt(zoneBateauA.getDerniereHabilete());
				}

				fluxSortie.writeBoolean(zoneBateauB.getHabileteTouchee());
				if (compteurObtention2 == 0 && !zoneBateauB.getHabileteTouchee()) {
					fluxSortie.writeInt(zoneBateauB.getDerniereHabilete());
				}



			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			// on ex�cutera toujours ceci, erreur ou pas
			try {
				fluxSortie.close();
			} catch (IOException e) {
				System.out.println("Erreur rencontree lors de la fermeture!");
			}
		} // fin finally
	}

	/**
	 * Methode qui permet de lire l'etat du jeu charge
	 * 
	 * @param nomFichier le nom du fichier charger
	 */
	// Cedric Thai
	private void lectureFichierSauvegarde(String nomFichier) {
		File fichierDeTravail;
		ObjectInputStream fluxEntree = null;

		String direction;
		if (nomFichier.equals("temporaire")) {
			direction = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
			direction += File.separator + "Temporaire";
		} else {
			direction = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
			direction += File.separator + "Sauvegarde";
		}

		File fichierSauvegarde = new File(direction);

		if (fichierSauvegarde.exists()) {
			try {
				fichierDeTravail = new File(direction, nomFichier);
				fluxEntree = new ObjectInputStream(new FileInputStream(fichierDeTravail));


				matchContreIA=fluxEntree.readBoolean();

				tourJoueurCorrespondant = fluxEntree.readBoolean();


				panSystemeTours.setNbToursJoueur1(fluxEntree.readInt());
				panSystemeTours.setNbToursJoueur2(fluxEntree.readInt());

				panSystemeTours.setNbTours(fluxEntree.readInt());
				panSystemeTours.setTempsRestant(fluxEntree.readInt());

				zoneBateauA.setListBateaux(fluxEntree.readObject());
				zoneBateauB.setListBateaux(fluxEntree.readObject());

				zoneBateauA.setTrous(fluxEntree.readObject());
				zoneBateauB.setTrous(fluxEntree.readObject());

				zoneBateauA.setEditeurHabiletes(panSystemeTours.isTourJoueur1());
				zoneBateauB.setEditeurHabiletes(panSystemeTours.isTourJoueur2());

				if (!nomFichier.equals("")) {
					zoneBateauA.setHabiletesSpeciales(fluxEntree.readObject());
					zoneBateauB.setHabiletesSpeciales(fluxEntree.readObject());
				}

				zoneBateauA.setTerrainDeJeuFichier(fluxEntree.readObject().toString());
				zoneBateauB.setTerrainDeJeuFichier(fluxEntree.readObject().toString());

				compteurObtention1 = fluxEntree.readInt();
				compteurObtention2 = fluxEntree.readInt();

				compteurDuree1 = fluxEntree.readInt();
				compteurDuree2 = fluxEntree.readInt();


				zoneBateauA.setHabileteBougeable(fluxEntree.readBoolean());
				zoneBateauB.setHabileteBougeable(fluxEntree.readBoolean());

				if (compteurObtention1 == 0 && !fluxEntree.readBoolean()) {
					zoneBateauA.setHabileteMax(false);
					zoneBateauA.setDerniereHabilete(fluxEntree.readInt());
				}
				if (compteurObtention2 == 0 && !fluxEntree.readBoolean()) {
					zoneBateauB.setHabileteMax(false);
					zoneBateauB.setDerniereHabilete(fluxEntree.readInt());
				}


					

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {

			} finally {
				try {
					fluxEntree.close();
				} catch (IOException e) {
					System.out.println("Erreur rencontree lors de la fermeture!");
				}

			}
		}
	} // fin methode

	/**
	 * Méthode qui enregistre une partie
	 */
	// Cedric Thai
	private void enregistrer() {
		InfoGuidageMissile.reinitialiserA0();
		zoneBateauA.resetMissile();
		zoneBateauB.resetMissile();
		if (afficherMinuteur) {
			panSystemeTours.arreterTemps();
		}
		String nomFichier = JOptionPane.showInputDialog("Entrez le nom de votre sauvegarde :");
		if (nomFichier == null) {
			nomFichier = "";
		}
		if (nomFichier.equals("")) {
			JOptionPane.showMessageDialog(null, "Nom vide", "ERREUR", JOptionPane.WARNING_MESSAGE);
			contentPane.requestFocusInWindow();
		} else {
			File direction = new File(System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer" + "/Sauvegarde");
			File confirmerFichierExiste = new File(direction, nomFichier + ".save");

			if (confirmerFichierExiste.exists()) {
				JOptionPane.showMessageDialog(null, "Un fichier avec ce nom existe déjà", "ERREUR",
						JOptionPane.WARNING_MESSAGE);
				contentPane.requestFocusInWindow();
			} else {
				ecritureFichierSauvegarde(nomFichier);
				JOptionPane.showMessageDialog(null, "Enregistré avec succès");
				contentPane.requestFocusInWindow();
			}
		}
		if (afficherMinuteur) {
			panSystemeTours.demarrerTemps();
		}
	}// fin methode

	/**
	 * Méthode statique qui permet de lier la méthode calculerUnFrame du missile et
	 * la classe HauteurMissileGRaphPanel. Elle est appelée à chaque nouveau frame
	 * calculé par la classe missile. La méthode appelle la méthode nouvPoint de la
	 * classe du graphique permettant de créer un nouveau point.
	 * 
	 * @param temps   le temps écoulé.
	 * @param hauteur la hauteur du missile en mètres
	 * @param vitesse la vitesse du missile en m/s
	 */
	// Théo Fourteau
	public static void miseAJourGraphiques(double temps, double hauteur, double vitesse) {

		panGraphiqueHauteur.nouvPoint(temps, hauteur, vitesse);
		panGraphiqueVitesse.nouvPoint(temps, hauteur, vitesse);
		panGraphiqueHauteur.repaint();
		panGraphiqueVitesse.repaint();
	}

	/**
	 * Méthode statique qui permet de changer le nombre de lignes et les graduations
	 * des graphiques directement depuis la méthode de calcul dans la classe
	 * InfoGuidageMissile.
	 * 
	 * @param temps   le temps écoulé.
	 * @param hauteur la hauteur du missile en mètres
	 * @param vitesse la vitesse du missile en m/s
	 */
	// Théo Fourteau
	public static void miseAJourLignesGraphiques(double temps, double hauteur, double vitesse) {

		panGraphiqueHauteur.repaint();
		panGraphiqueVitesse.repaint();

	}

	/**
	 * Méthode qui permet de supprimer les courbes des deux graphiques du guidage du
	 * missile lorsque c'est le tour de l'autre joueur.
	 */
	// Théo fourteau
	public static void supprimerCourbeGraph() {
		panGraphiqueVitesse.supprimerCourbe();
		panGraphiqueHauteur.supprimerCourbe();
	}

	/**
	 * Méthode qui permet de redessiner le panneau des informat3ions du guidage du
	 * missile. La méthode est appelée dans la classe InfoGuidageMissile.
	 */
	// Théo Fourteau
	public static void dessinerInfosGuidage() {
		panInfoGuidageMissile.repaint();
	}

	/**
	 * Méthode qui permet de vérifier si la fenêtre est ouverte ou non
	 * 
	 * @return estOuvert le booléen qui confirme si oui ou non la fenêtre est
	 *         ouverte.
	 */
	// Théo Fourteau
	public static boolean estOuvert() {
		return estOuvert;
	}

	/**
	 * Methode qui met l'IA a jour, elle est appelee a chaque changement de zone de
	 * bateau
	 */
	// Gabriel Maurice
	private void miseAJourIA() {
		zoneBateauA.setMatchContreIA(matchContreIA);
		if (panSystemeTours.isTourJoueur2()) {
			zoneBateauB.setVisible(false);
		} else {
			zoneBateauB.setVisible(true);
		}

		if (!panSystemeTours.getPartieCommencee() && panSystemeTours.isTourJoueur2()) {
			bateauPlaceAleatoire();
			actionBtnConfirmation();
			debloquerEnregistrer();
		}

		if (panSystemeTours.getPartieCommencee()) {
			zoneBateauA.setMatchCommence(true);
		} else {
			zoneBateauA.setMatchCommence(false);
		}

		ia.setZoneIA(zoneBateauA);
		ia.calculerAireBateauxJoueur();

		if (panSystemeTours.isTourJoueur2()) {
			zoneBateauA.setAireIA(ia.getAireBateauxVisibles());
			zoneBateauA.setTourIA(true);
		} else {
			zoneBateauA.setAireIA(new Area());
			zoneBateauA.setTourIA(false);
		}

		if (panSystemeTours.isTourJoueur2() && panSystemeTours.getPartieCommencee()) {
			ia.creerListePointsPossibles();
			ia.choisirPointAleatoire();
			zoneBateauA.setPointIA(ia.getPointChoisiEllipse());

			ia.choisirParametresLancement();
			lanceur.setAngleHorizontal(ia.getAngleHor());

			lanceur.setAngleVertical(ia.getAngleVer());
		}

		lblAfficheAngleV.setText(lanceur.getAngleVertical() + "°");
		lblAfficheAngleH.setText(lanceur.getAngleHorizontal() + "°");
		lblAfficheVitesseI.setText(jauge.getVitesseInitiale() + "m/s");
	}

	/**
	 * Methode qui permet de gerer la transition entre les tours
	 * 
	 * @throws InterruptedException exception pour le thread
	 */
	// Cedric Thai
	private void gererTransitionEntreTours() throws InterruptedException {
		if (panSystemeTours.isTourJoueur1()) {
			zoneBateauB.transitionEntreTours(true, 2);
			zoneBateauA.transitionEntreTours(false, 2);
			Thread.sleep(5000);
			zoneBateauB.reinitialisationTimer();
		} else {
			zoneBateauA.transitionEntreTours(true, 1);
			zoneBateauB.transitionEntreTours(false, 1);
			Thread.sleep(5000);
			zoneBateauA.reinitialisationTimer();
		}
	}

	/**
	 * Methode qui permet de demarrer une partie venant d'une sauvegarde
	 */
	// Cedric Thai
	public void demarrerPartieSauvegarde() {
		zoneBateauA.setEditeur(false);
		zoneBateauA.setEditeurHabiletes(true);
		zoneBateauB.setEditeur(false);
		zoneBateauB.setEditeurHabiletes(true);
		panSystemeTours.setPartieCommencee(true, tourJoueurCorrespondant);
		boutonEmplacementBateaux.setVisible(false);
		boutonEmplacementBateaux.setFocusable(false);
		panCommandesTir.setVisible(true);
		btnReplace.setVisible(false);
		btnPlaceAleatoir.setVisible(false);
		panSystemeTours.repaint();
		btnEnregistrer.setEnabled(true);

		switchZoneBateau();
	}

	/**
	 * Méthode qui permet d'initialiser la partie à 1 ou 2 joueurs, depuis
	 * l'exterieur de la classe.
	 * 
	 * @param matchContreIA booléen qui confirme si oui ou non la partie se déroule
	 *                      en mode 1 joueur. Si le booléen entré est faux alors,
	 *                      c'est une partie à deux joueurs.
	 */
	// Théo Fourteau
	public static void setMatchContreIA(boolean matchContreIA) {
		FenetreTerrainJeu.matchContreIA = matchContreIA;
	}

	/**
	 * Methode qui permet de gerer l'obtention des habiletes a chaque 3 tours
	 */
	// Cedric Thai
	public void gererObtentionHabilete() {
		if (!modeTesteur) {
			if (panSystemeTours.isTourJoueur1()) {
				zoneBateauA.setHabileteTouchee(false);
				compteurObtention1++;
				if (compteurObtention1 == 3) {
					zoneBateauA.setHabileteMax(false);
					compteurObtention1 = 0;
				}

			}
			if (panSystemeTours.isTourJoueur2()) {
				zoneBateauB.setHabileteTouchee(false);
				compteurObtention2++;
				if (compteurObtention2 == 3) {
					zoneBateauB.setHabileteMax(false);
					compteurObtention2 = 0;
				}
			}
		}
	}

	/**
	 * Methode qui permet d'attribuer une duree limitee pour l'habilete (1 tour)
	 */
	// Cedric Thai
	public void gererDureeHabilete() {
		if (!modeTesteur) {
			if (panSystemeTours.isTourJoueur1() && (zoneBateauA.getHabiletesSpeciales().size() == 1)) {
				compteurDuree1++;
				if (compteurDuree1 == 1) {
					zoneBateauA.setHabileteBougeable(false);
					zoneBateauA.enleverHabilete();
					compteurDuree1 = 0;
				}
			}

			if (panSystemeTours.isTourJoueur2() && (zoneBateauB.getHabiletesSpeciales().size() == 1)) {
				compteurDuree2++;
				if (compteurDuree2 == 1) {
					zoneBateauB.setHabileteBougeable(false);
					zoneBateauB.enleverHabilete();
					compteurDuree2 = 0;
				}
			}
		}
	}

	/**
	 * Methode qui permet de retirer les habiletes non utilisee
	 */
	// Cedric Thai
	public void gererHabileteNonUtilisee() {

		if (panSystemeTours.isTourJoueur1()) {
			if (zoneBateauA.getHabiletesSpeciales().size() == 0) {
				zoneBateauA.setHabileteMax(true);
				zoneBateauA.setHabileteBougeable(false);
			}
		} else {
			if (zoneBateauB.getHabiletesSpeciales().size() == 0) {
				zoneBateauB.setHabileteMax(true);
				zoneBateauB.setHabileteBougeable(false);

				if (!modeTesteur) {
					if (panSystemeTours.isTourJoueur1()) {
						if (zoneBateauA.getHabiletesSpeciales().size() == 0) {
							zoneBateauA.setHabileteMax(true);
							zoneBateauA.setHabileteBougeable(false);
						}
					} else {
						if (zoneBateauB.getHabiletesSpeciales().size() == 0) {
							zoneBateauB.setHabileteMax(true);
							zoneBateauB.setHabileteBougeable(false);
						}

					}
				}
			}

		}
	}

	/**
	 * Methode qui retourne si la fenetreTerrainJeu est en mode testeur
	 * 
	 * @return valeur boolean qui determine si le mode testeur est active
	 */
	// Theo Fourteau
	public static boolean isModeTesteur() {
		return modeTesteur;
	}

	/**
	 * Methode qui modifie la fenetreTerrainJeu pour le mode testeur
	 * 
	 * @param modeTesteur valeur boolean qui determine si le mode testeur est active
	 */
	// Theo Fourteau
	public static void setModeTesteur(boolean modeTesteur) {
		FenetreTerrainJeu.modeTesteur = modeTesteur;
	}

	/**
	 * Methode qui installe les donnees initiales lors du mode testeur
	 * 
	 * @param modeTesteur boolean qui determine si le mode testeur est active
	 */
	// Cedric Thai
	public void donneeInitialeModeTesteur(boolean modeTesteur) {
		if (modeTesteur) {

			accelerationGravitationnelle = 9.81;
			zoneBateauA.setTerrainDeJeuFichier("ocean.jpg");
			zoneBateauB.setTerrainDeJeuFichier("ocean.jpg");

			accelerationGravitationnelle = 9.81;
			zoneBateauA.setTerrainDeJeuFichier("Terre.jpg");
			zoneBateauB.setTerrainDeJeuFichier("Terre.jpg");
			afficherMinuteur = false;
		}
	}

	/**
	 * Méthode qui permet de changer l'état d'ouverture de la fenêtre
	 * 
	 * @param estOuvert le nouvel état d'ouverture de la fenêtre.
	 */
	// Théo Fourteau
	public static void setEstOuvert(boolean estOuvert) {
		if (estOuvert) {
			System.out.println("La fenêtre Terrain de jeu est ouverte.");
		} else {
			System.out.println("La fenêtre Terrain de jeu est fermée.");
		}

		FenetreTutoriel.estOuvert = estOuvert;
	}

}