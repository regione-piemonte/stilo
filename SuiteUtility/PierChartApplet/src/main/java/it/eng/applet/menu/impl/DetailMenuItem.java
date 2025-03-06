package it.eng.applet.menu.impl;

import it.eng.applet.menu.PieMenuItem;

import java.awt.event.ActionListener;

import org.jfree.chart.ChartMouseEvent;

/**
 * Rappresenta un {@link PieMenuItem} per la gestione del dettaglio
 * Viene mostrato se l'url non è nullo
 * @author Rametta
 *
 */
public class DetailMenuItem extends PieMenuItem<String> {

	private static final long serialVersionUID = -1426076162794693855L;
	public static String ACTION_DETAIL = "goToDetail";
	public static String TITLE = "Vai al dettaglio";
	
	public DetailMenuItem(ActionListener pActionListener){
		this(TITLE, ACTION_DETAIL, pActionListener);
	}
	
	public DetailMenuItem(String title, String action, ActionListener pActionListener){
		super(title, action, pActionListener);
	}

	@Override
	public boolean showIf(ChartMouseEvent paramChartMouseEvent) {
		String lStringUrl = paramChartMouseEvent.getEntity().getURLText();
		setOpzioni(lStringUrl);
		return lStringUrl!=null;
	}
	
}  
