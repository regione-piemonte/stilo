package it.eng.hybrid.module.pieChart.generator;



import it.eng.hybrid.module.pieChart.dataset.BarChartBuilder;
import it.eng.hybrid.module.pieChart.dataset.PieDataBean;

import java.text.AttributedString;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

public class CustomLegendGenerator implements PieSectionLabelGenerator {

	@Override
	public String generateSectionLabel(PieDataset paramPieDataset,
			Comparable paramComparable) {
		PieDataBean lPieDataBean = (PieDataBean)paramComparable;
		float lFloat = BarChartBuilder.getFloatValue(lPieDataBean.getValore());
		double lLong = (Math.round(lFloat*100.0)/100.0);
		return lPieDataBean.getLabel() + " - Conteggio : " + lLong + " (" + lPieDataBean.getPercArrotondata() + "%)";
	}

	@Override
	public AttributedString generateAttributedSectionLabel(
			PieDataset paramPieDataset, Comparable paramComparable) {
		PieDataBean lPieDataBean = (PieDataBean)paramComparable;
		return new AttributedString(lPieDataBean.getLabel());
	}

}
