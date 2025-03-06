package it.eng.hybrid.module.pieChart.ui;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BackButton extends JButton{
	
	private static final long serialVersionUID = 1673990865964946985L;
	public static String ACTION = "backButton";
	private LEVEL_BACK level;

	public BackButton(ActionListener pActionListener, LEVEL_BACK level) {
		super("Indietro");
		this.setLevel(level);
		setActionCommand(ACTION);
		setPreferredSize(new Dimension(80, 20));
		//Lo centro
		setAlignmentX(Component.CENTER_ALIGNMENT);
		addActionListener(pActionListener);
	}

	public void setLevel(LEVEL_BACK level) {
		this.level = level;
	}

	public LEVEL_BACK getLevel() {
		return level;
	}
}
