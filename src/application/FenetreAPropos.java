package application;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import aproposetaide.PanelAPropos;
/**
 *  Classe qui montre les createurs de l'application
 * @author Cedric Thai
 */
public class FenetreAPropos extends JFrame {
	
	/** Identifiant unique pour la sérialisation **/
	private static final long serialVersionUID = 1L;
	
	/** panneau contenant les composants de l'application **/
	private JPanel contentPane;
	
	/** instance de FenetreJouer pour retourner aux informations **/
	private FenetreInstructions retour;

	/**
	 * Lancement de la fenêtre d' A propos
	 * 
	 * @param args argument de création d'application
	 */
	// Cedric Thai
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreAPropos frame = new FenetreAPropos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création du panneau d'A propos
	 * 
	 */
	// Cedric Thai
	public FenetreAPropos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1081, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JButton btnRetour = new JButton("Retour aux instructions");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retour = new FenetreInstructions();
				retour.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.setLayout(null);
		btnRetour.setBounds(0, 0, 277, 23);
		contentPane.add(btnRetour);

		JLabel lblAPropos = new JLabel("À propos");
		lblAPropos.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblAPropos.setBounds(464, 11, 101, 76);
		contentPane.add(lblAPropos);
		
		PanelAPropos panelAPropos = new PanelAPropos();
		panelAPropos.setBounds(187, 71, 707, 397);
		contentPane.add(panelAPropos);

	}
}
