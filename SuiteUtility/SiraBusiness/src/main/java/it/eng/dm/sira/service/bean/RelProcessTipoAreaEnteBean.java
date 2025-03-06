package it.eng.dm.sira.service.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelProcessTipoAreaEnteBean {
	
	private String idRel;
	private BigDecimal idProcess;
	private String nomeProcess;
	private BigDecimal idTipologia;
	private String nomeTipologia;
	private BigDecimal idArea;
	private String nomeArea;
	private String idDominio;	
	private List<DominiVMguOrganigrammaBean> listaDomini;

	private String descrizioneRelazione;

	public String getIdRel() {
		return idRel;
	}

	public void setIdRel(String idRel) {
		this.idRel = idRel;
	}

	public BigDecimal getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(BigDecimal idProcess) {
		this.idProcess = idProcess;
	}

	public String getNomeProcess() {
		return nomeProcess;
	}

	public void setNomeProcess(String nomeProcess) {
		this.nomeProcess = nomeProcess;
	}

	public BigDecimal getIdTipologia() {
		return idTipologia;
	}

	public void setIdTipologia(BigDecimal idTipologia) {
		this.idTipologia = idTipologia;
	}

	public String getNomeTipologia() {
		return nomeTipologia;
	}

	public void setNomeTipologia(String nomeTipologia) {
		this.nomeTipologia = nomeTipologia;
	}

	public BigDecimal getIdArea() {
		return idArea;
	}

	public void setIdArea(BigDecimal idArea) {
		this.idArea = idArea;
	}

	public String getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}

	public List<DominiVMguOrganigrammaBean> getListaDomini() {
		return listaDomini;
	}

	public void setListaDomini(List<DominiVMguOrganigrammaBean> listaDomini) {
		this.listaDomini = listaDomini;
	}

	public String getDescrizioneRelazione() {
		return descrizioneRelazione;
	}

	public void setDescrizioneRelazione(String descrizioneRelazione) {
		this.descrizioneRelazione = descrizioneRelazione;
	}

}
