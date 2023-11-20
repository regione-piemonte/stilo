/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaStrutturaAmministrativa extends ContabiliaEntitaBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codiceTipoStruttura;
	
	public String getCodiceTipoStruttura() {
		return codiceTipoStruttura;
	}
	
	public void setCodiceTipoStruttura(String codiceTipoStruttura) {
		this.codiceTipoStruttura = codiceTipoStruttura;
	}
	
	@Override
	public String toString() {
		return "ContabiliaStrutturaAmministrativa [codiceTipoStruttura=" + codiceTipoStruttura + "]";
	}
	
}
