package application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import composantsDessin.BulleDialogue;
import composantsDessin.GraphPanel;
import composantsDessin.InfoGuidageMissile;
import composantsDessin.Jauge;
import composantsDessin.LanceurMissile;
import composantsDessin.ZoneBateau;
import outils.AjoutSon;
import outils.OutilsImage;
import partie.SystemeTours;

/**
 * Classe permettant a l'utilisateur de faire le tutoriel. La classe a la même
 * interface que la fenêtre Terrain de jeu, mais des bulles explicatives
 * permettent d'apprendre à jouer.
 * 
 * @author Théo Fourteau
 * @author Cedric Thai
 * @author Noé Julien
 * @author Gabriel Maurice
 * 
 */
public class FenetreTutoriel extends JFrame implements Runnable {

	/** identifiant de classe **/
	private static final long serialVersionUID = 1L;

	/** ACcélération gravitationnelle du mode de tutoriel **/
	private final float ACCELERATION_GRAVITATIONNELLE = -9.81f;

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

	/**
	 * Variable String qui représente le terrain de jeu sélectionné
	 */
	private String terrainDeJeu;

	/**
	 * Variable booléenne qui détermine si le minuteur est affiché
	 */
	private boolean afficherMinuteur = false;

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

	/** Coordonnée Y de la zone de placement du haut */
	private int placementZoneHautY = 6;
	/** Coordonnée Y de la zone de placement du bas */
	private int placementZoneBasY = 453;
	/** Coordonnée X des zones de placements */
	private int placementZoneX = 429;

	/** Si le composant est en cours d'animation **/
	private boolean enCoursDAnimation;
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
	 * missile change. >>>>>>> branch 'master' of
	 * https://gitlab.com/gabrielmaurice/33_guerreenmer.git
	 */
	private static InfoGuidageMissile panInfoGuidageMissile;
	/**
	 * Étiquette servant de sous-titre au panneau des informations du guidage du
	 * missile
	 **/
	private JLabel lblInfosMissile;
	/** Bulle qui affiche des informations liées à l'usage des graphiques **/
	private BulleDialogue bulleDialogueGraphique;
	/** Bulle qui affiche des informations liées à l'usage du canon **/
	private BulleDialogue bulleDialogueCanon;
	/** Bulle qui affiche des informations liées à l'usage des commandes de tir **/
	private BulleDialogue bulleDialogueTir;
	/** Bulle qui affiche des informations liées au placement des bateaux **/
	private BulleDialogue bulleDialogueBateaux;
	/**
	 * Bulle qui affiche des informations liées à l'utilisations des touches pour
	 * les habiletés
	 **/
	private BulleDialogue bulleDialogueCommandes;
	/** instance de FenetreTouchesClavier pour obtenir de l'aide sur les touches **/
	private FenetreTouchesClavier touchesClavier;
	/** Image de fond **/
	private Image imageFond;
	/** Variable de classe pour garder une trace de l'état de la fenêtre **/
	static boolean estOuvert = false;

	/**
	 * Lancement de la fenêtre du tutoriel
	 * 
	 * @param args argument de création d'application
	 */
	// Cedric Thai
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreTutoriel frame = new FenetreTutoriel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création du panneau du tutoriel
	 */
	// Théo Fourteau
	public FenetreTutoriel() {
		setEstOuvert(true);
		FenetreTerrainJeu.estOuvert = false;

		imageFond = OutilsImage.lireImage("FondTerrainJeu.png");
		AjoutSon.sonOcean();

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
		try {
			lectureFichierOptions();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentPane(contentPane);

		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AjoutSon.checkVolume();
				AjoutSon.sonPrincipal();
				AjoutSon.stopSonOcean();
				FenetreTutoriel.setEstOuvert(false);
				retour = new AppPrincipale33();
				retour.setVisible(true);
				setVisible(false);
			}
		});

		bulleDialogueBateaux = new BulleDialogue(0, 0, 200, 150, "gauche", "bateaux");
		bulleDialogueBateaux.setBounds(1200, 453, 278, 211);
		bulleDialogueBateaux.setOpaque(false);
		contentPane.add(bulleDialogueBateaux);
		contentPane.setComponentZOrder(bulleDialogueBateaux, 0);
		bulleDialogueBateaux.setVisible(true);

		bulleDialogueCanon = new BulleDialogue(0, 0, 200, 100, "droite", "canon");
		bulleDialogueCanon.setBounds(369, 313, 299, 142);
		contentPane.add(bulleDialogueCanon);
		bulleDialogueCanon.setOpaque(false);
		contentPane.setComponentZOrder(bulleDialogueCanon, 0);
		bulleDialogueCanon.setVisible(false);

		bulleDialogueGraphique = new BulleDialogue(0, 0, 200, 100, "gauche", "graphique");
		bulleDialogueGraphique.setBounds(277, 18, 278, 202);
		contentPane.add(bulleDialogueGraphique);
		bulleDialogueGraphique.setOpaque(false);
		contentPane.setComponentZOrder(bulleDialogueGraphique, 0);
		bulleDialogueGraphique.setVisible(false);

		bulleDialogueTir = new BulleDialogue(0, 0, 200, 100, "droite", "tir");
		bulleDialogueTir.setBounds(761, 313, 291, 191);
		contentPane.add(bulleDialogueTir);
		bulleDialogueTir.setOpaque(false);
		contentPane.setComponentZOrder(bulleDialogueTir, 0);
		bulleDialogueTir.setVisible(false);

		bulleDialogueCommandes = new BulleDialogue(0, 0, 200, 100, "droite", "commandes");
		bulleDialogueCommandes.setVisible(false);
		bulleDialogueCommandes.setBounds(289, 453, 251, 158);
		contentPane.add(bulleDialogueCommandes);
		bulleDialogueCommandes.setOpaque(false);
		contentPane.setComponentZOrder(bulleDialogueCommandes, 0);

		btnRetour.setBounds(0, 0, 92, 23);
		contentPane.add(btnRetour);

		JButton btnTouches = new JButton("Touches");
		btnTouches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				touchesClavier = new FenetreTouchesClavier();
				touchesClavier.setVisible(true);
				touchesClavier.setAlwaysOnTop(true);
				btnTouches.setVisible(true);
