package it.eng.applet.action.impl;

import it.eng.applet.PieChartApplet;
import it.eng.applet.action.LeftClickAction;

import org.jfree.chart.ChartMouseEvent;

/**
 * Implementa una {@link LeftClickAction} per la gestione del dettaglio
 * Esegue la goToDetail della {@link PieChartApplet}
 * @author Rametta
 *
 */
public class DetailLeftClickAction implements LeftClickAction {
	
	private PieChartApplet mApplet;
	
	/**
	 * Costruttore
	 * @param pPieChartApplet
	 */
	public DetailLeftClickAction(PieChartApplet pPieChartApplet){
		mApplet = pPieChartApplet;
	}

	@Override
	public void doAfterClick(ChartMouseEvent paramChartMouseEvent) {
		String lStringUrl = paramChartMouseEvent.getEntity().getURLText();
		if (lStringUrl!=null){
			mApplet.detail(lStringUrl);
		}
	}

}
