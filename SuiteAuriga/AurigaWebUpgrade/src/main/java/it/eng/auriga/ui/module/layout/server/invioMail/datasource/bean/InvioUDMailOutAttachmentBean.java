/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

public class InvioUDMailOutAttachmentBean {

	@NumeroColonna(numero = "1")
	private String nomeFile;
	@NumeroColonna(numero = "2")
	private String uri;
	@NumeroColonna(numero = "3")
	private String nroAllegato;
	@NumeroColonna(numero = "4")
	private Flag daTimbrare;
	@NumeroColonna(numero = "5")
	private Flag firmato;
	@NumeroColonna(numero = "6")
	private Flag pdf;
	@NumeroColonna(numero = "7")
	private Flag convertibile;
	@NumeroColonna(numero = "8")
	private String testoNelTimbro;
	@NumeroColonna(numero = "9")
	private String testoVicinoAlTimbro;
	@NumeroColonna(numero = "10")
	private String mimetype;
	@NumeroColonna(numero = "11")
	private String idDoc;	
	
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getNroAllegato() {
		return nroAllegato;
	}
	public void setNroAllegato(String nroAllegato) {
		this.nroAllegato = nroAllegato;
	}
	public Flag getDaTimbrare() {
		return daTimbrare;
	}
	public void setDaTimbrare(Flag daTimbrare) {
		this.daTimbrare = daTimbrare;
	}
	public Flag getFirmato() {
		return firmato;
	}
	public void setFirmato(Flag firmato) {
		this.firmato = firmato;
	}
	public Flag getPdf() {
		return pdf;
	}
	public void setPdf(Flag pdf) {
		this.pdf = pdf;
	}
	public Flag getConvertibile() {
		return convertibile;
	}
	public void setConvertibile(Flag convertibile) {
		this.convertibile = convertibile;
	}
	public String getTestoNelTimbro() {
		return testoNelTimbro;
	}
	public void setTestoNelTimbro(String testoNelTimbro) {
		this.testoNelTimbro = testoNelTimbro;
	}
	public String getTestoVicinoAlTimbro() {
		return testoVicinoAlTimbro;
	}
	public void setTestoVicinoAlTimbro(String testoVicinoAlTimbro) {
		this.testoVicinoAlTimbro = testoVicinoAlTimbro;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
}
