package it.eng.hybrid.module.pieChart.ui;

import it.eng.hybrid.module.pieChart.dataset.BarChartBuilder;
import it.eng.hybrid.module.pieChart.url.CustomBarUrlGenerator;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ThirdLevelChartPanel extends JPanel {

	private static final long serialVersionUID = 3882896618064263304L;
	private ThirdLevelChartPanel instance;
	
	public ThirdLevelChartPanel(String detailUrl, final PieChartApplication pieChartAppl, boolean generateUrl) throws MalformedURLException, IOException{
		//Salvo l'istanza
		instance = this;
		//Layout verticale di tipo box
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Creo il bottone
		//Creo il panel di dettaglio
		
		BarChartPanel detailPanel = new BarChartPanel(BarChartBuilder.retrieveDataset(detailUrl), pieChartAppl, 
				new ThirdLevelLeftClickAction(pieChartAppl), new CustomBarUrlGenerator(pieChartAppl.getActualLevel()+1+""), 
				generateUrl,
				new DetailMenuItem(pieChartAppl),
		new BackMenuItem(pieChartAppl, LEVEL_BACK.DETAIL));
		
		//Aggiungo il panel
		add(detailPanel);
		//Aggiungo il bottone
		add(new BackButton(pieChartAppl, LEVEL_BACK.DETAIL), BorderLayout.CENTER);
	}
}
