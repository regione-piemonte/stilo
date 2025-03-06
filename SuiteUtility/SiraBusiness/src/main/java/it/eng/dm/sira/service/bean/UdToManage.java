package it.eng.dm.sira.service.bean;

import java.util.List;

public class UdToManage {
	
	private String idUd;

	private List<DocumentToManage> documenti;
	
	private String codRuolo;

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public List<DocumentToManage> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<DocumentToManage> documenti) {
		this.documenti = documenti;
	}

	public String getCodRuolo() {
		return codRuolo;
	}

	public void setCodRuolo(String codRuolo) {
		this.codRuolo = codRuolo;
	}
	
}
