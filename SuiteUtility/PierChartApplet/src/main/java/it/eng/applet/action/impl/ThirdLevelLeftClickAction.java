package it.eng.applet.action.impl;

import org.jfree.chart.ChartMouseEvent;

import it.eng.applet.PieChartApplet;
import it.eng.applet.action.LeftClickAction;

public class ThirdLevelLeftClickAction implements LeftClickAction{

	private PieChartApplet applet;
	public ThirdLevelLeftClickAction(PieChartApplet applet){
		this.applet = applet;
	}
	@Override
	public void doAfterClick(ChartMouseEvent paramChartMouseEvent) {
		String lStringUrl = paramChartMouseEvent.getEntity().getURLText();
		if (lStringUrl!=null){
			applet.detail(lStringUrl);
		}
	}
}
