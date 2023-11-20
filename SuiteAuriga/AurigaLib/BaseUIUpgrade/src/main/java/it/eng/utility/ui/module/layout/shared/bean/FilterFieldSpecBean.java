/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FilterFieldSpecBean {

	private String name;
	private String title;
	private boolean toHide;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isToHide() {
		return toHide;
	}

	public void setToHide(boolean toHide) {
		this.toHide = toHide;
	}
	
}
