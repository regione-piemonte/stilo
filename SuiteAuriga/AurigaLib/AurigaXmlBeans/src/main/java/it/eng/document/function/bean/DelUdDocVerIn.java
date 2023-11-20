/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DelUdDocVerIn implements Serializable{

	private String idUdDocIn;
	private int flgCancFisicaIn;
	private String flgTipoTargetIn;	
	private String nroProgrVerIn;
	
	
	
	public int getFlgCancFisicaIn() {
		return flgCancFisicaIn;
	}
	public void setFlgCancFisicaIn(int flgCancFisicaIn) {
		this.flgCancFisicaIn = flgCancFisicaIn;
	}
	public String getIdUdDocIn() {
		return idUdDocIn;
	}
	public void setIdUdDocIn(String idUdDocIn) {
		this.idUdDocIn = idUdDocIn;
	}
	public String getFlgTipoTargetIn() {
		return flgTipoTargetIn;
	}
	public void setFlgTipoTargetIn(String flgTipoTargetIn) {
		this.flgTipoTargetIn = flgTipoTargetIn;
	}
	public String getNroProgrVerIn() {
		return nroProgrVerIn;
	}
	public void setNroProgrVerIn(String nroProgrVerIn) {
		this.nroProgrVerIn = nroProgrVerIn;
	}
	

	
	}
