package it.eng.generator.tooltip.pie;

import it.eng.applet.dataset.BarChartBuilder;
import it.eng.applet.dataset.bean.PieDataBean;

import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.data.general.PieDataset;

public class CustomPieToolTipGenerator implements PieToolTipGenerator{

	@Override
	public String generateToolTip(PieDataset arg0, Comparable arg1) {
		PieDataBean lPieDataBean = (PieDataBean)arg1;
		float lFloat = BarChartBuilder.getFloatValue(lPieDataBean.getValore());
		double lLong = (Math.round(lFloat*100.0)/100.0);
		return lPieDataBean.getLabel() + " - Conteggio : " + lLong + " (" + lPieDataBean.getPercArrotondata() + "%)";
	}

}
