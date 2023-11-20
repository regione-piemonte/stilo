/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspGetContenutoDocumentoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codiceFiscaleOp;
	private Integer IdTipdoc;
	private String uuid;
	
	public String getCodiceFiscaleOp() {
		return codiceFiscaleOp;
	}
	
	public void setCodiceFiscaleOp(String codiceFiscaleOp) {
		this.codiceFiscaleOp = codiceFiscaleOp;
	}
	
	public Integer getIdTipdoc() {
		return IdTipdoc;
	}
	
	public void setIdTipdoc(Integer idTipdoc) {
		IdTipdoc = idTipdoc;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspGetContenutoDocumentoRequest [codiceFiscaleOp=" + codiceFiscaleOp + ", IdTipdoc="
				+ IdTipdoc + ", uuid=" + uuid + "]";
	}
	
}
