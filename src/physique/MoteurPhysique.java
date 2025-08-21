package physique;

import outils.Vecteur3D;
import java.lang.Math;

/**
 * Cette classe regroupe les calculs physiques nécessaires au mouvement des
 * objets dans la scène. Elle utilise la méthode numérique d'Euler implicite.
 * 
 * @author Caroline Houle
 * @author Noé Julien
 * @author Théo Fourteau
 */
public class MoteurPhysique {

	/** accélération gravitationnelle (m/s^2) */
	private static final double ACCEL_G = 9.80665;

	/** tolérance utilisée dans les comparaisons réelles avec zéro */
	private static final double EPSILON = 1e-10;

	/** coefficient de restitution */
	private static final double COEFFICIENT_RESTITUTION = 0.8; // collision élastique

	/** Charge du missile en Coulombs */
	private static final double CHARGE_Q_MISSILE = 1;
	
	/** Constructeur **/
	public MoteurPhysique() {
		
	}

	/**
	 * Calcule et retourne la vitesse initiale d'un objet selon son impulsion au
	 * moment initial
	 * 
	 * @param impulsion l'impulsion de l'objet à l'instant initial (N•s)
	 * @param masse     la masse de l'objet (kg)
	 * @return la vitesse initiale de l'objet (m/s)
	 */
	// Noé Julien
	public static double calculVitesse(double impulsion, double masse) {
		return impulsion / masse;
	}

	/**
	 * Calcule et retourne l'impulsion d'un objet B immobile suite à une collision
	 * avec un objet A
	 * 
	 * @param vitesseMomentImpactA vitesse de l'objet A au moment de l'impact (m/s)
	 * @param masseA               masse de l'objet A (kg)
	 * @param masseB               masse de l'objet B (kg)
	 * @return l'impulsion de l'objet B directement après la collision (N•s)
	 */
	// Noé Julien
	public static double calculImpulsion(double vitesseMomentImpactA, double masseA, double masseB) {
		return (1 + COEFFICIENT_RESTITUTION) * vitesseMomentImpactA / (1 / masseA + 1 / masseB);
	}

	/**
	 * Calcule et retourne l'accélération (m/s^2) en utilisant F = ma
	 * 
	 * @param sommeDesForces somme des forces appliquées (N)
	 * @param masse          masse de l'objet (kg)
	 * @return l'accélération (F/m) (m/s^2)
	 * @throws Exception l'erreur si la masse est pratiquement nulle
	 */
	// Caroline Houle
	public static Vecteur3D calculAcceleration(Vecteur3D sommeDesForces, double masse) throws Exception {
		if (masse < EPSILON)
			throw new Exception(
					"Erreur MoteurPhysique: La masse �tant nulle ou presque l'acc�leration ne peut pas etre calcul�e.");
		else
			return new Vecteur3D(sommeDesForces.getX() / masse, sommeDesForces.getY() / masse,
					sommeDesForces.getZ() / masse);
	}

	/**
	 * Calcule et retourne la nouvelle vitesse (m/s), deltaT secondes plus tard, en
	 * utilisant la méthode numérique d'Euler implicite.
	 * 
	 * @param deltaT  l'intervalle de temps (s)
	 * @param vitesse la vitesse au début de l'intervalle de temps (m/s)
	 * @param accel   l'accélération au début de l'intervalle de temps (m/s^2)
	 * @return la vitesse à la fin de l'intervalle de temps (m/s)
	 */
	// Caroline Houle
	public static Vecteur3D calculVitesse(double deltaT, Vecteur3D vitesse, Vecteur3D accel) {
		Vecteur3D deltaVitesse = Vecteur3D.multiplie(accel, deltaT);
		Vecteur3D resultVit = vitesse.additionne(deltaVitesse);

		return new Vecteur3D(resultVit.getX(), resultVit.getY(), resultVit.getZ());

	}

	/**
	 * Calcule et retourne la nouvelle position (m), deltaT secondes plus tard, en
	 * utilisant la méthode numérique d'Euler implicite.
	 * 
	 * @param deltaT   l'intervalle de temps (s)
	 * @param position la position au début de l'intervalle de temps (m)
	 * @param vitesse  la vitesse au début de l'intervalle de temps (m/s)
	 * @return la position à la fin de l'intervalle de temps (m)
	 */
	// Caroline Houle
	public static Vecteur3D calculPosition(double deltaT, Vecteur3D position, Vecteur3D vitesse) {
		Vecteur3D deltaPosition = Vecteur3D.multiplie(vitesse, deltaT);
		Vecteur3D resultPos = position.additionne(deltaPosition);
		return new Vecteur3D(resultPos.getX(), resultPos.getY(), resultPos.getZ());
	}

	/**
	 * Forme et retourne un vecteur exprimant la force gravitationnelle s'appliquant
	 * sur un objet dont la masse est passée en paramètre
	 * 
	 * @param masse masse de l'objet
	 * @return le vecteur représentant la force gravitationnelle exercée
	 */
	// Caroline Houle
	public static Vecteur3D calculForceGrav(double masse) {
		return new Vecteur3D(0, ACCEL_G * masse, 0);
	}

	/**
	 * Permet de calculer la force gravitationelle et de la retourner en double
	 * 
	 * @param masse La masse de l'objet (kg)
	 * @param angle L'angle donné (degrée)
	 * @return la force gravitationelle en double
	 */
	// Noé Julien
	public static double forceGrav(double masse, double angle) {
		return (-masse * ACCEL_G * Math.sin(angle));
	}

	/**
	 * Méthode qui permet de calculer la force magnétique d'un champ
	 * 
	 * @param vitesse  le vecteur représentant la vitesse du projectile en m/s
	 * @param champMag le vecteur représentant le champ magnétique en Tesla
	 * @return fMagnetique, la force magnétique en N
	 */
	// Théo Fourteau
	public static Vecteur3D calculForceMag(Vecteur3D vitesse, Vecteur3D champMag) {
		return vitesse.prodVectoriel(champMag).multiplie(CHARGE_Q_MISSILE);
	}

}