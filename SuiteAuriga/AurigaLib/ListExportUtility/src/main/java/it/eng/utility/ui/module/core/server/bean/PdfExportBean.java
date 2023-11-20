/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class PdfExportBean extends ExportBean implements Serializable {
	
	private String title;
	private List<String> columnHeader;
	private int titleDimension;
	private float[] columnDimension;
	public enum Orientation {VERTICAL, HORIZONTAL}
	private Orientation orientation;
	public enum Dimension {A3, A4}
	private Dimension dimension;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getColumnHeader() {
		return columnHeader;
	}
	public void setColumnHeader(List<String> columnHeader) {
		this.columnHeader = columnHeader;
	}
	public void setTitleDimension(int titleDimension) {
		this.titleDimension = titleDimension;
	}
	public int getTitleDimension() {
		return titleDimension;
	}
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	public Orientation getOrientation() {
		if (orientation == null) return Orientation.HORIZONTAL;
		return orientation;
	}
	public void setColumnDimension(float[] columnDimension) {
		this.columnDimension = columnDimension;
	}
	public float[] getColumnDimension() {
		return columnDimension;
	}
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	public Dimension getDimension() {
		if (dimension == null) return Dimension.A4;
		return dimension;
	}
	
	
	

}
