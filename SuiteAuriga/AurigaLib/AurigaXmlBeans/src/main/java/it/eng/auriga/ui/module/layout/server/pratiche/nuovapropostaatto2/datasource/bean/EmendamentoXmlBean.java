/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.function.bean.Flag;

public class EmendamentoXmlBean {

	@NumeroColonna(numero = "1")
	private String idUd;

	@NumeroColonna(numero = "2")
	private String nroEmendamento;

	@NumeroColonna(numero = "3")
	private String nroSubEmendamento;

	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA)
	private Date tsCaricamento;

	@NumeroColonna(numero = "5")
	private String strutturaProponente;

	@NumeroColonna(numero = "6")
	private String cdcStrutturaProponente;

	@NumeroColonna(numero = "7")
	private String firmatari;

	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA)
	private Date tsPerfezionamento;

	@NumeroColonna(numero = "9")
	private String tipoFileRiferimento;

	@NumeroColonna(numero = "10")
	private Integer nroAllegatoRiferimento;

	@NumeroColonna(numero = "11")
	private Integer nroPaginaRiferimento;

	@NumeroColonna(numero = "12")
	private Integer nroRigaRiferimento;

	@NumeroColonna(numero = "13")
	private String effettoEmendamento;

	@NumeroColonna(numero = "14")
	private Flag emendamentoIntegrale;

	@NumeroColonna(numero = "15")
	private String testoHtml;

	@NumeroColonna(numero = "16")
	private String uriFile;

	@NumeroColonna(numero = "17")
	private String nomeFile;

	@NumeroColonna(numero = "18")
	private Flag firmato;

	@NumeroColonna(numero = "19")
	private String mimetype;

	@NumeroColonna(numero = "20")
	private Flag convertibilePdf;

	@NumeroColonna(numero = "21")
	private String pareriEspressi;

	@NumeroColonna(numero = "22")
	private String filePareri;
	
	@NumeroColonna(numero = "23")
	private String idProcess;
	
	@NumeroColonna(numero = "24")
	private BigDecimal dimensioneFile;
	
	@NumeroColonna(numero = "25")
	private String idEmendamento;

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getNroEmendamento() {
		return nroEmendamento;
	}

	public void setNroEmendamento(String nroEmendamento) {
		this.nroEmendamento = nroEmendamento;
	}

	public String getNroSubEmendamento() {
		return nroSubEmendamento;
	}

	public void setNroSubEmendamento(String nroSubEmendamento) {
		this.nroSubEmendamento = nroSubEmendamento;
	}

	public Date getTsCaricamento() {
		return tsCaricamento;
	}

	public void setTsCaricamento(Date tsCaricamento) {
		this.tsCaricamento = tsCaricamento;
	}

	public String getStrutturaProponente() {
		return strutturaProponente;
	}

	public void setStrutturaProponente(String strutturaProponente) {
		this.strutturaProponente = strutturaProponente;
	}

	public String getCdcStrutturaProponente() {
		return cdcStrutturaProponente;
	}

	public void setCdcStrutturaProponente(String cdcStrutturaProponente) {
		this.cdcStrutturaProponente = cdcStrutturaProponente;
	}

	public String getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}

	public Date getTsPerfezionamento() {
		return tsPerfezionamento;
	}

	public void setTsPerfezionamento(Date tsPerfezionamento) {
		this.tsPerfezionamento = tsPerfezionamento;
	}

	public String getTipoFileRiferimento() {
		return tipoFileRiferimento;
	}

	public void setTipoFileRiferimento(String tipoFileRiferimento) {
		this.tipoFileRiferimento = tipoFileRiferimento;
	}

	public Integer getNroAllegatoRiferimento() {
		return nroAllegatoRiferimento;
	}

	public void setNroAllegatoRiferimento(Integer nroAllegatoRiferimento) {
		this.nroAllegatoRiferimento = nroAllegatoRiferimento;
	}

	public Integer getNroPaginaRiferimento() {
		return nroPaginaRiferimento;
	}

	public void setNroPaginaRiferimento(Integer nroPaginaRiferimento) {
		this.nroPaginaRiferimento = nroPaginaRiferimento;
	}

	public Integer getNroRigaRiferimento() {
		return nroRigaRiferimento;
	}

	public void setNroRigaRiferimento(Integer nroRigaRiferimento) {
		this.nroRigaRiferimento = nroRigaRiferimento;
	}

	public String getEffettoEmendamento() {
		return effettoEmendamento;
	}

	public void setEffettoEmendamento(String effettoEmendamento) {
		this.effettoEmendamento = effettoEmendamento;
	}

	public Flag getEmendamentoIntegrale() {
		return emendamentoIntegrale;
	}

	public void setEmendamentoIntegrale(Flag emendamentoIntegrale) {
		this.emendamentoIntegrale = emendamentoIntegrale;
	}

	public String getTestoHtml() {
		return testoHtml;
	}

	public void setTestoHtml(String testoHtml) {
		this.testoHtml = testoHtml;
	}

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Flag getFirmato() {
		return firmato;
	}

	public void setFirmato(Flag firmato) {
		this.firmato = firmato;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Flag getConvertibilePdf() {
		return convertibilePdf;
	}

	public void setConvertibilePdf(Flag convertibilePdf) {
		this.convertibilePdf = convertibilePdf;
	}

	public String getPareriEspressi() {
		return pareriEspressi;
	}

	public void setPareriEspressi(String pareriEspressi) {
		this.pareriEspressi = pareriEspressi;
	}

	public String getFilePareri() {
		return filePareri;
	}

	public void setFilePareri(String filePareri) {
		this.filePareri = filePareri;
	}

	public String getIdProcess() {
		return idProcess;
	}
	
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

	public BigDecimal getDimensioneFile() {
		return dimensioneFile;
	}

	public void setDimensioneFile(BigDecimal dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}

	public String getIdEmendamento() {
		return idEmendamento;
	}

	public void setIdEmendamento(String idEmendamento) {
		this.idEmendamento = idEmendamento;
	}
	
}
