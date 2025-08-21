package application;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import aproposetaide.PanelImagesAvecDefilement;
import outils.AjoutSon;

/**
 * Classe qui permet a l'utilisateur de savoir les instructions sur le le
 * fonctionnement du jeu
 * 
 * @author Cedric Thai
 * @author Théo Fourteau
 * @author Caroline Houle
 */
public class FenetreInstructions extends JFrame {

	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 1L;
	/** panneau contenant les composants de l'application **/
	private JPanel contentPane;
	/** instance de AppPrincipale33 pour retourner au menu principal **/
	private AppPrincipale33 retour;
	/** instance de FenetreAPropos pour acceder a la fenetre A propos **/
	private FenetreAPropos fenetreAPropos;
	/** Bouton qui permet de revenir à la page précédente des instructions **/
	private JButton btnPagePrecedente;
	/** Bouton qui permet d'aller à la page suivante des instructions **/
	private JButton btnPageSuivante;
	/** Tableau contenant les références des captures d'écran des instructions **/
	private String tableauImages[];

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
					FenetreInstructions frame = new FenetreInstructions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructeur
	 * 
	 */
	// Théo Fourteau
	public FenetreInstructions() {
		
		//Ajout des images des instructions dans le tableau des images 
		tableauImages = new String[22]; // 22 pages

		for (int i = 1; i < 23; i++) {
			tableauImages[i-1] = "Instructions Guerre en Mer.00"+i+".jpeg";
		}
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1200, 808);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// creation du composant qui contiendra les pages d'aide
		PanelImagesAvecDefilement panAide = new PanelImagesAvecDefilement();
		// Pour modifier la largeur et la couleur du cadre autour des pages
		panAide.setLargeurCadre(10);
		panAide.setFichiersImages(tableauImages); // on precise quelles images seront utilisees
		panAide.setBounds(100, 80, 1000, 565);
		contentPane.add(panAide);

		btnPagePrecedente = new JButton("Page pr\u00E9c\u00E9dente");
		btnPagePrecedente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPagePrecedente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPagePrecedente.setEnabled(panAide.precedente());
				btnPageSuivante.setEnabled(true);
			}
		});
		btnPagePrecedente.setBounds(100, 705, 165, 45);
		contentPane.add(btnPagePrecedente);

		btnPageSuivante = new JButton("Page suivante");
		btnPageSuivante.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPageSuivante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPageSuivante.setEnabled(panAide.suivante());
				btnPagePrecedente.setEnabled(true);
			}

		});

		if (tableauImages.length == 1) {
			btnPagePrecedente.setEnabled(false);
			btnPageSuivante.setEnabled(false);
		}
		btnPageSuivante.setBounds(935, 705, 165, 45);
		contentPane.add(btnPageSuivante);

		JLabel lblAideInstructions = new JLabel("Aide : instructions d'utilisation");
		lblAideInstructions.setHorizontalAlignment(SwingConstants.CENTER);
		lblAideInstructions.setForeground(Color.WHITE);
		lblAideInstructions.setFont(new Font("Graphik", Font.BOLD, 25));
		lblAideInstructions.setBounds(326, 11, 547, 34);
		contentPane.add(lblAideInstructions);

		JButton btnRetour = new JButton("Retour");
		btnRetour.setBounds(0, 0, 95, 23);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AjoutSon.setPrincipal(false);
				retour = new AppPrincipale33();
				retour.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnRetour);

		JButton btnAPropos = new JButton("À propos");
		btnAPropos.setBounds(0, 30, 117, 23);
		btnAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenetreAPropos = new FenetreAPropos();
				fenetreAPropos.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnAPropos);
	}
}
