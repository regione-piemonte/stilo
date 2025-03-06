package it.eng.generator.tooltip.bar;

import it.eng.applet.PieChartApplet;
import it.eng.applet.dataset.bean.PieDataBean;

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
		PieDataBean lPieDataBean =  PieChartApplet.mapValues.get(Integer.valueOf(level)-1).get(paramInt1);
		// TODO Auto-generated method stub
		return lPieDataBean.getLabel();
	}

}
