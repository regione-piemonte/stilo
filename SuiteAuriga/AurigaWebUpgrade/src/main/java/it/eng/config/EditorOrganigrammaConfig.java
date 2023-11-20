/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class EditorOrganigrammaConfig {

	private static EditorOrganigrammaConfig instance;
	private String url;
	
	public static EditorOrganigrammaConfig getInstance(){
		if (instance == null){
			instance = new EditorOrganigrammaConfig();
		}
		return instance;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
}
