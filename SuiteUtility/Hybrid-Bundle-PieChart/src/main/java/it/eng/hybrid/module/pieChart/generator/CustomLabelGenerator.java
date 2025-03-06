package it.eng.hybrid.module.pieChart.generator;

import it.eng.hybrid.module.pieChart.dataset.PieDataBean;

import java.text.AttributedString;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

public class CustomLabelGenerator implements PieSectionLabelGenerator {

	@Override
	public AttributedString generateAttributedSectionLabel(PieDataset arg0,
			Comparable arg1) {
		PieDataBean lPieDataBean = (PieDataBean)arg1;
		return new AttributedString(lPieDataBean.getLabel());
	}

	@Override
	public String generateSectionLabel(PieDataset arg0, Comparable arg1) {
		PieDataBean lPieDataBean = (PieDataBean)arg1;
		return lPieDataBean.getLabel() + " (" + lPieDataBean.getPercArrotondata() + "%)";
	}

}
