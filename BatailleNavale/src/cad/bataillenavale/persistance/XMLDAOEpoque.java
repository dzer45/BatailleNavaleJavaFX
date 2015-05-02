package cad.bataillenavale.persistance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cad.bataillenavale.model.epoque.Epoque;

public class XMLDAOEpoque implements EpoqueDAO {

	private static EpoqueDAO instance = null;
	private final String path = "stockage/fich_epoque.xml";
	private Document document;
	private Element racine;

	private XMLDAOEpoque() {

		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			File f = new File(path);
			File dossier = new File("stockage");
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
		racine = document.getRootElement();

	} // XMLDAOEpoque

	public static EpoqueDAO getInstance() {
		if (instance == null) {
			instance = new XMLDAOEpoque();
		}
		return instance;
	}

	private void ecrireFichConfig() {
		// TODO Auto-generated method stub
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!-- Fichier des epoques -->\n" + "<epoques>\n"
				+ "</epoques>";

		try {
			File f = new File("stockage/fich_epoque.xml");
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
	public void insertEpoque(Epoque p) {
		// TODO Auto-generated method stub
		racine = document.getRootElement();
		Element epoque = new Element("epoque");
		epoque.addContent(new Element("name").setText(p.getName()));
		racine.addContent(epoque);
		/* Création du document */
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		try {
			sortie.output(document, new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteEpoque(Epoque p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Epoque findEpoque(Epoque p) {
		// TODO Auto-generated method stub
		return null;
	}

}
