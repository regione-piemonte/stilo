package it.eng.client.applet.bean;

import com.nilo.plaf.nimrod.NimRODTheme;

public class ThemeBean {
	
	public NimRODTheme getLook() {
		return theme;
	}

	private NimRODTheme theme;
	private String name;
	
	public ThemeBean(NimRODTheme theme,String name) {
		super();
		this.theme = theme;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
