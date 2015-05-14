package cad.bataillenavale.persistance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.map.Boat;
import cad.bataillenavale.model.map.Maritime;

public class XMLDAOEpoque implements EpoqueDAO {

	private static EpoqueDAO instance = null;
	private final String path = "stockage/fich_epoque.xml";
	private Document document;
	private Element racine;

	/**
	 * 
	 */
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

	/**
	 * 
	 * @return
	 */
	public static EpoqueDAO getInstance() {
		if (instance == null)
			synchronized (GameDAO.class) {
				instance = new XMLDAOEpoque();
			}
		return instance;
	}

	/**
	 * 
	 */
	private void ecrireFichConfig() {
		// TODO Auto-generated method stub
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!-- Fichier des epoques -->\n" 
				+ "<epoques>\n"
					+"<epoque>\n"
					+"<name>XXI</name>\n"
						+"<maritime>\n"
							+"<name>Sous_Marin</name>\n"
							+"<length>2</length>\n"
							+"<width>2</width>\n"
							+"<power>3</power>\n"
						+"</maritime>\n"
						+"<maritime>\n"
							+"<name>Bombardier</name>\n"
							+"<length>2</length>\n"
							+"<width>2</width>\n"
							+"<power>2</power>\n"
						+"</maritime>\n"
					+"</epoque>\n"
				
					+"<epoque>\n"
					+"<name>XVI</name>\n"
						+"<maritime>\n"
							+"<name>Croiseur</name>\n"
							+"<length>2</length>\n"
							+"<width>2</width>\n"
							+"<power>1</power>\n"
						+"</maritime>\n"
				+"</epoque>\n"
				+"</epoques>";

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

	@Override
	public List<Epoque> getAllEpoque() {
		// TODO Auto-generated method stub
		List<Element> epoques = racine.getChildren("epoque");
		ListIterator<?> iterator = epoques.listIterator();
		List<Epoque> listEpoques = new ArrayList<Epoque>();
		Epoque epoque = null;
		int length = 0, width = 0, power = 0;
		Maritime maritime;
		while (iterator.hasNext()) {
			Element e = (Element) iterator.next();
			epoque = new Epoque(e.getChildText("name"));
			listEpoques.add(epoque);
			List<?> maritimes = e.getChildren("maritime");
			ListIterator<?> iterator2 = maritimes.listIterator();
			while (iterator2.hasNext()) {
				Element el = (Element) iterator2.next();
				length = Integer.parseInt(el.getChildText("length"));
				width = Integer.parseInt(el.getChildText("width"));
				power = Integer.parseInt(el.getChildText("power"));
				maritime = new Boat(el.getChildText("name"), length, width,
						power);
				epoque.addMaritime(maritime);
			}
		}
		return listEpoques;
	}

	@Override
	public void insertMaritime(String epoqueName, Maritime m) {
		// TODO Auto-generated method stub
		List<Element> epoques = racine.getChildren("epoque");
		ListIterator<?> iterator = epoques.listIterator();
		boolean stop = true;
		while (iterator.hasNext() && stop) {
			Element e = (Element) iterator.next();
			if (e.getChildText("name") ==  epoqueName) {
				Element maritime = new Element("maritime");
				maritime.addContent(new Element("name").setText(m.getName()));
				maritime.addContent(new Element("length").setText(""
						+ m.getLength()));
				maritime.addContent(new Element("width").setText(""
						+ m.getWidth()));
				maritime.addContent(new Element("power").setText(""
						+ m.getPower()));
				e.addContent(maritime);
				/* Création du document */
				XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	
					try {
						sortie.output(document, new FileOutputStream(path));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		
				stop = false;
			}
		}
	}

}
