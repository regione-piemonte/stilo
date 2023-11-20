/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FindToponomasticoBean {
	private String codRapidoToponimo;
	private String nomeToponimo;
	private String descrToponimo;
    private String idToponimo;
    private boolean vuoto = false;
    
    private java.math.BigDecimal idToponimoOut;
    
	
	public String getCodRapidoToponimo() {
		return codRapidoToponimo;
	}
	public void setCodRapidoToponimo(String codRapidoToponimo) {
		this.codRapidoToponimo = codRapidoToponimo;
	}
	public String getNomeToponimo() {
		return nomeToponimo;
	}
	public void setNomeToponimo(String nomeToponimo) {
		this.nomeToponimo = nomeToponimo;
	}
	public String getDescrToponimo() {
		return descrToponimo;
	}
	public void setDescrToponimo(String descrToponimo) {
		this.descrToponimo = descrToponimo;
	}
	public String getIdToponimo() {
		return idToponimo;
	}
	public void setIdToponimo(String idToponimo) {
		this.idToponimo = idToponimo;
	}
	
	public java.math.BigDecimal getIdToponimoOut() {
		return idToponimoOut;
	}
	public void setIdToponimoOut(java.math.BigDecimal idToponimoOut) {
		this.idToponimoOut = idToponimoOut;
	}
	public void setVuoto(boolean vuoto) {
		this.vuoto = vuoto;
	}
	public boolean getVuoto() {
		return vuoto;
	}
	
		
	
}
