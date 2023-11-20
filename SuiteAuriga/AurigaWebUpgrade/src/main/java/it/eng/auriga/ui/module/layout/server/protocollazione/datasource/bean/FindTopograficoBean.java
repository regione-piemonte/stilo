/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FindTopograficoBean {
	private String codRapidoTopografico;
	private String nomeTopografico;
	private String descrTopografico;
    private String idTopografico;
    private boolean vuoto = false;
    
    private java.math.BigDecimal idTopograficoOut;
    
	
	public String getCodRapidoTopografico() {
		return codRapidoTopografico;
	}
	public void setCodRapidoTopografico(String codRapidoTopografico) {
		this.codRapidoTopografico = codRapidoTopografico;
	}
	public String getNomeTopografico() {
		return nomeTopografico;
	}
	public void setNomeTopografico(String nomeTopografico) {
		this.nomeTopografico = nomeTopografico;
	}
	public String getDescrTopografico() {
		return descrTopografico;
	}
	public void setDescrTopografico(String descrTopografico) {
		this.descrTopografico = descrTopografico;
	}
	public String getIdTopografico() {
		return idTopografico;
	}
	public void setIdTopografico(String idTopografico) {
		this.idTopografico = idTopografico;
	}
	
	public java.math.BigDecimal getIdTopograficoOut() {
		return idTopograficoOut;
	}
	public void setIdTopograficoOut(java.math.BigDecimal idTopograficoOut) {
		this.idTopograficoOut = idTopograficoOut;
	}
	public void setVuoto(boolean vuoto) {
		this.vuoto = vuoto;
	}
	public boolean getVuoto() {
		return vuoto;
	}
	
		
	
}
