package application;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import outils.OutilsImage;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * Classe qui indique le gagnant de la partie
 * 
 * @author Cedric Thai
 */
public class FenetreVictoire extends JFrame {
	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;
	/** panneau contenant les composants de l'application **/
	private JPanel contentPane;
	/** Valeur entier qui indique le numero du joueur gagnant **/
	private int joueurGagnant;
	/** instance de AppPrincipale33 **/
	private AppPrincipale33 retour;
	/** instance de FenetreTerrainJeu **/
	private FenetreTerrainJeu fenetreTerrainJeu;
	/** Image de fond **/
	private Image imageFond;
	/**
	 * Fenêtre affichée lorsqu'un joueur gagne dans le jeu
	 * 
	 * @param args les arguments de la ligne de commande
	 */
	// Cedric Thai
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreVictoire frame = new FenetreVictoire(0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Méthode pour afficher la victoire au joueur
	 * 
	 * @param joueurGagnant le joueur gagnant
	 */
	// Cedric Thai
	public FenetreVictoire(int joueurGagnant) {
		setTitle("Victoire ! Fin de la partie");
		imageFond = OutilsImage.lireImage("ImageFondVictoire.png");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		
		this.joueurGagnant = joueurGagnant;
		
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
		
		// Centre la fenêtre au milieu de l'écran
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblVictoire = new JLabel("Victoire pour le joueur " + this.joueurGagnant);
		lblVictoire.setHorizontalAlignment(SwingConstants.CENTER);
		lblVictoire.setFont(new Font("Graphik", Font.BOLD, 25));
		lblVictoire.setBounds(181, 119, 388, 34);
		contentPane.add(lblVictoire);

		JButton btnRetour = new JButton("Retour au menu principal");
		btnRetour.setFont(new Font("Graphik", Font.BOLD, 16));
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retour = new AppPrincipale33();
				retour.setVisible(true);
				setVisible(false);
			}
		});
		btnRetour.setBounds(250, 366, 250, 50);
		contentPane.add(btnRetour);

		JButton btnPartie1 = new JButton("Nouvelle partie: 1 joueur");
		btnPartie1.setFont(new Font("Graphik", Font.BOLD, 16));
		btnPartie1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FenetreTerrainJeu.setMatchContreIA(true);
				try {
					fenetreTerrainJeu = new FenetreTerrainJeu("",false);
					fenetreTerrainJeu.setVisible(true);
					setVisible(false);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPartie1.setBounds(250, 177, 250, 50);
		contentPane.add(btnPartie1);

		JButton btnPartie2 = new JButton("Nouvelle partie: 2 joueurs");
		btnPartie2.setFont(new Font("Graphik", Font.BOLD, 16));
		btnPartie2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FenetreTerrainJeu.setMatchContreIA(false);
				try {
					fenetreTerrainJeu = new FenetreTerrainJeu("",false);
					fenetreTerrainJeu.setVisible(true);
					setVisible(false);
				} catch (ClassNotFoundException e1) {

					e1.printStackTrace();
				}
			}
		});
		btnPartie2.setBounds(250, 239, 250, 50);
		contentPane.add(btnPartie2);

	}
}
