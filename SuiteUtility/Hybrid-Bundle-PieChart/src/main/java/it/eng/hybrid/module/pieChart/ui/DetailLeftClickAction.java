package it.eng.hybrid.module.pieChart.ui;

import org.jfree.chart.ChartMouseEvent;

/**
 * Implementa una {@link LeftClickAction} per la gestione del dettaglio
 * Esegue la goToDetail della {@link PieChartAppl}
 * @author Rametta
 *
 */
public class DetailLeftClickAction implements LeftClickAction {
	
	private PieChartApplication mAppl;
	
	/**
	 * Costruttore
	 * @param pPieChartAppl
	 */
	public DetailLeftClickAction(PieChartApplication pPieChartAppl){
		mAppl = pPieChartAppl;
	}

	@Override
	public void doAfterClick(ChartMouseEvent paramChartMouseEvent) {
		String lStringUrl = paramChartMouseEvent.getEntity().getURLText();
		if (lStringUrl!=null){
			mAppl.detail(lStringUrl);
		}
	}

}
