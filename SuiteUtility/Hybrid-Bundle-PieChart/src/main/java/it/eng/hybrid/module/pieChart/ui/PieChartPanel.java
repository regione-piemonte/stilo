package it.eng.hybrid.module.pieChart.ui;

import it.eng.hybrid.module.pieChart.dataset.PieChartProperty;
import it.eng.hybrid.module.pieChart.generator.CustomLabelGenerator;
import it.eng.hybrid.module.pieChart.generator.CustomLegendGenerator;
import it.eng.hybrid.module.pieChart.generator.CustomPieToolTipGenerator;
import it.eng.hybrid.module.pieChart.parameters.ReportParameterBean;
import it.eng.hybrid.module.pieChart.url.CustomPieUrlGenerator;
import it.eng.hybrid.module.pieChart.url.UrlGeneratorInterface;

import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;

/**
 * Costruisce un {@link ChartPanel} disegnando un {@link JFreeChart} di tipo Pie a
 * partire da un {@link PieChartProperty}.
 * 
 * Rimuove quasi tutti i componenti del menu contestuale lasciando solo il menu 
 * Stampa ed il menu Save As cui viene aggiunto un sotto menu per il salvataggio 
 * in PDF del grafico.
 * 
 * E' prevista anche la possibilità di aggiungere ulteriori menu di tipo {@link PieMenuItem}
 * @author Rametta
 *
 */
public class PieChartPanel extends CustomChartPanel {

	private static final long serialVersionUID = -5731788746581793269L;
	private static Logger mLogger = Logger.getLogger(PieChartPanel.class);

	private JPopupMenu contextMenu;
	private PieMenuItem[] injectedMenu;
	private CustomPieUrlGenerator urlGenerator;

	/**
	 * Costruttore che genera un {@link JFreeChart} di tipo Pie, con le dimensioni prese da {@link ReportParameterBean} 
	 * senza prevedere una funzione per 
	 * il click con tasto sinistro del mouse
	 * @param pPieChartProperty Proprietà del PieChart
	 * @param pieChartAppl L'Applicazione di partenza
	 * @param generateUrl Stabilisce se devono essere generati gli url per ogni riquadro (spicchio) del grafico
	 * @param pPieMenuItem Gli eventuali menu aggiuntivi
	 */
	public PieChartPanel(PieChartProperty pPieChartProperty, final PieChartApplication pieChartAppl, boolean generateUrl, CustomPieUrlGenerator pCustomPieUrlGenerator, PieMenuItem... pPieMenuItem){
		this(pPieChartProperty, pieChartAppl, generateUrl, null, pCustomPieUrlGenerator, pPieMenuItem);
	}

	/**
	 * Costruttore che genera un {@link JFreeChart} di tipo Pie, con le dimensioni prese da {@link ReportParameterBean} 
	 * @param pPieChartProperty Proprietà del PieChart
	 * @param pieChartAppl L'Applicazione di partenza
	 * @param generateUrl Stabilisce se devono essere generati gli url per ogni riquadro (spicchio) del grafico mediante {@link CustomPieUrlGenerator}
	 * @param pLeftClickAction Azione da effettuare alla pressione del tasto sinistro del mouse
	 * @param pPieMenuItem Gli eventuali menu aggiuntivi
	 */
	public PieChartPanel(PieChartProperty pPieChartProperty, final PieChartApplication pieChartAppl, boolean generateUrl, final LeftClickAction pLeftClickAction,
			CustomPieUrlGenerator pCustomPieUrlGenerator,
			PieMenuItem... pPieMenuItem){
		//Crea il Panel con il PieChart relativo
		super(ChartFactory.createPieChart(pPieChartProperty.getTitle(),
				pPieChartProperty.getDataset(),            // data
				pPieChartProperty.getDataset().getItemCount()>5?false:true,              // no legend
				true,               // tooltips
				generateUrl               // no URL generation
		), pieChartAppl,  generateUrl,   pLeftClickAction, pCustomPieUrlGenerator,
		pPieMenuItem);
	}

	@Override
	public void setUrlLabelGenerator(boolean generateUrl, UrlGeneratorInterface pUrlGeneratorInterface) {
		urlGenerator = (CustomPieUrlGenerator)pUrlGeneratorInterface;
		//Recupero il plot
		PiePlot plot = (PiePlot) getChart().getPlot();
		//Se devo generare gli url
		if (generateUrl){
			//Setto il mio generatore
			plot.setURLGenerator(urlGenerator);
		}
		plot.setLabelGenerator(new CustomLabelGenerator());
		plot.setLegendLabelGenerator(new CustomLegendGenerator());
		plot.setToolTipGenerator(new CustomPieToolTipGenerator());
//		plot.setLegendLabelURLGenerator(new CustomPieUrlGenerator("1"));
	}

}
