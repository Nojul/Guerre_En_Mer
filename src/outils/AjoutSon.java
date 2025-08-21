package outils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import application.FenetreTerrainJeu;

/**
 * Classe outil pour pouvoir jouer differents type de son
 * 
 * @author Noé Julien
 */
public class AjoutSon {
	
	/** Clip static pour le son */
	private static Clip clip;
	/** Clip static pour le son de l'océan */
	private static Clip clipOcean;
	/** Clip static pour la musique principal */
	private static Clip clipPrincipal;
	/** Indique si la musique principal est à activé ou non */
	private static boolean principal;
	/** Valeur flottante static pour le volume */
	private static float volume;
	
	/** Constructeur
	 * Pour la javadoc
	 */
	// Noé Julien 
	public AjoutSon() {}

	/**
	 * Joue le son à partir d'un fichier spécifié.
	 * 
	 * @param fileName Le nom du fichier audio
	 */
	// Noé Julien
	public static void joueSon(String fileName) {
		try {
			clip = AudioSystem.getClip();
			java.net.URL url = AjoutSon.class.getClassLoader().getResource(fileName);
			if (url != null) {
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
				clip.open(inputStream);
				if (volume != 0) {
					clip.start();
				}
			}
		} catch (Exception e) {
			System.err.println("Error playing sound: " + e.getMessage());
		}
	}

	/**
	 * Joue le son de l'ocean
	 */
	// Noé Julien
	public static void sonOcean() {
		try {
			clipOcean = AudioSystem.getClip();
			java.net.URL url = AjoutSon.class.getClassLoader().getResource("OceanSound.wav");
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
			clipOcean.open(inputStream);
			clipOcean.start();
			checkVolume();
			
		} catch (Exception e) {
			System.err.println("Error playing sound: " + e.getMessage());
		}
	}

	/**
	 * Arrête le son de l'ocean
	 */
	// Noé Julien
	public static void stopSonOcean() {
		
		if(clipOcean != null) {
		clipOcean.stop();
		}
	}

	/**
	 * Joue la musique principale du jeu
	 */
	// Noé Julien
	public static void sonPrincipal() {
		if (principal) {
			try {
				clipPrincipal = AudioSystem.getClip();
				java.net.URL url = AjoutSon.class.getClassLoader().getResource("ThunderMusic.wav");
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
				clipPrincipal.open(inputStream);
				checkVolume();
				clipPrincipal.start();
			} catch (Exception e) {
				System.err.println("Error playing sound: " + e.getMessage());
			}
		}
	}

	/**
	 * Arrête la lecture de la musique principale
	 */
	// Noé Julien
	public static void stopPrincipal() {
		System.out.println("stopPrincipal");
		clipPrincipal.stop();
	}

	/**
	 * Définit si le son principal doit être joué ou non.
	 * 
	 * @param principal valeur indiquant si le son doit être joué.
	 */
	// Noé Julien
	public static void setPrincipal(boolean principal) {
		AjoutSon.principal = principal;
	}

	/**
	 * Vérifie et ajuste le volume en fonction du bouton de son
	 * 
	 */
	// Noé Julien
	public static void checkVolume() {
		boolean b = BoutonChangementSon.isSonAllume();
		if (b) {
			volume = 1;
		} else {
			volume = 0;
		}

		if (clipOcean != null) {
			modifierVolume(clipOcean, volume);
		}
		if (clipPrincipal != null) {
			modifierVolume(clipPrincipal, volume);
		}
	}

	/**
	 * Méthode qui permet de changer le volume du son aux clip
	 * 
	 * @param clip le clip auquel le son va être changer
	 * @param vol  le volume désiré, entre 0 et 1
	 */
	// Noé Julien
	private static void modifierVolume(Clip clip, double vol) {
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(20f * (float) Math.log10((float) vol));

	}

	/**
	 * Regarde si le son principale(musique) joue
	 * 
	 * @return vrai si elle joue, sinon faux
	 */
	// Noé Julien
	public static boolean principaleJoue() {
		if (clipPrincipal != null) {
			return clipPrincipal.isRunning();
		} else {
			return false;
		}
	}
}
