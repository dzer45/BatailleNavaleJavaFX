package cad.bataillenavale.persistance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.epoque.EpoqueManager;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Boat;
import cad.bataillenavale.model.map.Case;
import cad.bataillenavale.model.map.Case.State;
import cad.bataillenavale.model.map.EmptyCase;
import cad.bataillenavale.model.map.Map;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.model.map.MaritimeCase;
import cad.bataillenavale.model.player.Difficult;
import cad.bataillenavale.model.player.Easy;
import cad.bataillenavale.model.player.Hard;
import cad.bataillenavale.model.player.IA;
import cad.bataillenavale.model.player.Medium;
import cad.bataillenavale.model.player.Player;

public class XMLDAOGame implements GameDAO {

	private volatile static GameDAO instance = null;
	private final String path = "stockage/save.xml";
	private Document document;
	private Element racine;
	private File f = new File(path);

	/**
	 * 
	 */
	private XMLDAOGame() {

		SAXBuilder sxb = new SAXBuilder();

		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML

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
		f.delete();
		ecrireFichConfig();
		Element curentPlayer = new Element("currentplayer").setText(p
				.getCurrentPlayer().getClass().getSimpleName());
		curentPlayer.addContent(new Element("name"));
		racine.addContent(curentPlayer);

		Element curentEpoque = new Element("currentepoque").setText(p
				.getCurrentEpoque().getName());
		racine.addContent(curentEpoque);

		Element length = new Element("length").setText(p.getLength() + "");
		racine.addContent(length);

		Element dif = new Element("dif").setText(p.getDif().getClass()
				.getSimpleName());
		racine.addContent(dif);

		Element player = new Element("player");
		racine.addContent(player);

		for (int i = 0; i < p.getLength(); i++) {
			for (int j = 0; j < p.getLength(); j++) {
				Element n = new Element(p.getMapPlayer().getCase(i, j)
						.getClass().getSimpleName()
						+ "");
				n.addContent(new Element("i").setText(i + ""));
				n.addContent(new Element("j").setText(j + ""));
				n.addContent(new Element("state").setText(p.getMapPlayer()
						.getCase(i, j).getState()
						+ ""));
				n.addContent(new Element("nbMaritimeReach").setText(p
						.getMapPlayer().getCase(i, j).getReachable()
						+ ""));
				if (p.getMapPlayer().getCase(i, j).getClass().getSimpleName()
						.equals("MaritimeCase")) {
					MaritimeCase mcas = (MaritimeCase) p.getMapPlayer()
							.getCase(i, j);
					n.addContent(new Element("maritime").setText(mcas
							.getMaritime().getName()));
				}
				player.addContent(n);
			}
		}

		Element ia = new Element("ia");
		racine.addContent(ia);
		for (int i = 0; i < p.getLength(); i++) {
			for (int j = 0; j < p.getLength(); j++) {
				Element n = new Element(p.getMapIA().getCase(i, j).getClass()
						.getSimpleName()
						+ "");
				n.addContent(new Element("i").setText(i + ""));
				n.addContent(new Element("j").setText(j + ""));
				n.addContent(new Element("state").setText(p.getMapIA()
						.getCase(i, j).getState()
						+ ""));
				n.addContent(new Element("nbMaritimeReach").setText(p
						.getMapIA().getCase(i, j).getReachable()
						+ ""));
				if (p.getMapIA().getCase(i, j).getClass().getSimpleName()
						.equals("MaritimeCase")) {
					MaritimeCase mcas = (MaritimeCase) p.getMapIA().getCase(i,
							j);
					n.addContent(new Element("maritime").setText(mcas
							.getMaritime().getName()));
				}
				ia.addContent(n);
			}
		}

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
	public BatailleNavale restore() {
		// TODO Auto-generated method stub
		BatailleNavale bataille = new BatailleNavale();
		bataille.getEpoques();
		String currentE = racine.getChildText("currentepoque");
		Epoque currentEpoque = EpoqueManager.getInstance().getEpoque(currentE);

		bataille.setCurrentEpoque(currentEpoque);
		int length = Integer.parseInt(racine.getChildText("length"));
		bataille.setLength(length);

		List<Element> player = racine.getChildren("player");
		ListIterator<?> iteratorP = player.listIterator();
		Map mapPlayer = new Map(length);
		boolean vrai = true;
		while (iteratorP.hasNext()) {
			Element e = (Element) iteratorP.next();
			List<Element> el = e.getChildren();
			for (int k = 0; k < el.size(); k++) {
				Element ele = el.get(k);

				int i = Integer.parseInt(ele.getChildText("i"));
				int j = Integer.parseInt(ele.getChildText("j"));

				String genre = ele.getName();
				String state = ele.getChildText("state");

				Case cas = null;
				switch (genre) {
				case "MaritimeCase":
					Boat bateau = (Boat) EpoqueManager.getInstance()
							.getEpoque(currentE)
							.getMaritime(ele.getChildText("maritime"))
							.doClone();
					cas = new MaritimeCase(mapPlayer, bateau);
					try {
						mapPlayer.addMaritime(i, j, bateau);
					} catch (MapException e1) {
					}
					mapPlayer.getCases()[i][j] = cas;
					break;
				case "EmptyCase":
					cas = new EmptyCase(mapPlayer);
					break;
				}

				switch (state) {
				case "NOTPLAYED":
					cas.setState(State.NOTPLAYED);
					break;
				case "TOUCHED":
					cas.setState(State.TOUCHED);
					break;
				case "MISSED":
					cas.setState(State.MISSED);
					break;
				}

				mapPlayer.getCases()[i][j] = cas;
			}
		}
		List<Element> ia = racine.getChildren("ia");
		ListIterator<?> iteratorIA = ia.listIterator();
		Map mapIA = new Map(length);
		vrai = true;
		while (iteratorIA.hasNext()) {
			Element e = (Element) iteratorIA.next();
			List<Element> el = e.getChildren();
			for (int k = 0; k < el.size(); k++) {
				Element ele = el.get(k);

				int i = Integer.parseInt(ele.getChildText("i"));
				int j = Integer.parseInt(ele.getChildText("j"));

				String genre = ele.getName();
				Case cas = null;
				String state = ele.getChildText("state");
				switch (genre) {
				case "MaritimeCase":
					Boat bateau = (Boat) EpoqueManager.getInstance()
							.getEpoque(currentE)
							.getMaritime(ele.getChildText("maritime"))
							.doClone();
					cas = new MaritimeCase(mapIA, bateau);
					try {
						mapIA.addMaritime(i, j, bateau);
					} catch (MapException e1) {
						// TODO Auto-generated catch block
					}
					break;
				case "EmptyCase":
					cas = new EmptyCase(mapIA);
					break;
				}

				switch (state) {
				case "NOTPLAYED":
					cas.setState(State.NOTPLAYED);
					break;
				case "TOUCHED":
					cas.setState(State.TOUCHED);
					break;
				case "MISSED":
					cas.setState(State.MISSED);
					break;
				}
				mapIA.getCases()[i][j] = cas;
			}
		}

		String current = racine.getChildTextNormalize("currentplayer");
		Player currentPlayer = null;
		switch (current) {
		case "Human":
			currentPlayer = bataille.getPlayer();
			break;
		case "IA":
			currentPlayer = bataille.getIA();
			break;
		}
		bataille.setCurrentPlayer(currentPlayer);

		String difficult = racine.getChildText("dif");
		Difficult dif = null;
		switch (difficult) {
		case "Easy":
			dif = new Easy(bataille);
			break;
		case "Medium":
			dif = new Medium(bataille);
			break;
		case "Hard":
			dif = new Hard(bataille);
			break;
		}

		bataille.getIA().setDifficult(dif);
		bataille.setDif(dif);

		bataille.getPlayer().setMap(mapPlayer);
		bataille.getIA().setMap(mapIA);


		bataille.getPlayer().setOpponent(bataille.getIA());
		bataille.getIA().setOpponent(bataille.getPlayer());
		
		return bataille;
	}

}
