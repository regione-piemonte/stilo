/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ProtocollazioneSueAsyncRequest")
public class ProtocollazioneAsyncRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fileName;
	private String hashFile;
	private String idReceiver;
	private String metadatiProtocollo;
	private String operazione;
	private String codApplicazione;
	private String istanzaApplicazione;
	private String userName;
	private String password;
	
	private List<AllegatiProtocollazioneAsyncRequest> listaAllegati;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getHashFile() {
		return hashFile;
	}
	
	public void setHashFile(String hashFile) {
		this.hashFile = hashFile;
	}
	
	public String getIdReceiver() {
		return idReceiver;
	}
	
	public void setIdReceiver(String idReceiver) {
		this.idReceiver = idReceiver;
	}
	
	public String getMetadatiProtocollo() {
		return metadatiProtocollo;
	}
	
	public void setMetadatiProtocollo(String metadatiProtocollo) {
		this.metadatiProtocollo = metadatiProtocollo;
	}

	public String getCodApplicazione() {
		return codApplicazione;
	}

	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}

	public String getIstanzaApplicazione() {
		return istanzaApplicazione;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setIstanzaApplicazione(String istanzaApplicazione) {
		this.istanzaApplicazione = istanzaApplicazione;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<AllegatiProtocollazioneAsyncRequest> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<AllegatiProtocollazioneAsyncRequest> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	
}
