package cad.bataillenavale.persistance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import cad.bataillenavale.model.BatailleNavale;

public class XMLDAOGame implements GameDAO{


	private volatile static GameDAO instance = null;
	private final String path = "stockage/save.xml";
	private Document document;
	
	/**
	 * 
	 */
	private XMLDAOGame() {

		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			File f = new File(path);
			File dossier = new File("save");
			if (!dossier.exists()) {
				dossier.mkdir();
			}
			if (!f.exists()) {
				this.ecrireFichConfig();
			}
			document = sxb.build(f);

		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}

	} // XMLDAOEpoque


	/**
	 * 
	 * @return
	 */
	public static GameDAO getInstance() {
		if (instance == null)
			 synchronized (GameDAO.class) {
				 instance = new XMLDAOGame();
			}
			 return instance;
	}

	/**
	 * 
	 */
	private void ecrireFichConfig() {
		// TODO Auto-generated method stub
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!-- Sauvegarde -->\n" + "<bataillenavale>\n"

				+ "</bataillenavale>";

		try {
			File f = new File("stockage/save.xml");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(s);
			bw.newLine();
			bw.flush();
			bw.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	
	@Override
	public void save(BatailleNavale p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BatailleNavale restore(BatailleNavale p) {
		// TODO Auto-generated method stub
		return null;
	}

}
