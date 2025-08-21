package partie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import application.FenetreTerrainJeu;
import composantsDessin.ZoneBateau;

/**
 * Classe qui permet de compter le nombre de tours et d'afficher à qui est le
 * tour dans la fenêtre Terrain de jeu. La classe fait aussi évoluer un minuteur
 * qui régule le temps de la partie.
 * 
 * @author Théo Fourteau
 * @author Cedric Thai
 */
public class SystemeTours extends JPanel {
	/** Identifiant unique pour la sérialisation */
	private static final long serialVersionUID = 4987579268843692186L;

	// Constantes
	/**
	 * Temps maximal en secondes pendant lequel les joueurs peuvent jouer. Passé ce
	 * temps, le tour va à l'autre joueur.
	 */
	private final int TEMPS_MAX_TOUR = 30;
	/** Nombre de tours joués dans la partie */
	private int nbTours = 1;
	/** Premier joueur */
	private Joueur joueur1;
	/** Deuxième joueur */
	private Joueur joueur2;

	/** booléen qui définie si c'est au joueur 1 de jouer */
	private boolean tourJoueur1 = true;
	/** booléen qui définie si c'est au joueur 2 de jouer */
	private boolean tourJoueur2 = false;

	/**
	 * Minuteur qui permet d'imposer un temps maximal de 2 min par tour et par
	 * joueur
	 */
	private Timer timer;

	/** Valeur entière qui définie le temps restant au minuteur en secondes */
	private int tempsRestant = TEMPS_MAX_TOUR;

	/** Variable de texte qui affiche sur le composant à qui est le tour */
	private String tourDuJoueur = "";

	/**
	 * Variable booléenne qui permet de définir si oui ou non il s'agit du premier
	 * tour de la partie. Initalisé à vrai.
	 */
	private boolean premierTour = true;

	/**
	 * Booléen qui confirme si oui ou non la partie est commencée: c'est à dire si
	 * les deux joueurs ont placés leurs bateaux Initialisé à faux car les joueurs
	 * doivent placer leurs bateaux
	 */
	private boolean partieCommencee = false;

	/** Variable booléenne qui détermine si le minuteur est affiché */
	private boolean afficherMinuteur = true;

	/**
	 * Variable static qui une fois incrémentée à 2 dans la fenêtre Terrain de jeu
	 * débute la partie.
	 */
	public static int placementBateau = 0;
	

	/**
	 * Constructeur de la classe
	 * 
	 * @param afficherMinuteur boolean determinant si le muniteur serait affiché
	 * 
	 * @param zoneBateauA la zone de bateaux du joueur 1
	 * 
	 * @param zoneBateauB la zone de bateaux du joueur 2
	 */
	// Théo Fourteau
	public SystemeTours(boolean afficherMinuteur,ZoneBateau zoneBateauA,ZoneBateau zoneBateauB) {
		joueur1 = new Joueur();
		joueur2 = new Joueur();
		
		this.setBackground(new Color(74, 123, 157));
		
		if (afficherMinuteur) {
			minuteur( zoneBateauA, zoneBateauB);
		}

	}// Fin constructeur

