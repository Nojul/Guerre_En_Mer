package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import outils.AjoutSon;
import outils.OutilsImage;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

/**
 * Cette classe est la fenetre du jeu où l'utilisateur peut charger une partie
 * ou en commencer une nouvelle
 * 
 * @author Cedric Thai
 * @author Noe Julien
 */
public class FenetreJouer extends JFrame {

	/** identifiant de classe **/
	private static final long serialVersionUID = 1L;
	/** panneau contenant les composants de l'application **/
	private JPanel contentPane;
	/** instance de AppPrincipale33 pour retourner au menu principal **/
	private AppPrincipale33 retour;
	/** instance de fenetreTerrainJeu pour pouvoir acceder au terrain de jeu **/
	private FenetreTerrainJeu fenetreTerrainJeu;
	/** Image de fond **/
	private Image imageFond;
	/** Police d'écriture des options **/
	private Font policeBoutons = new Font("Graphik", Font.BOLD, 30);
	/** Couleur des boutons du bas **/
	private Color couleurBoutons = new Color(122, 142, 155);

	/**
	 * Lancement de la fenêtre pour jouer
	 * 
	 * @param args argument de création d'application
	 */
	// Cedric Thai
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreJouer frame = new FenetreJouer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création du panneau pour jouer
	 * 
	 */
	// Cedric Thai
	public FenetreJouer() {
		imageFond = OutilsImage.lireImage("imageFenetreJouerGuerreEnMer.png");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 100, 1500, 850);
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
		contentPane.setLayout(null);

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
		btnRetour.setBounds(6, 6, 104, 35);
		contentPane.add(btnRetour);

		JButton btnCharger = new JButton("Charger une partie");
		btnCharger.setFont(policeBoutons);
		btnCharger.setBackground(couleurBoutons);
		btnCharger.setBorder(new LineBorder(couleurBoutons));
		btnCharger.setOpaque(true);
		btnCharger.setBorderPainted(true);
		btnCharger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nomFichier = "";

				String userDirection = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer" + "/Sauvegarde";

				File testDirection = new File(userDirection);
				if (testDirection.exists()) {
				} else if (testDirection.mkdirs()) {
				} else {
				}

				JFileChooser fileChooser = new JFileChooser(userDirection);

				int choix = fileChooser.showOpenDialog(null);

				if (choix == JFileChooser.APPROVE_OPTION) {
					File fichierChoisi = fileChooser.getSelectedFile();
					nomFichier = fichierChoisi.getName();
					try {
						arretMusique();
						FenetreTerrainJeu.setSonOcean(true);
						fenetreTerrainJeu = new FenetreTerrainJeu(nomFichier, false);
						FenetreTerrainJeu.setEstOuvert(true);
						fenetreTerrainJeu.setVisible(true);
						setVisible(false);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Problème de lecture de sauvegarde");

				}

			}

		});
		btnCharger.setBounds(400, 93, 700, 150);
		contentPane.add(btnCharger);

		JButton btnNouvelle = new JButton("Nouvelle partie: 1 joueur");
		btnNouvelle.setFont(policeBoutons);
		btnNouvelle.setBackground(couleurBoutons);
		btnNouvelle.setBorder(new LineBorder(couleurBoutons));
		btnNouvelle.setOpaque(true);
		btnNouvelle.setBorderPainted(true);

		btnNouvelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arretMusique();
				FenetreTerrainJeu.setSonOcean(true);
				try {
					FenetreTerrainJeu.setMatchContreIA(true);
					fenetreTerrainJeu = new FenetreTerrainJeu("", false);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				FenetreTerrainJeu.setEstOuvert(true);
				fenetreTerrainJeu.setVisible(true);
				setVisible(false);
			}
		});
		btnNouvelle.setBounds(400, 336, 700, 150);
		contentPane.add(btnNouvelle);

		JButton btnNouvelle_2 = new JButton("Nouvelle partie: 2 joueurs");
		btnNouvelle_2.setFont(policeBoutons);
		btnNouvelle_2.setBackground(couleurBoutons);
		btnNouvelle_2.setBorder(new LineBorder(couleurBoutons));
		btnNouvelle_2.setOpaque(true);
		btnNouvelle_2.setBorderPainted(true);
		btnNouvelle_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arretMusique();
				FenetreTerrainJeu.setSonOcean(true);
				try {
					FenetreTerrainJeu.setMatchContreIA(false);
					fenetreTerrainJeu = new FenetreTerrainJeu("", false);

				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				FenetreTerrainJeu.setEstOuvert(true);
				fenetreTerrainJeu.setVisible(true);
				setVisible(false);
			}
		});

		btnNouvelle_2.setBounds(400, 579, 700, 150);
		contentPane.add(btnNouvelle_2);

		JButton btnTest = new JButton("Mode testeur");
		btnTest.setFont(policeBoutons);
		btnTest.setBackground(couleurBoutons);
		btnTest.setBorder(new LineBorder(couleurBoutons));
		btnTest.setOpaque(true);
		btnTest.setBorderPainted(true);

		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arretMusique();
				FenetreTerrainJeu.setSonOcean(true);
				JOptionPane.showMessageDialog(null,
						"Dans le mode testeur, les habiletés sont infinies et ne disparaissent pas. "
								+ "\nPour les obtenir,utilisez la roulette de votre souris. "
								+ "\nAussi, la victoire est donnée lorsque deux bateaux ont été coulés afin de faciliter les tests. ");
				try {
					FenetreTerrainJeu.setMatchContreIA(false);
					fenetreTerrainJeu = new FenetreTerrainJeu("", true);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				FenetreTerrainJeu.setEstOuvert(true);
				fenetreTerrainJeu.setVisible(true);
				setVisible(false);
			}
		});
		btnTest.setBounds(0, 700, 250, 150);
		contentPane.add(btnTest);

		ecritureFichierInitial();

	}

	/**
	 * Cette methode ecrit un fichier avec les donnees initiales
	 */
	// Cedric Thai
	private void ecritureFichierInitial() {
		String direction = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
		direction += File.separator + "Initial";

		File testDirection = new File(direction);

		if (testDirection.exists()) {
		} else if (testDirection.mkdirs()) {
		} else {
		}

		File fichierDeTravail = new File(direction, "depart.txt");
		ObjectOutputStream fluxSortie = null;

		try {
			fluxSortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichierDeTravail)));
			fluxSortie.writeBoolean(true);
			fluxSortie.writeDouble(9.8);
			fluxSortie.writeObject("Terre.jpg");
			fluxSortie.writeBoolean(false);
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
	 * Méthode qui arrête la musique de jouer
	 */
	// Noé Julien
	public void arretMusique() {
		AjoutSon.stopPrincipal();
	}
}
