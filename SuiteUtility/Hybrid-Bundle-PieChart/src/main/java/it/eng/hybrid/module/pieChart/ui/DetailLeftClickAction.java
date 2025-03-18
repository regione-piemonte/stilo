/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
