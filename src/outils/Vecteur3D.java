package outils;

import java.io.Serializable;
import java.lang.Math;

/**
 * La classe Vecteur permet de realiser les operations de base sur un vecteur
 * Euclidien en trois dimensions (x,y,z), o� x, y et z sont les composantes du
 * vecteur.
 * 
 * **ATTENTION*** Les identifiants x,y et z sont utilis�s dans cette classe non
 * pas pour repr�senter des position, mais bien pour repr�senter des
 * composantes!!
 * 
 * Cette classe est une version 3d modifi�e et augment�e de la classe SVector3d
 * ecrite par Simon Vezina dans le cadre du cours de physique.
 * 
 * @author Simon V�zina
 * @author Caroline Houle
 * @author Gabriel Maurice
 */
public class Vecteur3D implements Serializable {
	// champs de base

	/** Identifiant unique de sérialisation **/
	private static final long serialVersionUID = 2801033839926158076L;
	/** tolerance utilisee dans les comparaisons reeles avec zero */
	private static final double EPSILON = 1e-10;
	/** Composante x du vecteur 3D. */
	protected double x;
	/** Composante y du vecteur 3D. */
	protected double y;
	/** Composante z du vecteur 3D. */
	protected double z;

	/**
	 * Constructeur representant un vecteur 2d aux composantes nulles
	 */
//	Gabriel Maurice
	public Vecteur3D() {
		x = 0;
		y = 0;
		z = 0;
	}

