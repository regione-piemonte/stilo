/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspUpdateStatoDocumentoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codiceFiscaleOp;
	private Integer IdTipdoc;
	private String uuid;
	private String esitoInvio;
	private Date dataInvio;
	
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
	
	public String getEsitoInvio() {
		return esitoInvio;
	}
	
	public void setEsitoInvio(String esitoInvio) {
		this.esitoInvio = esitoInvio;
	}
	
	public Date getDataInvio() {
		return dataInvio;
	}
	
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	
	@Override
	public String toString() {
		return "ContabilitaAdspUpdateStatoDocumentoRequest [codiceFiscaleOp=" + codiceFiscaleOp + ", IdTipdoc="
				+ IdTipdoc + ", uuid=" + uuid + ", esitoInvio=" + esitoInvio + ", dataInvio=" + dataInvio + "]";
	}
	
}
