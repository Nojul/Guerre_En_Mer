package aproposetaide;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * Panel qui affiche dans des onglets de l'information sur les auteurs et sur
 * les sources
 * 
 * @author Caroline Houle
 * @author Théo Fourteau
 */
public class PanelAPropos extends JPanel {
	/** Indice unique de sérialisation **/
	private static final long serialVersionUID = -3110011146750233775L;

	/**
	 * Création du panel avec fenêtre des auteurs et des sources Source: Utilisation
	 * de ChatGPT pour convertir un texte sous forme de HTML, donc pas de
	 * modification une fois que ChatGPT a généré le code.
	 * 
	 * Les sources s'affichent ensuite dans un JScrollPane.
	 */
	// Théo Fourteau
	public PanelAPropos() {
		setLayout(null);
		// noter: aucun layout précisé: le conteneur à onglets prendra la largeur de la
		// plus longue ligne de texte
		JTabbedPane tabOnglets = new JTabbedPane(JTabbedPane.TOP);
		tabOnglets.setBounds(10, 10, 900, 517);
		add(tabOnglets);

		JPanel pnlAuteurs = new JPanel();
		tabOnglets.addTab("Auteurs", null, pnlAuteurs, null);
		pnlAuteurs.setLayout(null);

		JLabel lblAuteurs = new JLabel("<html>" + "Équipe 33 " + "<br>" + "<br>Théo Fourteau" + "<br>Noé Julien"
				+ "<br>Gabriel Maurice" + "<br>Cédric Thai" + "<br><br>Cours 420-SCD"
				+ "<br>Intégration des apprentissages en SIM" + "<br>Hiver 2024</html>");
		lblAuteurs.setBounds(6, 5, 700, 352);
		lblAuteurs.setVerticalAlignment(SwingConstants.TOP);
		pnlAuteurs.add(lblAuteurs);

		JPanel pnlSources = new JPanel();
		tabOnglets.addTab("Sources", null, pnlSources, null);
		

		JLabel lblSources = new JLabel( "<html>" + "<style>" + "li { margin-bottom: 5px; }"
				+ "a { color: #0066cc; text-decoration: none; }" + "</style>" + "<ul>"
				+ "<li><b>Nature Sounds - Topic.</b> (2020, August 27). Ocean ambience with waves and seagulls [Video]. YouTube. "
				+ "<a href='https://www.youtube.com/watch?v=qvNKGhfgZPw'>Watch video</a></li>"
				+ "<li><b>Pixabay.</b> (2023b, March 16). Missile Blast 2 | Royalty-free music. Pixabay. "
				+ "<a href='https://pixabay.com/sound-effects/missile-blast-2-95177/'>Listen</a></li>"
				+ "<li><b>Pixabay.</b> (2023, March 2). SPLASH (by blaukreuz) | Royalty-free Music. Pixabay. "
				+ "<a href='https://pixabay.com/sound-effects/splash-by-blaukreuz-6261/'>Listen</a></li>"
				+ "<li><b>Comercial Emigor.</b> (2021, January 11). Explosion sound effect [Video]. YouTube. "
				+ "<a href='https://www.youtube.com/watch?v=w2vm1lcQMxk'>Watch video</a></li>"
				+ "<li><b>Son:</b> <a href='https://www.chosic.com/download-audio/58993/'>Download</a>, "
				+ "Thunder Unison by Keys of Moon | <a href='https://soundcloud.com/keysofmoon'>Listen</a>, "
				+ "Music promoted by <a href='https://www.chosic.com/free-music/all/'>Chosic</a>, "
				+ "Creative Commons CC BY 4.0, "
				+ "<a href='https://creativecommons.org/licenses/by/4.0/'>License</a></li>"
				+ "<li><b>Story time with PapaSquatch.</b> (2020, May 4). Body falling in water sound effect | splash in water sound effect [Video]. YouTube. "
				+ "<a href='https://www.youtube.com/watch?v=fpcaxXY5aIM'>Watch video</a></li>"
				+ "<li><b>Moon Texture Images.</b> Browse 248,212 stock photos, vectors, and video. (n.d.). Adobe Stock. "
				+ "<a href='https://stock.adobe.com/ca/search?k=moon+texture&asset_id=309045980'>Adobe Stock</a></li>"
				+ "<li><b>Mars Texture.</b> (2019, May 2). "
				+ "<a href='https://www.textures4photoshop.com/tex/stone-and-rock/mars-texture.aspx'>Mars Texture</a></li>"
				+ "<li><b>PSDDude.</b> (2018, July 6). Free Water Texture images (High Quality). Photoshop Tutorials and Resources | PSDDude. "
				+ "<a href='https://www.psd-dude.com/tutorials/resources/free-water-textures-and-backgrounds.aspx'>PSDDude</a></li>"
				+ "<li><b>Phom-In, P.</b> (2019, June 20). Night sky with stars sparkling on black background. iStock. "
				+ "<a href='https://www.istockphoto.com/photo/night-sky-with-stars-sparkling-on-black-background-gm1156836845-315443351'>iStock</a></li>"
				+ "<li><b>Type 31 Frigate 3D - TurboSquid 1658142.</b> (n.d.). <a href='https://www.turbosquid.com/3d-models/type-31-frigate-1658142'>TurboSquid</a></li>"
				+ "<li><b>3D Arafura Class OPV Vessel Ship - TurboSquid 1486005.</b> (n.d.). <a href='https://www.turbosquid.com/3d-models/arafura-class-opv-1486005'>TurboSquid</a></li>"
				+ "<li><b>AI Character Generator (free, no sign-up, unlimited).</b> (n.d.). <a href='https://perchance.org/ai-character-generator'>perchance.org</a></li>"
				+ "<li><b>Vimare, T.</b> (2016, July 15). Plan d’eau sous ciel bleu et blanc le jour. Unsplash. "
				+ "<a href='https://unsplash.com/fr/photos/plan-deau-sous-ciel-bleu-et-blanc-le-jour-IZ01rjX0XQA'>Unsplash</a></li>"
				+ "<li><b>Free Photo | White cloud on blue sky and sea.</b> (2019, February 19). Freepik. "
				+ "<a href='https://www.freepik.com/free-photo/white-cloud-blue-sky-sea_3962982.htm#fromView=search&page=1&position=0&uuid=feb0e960-7f85-4beb-ad9f-750c9ba5259e'>Freepik</a></li>"
				+ "<li><b>Tir aérien aérien d'une mer bleue ondulée - parfait pour mobile | Photo Gratuite.</b> (2021, June 12). Freepik. "
				+ "<a href='https://fr.freepik.com/photos-gratuite/tir-aerien-aerien-mer-bleue-ondulee-parfait-pour-mobile_15036303.htm#from_view=detail_alsolike'>Freepik</a></li>"
				+ "<li><b>Free Photo | Metallic texture with grunge style metal plates and screws.</b> (2020, June 1). Freepik. "
				+ "<a href='https://www.freepik.com/free-photo/metallic-texture-with-grunge-style-metal-plates-screws_8533251.htm#fromView=search&page=1&position=0&uuid=e7c24e1e-96d2-4cc1-9871-5b44a6732b8a'>Freepik</a></li>"
				+ "<li><b>Free Vector | Usb types port plug in realistic connectors set of isolated consumer wire connection.</b> (2021, July 20). Freepik. "
				+ "<a href='https://www.freepik.com/free-vector/usb-types-port-plug-realistic-connectors-set-isolated-consumer-wire-connection_16217904.htm'>Freepik</a></li>"
				+ "<li><b>CMS consoles.</b> (n.d.). Lockheed Martin. "
				+ "<a href='https://www.lockheedmartin.com/en-ca/index.html'>Lockheed Martin</a></li>"
				+ "<li><b>Free Photo | Gray smooth textured design.</b> (2021, January 19). Freepik. "
				+ "<a href='https://www.freepik.com/free-photo/gray-smooth-textured-design_12191027.htm#fromView=search&page=1&position=6&uuid=6519e7ac-ef9e-49df-a1e3-76843364431d'>Freepik</a></li>"
				+ "<li><b>Free Photo | Metal texture background with rivets.</b> (2018, June 1). Freepik. "
				+ "<a href='https://www.freepik.com/free-photo/metal-texture-background-with-rivets_2352388.htm#fromView=search&page=1&position=48&uuid=ddb2cd70-bbcb-4883-bfb8-149d2cd34870'>Freepik</a></li>"
				+ "<li><b>File:Navy Cross.png - Wikimedia Commons.</b> (2014, August 18). "
				+ "<a href='https://commons.wikimedia.org/w/index.php?curid=34819920'>Wikimedia Commons</a></li>"
				+ "</ul>" + "</html>");
		
		

		
		JScrollPane spSources = new JScrollPane();
		spSources.setPreferredSize(new Dimension(700, 352)); // Définir une taille initiale, mais le JScrollPane ajustera sa taille en fonction de son contenu
		spSources.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spSources.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		spSources.setViewportView(lblSources);
		spSources.setWheelScrollingEnabled(true);

		pnlSources.add(spSources);
	
	}
}
