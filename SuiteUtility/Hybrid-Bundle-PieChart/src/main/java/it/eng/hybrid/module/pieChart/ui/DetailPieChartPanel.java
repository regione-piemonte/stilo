package it.eng.hybrid.module.pieChart.ui;

import it.eng.hybrid.module.pieChart.dataset.PieChartBuilder;
import it.eng.hybrid.module.pieChart.url.CustomPieUrlGenerator;

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
	 * @param pieChartAppl L'applicazione di partenza
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public DetailPieChartPanel(String detailUrl, final PieChartApplication pieChartAppl, boolean generateUrl) throws MalformedURLException, IOException{
		//Salvo l'istanza
		instance = this;
		//Layout verticale di tipo box
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Creo il bottone
		//Creo il panel di dettaglio
		PieChartPanel detailPanel = new PieChartPanel(PieChartBuilder.retrieveDataset(detailUrl), pieChartAppl, generateUrl,
				new ThirdLevelLeftClickAction(pieChartAppl), new CustomPieUrlGenerator(pieChartAppl.getActualLevel()+1+""),
				new BackMenuItem(pieChartAppl, LEVEL_BACK.MAIN), new ThirdLevelMenuItem(pieChartAppl));
		//Aggiungo il panel
		add(detailPanel);
		//Aggiungo il bottone
		add(new BackButton(pieChartAppl, LEVEL_BACK.MAIN), BorderLayout.CENTER);
	}
}
