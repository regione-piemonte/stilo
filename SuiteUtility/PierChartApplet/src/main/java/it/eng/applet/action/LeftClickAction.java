package it.eng.applet.action;

import it.eng.applet.panel.PieChartPanel;

import org.jfree.chart.ChartMouseEvent;

/**
 * Interfaccia per la gestione di un click con il tasto sinistro
 * sul {@link PieChartPanel}
 * @author Rametta
 *
 */
public interface LeftClickAction {

	public void doAfterClick(ChartMouseEvent paramChartMouseEvent);
}
