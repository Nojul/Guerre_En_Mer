package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import geometrie.Missile;
import outils.BoutonCirculaire;
import outils.OutilsImage;
import outils.AjoutSon;
import outils.BoutonChangementSon;

/**
 * Classe permettant a l'utilisateur de modifier des parametres en lien avec le
 * jeu
 * 
 * @author Cedric Thai
 */
public class FenetreOptions extends JFrame {

	/** identifiant de classe **/
	private static final long serialVersionUID = 1L;

	/** panneau contenant les composants de l'application **/
	private JPanel contentPane;

	/** instance de AppPrincipale33 pour retourner au menu principal **/
	private AppPrincipale33 retour;

	/** instance de FenetreTerrainJeu pour acceder au terrain de jeu **/
	private FenetreTerrainJeu accesFenetreTerrainJeu;

	/** groupe de boutons **/
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/** bouton radio pour activer les touches Fleches **/
	private JRadioButton rdbtnFleches;

	/** bouton radio pour activer les touches WASD **/
	private JRadioButton rdbtnWASD;

	/** tourniquet pour changer l'acceleration de la missile **/
	private JSpinner spnAccel;

	/** variable boolean qui indique si les fleches ont ete activees **/
	private boolean flechesActives = true;

	/** variable boolean qui indique si le chronometre est affiche **/
	private boolean minuteurActif = false;

	/** liste deroulante qui permet de changer le terrain de jeu **/
	private JComboBox<String> cmbBoxTerrain;

	/** case a cocher qui permet d'afficher le minuteur **/
	private JCheckBox chckbxMinuteur;
	/** Case à cocher pour afficher ou non le vecteur vitesse sur le missile **/
	private JCheckBox chckFlecheVect;
	/**
	 * Booléen qui permet de confirmer si oui ou non, il faut afficher le vecteur de
	 * vitesse
	 **/
	private boolean affichFleche;
	/** Texte associé à la case à cocher **/
	private JLabel lblChoixFleches;

	/** Image de fond **/
	private Image imageFond;
	/** Police d'écriture des options **/
	private Font policeOption = new Font("Graphik", Font.BOLD, 20);
	/** Représente un JLabel pour le choix du son */
	private JLabel lblChoixSon;
	/** Le volume du son représenté par un booléen */
	private boolean volumeSon;
	/** Le bouton de changement de son associé */
	BoutonChangementSon boutonChangementSon = new BoutonChangementSon();

	/**
	 * Lancement de la fenêtre des options
	 * 
	 * @param args argument de création d'application
	 */
	// Cedric Thai
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreOptions frame = new FenetreOptions(false, false,false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création du panneau des options
	 * 
	 * @param fenetreTerrainJeu variable boolean qui determine si fenetreOptions est
	 *                          appele dans fenetreTerrainJeu
	 * @param partieCommencee   variable boolean qui determine si la partie est
	 *                          commencee
	 * @param iaActif variable boolean qui determine si on doit retourner a une partie contre ia
	 * 
	 * @throws ClassNotFoundException Cette exception indique qu'une classe
	 *                                spécifiée n'a pas pu être trouvée lors de
	 *                                l'exécution du programme
	 */
	// Cedric Thai
	public FenetreOptions(boolean fenetreTerrainJeu, boolean partieCommencee, boolean iaActif) throws ClassNotFoundException {
		setFont(new Font("Graphik", Font.BOLD, 18));
		setTitle("Options du jeu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		this.setResizable(false);
		imageFond = OutilsImage.lireImage("imageOptionsGuerreEnMer.png");
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
		setContentPane(contentPane);

		JButton btnReinitialiserOptions = new JButton("Réinitialiser les options");
		btnReinitialiserOptions.setFont(new Font("Graphik", Font.BOLD, 18));
		btnReinitialiserOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnFleches.setSelected(true);
				spnAccel.setModel(new SpinnerNumberModel(9.8, 1.6, 20, 0.1));
				cmbBoxTerrain
						.setModel(new DefaultComboBoxModel<String>(new String[] { "Terre", "Vesperion Prime", "Celestia-47B" }));
				chckbxMinuteur.setSelected(false);
				chckFlecheVect.setSelected(false);
				ecritureFichier();
			}
		});
		contentPane.setLayout(null);
		btnReinitialiserOptions.setBounds(750, 0, 250, 35);
		contentPane.add(btnReinitialiserOptions);

