/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FatturaALGDocumentoIn extends CreaModDocumentoInBean {
	
	private static final long serialVersionUID = -4011655723485405230L;
	
	@XmlVariabile(nome="COD_SOCIETA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codSocieta;

	@XmlVariabile(nome="COD_CLIENTE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codCliente;
	
	@XmlVariabile(nome="CF_PIVA_CLIENTE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String cfPivaCliente;
	
	@XmlVariabile(nome="DES_CLIENTE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String desCliente;
	
	@XmlVariabile(nome="COD_TIPO_DOC_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codTipoDoc;
	
	@XmlVariabile(nome="COD_PROCESSO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codProcesso;
	
	@XmlVariabile(nome="SEZIONALE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String sezionale;
	
	@XmlVariabile(nome="DATA_DOCUMENTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String dataDocumento;
	
	@XmlVariabile(nome="NRO_DOCUMENTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nroDocumento;
	
	@XmlVariabile(nome="TIPO_FATTURA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipoFattura;
	
	@XmlVariabile(nome="COD_CONTRATTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codContratto;
	
	@XmlVariabile(nome="COD_FORNITURA_Doc", tipo = TipoVariabile.SEMPLICE)
	private Integer codFornitura;
	
	@XmlVariabile(nome="TIPO_SERVIZIO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipoServizio;
	
	@XmlVariabile(nome="COD_PDR_POD_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codPdrPod;

	public String getCodSocieta() {
		return codSocieta;
	}

	public void setCodSocieta(String codSocieta) {
		this.codSocieta = codSocieta;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getCfPivaCliente() {
		return cfPivaCliente;
	}

	public void setCfPivaCliente(String cfPivaCliente) {
		this.cfPivaCliente = cfPivaCliente;
	}

	public String getDesCliente() {
		return desCliente;
	}

	public void setDesCliente(String desCliente) {
		this.desCliente = desCliente;
	}

	public String getCodTipoDoc() {
		return codTipoDoc;
	}

	public void setCodTipoDoc(String codTipoDoc) {
		this.codTipoDoc = codTipoDoc;
	}

	public String getCodProcesso() {
		return codProcesso;
	}

	public void setCodProcesso(String codProcesso) {
		this.codProcesso = codProcesso;
	}

	public String getSezionale() {
		return sezionale;
	}

	public void setSezionale(String sezionale) {
		this.sezionale = sezionale;
	}

	public String getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getTipoFattura() {
		return tipoFattura;
	}

	public void setTipoFattura(String tipoFattura) {
		this.tipoFattura = tipoFattura;
	}

	public String getCodContratto() {
		return codContratto;
	}

	public void setCodContratto(String codContratto) {
		this.codContratto = codContratto;
	}

	public Integer getCodFornitura() {
		return codFornitura;
	}

	public void setCodFornitura(Integer codFornitura) {
		this.codFornitura = codFornitura;
	}

	public String getTipoServizio() {
		return tipoServizio;
	}

	public void setTipoServizio(String tipoServizio) {
		this.tipoServizio = tipoServizio;
	}

	public String getCodPdrPod() {
		return codPdrPod;
	}

	public void setCodPdrPod(String codPdrPod) {
		this.codPdrPod = codPdrPod;
	}
}

