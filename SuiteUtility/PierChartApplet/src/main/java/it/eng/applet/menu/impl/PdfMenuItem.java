package it.eng.applet.menu.impl;

import it.eng.applet.menu.PieMenuItem;

import java.awt.event.ActionListener;

import org.jfree.chart.ChartMouseEvent;

/**
 * Menu item per la gestione del pdf
 * @author Rametta
 *
 */
public class PdfMenuItem extends PieMenuItem {
	
	private static final long serialVersionUID = -2878576684538381082L;
	public static String ACTION_PDF = "PDF";
	public static String TITLE = "PDF..";

	/**
	 * Crea un menu item per la gestione del pdf
	 * @param pActionListener
	 */
	public PdfMenuItem(ActionListener pActionListener) {
		super(TITLE, ACTION_PDF, pActionListener);
	}

	@Override
	public boolean showIf(ChartMouseEvent paramChartMouseEvent) {
		return true;
	}

}
