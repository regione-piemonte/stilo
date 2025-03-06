package it.eng.hybrid.module.pieChart.ui;

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
