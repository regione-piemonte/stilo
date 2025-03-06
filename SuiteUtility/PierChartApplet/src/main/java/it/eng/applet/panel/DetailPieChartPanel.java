package it.eng.applet.panel;

import it.eng.applet.PieChartApplet;
import it.eng.applet.action.LeftClickAction;
import it.eng.applet.action.impl.ThirdLevelLeftClickAction;
import it.eng.applet.dataset.PieChartBuilder;
import it.eng.applet.dataset.PieChartProperty;
import it.eng.applet.menu.impl.BackMenuItem;
import it.eng.applet.menu.impl.DetailMenuItem;
import it.eng.applet.menu.impl.LEVEL_BACK;
import it.eng.applet.menu.impl.ThirdLevelMenuItem;
import it.eng.applet.panel.button.BackButton;
import it.eng.generator.url.pie.CustomPieUrlGenerator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Crea un JPanel con all'interno un {@link PieChartPanel} ed un bottone 
 * per tornare al grafico principale
 * @author Rametta
 *
 */
public class DetailPieChartPanel extends JPanel{

	private static final long serialVersionUID = 8296317907071575731L;
	
	private JPanel instance;
	
	/**
	 * Costruttore per la creazione del JPanel di dettaglio
	 * @param detailUrl L'url da cui recuperare le {@link PieChartProperty}.
	 * 
	 * Alla pressione del tasto Indietro si torna al grafico principale
	 * @param pieChartApplet L'applet id partenza
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public DetailPieChartPanel(String detailUrl, final PieChartApplet pieChartApplet, boolean generateUrl) throws MalformedURLException, IOException{
		//Salvo l'istanza
		instance = this;
		//Layout verticale di tipo box
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Creo il bottone
		//Creo il panel di dettaglio
		PieChartPanel detailPanel = new PieChartPanel(PieChartBuilder.retrieveDataset(detailUrl), pieChartApplet, generateUrl,
				new ThirdLevelLeftClickAction(pieChartApplet), new CustomPieUrlGenerator(pieChartApplet.getActualLevel()+1+""),
				new BackMenuItem(pieChartApplet, LEVEL_BACK.MAIN), new ThirdLevelMenuItem(pieChartApplet));
		//Aggiungo il panel
		add(detailPanel);
		//Aggiungo il bottone
		add(new BackButton(pieChartApplet, LEVEL_BACK.MAIN), BorderLayout.CENTER);
	}
}
