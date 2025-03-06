package it.eng.applet.menu;

import it.eng.applet.panel.PieChartPanel;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.jfree.chart.ChartMouseEvent;

/**
 * Rappresenta un menuitem che può essere aggiunto al menu item del {@link PieChartPanel}
 * in base alla condizione showIf
 * @author Rametta
 *
 */
public abstract class PieMenuItem<T> extends JMenuItem {

	private static final long serialVersionUID = 6862128008271100185L;
	private T opzioni;
	
	/**
	 * Restituisce un {@link JMenuItem} con text pari al title, actionCommand pari ad actionCommand ricevuto
	 * in ingresso e aggangia l'actionListener ricevuto in ingresso
	 * @param title
	 * @param actionCommand
	 * @param pActionListener
	 */
	public PieMenuItem(String title, String actionCommand, ActionListener pActionListener){
		super();
		setText(title);
		setActionCommand(actionCommand);
		addActionListener(pActionListener);
	}
	
	/**
	 * Identifica se mostrare o meno il menu item a partire dall'evento {@link ChartMouseEvent}
	 * 
	 * @param paramChartMouseEvent L'evento passato è già relativo ad un contextClick (tasto destro del mouse)
	 * @return
	 */
	public abstract boolean showIf(ChartMouseEvent paramChartMouseEvent);

	/**
	 * Setta Eventuali opzioni aggiuntive utili quando si processa l'action
	 * @param opzioni
	 */
	public void setOpzioni(T opzioni) {
		this.opzioni = opzioni;
	}

	/**
	 * Restituisce le eventuali opzioni aggiuntive settate in fase di processamento dello showif
	 * @return
	 */
	public T getOpzioni() {
		return opzioni;
	}
	
	
}
