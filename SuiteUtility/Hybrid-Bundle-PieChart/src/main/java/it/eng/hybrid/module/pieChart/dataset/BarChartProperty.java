package it.eng.hybrid.module.pieChart.dataset;

import org.jfree.data.category.CategoryDataset;

public class BarChartProperty {

	private String title;
	private String assexLabel;
	private String asseyLabel;
	private CategoryDataset dataset;
	private String error;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public CategoryDataset getDataset() {
		return dataset;
	}
	public void setDataset(CategoryDataset dataset) {
		this.dataset = dataset;
	}
	public String getAssexLabel() {
		return assexLabel;
	}
	public void setAssexLabel(String assexLabel) {
		this.assexLabel = assexLabel;
	}
	public String getAsseyLabel() {
		return asseyLabel;
	}
	public void setAsseyLabel(String asseyLabel) {
		this.asseyLabel = asseyLabel;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