	/**
	 * Constructeur avec composantes x,y
	 * 
	 * @param x La composante x du vecteur.
	 * @param y La composante y du vecteur.
	 * @param z La composante z du vecteur.
	 */
//	Gabriel Maurice
	public Vecteur3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructeur qui cr�e un nouveau vecteur qui contient les m�mes composantes
	 * que celui pass� en param�tre
	 * 
	 * @param v Le vecteur � reproduire
	 */
//	Gabriel Maurice
	public Vecteur3D(Vecteur3D v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	/**
	 * Methode qui donne acces a la composante x du vecteur.
	 * 
	 * @return La composante x
	 */
//	Gabriel Maurice
	public double getX() {
		return x;
	}

	/**
	 * Methode qui donne acces a la composante y du vecteur.
	 * 
	 * @return La composante y
	 */
//	Gabriel Maurice
	public double getY() {
		return y;
	}

	/**
	 * Methode qui donne acces a la composante z du vecteur.
	 * 
	 * @return La composante z
	 */
//	Gabriel Maurice
	public double getZ() {
		return z;
	}

	/**
	 * Methode qui permet de modifier la composante x du vecteur.
	 * 
	 * @param x La nouvelle composante x
	 */
//	Gabriel Maurice
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Methode qui permet de modifier la composante y du vecteur.
	 * 
	 * @param y La nouvelle composante y
	 */
//	Gabriel Maurice
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Methode qui permet de modifier la composante z du vecteur.
	 * 
	 * @param z La nouvelle composante z
	 */
//	Gabriel Maurice
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Methode qui permet de modifier les deux composantes du vecteur.
	 * 
	 * @param x La nouvelle composante x
	 * @param y La nouvelle composante y
	 * @param z La nouvelle composante z
	 */
//	Gabriel Maurice
	public void setComposantes(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Methode qui permet de modifier les deux composantes du vecteur en
	 * reproduisant celles du vecteur pass� en param�tre
	 * 
	 * @param v Le vecteur dont on d�sire copier les composantes
	 */
//	Gabriel Maurice
	public void setComposantes(Vecteur3D v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	/**
	 * Genere une chaine de caractere avec les informations du vecteur
	 * 
	 * @return Une chaîne de caractères représentant le vecteur avec ses composantes
	 *         x, y et z.
	 */
//	Gabriel Maurice
	public String toString() {
		return "[ x = " + x + ", y = " + y + ", y = " + z + "]";
	}

	/**
	 * Genere une chaine de caractere avec les informations du vecteur, avec un
	 * nombre de decimales restreint
	 * 
	 * @param nbDecimales Nombre de chiffres significatifs d�sir�s
	 * @return Une chaîne de caractères représentant le vecteur avec les décimales
	 *         spécifiées pour chaque composante.
	 */
//	Gabriel Maurice
	public String toString(int nbDecimales) {
		return "[ x = " + String.format("%." + nbDecimales + "f", x) + ", y = "
				+ String.format("%." + nbDecimales + "f", y) + ", z = " + String.format("%." + nbDecimales + "f", z)
				+ "]";
	}

//	/**
//	 * Determine si le vecteur courant est egal ou non a un autre vecteur, a EPSILON pres
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if(this == obj)
//			return true;
//
//		if(obj == null)
//			return false;
//
//		if(!(obj instanceof Vecteur3D))
//			return false;
//
//		Vecteur3D other = (Vecteur3D) obj;
//
//		//Comparaison des valeurs x,y et z en utilisant la precision de EPSILON modulee par la valeur a comparer
//		if(Math.abs(x - other.x) > Math.abs(x)*EPSILON)
//			return false;
//
//		if(Math.abs(y - other.y) > Math.abs(y)*EPSILON)
//			return false;
//		if(Math.abs(z - other.z) > Math.abs(z)*EPSILON)
//			return false;
//
//		return true;
//	}

	/**
	 * Methode qui calcule et retourner l'addition du vecteur courant et d'un autre
	 * vecteur. Le vecteur courant reste inchang�.
	 * 
	 * @param v Le vecteur a ajouter au vecteur courant
	 * @return Un nouveau vecteur qui represente la somme des deux vecteurs
	 */
//	Gabriel Maurice
	public Vecteur3D additionne(Vecteur3D v) {
		return new Vecteur3D(x + v.x, y + v.y, z + v.z);
	}

	/**
	 * Methode de classe qui retourne l'addition de deux vecteurs quelconques.
	 * 
	 * @param v1 Le premier vecteur
	 * @param v2 Le deuxieme vecteur
	 * @return Un nouveau vecteur qui represente la somme des deux vecteurs
	 */
//	Gabriel Maurice
	public static Vecteur3D additionne(Vecteur3D v1, Vecteur3D v2) {
		return v1.additionne(v2);
	}

	/**
	 * Methode qui calcule et retourne le vecteur resultant de la soustraction d'un
	 * vecteur quelconque du vecteur courant. Le vecteur courant reste inchang�.
	 * 
	 * @param v Le vecteur a soustraire au vecteur courant.
	 * @return Un nouveau vecteur qui represente la soustraction des deux vecteurs.
	 */
//	Gabriel Maurice
	public Vecteur3D soustrait(Vecteur3D v) {
		return new Vecteur3D(x - v.x, y - v.y, z - v.z);
	}

	/**
	 * Methode de classe qui retourne la soustraction entre deux vecteurs
	 * quelconques.
	 * 
	 * @param v1 Le premier vecteur
	 * @param v2 Le deuxieme vecteur, a soustraire du premier
	 * @return Un nouveau vecteur qui represente la diffrence entre les deux
	 *         vecteurs
	 */
//	Gabriel Maurice
	public static Vecteur3D soustrait(Vecteur3D v1, Vecteur3D v2) {
		return v1.soustrait(v2);
	}

	/**
	 * Methode qui effectue la multiplication du vecteur courant par une scalaire.Le
	 * vecteur courant reste inchang�.
	 * 
	 * @param m - Le muliplicateur
	 * @return Un nouveau vecteur qui represente le resultat de la multiplication
	 *         par un scalaire m sur le vecteur.
	 */
//	Gabriel Maurice
	public Vecteur3D multiplie(double m) {
		return new Vecteur3D(m * x, m * y, m * z);
	}

	/**
	 * Methode de classe qui effectue la multiplication d'un vecteur quelconque par
	 * une scalaire.
	 * 
	 * @param v Le vecteur
	 * @param m Le muliplicateur
	 * @return Un nouveau vecteur qui represente le resultat de la multiplication
	 *         par un scalaire m sur le vecteur.
	 */
//	Gabriel Maurice
	public static Vecteur3D multiplie(Vecteur3D v, double m) {
		return v.multiplie(m);
	}

	/**
	 * Methode pour obtenir le module du vecteur courant.
	 * 
	 * @return Le module du vecteur courant.
	 */
//	Gabriel Maurice
	public double module() {
		return Math.sqrt((x * x) + (y * y) + (z * z));
	}

	/**
	 * Methode de classe pour obtenir le module d'un vecteur quelconque.
	 * 
	 * @param v Le vecteur
	 * @return Le module du vecteur.
	 */
//	Gabriel Maurice
	public static double module(Vecteur3D v) {
		return v.module();
	}

	/**
	 * Methode pour normaliser le vecteur courant. Un vecteur normalise possede la
	 * meme orientation, mais possede une longeur unitaire. Si le module du vecteur
	 * est nul, le vecteur normalise sera le vecteur nul (0.0, 0.0).
	 * 
	 * @return Le vecteur normalise.
	 * @throws Exception Si le vecteur ne peut pas etre normalise etant trop petit
	 *                   ou de longueur nulle.
	 */
//	Gabriel Maurice
	public Vecteur3D normalise() throws Exception {
		double mod = module(); // obtenir le module du vecteur

		// Verification du module. S'il est trop petit, nous ne pouvons pas
		// numeriquement normaliser ce vecteur
		if (mod < EPSILON)
			throw new Exception("Erreur Vecteur: Le vecteur " + this.toString()
					+ " �tant nul ou presque nul ne peut pas etre normalis�.");
		else
			return new Vecteur3D(x / mod, y / mod, z / mod);
	}

	/**
	 * Methode de classe pour normaliser un vecteur quelconque. Un vecteur normalise
	 * possede la meme orientation, mais possede une longeur unitaire. Si le module
	 * du vecteur est nul, le vecteur normalise sera le vecteur nul (0.0, 0.0).
	 * 
	 * @param v Le vecteur
	 * @return Le vecteur normalis�.
	 * @throws Exception Si le vecteur ne peut pas etre normalise etant trop petit
	 *                   ou de longueur nulle.
	 */
//	Gabriel Maurice
	public static Vecteur3D normalise(Vecteur3D v) throws Exception {
		return v.normalise();
	}

	/**
	 * Methode pour effectuer le produit scalaire du vecteur courant avec un autre
	 * vecteur.
	 * 
	 * @param v L'autre vecteur.
	 * @return Le produit scalaire entre les deux vecteurs.
	 */
//	Gabriel Maurice
	public double prodScalaire(Vecteur3D v) {
		return (x * v.x + y * v.y + z * v.z);
	}

	/**
	 * Methode de classe pour effectuer le produit scalaire entre deux vecteurs
	 * quelconques.
	 * 
	 * @param v1 Le premier vecteur
	 * @param v2 Le deuxieme vecteur
	 * @return Le produit scalaire entre les deux vecteurs.
	 */
//	Gabriel Maurice
	public static double prodScalaire(Vecteur3D v1, Vecteur3D v2) {
		return (v1.prodScalaire(v2));
	}

	/**
	 * Convertit un vecteur en coordonnées cartésiennes à partir de son module et de
	 * ses angles horizontaux et verticaux.
	 *
	 * @param module    Le module du vecteur.
	 * @param angleHor  L'angle horizontal du vecteur en degrés.
	 * @param angleVert L'angle vertical du vecteur en degrés.
	 * @return Un vecteur 3D représentant les composantes x, y et z calculées.
	 */
//	Gabriel Maurice
	public static Vecteur3D conversionEnComposants(double module, double angleHor, double angleVert) {
		return new Vecteur3D(module * Math.sin(Math.toRadians(angleHor)) * Math.cos(Math.toRadians(angleVert)),
				module * Math.cos(Math.toRadians(angleHor)) * Math.cos(Math.toRadians(angleVert)),
				module * Math.sin(Math.toRadians(angleVert)));
	}

	/**
	 * M�thode pour effectuer le <b>produit vectoriel</b> avec un autre vecteur v.
	 * <p>
	 * Cette version du produit vectoriel est impl�ment� en respectant la <b> r�gle
	 * de la main droite </b>. Il est important de rappeler que le produit vectoriel
	 * est <b> anticommutatif </b> (AxB = -BxA) et que l'ordre des vecteurs doit
	 * �tre ad�quat pour obtenir le r�sultat d�sir�. Si l'un des deux vecteurs est
	 * <b> nul </b> ou les deux vecteurs sont <b> parall�les </b>, le r�sultat sera
	 * un <b> vecteur nul </b>.
	 * </p>
	 * 
	 * @param v Le second vecteur dans le produit vectoriel.
	 * @return Le r�sultat du produit vectoriel.
	 */
//	Gabriel Maurice
	public Vecteur3D prodVectoriel(Vecteur3D v) {

		return new Vecteur3D(y * v.z - z * v.y, -1 * (x * v.z - z * v.x), x * v.y - y * v.x);
	}

}// fin classe Vecteur
