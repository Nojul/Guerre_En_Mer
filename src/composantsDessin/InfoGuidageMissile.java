package composantsDessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import application.FenetreTerrainJeu;
import application.FenetreTutoriel;
import geometrie.Missile;
import outils.Vecteur3D;

/**
 * Classe qui permet d'afficher dans un JPanel, les informations liées au
 * guidage du missile que le joueur s'apprête à tirer. En effet, le composanta
 * affiche la portée maximale, le temps de vol et l'altitude maximale. Pendant
 * l'animation, le composant affiche aussi les coordonnées en X,Y et Z du
 * missile. Le panneau affiche aussi la masse du missile en fonction de celui
 * qui est sélectionné.
 * 
 * @author Théo Fourteau
 */
public class InfoGuidageMissile extends JPanel {

	/** Indice unique de sérialisation **/
	private static final long serialVersionUID = 1305000565895159165L;
	/** Angle avec l'horizontale initial du canon (en degrés) **/
	private static double angleI = 15;
	/** Vitesse initiale du missile **/
	private static double vitesseI = 30;
	/** Acceleration gravitationnelle du lieu **/
	private static double facteurGravite;
	/** Temps de vol du missile estimé en secondes **/
	static double tempsVol;
	/** Portée maximale du missile estimée en mètres **/
	static double porteeMissile;
	/** Altitude maximale du missile estimée en mètres **/
	static double hauteurMissile;
	/** Booléen qui permet de définir toutes les valeurs affichées à 0 **/
	static boolean setToZero = true;
	/** Chaine de caractère qui définit les coordonnées en x,y et z du missile **/
	private static String coordonneesXYZ = "Position: ( nulle, nulle, nulle )";
	/**
	 * Boolean qui determine si InfoGuidageMissile est appele dans FenetreTerrainJeu
	 **/
	private static boolean terrainJeu = false;
	/**
	 * Boolean qui determine si InfoGuidageMissile est appele dans Tutoriel
	 **/
	private static boolean tutoriel = false;
	

	/**
	 * Constructeur de la classe
	 **/
	// Théo Fourteau
	public InfoGuidageMissile() {
		if(FenetreTerrainJeu.estOuvert()) {
			terrainJeu = true;
			tutoriel = false;
		}else if(FenetreTutoriel.estOuvert()) {
			terrainJeu = false;
			tutoriel = true;
		}
		

	}// fin constructeur

	/**
	 * Dessin des informations liées à la trajectoire du missile.
	 * 
	 * @param g le contexte graphique.
	 */
	// Théo Fourteau
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Graphics2D g2dPriv = (Graphics2D) g2d.create(); // Copie du composant graphique.

		g2dPriv.setColor((new Color(50, 106, 73)));

		if (!setToZero) {
			// Appel de la méthode de calculs
			calculsGuidage();
		} else {
			calculsGuidage(15, 30);
		}
		setToZero = false;

		// Création du format pour trois décimales
		DecimalFormat df = new DecimalFormat("#.###");

		// Affichage des valeurs calculées avec 3 décimales
		String porteeMissileStr = "Portée maximale : " + df.format(porteeMissile) + " m";
		String tempsVolStr = "Temps de vol : " + df.format(tempsVol) + " s";
		String hauteurMaxStr = "Altitude maximale : " + df.format(hauteurMissile) + " m";

		// Position d'affichage des valeurs
		int x = 10; // Position x
		int y = 20; // Position y

