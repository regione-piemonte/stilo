/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class EmendamentoBean {

	private String idUd;
	private String nroEmendamento;
	private String nroSubEmendamento;
	private Date tsCaricamento;
	private String strutturaProponente;
	private String cdcStrutturaProponente;
	private String firmatari;
	private Date tsPerfezionamento;
	private String tipoFileRiferimento;
	private String nroAllegatoRiferimento;
	private String nroPaginaRiferimento;
	private String nroRigaRiferimento;
	private String effettoEmendamento;
	private Boolean emendamentoIntegrale;
	private String testoHtml;
	private String uriFile;
	private String nomeFile;
	private Boolean firmato;
	private String mimetype;
	private Boolean convertibilePdf;
	private String pareriEspressi;
	private String filePareri;
	private String idProcess;
	private String bytes;
	private String organoCollegiale;
	private List<EmendamentoBean> listaSubEmendamenti;
	
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
	
	public String getNroAllegatoRiferimento() {
		return nroAllegatoRiferimento;
	}
	
	public void setNroAllegatoRiferimento(String nroAllegatoRiferimento) {
		this.nroAllegatoRiferimento = nroAllegatoRiferimento;
	}
	
	public String getNroPaginaRiferimento() {
		return nroPaginaRiferimento;
	}
	
	public void setNroPaginaRiferimento(String nroPaginaRiferimento) {
		this.nroPaginaRiferimento = nroPaginaRiferimento;
	}
	
	public String getNroRigaRiferimento() {
		return nroRigaRiferimento;
	}
	
	public void setNroRigaRiferimento(String nroRigaRiferimento) {
		this.nroRigaRiferimento = nroRigaRiferimento;
	}
	
	public String getEffettoEmendamento() {
		return effettoEmendamento;
	}
	
	public void setEffettoEmendamento(String effettoEmendamento) {
		this.effettoEmendamento = effettoEmendamento;
	}
	
	public Boolean getEmendamentoIntegrale() {
		return emendamentoIntegrale;
	}
	
	public void setEmendamentoIntegrale(Boolean emendamentoIntegrale) {
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
	
	public Boolean getFirmato() {
		return firmato;
	}
	
	public void setFirmato(Boolean firmato) {
		this.firmato = firmato;
	}
	
	public String getMimetype() {
		return mimetype;
	}
	
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	
	public Boolean getConvertibilePdf() {
		return convertibilePdf;
	}
	
	public void setConvertibilePdf(Boolean convertibilePdf) {
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
	
	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}
	
	public String getOrganoCollegiale() {
		return organoCollegiale;
	}

	public void setOrganoCollegiale(String organoCollegiale) {
		this.organoCollegiale = organoCollegiale;
	}

	public List<EmendamentoBean> getListaSubEmendamenti() {
		return listaSubEmendamenti;
	}

	public void setListaSubEmendamenti(List<EmendamentoBean> listaSubEmendamenti) {
		this.listaSubEmendamenti = listaSubEmendamenti;
	}

    public static Comparator<EmendamentoBean> EmendamentoNo = new Comparator<EmendamentoBean>() {

		@Override
		public int compare(EmendamentoBean e1, EmendamentoBean e2) {
			 int eNo1;
			 int eNo2;
			 
			 if (StringUtils.isNotBlank(e1.getNroSubEmendamento()) && StringUtils.isNotBlank(e2.getNroSubEmendamento())) {
				 eNo1 = Integer.parseInt(e1.getNroEmendamento());
				 eNo2 = Integer.parseInt(e2.getNroEmendamento());
			 } else {
				 eNo1 = Integer.parseInt(e1.getNroEmendamento());
				 eNo2 = Integer.parseInt(e2.getNroEmendamento());
			 }

			return eNo1-eNo2;
		}
	};
    
 
	
}
