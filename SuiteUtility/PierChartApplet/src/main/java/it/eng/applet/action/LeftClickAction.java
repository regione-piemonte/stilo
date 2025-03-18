/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
