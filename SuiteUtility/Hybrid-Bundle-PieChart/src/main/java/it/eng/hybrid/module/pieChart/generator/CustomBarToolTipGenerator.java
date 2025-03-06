package it.eng.hybrid.module.pieChart.generator;

import it.eng.hybrid.module.pieChart.dataset.PieDataBean;
import it.eng.hybrid.module.pieChart.ui.PieChartApplication;

import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;

public class CustomBarToolTipGenerator implements CategoryToolTipGenerator{

	private String level;
	
	public CustomBarToolTipGenerator(String level) {
		this.level = level;
	}

	@Override
	public String generateToolTip(CategoryDataset paramCategoryDataset,
			int paramInt1, int paramInt2) {
		PieDataBean lPieDataBean =  PieChartApplication.mapValues.get(Integer.valueOf(level)-1).get(paramInt1);
		return lPieDataBean.getLabel();
	}

}
