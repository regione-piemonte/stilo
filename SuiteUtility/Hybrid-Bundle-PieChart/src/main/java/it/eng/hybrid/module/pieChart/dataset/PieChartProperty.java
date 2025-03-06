package it.eng.hybrid.module.pieChart.dataset;

import org.jfree.data.general.PieDataset;

/**
 * Classe che rappresenta le proprietà semplici di un {@link PieChartPanel},
 * ovvero il titolo e il dataset di tipo {@link PieDataset}
 * 
 * @author Rametta
 * 
 */
public class PieChartProperty {

	public String title;
	public PieDataset dataset;
	private String error;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PieDataset getDataset() {
		return dataset;
	}

	public void setDataset(PieDataset dataset) {
		this.dataset = dataset;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