	/**
	 * Méthode qui crée le minuteur et l'actualise en fonction des tours des deux
	 * joueurs. Quand le minuteur se termine, le nombre de tours augmente.
	 * 
	 * @param zoneBateauA la zone de bateaux du joueur 1
	 * 
	 * @param zoneBateauB la zone de bateaux du joueur 2
	 */
	// Théo Fourteau
	public void minuteur(ZoneBateau zoneBateauA,ZoneBateau zoneBateauB) {
		this.timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (partieCommencee && !FenetreTerrainJeu.isModeTesteur()) {

					tempsRestant--;
					repaint();

					if (tempsRestant <= 0) {
						timer.stop();

						if (isTourJoueur1()) {
							timer.stop();
							zoneBateauB.transitionEntreTours(true,2);
							zoneBateauA.transitionEntreTours(false,2);
							joueur1.setNbToursJoues(joueur1.getNbToursJoues() + 1);
							setTourJoueur2();
						} else if (isTourJoueur2()) {
							timer.stop();
							zoneBateauA.transitionEntreTours(true,1);
							zoneBateauB.transitionEntreTours(false,1);
							joueur2.setNbToursJoues(joueur2.getNbToursJoues() + 1);
							setTourJoueur1();
						}

						if (joueur1.getNbToursJoues() == joueur2.getNbToursJoues()) {
							nbTours++;
						}

						// Réinitialiser le minuteur pour le prochain tour
						reinitialisationTimer();
					}
				}
			}
		});
		timer.start();
	}

	/**
	 * Méthode qui permet de réinitialiser le temps du minuteur au temps maximal.
	 */
	// Théo Fourteau
	public void reinitialisationTimer() {
		tempsRestant = TEMPS_MAX_TOUR;
		timer.restart();

	}

	/**
	 * Permet de « dessiner » le chronomètre et afficher le tour auquel les joueurs
	 * sont rendus.
	 * 
	 * @param g le contexte graphqique
	 */
	// Théo Fourteau
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2dPriv = (Graphics2D) g.create(); // Copie du composant graphique.
		g2dPriv.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2dPriv.setColor(Color.BLACK);

		if (FenetreTerrainJeu.estOuvert()) {
			if (tourJoueur1) {
				tourDuJoueur = "Tour du joueur 1";
			} else {
				tourDuJoueur = "Tour du joueur 2";
			}
		}

		g2dPriv.drawString(tourDuJoueur, 20, 20);

		if (partieCommencee) {
			// Dessiner le minuteur (temps restant)
			int minutes = tempsRestant / 60;
			int secondes = tempsRestant % 60;
			String tempsRestantStr = String.format("%02d:%02d", minutes, secondes);

			if (afficherMinuteur) {
				if(!FenetreTerrainJeu.isModeTesteur()) {
					g2dPriv.drawString("Temps restant: " + tempsRestantStr, 20, 40);
				} else {
					g2dPriv.drawString("Temps restant: ∞ ", 20, 40);
				}
			}
			if (!FenetreTerrainJeu.estOuvert()) {
				g2dPriv.drawString("Tutoriel", 20, 40);
			}
			g2dPriv.drawString("Tour " + nbTours, 20, 60);
		} else {
			g2dPriv.drawString("Choix du lieu", 20, 40);
			g2dPriv.drawString("des bateaux", 20, 60);
		}

	}

	/**
	 * Méthode qui permet d'obtenir le nombre de tours qui ont été joués dans la
	 * partie
	 * 
	 * @return nbTours le nombre de tous joués au cours de la partie.
	 */
	// Théo Fourteau
	public int getNbTours() {
		return nbTours;
	}

	/**
	 * Méthode qui permet de modifier le nombre de tours qui ont été joués dans la
	 * partie
	 * 
	 * @param nouvNbTours le nouveau nombre de tours joués.
	 */
	// Théo Fourteau
	public void setNbTours(int nouvNbTours) {
		this.nbTours = nouvNbTours;
		repaint();
	}

	/**
	 * Méthode qui modifie le nombre de tours joués par chacun des joueurs et le
	 * nombre total de tours. La méthode repaint aussi le composant pour actualiser
	 * les tours sur la fenêtre de jeu.
	 */
	// Théo Fourteau
	public void modificationTours() {

		if (FenetreTerrainJeu.estOuvert()) {

			if (afficherMinuteur) {
				reinitialisationTimer(); // Réinitialisation du minuteur
			}

			if (tourJoueur1) {
				joueur1.setNbToursJoues(joueur1.getNbToursJoues() + 1);
				setTourJoueur2();
			} else if (tourJoueur2) {
				joueur2.setNbToursJoues(joueur2.getNbToursJoues() + 1);
				setTourJoueur1();

			}

			if (joueur1.getNbToursJoues() == joueur2.getNbToursJoues()) {
				nbTours++;
			}

		} else {
			nbTours++;
		}

		this.repaint();
	}

	/**
	 * Méthode qui permet de retourner si oui ou non il s'agit du premier tour de la
	 * partie
	 * 
	 * @return premierTour booléen qui confirme si oui ou non il s'agit du premier
	 *         tour
	 */
	// Théo Fourteau
	public boolean isPremierTour() {
		return premierTour;
	}

	/**
	 * Méthode qui permet de terminer le premier tour
	 */
	// Théo Fourteau
	public void setPremierTour() {
		premierTour = false;
	}

	/**
	 * Méthode qui retourne si oui ou non c'est au premier joueur de jouer
	 * 
	 * @return tourJoueur1 le booleen qui confirme si oui ou non c'est au premier
	 *         joueur de jouer
	 */
	// Théo Fourteau
	public boolean isTourJoueur1() {
		return tourJoueur1;
	}

	/**
	 * Méthode qui permet de donner le tour au joueur 1
	 */
	// Théo Fourteau
	public void setTourJoueur1() {
		tourJoueur1 = true;
		tourJoueur2 = false;
	}

	/**
	 * Méthode qui retourne si oui ou non c'est au deuxième joueur de jouer.
	 * 
	 * @return tourJoueur2 le booléen qui confirme si oui ou non c'est au deuxième
	 *         joueur de jouer.
	 */
	// Théo Fourteau
	public boolean isTourJoueur2() {
		return tourJoueur2;
	}

	/**
	 * Méthode qui permet de donner le tour au joueur 2
	 */
	// Théo Fourteau
	public void setTourJoueur2() {
		tourJoueur2 = true;
		tourJoueur1 = false;
	}

	/**
	 * Méthode qui permet de retourner si oui ou non la partie est commencée
	 * 
	 * @return partieCommencee qui définit si la partie a été commencée
	 */
	// Théo Fourteau
	public boolean getPartieCommencee() {
		return partieCommencee;
	}

	/**
	 * Méthode qui permet de définir si oui ou non la partie a été commencée
	 * 
	 * @param partieCommencee la nouvelle situation de la partie
	 * 
	 * @param partieChargee   la partie va commencer avec le joueur correspondant
	 *                        (TRUE=Joueur1/FALSE=Joueur2)
	 */
	// Théo Fourteau
	public void setPartieCommencee(boolean partieCommencee, boolean partieChargee) {
		if (partieChargee) {
			this.setTourJoueur1();
		} else {
			this.setTourJoueur2();
		}
		this.partieCommencee = partieCommencee;
		repaint();
	}

	/**
	 * Methode qui permet d'enlever le minuteur
	 * 
	 * @param afficherMinuteur boolean determinant si le muniteur serait affiché
	 */
	// Cedric Thai
	public void setMinuteur(boolean afficherMinuteur) {
		this.afficherMinuteur = afficherMinuteur;
		repaint();
	} // fin methode

	/**
	 * Cette methode permet d'arreter le minuteur
	 */
	// Cedric Thai
	public void arreterTemps() {
		timer.stop();
	}

	/**
	 * Cette methode permet de demarrer le minuteur
	 */
	// Cedric Thai
	public void demarrerTemps() {
		timer.start();
	}

	/**
	 * Cette methode permet d'obtenir le temps restant
	 * 
	 * @return le temps restant
	 */
	// Cedric Thai
	public int getTempsRestant() {
		return this.tempsRestant;
	}

	/**
	 * Cette methode permet de modifier le temps restant
	 * 
	 * @param tempsRestant le temps restant de la partie
	 */
	// Cedric Thai
	public void setTempsRestant(int tempsRestant) {
		this.tempsRestant = tempsRestant;
		repaint();
	}
	/**
	 * Methode qui retourne le nombre de tours du joueur 1
	 * @return le nombre de tours du joueur 1
	 */
	// Cedric Thai
	public int getNbToursJoueur1() {
		return joueur1.getNbToursJoues();
	}
	/**
	 * Methode qui modifie le nombre de tours du joueur 1
	 * @param nbTours le nombre de tours du joueur 1
	 */
	// Cedric Thai
	public void setNbToursJoueur1(int nbTours) {
		joueur1.setNbToursJoues(nbTours);
		repaint();
	}
	
	/**
	 * Methode qui retourne le nombre de tours du joueur 2
	 * @return le nombre de tours du joueur 2
	 */
	// Cedric Thai
	public int getNbToursJoueur2() {
		return joueur2.getNbToursJoues();
	}
	
	/**
	 * Methode qui modifie le nombre de tours du joueur 1
	 * @param nbTours le nombre de tours du joueur 1
	 */
	// Cedric Thai
	public void setNbToursJoueur2(int nbTours) {
		joueur2.setNbToursJoues(nbTours);
		repaint();
	}
	
	
}
