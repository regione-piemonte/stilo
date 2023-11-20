/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class Proposta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String siglaTipoAtto;
	private String codSettore;
	private String numatto;
	private String annoatto;
	private String dataatto;
	
	public String getSiglaTipoAtto() {
		return siglaTipoAtto;
	}
	
	public void setSiglaTipoAtto(String siglaTipoAtto) {
		this.siglaTipoAtto = siglaTipoAtto;
	}
	
	public String getCodSettore() {
		return codSettore;
	}
	
	public void setCodSettore(String codSettore) {
		this.codSettore = codSettore;
	}
	
	public String getNumatto() {
		return numatto;
	}
	
	public void setNumatto(String numatto) {
		this.numatto = numatto;
	}
	
	public String getAnnoatto() {
		return annoatto;
	}
	
	public void setAnnoatto(String annoatto) {
		this.annoatto = annoatto;
	}
	
	public String getDataatto() {
		return dataatto;
	}
	
	public void setDataatto(String dataatto) {
		this.dataatto = dataatto;
	}

	@Override
	public String toString() {
		return "Proposta [siglaTipoAtto=" + siglaTipoAtto + ", codSettore=" + codSettore + ", numatto=" + numatto
				+ ", annoatto=" + annoatto + ", dataatto=" + dataatto + "]";
	}
	
}
