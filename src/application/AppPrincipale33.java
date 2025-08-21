package application;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import outils.AjoutSon;
import outils.BoutonCirculaire;
import outils.OutilsImage;

/**
 * Cette classe est la fenetre principale du jeu ou l'utilisateur peut naviguer
 * a travers l'application
 * 
 * @author Cedric Thai
 * @author Théo Fourteau
 * @author Noé Julien
 */

public class AppPrincipale33 extends JFrame {
	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;

	/** panneau contenant les composants de l'application **/
	private JPanel contentPane;

	/** instance de FenetreJouer pour jouer **/
	private FenetreJouer fenetreJouer;

	/** instance de FenetreTutoriel pour acceder au tutoriel **/
	private FenetreTutoriel fenetreTutoriel;

	/** instance de FenetreJouer pour acceder aux options **/
	private FenetreOptions fenetreOptions;

	/** instance de FenetreJouer pour acceder aux informations **/
	private FenetreInstructions fenetreInstructions;

	/** Image de fond **/
	private Image imageDepart;

	/** Couleur des boutons du bas **/
	private Color couleurBoutons = new Color(122, 142, 155);
	/** Police d'écriture **/
	private Font policeBoutons = new Font("Graphik", Font.BOLD, 30);

	/**
	 * Lancement de la fenêtre de l'application principale
	 * 
	 * @param args argument de création d'application
	 */
	// Cedric Thai
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppPrincipale33 frame = new AppPrincipale33();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création du panneau de l'application principale
	 * 
	 */
	// Théo Fourteau
	public AppPrincipale33() {
		if (!AjoutSon.principaleJoue()) {
			AjoutSon.setPrincipal(true);
			AjoutSon.sonPrincipal();
		}

		imageDepart = OutilsImage.lireImage("imageDepartGuerreEnMer.png");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 100, 1500, 850);
		this.setResizable(false);
		contentPane = new JPanel() {

			private static final long serialVersionUID = 4646591221974773456L;

			protected void paintComponent(Graphics g) {
				super.paintComponents(g);
				if (imageDepart != null) {
					// Dessiner l'image en utilisant sa largeur et sa hauteur
					g.drawImage(imageDepart, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};

		setContentPane(contentPane);
		contentPane.setLayout(null);

//		try {
////			InputStream fontStream = getClass().getResourceAsStream("/33_GuerreEnMer/ressources/American Captain.ttf");
//			InputStream fontStream = getClass().getResourceAsStream("/resources/American Captain.ttf");
//					
//			if (fontStream == null) {
//				throw new IOException("Font file not found");
//			}
//			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(40f);
//			btnJouer.setFont(font);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		JButton btnJouer = new JButton("Jouer");
		btnJouer.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnJouer.setFont(policeBoutons);
		btnJouer.setBorder(new LineBorder(new Color(226, 189, 121), 3));
		btnJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String direction = System.getProperty("user.home") + "/Desktop" + "/33_GuerreEnMer";
				File testDirection = new File(direction);
				if (testDirection.exists()) {
				} else if (testDirection.mkdirs()) {

				}
				fenetreJouer = new FenetreJouer();
				fenetreJouer.setVisible(true);
				setVisible(false);

			}
		});
		btnJouer.setBackground(couleurBoutons);
		btnJouer.setOpaque(true);

		btnJouer.setBorderPainted(true);
		btnJouer.setBounds(150, 681, 300, 100);
		contentPane.add(btnJouer);

		JButton btnTutoriel = new JButton("Tutoriel");
		btnTutoriel.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnTutoriel.setFont(policeBoutons);
		btnTutoriel.setBorder(new LineBorder(new Color(226, 189, 121), 3));
		btnTutoriel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arretMusique();
				fenetreTutoriel = new FenetreTutoriel();
				FenetreTutoriel.setEstOuvert(true);
				FenetreTerrainJeu.setEstOuvert(false);
			
				fenetreTutoriel.setVisible(true);
				setVisible(false);
			}
		});
		btnTutoriel.setBounds(600, 681, 300, 100);
		btnTutoriel.setBackground(couleurBoutons);
		btnTutoriel.setOpaque(true);
		btnTutoriel.setBorderPainted(true);
		contentPane.add(btnTutoriel);

		JButton btnOptions = new BoutonCirculaire("Options");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fenetreOptions = new FenetreOptions(false, false,false);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				fenetreOptions.setVisible(true);
				setVisible(false);
			}
		});
		btnOptions.setBounds(1251, 26, 128, 115);
		contentPane.add(btnOptions);
		JButton btnInformations = new JButton("Instructions");
		btnInformations.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnInformations.setFont(policeBoutons);
		btnInformations.setBorder(new LineBorder(new Color(226, 189, 121), 3));
		btnInformations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenetreInstructions = new FenetreInstructions();
				fenetreInstructions.setVisible(true);
				setVisible(false);
			}
		});
		btnInformations.setBackground(couleurBoutons);
		btnInformations.setOpaque(true);
		btnInformations.setBorderPainted(true);
		btnInformations.setBounds(1050, 681, 300, 100);
		contentPane.add(btnInformations);
	}

	/**
	 * Méthode qui arrête la musique de jouer
	 */
	// Noé Julien
	public void arretMusique() {
		AjoutSon.stopPrincipal();
	}
}
