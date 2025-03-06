package it.eng.hybrid.module.pieChart.ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.RectangleInsets;

import it.eng.hybrid.module.pieChart.url.UrlGeneratorInterface;

public abstract class CustomChartPanel extends ChartPanel {

	private static final long serialVersionUID = -5731788746581793269L;
	private static Logger mLogger = Logger.getLogger(CustomChartPanel.class);

	private JPopupMenu contextMenu;
	private PieMenuItem[] injectedMenu;

	public CustomChartPanel(JFreeChart chart, final PieChartApplication pieChartAppl, boolean generateUrl, final LeftClickAction pLeftClickAction,
			UrlGeneratorInterface pUrlGenerator, PieMenuItem... pPieMenuItem) {
		super(chart);
		TextTitle lTextTitle = getChart().getTitle();
		Font lFont = lTextTitle.getFont();
		int lIntFont = lFont.getSize();
		lIntFont = 15;
		Font lFont2 = new Font(lFont.getName(), lFont.getStyle(), lIntFont);
		lTextTitle.setFont(lFont2);
		getChart().setTitle(lTextTitle);
		// Salvo i menu aggiuntivi
		injectedMenu = pPieMenuItem;
		// Aggiusto il menu
		adjustMenu(pieChartAppl);
		setUrlLabelGenerator(generateUrl, pUrlGenerator);
		setMouseWheelEnabled(true);
		// Setto i margini
		getChart().setPadding(new RectangleInsets(4, 8, 2, 2));
		// Setto l'attuale grafico
		pieChartAppl.setActualChart(getChart());
		// Setto la dimensione come da parametri
		setPreferredSize(new Dimension(PieChartApplication.getParameters().getWidth(), PieChartApplication.getParameters().getHeight()));
		// Setto il mouse listener
		addChartMouseListener(new ChartMouseListener() {

			public void chartMouseMoved(ChartMouseEvent paramChartMouseEvent) {
			}

			public void chartMouseClicked(ChartMouseEvent paramChartMouseEvent) {
				System.out.println(paramChartMouseEvent.getEntity().toString());
				// se ho cliccato con il tasto sinistro
				if (paramChartMouseEvent.getTrigger().getButton() == 1) {
					// Se ho previsto una azione
					if (pLeftClickAction != null) {
						// La eseguo
						pLeftClickAction.doAfterClick(paramChartMouseEvent);
					}
				} else {
					// Se ho cliccato con il tasto destro
					if (paramChartMouseEvent.getTrigger().getButton() == 3) {
						// Ciclo i vari menu item aggiuntivi
						for (PieMenuItem pPieMenuItem : injectedMenu) {
							// Se li devo mostrare
							if (pPieMenuItem.showIf(paramChartMouseEvent)) {
								// Li aggiungo
								contextMenu.add(pPieMenuItem);
							} else {
								// altrimenti li elimino
								contextMenu.remove(pPieMenuItem);
							}
						}
						// Aggiusto graficamente il menu
						contextMenu.pack();
					}
				}
			}
		});
	}

	public abstract void setUrlLabelGenerator(boolean generateUrl, UrlGeneratorInterface pUrlGenerator);

	/**
	 * Ha il compito di rimuovere tutti i menu e lasciare solo ed esclusivamente il il SaveAs cui viene aggiunto un sotto menu PDF di tipo {@link PdfMenuItem},
	 * ed il menu stampa
	 * 
	 * @param pieChartAppl
	 */
	protected void adjustMenu(PieChartApplication pieChartAppl) {
		// Recupero il menu
		JPopupMenu lJPopupMenu = getPopupMenu();
		// Rimuovo i sotto menu che non interessano
		lJPopupMenu.remove(0);
		lJPopupMenu.remove(0);
		lJPopupMenu.remove(0);
		lJPopupMenu.remove(3);
		lJPopupMenu.remove(3);
		lJPopupMenu.remove(3);
		lJPopupMenu.remove(3);
		lJPopupMenu.remove(3);
		JMenu lJMenu = (JMenu) lJPopupMenu.getComponent(0);
		// Creo il menu pdf
		PdfMenuItem lPdfMenuItem = new PdfMenuItem(pieChartAppl);
		ExcelMenuItem lExcelMenuItem = new ExcelMenuItem(pieChartAppl);
		// Lo aggiungo
		lJMenu.add(lPdfMenuItem);
		lJMenu.add(lExcelMenuItem);
		contextMenu = lJPopupMenu;
	}
}