		// Affichage des valeurs sur le composant de dessin
		g2dPriv.drawString(porteeMissileStr, x, y);
		g2dPriv.drawString(tempsVolStr, x, y + 40); // Espacement vertical de 20 pixels entre chaque ligne
		g2dPriv.drawString(hauteurMaxStr, x, y + 80); // Espacement vertical de 20 pixels entre chaque ligne
		g2dPriv.drawString(coordonneesXYZ, x, y + 120);

	}

	/**
	 * Méthode qui permet d'effectuer tous les calculs liées au guidage du missile.
	 * En effet, elle calcule la portée maximale en m, le temps de vol en s et
	 * l'altitude maximale atteignable en m, avec les informations données par la
	 * jauge et le canon. À chaque modification de ces composants, les calculs sont
	 * ré-effectués Les valeurs calculées sont affichées dans le panneau.La méthode
	 * met les graphiques à jour.
	 */
	// Théo fourteau
	static void calculsGuidage() {
		double angleRadians = Math.toRadians(angleI);
		double vitesseHorizontale = vitesseI * Math.cos(angleRadians);
		double vitesseVerticale = vitesseI * Math.sin(angleRadians);

		// Temps de vol
		tempsVol = (2 * vitesseI * Math.sin(angleRadians)) / facteurGravite;
		// gravité
		// Portée maximale
		porteeMissile = vitesseHorizontale * tempsVol;

		// Altitude maximale
		hauteurMissile = (Math.pow(vitesseVerticale, 2)) / (2 * facteurGravite);

		if (terrainJeu) {
			FenetreTerrainJeu.miseAJourLignesGraphiques(tempsVol, hauteurMissile, porteeMissile);
		}else if (tutoriel){
			FenetreTutoriel.miseAJourLignesGraphiques(tempsVol, hauteurMissile, porteeMissile);
		}

	}

	/**
	 * Méthode qui permet d'effectuer les calculs de guidage avec deux paramètres.
	 * C'est la méthode qui est utilisée au début quand les paramètres de
	 * l'application n'ont pas encore été appliqués. La méthode met les graphiques à
	 * jour.
	 * 
	 * @param angle   l'angle du missile avec la verticale
	 * @param vitesse la vitesse du missile en m/s
	 */
	// Théo fourteau
	static void calculsGuidage(int angle, double vitesse) {
		double angleRadians = Math.toRadians(angleI);
		double vitesseHorizontale = vitesseI * Math.cos(angleRadians);
		double vitesseVerticale = vitesseI * Math.sin(angleRadians);

		// Temps de vol
		tempsVol = (2 * vitesseI * Math.sin(angleRadians)) / facteurGravite;
		// gravité
		// Portée maximale
		porteeMissile = vitesseHorizontale * tempsVol;

		// Altitude maximale
		hauteurMissile = (Math.pow(vitesseVerticale, 2)) / (2 * facteurGravite);

		if (terrainJeu) {
			FenetreTerrainJeu.miseAJourLignesGraphiques(tempsVol, hauteurMissile, porteeMissile);
		} else if(tutoriel){
			FenetreTutoriel.miseAJourLignesGraphiques(tempsVol, hauteurMissile, porteeMissile);
		}

	}

	/**
	 * Méthode qui permet de changer l'angle initial du missile avec l'horizontale.
	 * La méthode appelle la méthode de calculs.
	 * 
	 * @param nouvAngle le nouvel angle en degrés
	 */
	// Théo Fourteau
	public static void changeAngle(double nouvAngle) {
		angleI = nouvAngle;
		calculsGuidage();
		if (terrainJeu) {
			FenetreTerrainJeu.dessinerInfosGuidage();
		} else if(tutoriel){
			FenetreTutoriel.dessinerInfosGuidage();
		}
	}

	/**
	 * Méthode qui permet de changer la vitesse initiale du missile. La méthode
	 * appelle la méthode de calculs
	 * 
	 * @param vitesse la nouvelle vitesse du missile
	 */
	// Théo Fourteau
	public static void changeVitesse(double vitesse) {
		vitesseI = vitesse;
		Missile.setVitesse0(vitesse);
		calculsGuidage();
		if (terrainJeu) {
			FenetreTerrainJeu.dessinerInfosGuidage();
		} else if (tutoriel){
			FenetreTutoriel.dessinerInfosGuidage();
		}
	}

	/**
	 * Méthode qui permet de définir toutes les valeurs affichées comme égales à 0.
	 * Cela sert à les cacher pour l'autre joueur.
	 **/
	// Théo Fourteau
	public static void reinitialiserA0() {
		setToZero = true;
		coordonneesXYZ = "Pressez le bouton de Tir";
		if (terrainJeu) {
			FenetreTerrainJeu.dessinerInfosGuidage();
		} else if (tutoriel){
			FenetreTutoriel.dessinerInfosGuidage();
		}
	}

	/**
	 * Méthode qui permet de changer l'affichage des coordonnées du missile à chaque
	 * frame de l'animation de celui-ci.
	 * 
	 * @param positionMissile le Vecteur3D qui représente la position du missile.
	 */
	// Théo Fourteau
	public static void changementCoordonnees(Vecteur3D positionMissile) {
		double posX = Math.abs(positionMissile.getX());
		double posY = Math.abs(positionMissile.getY());
		double posZ = Math.abs(positionMissile.getZ());

		String formatPosX = String.format("%.1f", posX);
		String formatPosY = String.format("%.1f", posY);
		String formatPosZ = String.format("%.1f", posZ);

		// Créer la chaîne de coordonnées avec le bon format
		coordonneesXYZ = "Position (m): ( " + formatPosX + ", " + formatPosY + ", " + formatPosZ + " )";
		if (terrainJeu) {
			FenetreTerrainJeu.dessinerInfosGuidage();
		} else if (tutoriel){
			FenetreTutoriel.dessinerInfosGuidage();
		}

	}

	/**
	 * Méthode qui permet de changer l'accélération gravitationnelle pour les
	 * calculs.
	 * 
	 * @param facteurGravite la nouvelle accélération gravitationnelle
	 */
	// Théo Fourteau
	public static void setFacteurGravite(double facteurGravite) {
		InfoGuidageMissile.facteurGravite = facteurGravite;
	}

	/**
	 * Méthode qui permet d'obtenir le temps de vol maximum pour le dessin des
	 * graphiques
	 * 
	 * @return tempsVol le temps de vol maximum estimé
	 */
	// Théo Fourteau
	public static double getTempsVol() {
		return tempsVol;
	}

	/**
	 * Méthode qui permet d'obtenir la portée maximale pour le dessin des graphiques
	 * 
	 * @return porteeMissile la portée maximale estimée du missile
	 */
	// Théo Fourteau
	public static double getPorteeMissile() {
		return porteeMissile;
	}

	/**
	 * Méthode qui permet d'obtenir la hauteur maximale pour le dessin des
	 * graphiques
	 * 
	 * @return hauteurMissile la hauteur maximale que le missile devrait atteindre.
	 */
	// Théo Fourteau
	public static double getHauteurMissile() {
		return hauteurMissile;
	}

	/**
	 * Méthode qui permet d'obtenir la vitesse initiale du missile pour le dessin
	 * des graphiques
	 * 
	 * @return vitesseI la vitesse initiale du missile.
	 */
	// Théo Fourteau
	public static double getVitesseI() {
		return vitesseI;
	}

}
