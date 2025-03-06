package it.eng.hybrid.module.pieChart.generator;

import it.eng.hybrid.module.pieChart.dataset.BarChartBuilder;
import it.eng.hybrid.module.pieChart.dataset.PieDataBean;

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
