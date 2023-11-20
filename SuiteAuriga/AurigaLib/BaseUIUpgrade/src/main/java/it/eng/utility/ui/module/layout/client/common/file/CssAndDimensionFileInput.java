/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Cursor;

public class CssAndDimensionFileInput {

	private String cssClass = "cabinet";
	private int height = 18;
	private int width = 24;
	private Cursor cursor = Cursor.POINTER;
	private boolean showHover = true;
	
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Cursor getCursor() {
		return cursor;
	}
	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	public boolean isShowHover() {
		return showHover;
	}
	public void setShowHover(boolean showHover) {
		this.showHover = showHover;
	}
	
}
