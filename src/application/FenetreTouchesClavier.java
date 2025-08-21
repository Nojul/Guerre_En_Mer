package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Fenêtre obligatoire pour les applications qui utilisent les touches du
 * clavier
 * 
 * @author Caroline Houle
 * @author Noé Julien
 */
public class FenetreTouchesClavier extends JFrame {
	/** Identifiant unique pour la sérialisation **/
	private static final long serialVersionUID = 1L;
	/** Panneau contenant les divers composants de la fenêtre **/
	private JPanel contentPane;
	/** Tableau contenant les commandes et leurs fonctionnalités. **/
	private JTable table;

	/**
	 * Lancement de la fenêtre
	 * 
	 * @param args argument de création d'application
	 */
	// Caroline Houle
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreTouchesClavier frame = new FenetreTouchesClavier();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création de la fenêtre
	 */
	// Noé Julien
	public FenetreTouchesClavier() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Enlève le focus de la fenêtre des touches et permet de bouger le canon après
		// avoir consulté la fenêtre des touches.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
			}
		});

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitre = new JLabel("Utilisastion des touches du clavier");
		lblTitre.setBounds(38, 26, 243, 17);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitre.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblTitre);

		table = new JTable();
		table.setBounds(6, 93, 388, 250);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//
		// si plus que 3 lignes, agrandir le composant sur la fenêtre!
		//

		table.setModel(new DefaultTableModel(new Object[][] { { "R", "Tourner le bateau sélectionné en mode éditeur." },
				{ "R", "Tourner le mur sélectionné en mode éditeur." }, { "G", "Redimensionner le mur sélectionné." },
				{ "G", "Redimensionner le champ magnétique sélectionné." }, { "W/↑", "Diminue l'angle vers le bas" },
				{ "A/ ←", "Tourne le canon vers la gauche" }, { "S/↓", "Augmente l'angle vers le haut" },
				{ "D/→", "Tourne le canon vers la droite" },
				{ "[espace]", "Augmente ou diminue la gauge de vitesse initiale" },
				{ "SourisRoue", "Changer les habiletés(Mode Test)" },
				
				{ null, null }, }, new String[] { "New column", "New column" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { String.class, String.class };

			/** **/
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setMaxWidth(70);
		contentPane.add(table);

		JLabel lblTitreCol1 = new JLabel("Touche");
		lblTitreCol1.setBounds(6, 67, 46, 14);
		lblTitreCol1.setForeground(Color.BLUE);
		lblTitreCol1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblTitreCol1);

		JLabel lblTitreCol2 = new JLabel("Effet");
		lblTitreCol2.setBounds(113, 67, 46, 14);
		lblTitreCol2.setForeground(Color.BLUE);
		lblTitreCol2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblTitreCol2);

	}
}
