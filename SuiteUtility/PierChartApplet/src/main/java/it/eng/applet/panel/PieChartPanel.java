package it.eng.applet.panel;

import it.eng.applet.PieChartApplet;
import it.eng.applet.action.LeftClickAction;
import it.eng.applet.dataset.PieChartProperty;
import it.eng.applet.menu.PieMenuItem;
import it.eng.applet.menu.impl.PdfMenuItem;
import it.eng.generator.label.CustomLabelGenerator;
import it.eng.generator.legend.CustomLegendGenerator;
import it.eng.generator.tooltip.pie.CustomPieToolTipGenerator;
import it.eng.generator.url.UrlGeneratorInterface;
import it.eng.generator.url.pie.CustomPieUrlGenerator;
import it.eng.parameter.ReportParameterBean;

import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.ui.RectangleInsets;

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
	 * @param pieChartApplet L'Applet di partenza
	 * @param generateUrl Stabilisce se devono essere generati gli url per ogni riquadro (spicchio) del grafico
	 * @param pPieMenuItem Gli eventuali menu aggiuntivi
	 */
	public PieChartPanel(PieChartProperty pPieChartProperty, final PieChartApplet pieChartApplet, boolean generateUrl, CustomPieUrlGenerator pCustomPieUrlGenerator, PieMenuItem... pPieMenuItem){
		this(pPieChartProperty, pieChartApplet, generateUrl, null, pCustomPieUrlGenerator, pPieMenuItem);
	}

	/**
	 * Costruttore che genera un {@link JFreeChart} di tipo Pie, con le dimensioni prese da {@link ReportParameterBean} 
	 * @param pPieChartProperty Proprietà del PieChart
	 * @param pieChartApplet L'Applet di partenza
	 * @param generateUrl Stabilisce se devono essere generati gli url per ogni riquadro (spicchio) del grafico mediante {@link CustomPieUrlGenerator}
	 * @param pLeftClickAction Azione da effettuare alla pressione del tasto sinistro del mouse
	 * @param pPieMenuItem Gli eventuali menu aggiuntivi
	 */
	public PieChartPanel(PieChartProperty pPieChartProperty, final PieChartApplet pieChartApplet, boolean generateUrl, final LeftClickAction pLeftClickAction,
			CustomPieUrlGenerator pCustomPieUrlGenerator,
			PieMenuItem... pPieMenuItem){
		//Crea il Panel con il PieChart relativo
		super(ChartFactory.createPieChart(pPieChartProperty.getTitle(),
				pPieChartProperty.getDataset(),            // data
				pPieChartProperty.getDataset().getItemCount()>5?false:true,              // no legend
				true,               // tooltips
				generateUrl               // no URL generation
		), pieChartApplet,  generateUrl,   pLeftClickAction, pCustomPieUrlGenerator,
		pPieMenuItem);
//		//Salvo i menu aggiuntivi
//		injectedMenu = pPieMenuItem;
//		//Aggiusto il menu
//		adjustMenu(pieChartApplet);
//		//Recupero il plot
//		PiePlot plot = (PiePlot) getChart().getPlot();
//		//Se devo generare gli url
//		if (generateUrl){
//			//Setto il mio generatore
//			plot.setURLGenerator(new CustomPieUrlGenerator());
//		}
//		plot.setLabelGenerator(new CustomLabelGenerator());
//		setMouseWheelEnabled(true);
//		//Setto i margini
//		getChart().setPadding(new RectangleInsets(4, 8, 2, 2));
//		//Setto nell'applet l'attuale grafico
//		pieChartApplet.setActualChart(getChart());
//		//Setto la dimensione come da parametri
//		setPreferredSize(new Dimension(PieChartApplet.getParameters().getWidth(), PieChartApplet.getParameters().getHeight()));
//		//Setto il mouse listener
//		addChartMouseListener(new ChartMouseListener() {
//			public void chartMouseMoved(ChartMouseEvent paramChartMouseEvent) {
//			}
//			public void chartMouseClicked(ChartMouseEvent paramChartMouseEvent) {
//				//se ho cliccato con il tasto sinistro
//				if (paramChartMouseEvent.getTrigger().getButton() == 1){
//					//Se ho previsto una azione
//					if (pLeftClickAction!=null){
//						//La eseguo
//						pLeftClickAction.doAfterClick(paramChartMouseEvent);
//					}
//				} else {
//					//Se ho cliccato con il tasto destro
//					if (paramChartMouseEvent.getTrigger().getButton() == 3){
//						//Ciclo i vari menu item aggiuntivi
//						for (PieMenuItem pPieMenuItem : injectedMenu){
//							//Se li devo mostrare
//							if (pPieMenuItem.showIf(paramChartMouseEvent)){
//								//Li aggiungo
//								contextMenu.add(pPieMenuItem);
//							} else {
//								//altrimenti li elimino
//								contextMenu.remove(pPieMenuItem);
//							}
//						}
//						//Aggiusto graficamente il menu
//						contextMenu.pack();
//					}
//				}
//			}
//		});
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

//	/**
//	 * Ha il compito di rimuovere tutti i menu e lasciare solo ed esclusivamente
//	 * il il SaveAs cui viene aggiunto un sotto menu PDF di tipo {@link PdfMenuItem}, ed il menu stampa
//	 * @param pieChartApplet
//	 */
//	protected void adjustMenu(PieChartApplet pieChartApplet) {
//		//Recupero il menu
//		JPopupMenu lJPopupMenu = getPopupMenu();
//		//Rimuovo i sotto menu che non interessano
//		lJPopupMenu.remove(0);
//		lJPopupMenu.remove(0);
//		lJPopupMenu.remove(0);
//		lJPopupMenu.remove(3);
//		lJPopupMenu.remove(3);
//		lJPopupMenu.remove(3);
//		lJPopupMenu.remove(3);
//		lJPopupMenu.remove(3);
//		JMenu lJMenu = (JMenu)lJPopupMenu.getComponent(0);
//		//Creo il menu pdf
//		PdfMenuItem lPdfMenuItem = new PdfMenuItem(pieChartApplet);
//		//Lo aggiungo
//		lJMenu.add(lPdfMenuItem);
//		contextMenu = lJPopupMenu;
//	}
}
