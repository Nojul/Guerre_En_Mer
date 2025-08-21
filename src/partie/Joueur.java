package partie;

/**
 * Classe qui permet de créer des objets de type joueurs définis par plusieurs
 * paramètres
 * 
 * @author Théo Fourteau
 */
public class Joueur {
	/** Nombre initial de bateaux pour les joueurs. */
	private int nbBateauxRestants;
	/** Nombre de tours joués par le joueur */
	private int nbToursJoues;
	/**
	 * Booléen qui détermine si les bateaux du joueur ont été placés et que la
	 * partie peut commencer.
	 */
	private boolean bateauxPlaces;

	/**
	 * Constructeur de la classe
	 */
	// Théo Fourteau
	public Joueur() {
		setNbBateauxRestants(5);
		nbToursJoues = 0; // Aucun tour n'a encore été joué
		setBateauxPlaces(false);

	} // fin constructeur

	/**
	 * Méthode qui permet d'obtenir le nombre de tours joués par le joueur
	 * 
	 * @return nbToursJoues le nombre de tours joués
	 */
	// Théo Fourteau
	public int getNbToursJoues() {
		return nbToursJoues;
	}

	/**
	 * Méthode qui permet de modifier le nombre de tours joués par le joueur
	 * 
	 * @param nbToursJoues le nouveau nombre de tours joués
	 */
	// Théo Fourteau
	public void setNbToursJoues(int nbToursJoues) {
		this.nbToursJoues = nbToursJoues;
	}

	/**
	 * Méthode qui permet de confirmer si oui ou non les bateaux du joueur ont été
	 * placés
	 * 
	 * @return bateauxPlaces la valeur qui définie si oui ou non les bateaux ont été
	 *         placés
	 */
	// Théo Fourteau
	public boolean isBateauxPlaces() {
		return bateauxPlaces;
	}

	/**
	 * Méthode qui permet de modifier la valeur de bateauxPlaces qui détermine si
	 * oui ou non les bateaux du joueurs ont été placés.
	 * 
	 * @param bateauxPlaces la nouvelle valeur qui définie si oui ou non les bateaux
	 *                      ont été placés
	 */
	// Théo Fourteau
	public void setBateauxPlaces(boolean bateauxPlaces) {
		this.bateauxPlaces = bateauxPlaces;
	}

	/**
	 * Méthode qui permet d'obtenir le nombre de bateaux du joueur qui n'ont pas été
	 * coulés par l'adversaire.
	 * 
	 * @return nbBateauxRestants le nombre de bateaux restants
	 */
	// Théo Fourteau
	public int getNbBateauxRestants() {
		return nbBateauxRestants;
	}

	/**
	 * Méthode qui permet de modifier le nombre de bateaux restant au joueur (qui
	 * n'ont pas été coulés)
	 * 
	 * @param nbBateauxRestants le nouveau nombre de bateaux restant.
	 */
	// Théo Fourteau
	public void setNbBateauxRestants(int nbBateauxRestants) {
		this.nbBateauxRestants = nbBateauxRestants;
	}

}
