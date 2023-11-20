/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ItemFilterBean {

	private String name;
	private String title;
	private ItemFilterType type;
	private boolean value;
	private boolean display;
	private String icon;
	private Integer width;
	private String prefix;
	private String suffix;
	private String paramDBShowIf;

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

	public ItemFilterType getType() {
		return type;
	}

	public void setType(ItemFilterType type) {
		this.type = type;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getParamDBShowIf() {
		return paramDBShowIf;
	}

	public void setParamDBShowIf(String paramDBShowIf) {
		this.paramDBShowIf = paramDBShowIf;
	}

}
