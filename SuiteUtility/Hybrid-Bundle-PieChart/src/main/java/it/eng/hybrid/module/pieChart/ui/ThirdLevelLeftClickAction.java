package it.eng.hybrid.module.pieChart.ui;

import org.jfree.chart.ChartMouseEvent;



public class ThirdLevelLeftClickAction implements LeftClickAction{

	private PieChartApplication appl;
	
	public ThirdLevelLeftClickAction(PieChartApplication appl){
		this.appl = appl;
	}
	@Override
	public void doAfterClick(ChartMouseEvent paramChartMouseEvent) {
		String lStringUrl = paramChartMouseEvent.getEntity().getURLText();
		if (lStringUrl!=null){
			appl.detail(lStringUrl);
		}
	}
}