//				setVisible(false);
			}
		});
		btnTouches.setBounds(0, 20, 92, 23);
		contentPane.add(btnTouches);

		lanceur = new LanceurMissile();
		lanceur.setBounds(641, 300, 150, 100);
		lanceur.setBackground(null);
		contentPane.add(lanceur);

		lblAfficheAngleV = new JLabel(lanceur.getAngleVertical() + " °");

		lblAfficheAngleH = new JLabel(lanceur.getAngleHorizontal() + " °");

		terrainDeJeu = "Terre.jpg";
		zoneBateauA = new ZoneBateau(true, terrainDeJeu, false); // terrainDeJeu
		zoneBateauA.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				confirmationVisible();
			}
		});

		zoneBateauA.setBounds(placementZoneX, placementZoneBasY, 965, 341);
		zoneBateauA.setOpaque(false);
		contentPane.add(zoneBateauA);
		zoneBateauA.setTutoriel(true);
		zoneBateauB = new ZoneBateau(false, terrainDeJeu, false); // terrainDeJeu
		zoneBateauB.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				confirmationVisible();
			}
		});

		zoneBateauB.setBackground(new Color(252, 255, 243));
		zoneBateauB.setOpaque(false);
		zoneBateauB.setTutoriel(true);
		zoneBateauB.setBounds(placementZoneX, placementZoneHautY, 965, 341);
		zoneBateauB.cacheBateaux(false);
		contentPane.add(zoneBateauB);

		panSystemeTours = new SystemeTours(afficherMinuteur, null, null);
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

		lblAfficheVitesseI = new JLabel((int) jauge.getVitesseInitiale() + " m/s");
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

					if (panSystemeTours.isTourJoueur1()) {
						zoneBateauB.demarrer();
					} else {
						zoneBateauA.demarrer();
					}

					demarrer();
					btnTir.setFocusable(false);

				} else {

				}

				// Permet de déselectionner le bouton et avoir accès aux autres commandes même
				// quand il a été pressé.
				btnTir.setFocusable(false);

			}
		});
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
		boutonEmplacementBateaux.setBounds(1087, 372, 270, 41);
		boutonEmplacementBateaux.setText("Confirmer l'emplacement des bateaux");
		boutonEmplacementBateaux.setFocusable(true);
		contentPane.add(boutonEmplacementBateaux);
		confirmationVisible();

		panSortiesPhysique = new JPanel();
		panSortiesPhysique.setBackground(Color.BLACK);
		panSortiesPhysique.setBorder(new LineBorder(Color.BLACK));
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

		lblInfosMissile = new JLabel("Estimation de la trajectoire");
		lblInfosMissile.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblInfosMissile.setForeground(new Color(50, 106, 73));
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

		lblPasDeTemp = new JLabel("Pas de temp: " + (double) sliderPasDeTemps.getValue() / 100 + " secondes");
		lblPasDeTemp.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasDeTemp.setForeground(new Color(50, 106, 73));
		lblPasDeTemp.setBounds(0, 676, 325, 16);
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

		boutonEmplacementBateaux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionBtnConfirmation();
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

						// Affichage de la vitesse formattée
						lblAfficheVitesseI.setText(vitesseFormatee + " m/s");

					}

				} else if (modeFlecheActif) {

					if (!enCoursDAnimation) {

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

		zoneBateauA.setAccelGrav(ACCELERATION_GRAVITATIONNELLE);
		zoneBateauB.setAccelGrav(ACCELERATION_GRAVITATIONNELLE);

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
					&& panSystemeTours.isTourJoueur2()) {
				// Pause de 1 sec

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Processus interrompu!");
				}

				zoneBateauB.demarrer();

				demarrer();
			}

			if (enCoursDAnimation) {

				btnTir.setVisible(false);
				btnTir.setFocusable(false);

				if (zoneBateauB.getCollision()) {
					arreter();
				}
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("Processus interrompu!");
			}
		}

	}
