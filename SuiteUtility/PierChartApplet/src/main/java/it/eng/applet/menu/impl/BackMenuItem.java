package it.eng.applet.menu.impl;

import it.eng.applet.menu.PieMenuItem;

import java.awt.event.ActionListener;

import org.jfree.chart.ChartMouseEvent;

/**
 * Rappresenta un {@link PieMenuItem} per la gestione del back to main
 * Viene mostrato sempre
 * @author Rametta
 *
 */
public class BackMenuItem extends PieMenuItem<LEVEL_BACK> {
	
	private static final long serialVersionUID = -1426076162794693855L;
	public static String ACTION_BACK_TO_MAIN = "back";
	public static String TITLE = "Indietro";
	
	public BackMenuItem(ActionListener pActionListener, LEVEL_BACK level){
		super(TITLE, ACTION_BACK_TO_MAIN, pActionListener);
		setOpzioni(level);
	}

	@Override
	public boolean showIf(ChartMouseEvent paramChartMouseEvent) {
		return true;
	} 
}  
