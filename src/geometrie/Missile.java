package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import application.FenetreTerrainJeu;
import application.FenetreTutoriel;
import composantsDessin.InfoGuidageMissile;
import interfaces.Dessinable;
import outils.FlecheVectorielle;
import outils.Vecteur2D;
import outils.Vecteur3D;
import physique.MoteurPhysique;

/**
 * Classe qui represente l'objet missile, la physique du missile est controlee
 * ici
 * 
 * @author Gabriel Maurice
 * @author Théo Fourteau
 * @author Noé Julien
 */
public class Missile implements Dessinable {
	/** La hitbox du missile **/
	private Point2D.Double missilePoint;
	/** La longeur du missile **/
	private int height = 52;
	/** La largeur du missile **/
	private int width = 16;
	/** Chaîne choisisant le tpye d'image a dessiner **/
	private static String choixImage;
	/** la vitesse initiale du missile **/
	private static double vitesse0;
	/** L'angle horizontal du missile lors du lancement **/
	private static double angleHor;
	/** L'angle vertical du missile lors du lancement **/
	private static double angleVert;
	/** Le vecteur de position du missile **/
	private Vecteur3D position;
	/** Le vecteur de vitesse du missile **/
	private Vecteur3D vitesse;
	/** Le vecteur d'acceleration du missile **/
	private Vecteur3D accel;
	/** Le temps ecole depuis le debut de la simulation **/
	private double tempsEcoule;
	/** Booleen qui gere les collision **/
	private boolean collision = false;
	/** La hauteur de la camera pour la perspective **/
	private double hauteurCam = 200;
	/** Le facteur de redimensionnement du missile lorsqu'il est dans les airs **/
	private double facteurRedim;
	/** masse du missile en kg **/
	private static double masse = 800;
	/** flèche vectorielle utilisée pour la vitesse **/
	private FlecheVectorielle flecheVitesse;
	/** Valeur booléenne si la flèche vectorielle est affichée **/
	private static boolean affichFleche;
	/** Charge du missile **/
	private static double charge = 0.005;

	/**
	 * Constructeur de la classe
	 */
//	Gabriel Maurice
	public Missile() {
		position = new Vecteur3D(0, 0, 0);
		vitesse = new Vecteur3D(0, 0, 0);
		accel = new Vecteur3D(0, 0, 0);
		choixImage = "Basique";
		creerLaGeometrie();
	}

	/**
	 * Cree la geometrie du missile
	 */
//	Gabriel Maurice
	public void creerLaGeometrie() {
		missilePoint = new Point2D.Double((int) position.getX() + width / 2, (int) position.getY());
	}

	/**
	 * Dessine le missile
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Gabriel Maurice
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		missilePoint.x = (int) position.getX();
		missilePoint.y = (int) position.getY();

		g2dPrive.rotate(Math.PI, missilePoint.x, missilePoint.y);
		g2dPrive.translate(position.getX(), position.getY());
		g2dPrive.scale(0.5, 0.5);
		g2dPrive.translate(0, height / 2);
		g2dPrive.scale(facteurRedim, facteurRedim);
		g2dPrive.translate(-position.getX(), -position.getY() - height / 2);

		g2dPrive.rotate(Math.toRadians(angleHor), missilePoint.x, missilePoint.y);
		if (!collision) {
			DessinTypeMissile missile1 = new DessinTypeMissile((int) position.getX() - width / 2, (int) position.getY(),
					height, width, choixImage);
			missile1.dessiner(g2dPrive);
		}
		g2dPrive.rotate(-Math.PI, missilePoint.x, missilePoint.y);
		g2dPrive.translate(position.getX(), position.getY());
		g2dPrive.scale(0.5, 0.5);
		g2dPrive.translate(0, height / 2);
		g2dPrive.scale(facteurRedim, facteurRedim);
		g2dPrive.translate(-position.getX(), -position.getY() - height / 2);

		dessinFleche(g2d);
	}

	/**
	 * Methode qui s'occupe du lancement du missile
	 */
//	Gabriel Maurice
	public void lancerMissile() {
		vitesse.setComposantes(Vecteur3D.conversionEnComposants(vitesse0, -angleHor, angleVert));
	}