		if (fenetreTerrainJeu) {
			JButton btnReinitialiser = new JButton("Réinitialiser la partie");
			btnReinitialiser.setFont(new Font("Graphik", Font.BOLD, 18));
			btnReinitialiser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						accesFenetreTerrainJeu.setMatchContreIA(iaActif);
						accesFenetreTerrainJeu = new FenetreTerrainJeu("", false);
						FenetreTerrainJeu.setSonOcean(false);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					accesFenetreTerrainJeu.setVisible(true);
					setVisible(false);
				}
			});

			contentPane.setLayout(null);
			btnReinitialiser.setBounds(6, 540, 200, 35);
			contentPane.add(btnReinitialiser);

			if (partieCommencee) {
				JButton btnRetourPartie = new JButton("Retour au jeu");
				btnRetourPartie.setFont(new Font("Graphik", Font.BOLD, 18));
				btnRetourPartie.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							accesFenetreTerrainJeu = new FenetreTerrainJeu("temporaire", false);
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						accesFenetreTerrainJeu.setVisible(true);
						setVisible(false);
					}
				});
				contentPane.setLayout(null);
				btnRetourPartie.setBounds(210, 540, 175, 35);
				contentPane.add(btnRetourPartie);

			}

		} else {

			JButton btnRetour = new JButton("Retour");
			btnRetour.setFont(new Font("Graphik", Font.BOLD, 18));
			btnRetour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AjoutSon.setPrincipal(false);
					retour = new AppPrincipale33();
					retour.setVisible(true);
					setVisible(false);
				}
			});
			contentPane.setLayout(null);
			btnRetour.setBounds(6, 540, 104, 35);
			contentPane.add(btnRetour);

		}
		contentPane.setLayout(null);

		JLabel lblOptions = new JLabel("Options");
		lblOptions.setFont(new Font("Graphik", Font.BOLD, 35));
		lblOptions.setBounds(17, 6, 191, 48);
		contentPane.add(lblOptions);

		spnAccel = new JSpinner();
		spnAccel.setModel(new SpinnerNumberModel(9.8, 1.6, 20, 0.1));
		spnAccel.setFont(new Font("Graphik", Font.BOLD, 18));
		spnAccel.setBounds(633, 138, 246, 36);
		contentPane.add(spnAccel);

		cmbBoxTerrain = new JComboBox<String>();
		cmbBoxTerrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (cmbBoxTerrain.getSelectedItem().toString()) {
				case "Terre":
					spnAccel.setModel(new SpinnerNumberModel(9.8, 1.6, 20, 0.1));
					break;

				case "Vesperion Prime":
					spnAccel.setModel(new SpinnerNumberModel(13.6, 1.6, 20, 0.1));
					break;

				case "Celestia-47B":
					spnAccel.setModel(new SpinnerNumberModel(7.1, 1.6, 20, 0.1));
					break;
				} // fin switch
			}
		});
		cmbBoxTerrain.setFont(new Font("Graphik", Font.BOLD, 18));
		cmbBoxTerrain.setModel(new DefaultComboBoxModel<String>(new String[] { "Terre", "Vesperion Prime", "Celestia-47B" }));

		cmbBoxTerrain.setBounds(633, 53, 214, 36);
		contentPane.add(cmbBoxTerrain);

		lectureFichierOptions();

		rdbtnFleches = new JRadioButton("");
		rdbtnFleches.setSelected(flechesActives);
		buttonGroup.add(rdbtnFleches);
		rdbtnFleches.setBounds(633, 315, 28, 23);
		contentPane.add(rdbtnFleches);

		rdbtnWASD = new JRadioButton("");
		rdbtnWASD.setSelected(!flechesActives);
		buttonGroup.add(rdbtnWASD);
		rdbtnWASD.setBounds(736, 315, 28, 23);
		contentPane.add(rdbtnWASD);

		JLabel lblFleches = new JLabel("Flèches");
		lblFleches.setHorizontalAlignment(SwingConstants.CENTER);
		lblFleches.setFont(new Font("Graphik", Font.BOLD, 18));
		lblFleches.setBounds(606, 289, 85, 25);
		contentPane.add(lblFleches);

		JLabel lblWASD = new JLabel("WASD");
		lblWASD.setHorizontalAlignment(SwingConstants.CENTER);
		lblWASD.setFont(new Font("Graphik", Font.BOLD, 18));
		lblWASD.setBounds(722, 289, 56, 25);
		contentPane.add(lblWASD);

		JLabel lblSauvegardeSucces = new JLabel("");
		lblSauvegardeSucces.setForeground(new Color(0, 0, 255));
		lblSauvegardeSucces.setHorizontalAlignment(SwingConstants.CENTER);
		lblSauvegardeSucces.setFont(new Font("Graphik", Font.BOLD, 18));
		lblSauvegardeSucces.setBounds(223, 507, 554, 50);
		contentPane.add(lblSauvegardeSucces);

		JButton btnSauvegarder = new BoutonCirculaire("Sauvegarder");
		btnSauvegarder.setFont(new Font("Graphik", Font.BOLD, 18));
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ecritureFichier();
				lblSauvegardeSucces.setText("Sauvegardé avec succès");

				System.out.println("sauvegardé");

			}
		});
		btnSauvegarder.setBounds(834, 420, 160, 127);
		contentPane.add(btnSauvegarder);

		JLabel lblAccel = new JLabel("Accélération gravitationnelle:");
		lblAccel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAccel.setFont(policeOption);
		lblAccel.setBounds(246, 143, 349, 27);
		contentPane.add(lblAccel);

		chckFlecheVect = new JCheckBox("");
		chckFlecheVect.setSelected(affichFleche);
		Missile.setAffichFleche(chckFlecheVect.isSelected());
		chckFlecheVect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Missile.setAffichFleche(chckFlecheVect.isSelected());
				if (chckFlecheVect.isSelected()) {
					affichFleche = true;
				} else {
					affichFleche = false;
				}
			}
		});
		chckFlecheVect.setBounds(633, 400, 28, 23);
		contentPane.add(chckFlecheVect);

		JLabel lblTerrain = new JLabel("Terrain de jeu:");
		lblTerrain.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTerrain.setFont(policeOption);
		lblTerrain.setBounds(299, 58, 296, 27);
		contentPane.add(lblTerrain);

		JLabel lblMinuteur = new JLabel("Affichage du minuteur:");
		lblMinuteur.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMinuteur.setFont(policeOption);
		lblMinuteur.setBounds(333, 228, 262, 27);
		contentPane.add(lblMinuteur);

		chckbxMinuteur = new JCheckBox("");
		chckbxMinuteur.setSelected(minuteurActif);
		chckbxMinuteur.setBounds(686, 230, 28, 23);
		contentPane.add(chckbxMinuteur);

		lblChoixFleches = new JLabel("Choix des commandes:");
		lblChoixFleches.setHorizontalAlignment(SwingConstants.TRAILING);
		lblChoixFleches.setFont(policeOption);
		lblChoixFleches.setBounds(333, 313, 262, 27);
		contentPane.add(lblChoixFleches);

		JLabel lblChoixFlecheVecteur = new JLabel("Affichage du vecteur vitesse:");
		lblChoixFlecheVecteur.setHorizontalAlignment(SwingConstants.TRAILING);
		lblChoixFlecheVecteur.setFont(policeOption);
		lblChoixFlecheVecteur.setBounds(272, 398, 323, 27);
		contentPane.add(lblChoixFlecheVecteur);

		boutonChangementSon.setBounds(633, 446, 100, 100);
		boutonChangementSon.setSelected(volumeSon);
		contentPane.add(boutonChangementSon);

		lblChoixSon = new JLabel("État du son:");
		lblChoixSon.setHorizontalAlignment(SwingConstants.TRAILING);
		lblChoixSon.setFont(new Font("Graphik", Font.BOLD, 20));
		lblChoixSon.setBounds(307, 483, 288, 27);
		contentPane.add(lblChoixSon);

	}

	/**
	 * Cette methode ecrit un fichier en prenant compte les options choisies par
	 * l'utilisateur
	 */
	// Cedric Thai
	private void ecritureFichier() {
		String direction = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
		direction += File.separator + "Options";

		File testDirection = new File(direction);

		if (testDirection.exists()) {
		} else if (testDirection.mkdirs()) {
		} else {
		}

		File fichierDeTravail = new File(direction, "modifie.txt");
		ObjectOutputStream fluxSortie = null;

		try {
			fluxSortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichierDeTravail)));
			if (rdbtnFleches.isSelected()) {
				fluxSortie.writeBoolean(true);
			} else {
				fluxSortie.writeBoolean(false);
			}
			fluxSortie.writeDouble(Double.parseDouble(spnAccel.getValue().toString()));
			switch (cmbBoxTerrain.getSelectedItem().toString()) {
			case "Terre":
				fluxSortie.writeObject("Terre.jpg");
				break;

			case "Vesperion Prime":
				fluxSortie.writeObject("Vesperion Prime.jpg");
				break;

			case "Celestia-47B":
				fluxSortie.writeObject("Celestia-47B.jpg");
				break;
			} // fin switch
			if (chckbxMinuteur.isSelected()) {
				fluxSortie.writeBoolean(true);
			} else {
				fluxSortie.writeBoolean(false);
			}

			if (chckFlecheVect.isSelected()) {
				fluxSortie.writeBoolean(true);
			} else {
				fluxSortie.writeBoolean(false);
			}

			if (boutonChangementSon.isSelected()) {
				fluxSortie.writeBoolean(true);
			} else {
				fluxSortie.writeBoolean(false);

			}

		} catch (IOException e) {
			System.out.println("Erreur lors de l'ecriture");
			e.printStackTrace();
		} finally {
			try {
				fluxSortie.close();
			} catch (IOException e) {
				System.out.println("Erreur lors de la fermeture");
			}
		} // fin finally
	} // fin methode

	/**
	 * Cette methode permet de lire le fichier venant des options et d'ajuster les
	 * valeurs de la FenetreOptions
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

			try {
				flechesActives = fluxEntree.readBoolean();
				spnAccel.setModel(new SpinnerNumberModel(fluxEntree.readDouble(),1.6, 20, 0.1));
				switch (fluxEntree.readObject().toString()) {
				case "Terre.jpg":
					cmbBoxTerrain.setModel(
							new DefaultComboBoxModel<String>(new String[] { "Terre", "Vesperion Prime", "Celestia-47B"}));
					break;

				case "Vesperion Prime.jpg":
					cmbBoxTerrain.setModel(
							new DefaultComboBoxModel<String>(new String[] { "Vesperion Prime", "Terre", "Celestia-47B"}));
					break;

				case "Celestia-47B.jpg":
					cmbBoxTerrain.setModel(
							new DefaultComboBoxModel<String>(new String[] { "Celestia-47B", "Terre", "Vesperion Prime"}));
					break;
				} // fin switch
				minuteurActif = fluxEntree.readBoolean();

				affichFleche = fluxEntree.readBoolean();

				volumeSon = fluxEntree.readBoolean();
			} catch (FileNotFoundException e) {
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Erreur rencontree lors de la lecture");
				e.printStackTrace();
				System.exit(0);
			} finally {
				try {
					fluxEntree.close();
				} catch (IOException e) {
					System.out.println("Erreur rencontree lors de la fermeture!");
				}

			}
		}
	} // fin methode

}
