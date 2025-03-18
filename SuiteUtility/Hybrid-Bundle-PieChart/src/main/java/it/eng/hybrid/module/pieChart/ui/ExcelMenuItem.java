/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.event.ActionListener;


import org.jfree.chart.ChartMouseEvent;

public class ExcelMenuItem extends PieMenuItem {

	private static final long serialVersionUID = -2878576684538381082L;
	public static String ACTION_EXCEL = "XLS";
	public static String TITLE = "XLS..";

	/**
	 * Crea un menu item per la gestione del pdf
	 * @param pActionListener
	 */
	public ExcelMenuItem(ActionListener pActionListener) {
		super(TITLE, ACTION_EXCEL, pActionListener);
	}

	@Override
	public boolean showIf(ChartMouseEvent paramChartMouseEvent) {
		return true;
	}
}
