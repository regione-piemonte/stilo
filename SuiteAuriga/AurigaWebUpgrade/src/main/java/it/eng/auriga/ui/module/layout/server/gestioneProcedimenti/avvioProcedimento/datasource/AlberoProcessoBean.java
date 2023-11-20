/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AlberoProcessoBean {

	private String id;
	private String title;
	private String altToShow;
	private String parent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAltToShow() {
		return altToShow;
	}
	public void setAltToShow(String altToShow) {
		this.altToShow = altToShow;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
}
