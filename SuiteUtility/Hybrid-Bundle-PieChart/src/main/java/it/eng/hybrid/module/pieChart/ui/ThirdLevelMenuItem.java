package it.eng.hybrid.module.pieChart.ui;

import java.awt.event.ActionListener;

public class ThirdLevelMenuItem extends DetailMenuItem{

	private static final long serialVersionUID = -3589461894585250903L;
	public static String ACTION_DETAIL = "goToThirdLevel";
	public static String TITLE = "Vai al dettaglio";
	
	public ThirdLevelMenuItem(ActionListener pActionListener){
		super(TITLE, ACTION_DETAIL, pActionListener);
	}

}