//		

	/**
	 * Methode qui arrete le thread
	 */
	// Gabriel Maurice
	public void arreter() {

		// Pause de 3 secondes:
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("Processus interrompu!");
		}

		supprimerCourbeGraph();
		panSystemeTours.modificationTours();
		InfoGuidageMissile.reinitialiserA0();
		zoneBateauA.resetMissile();
		zoneBateauB.resetMissile();
		enCoursDAnimation = false;
		btnTir.setVisible(true);
		btnTir.setFocusable(true);

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

		zoneBateauB.placeAleatoirement();
		zoneBateauB.cacheBateaux(true);
		panSystemeTours.setTourJoueur2();
		zoneBateauB.setEditeur(false);
		zoneBateauA.setEditeur(false);

		if (SystemeTours.placementBateau == 2 && zoneBateauB.bateauxPlace()) {
			zoneBateauB.setEditeur(false);
			panSystemeTours.setPartieCommencee(true, true);
			boutonEmplacementBateaux.setVisible(false);
			boutonEmplacementBateaux.setFocusable(false);
			panCommandesTir.setVisible(true);
			btnReplace.setVisible(false);
			btnPlaceAleatoir.setVisible(false);
			switchZoneBateau();
			panSystemeTours.repaint();
		} else if (!zoneBateauB.bateauxPlace() && SystemeTours.placementBateau >= 2) {
			SystemeTours.placementBateau--;
		}
		panSystemeTours.setPartieCommencee(true, true);
		switchZoneBateau();
		boutonEmplacementBateaux.setVisible(false);
		boutonEmplacementBateaux.setFocusable(false);

		panCommandesTir.setVisible(true);
		btnReplace.setVisible(false);
		btnPlaceAleatoir.setVisible(false);

		zoneBateauA.setEditeurHabiletes(true);

		panSystemeTours.repaint();

		// Affichage des bulles de dialogue
		bulleDialogueBateaux.setVisible(false);
		bulleDialogueTir.setVisible(true);
		bulleDialogueCanon.setVisible(true);
		bulleDialogueGraphique.setVisible(true);
		bulleDialogueCommandes.setVisible(true);

		contentPane.requestFocusInWindow();
		confirmationVisible();
	}

	/**
	 * Méthode qui change les zones de placement en fonction du tour du joueur.
	 */
	// Noé Julien
	public void switchZoneBateau() {

		zoneBateauB.setInverse(true);
		zoneBateauA.setInverse(false);

		zoneBateauA.setBounds(placementZoneX, placementZoneBasY, 965, 341);
		zoneBateauB.setBounds(placementZoneX, placementZoneHautY, 965, 341);

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
	 * Méthode qui permet de changer l'étiquette qui affiche le pas de temps et de
	 * renvoyer la nouveau pas de temp dans la classe ZoneBateau
	 */
	// Noé Julien
	private void changePasDeTemps() {
		lblPasDeTemp.setText("Pas de l'animation: " + (double) sliderPasDeTemps.getValue() / 100 + " secondes");
		ZoneBateau.setDeltaT((double) sliderPasDeTemps.getValue() / 100);
	}

	/**
	 * Méthode statique qui permet de lier la méthode calculerUnFrame du missile et
	 * la classe HauteurMissileGRaphPanel. Elle est appelée à chaque nouveau frame
	 * calculé par la classe missile. La méthode appelle la méthode nouvPoint de la
	 * classe du graphique permettant de créer un nouveau point.
	 * 
	 * @param hauteur la hauteur du missile en mètres
	 * @param temps   le temps écoulé.
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
	 * Méthode qui permet de redessiner le panneau des informations du guidage du
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
	 * Méthode qui permet de changer l'état d'ouverture de la fenêtre
	 * 
	 * @param estOuvert le nouvel état d'ouverture de la fenêtre.
	 */
	// Théo Fourteau
	public static void setEstOuvert(boolean estOuvert) {
		if (estOuvert) {
			System.out.println("La fenêtre Tutoriel est ouverte.");
		} else {
			System.out.println("La fenêtre Tutoriel est fermée.");
		}
		FenetreTutoriel.estOuvert = estOuvert;
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

			InfoGuidageMissile.setFacteurGravite(-ACCELERATION_GRAVITATIONNELLE);

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
}
