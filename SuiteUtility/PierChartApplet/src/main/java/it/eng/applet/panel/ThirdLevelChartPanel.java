package it.eng.applet.panel;

import it.eng.applet.PieChartApplet;
import it.eng.applet.action.impl.ThirdLevelLeftClickAction;
import it.eng.applet.dataset.BarChartBuilder;
import it.eng.applet.menu.impl.BackMenuItem;
import it.eng.applet.menu.impl.DetailMenuItem;
import it.eng.applet.menu.impl.LEVEL_BACK;
import it.eng.applet.panel.button.BackButton;
import it.eng.generator.url.bar.CustomBarUrlGenerator;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ThirdLevelChartPanel extends JPanel {

	private static final long serialVersionUID = 3882896618064263304L;
	private ThirdLevelChartPanel instance;
	
	public ThirdLevelChartPanel(String detailUrl, final PieChartApplet pieChartApplet, boolean generateUrl) throws MalformedURLException, IOException{
		//Salvo l'istanza
		instance = this;
		//Layout verticale di tipo box
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Creo il bottone
		//Creo il panel di dettaglio
		
		BarChartPanel detailPanel = new BarChartPanel(BarChartBuilder.retrieveDataset(detailUrl), pieChartApplet, 
				new ThirdLevelLeftClickAction(pieChartApplet), new CustomBarUrlGenerator(pieChartApplet.getActualLevel()+1+""), 
				generateUrl,
				new DetailMenuItem(pieChartApplet),
		new BackMenuItem(pieChartApplet, LEVEL_BACK.DETAIL));
		
//		BarChartPanel detailPanel = new BarChartPanel(BarChartBuilder.retrieveDataset(detailUrl), pieChartApplet,
//				new ThirdLevelLeftClickAction(pieChartApplet),
//				new BackMenuItem(pieChartApplet, LEVEL_BACK.DETAIL));
		//Aggiungo il panel
		add(detailPanel);
		//Aggiungo il bottone
		add(new BackButton(pieChartApplet, LEVEL_BACK.DETAIL), BorderLayout.CENTER);
	}
}
