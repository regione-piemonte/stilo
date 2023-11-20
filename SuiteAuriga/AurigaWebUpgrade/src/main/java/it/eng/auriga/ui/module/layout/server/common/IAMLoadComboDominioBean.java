/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class IAMLoadComboDominioBean extends LoadComboDominioBean{

	private String iamUsername;
	private String iamMatricola;
	
	public String getIamUsername() {
		return iamUsername;
	}
	
	public void setIamUsername(String iamUsername) {
		this.iamUsername = iamUsername;
	}
	
	public String getIamMatricola() {
		return iamMatricola;
	}
	
	public void setIamMatricola(String iamMatricola) {
		this.iamMatricola = iamMatricola;
	}
		
}