	/**
	 * Calcul la position et la vitesse apres un certain temps ecoule
	 * 
	 * @param deltaT Le temps entre chaque calcul
	 */
//	Gabriel Maurice
	public void calculerUnFrame(double deltaT) {
		if (!collision) {

			vitesse.setComposantes(MoteurPhysique.calculVitesse(deltaT, vitesse, accel));
			position.setComposantes(MoteurPhysique.calculPosition(deltaT, position, vitesse));

			double hauteurMissile = Math.max(position.getZ(), 0);
			facteurRedim = hauteurCam / (hauteurCam - position.getZ());

			printVitesseEtPosition();
			tempsEcoule += deltaT;
			// Mise à jour du graphique de hauteur en fonction du temps

			// Si la fenêtre de jeu est ouverte ou si c'est la fenêtre Tutoriel.
			if (FenetreTerrainJeu.estOuvert()) {
				// Appeler la méthode miseAJourGraphiques si la fenêtre est ouverte
				FenetreTerrainJeu.miseAJourGraphiques(tempsEcoule, hauteurMissile, vitesse.module());
			} else {
				FenetreTutoriel.miseAJourGraphiques(tempsEcoule, hauteurMissile, vitesse.module());
			}

			InfoGuidageMissile.changementCoordonnees(position);

			verifierCollisionSol();
		}
	}

	/**
	 * Verifie si le missile entre en contact avec le sol
	 */
//	Gabriel Maurice
	public void verifierCollisionSol() {
		if (position.getZ() <= 0 && vitesse.getZ() < 0) {
			collision = true;
			tempsEcoule = 0; // réinitiliasation du temps à 0

		}
	}

	/**
	 * Methode appele lorsque le missile touche les murs rouge
	 */
//	Gabriel Maurice
	public void collisionMur() {
		collision = true;
		tempsEcoule = 0;
	}

	/**
	 * Affiche la position et la vitesse du missile en x, y et z après un certain
	 * temps.
	 */
//	Gabriel Maurice
	public void printVitesseEtPosition() {
		System.out.println("Position du missile en x, y et z apres " + (double) Math.round(tempsEcoule * 100) / 100
				+ " s: " + (double) Math.round(position.getX() * 100) / 100 + ", "
				+ (double) Math.round(position.getY() * 100) / 100 + ", "
				+ (double) Math.round(position.getZ() * 100) / 100);
		System.out.println("Vitesse du missile en x, y et z apres " + (double) Math.round(tempsEcoule * 100) / 100
				+ " s: " + (double) Math.round(vitesse.getX() * 100) / 100 + ", "
				+ (double) Math.round(vitesse.getY() * 100) / 100 + ", "
				+ (double) Math.round(vitesse.getZ() * 100) / 100);
		System.out.println("\n" + "---------------------------------------------- " + "\n");
	}

	/**
	 * Méthode qui applique des forces externes sur le missile pour changer sa
	 * trajectoire
	 * 
	 * @param force la force externe appliquée
	 */
	// Théo Fourteau
	public void appliquerForce(Vecteur3D force) {
		try {
			this.accel = MoteurPhysique.calculAcceleration(force, masse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode appele lorsque le missile touche le mur gauche droite
	 */
//	Gabriel Maurice
	public void collisionMurLat() {
		vitesse = new Vecteur3D(-vitesse.getX(), vitesse.getY(), vitesse.getZ());
	}

	/**
	 * Methode appele lorsque le missile touche le mur du fond
	 */
//	Gabriel Maurice
	public void collisionMurFond() {
		vitesse = new Vecteur3D(vitesse.getX(), -vitesse.getY(), vitesse.getZ());

	}

	/**
	 * Methode qui retourne la vitesse initiale
	 * 
	 * @return vitesse0 la itesse initiale
	 */
//	Gabriel Maurice
	public static double getVitesse0() {
		return vitesse0;
	}

	/**
	 * Methode qui change la vitesse initiale
	 * 
	 * @param vitesse0 la vitesse initiale
	 */
//	Gabriel Maurice
	public static void setVitesse0(double vitesse0) {
		Missile.vitesse0 = vitesse0;
	}

	/**
	 * Methode qui retourne l'angle horizontal
	 * 
	 * @return angleHor l'angle horizontal
	 */
//	Gabriel Maurice
	public static double getAngleHor() {
		return angleHor;
	}

	/**
	 * Methode qui change l'angle horizontale
	 * 
	 * @param angleHor l'angle horizontale
	 */
//	Gabriel Maurice
	public static void setAngleHor(double angleHor) {
		Missile.angleHor = angleHor;
	}

	/**
	 * Methode qui retourne l'angle vertical
	 * 
	 * @return angleVert l'angle vertical
	 */
//	Gabriel Maurice
	public static double getAngleVert() {
		return angleVert;
	}

	/**
	 * Methode qui change l'angle vertical
	 * 
	 * @param angleVert l'angle vertical
	 */
//	Gabriel Maurice
	public static void setAngleVert(double angleVert) {
		Missile.angleVert = angleVert;
	}

	/**
	 * Methode qui retourne la position
	 * 
	 * @return position la position
	 */
//	Gabriel Maurice
	public Vecteur3D getPosition() {
		return position;
	}

	/**
	 * Methode qui change la position
	 * 
	 * @param position la position
	 */
//	Gabriel Maurice
	public void setPosition(Vecteur3D position) {
		this.position = position;
	}

	/**
	 * Methode qui retourne la vitesse
	 * 
	 * @return vitesse la vitesse
	 */
//	Gabriel Maurice
	public Vecteur3D getVitesse() {
		return vitesse;
	}

	/**
	 * Methode qui change la vitesse
	 * 
	 * @param vitesse la vitesse
	 */
//	Gabriel Maurice
	public void setVitesse(Vecteur3D vitesse) {
		this.vitesse = vitesse;
	}

	/**
	 * Methode qui retourne l'acceleration
	 * 
	 * @return vitesse l'acceleration
	 */
//	Gabriel Maurice
	public Vecteur3D getAccel() {
		return accel;
	}

	/**
	 * Methode qui change l'aceleration
	 * 
	 * @param accel l'acceleration
	 */
//	Gabriel Maurice
	public void setAccel(Vecteur3D accel) {
		this.accel = accel;
	}

	/**
	 * Methode qui retourne la largeur du missile
	 * 
	 * @return width la largeur
	 */
//	Gabriel Maurice
	public int getWidth() {
		return width;
	}

	/**
	 * Methode qui retourne la hauteur du missile
	 * 
	 * @return height la hauteur
	 */
//	Gabriel Maurice
	public int getHeight() {
		return height;
	}

	/**
	 * Methode qui retourne le booleen de collision
	 * 
	 * @return collisionSol le booleen de collision
	 */
//	Gabriel Maurice
	public boolean getCollisionSol() {
		return collision;
	}

	/**
	 * Methode qui change le booleen de collision
	 * 
	 * @param collisionSol le boolean de collision
	 */
//	Gabriel Maurice
	public void setCollisionSol(boolean collisionSol) {
		this.collision = collisionSol;
	}

	/**
	 * Methode qui retourne la pointe du missile
	 * 
	 * @return missilePoint la pointe du missile
	 */
//	Gabriel Maurice
	public Point2D.Double getMissilePoint() {
		return missilePoint;
	}

	/**
	 * Renvoie le choix d'image.
	 * 
	 * @return Le choix d'image sous forme de String
	 */
	// Noé Julien
	public static String getChoixImage() {
		return choixImage;
	}

	/**
	 * Définit le choix d'image.
	 * 
	 * @param choix Le choix d'image à définir en String
	 */
	// Noé Julien
	public static void setChoixImage(String choix) {
		switch (choix) {
		case "R-360-1":
			choixImage = "Basique";
			masse = 800;
			charge = 0.005;
			break;
		case "AGM-84-K2":
			choixImage = "espace";
			masse = 1000;
			charge = -0.005;
			break;
		}
	}

	/**
	 * Methode qui retourne la masse du missile
	 * 
	 * @return masse la masse du missile
	 */
	// Noé Julien
	public static double getMasse() {
		return masse;
	}

	/**
	 * Methode qui retourne la charge du missile
	 * 
	 * @return charge la charge du missile
	 */
	// Noé Julien
	public static double getCharge() {
		return charge;
	}

	/**
	 * Définit la charge du missile
	 * 
	 * @param charge la nouvelle charge a donner
	 */
	// Noé Julien
	public static void setCharge(double charge) {
		Missile.charge = charge;
	}

	/**
	 * Méthode qui permet de dessiner une flèche vectorielle représentant la vitesse
	 * du missile
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Noé Julien
	public void dessinFleche(Graphics2D g2d) {
		if (!collision && affichFleche) {
			Vecteur2D vitesse2D = new Vecteur2D(vitesse.getX(), vitesse.getY());
			flecheVitesse = new FlecheVectorielle((missilePoint.getX()), (missilePoint.getY()), vitesse2D);

			flecheVitesse.setAngleTete(80);
			g2d.setColor(Color.red);
			flecheVitesse.setLongueurTraitDeTete(facteurRedim * 2);
			flecheVitesse.redimensionneCorps(facteurRedim * 2);
			flecheVitesse.dessiner(g2d);
		}
	}

	/**
	 * Methode qui change si il faut afficher la flèche vectorielle
	 * 
	 * @param choix le nouveau choix
	 */
	// Noé Julien
	public static void setAffichFleche(boolean choix) {
		Missile.affichFleche = choix;
	}

}